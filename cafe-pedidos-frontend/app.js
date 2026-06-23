/* ==========================================================================
   CafeExpress - logica de la SPA (Vue 3, sin paso de build)
   Consume la API REST del backend Spring Boot.
   ========================================================================== */

const { createApp } = Vue;

// Si el backend corre en otra maquina/puerto, cambiar aca.
const API = "http://localhost:8080/api";

createApp({
    data() {
        return {
            vista: "mostrador",

            // Catalogo
            menu: { bases: [], adicionales: [], descuentos: [] },

            // Carrito (cliente)
            carrito: [],
            cliente: "",
            tipoDescuento: "ninguno",
            puntos: 0,

            // Modal "personalizar" (Decorator)
            modalAbierto: false,
            baseSel: null,
            adicionalesSel: [],
            cantidadSel: 1,

            // Cocina (KDS)
            tickets: [],
            notificaciones: [],
            stats: { conteoPorEstado: {}, facturacionEntregada: 0 },
            timer: null,

            // Toasts accesibles
            toasts: [],
            toastSeq: 0,
        };
    },

    computed: {
        // Precio unitario de la bebida que se esta personalizando (base + adicionales).
        precioUnitarioModal() {
            if (!this.baseSel) return 0;
            const extras = this.adicionalesSel.reduce((acc, id) => acc + this.precioAdicional(id), 0);
            return this.baseSel.precio + extras;
        },
        precioModal() {
            return this.precioUnitarioModal * this.cantidadSel;
        },
        descripcionModal() {
            if (!this.baseSel) return "";
            const extras = this.adicionalesSel.map((id) => this.nombreAdicional(id));
            return [this.baseSel.nombre, ...extras].join(" + ");
        },
        subtotalCarrito() {
            return this.carrito.reduce((acc, i) => acc + i.precioUnitario * i.cantidad, 0);
        },
        // Estimacion de descuento espejada del backend (el total final lo confirma el server).
        descuentoEstimado() {
            const sub = this.subtotalCarrito;
            switch (this.tipoDescuento) {
                case "estudiante":
                    return sub * 0.1;
                case "happyhour": {
                    const h = new Date().getHours();
                    return h >= 15 && h < 18 ? sub * 0.2 : 0;
                }
                case "fidelidad":
                    return Math.min(this.puntos * 50, sub);
                default:
                    return 0;
            }
        },
        totalEstimado() {
            return this.subtotalCarrito - this.descuentoEstimado;
        },
    },

    methods: {
        // ---------- Catalogo ----------
        async cargarMenu() {
            try {
                const r = await fetch(`${API}/menu`);
                this.menu = await r.json();
            } catch (e) {
                this.toast("No se pudo cargar el menu. ¿Esta corriendo el backend?", "error");
            }
        },

        // ---------- Modal personalizar (Decorator) ----------
        abrirModal(base) {
            this.baseSel = base;
            this.adicionalesSel = [];
            this.cantidadSel = 1;
            this.modalAbierto = true;
        },
        cerrarModal() {
            this.modalAbierto = false;
            this.baseSel = null;
        },
        toggleAdicional(id) {
            const i = this.adicionalesSel.indexOf(id);
            if (i >= 0) this.adicionalesSel.splice(i, 1);
            else this.adicionalesSel.push(id);
        },
        agregarAlCarrito() {
            this.carrito.push({
                baseId: this.baseSel.id,
                adicionales: [...this.adicionalesSel],
                cantidad: Math.max(1, this.cantidadSel),
                descripcion: this.descripcionModal,
                precioUnitario: this.precioUnitarioModal,
            });
            this.toast(`Agregado: ${this.descripcionModal}`, "ok");
            this.cerrarModal();
        },
        quitarDelCarrito(i) {
            this.carrito.splice(i, 1);
        },

        // ---------- Confirmar pedido ----------
        async confirmarPedido() {
            if (this.carrito.length === 0) {
                this.toast("El carrito esta vacio", "error");
                return;
            }
            const body = {
                cliente: this.cliente,
                tipoDescuento: this.tipoDescuento,
                puntosFidelidad: Number(this.puntos) || 0,
                items: this.carrito.map((i) => ({
                    baseId: i.baseId,
                    adicionales: i.adicionales,
                    cantidad: i.cantidad,
                })),
            };
            try {
                const r = await fetch(`${API}/pedidos`, {
                    method: "POST",
                    headers: { "Content-Type": "application/json" },
                    body: JSON.stringify(body),
                });
                if (!r.ok) throw new Error((await r.json()).error || "Error");
                const pedido = await r.json();
                this.toast(`Pedido #${pedido.id} confirmado. Total ${this.fmt(pedido.total)}`, "ok");
                this.carrito = [];
                this.cliente = "";
                this.tipoDescuento = "ninguno";
                this.puntos = 0;
                this.refrescarCocina();
            } catch (e) {
                this.toast(e.message, "error");
            }
        },

        // ---------- Cocina (State + Observer) ----------
        async refrescarCocina() {
            try {
                const [rp, rn, rs] = await Promise.all([
                    fetch(`${API}/pedidos`),
                    fetch(`${API}/notificaciones`),
                    fetch(`${API}/estadisticas`),
                ]);
                const pedidos = await rp.json();
                this.tickets = pedidos.filter((p) =>
                    ["PENDIENTE", "EN_PREPARACION", "LISTO"].includes(p.estado)
                );
                this.notificaciones = await rn.json();
                this.stats = await rs.json();
            } catch (e) {
                /* backend abajo: se reintenta en el proximo tick */
            }
        },
        async accion(id, accion) {
            // Confirmacion para acciones destructivas (prevencion de errores).
            if (accion === "cancelar" && !confirm(`¿Cancelar el pedido #${id}?`)) return;
            const endpoint = {
                preparar: "preparar",
                listo: "listo",
                entregar: "entregar",
                cancelar: "cancelar",
            }[accion];
            try {
                const r = await fetch(`${API}/pedidos/${id}/${endpoint}`, { method: "POST" });
                if (!r.ok) throw new Error((await r.json()).error || "Error");
                await this.refrescarCocina();
            } catch (e) {
                this.toast(e.message, "error");
            }
        },

        // ---------- Helpers ----------
        nombreAdicional(id) {
            const a = this.menu.adicionales.find((x) => x.id === id);
            return a ? a.nombre : id;
        },
        precioAdicional(id) {
            const a = this.menu.adicionales.find((x) => x.id === id);
            return a ? a.precio : 0;
        },
        etiquetaEstado(estado) {
            return {
                PENDIENTE: "⏳ Pendiente",
                EN_PREPARACION: "☕ En preparacion",
                LISTO: "✅ Listo",
                ENTREGADO: "📦 Entregado",
                CANCELADO: "✖ Cancelado",
            }[estado] || estado;
        },
        etiquetaAccion(accion) {
            return {
                preparar: "▶ Preparar",
                listo: "✅ Marcar listo",
                entregar: "📦 Entregar",
                cancelar: "✖ Cancelar",
            }[accion] || accion;
        },
        claseBotonAccion(accion) {
            if (accion === "cancelar") return "btn btn-peligro";
            return "btn btn-primario";
        },
        fmt(n) {
            return "$" + Number(n).toLocaleString("es-AR");
        },
        toast(mensaje, tipo = "") {
            const id = ++this.toastSeq;
            this.toasts.push({ id, mensaje, tipo });
            setTimeout(() => {
                this.toasts = this.toasts.filter((t) => t.id !== id);
            }, 3500);
        },
    },

    mounted() {
        this.cargarMenu();
        this.refrescarCocina();
        // Polling: la cocina refleja los cambios que disparan los observadores.
        this.timer = setInterval(this.refrescarCocina, 2500);
    },
    unmounted() {
        clearInterval(this.timer);
    },
}).mount("#app");
