package main.accountService;

import main.user.UserProfile;

import java.util.List;

/**
 * Created by said on 13.10.15.
 */
public interface AccountService {
    boolean addUser(String userName, UserProfile userProfile);

    void addSessions(String sessionId, UserProfile userProfile);

    void deleteSession(String sessionId);

    UserProfile getUser(String userName);

    UserProfile getSessions(String sessionId);

    long getCountUsers();

    long getCountOnlineUsers();

    void updateUser(UserProfile userProfile);

    List<UserProfile> getUsersByScore(int limit);
}
