#!/usr/bin/env sh
set -eu
APP_HOME=$(CDPATH= cd -- "$(dirname -- "$0")" && pwd)
WRAPPER_JAR="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"
if [ ! -f "$WRAPPER_JAR" ]; then
  echo "Descargando Gradle Wrapper 9.9.2 oficial..."
  if command -v curl >/dev/null 2>&1; then
    curl -fLsS https://services.gradle.org/distributions/gradle-9.9.2-wrapper.jar -o "$WRAPPER_JAR"
  elif command -v wget >/dev/null 2>&1; then
    wget -q https://services.gradle.org/distributions/gradle-9.9.2-wrapper.jar -O "$WRAPPER_JAR"
  else
    echo 'Necesitas curl o wget' >&2; exit 1
  fi
  if command -v sha256sum >/dev/null 2>&1; then
    printf '%s  %s\n' 621008afc4f5d59357fcf06ffa644dd5aade5e091bdc363742710445f73cdaf8 "$WRAPPER_JAR" | sha256sum -c -
  fi
fi
exec "${JAVA_HOME:+$JAVA_HOME/bin/}java" -classpath "$WRAPPER_JAR" org.gradle.wrapper.GradleWrapperMain "$@"
