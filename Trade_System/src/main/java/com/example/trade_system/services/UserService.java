package com.example.trade_system.services;

import com.example.trade_system.models.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.security.SecureRandom;
@Service
public class UserService {
    private static final String USERS_FILE = "users.txt";
    private final Map<String, User> users = new HashMap<>();
    private final Map<String, String> usersIds = new HashMap<>();
    private final ObjectMapper mapper = new ObjectMapper();

    public UserService() throws IOException {
        loadUsers();
    }

    private void loadUsers() throws IOException {
        File file = new File(USERS_FILE);
        if (!file.exists()) return;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                User user = mapper.readValue(line, User.class);
                users.put(user.getUsername(), user);
                usersIds.put(user.getUserId(), user.getUsername());
            }
        }
    }

    public boolean register(String username, String rawPassword) throws IOException {
        if (users.containsKey(username)) return false;
        String userId = UUID.randomUUID().toString();
        String hash = BCrypt.hashpw(rawPassword, BCrypt.gensalt());
        User user = new User(userId, username, hash);
        users.put(username, user);
        saveUser(user);
        return true;
    }

    public User login(String username, String rawPassword) {
        User user = users.get(username);
        if (user != null && BCrypt.checkpw(rawPassword, user.getPassword())) {
            return user;
        }
        return null;
    }

    private void saveUser(User user) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USERS_FILE, true))) {
            writer.write(mapper.writeValueAsString(user));
            writer.newLine();
        }
    }

    public String getUserById(String userId) {
        return usersIds.get(userId);
    }
}
