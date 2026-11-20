@echo off
setlocal
set "APP_HOME=%~dp0"
set "WRAPPER_JAR=%APP_HOME%gradle\wrapper\gradle-wrapper.jar"
if not exist "%WRAPPER_JAR%" (
  echo Descargando el wrapper oficial de Gradle 9.9.2 ...
  powershell -NoProfile -ExecutionPolicy Bypass -Command "$ErrorActionPreference='Stop'; $target='%WRAPPER_JAR%'; Invoke-WebRequest -Uri 'https://services.gradle.org/distributions/gradle-9.9.2-wrapper.jar' -OutFile $target; $actual=(Get-FileHash -Algorithm SHA256 $target).Hash.ToLowerInvariant(); if ($actual -ne '621008afc4f5d59357fcf06ffa644dd5aade5e091bdc363742710445f73cdaf8') { Remove-Item $target -Force; throw 'SHA256 del wrapper invalido' }"
  if errorlevel 1 exit /b 1
)
if defined JAVA_HOME (
  set "JAVA_EXE=%JAVA_HOME%\bin\java.exe"
) else (
  set "JAVA_EXE=java.exe"
)
"%JAVA_EXE%" -classpath "%WRAPPER_JAR%" org.gradle.wrapper.GradleWrapperMain %*
exit /b %errorlevel%
