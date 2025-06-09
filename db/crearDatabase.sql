-- Crear la base de datos
CREATE DATABASE IF NOT EXISTS Donaciones;
USE Donaciones;

-- Tabla Persona (necesaria para tener DNI unico)
CREATE TABLE Padrino (
    dni CHAR(8) PRIMARY KEY NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    direccion VARCHAR(200),
    codigoPostal VARCHAR(10),
    email VARCHAR(100),
    facebook VARCHAR(100),
    telFijo VARCHAR(20),
    telCelular VARCHAR(20),
    fechaNacimiento DATE
);

-- Tabla Donante
CREATE TABLE Donante (
	dni CHAR(8) PRIMARY KEY,
    cuit VARCHAR(13) UNIQUE NOT NULL,
    ocupacion VARCHAR(30) NOT NULL,
	CONSTRAINT fk_donante_padrino FOREIGN KEY (dni) REFERENCES Padrino(dni) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Tabla Contacto
CREATE TABLE Contacto (
    dni CHAR(8) PRIMARY KEY,
    fechaPrimerContacto DATE NOT NULL,
    fechaAlta DATE,
    fechaBaja DATE,
    fechaRechazoAdhesion DATE,
    estado ENUM('Sin llamar', 'ERROR', 'En gestión', 'Adherido', 'Amigo', 'No acepta', 'Baja', 'Voluntario'),
    CONSTRAINT fk_contacto_padrino FOREIGN KEY (dni) REFERENCES Padrino(dni) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Tabla Programa
CREATE TABLE Programa (
    nombre VARCHAR(100) PRIMARY KEY,
    descripcion TEXT NOT NULL
);

-- Tabla MedioDePago
CREATE TABLE MedioDePago (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    nombreTitular VARCHAR(100) NOT NULL
);

-- Tabla TarjetaDeCredito
CREATE TABLE TarjetaDeCredito (
    id INTEGER PRIMARY KEY,
    numero VARCHAR(20) NOT NULL UNIQUE,
    nombreTarjeta VARCHAR(100) NOT NULL,
    fechaVencimiento DATE NOT NULL,
    CONSTRAINT fk_tc_mediodepago FOREIGN KEY (id) REFERENCES MedioDePago(id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- Tabla DebitoTransferencia
CREATE TABLE DebitoTransferencia (
    id INTEGER PRIMARY KEY,
    CBU CHAR(22) UNIQUE,
    numeroCuenta VARCHAR(20) NOT NULL,
    nombreBanco VARCHAR(100) NOT NULL,
    sucursalBanco VARCHAR(100) NOT NULL,
    tipoCuenta VARCHAR(50) NOT NULL,
    CONSTRAINT fk_dt_mediodepago FOREIGN KEY (id) REFERENCES MedioDePago(id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- Tabla Aporta
CREATE TABLE Aporta (
    dni CHAR(8),
    nombrePrograma VARCHAR(100),
    monto DECIMAL(10,2) NOT NULL CHECK (monto > 0),
    frecuencia ENUM('Mensual','Semestral') NOT NULL,
    idMP INTEGER,
    CONSTRAINT pk_aporta PRIMARY KEY (dni, nombrePrograma),
    CONSTRAINT fk_aporta_donante FOREIGN KEY (dni) REFERENCES Donante(dni)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_aporta_programa FOREIGN KEY (nombrePrograma) REFERENCES Programa(nombre)
        ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT fk_aporta_mediodepago FOREIGN KEY (idMP) REFERENCES MedioDePago(id)
        ON DELETE SET NULL ON UPDATE CASCADE
);

CREATE TABLE AuditoriaEliminacionDonante (
	id INTEGER AUTO_INCREMENT PRIMARY KEY,
    dni CHAR(8),
    nombre VARCHAR(50),
    apellido VARCHAR(50),
    fechaEliminacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    usuarioDB VARCHAR(100)
);

DELIMITER //
CREATE TRIGGER trigger_eliminacion_donante
AFTER DELETE ON Donante
FOR EACH ROW
BEGIN
	DECLARE nombre_donante VARCHAR(50);
    DECLARE apellido_donante VARCHAR(50);

    SELECT nombre, apellido INTO nombre_donante, apellido_donante FROM Padrino WHERE dni=OLD.dni;
    INSERT INTO AuditoriaEliminacionDonante (dni, nombre, apellido, fechaEliminacion, usuarioDB)
    VALUES (
        OLD.dni,
        nombre_donante,
        apellido_donante,
        NOW(),
        USER()
    );
    DELETE FROM Padrino WHERE dni=OLD.dni;
END;
//
DELIMITER ;

DELIMITER //
CREATE TRIGGER trigger_eliminacion_contacto
AFTER DELETE ON Contacto
FOR EACH ROW
BEGIN
	DELETE FROM Padrino WHERE dni=OLD.dni;
END;
//