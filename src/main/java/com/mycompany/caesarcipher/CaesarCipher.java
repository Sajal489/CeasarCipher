package com.mycompany.caesarcipher;

import java.io.*;
import java.util.*;

public class CaesarCipher {

    static final int BOUND = 95;
    static Map<Character, Integer> map = new HashMap<>();
    static Map<Integer, Character> map2 = new HashMap<>();

    public static void main(String[] args) throws FileNotFoundException {

        makemaps(); // makes the mapping
        Scanner console = new Scanner(System.in); //create a user input
        System.out.println("Enter a key");
        int key = console.nextInt();
        file(key);// set up the files you will read from and write to

    }

    public static void file(int key) throws FileNotFoundException {
        // generate a random number/seed since we want make it somewhat challenging for people to crack
        Random n = new Random();
        key = n.nextInt(100000000) + 95;

        File file = new File("message.txt");
        Scanner reader = new Scanner(file);
        String message = "";
        PrintWriter writer = new PrintWriter("res.txt");
        while (reader.hasNextLine()) {
            message = reader.nextLine();
            String e = encode(message, key);
            writer.println(e);
        }
        reader.close();
        writer.close();

        file = new File("res.txt");
        reader = new Scanner(file);
        writer = new PrintWriter("decoded.txt");
        while (reader.hasNextLine()) {
            message = reader.nextLine();
            String e = decode(message, key);
            writer.println(e);
        }
        reader.close();
        writer.close();

    }

    public static String encode(String message, int key) {
        StringBuilder sb = new StringBuilder(message);
        for (int i = 0; i < message.length(); i++) {
            char checker = message.charAt(i);
            try {
                sb.setCharAt(i, map2.get((map.get(checker) + key) % BOUND));
            } catch (NullPointerException e) {
                System.out.println("There is a non-ASCII character");
                System.out.println(checker);
                System.exit(0);
            }

        }
        return sb.toString();
    }

    public static String decode(String message, int key) {
        StringBuilder sb = new StringBuilder(message);
        for (int i = 0; i < message.length(); i++) {
            char checker = message.charAt(i);
            int val = BOUND - (Math.abs((map.get(checker) - key)) % BOUND);
            if (val == BOUND) {
                val = 0;
            }
            sb.setCharAt(i, map2.get(val));
        }
        return sb.toString();
    }

    public static void makemaps() {
        for (int i = 32; i <= 127; i++) {
            map.put((char) i, i - 32);
            map2.put(i - 32, (char) i);
        }

    }
}
