package dbService.executor;

import org.hibernate.Session;

/**
 * Created by said on 29.11.15.
 */
public interface ExecQuery<T, P> {
    T execQuery(Session session, P parameter);
}
