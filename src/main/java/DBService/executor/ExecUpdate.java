package dbService.executor;

import org.hibernate.Session;

/**
 * Created by said on 29.11.15.
 */
public interface ExecUpdate<P> {
    void execUpdate(Session session, P parameter);
}
