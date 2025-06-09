USE Donaciones;

-- Insertar Padrinos (personas)
INSERT INTO Padrino (dni, nombre, apellido, direccion, codigoPostal, email, facebook, telFijo, telCelular, fechaNacimiento) VALUES
('12345678', 'Pepe', 'McPepe', 'Calle 123', '1000', 'pepemcpepe@gmail.com', 'pepito', '1234567', '987654321', '1980-01-01'),
('87654321', 'Fulano', 'Fulanez', 'Calle 456', '2000', 'fulano@gmail.com', 'fulanitofeisbuc', '7654321', '123456789', '1990-03-31'),
('11112222', 'Mengano', 'Menganez', 'Calle Luna', '3000', 'mengano@gmail.com', 'menganito', '1112223', '999888777', '1975-02-01'),
('33334444', 'Zutano', 'Zutanez', 'Calle Sol', '4000', 'zutano@yahoo.com.ar', 'zutanoFB', '4445556', '666777888', '1985-02-28'),
('55556666', 'Pedro', 'McPedro', 'Avenida Rivadavia', '5000', 'peter@hotmail.com', 'pedrofb', '7778889', '999111222', '2000-01-01');

-- Insertar Donantes
INSERT INTO Donante (dni, cuit, ocupacion) VALUES
('12345678', '20-12345678-9', 'Empleado'),
('87654321', '23-87654321-1', 'Desempleado');

-- Insertar Contactos
INSERT INTO Contacto (dni, fechaPrimerContacto, fechaAlta, fechaBaja, fechaRechazoAdhesion, estado) VALUES
('11112222', '2025-05-30', '2024-01-05', NULL, NULL, 'En gestión'),
('33334444', '2025-01-01', '2024-02-05', NULL, NULL, 'Voluntario'),
('55556666', '2025-03-28', '2025-03-30', NULL, NULL, 'Amigo');

-- Insertar Programas
INSERT INTO Programa (nombre, descripcion) VALUES
('Alimentación', 'Programa de alimentación'),
('Educación', 'Apoyo escolar y becas'),
('Salud', 'Atención médica');

-- Insertar Medios de Pago
INSERT INTO MedioDePago (nombreTitular) VALUES
('Pepe McPepe'),
('Fulano Fulanez');

-- Insertar TarjetaDeCredito
INSERT INTO TarjetaDeCredito (id, numero, nombreTarjeta, fechaVencimiento) VALUES
(1, '4111111111111111', 'Visa', '2025-12-31');

-- Insertar DebitoTransferencia
INSERT INTO DebitoTransferencia (id, CBU, numeroCuenta, nombreBanco, sucursalBanco, tipoCuenta) VALUES
(2, '1234567890123456789012', '1234567890', 'Banco Nación', 'Sucursal Rio Cuarto', 'Caja de Ahorro');

-- Insertar Aportes
INSERT INTO Aporta (dni, nombrePrograma, monto, frecuencia, idMP) VALUES
('12345678', 'Alimentación', 1000.00, 'Mensual', 1),
('87654321', 'Educación', 1500.00, 'Semestral', 2);