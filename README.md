# CaféExpress — TP Final Integrador (Ingeniería de Software III)

Sistema **full-stack** de pedidos de cafetería que integra **cuatro patrones de diseño GOF** en el backend y **Diseño Centrado en el Usuario (DCU/UX/HCI)** en el frontend.

- **Integrantes (Grupo D):** Gregorio Luján, Manuel Moya, Jan Lahorca, Agustín Martínez
- **Materia:** Ingeniería de Software III (UCH) — Docente: Lic. Marcelo Palma

## Patrones aplicados

| Patrón | Dónde | Para qué |
|---|---|---|
| **Decorator** | `bebida/` | Componer una bebida base con N adicionales sin explosión de clases |
| **State** | `pedido/estado/` | Ciclo de vida del pedido con transiciones válidas por estado |
| **Strategy** | `descuento/` | Políticas de descuento intercambiables (estudiante, happy hour, fidelidad) |
| **Observer** | `pedido/observer/` | Difundir cada cambio de estado a cocina, cliente y estadísticas |

## Estructura del repositorio

```
TP-Final-ISW3/
├── docker-compose.yml      Orquesta backend + frontend (docker compose up --build)
├── COMO-EJECUTAR.md        Instructivo de ejecución (Docker y manual)
├── cafe-pedidos-backend/   Backend Java 17 + Spring Boot 3.3 (Maven) + Dockerfile
├── cafe-pedidos-frontend/  Frontend SPA Vue 3 (sin build, vendorizado) + Dockerfile
├── diagramas/              UML PlantUML (.puml) + .png (clases y secuencia por patrón)
├── wireframes/             Wireframes de baja fidelidad (.svg / .png)
├── capturas/               Capturas de la app funcionando
└── informe/                Informe integrador (.md fuente, .pdf y .docx generados)
```

## Cómo ejecutar

Instrucciones completas en **[COMO-EJECUTAR.md](COMO-EJECUTAR.md)**.

### Con Docker (recomendada)
Desde la raíz del proyecto:
```bash
docker compose up --build
```
- Aplicación web: http://localhost:5500
- API: http://localhost:8080

### Manual (sin Docker)
```bash
# Backend
cd cafe-pedidos-backend && mvn spring-boot:run        # http://localhost:8080
# Frontend (otra terminal)
cd cafe-pedidos-frontend && python3 -m http.server 5500   # http://localhost:5500
```

## Endpoints principales

| Método | Endpoint | Descripción |
|---|---|---|
| GET | `/api/menu` | Catálogo (bebidas, adicionales, descuentos) |
| POST | `/api/pedidos` | Crea un pedido (Decorator + Strategy) |
| POST | `/api/pedidos/{id}/{preparar\|listo\|entregar\|cancelar}` | Transiciones de estado (State) |
| GET | `/api/cocina` · `/api/notificaciones` · `/api/estadisticas` | Vistas de los observadores (Observer) |

## Regenerar diagramas e informe

```bash
# Diagramas (requiere plantuml.jar y Java)
cd diagramas && java -jar ~/plantuml.jar -tpng *.puml

# Wireframes SVG -> PNG (requiere cairosvg)
cd ../wireframes && for f in wf-*; do python3 -c "import cairosvg;cairosvg.svg2png(url='$f',write_to='${f%.svg}.png',output_width=760)"; done

# Informe PDF y DOCX (requiere markdown, weasyprint, python-docx, pillow)
cd ../informe && python3 build_pdf.py && python3 build_docx.py
```

## Pruebas

```bash
cd cafe-pedidos-backend && mvn test
```
`PatronesTest` valida los cuatro patrones (Decorator, State, Strategy, Observer).
