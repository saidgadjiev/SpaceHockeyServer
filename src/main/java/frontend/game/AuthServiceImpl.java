package frontend.game;

/**
 * Created by said on 20.10.15.
 */

import main.gameService.AuthService;

import java.util.HashMap;
import java.util.Map;

/**
 * @author v.chibrikov
 */
public class AuthServiceImpl implements AuthService {
    private Map<String, String> userSessions = new HashMap<>();

    @Override
    public String getUserName(String sessionId) {
        return userSessions.get(sessionId);
    }

    @Override
    public void saveUserName(String sessionId, String name) {
        userSessions.put(sessionId, name);
    }
}