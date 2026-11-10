# Guía de la API

Todas las rutas de esta guía son relativas a `http://localhost:8080`. La API funciona con JSON y, salvo las rutas públicas de autenticación y documentación, requiere un token Bearer válido.

## Autenticación

| Método | Ruta | Descripción |
| --- | --- | --- |
| POST | `/api/auth/registro` | Registrar un usuario con autoridad `USER` |
| POST | `/api/auth/autenticar` | Verificar credenciales y obtener JWT |

Ejemplo de solicitud:

```http
POST /api/auth/autenticar
Content-Type: application/json
```

```json
{
  "email": "admin@example.com",
  "password": "<contraseña de demostración>"
}
```

La respuesta correcta devuelve `token`. En Postman, los scripts de login lo guardan en las variables `tokenUSER` o `tokenADMIN` según la petición ejecutada.

## Recursos de negocio

### Clientes

| Método | Ruta | Acción |
| --- | --- | --- |
| POST | `/api/clientes` | Crear cliente |
| GET | `/api/clientes` | Listar clientes |
| GET | `/api/clientes/{id}` | Obtener un cliente |
| PUT | `/api/clientes/{id}` | Actualizar un cliente |
| DELETE | `/api/clientes/{id}` | Eliminar un cliente |
| GET | `/api/clientes/buscar?dni={dni}` | Buscar cliente por DNI |
| GET | `/api/clientes/{id}/envios` | Listar envíos de un cliente |

### Direcciones

| Método | Ruta | Acción |
| --- | --- | --- |
| POST | `/api/direcciones` | Crear dirección |
| GET | `/api/direcciones` | Listar direcciones |
| GET | `/api/direcciones/{id}` | Obtener una dirección |
| PUT | `/api/direcciones/{id}` | Actualizar una dirección |
| DELETE | `/api/direcciones/{id}` | Eliminar una dirección |

### Transportistas

| Método | Ruta | Acción |
| --- | --- | --- |
| POST | `/api/transportistas` | Crear transportista |
| GET | `/api/transportistas` | Listar transportistas |
| GET | `/api/transportistas/{id}` | Obtener un transportista |
| PUT | `/api/transportistas/{id}` | Actualizar un transportista |
| DELETE | `/api/transportistas/{id}` | Eliminar un transportista |

### Envíos

| Método | Ruta | Acción |
| --- | --- | --- |
| POST | `/api/envios` | Crear envío |
| GET | `/api/envios` | Listar envíos (consulta optimizada con caché) |
| GET | `/api/envios/{id}` | Obtener un envío |
| PUT | `/api/envios/{id}` | Actualizar un envío |
| DELETE | `/api/envios/{id}` | Eliminar un envío |
| GET | `/api/envios/ordenados` | Listar envíos ordenados por fecha |
| POST | `/api/envios/cache/limpiar` | Invalidar caché de envíos (solo ADMIN) |

Ejemplo de creación:

```http
POST /api/envios
Authorization: Bearer <TOKEN_JWT>
Content-Type: application/json
```

```json
{
  "codigoSeguimiento": "SEG-2026-006",
  "fechaEnvio": "2026-11-06",
  "estado": "PENDIENTE",
  "idTransportista": 1,
  "idCliente": 1,
  "idDireccionOrigen": 1,
  "idDireccionDestino": 2
}
```

Los identificadores son ilustrativos: deben corresponder a filas existentes en la base de datos. El campo `idTransportista` puede omitirse o enviarse como `null` cuando todavía no hay transportista asignado.

### Paquetes

| Método | Ruta | Acción |
| --- | --- | --- |
| POST | `/api/paquetes` | Crear paquete |
| GET | `/api/paquetes` | Listar paquetes |
| GET | `/api/paquetes/{id}` | Obtener un paquete |
| PUT | `/api/paquetes/{id}` | Actualizar un paquete |
| DELETE | `/api/paquetes/{id}` | Eliminar un paquete |

Ejemplo de creación:

```json
{
  "descripcion": "Componentes electrónicos",
  "peso": 2.5,
  "alto": 15.0,
  "ancho": 25.0,
  "largo": 35.0,
  "idEnvio": 1
}
```

Los campos de peso y dimensiones deben ser positivos. La implementación no define unidades como parte del contrato API actual; cualquier integración real debería acordarlas de forma explícita.

## Documentación y monitoreo

| Método | Ruta | Autorización |
| --- | --- | --- |
| GET | `/swagger-ui.html` | Pública |
| GET | `/v3/api-docs` | Pública |
| GET | `/actuator/health` | ADMIN |
| GET | `/actuator/info` | ADMIN |
| GET | `/actuator/metrics` | ADMIN |

## Códigos de respuesta

La API devuelve `200 OK` para las operaciones CRUD exitosas del proyecto actual, incluso en creación o eliminación. Esto refleja el contrato existente; no se añadió un cambio de semántica HTTP a esta edición documental.

- `400 Bad Request`: datos no válidos o error en la solicitud.
- `401 Unauthorized`: ausencia de autenticación válida o credenciales incorrectas.
- `403 Forbidden`: usuario autenticado sin permisos suficientes.
- `404 Not Found`: recurso inexistente.
- `409 Conflict`: duplicación de datos con restricciones de unicidad o conflicto de negocio gestionado.
- `500 Internal Server Error`: error no previsto.

La estructura de errores contiene `timestamp`, `status`, `error`, `message` y `path`. La lista anterior muestra los códigos contemplados; cada endpoint puede devolver solo un subconjunto de ellos.

## Prueba manual de la caché

1. Iniciar la aplicación o limpiar la caché.
2. Ejecutar `GET /api/envios` y observar las consultas SQL.
3. Repetir `GET /api/envios` y comprobar que no se repite la consulta de la lista.
4. Enviar `POST /api/envios/cache/limpiar` con JWT ADMIN.
5. Ejecutar de nuevo el GET y comprobar que vuelve a consultar PostgreSQL.
6. Comprobar que un JWT USER obtiene `403` al intentar limpiar la caché.

La colección `postman/logistica-api.postman_collection.json` contiene solicitudes preparadas para este recorrido. La respuesta a una prueba CORS en Postman es orientativa: el navegador es quien impone realmente la política de origen cruzado.
