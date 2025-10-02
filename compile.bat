@echo off
echo  Compilación Simple (sin MySQL)...

REM Crear directorio de clases si no existe
if not exist classes mkdir classes

echo Compilando sin dependencias externas...

REM Compilar solo con classpath básico
javac -d classes src/com/darkkitchen/Main.java src/com/darkkitchen/dao/*.java src/com/darkkitchen/model/*.java src/com/darkkitchen/ui/*.java

if %ERRORLEVEL% EQU 0 (
    echo  Compilación básica exitosa
    echo.
    echo   NOTA: Para ejecutar necesitarás:
    echo    1. MySQL ejecutándose
    echo    2. mysql-connector-java.jar en lib/
    echo    3. Base de datos dark_kitchen creada
    echo.
    echo  Si tienes todo configurado, ejecuta: run.bat
) else (
    echo  Error en la compilación básica
    echo.
    echo  Posibles problemas:
    echo    - Verifica que Java JDK esté instalado
    echo    - Revisa errores de sintaxis en el código
)

pause