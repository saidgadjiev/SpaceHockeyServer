package gameMechanics;

import main.gameService.GamePosition;
import main.gameService.GameResultState;
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
        final GamePosition myPosition = GamePosition.UPPER;
        final GamePosition enemyPosition = myPosition.getOposite();

        testPlayer1.setMyPosition(myPosition);
        testPlayer2.setMyPosition(enemyPosition);
        gameSession = new GameSession(testPlayer1, testPlayer2);
    }

    @Test
    public void testGetEnemyPlayer() throws Exception {
        assertEquals(testPlayer2.getName(), gameSession.getEnemyPlayer(testPlayer1.getMyPosition()).getName());
        assertEquals(testPlayer1.getName(), gameSession.getEnemyPlayer(testPlayer2.getMyPosition()).getName());
    }

    @Test
    public void testGetFirst() throws Exception {
        assertEquals(testPlayer1, gameSession.getFirstPlayer());
    }

    @Test
    public void testFirstWinner() throws Exception {
        gameSession.getFirstPlayer().incrementScore();
        gameSession.determineWinner();

        assertEquals(GameResultState.FIRST_WIN, gameSession.getResultState());
    }

    @Test
    public void testSecondWinner() throws Exception {
        gameSession.getSecondPlayer().incrementScore();
        gameSession.determineWinner();

        assertEquals(GameResultState.SECOND_WIN, gameSession.getResultState());
    }

    @Test
    public void testDeadHeat() {
        gameSession.getFirstPlayer().incrementScore();
        gameSession.getSecondPlayer().incrementScore();
        gameSession.determineWinner();

        assertEquals(GameResultState.DEAD_HEAT, gameSession.getResultState());
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