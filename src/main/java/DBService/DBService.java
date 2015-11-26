package dbService;

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

    long readCountAll();

    void update(UserProfile dataSet);

    List<UserProfile> readLimitOrder(int limit);

    void shutdown();
}
