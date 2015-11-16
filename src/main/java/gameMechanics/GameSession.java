package gameMechanics;

import gameMechanics.game.Direction;
import gameMechanics.game.Platform;
import gameMechanics.game.Position;
import main.gameService.Game;
import main.gameService.Player;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by said on 20.10.15.
 */

public class GameSession {
    private final long startTime;
    private final Game first;
    private final Game second;
    private State sessionState = State.PLAY;
    private enum State { PLAY, FINISH }

    private Map<Player, Game> players = new HashMap<>();

    public GameSession(Player player1, Player player2) {
        startTime = new Date().getTime();
        player1.setPlatform(new Platform(new Position(235, 80), Direction.STOP, 4));
        player2.setPlatform(new Platform(new Position(235, 610), Direction.STOP, 4));
        Game game1 = new Game(player1);
        game1.setEnemyPlayer(player2);

        Game game2 = new Game(player2);
        game2.setEnemyPlayer(player1);

        players.put(player1, game1);
        players.put(player2, game2);

        this.first = game1;
        this.second = game2;
    }

    public Game getEnemyPlayer(Player player) {
        Player enemyPlayer = players.get(player).getEnemyPlayer();
        return players.get(enemyPlayer);
    }

    public Game getSelfPlayer(Player player) {
        return players.get(player);
    }

    public long getSessionTime(){
        return new Date().getTime() - startTime;
    }

    public Game getFirstPlayer() {
        return first;
    }

    public void determineWinner() {
        if (first.getMyScore() > second.getMyScore()) {
            first.setGameState(1);
            second.setGameState(2);
        }  else if (first.getMyScore() < second.getMyScore()) {
            first.setGameState(2);
            second.setGameState(1);
        } else {
            first.setGameState(0);
            second.setGameState(0);
        }
        sessionState = State.FINISH;
    }

    public boolean isFinished() {
        return sessionState == State.FINISH;
    }

    public Game getSecond() {
        return second;
    }
}
