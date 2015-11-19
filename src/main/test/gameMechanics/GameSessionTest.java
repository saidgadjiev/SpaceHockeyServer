package gameMechanics;

import main.gameService.Player;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
  Created by said on 31.10.15.
 */

public class GameSessionTest {
    private GameSession gameSession;
    private Player testPlayer1 = new Player("test_user1");
    private Player testPlayer2 = new Player("test_user2");

    @Before
    public void setUp() {
        testPlayer1.setMyPosition(1);
        testPlayer2.setMyPosition(2);
        gameSession = new GameSession(testPlayer1, testPlayer2);
    }

    @Test
    public void testGetEnemyPlayer() throws Exception {
        assertEquals(testPlayer2.getName(), gameSession.getEnemyPlayer(testPlayer1.getMyPosition()).getName());
        assertEquals(testPlayer1.getName(), gameSession.getEnemyPlayer(testPlayer2.getMyPosition()).getName());
    }

    @Test
    public void testGetSelf() throws Exception {
        assertEquals(testPlayer1.getName(), gameSession.getSelfPlayer(testPlayer1.getMyPosition()).getName());
        assertEquals(testPlayer2.getName(), gameSession.getSelfPlayer(testPlayer2.getMyPosition()).getName());
    }

    @Test
    public void testGetFirst() throws Exception {
        assertEquals(testPlayer1, gameSession.getFirstPlayer());
    }

    @Test
    public void testFirstWinner() throws Exception {
        gameSession.getFirstPlayer().incrementScore();
        gameSession.determineWinner();

        assertEquals(1, gameSession.getFirstPlayer().getResultStatus());
        assertEquals(2, gameSession.getSecondPlayer().getResultStatus());
    }

    @Test
    public void testSecondWinner() throws Exception {
        gameSession.getSecondPlayer().incrementScore();
        gameSession.determineWinner();

        assertEquals(1, gameSession.getSecondPlayer().getResultStatus());
        assertEquals(2, gameSession.getFirstPlayer().getResultStatus());
    }

    @Test
    public void testDeadHeat() {
        gameSession.getFirstPlayer().incrementScore();
        gameSession.getSecondPlayer().incrementScore();
        gameSession.determineWinner();

        assertEquals(gameSession.getSecondPlayer().getResultStatus(), gameSession.getFirstPlayer().getResultStatus());
    }

    @Test
    public void testGetSecond() throws Exception {
        assertEquals(testPlayer2.getName(), gameSession.getSecondPlayer().getName());
    }

    @Test
    public void testFinishGame() {
        gameSession.determineWinner();

        assertTrue(gameSession.isFinished());
    }
}