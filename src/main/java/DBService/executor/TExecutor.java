package dbService.executor;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

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

    public <P> void execUpdate(ExecUpdate<P> exec, P parameter) {
        Session session = null;
        Transaction transaction = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            exec.execUpdate(session, parameter);
        } catch (HibernateException ex) {
            ex.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        } finally {
            if (transaction != null) {
                transaction.commit();
            }
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }
}
