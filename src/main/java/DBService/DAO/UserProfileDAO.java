package dbService.dao;

import main.user.UserProfile;
import org.hibernate.*;
import org.hibernate.Query;
import org.hibernate.annotations.NamedNativeQueries;
import org.hibernate.annotations.NamedNativeQuery;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import javax.persistence.*;
import java.util.List;
import java.util.Queue;

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
    }

    public UserProfile read(long id) {
        return (UserProfile) session.get(UserProfile.class, id);
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

    public void update(UserProfile dataSet) {
        session.update(dataSet);
    }

    @SuppressWarnings("unchecked")
    public List<UserProfile> readLimitOrder(int limit) {
        Query query = session.createSQLQuery(
                "CALL proc(:var)")
                .addEntity(UserProfile.class)
                .setParameter("var", limit);

        return (List<UserProfile>) query.list();
    }

    public long readCountAll() {
        Criteria criteria = session.createCriteria(UserProfile.class);
        criteria.setProjection(Projections.rowCount());

        return (long) criteria.list().get(0);
    }
}
