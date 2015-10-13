package main.accountService;

import main.UserProfile;

/**
 * Created by said on 13.10.15.
 */
public interface AccountService {
    boolean addUser(String userName, UserProfile userProfile);
    void addSessions(String sessionId, UserProfile userProfile);
    void deleteSession(String sessionId);
    UserProfile getUser(String userName);
    UserProfile getSessions(String sessionId);
    int getCountUsers();
    int getCountOnlineUsers();
}
