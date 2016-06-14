package main.accountService;

import main.user.UserProfile;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by said on 09.10.15.
 */

public class AccountServiceImplTest {
    private final AccountServiceImpl accountService = new AccountServiceImpl();
    private final UserProfile testUser = new UserProfile("testLogin", "testPassword", "said@mail.ru");

    @Test
    public void testAddUser() throws Exception {
        accountService.addUser(testUser.getLogin(), testUser);

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
        accountService.addUser(testUser.getLogin(), testUser);

        assertEquals(testUser, accountService.getUser(testUser.getLogin()));
    }

    @Test
    public void testGetSessions() throws Exception {
        accountService.addSessions("testSessionID", testUser);

        assertEquals(accountService.getSessions("testSessionID"), testUser);
    }

    @Test
    public void testGetCountUsers() throws Exception {
        accountService.addUser(testUser.getLogin(), testUser);

        assertEquals(accountService.getCountUsers(), 1);
    }

    @Test
    public void testGetCountOnlineUsers() throws Exception {
        accountService.addSessions("testSessionID", testUser);

        assertEquals(accountService.getCountOnlineUsers(), 1);
    }

}