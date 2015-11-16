package gameMechanics;

import main.gameService.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by said on 31.10.15.
 */
public class GameSessionTest {
    private GameSession gameSession;
    private Player testPlayer1 = new Player("test_user1");
    private Player testPlayer2 = new Player("test_user2");

    @Before
    public void setUp() {
        gameSession = new GameSession(testPlayer1, testPlayer2);
    }

    @Test
    public void testGetEnemy() throws Exception {
        assertEquals(testPlayer2.getName(), gameSession.getEnemyPlayer(testPlayer1).getMyPlayerName());
        assertEquals(testPlayer2.getName(), gameSession.getEnemyPlayer(testPlayer2).getMyPlayerName());
    }

    @Test
    public void testGetSelf() throws Exception {
        assertEquals(testPlayer1.getName(), gameSession.getSelfPlayer(testPlayer1).getMyPlayerName());
        assertEquals(testPlayer2.getName(), gameSession.getSelfPlayer(testPlayer2).getMyPlayerName());
    }

    @Test
    public void testGetFirst() throws Exception {
        assertEquals(testPlayer1.getName(), gameSession.getFirstPlayer().getMyPlayerName());
        assertEquals(testPlayer2.getName(), gameSession.getFirstPlayer().getEnemyPlayerName());
    }

    @Test
    public void testFirstWinner() throws Exception {
        gameSession.getFirstPlayer().incrementMyScore();
        gameSession.determineWinner();

        assertEquals(1, gameSession.getFirstPlayer().getGameState());
        assertEquals(2, gameSession.getSecond().getGameState());
    }

    @Test
    public void testSecondWinner() throws Exception {
        gameSession.getSecond().incrementMyScore();
        gameSession.determineWinner();

        assertEquals(1, gameSession.getSecond().getGameState());
        assertEquals(2, gameSession.getFirstPlayer().getGameState());
    }

    @Test
    public void testDeadHeat() {
        gameSession.getFirstPlayer().incrementMyScore();
        gameSession.getSecond().incrementMyScore();
        gameSession.determineWinner();

        assertEquals(gameSession.getSecond().getGameState(), gameSession.getFirstPlayer().getGameState());
    }

    @Test
    public void testGetSecond() throws Exception {
        assertEquals(testPlayer2.getName(), gameSession.getSecond().getMyPlayerName());
        assertEquals(testPlayer1.getName(), gameSession.getSecond().getEnemyPlayerName());
    }

    @Test
    public void testFinishGame() {
        gameSession.determineWinner();

        assertTrue(gameSession.isFinished());
    }
}