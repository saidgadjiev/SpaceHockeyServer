package main;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by v.chibrikov on 13.09.2014.
 */
public class AccountService {
    private Map<String, UserProfile> users = new HashMap<>();
    private Map<String, UserProfile> sessions = new HashMap<>();

    public boolean addUser(String userName, UserProfile userProfile) {
        if (users != null) {
            if (users.containsKey(userName))
                return false;
            users.put(userName, userProfile);
            return true;
        }
    }

    public void addSessions(String sessionId, UserProfile userProfile) {
        if (sessions != null) {
            sessions.put(sessionId, userProfile);
        }
    }

    public void deleteSession(@Nullable String sessionId) {
        if (sessions != null) {
            sessions.remove(sessionId);
        }
    }

    @Nullable
    public UserProfile getUser(String userName) {
        if (users != null) {
            return users.get(userName);
        }
    }

    @Nullable
    public UserProfile getSessions(String sessionId) {
        if (sessions != null) {
            return sessions.get(sessionId);
        }
    }

    public int getCountUsers() {
        if (users != null) {
            return users.size();
        }
    }

    public int getCountOnlineUsers() {
        if (sessions != null) {
            return sessions.size();
        }
    }
}
