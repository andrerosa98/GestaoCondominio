@echo off
:: Configura o terminal para usar UTF-8
chcp 65001 > nul

:: Move para o diretório onde o .bat está localizado
cd /d "%~dp0"

:: 1. Cria o diretório de saída (se não existir)
if not exist "..\out" (
    mkdir "..\out"
)

:: 2. Compila o código-fonte para a pasta 'out'
echo Compilando o código-fonte...
javac -d ..\out -cp . com\gestaoCondominio\controller\Main.java

if %errorlevel% neq 0 (
    echo Erro na compilação. Verifique os arquivos de código.
    pause
    exit /b
)

:: 3. Executa a classe principal
echo Iniciando o programa...
java -cp ..\out com.gestaoCondominio.controller.Main

:: 4. Pausa para o terminal não fechar automaticamente
pause