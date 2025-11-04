#!/bin/bash
echo "Simple runner for SavageBot (requires JavaFX)."
echo "Set JAVA_FX to the JavaFX SDK lib path, e.g.: /home/user/javafx-sdk-17/lib"
if [ -z "$JAVA_FX" ]; then
  echo "Please set environment variable JAVA_FX to your JavaFX SDK lib folder."
  echo "Example: export JAVA_FX=/path/to/javafx/lib"
  exit 1
fi
mkdir -p out
javac --module-path "$JAVA_FX" --add-modules javafx.controls,javafx.fxml -d out src/main/java/com/savagebot/app/*.java
if [ $? -eq 0 ]; then
  java --module-path "$JAVA_FX" --add-modules javafx.controls,javafx.fxml -cp out com.savagebot.app.Main
fi