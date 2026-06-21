package TicTacToe.models;

import TicTacToe.models.enums.BotDifficulty;
import TicTacToe.models.enums.PlayerType;
import TicTacToe.strategies.BotPlayingStrategy;

public class BotPlayer extends Player{
    private BotDifficulty botDifficulty;
    private BotPlayingStrategy botPlayingStrategy;

    public BotPlayer(int id, String name, Symbol symbol){
        super(id, name,symbol, PlayerType.BOT);
        this.botDifficulty = botDifficulty;
//        this.botPlayingStrategy = BotPlayingStrategyFactory.getStrategy(botDifficulty);
    }

    public Move makeMove(Board board){
        System.out.println("Its Bot moving strategy");
        return null;
    }
}
