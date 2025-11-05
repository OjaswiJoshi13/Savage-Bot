@echo off
echo SavageBot: Starting with Gemini API (requires JavaFX + json.jar)
if "%JAVA_FX%"=="" (
  echo Please set JAVA_FX to your JavaFX SDK lib path.
  echo Example: set JAVA_FX=C:\javafx-sdk-17\lib
  pause
  exit /b 1
)
if not exist out mkdir out
xcopy src\main\resources out /E /Y >nul

javac -cp libs\json.jar --module-path "%JAVA_FX%" --add-modules javafx.controls,javafx.fxml -d out src\main\java\com\savagebot\app\*.java

if %errorlevel%==0 (
  java -cp libs\json.jar;out --module-path "%JAVA_FX%" --add-modules javafx.controls,javafx.fxml com.savagebot.app.Main
)
pause
