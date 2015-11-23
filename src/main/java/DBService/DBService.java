package DBService;

import main.user.UserProfile;

import java.util.List;

/**
 * Created by said on 23.11.15.
 */
public interface DBService {

    String getLocalStatus();

    void save(UserProfile dataSet);

    UserProfile read(long id);

    UserProfile readByName(String name);

    List<UserProfile> readAll();

    void shutdown();
}
