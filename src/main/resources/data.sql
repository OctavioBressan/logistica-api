
/*
Usamos nextval() para que el sistema auto genere los id
y evitar conflictos.
*/

/* Datos ficticios para pruebas locales de la API. */


/* Inserciones a la tabla de direcciones */

INSERT INTO direcciones
(id, calle, numero, ciudad, provincia, codigo_postal)
SELECT
    nextval('direcciones_seq'),
    datos.calle,
    datos.numero,
    datos.ciudad,
    datos.provincia,
    datos.codigo_postal
FROM (
         VALUES
             ('San Martin', 125, 'Rio Tercero', 'Cordoba', '5850'),
             ('Belgrano', 450, 'Cordoba', 'Cordoba', '5000'),
             ('Mitre', 780, 'Villa Maria', 'Cordoba', '5900'),
             ('Rivadavia', 220, 'Tancacha', 'Cordoba', '5933'),
             ('Sarmiento', 910, 'Almafuerte', 'Cordoba', '5854')
     ) AS datos(calle, numero, ciudad, provincia, codigo_postal)
WHERE NOT EXISTS (
    SELECT 1
    FROM direcciones d
    WHERE d.calle = datos.calle
      AND d.numero = datos.numero
      AND d.ciudad = datos.ciudad
      AND d.provincia = datos.provincia
      AND d.codigo_postal = datos.codigo_postal
);


/* Inserciones a la tabla de transportistas */

INSERT INTO transportistas
(id, dni, nombre, apellido, telefono, email)
SELECT
    nextval('transportistas_seq'),
    datos.dni,
    datos.nombre,
    datos.apellido,
    datos.telefono,
    datos.email
FROM (
         VALUES
             ('40111001', 'Martin', 'Lopez',
              '3571123001', 'transportista1@ejemplo.com'),

             ('40111002', 'Lucia', 'Fernandez',
              '3571123002', 'transportista2@ejemplo.com'),

             ('40111003', 'Pablo', 'Gomez',
              '3571123003', 'transportista3@ejemplo.com'),

             ('40111004', 'Sofia', 'Martinez',
              '3571123004', 'transportista4@ejemplo.com'),

             ('40111005', 'Diego', 'Rodriguez',
              '3571123005', 'transportista5@ejemplo.com')
     ) AS datos(dni, nombre, apellido, telefono, email)
WHERE NOT EXISTS (
    SELECT 1
    FROM transportistas t
    WHERE t.dni = datos.dni
);


/* Inserciones a la tabla de clientes */

INSERT INTO clientes
(id, dni, nombre, apellido, email, telefono)
SELECT
    nextval('clientes_seq'),
    datos.dni,
    datos.nombre,
    datos.apellido,
    datos.email,
    datos.telefono
FROM (
         VALUES
             ('30111001', 'Juan', 'Perez',
              'cliente1@ejemplo.com', '3571223001'),

             ('30111002', 'Maria', 'Garcia',
              'cliente2@ejemplo.com', '3571223002'),

             ('30111003', 'Carlos', 'Romero',
              'cliente3@ejemplo.com', '3571223003'),

             ('30111004', 'Ana', 'Torres',
              'cliente4@ejemplo.com', '3571223004'),

             ('30111005', 'Pedro', 'Diaz',
              'cliente5@ejemplo.com', '3571223005')
     ) AS datos(dni, nombre, apellido, email, telefono)
WHERE NOT EXISTS (
    SELECT 1
    FROM clientes c
    WHERE c.dni = datos.dni
);

/* Inserciones a la tabla de usuarios */

INSERT INTO usuarios
(id, nombre, apellido, email, password, role)
SELECT
    nextval('usuarios_seq'),
    datos.nombre,
    datos.apellido,
    datos.email,
    datos.password,
    datos.role
FROM (
         VALUES
             ('Usuario', 'Demostracion',
              'usuario@example.com',
              '$2a$10$xLPpbHHhC9.Opm5elhi4ZOQKmMcXpi6XkPYKN6EWy8NZS.i2CrIR.', 0),

             ('Admin', 'Sistema',
              'admin@example.com',
              '$2a$10$xLPpbHHhC9.Opm5elhi4ZOQKmMcXpi6XkPYKN6EWy8NZS.i2CrIR.', 1),

             ('Lucia', 'Martinez',
              'lucia@example.com',
              '$2a$10$xLPpbHHhC9.Opm5elhi4ZOQKmMcXpi6XkPYKN6EWy8NZS.i2CrIR.', 0),

             ('Martin', 'Lopez',
              'martin@example.com',
              '$2a$10$xLPpbHHhC9.Opm5elhi4ZOQKmMcXpi6XkPYKN6EWy8NZS.i2CrIR.', 0),

             ('Sofia', 'Administrador',
              'sofia.admin@example.com',
              '$2a$10$xLPpbHHhC9.Opm5elhi4ZOQKmMcXpi6XkPYKN6EWy8NZS.i2CrIR.', 1)
     ) AS datos(nombre, apellido, email, password, role)
WHERE NOT EXISTS (
    SELECT 1
    FROM usuarios u
    WHERE u.email = datos.email
);


/* Inserciones a la tabla clientes_direcciones */

INSERT INTO clientes_direcciones
(cliente_id, direccion_id)
SELECT
    c.id,
    d.id
FROM (
         VALUES
             ('30111001', 'San Martin', 125, 'Rio Tercero'),
             ('30111001', 'Belgrano', 450, 'Cordoba'),
             ('30111002', 'Belgrano', 450, 'Cordoba'),
             ('30111003', 'Mitre', 780, 'Villa Maria'),
             ('30111003', 'Rivadavia', 220, 'Tancacha'),
             ('30111004', 'Rivadavia', 220, 'Tancacha'),
             ('30111005', 'Sarmiento', 910, 'Almafuerte')
     ) AS datos(dni, calle, numero, ciudad)
         JOIN clientes c
              ON c.dni = datos.dni
         JOIN direcciones d
              ON d.calle = datos.calle
                  AND d.numero = datos.numero
                  AND d.ciudad = datos.ciudad
WHERE NOT EXISTS (
    SELECT 1
    FROM clientes_direcciones cd
    WHERE cd.cliente_id = c.id
      AND cd.direccion_id = d.id
);


/* Inserciones a la tabla de envios */

INSERT INTO envios
(id, codigo_seguimiento, fecha_envio, estado,
 cliente_id, transportista_id,
 direccion_origen_id, direccion_destino_id)
SELECT
    nextval('envios_seq'),
    datos.codigo,
    datos.fecha,
    datos.estado,
    c.id,
    t.id,
    origen.id,
    destino.id
FROM (
         VALUES
             ('SEG-2026-001', DATE '2026-09-20', 'PENDIENTE',
              '30111001', '40111001',
              'San Martin', 'Mitre'),

             ('SEG-2026-002', DATE '2026-09-21', 'EN_CAMINO',
              '30111002', '40111002',
              'Belgrano', 'Rivadavia'),

             ('SEG-2026-003', DATE '2026-09-22', 'ENTREGADO',
              '30111003', '40111003',
              'Mitre', 'Sarmiento'),

             ('SEG-2026-004', DATE '2026-09-23', 'PENDIENTE',
              '30111004', '40111004',
              'Rivadavia', 'San Martin'),

             ('SEG-2026-005', DATE '2026-09-24', 'EN_CAMINO',
              '30111005', '40111005',
              'Sarmiento', 'Belgrano')
     ) AS datos(
                codigo, fecha, estado,
                dni_cliente, dni_transportista,
                calle_origen, calle_destino
    )
         JOIN clientes c
              ON c.dni = datos.dni_cliente
         JOIN transportistas t
              ON t.dni = datos.dni_transportista
         JOIN direcciones origen
              ON origen.calle = datos.calle_origen
         JOIN direcciones destino
              ON destino.calle = datos.calle_destino
WHERE NOT EXISTS (
    SELECT 1
    FROM envios e
    WHERE e.codigo_seguimiento = datos.codigo
);


/* Inserciones a la tabla de paquetes */

INSERT INTO paquetes
(id, descripcion, peso, alto, ancho, largo, envio_id)
SELECT
    nextval('paquetes_seq'),
    datos.descripcion,
    datos.peso,
    datos.alto,
    datos.ancho,
    datos.largo,
    e.id
FROM (
         VALUES
             ('Documentacion',
              0.5, 5.0, 20.0, 30.0, 'SEG-2026-001'),

             ('Componentes electronicos',
              2.5, 15.0, 25.0, 35.0, 'SEG-2026-002'),

             ('Repuestos mecanicos',
              8.0, 30.0, 40.0, 50.0, 'SEG-2026-003'),

             ('Indumentaria',
              3.0, 20.0, 30.0, 40.0, 'SEG-2026-004'),

             ('Material de oficina',
              5.5, 25.0, 35.0, 45.0, 'SEG-2026-005')
     ) AS datos(
                descripcion, peso, alto, ancho, largo, codigo_envio
    )
         JOIN envios e
              ON e.codigo_seguimiento = datos.codigo_envio
WHERE NOT EXISTS (
    SELECT 1
    FROM paquetes p
    WHERE p.descripcion = datos.descripcion
      AND p.envio_id = e.id
);