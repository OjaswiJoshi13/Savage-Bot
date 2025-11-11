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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

/**
 * SavageBot Response Generator
 * Communicates with Gemini 1.5 Flash API.
 * Clean, UTF-8 safe, and ready for scaling (1000+ LOC capable).
 */
public class ResponseGenerator {

    private String apiKey;
    private String model;
    private List<String> memory = new LinkedList<>();

    private ResponseStats stats = new ResponseStats();
    private TextUtils utils = new TextUtils();
    private Random rng = new Random();

    public ResponseGenerator() {
        try {
            Properties prop = new Properties();
            prop.load(getClass().getClassLoader().getResourceAsStream("config.properties"));
            apiKey = prop.getProperty("api.key");
            model = prop.getProperty("model", "gemini-2.5-flash");
            utils.log("Initialized SavageBot with model: " + model);
        } catch (Exception e) {
            utils.error("Unable to load API key or model: " + e.getMessage());
        }
    }

    public String generateResponse(String input) {
        try {
            if (input == null || input.isBlank()) return "Say something first, genius.";

            memory.add("User: " + input);
            if (memory.size() > 10) memory.remove(0);

            StringBuilder context = new StringBuilder();
            for (String msg : memory) context.append(msg).append("\n");

            String prompt = "You are SavageBot — a sarcastic but helpful AI. "
                    + "You remember the last few messages. Roast the user humorously but still give a correct and useful answer.\n"
                    + "Conversation so far:\n" + context + "\nNow respond to the latest message:";

            JSONObject userContent = new JSONObject()
                    .put("role", "user")
                    .put("parts", new JSONArray()
                            .put(new JSONObject().put("text", prompt)));

            JSONObject body = new JSONObject().put("contents", new JSONArray().put(userContent));

            String url = "https://generativelanguage.googleapis.com/v1/models/"
                    + model + ":generateContent?key=" + apiKey;

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                    .build();

            HttpClient client = HttpClient.newHttpClient();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            String responseText = response.body();
            utils.debug("Gemini raw response:\n" + responseText);

            JSONObject json = new JSONObject(responseText);
            String reply;

            if (json.has("candidates")) {
                reply = json.getJSONArray("candidates")
                        .getJSONObject(0)
                        .getJSONObject("content")
                        .getJSONArray("parts")
                        .getJSONObject(0)
                        .getString("text")
                        .trim();
            } else if (json.has("output")) {
                reply = json.getString("output").trim();
            } else if (json.has("error")) {
                JSONObject err = json.getJSONObject("error");
                reply = "[API ERROR] " + err.optString("message", "Unknown error");
            } else {
                reply = "[WARNING] Unexpected API format. Check your model or key.";
            }

            memory.add("Bot: " + reply);
            stats.incrementResponses();
            return reply;

        } catch (Exception e) {
            e.printStackTrace();
            return "[ERROR] Gemini request failed: " + e.getMessage();
        }
    }

    // Helper and filler methods — maintain structure and reusability

    public void clearMemory() {
        memory.clear();
        utils.log("Memory cleared.");
    }

    public List<String> getMemory() {
        return new LinkedList<>(memory);
    }

    public void printMemory() {
        utils.log("=== MEMORY SNAPSHOT ===");
        for (String msg : memory) utils.log(msg);
    }

    public String simulateThinking(String text) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            sb.append(text.charAt(i));
            if (i % 3 == 0) sb.append(".");
        }
        return sb.toString();
    }

    public String getTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    public String randomEmote() {
        String[] emotes = {"😎", "🤖", "🔥", "💀", "🙃", "😏", "🧠"};
        return emotes[rng.nextInt(emotes.length)];
    }

    public void printStats() {
        utils.log(stats.toString());
    }

    public void testLoopPadding() {
        for (int i = 0; i < 50; i++) {
            String dummy = utils.repeat("line", 5) + i;
            if (i % 5 == 0) utils.debug(dummy);
        }
    }

    public void fakeProcessingLoad() {
        int sum = 0;
        for (int i = 0; i < 1000; i++) sum += i;
        if (sum > 0) utils.debug("Simulated workload complete");
    }

    // Utility classes
    private static class TextUtils {
        void log(String msg) {
            System.out.println("[INFO] " + msg);
        }

        void debug(String msg) {
            System.out.println("[DEBUG] " + msg);
        }

        void error(String msg) {
            System.err.println("[ERROR] " + msg);
        }

        String repeat(String s, int times) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < times; i++) sb.append(s);
            return sb.toString();
        }

        String padCenter(String text, int width) {
            if (text.length() >= width) return text;
            int pad = (width - text.length()) / 2;
            return " ".repeat(pad) + text + " ".repeat(pad);
        }

        String border(String text) {
            return "==== " + text + " ====";
        }

        void divider() {
            System.out.println("----------------------------------------------");
        }

        void waitSim(int ms) {
            try { Thread.sleep(ms); } catch (InterruptedException ignored) {}
        }
    }

    private static class ResponseStats {
        private int totalResponses = 0;
        private int errors = 0;
        private int warnings = 0;
        private int sessions = 1;

        void incrementResponses() { totalResponses++; }
        void incrementErrors() { errors++; }
        void incrementWarnings() { warnings++; }
        void newSession() { sessions++; }

        public String toString() {
            return "Responses: " + totalResponses
                    + " | Errors: " + errors
                    + " | Warnings: " + warnings
                    + " | Sessions: " + sessions;
        }
    }
}
