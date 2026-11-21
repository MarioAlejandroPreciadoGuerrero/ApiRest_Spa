# ApiRest_Spa

## Insertar usuarios de prueba

`UserRequest` recibe `username`, `email` y `password`. El servicio guarda `username` como `nombre` y cifra la contraseña con BCrypt; por eso, al insertar directamente en la base de datos, la columna `password` debe contener un hash BCrypt, no la contraseña en texto plano.

El siguiente SQL agrega cinco usuarios a `Usuarios`. Las contraseñas para iniciar sesión son `SpaUser@2026` hasta `SpaUser@2030`, en el mismo orden:

```sql
INSERT INTO Usuarios (fecha_registro, nombre, email, password)
VALUES
    (NOW(), 'UsuarioUno', 'usuario1@example.com', '$2a$10$XqReKhng9fF928s9yWMqseEkbG5V6qUlY7/NZ09R23VKMs2shoL6i'),
    (NOW(), 'UsuarioDos', 'usuario2@example.com', '$2a$10$mlQplF4vF5qsLzchWmlLG.kVgXFjVqrecidK.IZmD3PCvYfUkK2tG'),
    (NOW(), 'UsuarioTres', 'usuario3@example.com', '$2a$10$R/ZkcB2fLh6JOEZ7DPWp9OoezBxYc1WxoA8muxE4nxLCnKvG0y.Ay'),
    (NOW(), 'UsuarioCuatro', 'usuario4@example.com', '$2a$10$Yfs5zLrzdMQhfq1lh1jmFOOoIUXaMMbNQ6McM8FMAiDRHf4sCKEq.'),
    (NOW(), 'UsuarioCinco', 'usuario5@example.com', '$2a$10$4NQij0Yh3HNnwKtCajFrpOeyj4jOF.hsTEZKz0fw/v9Bd6DM7mHV6');
```
## Insertar sucursales, terapeutas y servicios

Cada terapeuta comparte su identificador con un registro de `Usuarios` (`@MapsId` en la entidad `Terapeuta`). Por eso, primero inserta diez usuarios que servirán como cuentas de los terapeutas. En estos datos de prueba, todos usan la contraseña `SpaUser@2026`.

```sql
INSERT INTO usuarios (fecha_registro, nombre, email, password)
VALUES
    (NOW(), 'TerapeutaUno', 'terapeuta1@example.com', '4321_dcbA'),
    (NOW(), 'TerapeutaDos', 'terapeuta2@example.com', '4321_dcbA'),
    (NOW(), 'TerapeutaTres', 'terapeuta3@example.com', '4321_dcbA'),
    (NOW(), 'TerapeutaCuatro', 'terapeuta4@example.com', '4321_dcbA'),
    (NOW(), 'TerapeutaCinco', 'terapeuta5@example.com', '4321_dcbA'),
    (NOW(), 'TerapeutaSeis', 'terapeuta6@example.com', '4321_dcbA'),
    (NOW(), 'TerapeutaSiete', 'terapeuta7@example.com', '4321_dcbA'),
    (NOW(), 'TerapeutaOcho', 'terapeuta8@example.com', '4321_dcbA'),
    (NOW(), 'TerapeutaNueve', 'terapeuta9@example.com', '4321_dcbA'),
    (NOW(), 'TerapeutaDiez', 'terapeuta10@example.com', '4321_dcbA');
```

Despues inserta las tres sucursales:

```sql
INSERT INTO sucursales
    (nombre, direccion, colonia, codigo_postal, hora_apertura, hora_cierre)
VALUES
    ('Sucursal Centro', 'Av. Nula 168', 'Sepa', '06000', '09:00', '20:00'),
    ('Sucursal Norte', 'Bv. Indefinida 333', 'Xalla', '07700', '09:00', '20:00'),
    ('Sucursal Sur', 'Calle Desconocida 404', 'Porahi', '03100', '09:00', '20:00');
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

Crea los diez terapeutas. Las subconsultas los relacionan con el usuario y la sucursal por nombre, sin depender de que los IDs generados sean consecutivos:

```sql
INSERT INTO terapeutas (id_usuario, id_sucursal, fecha_contratacion)
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

Crea las salas y relacionalas con sus respectivas sucursales, 4 salas por sucursal en esta prueba:

```sql
INSERT INTO salas(activa, numero_sala, id_sucursal)
VALUES
(1, 'S1', 1),
(1, 'S2', 1),
(1, 'S3', 1),
(1, 'S4', 1),
(1, 'S1-P1', 2),
(1, 'S2-P1', 2),
(1, 'S1-P2', 2),
(1, 'S2-P2', 2),
(1, 'S1', 3),
(1, 'S2', 3),
(1, 'S3', 3),
(1, 'S4', 3);
```