package main;

import java.util.HashMap;

/**
 * Created by said on 13.09.2014.
 */
public class AccountService {
    private HashMap<String, UserProfile> users = new HashMap<>();
    private HashMap<String, UserProfile> sessions = new HashMap<>();

    public boolean addUser(String userName, UserProfile userProfile) {
        if (users.containsKey(userName))
            return false;
        users.put(userName, userProfile);
        return true;
    }

    public void addSessions(String sessionId, UserProfile userProfile) {
        sessions.put(sessionId, userProfile);
    }

    public void deleteSession(String sessionId) {
        sessions.remove(sessionId);
    }

    public UserProfile getUser(String userName) {
        return users.get(userName);
    }

    public UserProfile getSessions(String sessionId) {
        return sessions.get(sessionId);
    }

    public int getCountUsers() {
        return users.size();
    }

    public int getCountOnlineUsers() {
        return sessions.size();
    }
}
