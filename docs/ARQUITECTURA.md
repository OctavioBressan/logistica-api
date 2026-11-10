# Arquitectura y decisiones técnicas

## Alcance

La API modela una operación logística con clientes, direcciones, transportistas, envíos, paquetes y usuarios. Proporciona operaciones CRUD sobre las cinco entidades de negocio y endpoints adicionales de búsqueda, autenticación, monitoreo y gestión de caché.

La organización existente sigue una estructura por capas. Esta edición para portfolio conserva los servicios, repositorios, mapeos de DTO y demás reglas de negocio del proyecto original.

## Componentes

**Controller:** recibe peticiones HTTP, ejecuta validación de los `RequestDTO` marcados con `@Valid`, delega a servicios y publica los endpoints. Se utiliza SpringDoc para describir sus operaciones.

**Service:** centraliza la lógica de aplicación, resuelve referencias entre entidades, administra las transacciones y transforma entidades a `ResponseDTO`. La lógica está implementada en clases `*ServiceImpl`.

**Repository:** usa interfaces de Spring Data JPA que extienden `JpaRepository`. Incluye consultas derivadas del nombre y la consulta optimizada del listado de envíos.

**Model:** separa las entidades JPA de los modelos de entrada y salida. Los DTO ayudan a controlar los datos expuestos por la API.

**Security:** valida el JWT recibido en el encabezado `Authorization`, carga el usuario y aplica reglas de autenticación y autorización con Spring Security. Los roles utilizados en el proyecto son `USER` y `ADMIN`.

**Exception:** centraliza los errores que alcanzan Spring MVC y genera respuestas JSON homogéneas. La cadena de seguridad también dispone de componentes específicos para `401` y `403`.

## Flujo de una solicitud autenticada

1. El cliente envía una petición HTTP con `Authorization: Bearer <token>`.
2. `JwtAuthenticationFilter` examina el token y establece la autenticación si es válido.
3. `SecurityFilterChain` evalúa los permisos de acceso a la ruta.
4. Spring MVC invoca el controlador correspondiente.
5. El controlador valida la entrada y delega a un servicio.
6. El servicio trabaja con los repositorios y convierte el resultado en un DTO.
7. El controlador devuelve una respuesta serializada en JSON; las excepciones se transforman en respuestas HTTP de error según su origen.

## Modelo de dominio

- Un `Cliente` puede estar vinculado a múltiples `Direccion`.
- Un `Envio` referencia a un `Cliente`, una `Direccion` de origen y otra de destino.
- Un `Transportista` puede asociarse a múltiples `Envio`; la asignación es opcional.
- Un `Envio` puede tener múltiples `Paquete`.
- `Usuario` pertenece a la capa de autenticación y almacena las credenciales codificadas y el rol.

## Optimización de consultas

El listado de envíos necesita obtener `envio.getPaquetes().size()` para calcular `cantidadPaquetes`. Si la colección se consulta de forma diferida, cada acceso puede disparar consultas adicionales. Por eso, en `EnvioRepository` se definió un método de consulta específico con:

```java
@EntityGraph(attributePaths = {"paquetes"})
List<Envio> findAllBy();
```

`EnvioServiceImpl.listarEnvios()` utiliza `findAllBy()` y transforma los resultados en `EnvioResponseDTO`. El método `findAll()` heredado de `JpaRepository` conserva su funcionamiento normal.

**Alcance de la decisión:** la carga anticipada de paquetes se aplica al listado general. Los demás métodos de búsqueda no utilizan necesariamente la misma estrategia.

## Estrategia de caché

La aplicación usa el sistema de caché simple de Spring Boot. `@Cacheable(value = "envios")` evita ejecutar nuevamente `listarEnvios()` cuando el resultado está almacenado. Al no existir argumentos en el método, la clave predeterminada es `SimpleKey.EMPTY`.

Se invalida con `@CacheEvict(value = "envios", allEntries = true)` al modificar envíos o paquetes. También existe un endpoint de invalidación manual, protegido con `@PreAuthorize("hasAuthority('ADMIN')")`.

La caché es local al proceso y no es transaccional ni distribuida; para múltiples instancias y cargas productivas habría que evaluar otra política de coherencia, expiración y monitoreo.

## Decisiones de seguridad

- JWT como mecanismo de autenticación sin sesión en servidor (`STATELESS`).
- Contraseñas codificadas mediante BCrypt.
- Registro público con asignación de rol `USER`.
- Operaciones de negocio protegidas por autenticación.
- Monitoreo Actuator y limpieza manual de caché reservados a `ADMIN`.
- Documentación pública, sin acceso implícito a operaciones protegidas.
- CORS limitado al origen local configurado para el frontend de demostración.

## Límites conocidos

No existe una infraestructura de despliegue productivo, configuración de secretos por ambiente, migraciones de esquema, paginación sistemática, caché distribuida ni una suite integral de pruebas automatizadas. Las medidas necesarias están detalladas en [Pendientes para producción](PENDIENTES_PRODUCCION.md).
