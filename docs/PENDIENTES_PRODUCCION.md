# Pendientes para operar en producción

El backend es una demostración funcional adecuada para explicar arquitectura, persistencia, autenticación y optimización en un portfolio. **No es todavía un sistema listo para desplegar y administrar operaciones reales de una empresa.** La clave JWT y la contraseña de PostgreSQL ya se obtienen de variables de entorno, y los archivos locales con secretos están excluidos de Git. Los siguientes puntos detallan lo que todavía falta para operar con datos reales.

## Prioridad alta: seguridad y configuración

1. **Gestión y rotación de secretos.** La configuración ya utiliza `DB_PASSWORD` y `JWT_SECRET` provenientes del entorno, sin valores secretos predeterminados. Para una operación real se necesita un gestor de secretos, política de rotación y restricciones de acceso. La clave JWT antigua del proyecto académico no debe reutilizarse.
2. **No publicar historiales con secretos.** El proyecto público debe iniciar con un repositorio Git nuevo. No importar el historial Mercurial anterior, que incluía valores de demostración embebidos. El ZIP público no incluye `.hg`.
3. **Eliminar cuentas de prueba de ambientes reales.** `data.sql` incluye usuarios y credenciales de demostración. Su uso debe restringirse a una base de desarrollo aislada; nunca cargar este script en producción.
4. **Habilitar TLS/HTTPS.** JWT y credenciales no deben transmitirse por HTTP público.
5. **Revisar la política de autorizaciones por recurso.** Actualmente un USER autenticado puede acceder a las rutas de negocio protegidas genéricamente. Antes de utilizar datos de clientes reales, debe definirse quién puede ver y modificar cada recurso, con controles por usuario/organización.
6. **Revisar exposición de Swagger y Actuator.** En esta versión Swagger es público y Actuator se limita a ADMIN. En producción conviene revisar exposición por ambiente, segmentación de red y privilegios mínimos.
7. **Revisar CORS.** La configuración actual permite solamente `http://localhost:3000`; debería establecerse desde configuración del entorno y limitarse a los orígenes desplegados.

## Prioridad alta: persistencia y operación

8. **Migraciones versionadas.** Sustituir `spring.jpa.hibernate.ddl-auto=update` por una política de migraciones controladas (por ejemplo, Flyway o Liquibase) y validación de esquema.
9. **Observabilidad y registros.** Establecer logs estructurados, niveles por ambiente, correlación de peticiones, métricas operativas y alertas. Desactivar el SQL detallado por defecto fuera de desarrollo para evitar exposición de datos y ruido.
10. **Backups y recuperación.** Definir políticas reales de copia de seguridad, restauración, monitoreo de PostgreSQL y retención.
11. **Pruebas automatizadas.** Incorporar pruebas unitarias, de integración, seguridad, control de permisos y comportamiento de caché; las solicitudes Postman actuales no sustituyen una suite de regresión.
12. **Despliegue reproducible y CI.** Incorporar perfiles por ambiente, pipeline de compilación, análisis estático, validaciones y un proceso de despliegue documentado.

## Evolución funcional y de rendimiento

13. **Paginación y filtros.** Actualmente hay listados completos sin paginación generalizada. Una API con volumen real requiere límites y filtros, en especial para envíos.
14. **Caché en entornos distribuidos.** La caché actual es en memoria, sin TTL y sin coordinación entre instancias. Si la solución escala horizontalmente, definir expiración, invalidez, métricas y, según el caso, un proveedor distribuido.
15. **Definir unidades y contratos.** El modelo tiene peso y dimensiones numéricas, pero las unidades de medida no forman parte del contrato; establecerlas, documentarlas y validarlas.
16. **API versionada.** Valorar `/api/v1` y una estrategia de compatibilidad si la API fuese consumida por clientes externos.
17. **Hardening HTTP.** Evaluar límites de solicitudes, rate limiting del login, controles sobre respuestas de error, cabeceras de seguridad y auditoría de acciones críticas.
18. **Semántica HTTP.** Revisar si los POST de creación deberían responder `201 Created` y los DELETE `204 No Content`, considerando compatibilidad con consumidores existentes.
19. **Gestión del ciclo de vida de JWT.** Valorar políticas de expiración, revocación/rotación y refresh tokens según la necesidad de negocio; actualmente el token tiene una vida de 24 horas.

## Consideración sobre privacidad

Los datos de prueba de `data.sql` y Postman son **públicos y ficticios**. Se reemplazaron los correos personales de muestra por direcciones de `example.com`. Nunca añadir datos reales de clientes, secretos productivos ni archivos locales con credenciales.

## Próximo hito recomendado

Para una segunda versión orientada a despliegue real, priorizar: (1) gestión y rotación de secretos, (2) reglas de autorización por recurso, (3) migraciones y perfiles por ambiente, (4) suite automatizada y CI, (5) paginación y observabilidad.
