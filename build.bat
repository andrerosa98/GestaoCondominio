@echo off

:: Configura o terminal para usar UTF-8
chcp 65001 > nul

:: Move para a raiz do diretório onde o .bat está localizado
cd /d "%~dp0"

:: 1. Cria o diretório de saída (out), se ainda não existir
if not exist "out" (
    mkdir "out"
)

:: 2. Compila todos os arquivos .java manualmente
echo Compilando o código-fonte...
javac -d out -cp "lib/*" src/com/gestaoCondominio/controller/Main.java src/com/gestaoCondominio/service/*.java src/com/gestaoCondominio/util/*.java

if %errorlevel% neq 0 (
    echo Erro na compilação. Verifique os arquivos de código.
    pause
    exit /b
)

:: 3. Executa a classe principal com MySQL no classpath
echo Iniciando o programa...
java -cp "out;lib/*" com.gestaoCondominio.controller.Main

:: 4. Pausa para evitar fechamento imediato do terminal
pause