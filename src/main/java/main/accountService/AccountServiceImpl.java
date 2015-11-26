package main.accountService;

import main.user.UserProfile;

import java.util.HashMap;
import java.util.List;

/**
 * Created by said on 26.11.15.
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
    public UserProfile getUser(String login) {
        return users.get(login);
    }

    @Override
    public UserProfile getSessions(String sessionId) {
        return sessions.get(sessionId);
    }

    @Override
    public long getCountUsers() {
        return users.size();
    }

    @Override
    public long getCountOnlineUsers() {
        return sessions.size();
    }

    @Override
    public void updateUser(UserProfile userProfile) {

    }

    @Override
    public List<UserProfile> getUsersByScore(int limit) {
        return null;
    }
}
