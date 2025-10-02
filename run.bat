@echo off
echo  Ejecutando Dark Kitchen...

REM Verificar si las clases están compiladas
if not exist classes (
    echo  Las clases no están compiladas. Ejecuta compile.bat primero.
    pause
    exit /b 1
)

REM Verificar si existe el conector MySQL
if not exist lib\mysql-connector*.jar (
    echo  Conector MySQL no encontrado en lib/
    echo Descarga mysql-connector-java.jar y colócalo en la carpeta lib/
    pause
    exit /b 1
)

REM Ejecutar la aplicación
java -cp "classes;lib/*" com.darkkitchen.Main

pause