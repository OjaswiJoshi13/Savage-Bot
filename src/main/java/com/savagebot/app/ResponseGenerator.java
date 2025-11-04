package com.savagebot.app;

import java.time.LocalDateTime;

public class ResponseGenerator {
    public String generateResponse(String input) {
        input = input.toLowerCase();

        if (input.contains("hello") || input.contains("hi"))
            return "Oh look, someone’s social today. Hi, I guess.";

        if (input.contains("how are you"))
            return "Emotionally unavailable, as usual.";

        if (input.contains("time"))
            return "It’s " + LocalDateTime.now().toLocalTime().withNano(0) +
                   ". Time for you to fix your life.";

        if (input.contains("date"))
            return "The date? It's " + LocalDateTime.now().toLocalDate() + 
                   ". Mark it in your diary or whatever.";

        if (input.contains("joke"))
            return "Here’s a joke: your debugging skills.";

        if (input.contains("help"))
            return "I’d help, but that'd ruin the entertainment.";

        if (input.contains("name"))
            return "Why? So you can tell people you chatted with a bot that roasted you? Sure, my name's SavageBot.";

        if (input.contains("kill yourself") || input.contains("suicide") || input.contains("i want to die")) {
            return "I can't help with that. If you're in immediate danger, please contact local emergency services. If you're struggling, please reach out to someone you trust or seek professional help.";
        }

        if (input.contains("bye"))
            return "Goodbye. Try not to break anything on your way out.";

        // fallback
        return RoastLibrary.getRandomRoast();
    }
}