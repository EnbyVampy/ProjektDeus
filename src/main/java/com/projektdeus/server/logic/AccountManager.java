package com.projektdeus.server.logic;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class AccountManager {
    private static final String FILE_PATH = "accounts.json";
    private Map<String, String> accounts = new HashMap<>();
    private final Gson gson = new Gson();

    public AccountManager() {
        load();
    }

    public synchronized boolean register(String user, String pass) {
        if (accounts.containsKey(user)) {
            System.out.println("Registration failed: User " + user + " already exists.");
            return false;
        }
        accounts.put(user, pass);
        save();
        System.out.println("New account registered: " + user);
        return true;
    }

    public synchronized boolean login(String user, String pass) {
        boolean success = pass.equals(accounts.get(user));
        System.out.println("Login attempt for " + user + (success ? ": SUCCESS" : ": FAILED"));
        return success;
    }

    private void load() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;
        try (Reader reader = new FileReader(file)) {
            accounts = gson.fromJson(reader, new TypeToken<Map<String, String>>(){}.getType());
        } catch (IOException e) { e.printStackTrace(); }
    }

    private void save() {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(accounts, writer);
        } catch (IOException e) { e.printStackTrace(); }
    }
}