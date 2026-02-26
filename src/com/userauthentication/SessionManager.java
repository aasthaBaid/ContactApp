package com.userauthentication;

import java.util.HashMap;
import java.util.Map;

import com.userregistration.User;

public class SessionManager {

    private static SessionManager instance;
    private Map<String, Session> activeSessions;

    private SessionManager() {
        activeSessions = new HashMap<>();
    }

    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void createSession(User user) {
        Session session = new Session(user);
        activeSessions.put(user.getEmail(), session);
        System.out.println("Session created for: " + user.getEmail());
    }

    public void destroySession(User user) {
        activeSessions.remove(user.getEmail());
        System.out.println("Session destroyed for: " + user.getEmail());
    }

    public boolean isLoggedIn(String email) {
        return activeSessions.containsKey(email);
    }
}