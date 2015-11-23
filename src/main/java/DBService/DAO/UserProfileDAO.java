package DBService.DAO;

import main.user.UserProfile;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import java.util.List;

/**
 * Created by said on 23.11.15.
 */

public class UserProfileDAO {
    private Session session;

    public UserProfileDAO(Session session) {
        this.session = session;
    }

    public void save(UserProfile dataSet) {
        session.save(dataSet);
        session.close();
    }

    public UserProfile read(long id) {
        return (UserProfile) session.load(UserProfile.class, id);
    }

    public UserProfile readByName(String name) {
        Criteria criteria = session.createCriteria(UserProfile.class);
        return (UserProfile) criteria.add(Restrictions.eq("login", name)).uniqueResult();
    }

    @SuppressWarnings("unchecked")
    public List<UserProfile> readAll() {
        Criteria criteria = session.createCriteria(UserProfile.class);
        return (List<UserProfile>) criteria.list();
    }
}
