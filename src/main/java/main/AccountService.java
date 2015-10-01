package main;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

/**
 * Created by said on 13.09.2014.
 */
public class AccountService {
    private HashMap<String, UserProfile> users = new HashMap<>();
    private HashMap<String, UserProfile> sessions = new HashMap<>();

    public boolean addUser(String userName, UserProfile userProfile) {
        assert users != null;
        if (users.containsKey(userName))
            return false;
        users.put(userName, userProfile);
        return true;
    }

    public void addSessions(String sessionId, UserProfile userProfile) {
        assert sessions != null;
        sessions.put(sessionId, userProfile);
    }

    public void deleteSession(@Nullable String sessionId) {
        assert sessions != null;
        sessions.remove(sessionId);
    }

    @Nullable
    public UserProfile getUser(String userName) {
        assert users != null;
        return users.get(userName);
    }

    @Nullable
    public UserProfile getSessions(String sessionId) {
        assert sessions != null;
        return sessions.get(sessionId);
    }

    public int getCountUsers() {
        assert users != null;
        return users.size();
    }

    public int getCountOnlineUsers() {
        assert sessions != null;
        return sessions.size();
    }
}
