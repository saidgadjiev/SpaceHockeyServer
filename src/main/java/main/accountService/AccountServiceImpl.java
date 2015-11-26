package main.accountService;

import dbService.DBService;
import dbService.DBServiceImpl;
import main.user.UserProfile;

import java.util.HashMap;
import java.util.List;

/**
 * Created by said on 13.09.2014.
 */

public class AccountServiceImpl implements AccountService {
    private DBService dbService = new DBServiceImpl();
    private HashMap<String, UserProfile> sessions = new HashMap<>();

    @Override
    public boolean addUser(String userName, UserProfile userProfile) {
        if (dbService.readByName(userName) != null)
            return false;
        dbService.save(userProfile);
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
        return dbService.readByName(login);
    }

    @Override
    public UserProfile getSessions(String sessionId) {
        return sessions.get(sessionId);
    }

    @Override
    public long getCountUsers() {
        return dbService.readCountAll();
    }

    @Override
    public long getCountOnlineUsers() {
        return sessions.size();
    }

    @Override
    public void updateUser(UserProfile userProfile) {
        dbService.update(userProfile);
    }

    @Override
    public List<UserProfile> getUsersByScore(int limit) {
        return dbService.readLimitOrder(limit);
    }
}
