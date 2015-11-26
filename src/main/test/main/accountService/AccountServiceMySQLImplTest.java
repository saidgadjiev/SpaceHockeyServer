package main.accountService;

import dbService.DBService;
import dbService.DBServiceImpl;
import main.user.UserProfile;
import org.junit.Before;
import org.junit.Test;
import resource.DBServerSettings;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by said on 26.11.15.
 */
public class AccountServiceMySQLImplTest {

    private AccountServiceMySQLImpl accountService;
    private final UserProfile testUser = new UserProfile("testLogin", "testPassword", "said@mail.ru");

    @Before
    public void setUp() {
        DBServerSettings dbServerSettings = mock(DBServerSettings.class);
        when(dbServerSettings.getConnectionUrl()).thenReturn("jdbc:mysql://localhost:3306/Test");
        when(dbServerSettings.getDialect()).thenReturn("org.hibernate.dialect.MySQLDialect");
        when(dbServerSettings.getDriverClass()).thenReturn("com.mysql.jdbc.Driver");
        when(dbServerSettings.getMode()).thenReturn("validate");
        when(dbServerSettings.getShowSql()).thenReturn("false");
        when(dbServerSettings.getPassword()).thenReturn("said1995");
        when(dbServerSettings.getUsername()).thenReturn("root");

        DBService dbService = new DBServiceImpl(dbServerSettings);
        accountService = new AccountServiceMySQLImpl(dbService);
        accountService.addUser(testUser.getLogin(), testUser);
    }

    @Test
    public void testAddUser() throws Exception {
        final UserProfile user = accountService.getUser(testUser.getLogin());

        assertNotNull(user);
        assertEquals(testUser.getLogin(), user.getLogin());
        assertEquals(testUser.getPassword(), user.getPassword());
        assertEquals(testUser.getEmail(), user.getEmail());
    }

    @Test
    public void testAddSessions() throws Exception {
        accountService.addSessions("testSessionID", testUser);

        assertEquals(accountService.getCountOnlineUsers(), 1);
    }

    @Test
    public void testDeleteSession() throws Exception {
        accountService.addSessions("testSessionID", testUser);
        accountService.deleteSession("testSessionID");

        assertEquals(accountService.getCountOnlineUsers(), 0);
    }

    @Test
    public void testGetUser() throws Exception {

        assertEquals(testUser.getLogin(), accountService.getUser(testUser.getLogin()).getLogin());
    }

    @Test
    public void testGetSessions() throws Exception {
        accountService.addSessions("testSessionID", testUser);

        assertEquals(accountService.getSessions("testSessionID"), testUser);
    }

    @Test
    public void testGetCountUsers() throws Exception {

        assertEquals(accountService.getCountUsers(), 1);
    }

    @Test
    public void testGetCountOnlineUsers() throws Exception {
        accountService.addSessions("testSessionID", testUser);

        assertEquals(accountService.getCountOnlineUsers(), 1);
    }
}