package com.savagebot.app;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class SavageBotUI extends Application {

    private VBox chatContainer;
    private TextField userInput;
    private Button sendButton;
    private ResponseGenerator generator = new ResponseGenerator();
    private Properties config = new Properties();

    @Override
    public void start(Stage stage) throws Exception {
        // Load config
        try (InputStream in = getClass().getResourceAsStream("/config.properties")) {
            if (in != null) config.load(in);
        }

        loadStyles(stage);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);

        chatContainer = new VBox(10);
        chatContainer.setPadding(new Insets(12));
        scrollPane.setContent(chatContainer);

        userInput = new TextField();
        userInput.setPromptText("Type something... if you can think, that is.");
        userInput.setPrefWidth(320);

        sendButton = new Button("Send");

        sendButton.setOnAction(e -> handleUserInput());
        userInput.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) handleUserInput();
        });

        HBox inputBox = new HBox(10, userInput, sendButton);
        inputBox.setPadding(new Insets(10));
        inputBox.setAlignment(Pos.CENTER);

        BorderPane root = new BorderPane();
        root.setCenter(scrollPane);
        root.setBottom(inputBox);

        Scene scene = new Scene(root, 520, 640);
        String css = config.getProperty("css","css/neon.css");
        scene.getStylesheets().add(getClass().getResource("/" + css).toExternalForm());

        stage.setTitle("SavageBot 💀 - The Sarcastic Companion");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("/images/bot.png")));
        stage.setScene(scene);
        stage.show();

        addBotMessage("Oh great, another human. Type something and waste my CPU cycles.");
    }

    private void handleUserInput() {
        String input = userInput.getText().trim();
        if (input.isEmpty()) return;

        addUserMessage(input);
        String response = generator.generateResponse(input);
        addBotMessage(response);

        userInput.clear();

        if (input.toLowerCase().contains("bye")) {
            addBotMessage("Finally. Don't come back unless you've improved.");
            userInput.setDisable(true);
            sendButton.setDisable(true);
        }
    }

    private void addUserMessage(String msg) {
        HBox hb = new HBox();
        hb.setAlignment(Pos.CENTER_RIGHT);
        Label lbl = new Label(msg);
        lbl.getStyleClass().add("user-bubble");
        lbl.setWrapText(true);
        lbl.setMaxWidth(340);
        hb.getChildren().add(lbl);
        chatContainer.getChildren().add(hb);
        scrollToBottom();
    }

    private void addBotMessage(String msg) {
        HBox hb = new HBox(10);
        hb.setAlignment(Pos.CENTER_LEFT);

        ImageView iv = new ImageView(new Image(getClass().getResourceAsStream("/images/bot.png")));
        iv.setFitWidth(36);
        iv.setFitHeight(36);

        Label lbl = new Label(msg);
        lbl.getStyleClass().add("bot-bubble");
        lbl.setWrapText(true);
        lbl.setMaxWidth(380);

        hb.getChildren().addAll(iv, lbl);
        chatContainer.getChildren().add(hb);
        scrollToBottom();
    }

    private void scrollToBottom() {
        // Force layout and scroll to bottom
        chatContainer.layout();
        // scrollpane auto handled by binding might be better; simple approach:
        Stage s = (Stage) userInput.getScene().getWindow();
        s.sizeToScene();
    }

    private void loadStyles(Stage stage) {
        // allow switching fonts via config - loads a font file if present
        String fontFile = config.getProperty("font.file", "");
        if (!fontFile.isEmpty()) {
            try {
                InputStream f = getClass().getResourceAsStream("/" + fontFile);
                if (f != null) {
                    Font.loadFont(f, 14);
                }
            } catch (Exception e) {
                System.out.println("Could not load custom font: " + e.getMessage());
            }
        }
    }
}