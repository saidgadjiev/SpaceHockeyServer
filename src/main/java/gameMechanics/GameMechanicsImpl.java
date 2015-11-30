package gameMechanics;

import frontend.transport.TransportSystem;
import gameMechanics.game.Direction;
import main.accountService.AccountService;
import main.gameService.GameMechanics;
import main.gameService.GamePosition;
import main.gameService.Player;
import main.gameService.WebSocketService;
import main.user.UserProfile;
import resource.GameMechanicsSettings;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
  Created by said on 20.10.15.
 */

@SuppressWarnings("SpellCheckingInspection")
public class GameMechanicsImpl implements GameMechanics {
    private final int stepTime;
    private final int gameTime;
    private AccountService accountService;
    private WebSocketService webSocketService;
    private TransportSystem transportSystem;
    private Map<Player, GameSession> playerToGame = new HashMap<>();
    private List<GameSession> allSessions = new LinkedList<>();
    private ConcurrentLinkedQueue<Player> waiters = new ConcurrentLinkedQueue<>();

    public GameMechanicsImpl(AccountService accountService, WebSocketService webSocketService, TransportSystem transportSystem, GameMechanicsSettings gameMechanicsSettings) {
        this.accountService = accountService;
        this.webSocketService = webSocketService;
        this.transportSystem = transportSystem;
        this.stepTime = gameMechanicsSettings.getStepTime() / 60;
        this.gameTime = gameMechanicsSettings.getGameTime();
    }

    public GameMechanicsImpl(WebSocketService webSocketService) {
        this.webSocketService = webSocketService;
        this.stepTime = 1000 / 60;
        this.gameTime = 10000;
    }

    public void addPlayer(Player player) {
        waiters.add(player);
    }

    public void incrementScore(Player myPlayer) {
        myPlayer.incrementScore();
        transportSystem.syncScore(playerToGame.get(myPlayer));
    }

    public void changePlatformDirection(Player player, Direction direction) {
        player.getPlatform().setDirection(direction);
    }

    @SuppressWarnings("InfiniteLoopStatement")
    public void run() {
        int fpsUpdate = 60;
        double timePerTickUpdate = 1000000000 / fpsUpdate;
        double deltaUpdate = 0;
        long now;
        long lastTime = System.nanoTime();

        while (true) {
            now = System.nanoTime();
            deltaUpdate += (now - lastTime) / timePerTickUpdate;
            lastTime = now;
            createGame();
            if (deltaUpdate >= 1) {
                makeStep();
                gmStep();
                deltaUpdate--;
            }
        }
    }

    public void createGame() {
        while (waiters.size() > 1) {
            Player first = waiters.poll();
            Player second = waiters.poll();

            final GamePosition myPosition = GamePosition.UPPER;
            final GamePosition enemyPosition = GamePosition.LOWER;
            first.setMyPosition(myPosition);
            second.setMyPosition(enemyPosition);

            starGame(first, second);
        }
    }

    private void gmStep() {
        for (GameSession session : allSessions) {
            if (!session.isFinished()) {
                session.sessionStep();
                if (session.getSessionStep() == 2) {
                    transportSystem.syncGameWorld(session);
                    session.setStepCountZero();
                }
                //if (session.getSessionTime() > gameTime) {
                //    finishGame(session);
                //}
            }
        }
    }

    private void makeStep() {
        for (GameSession session : allSessions) {
            if (!session.isFinished()) {
                session.makeStep();
            }
        }
    }

    private void freeResource(GameSession session) {
        Player firstPlayer = session.getFirstPlayer();
        Player secondPlayer = session.getSecondPlayer();

        playerToGame.remove(firstPlayer);
        playerToGame.remove(secondPlayer);
        allSessions.remove(session);
        transportSystem.removeWebSocket(session);
        webSocketService.removeWebSocket(firstPlayer);
        webSocketService.removeWebSocket(secondPlayer);
    }

    private void finishGame(GameSession session) {
        Player firstPlayer = session.getFirstPlayer();
        Player secondPlayer = session.getSecondPlayer();

        session.determineWinner();
        transportSystem.gameOver(session);
        switch (session.getResultState()) {
            case FIRST_WIN:
                UserProfile firstProfile = accountService.getUser(firstPlayer.getName());
                firstProfile.incrementScore();
                accountService.updateUser(firstProfile);
                break;
            case SECOND_WIN:
                UserProfile secondProfile = accountService.getUser(secondPlayer.getName());
                secondProfile.incrementScore();
                accountService.updateUser(secondProfile);
                break;
            default:
                break;
        }
        freeResource(session);
    }

    private void starGame(Player firstPlayer, Player secondPlayer) {
        GameSession gameSession = new GameSession(firstPlayer, secondPlayer);
        allSessions.add(gameSession);
        playerToGame.put(firstPlayer, gameSession);
        playerToGame.put(secondPlayer, gameSession);

        transportSystem.startGame(gameSession);
    }

    public List<GameSession> getAllSessions() {
        return allSessions;
    }
}
