package main.accountService;

import main.UserProfile;

import java.util.HashMap;

/**
 * Created by said on 13.09.2014.
 */
public class AccountServiceImpl implements AccountService {
    private HashMap<String, UserProfile> users = new HashMap<>();
    private HashMap<String, UserProfile> sessions = new HashMap<>();

    @Override
    public boolean addUser(String userName, UserProfile userProfile) {
        if (users.containsKey(userName))
            return false;
        users.put(userName, userProfile);
        return true;
    }

    @Override
    public void addSessions(String sessionId, UserProfile userProfile) {
        sessions.put(sessionId, userProfile);
    }

    @Override
    public void deleteSession(String sessionId) {
        sessions.remove(sessionId);
    }

    @Override
    public UserProfile getUser(String userName) {
        return users.get(userName);
    }

    @Override
    public UserProfile getSessions(String sessionId) {
        return sessions.get(sessionId);
    }

    @Override
    public int getCountUsers() {
        return users.size();
    }

    @Override
    public int getCountOnlineUsers() {
        return sessions.size();
    }
}
