package gameMechanics;

import main.user.UserProfile;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by said on 31.10.15.
 */
public class GameSessionTest {
    private GameSession gameSession;
    private UserProfile testUser1 = new UserProfile("test_user1", "test_pass1", "test1@mail.ru");
    private UserProfile testUser2 = new UserProfile("test_user2", "test_pass2", "test2@mail.ru");

    @Before
    public void setUp() {
        gameSession = new GameSession(testUser1.getLogin(), testUser2.getLogin());
    }

    @Test
    public void testGetEnemy() throws Exception {
        assertEquals(testUser2.getLogin(), gameSession.getEnemy(testUser1.getLogin()).getMyName());
        assertEquals(testUser1.getLogin(), gameSession.getEnemy(testUser2.getLogin()).getMyName());
    }

    @Test
    public void testGetSelf() throws Exception {
        assertEquals(testUser1.getLogin(), gameSession.getSelf(testUser1.getLogin()).getMyName());
        assertEquals(testUser2.getLogin(), gameSession.getSelf(testUser2.getLogin()).getMyName());
    }

    @Test
    public void testGetFirst() throws Exception {
        assertEquals(testUser1.getLogin(), gameSession.getFirst().getMyName());
        assertEquals(testUser2.getLogin(), gameSession.getFirst().getEnemyName());
    }

    @Test
    public void testFirstWinner() throws Exception {
        gameSession.getFirst().incrementMyScore();
        gameSession.determineWinner();

        assertEquals(1, gameSession.getFirst().getGameState());
        assertEquals(2, gameSession.getSecond().getGameState());
    }

    @Test
    public void testSecondWinner() throws Exception {
        gameSession.getSecond().incrementMyScore();
        gameSession.determineWinner();

        assertEquals(1, gameSession.getSecond().getGameState());
        assertEquals(2, gameSession.getFirst().getGameState());
    }

    @Test
    public void testDeadHeat() {
        gameSession.getFirst().incrementMyScore();
        gameSession.getSecond().incrementMyScore();
        gameSession.determineWinner();

        assertEquals(gameSession.getSecond().getGameState(), gameSession.getFirst().getGameState());
    }

    @Test
    public void testGetSecond() throws Exception {
        assertEquals(testUser2.getLogin(), gameSession.getSecond().getMyName());
        assertEquals(testUser1.getLogin(), gameSession.getSecond().getEnemyName());
    }

    @Test
    public void testFinishGame() {
        gameSession.determineWinner();

        assertTrue(gameSession.isFinished());
    }
}