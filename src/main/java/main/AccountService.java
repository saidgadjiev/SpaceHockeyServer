package main;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

/**
 * Created by said on 13.09.2014.
 */
public class AccountService {
    @NotNull private HashMap<String, UserProfile> users = new HashMap<>();
    @NotNull private HashMap<String, UserProfile> sessions = new HashMap<>();

    public boolean addUser(@NotNull String userName, UserProfile userProfile) {
        if (users.containsKey(userName))
            return false;
        users.put(userName, userProfile);
        return true;
    }

    public void addSessions(@Nullable String sessionId, UserProfile userProfile) {
        sessions.put(sessionId, userProfile);
    }

    public void deleteSession(@Nullable String sessionId) {
        sessions.remove(sessionId);
    }

    @Nullable
    public UserProfile getUser(@Nullable String userName) {
        return users.get(userName);
    }

    @Nullable
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
