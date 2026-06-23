# Cómo ejecutar CaféExpress

Hay dos formas de levantar el proyecto: con **Docker** (recomendada, un solo comando) o de forma **manual** (sin Docker).

---

## Opción A — Con Docker (recomendada)

### Requisitos
- [Docker](https://docs.docker.com/get-docker/) instalado.
- Docker Compose (viene incluido en Docker Desktop; en Linux: plugin `docker compose`).

### Pasos
Desde la carpeta raíz del proyecto (donde está `docker-compose.yml`):

```bash
docker compose up --build
```

> Si tu Docker es viejo y no reconoce `docker compose`, usá el guion: `docker-compose up --build`.

Esto construye y levanta **dos contenedores**:

| Servicio | Imagen | Puerto host | Descripción |
|---|---|---|---|
| `cafe-backend` | Spring Boot (Java 17) | `8080` | API REST |
| `cafe-frontend` | Nginx | `5500` | SPA (interfaz web) |

### Usar la aplicación
Abrir en el navegador:

- **Aplicación web:** http://localhost:5500
- **API (opcional, para probar):** http://localhost:8080/api/menu

La primera build tarda unos minutos (descarga dependencias de Maven). Las siguientes son casi instantáneas por la caché.

### Detener
```bash
# Ctrl+C en la terminal, y luego:
docker compose down
```

### Comandos útiles
```bash
docker compose up --build -d     # levantar en segundo plano
docker compose logs -f           # ver logs
docker compose ps                # ver estado de los contenedores
docker compose down              # bajar todo
```

---

## Opción B — Manual (sin Docker)

### Requisitos
- Java 17+ y Maven (backend).
- Python 3 **o** la extensión *Live Server* de VS Code (frontend).

### Backend
```bash
cd cafe-pedidos-backend
mvn spring-boot:run
# o: mvn clean package && java -jar target/cafe-pedidos-backend-1.0.0.jar
# API en http://localhost:8080
```

### Frontend
En otra terminal:
```bash
cd cafe-pedidos-frontend
python3 -m http.server 5500
# Abrir http://localhost:5500/index.html
```

---

## Notas
- El frontend (que corre en el navegador) consume la API en `http://localhost:8080`. Ambas opciones mapean ese puerto al host, así que funciona sin cambios.
- Si querés apuntar el frontend a otra dirección de backend, editá la constante `API` al inicio de `cafe-pedidos-frontend/app.js`.
- El backend habilita CORS para `/api/**`, por eso el frontend puede consumirlo desde otro puerto/origen.

## Prueba rápida de la API
```bash
# Menú
curl http://localhost:8080/api/menu

# Crear un pedido (Latte + shot + vainilla, descuento estudiante)
curl -X POST http://localhost:8080/api/pedidos \
  -H "Content-Type: application/json" \
  -d '{"cliente":"Gregorio","tipoDescuento":"estudiante","puntosFidelidad":0,
       "items":[{"baseId":"lat","adicionales":["shot","vainilla"],"cantidad":1}]}'
```
