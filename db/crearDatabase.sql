-- Crear la base de datos
DROP DATABASE IF EXISTS Donaciones;
CREATE DATABASE Donaciones;
USE Donaciones;

-- Tabla Persona (necesaria para tener DNI unico)
DROP TABLE IF EXISTS Padrino;
CREATE TABLE Padrino (
    dni CHAR(8) PRIMARY KEY NOT NULL,
    nombre VARCHAR(50) NOT NULL,
    apellido VARCHAR(50) NOT NULL,
    direccion VARCHAR(200) NOT NULL,
    codigoPostal VARCHAR(10) NOT NULL,
    email VARCHAR(100) NOT NULL,
    facebook VARCHAR(100) NOT NULL,
    telFijo VARCHAR(20) NOT NULL,
    telCelular VARCHAR(20) NOT NULL,
    fechaNacimiento DATE NOT NULL
);

-- Tabla Donante
DROP TABLE IF EXISTS Donante;
CREATE TABLE Donante (
	dni CHAR(8) PRIMARY KEY,
    cuit VARCHAR(13) UNIQUE NOT NULL,
    ocupacion VARCHAR(30) NOT NULL,
	CONSTRAINT fk_donante_padrino FOREIGN KEY (dni) REFERENCES Padrino(dni) ON DELETE CASCADE ON UPDATE CASCADE
);

-- Tabla Contacto
DROP TABLE IF EXISTS Contacto;
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
DROP TABLE IF EXISTS Programa;
CREATE TABLE Programa (
    nombre VARCHAR(100) PRIMARY KEY,
    descripcion TEXT NOT NULL
);

-- Tabla MedioDePago
DROP TABLE IF EXISTS MedioDePago;
CREATE TABLE MedioDePago (
    id INTEGER AUTO_INCREMENT PRIMARY KEY,
    nombreTitular VARCHAR(50) NOT NULL,
    apellidoTitular VARCHAR(50) NOT NULL
);

-- Tabla TarjetaDeCredito
DROP TABLE IF EXISTS TarjetaDeCredito;
CREATE TABLE TarjetaDeCredito (
    id INTEGER PRIMARY KEY,
    numero VARCHAR(20) NOT NULL UNIQUE,
    nombreTarjeta VARCHAR(100) NOT NULL,
    fechaVencimiento DATE NOT NULL,
    CONSTRAINT fk_tc_mediodepago FOREIGN KEY (id) REFERENCES MedioDePago(id)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- Tabla DebitoTransferencia
DROP TABLE IF EXISTS DebitoTransferencia;
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
DROP TABLE IF EXISTS Aporta;
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

DROP TABLE IF EXISTS AuditoriaEliminacionDonante;
CREATE TABLE AuditoriaEliminacionDonante (
	id INTEGER AUTO_INCREMENT PRIMARY KEY,
    dni CHAR(8),
    nombre VARCHAR(50),
    apellido VARCHAR(50),
    fechaEliminacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    usuarioDB VARCHAR(100)
);

DROP TRIGGER IF EXISTS trigger_eliminacion_donante;
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

DROP TRIGGER IF EXISTS trigger_eliminacion_contacto;
DELIMITER //
CREATE TRIGGER trigger_eliminacion_contacto
AFTER DELETE ON Contacto
FOR EACH ROW
BEGIN
	DELETE FROM Padrino WHERE dni=OLD.dni;
END;
//