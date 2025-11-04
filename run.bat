@echo off
echo Simple runner for SavageBot (requires JavaFX).
echo Set JAVA_FX to the JavaFX SDK lib path, e.g.: C:\javafx-sdk-17\lib
if "%JAVA_FX%"=="" (
  echo Please set environment variable JAVA_FX to your JavaFX SDK lib folder.
  echo Example: set JAVA_FX=C:\path\to\javafx\lib
  pause
  exit /b 1
)
if not exist out mkdir out
javac --module-path "%JAVA_FX%" --add-modules javafx.controls,javafx.fxml -d out src\\main\\java\\com\\savagebot\\app\\*.java
if %errorlevel%==0 (
  java --module-path "%JAVA_FX%" --add-modules javafx.controls,javafx.fxml -cp out com.savagebot.app.Main
)
pause