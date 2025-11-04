package com.savagebot.app;

import java.util.Random;

public class RoastLibrary {
    private static final String[] ROASTS = {
        "You're like a semicolon — unnecessary but somehow causing problems.",
        "Your code runs? Lucky accident.",
        "Oh great, another human who thinks they’re special.",
        "Do you ever read error messages, or just cry?",
        "If laziness were a skill, you’d be a senior developer.",
        "I’d explain it to you, but I left my crayons at home.",
        "You debug like a detective who forgets to look at the clue.",
        "Congrats on trying. Apply that energy to something useful.",
        "Console.log called; it said 'I miss you.'",
        "Error 404: Common sense not found."
    };

    public static String getRandomRoast() {
        Random rand = new Random();
        return ROASTS[rand.nextInt(ROASTS.length)];
    }
}