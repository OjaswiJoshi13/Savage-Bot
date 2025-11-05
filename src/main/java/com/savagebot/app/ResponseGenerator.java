package com.savagebot.app;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

public class ResponseGenerator {
    private String apiKey;
    private String model;
    private List<String> memory = new LinkedList<>();

    public ResponseGenerator() {
        try {
            Properties prop = new Properties();
            prop.load(Files.newInputStream(Paths.get("out/config.properties")));
            apiKey = prop.getProperty("api.key");
            model = prop.getProperty("model", "gemini-1.5-flash");
        } catch (Exception e) {
            System.err.println("Error loading API key: " + e.getMessage());
        }
    }

    public String generateResponse(String input) {
        try {
            // Keep chat history
            memory.add("User: " + input);
            if (memory.size() > 10) memory.remove(0);

            // Build memory context
            StringBuilder context = new StringBuilder();
            for (String msg : memory) context.append(msg).append("\n");

            String prompt = "You're SavageBot — a sarcastic but helpful AI. You remember the last few messages. " +
                    "Roast the user with humor but still answer correctly.\nConversation so far:\n" + context +
                    "\nNow respond to the latest message helpfully but with sarcasm.";

            JSONObject content = new JSONObject()
                .put("contents", new JSONArray()
                    .put(new JSONObject()
                        .put("parts", new JSONArray()
                            .put(new JSONObject().put("text", prompt)))));

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://generativelanguage.googleapis.com/v1beta/models/" + model + ":generateContent?key=" + apiKey))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(content.toString()))
                .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

            JSONObject json = new JSONObject(response.body());
            String reply = json.getJSONArray("candidates")
                    .getJSONObject(0)
                    .getJSONObject("content")
                    .getJSONArray("parts")
                    .getJSONObject(0)
                    .getString("text")
                    .trim();

            memory.add("Bot: " + reply);
            return reply;
        } catch (Exception e) {
            e.printStackTrace();
            return "Ugh, something broke while contacting Gemini. Probably your fault.";
        }
    }
}
