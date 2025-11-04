# SavageBot - Sarcastic Java Chatbot (JavaFX)

SavageBot is a sarcastic, roast-happy chatbot built in Java using OOP principles and JavaFX for the GUI.
It features:
- Sarcastic/taunting replies
- Neon/dark CSS themes
- Easy customization via `src/main/resources/config.properties`
- Chat bubble style UI with bot avatar
- Simple, clean OOP code (ResponseGenerator, RoastLibrary, SavageBotUI, User)

## What is included
- Java source files: `src/main/java/com/savagebot/app/`
- Resources: `src/main/resources/css/`, `src/main/resources/images/`
- `config.properties` to switch CSS and font
- `fonts/` folder where you can drop custom fonts
- This README and usage instructions

## How to build & run

### Option A: Run using your IDE (recommended)
1. Open this folder as a project in IntelliJ IDEA, Eclipse, or VS Code (Java extension).
2. Ensure you have **Java 11+** installed and JavaFX SDK configured (JavaFX is required).
3. Add JavaFX library to your project's module path (or classpath) and run `com.savagebot.app.Main`.

### Option B: Run from command line (without build tools)
You need:
- Java 11+ JDK installed
- JavaFX SDK downloaded for your platform

Steps:
1. Compile:
   ```
   javac --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml -d out \
     src/main/java/com/savagebot/app/*.java
   ```
2. Run:
   ```
   java --module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml -cp out com.savagebot.app.Main
   ```

Replace `/path/to/javafx/lib` with the path to the `lib` folder inside your JavaFX SDK.

## Quick customization
- Change theme: Open `src/main/resources/config.properties` and set `css=css/dark.css` or `css=css/neon.css`.
- Add fonts: Put `.ttf` file in `fonts/` and set `font.file=fonts/YourFont.ttf` in `config.properties`.
- Edit roasts/responses: Modify `RoastLibrary.java` and `ResponseGenerator.java`.

## Notes
- This project ships as source code. If you want a runnable JAR, build it in your IDE or with Gradle/Maven. I provided detailed steps to run from command line.
- The bot includes safety handling for self-harm related inputs; it will refuse and suggest help.

Enjoy the savagery. 😈