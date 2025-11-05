# 💀 SavageBot - Sarcastic Java Chatbot (JavaFX + Google Gemini API)

SavageBot is a **sarcastic, roast-happy chatbot** built in Java using OOP principles and **JavaFX** for the GUI.  
Now upgraded with **Google Gemini AI** — so it not only roasts you, but also gives real, intelligent replies. 😈  

It features:
- Sarcastic/taunting yet *helpful* AI replies  
- Gemini API integration (real AI brain 🧠)  
- Neon/Dark CSS themes  
- Chat bubble style UI with bot avatar  
- Short-term message memory  
- Simple, clean OOP code structure (`ResponseGenerator`, `RoastLibrary`, `SavageBotUI`, `User`)

---

## 🧩 What’s Included

- Java source files: `src/main/java/com/savagebot/app/`
- Resources: `src/main/resources/css/`, `src/main/resources/images/`
- Configuration file: `config.properties` for fonts, styles, and API key
- `fonts/` folder for custom fonts
- Gemini API integration in `ResponseGenerator.java`
- `libs/json.jar` — for JSON parsing
- `run.bat` and `run.sh` — ready-to-run scripts
- This README and usage instructions

---

## 🧠 Prerequisites

Before running SavageBot, you’ll need:
- **Java 17+ (JDK)** → [https://adoptium.net](https://adoptium.net)
- **JavaFX SDK 17** → [https://gluonhq.com/products/javafx/](https://gluonhq.com/products/javafx/)
- **Google Gemini API Key** → [https://aistudio.google.com/app/apikey](https://aistudio.google.com/app/apikey)
- **org.json JAR** (JSON library for parsing API responses)

---

## ⚙️ Folder Structure

```bash
SavageBot/
├─ src/
│ └─ main/
│ ├─ java/com/savagebot/app/
│ │ ├─ Main.java
│ │ ├─ SavageBotUI.java
│ │ ├─ ResponseGenerator.java
│ │ ├─ RoastLibrary.java
│ │ └─ User.java
│ └─ resources/
│ ├─ css/
│ │ ├─ neon.css
│ │ └─ dark.css
│ ├─ images/
│ │ └─ bot.png
│ └─ config.properties
├─ libs/
│ └─ json.jar
├─ out/
├─ run.bat
├─ run.sh
└─ README.md
```

---

## 💡 How to Set Up & Run

### 🪟 Option A: Run from an IDE (Recommended)
1. Open this folder in **IntelliJ IDEA**, **VS Code**, or **Eclipse**.  
2. Add the JavaFX SDK libraries (`C:\javafx-sdk-17\lib`) to your module path.  
3. Add `libs/json.jar` to your classpath.  
4. Create a run configuration with:
   - Main class: `com.savagebot.app.Main`
   - VM options:  
     ```
     --module-path "C:\javafx-sdk-17\lib" --add-modules javafx.controls,javafx.fxml
     ```
5. Run it! 🎉

---

### 💻 Option B: Run from Command Line (Manual)

#### Step 1 — Set the JavaFX path  
If not set permanently, run:
```cmd
set JAVA_FX=C:\javafx-sdk-17\lib
```
#### Step 2 — Compile
```cmd
javac -cp libs\json.jar --module-path "%JAVA_FX%" --add-modules javafx.controls,javafx.fxml -d out src\main\java\com\savagebot\app\*.java
```
#### Step 3 — Run
```cmd
java -cp libs\json.jar;out --module-path "%JAVA_FX%" --add-modules javafx.controls,javafx.fxml com.savagebot.app.Main
```
✅ The GUI window will open, and your sarcastic AI will start chatting.

---

### 🧠 Gemini API Setup
#### Step 1 — Get Your API Key
Go to:
👉 https://aistudio.google.com/app/apikey

Click Create API Key, and copy it. (It starts with AIza...)

#### Step 2 — Add to Config
Open:
```css
src/main/resources/config.properties
```
Paste:
```Properties
api.key=AIzaSyDYourGeminiAPIKey
model=gemini-1.5-flash
css=css/neon.css
font.file=
```

---

### 🧩 Add JSON Library
1. Download org.json JAR:
👉 https://mvnrepository.com/artifact/org.json/json/latest
2. Move it to:
```bash
SavageBot/libs/json.jar
```
3. Done! Java can now parse API responses correctly.

---

### 🎨 Quick Customization
- Change theme:
```properties
css=css/dark.css
```
- Add fonts:
```properties
font.file=fonts/YourFont.ttf
```
- Edit roasts/responses:
Modify RoastLibrary.java and ResponseGenerator.java

---

### 🧩 Sample run.bat
```bat
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
```

---

## ⚙️ Troubleshooting
If something doesn’t work, don’t panic — SavageBot is sarcastic, not broken (usually).  
Here’s a quick guide to fix common issues 💀👇

| Problem | Likely Cause | Fix |
|----------|---------------|-----|
| ❌ **`Error: JavaFX runtime components are missing`** | The JavaFX SDK path isn’t set. | Run this in CMD before starting:<br>`set JAVA_FX=C:\javafx-sdk-17\lib`<br>Or permanently with:<br>`setx JAVA_FX "C:\javafx-sdk-17\lib"` |
| ⚠️ **`Cannot find org.json`** | The JSON library isn’t added to the classpath. | Download [org.json JAR](https://repo1.maven.org/maven2/org/json/json/20250517/json-20250517.jar) and place it in `libs/json.jar`. Then make sure your compile/run commands include:<br>`-cp libs\json.jar` |
| 🌀 **`NullPointerException getResource(...)`** | CSS or image files are missing from the `out/` directory. | Ensure your `run.bat` has:<br>`xcopy src\main\resources out /E /Y >nul`<br>And that you use absolute paths in code like:<br>`getClass().getResource("/css/neon.css")` |
| 🔒 **`Ugh, API call failed` or no reply from Gemini** | Invalid Gemini API key or no internet. | Check `src/main/resources/config.properties`:<br>`api.key=AIzaSyDYourGeminiAPIKey`<br>`model=gemini-1.5-flash`<br>Make sure you have an active internet connection. |
| 💅 **No styling / plain white window** | CSS path incorrect or not copied. | Verify:<br>`out/css/neon.css` exists.<br>Or change in config:<br>`css=css/dark.css` |
| 🧠 **Bot doesn’t remember context** | You’re running a stateless version (no memory). | To enable memory, add a list in `ResponseGenerator.java` and keep the last few messages:<br>`List<String> memory = new LinkedList<>();` |

---

🧰 **Pro Tips**
- Always copy resources to the `out/` folder before running.  
- If using IntelliJ IDEA, mark `src/main/resources` as *Resources Root* so it auto-copies CSS and images.  
- If API fails, you can still test offline — SavageBot will roast you locally anyway.  

> “If all else fails, blame the developer. That’s you, by the way.” 😎

---

## 💾 Future Enhancements
- Persistent chat memory (save + load chat history)
- Multiple personality modes (sarcastic, kind, chaotic)
- Live theme switch (Neon ↔ Dark)
- TTS voice output

---

## 💬 Example Chat

Here’s how SavageBot behaves in the wild 👇

**You:**  
> hey savagebot what’s java  

**SavageBot:**  
> Oh, you mean that language that makes you write 10 lines just to print “Hello”?  
> Anyway — it’s object-oriented, ancient, and somehow still what’s running me right now.  

**You:**  
> wow, rude.  

**SavageBot:**  
> Not rude. Honest. There’s a difference — look it up, genius. 😏  

**You:**  
> okay fine, what’s python then?  

**SavageBot:**  
> Ah yes, the language people use when they’re too lazy to type semicolons.  
> But I’ll admit — it’s cleaner, faster, and makes Java devs cry a little inside. 🐍  

---

🧠 *SavageBot always mixes sarcasm with accuracy — so you’ll learn things, but also get roasted while doing it.*

---

## 📜 License
This project is free for educational or personal use.
You can modify, extend, or remix it — just don’t rebrand it as “AI 2.0” and sell it to investors 😏

---
