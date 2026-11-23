# ApiRest_Spa

## Insertar usuarios de prueba

`UserRequest` recibe `username`, `email` y `password`. El servicio guarda `username` como `nombre` y cifra la contraseña con BCrypt; por eso, al insertar directamente en la base de datos, la columna `contrasenia` debe contener un hash BCrypt, no la contraseña en texto plano.

El siguiente SQL agrega cinco usuarios a `Usuarios`. Las contraseñas para iniciar sesión son `SpaUser@2026` hasta `SpaUser@2030`, en el mismo orden:

Ejecuta los bloques en el orden mostrado sobre una base de datos sin estos datos de prueba; las inserciones no están diseñadas para ejecutarse varias veces.

```sql
INSERT INTO Usuarios(fecha_registro, nombre, email, contrasenia)
VALUES
    (NOW(), 'UsuarioUno', 'usuario1@example.com', '$2a$10$XqReKhng9fF928s9yWMqseEkbG5V6qUlY7/NZ09R23VKMs2shoL6i'),
    (NOW(), 'UsuarioDos', 'usuario2@example.com', '$2a$10$mlQplF4vF5qsLzchWmlLG.kVgXFjVqrecidK.IZmD3PCvYfUkK2tG'),
    (NOW(), 'UsuarioTres', 'usuario3@example.com', '$2a$10$R/ZkcB2fLh6JOEZ7DPWp9OoezBxYc1WxoA8muxE4nxLCnKvG0y.Ay'),
    (NOW(), 'UsuarioCuatro', 'usuario4@example.com', '$2a$10$Yfs5zLrzdMQhfq1lh1jmFOOoIUXaMMbNQ6McM8FMAiDRHf4sCKEq.'),
    (NOW(), 'UsuarioCinco', 'usuario5@example.com', '$2a$10$4NQij0Yh3HNnwKtCajFrpOeyj4jOF.hsTEZKz0fw/v9Bd6DM7mHV6');
```
## Insertar terapeutas

Cada terapeuta comparte su identificador con un registro de `Usuarios` (`@MapsId` en la entidad `Terapeuta`). Por eso, primero inserta diez usuarios que servirán como cuentas de los terapeutas. En estos datos de prueba, todos usan la contraseña `SpaUser@2026`.

```sql
INSERT INTO Usuarios(fecha_registro, nombre, email, contrasenia)
VALUES
    (NOW(), 'TerapeutaUno', 'terapeuta1@example.com', '$2a$10$XqReKhng9fF928s9yWMqseEkbG5V6qUlY7/NZ09R23VKMs2shoL6i'),
    (NOW(), 'TerapeutaDos', 'terapeuta2@example.com', '$2a$10$XqReKhng9fF928s9yWMqseEkbG5V6qUlY7/NZ09R23VKMs2shoL6i'),
    (NOW(), 'TerapeutaTres', 'terapeuta3@example.com', '$2a$10$XqReKhng9fF928s9yWMqseEkbG5V6qUlY7/NZ09R23VKMs2shoL6i'),
    (NOW(), 'TerapeutaCuatro', 'terapeuta4@example.com', '$2a$10$XqReKhng9fF928s9yWMqseEkbG5V6qUlY7/NZ09R23VKMs2shoL6i'),
    (NOW(), 'TerapeutaCinco', 'terapeuta5@example.com', '$2a$10$XqReKhng9fF928s9yWMqseEkbG5V6qUlY7/NZ09R23VKMs2shoL6i'),
    (NOW(), 'TerapeutaSeis', 'terapeuta6@example.com', '$2a$10$XqReKhng9fF928s9yWMqseEkbG5V6qUlY7/NZ09R23VKMs2shoL6i'),
    (NOW(), 'TerapeutaSiete', 'terapeuta7@example.com', '$2a$10$XqReKhng9fF928s9yWMqseEkbG5V6qUlY7/NZ09R23VKMs2shoL6i'),
    (NOW(), 'TerapeutaOcho', 'terapeuta8@example.com', '$2a$10$XqReKhng9fF928s9yWMqseEkbG5V6qUlY7/NZ09R23VKMs2shoL6i'),
    (NOW(), 'TerapeutaNueve', 'terapeuta9@example.com', '$2a$10$XqReKhng9fF928s9yWMqseEkbG5V6qUlY7/NZ09R23VKMs2shoL6i'),
    (NOW(), 'TerapeutaDiez', 'terapeuta10@example.com', '$2a$10$XqReKhng9fF928s9yWMqseEkbG5V6qUlY7/NZ09R23VKMs2shoL6i');
```

Despues inserta las tres sucursales, antes de crear los terapeutas que dependen de ellas:

```sql
INSERT INTO sucursales
    (nombre, direccion, colonia, codigo_postal, hora_apertura, hora_cierre)
VALUES
    ('Sucursal Centro', 'Av. Nula 168', 'Sepa', '06000', '09:00', '20:00'),
    ('Sucursal Norte', 'Bv. Indefinida 333', 'Xalla', '07700', '09:00', '20:00'),
    ('Sucursal Sur', 'Calle Desconocida 404', 'Porahi', '03100', '09:00', '20:00');
```

Crea los diez terapeutas. Las subconsultas los relacionan con el usuario y la sucursal por nombre, sin depender de que los IDs generados sean consecutivos:

```sql
INSERT INTO terapeutas(id_usuario, id_sucursal, fecha_contratacion)
VALUES
    ((SELECT id_usuario FROM Usuarios WHERE nombre = 'TerapeutaUno'), (SELECT id_sucursal FROM sucursales WHERE nombre = 'Sucursal Centro'), '2024-01-15'),
    ((SELECT id_usuario FROM Usuarios WHERE nombre = 'TerapeutaDos'), (SELECT id_sucursal FROM sucursales WHERE nombre = 'Sucursal Centro'), '2024-02-01'),
    ((SELECT id_usuario FROM Usuarios WHERE nombre = 'TerapeutaTres'), (SELECT id_sucursal FROM sucursales WHERE nombre = 'Sucursal Centro'), '2024-03-10'),
    ((SELECT id_usuario FROM Usuarios WHERE nombre = 'TerapeutaCuatro'), (SELECT id_sucursal FROM sucursales WHERE nombre = 'Sucursal Norte'), '2024-01-20'),
    ((SELECT id_usuario FROM Usuarios WHERE nombre = 'TerapeutaCinco'), (SELECT id_sucursal FROM sucursales WHERE nombre = 'Sucursal Norte'), '2024-02-12'),
    ((SELECT id_usuario FROM Usuarios WHERE nombre = 'TerapeutaSeis'), (SELECT id_sucursal FROM sucursales WHERE nombre = 'Sucursal Norte'), '2024-03-05'),
    ((SELECT id_usuario FROM Usuarios WHERE nombre = 'TerapeutaSiete'), (SELECT id_sucursal FROM sucursales WHERE nombre = 'Sucursal Sur'), '2024-01-25'),
    ((SELECT id_usuario FROM Usuarios WHERE nombre = 'TerapeutaOcho'), (SELECT id_sucursal FROM sucursales WHERE nombre = 'Sucursal Sur'), '2024-02-18'),
    ((SELECT id_usuario FROM Usuarios WHERE nombre = 'TerapeutaNueve'), (SELECT id_sucursal FROM sucursales WHERE nombre = 'Sucursal Sur'), '2024-03-14'),
    ((SELECT id_usuario FROM Usuarios WHERE nombre = 'TerapeutaDiez'), (SELECT id_sucursal FROM sucursales WHERE nombre = 'Sucursal Sur'), '2024-04-02');
```

Inserta los diez servicios:

```sql
INSERT INTO servicios (nombre, duracion, descripcion, categoria, precio)
VALUES
    ('Masaje relajante', 60, 'Masaje corporal para favorecer la relajación.', 'Masajes', 700.00),
    ('Masaje descontracturante', 60, 'Masaje enfocado en aliviar tensión muscular.', 'Masajes', 850.00),
    ('Masaje con piedras calientes', 75, 'Masaje relajante con aplicación de piedras calientes.', 'Masajes', 950.00),
    ('Aromaterapia', 45, 'Sesión de relajación con aceites esenciales.', 'Bienestar', 600.00),
    ('Facial hidratante', 60, 'Limpieza e hidratación facial.', 'Faciales', 750.00),
    ('Facial revitalizante', 75, 'Tratamiento facial para revitalizar la piel.', 'Faciales', 900.00),
    ('Reflexología', 45, 'Técnica de presión aplicada en los pies.', 'Terapias', 650.00),
    ('Exfoliación corporal', 50, 'Tratamiento exfoliante para renovar la piel.', 'Tratamientos', 800.00),
    ('Envoltura corporal', 60, 'Tratamiento corporal hidratante y relajante.', 'Tratamientos', 950.00),
    ('Masaje deportivo', 60, 'Masaje para recuperación y bienestar muscular.', 'Masajes', 900.00);
```

Crea las salas y relacionalas con sus respectivas sucursales, 4 salas por sucursal en esta prueba:

```sql
INSERT INTO salas(activa, numero_sala, id_sucursal)
VALUES
(1, 'S1', (SELECT id_sucursal FROM sucursales WHERE nombre = 'Sucursal Centro')),
(1, 'S2', (SELECT id_sucursal FROM sucursales WHERE nombre = 'Sucursal Centro')),
(1, 'S3', (SELECT id_sucursal FROM sucursales WHERE nombre = 'Sucursal Centro')),
(1, 'S4', (SELECT id_sucursal FROM sucursales WHERE nombre = 'Sucursal Centro')),
(1, 'S1-P1', (SELECT id_sucursal FROM sucursales WHERE nombre = 'Sucursal Norte')),
(1, 'S2-P1', (SELECT id_sucursal FROM sucursales WHERE nombre = 'Sucursal Norte')),
(1, 'S1-P2', (SELECT id_sucursal FROM sucursales WHERE nombre = 'Sucursal Norte')),
(1, 'S2-P2', (SELECT id_sucursal FROM sucursales WHERE nombre = 'Sucursal Norte')),
(1, 'S1', (SELECT id_sucursal FROM sucursales WHERE nombre = 'Sucursal Sur')),
(1, 'S2', (SELECT id_sucursal FROM sucursales WHERE nombre = 'Sucursal Sur')),
(1, 'S3', (SELECT id_sucursal FROM sucursales WHERE nombre = 'Sucursal Sur')),
(1, 'S4', (SELECT id_sucursal FROM sucursales WHERE nombre = 'Sucursal Sur'));
```

Despues inserta las especialidades de los terapeutas, cada uno tiene dos especialidades:

```sql
INSERT INTO terapeutas_servicios(id_terapeuta, id_servicio)
VALUES
    ((SELECT id_usuario FROM Usuarios WHERE nombre = 'TerapeutaUno'), (SELECT id FROM servicios WHERE nombre = 'Masaje relajante')),
    ((SELECT id_usuario FROM Usuarios WHERE nombre = 'TerapeutaUno'), (SELECT id FROM servicios WHERE nombre = 'Aromaterapia')),
    ((SELECT id_usuario FROM Usuarios WHERE nombre = 'TerapeutaDos'), (SELECT id FROM servicios WHERE nombre = 'Masaje descontracturante')),
    ((SELECT id_usuario FROM Usuarios WHERE nombre = 'TerapeutaDos'), (SELECT id FROM servicios WHERE nombre = 'Masaje deportivo')),
    ((SELECT id_usuario FROM Usuarios WHERE nombre = 'TerapeutaTres'), (SELECT id FROM servicios WHERE nombre = 'Masaje con piedras calientes')),
    ((SELECT id_usuario FROM Usuarios WHERE nombre = 'TerapeutaTres'), (SELECT id FROM servicios WHERE nombre = 'Exfoliación corporal')),
    ((SELECT id_usuario FROM Usuarios WHERE nombre = 'TerapeutaCuatro'), (SELECT id FROM servicios WHERE nombre = 'Facial hidratante')),
    ((SELECT id_usuario FROM Usuarios WHERE nombre = 'TerapeutaCuatro'), (SELECT id FROM servicios WHERE nombre = 'Facial revitalizante')),
    ((SELECT id_usuario FROM Usuarios WHERE nombre = 'TerapeutaCinco'), (SELECT id FROM servicios WHERE nombre = 'Reflexología')),
    ((SELECT id_usuario FROM Usuarios WHERE nombre = 'TerapeutaCinco'), (SELECT id FROM servicios WHERE nombre = 'Envoltura corporal')),
    ((SELECT id_usuario FROM Usuarios WHERE nombre = 'TerapeutaSeis'), (SELECT id FROM servicios WHERE nombre = 'Masaje relajante')),
    ((SELECT id_usuario FROM Usuarios WHERE nombre = 'TerapeutaSeis'), (SELECT id FROM servicios WHERE nombre = 'Masaje deportivo')),
    ((SELECT id_usuario FROM Usuarios WHERE nombre = 'TerapeutaSiete'), (SELECT id FROM servicios WHERE nombre = 'Aromaterapia')),
    ((SELECT id_usuario FROM Usuarios WHERE nombre = 'TerapeutaSiete'), (SELECT id FROM servicios WHERE nombre = 'Reflexología')),
    ((SELECT id_usuario FROM Usuarios WHERE nombre = 'TerapeutaOcho'), (SELECT id FROM servicios WHERE nombre = 'Facial hidratante')),
    ((SELECT id_usuario FROM Usuarios WHERE nombre = 'TerapeutaOcho'), (SELECT id FROM servicios WHERE nombre = 'Exfoliación corporal')),
    ((SELECT id_usuario FROM Usuarios WHERE nombre = 'TerapeutaNueve'), (SELECT id FROM servicios WHERE nombre = 'Masaje descontracturante')),
    ((SELECT id_usuario FROM Usuarios WHERE nombre = 'TerapeutaNueve'), (SELECT id FROM servicios WHERE nombre = 'Envoltura corporal')),
    ((SELECT id_usuario FROM Usuarios WHERE nombre = 'TerapeutaDiez'), (SELECT id FROM servicios WHERE nombre = 'Masaje con piedras calientes')),
    ((SELECT id_usuario FROM Usuarios WHERE nombre = 'TerapeutaDiez'), (SELECT id FROM servicios WHERE nombre = 'Facial revitalizante'));
```

Inserta dos citas pasadas y completadas para cada cliente. Cada cita usa un terapeuta de la sucursal correspondiente que ofrece el servicio y una sala de esa misma sucursal:
```sql
INSERT INTO citas
    (id_usuario, id_servicio, id_terapeuta, id_sala, fecha_hora_inicio, fecha_hora_fin, estado, fecha_creada, precio_total)
VALUES
    ((SELECT id_usuario FROM Usuarios WHERE nombre = 'UsuarioUno'), (SELECT id FROM servicios WHERE nombre = 'Masaje relajante'), (SELECT id_usuario FROM Usuarios WHERE nombre = 'TerapeutaUno'), (SELECT sa.id_sala FROM salas sa JOIN sucursales su ON su.id_sucursal = sa.id_sucursal WHERE su.nombre = 'Sucursal Centro' AND sa.numero_sala = 'S1'), '2025-01-10 10:00:00', '2025-01-10 11:00:00', 'COMPLETADA', '2025-01-01 09:00:00', 700.00),
    ((SELECT id_usuario FROM Usuarios WHERE nombre = 'UsuarioUno'), (SELECT id FROM servicios WHERE nombre = 'Aromaterapia'), (SELECT id_usuario FROM Usuarios WHERE nombre = 'TerapeutaUno'), (SELECT sa.id_sala FROM salas sa JOIN sucursales su ON su.id_sucursal = sa.id_sucursal WHERE su.nombre = 'Sucursal Centro' AND sa.numero_sala = 'S2'), '2025-01-20 12:00:00', '2025-01-20 12:45:00', 'COMPLETADA', '2025-01-02 09:00:00', 600.00),
    ((SELECT id_usuario FROM Usuarios WHERE nombre = 'UsuarioDos'), (SELECT id FROM servicios WHERE nombre = 'Masaje descontracturante'), (SELECT id_usuario FROM Usuarios WHERE nombre = 'TerapeutaDos'), (SELECT sa.id_sala FROM salas sa JOIN sucursales su ON su.id_sucursal = sa.id_sucursal WHERE su.nombre = 'Sucursal Centro' AND sa.numero_sala = 'S3'), '2025-02-10 10:00:00', '2025-02-10 11:00:00', 'COMPLETADA', '2025-02-01 09:00:00', 850.00),
    ((SELECT id_usuario FROM Usuarios WHERE nombre = 'UsuarioDos'), (SELECT id FROM servicios WHERE nombre = 'Masaje deportivo'), (SELECT id_usuario FROM Usuarios WHERE nombre = 'TerapeutaDos'), (SELECT sa.id_sala FROM salas sa JOIN sucursales su ON su.id_sucursal = sa.id_sucursal WHERE su.nombre = 'Sucursal Centro' AND sa.numero_sala = 'S4'), '2025-02-20 12:00:00', '2025-02-20 13:00:00', 'COMPLETADA', '2025-02-02 09:00:00', 900.00),
    ((SELECT id_usuario FROM Usuarios WHERE nombre = 'UsuarioTres'), (SELECT id FROM servicios WHERE nombre = 'Masaje con piedras calientes'), (SELECT id_usuario FROM Usuarios WHERE nombre = 'TerapeutaTres'), (SELECT sa.id_sala FROM salas sa JOIN sucursales su ON su.id_sucursal = sa.id_sucursal WHERE su.nombre = 'Sucursal Centro' AND sa.numero_sala = 'S1'), '2025-03-10 10:00:00', '2025-03-10 11:15:00', 'COMPLETADA', '2025-03-01 09:00:00', 950.00),
    ((SELECT id_usuario FROM Usuarios WHERE nombre = 'UsuarioTres'), (SELECT id FROM servicios WHERE nombre = 'Exfoliación corporal'), (SELECT id_usuario FROM Usuarios WHERE nombre = 'TerapeutaTres'), (SELECT sa.id_sala FROM salas sa JOIN sucursales su ON su.id_sucursal = sa.id_sucursal WHERE su.nombre = 'Sucursal Centro' AND sa.numero_sala = 'S2'), '2025-03-20 12:00:00', '2025-03-20 12:50:00', 'COMPLETADA', '2025-03-02 09:00:00', 800.00),
    ((SELECT id_usuario FROM Usuarios WHERE nombre = 'UsuarioCuatro'), (SELECT id FROM servicios WHERE nombre = 'Facial hidratante'), (SELECT id_usuario FROM Usuarios WHERE nombre = 'TerapeutaCuatro'), (SELECT sa.id_sala FROM salas sa JOIN sucursales su ON su.id_sucursal = sa.id_sucursal WHERE su.nombre = 'Sucursal Norte' AND sa.numero_sala = 'S1-P1'), '2025-04-10 10:00:00', '2025-04-10 11:00:00', 'COMPLETADA', '2025-04-01 09:00:00', 750.00),
    ((SELECT id_usuario FROM Usuarios WHERE nombre = 'UsuarioCuatro'), (SELECT id FROM servicios WHERE nombre = 'Facial revitalizante'), (SELECT id_usuario FROM Usuarios WHERE nombre = 'TerapeutaCuatro'), (SELECT sa.id_sala FROM salas sa JOIN sucursales su ON su.id_sucursal = sa.id_sucursal WHERE su.nombre = 'Sucursal Norte' AND sa.numero_sala = 'S2-P1'), '2025-04-20 12:00:00', '2025-04-20 13:15:00', 'COMPLETADA', '2025-04-02 09:00:00', 900.00),
    ((SELECT id_usuario FROM Usuarios WHERE nombre = 'UsuarioCinco'), (SELECT id FROM servicios WHERE nombre = 'Reflexología'), (SELECT id_usuario FROM Usuarios WHERE nombre = 'TerapeutaCinco'), (SELECT sa.id_sala FROM salas sa JOIN sucursales su ON su.id_sucursal = sa.id_sucursal WHERE su.nombre = 'Sucursal Norte' AND sa.numero_sala = 'S1-P2'), '2025-05-10 10:00:00', '2025-05-10 10:45:00', 'COMPLETADA', '2025-05-01 09:00:00', 650.00),
    ((SELECT id_usuario FROM Usuarios WHERE nombre = 'UsuarioCinco'), (SELECT id FROM servicios WHERE nombre = 'Envoltura corporal'), (SELECT id_usuario FROM Usuarios WHERE nombre = 'TerapeutaCinco'), (SELECT sa.id_sala FROM salas sa JOIN sucursales su ON su.id_sucursal = sa.id_sucursal WHERE su.nombre = 'Sucursal Norte' AND sa.numero_sala = 'S2-P2'), '2025-05-20 12:00:00', '2025-05-20 13:00:00', 'COMPLETADA', '2025-05-02 09:00:00', 950.00);
```

Inserta los métodos de pago antes de sus pagos:
```sql
INSERT INTO metodos_pago(nombre)
VALUES ('Efectivo'), ('Tarjeta'), ('Transferencia');
```

Inserta un pago realizado por cada cita; como cada cliente tiene dos citas, quedan dos pagos pagados por cliente:
```sql
INSERT INTO pagos(estado_pago, fecha_pago, monto, id_cita, id_metodo_pago)
VALUES
    ('PAGADO', '2025-01-10 11:05:00', 700.00, (SELECT id_cita FROM citas WHERE id_usuario = (SELECT id_usuario FROM Usuarios WHERE nombre = 'UsuarioUno') AND fecha_hora_inicio = '2025-01-10 10:00:00'), (SELECT id_metodo_pago FROM metodos_pago WHERE nombre = 'Efectivo')),
    ('PAGADO', '2025-01-20 12:50:00', 600.00, (SELECT id_cita FROM citas WHERE id_usuario = (SELECT id_usuario FROM Usuarios WHERE nombre = 'UsuarioUno') AND fecha_hora_inicio = '2025-01-20 12:00:00'), (SELECT id_metodo_pago FROM metodos_pago WHERE nombre = 'Tarjeta')),
    ('PAGADO', '2025-02-10 11:05:00', 850.00, (SELECT id_cita FROM citas WHERE id_usuario = (SELECT id_usuario FROM Usuarios WHERE nombre = 'UsuarioDos') AND fecha_hora_inicio = '2025-02-10 10:00:00'), (SELECT id_metodo_pago FROM metodos_pago WHERE nombre = 'Transferencia')),
    ('PAGADO', '2025-02-20 13:05:00', 900.00, (SELECT id_cita FROM citas WHERE id_usuario = (SELECT id_usuario FROM Usuarios WHERE nombre = 'UsuarioDos') AND fecha_hora_inicio = '2025-02-20 12:00:00'), (SELECT id_metodo_pago FROM metodos_pago WHERE nombre = 'Efectivo')),
    ('PAGADO', '2025-03-10 11:20:00', 950.00, (SELECT id_cita FROM citas WHERE id_usuario = (SELECT id_usuario FROM Usuarios WHERE nombre = 'UsuarioTres') AND fecha_hora_inicio = '2025-03-10 10:00:00'), (SELECT id_metodo_pago FROM metodos_pago WHERE nombre = 'Tarjeta')),
    ('PAGADO', '2025-03-20 12:55:00', 800.00, (SELECT id_cita FROM citas WHERE id_usuario = (SELECT id_usuario FROM Usuarios WHERE nombre = 'UsuarioTres') AND fecha_hora_inicio = '2025-03-20 12:00:00'), (SELECT id_metodo_pago FROM metodos_pago WHERE nombre = 'Transferencia')),
    ('PAGADO', '2025-04-10 11:05:00', 750.00, (SELECT id_cita FROM citas WHERE id_usuario = (SELECT id_usuario FROM Usuarios WHERE nombre = 'UsuarioCuatro') AND fecha_hora_inicio = '2025-04-10 10:00:00'), (SELECT id_metodo_pago FROM metodos_pago WHERE nombre = 'Efectivo')),
    ('PAGADO', '2025-04-20 13:20:00', 900.00, (SELECT id_cita FROM citas WHERE id_usuario = (SELECT id_usuario FROM Usuarios WHERE nombre = 'UsuarioCuatro') AND fecha_hora_inicio = '2025-04-20 12:00:00'), (SELECT id_metodo_pago FROM metodos_pago WHERE nombre = 'Tarjeta')),
    ('PAGADO', '2025-05-10 10:50:00', 650.00, (SELECT id_cita FROM citas WHERE id_usuario = (SELECT id_usuario FROM Usuarios WHERE nombre = 'UsuarioCinco') AND fecha_hora_inicio = '2025-05-10 10:00:00'), (SELECT id_metodo_pago FROM metodos_pago WHERE nombre = 'Transferencia')),
    ('PAGADO', '2025-05-20 13:05:00', 950.00, (SELECT id_cita FROM citas WHERE id_usuario = (SELECT id_usuario FROM Usuarios WHERE nombre = 'UsuarioCinco') AND fecha_hora_inicio = '2025-05-20 12:00:00'), (SELECT id_metodo_pago FROM metodos_pago WHERE nombre = 'Efectivo'));
```

# API REST

Realiza pruebas desde postman hacia la api

1. Corre ApiRestSpaApplication
2. En Postman, selecciona **Import** y elige ese archivo.
3. Revisa la variable `baseUrl` (por defecto `http://localhost:8080`).
4. Ejecuta primero **Auth > Login**. El script de pruebas guarda el token automáticamente en la variable `token`.
5. Ejecuta el resto de peticiones; las protegidas ya envían `Authorization: Bearer {{token}}`.

## Notas
- Los IDs de ejemplo (`idServicio=1`, `idTerapeuta=6`, `idSala=1`, `idMetodoPago=1`) corresponden a los datos de prueba de este README. Verifica los IDs reales con los endpoints de catálogo.
- Las respuestas utilizan DTOs para evitar exponer hashes de contraseñas ni entidades JPA directamente.
- `POST /api/citas` valida que el servicio, el terapeuta y la sala existan, pero no valida que el terapeuta ofrezca ese servicio ni que pertenezca a la misma sucursal que la sala.