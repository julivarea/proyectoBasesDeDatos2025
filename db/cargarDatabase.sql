USE Donaciones;

-- Insertar Padrinos (personas)
INSERT INTO Padrino (dni, nombre, apellido, direccion, codigoPostal, email, facebook, telFijo, telCelular, fechaNacimiento) VALUES
('12345678', 'Pepe', 'McPepe', 'Calle 123', '1000', 'pepemcpepe@gmail.com', 'pepito', '1234567', '987654321', '1980-01-01'),
('87654321', 'Fulano', 'Fulanez', 'Calle 456', '2000', 'fulano@gmail.com', 'fulanitofeisbuc', '7654321', '123456789', '1990-03-31'),
('11112222', 'Mengano', 'Menganez', 'Calle Luna', '3000', 'mengano@gmail.com', 'menganito', '1112223', '999888777', '1975-02-01'),
('33334444', 'Zutano', 'Zutanez', 'Calle Sol', '4000', 'zutano@yahoo.com.ar', 'zutanoFB', '4445556', '666777888', '1985-02-28'),
('55556666', 'Pedro', 'McPedro', 'Avenida Rivadavia', '5000', 'peter@hotmail.com', 'pedrofb', '7778889', '999111222', '2000-01-01'),
('22223333', 'Laura', 'González', 'Mitre 789', '1405', 'laura@gmail.com', 'laugo', '43434343', '1541234567', '1988-07-12'),
('44445555', 'Carlos', 'Ramírez', 'San Martín 111', '1704', 'carlosr@yahoo.com', 'carlitofb', '43123456', '1567894321', '1979-09-10'),
('66667777', 'Lucía', 'Martínez', 'Belgrano 222', '1500', 'lucia@outlook.com', 'lulumtz', '42678900', '1587654321', '1992-12-25'),
('77778888', 'Martín', 'Suárez', 'Independencia 333', '1600', 'msuarez@gmail.com', 'martinfb', '41111111', '1599998888', '1982-03-05'),
('88889999', 'Ana', 'Pérez', 'Av. Santa Fe 999', '1200', 'anap@gmail.com', 'anitap', '43211234', '1566667777', '1995-08-19'),
('99990000', 'Diego', 'López', 'Dorrego 123', '1300', 'dlopez@gmail.com', 'diegofb', '42223334', '1577778888', '1980-06-30');

-- Donantes
INSERT INTO Donante (dni, cuit, ocupacion) VALUES
('12345678', '20-12345678-9', 'Empleado'),
('87654321', '23-87654321-1', 'Desempleado'),
('22223333', '20-22223333-1', 'Autónomo'),
('44445555', '23-44445555-9', 'Profesional'),
('66667777', '27-66667777-3', 'Jubilado'),
('88889999', '23-88889999-2', 'Estudiante'),
('99990000', '20-99990000-7', 'Empleado');

-- Contactos
INSERT INTO Contacto (dni, fechaPrimerContacto, fechaAlta, fechaBaja, fechaRechazoAdhesion, estado) VALUES
('11112222', '2025-05-30', '2025-05-31', '2025-06-01', '2025-06-02', 'ERROR'),
('33334444', '2025-01-01', '2025-01-02', '2025-01-03', '2025-01-04', 'Voluntario'),
('55556666', '2025-03-28', '2025-03-30', '2025-03-31', '2025-04-01', 'Amigo'),
('77778888', '2025-06-01', '2025-06-02', '2025-06-03', '2025-06-04', 'Baja');

-- Programas
INSERT INTO Programa (nombre, descripcion) VALUES
('Alimentación', 'Programa de alimentación'),
('Educación', 'Apoyo escolar y becas'),
('Salud', 'Atención médica');

-- Medios de pago
INSERT INTO MedioDePago (nombreTitular, apellidoTitular) VALUES
('Pepe', 'McPepe'),
('Fulano', 'Fulanez'),
('Laura', 'González'),
('Carlos', 'Ramírez'),
('Lucía', 'Martínez'),
('Ana', 'Pérez'),
('Diego', 'López');

-- Tarjetas de crédito
INSERT INTO TarjetaDeCredito (id, numero, nombreTarjeta, fechaVencimiento) VALUES
(1, '4111111111111111', 'Visa', '2025-12-31'),
(3, '5555444433332222', 'Mastercard', '2026-05-31'),
(5, '4111222233334444', 'Visa', '2027-01-15'),
(7, '4000123412341234', 'Amex', '2026-10-01');

-- Débito/transferencias
INSERT INTO DebitoTransferencia (id, CBU, numeroCuenta, nombreBanco, sucursalBanco, tipoCuenta) VALUES
(2, '1234567890123456789012', '1234567890', 'Banco Nación', 'Sucursal Rio Cuarto', 'Caja de Ahorro'),
(4, '2345678901234567890123', '9876543210', 'Banco Galicia', 'Sucursal Belgrano', 'Cuenta Corriente'),
(6, '3456789012345678901234', '1231231234', 'Banco Provincia', 'Sucursal La Plata', 'Caja de Ahorro');

-- Aportes
INSERT INTO Aporta (dni, nombrePrograma, monto, frecuencia, idMP) VALUES
('12345678', 'Alimentación', 1000.00, 'Mensual', 1),
('12345678', 'Educación', 1300.00, 'Mensual', 1),
('12345678', 'Salud', 1400.00, 'Semestral', 1),
('87654321', 'Educación', 1500.00, 'Semestral', 2),
('22223333', 'Salud', 2000.00, 'Mensual', 3),
('44445555', 'Alimentación', 1200.00, 'Semestral', 4),
('66667777', 'Educación', 900.00, 'Mensual', 5),
('88889999', 'Alimentación', 700.00, 'Mensual', 6),
('99990000', 'Salud', 1800.00, 'Mensual', 7),
('99990000', 'Educación', 1100.00, 'Semestral', 7),
('99990000', 'Alimentación', 950.00, 'Mensual', 7);