@echo off
title Eureka Discovery Server (Port 8761)
color 0A

echo =====================================================================
echo           DEMARRAGE DE EUREKA DISCOVERY SERVER (PORT 8761)
echo =====================================================================
echo.

set "JAVA_BIN="

REM 1. Verifier si JAVA_HOME existe
if defined JAVA_HOME (
    if exist "%JAVA_HOME%\bin\java.exe" set "JAVA_BIN=%JAVA_HOME%\bin\java.exe"
)

REM 2. Verifier dans le repertoire IntelliJ JDKs
if not defined JAVA_BIN (
    if exist "C:\Users\manel\.jdks\ms-17.0.17\bin\java.exe" (
        set "JAVA_BIN=C:\Users\manel\.jdks\ms-17.0.17\bin\java.exe"
    ) else if exist "C:\Users\manel\.jdks\jbr-17.0.14\bin\java.exe" (
        set "JAVA_BIN=C:\Users\manel\.jdks\jbr-17.0.14\bin\java.exe"
    ) else if exist "C:\Users\manel\.jdks\ms-17.0.20.1\bin\java.exe" (
        set "JAVA_BIN=C:\Users\manel\.jdks\ms-17.0.20.1\bin\java.exe"
    )
)

REM 3. Fallback sur la commande standard
if not defined JAVA_BIN (
    set "JAVA_BIN=java"
)

set "JAR_PATH=%~dp0codeSource_correctionProsit\backend\backend\eureka-server\target\eureka-server-0.0.1-SNAPSHOT.jar"

if not exist "%JAR_PATH%" (
    echo [ERREUR] Le fichier JAR introuvable: %JAR_PATH%
    pause
    exit /b 1
)

echo Java utilise : %JAVA_BIN%
echo.
echo URL du Dashboard Eureka : http://localhost:8761/
echo URL de registration     : http://localhost:8761/eureka
echo.
echo Appuyez sur Ctrl+C dans cette fenetre pour arreter Eureka.
echo =====================================================================
echo.

"%JAVA_BIN%" -jar "%JAR_PATH%"

pause
