@echo off
set USUARIO=root
set CONTRASENA=tuContraseña

REM Archivos SQL a ejecutar
set ARCHIVO1=crearDatabase.sql
set ARCHIVO2=cargarDatabase.sql

REM Ejecutar los scripts
echo Ejecutando %ARCHIVO1%...
mysql -u %USUARIO% -p%CONTRASENA% < %ARCHIVO1%

echo Ejecutando %ARCHIVO2%...
mysql -u %USUARIO% -p%CONTRASENA% < %ARCHIVO2%

echo Todos los scripts fueron ejecutados correctamente.
pause
