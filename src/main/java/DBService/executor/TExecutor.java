package dbService.executor;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Created by said on 29.11.15.
 */
public class TExecutor {
    private SessionFactory sessionFactory;

    public TExecutor(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public <T, P> T execQuery(ExecQuery<T, P> exec, P parameter) {
        T result = null;
        Session session = null;

        try {
            session = sessionFactory.openSession();
            result = exec.execQuery(session, parameter);
        } catch (HibernateException ex) {
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }

        return result;
    }

    public <T> void execUpdate() {

    }
}
