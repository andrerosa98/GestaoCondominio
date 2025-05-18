@echo off
setlocal enabledelayedexpansion

:: Diretórios
set OUT_DIR=out
set SRC_DIR=src
set LIB_DIR=lib

:: Limpa compilações anteriores
if exist "%OUT_DIR%" rd /s /q "%OUT_DIR%"
mkdir "%OUT_DIR%"

:: Cria uma lista de arquivos .java
set JAVA_FILES=
for /R "%SRC_DIR%" %%f in (*.java) do (
    set "JAVA_FILES=!JAVA_FILES! %%f"
)

:: Compila os arquivos
echo Compilando arquivos...
javac -d "%OUT_DIR%" -cp "%LIB_DIR%\*" !JAVA_FILES!

:: Verifica se a compilação foi bem-sucedida
if not exist "%OUT_DIR%\com\gestaoCondominio\controller\Main.class" (
    echo Houve um problema na compilação. Verifique os erros acima.
    pause
    exit /b 1
)

:: Executa a aplicação
echo Executando aplicação...
java -cp "%OUT_DIR%;%LIB_DIR%\*" com.gestaoCondominio.controller.Main

pause
