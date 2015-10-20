package main.gameService;

/**
 * Created by said on 20.10.15.
 */
public interface AuthService {

    String getUserName(String sessionId);

    void saveUserName(String sessionId, String name);
}
