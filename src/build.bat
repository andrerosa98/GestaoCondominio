@echo off

:: Configura o terminal para usar UTF-8
chcp 65001 > nul

:: Move para o diretório onde o .bat está localizado
cd /d "%~dp0"

:: 1. Cria o diretório de saída
if not exist "out" (
    mkdir "out"
)

:: 2. Compila o código-fonte, incluindo o driver MySQL
echo Compilando o código-fonte...
javac -d out -cp "lib/*" src/com/gestaoCondominio/controller/Main.java

if %errorlevel% neq 0 (
    echo Erro na compilação. Verifique os arquivos de código.
    pause
    exit /b
)

:: 3. Executa a classe principal com MySQL no classpath
java -cp "out;lib/*" com.gestaoCondominio.controller.Main

:: 4. Pausa para o terminal não fechar imediatamente
pause