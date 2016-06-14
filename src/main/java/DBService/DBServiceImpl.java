package dbService;

import dbService.dao.UserProfileDAO;
import dbService.executor.TExecutor;
import main.user.UserProfile;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import resource.DBServerSettings;

import java.util.List;

/**
 * Created by said on 23.11.15.
 */
public class DBServiceImpl implements DBService {
    private SessionFactory sessionFactory;
    private TExecutor tExecutor;

    public DBServiceImpl(DBServerSettings dbServerSettings) {
        Configuration configuration = new Configuration();

        configuration.addAnnotatedClass(UserProfile.class);
        configuration.setProperty("hibernate.dialect", dbServerSettings.getDialect());
        configuration.setProperty("hibernate.connection.driver_class", dbServerSettings.getDriverClass());
        configuration.setProperty("hibernate.connection.url", dbServerSettings.getConnectionUrl());
        configuration.setProperty("hibernate.connection.username", dbServerSettings.getUsername());
        configuration.setProperty("hibernate.connection.password", dbServerSettings.getPassword());
        configuration.setProperty("hibernate.show_sql", dbServerSettings.getShowSql());
        configuration.setProperty("hibernate.hbm2ddl.auto", dbServerSettings.getMode());

        sessionFactory = createSessionFactory(configuration);
        tExecutor = new TExecutor(sessionFactory);
    }

    public String getLocalStatus() {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        String status = transaction.getLocalStatus().toString();
        session.close();

        return status;
    }

    public void save(UserProfile dataSet) {
        tExecutor.execUpdate(
                (session, parameter) -> {
                    UserProfileDAO dao = new UserProfileDAO(session);
                    dao.save(dataSet);
                }, dataSet);
    }

    public UserProfile read(long id) {
        return tExecutor.execQuery(
                (session, parameter) -> {
                    dbService.dao.UserProfileDAO dao = new UserProfileDAO(session);

                    return dao.read(parameter);
                }, id);
    }

    public UserProfile readByName(String name) {
        return tExecutor.execQuery(
                (session, parameter) -> {
                    UserProfileDAO dao = new UserProfileDAO(session);

                    return dao.readByName(parameter);
                }, name);
    }

    public List<UserProfile> readAll() {
        return tExecutor.execQuery(
                (session, parameter) -> {
                    UserProfileDAO dao = new UserProfileDAO(session);

                    return dao.readAll();
                }, null);
    }

    public long readCountAll() {
        return tExecutor.execQuery(
                (session, parameter) -> {
                    UserProfileDAO dao = new UserProfileDAO(session);

                    return dao.readCountAll();
                }, null);
    }

    public void update(UserProfile dataSet) {
        tExecutor.execUpdate(
                (session, parameter) -> {
                    UserProfileDAO dao = new UserProfileDAO(session);
                    dao.update(dataSet);
                }, dataSet);
    }

    public List<UserProfile> readLimitOrder(int limit) {
            Session session = sessionFactory.openSession();
            UserProfileDAO dao = new UserProfileDAO(session);

            return dao.readLimitOrder(limit);
    }

    public void shutdown() {
        sessionFactory.close();
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();

        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();

        return configuration.buildSessionFactory(serviceRegistry);
    }
}
