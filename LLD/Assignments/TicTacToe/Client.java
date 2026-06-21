package TicTacToe;

import TicTacToe.controllers.GameController;
import TicTacToe.models.*;
import TicTacToe.models.enums.GameState;
import TicTacToe.models.enums.PlayerType;
import TicTacToe.strategies.RowWinningStrategy;
import TicTacToe.strategies.WinningStrategy;

import java.util.ArrayList;
import java.util.List;

public class Client {
    public static void main(String[] args){
        System.out.println("-----------------------------------------------------------------------------------");
        GameController gameController = new GameController();

        int size = 4;
        List<Player> players = new ArrayList<>();
        players.add(new HumanPlayer(1,"Parakh",new Symbol("C"),27));
        players.add(new HumanPlayer(1,"Vegeta",new Symbol("O"),27));
//        players.add(new BotPlayer(1,"Botty",new Symbol("X"), BotDifficulty.EASY));
//        players.add(new BotPlayer(1,"Botty",new Symbol("X"), BotDifficulty.EASY));
        List<WinningStrategy> winningStrategies = new ArrayList<>();
        winningStrategies.add(new RowWinningStrategy(size));
        Game game = gameController.startGame(size,players,winningStrategies);

        while(gameController.getGameState(game).equals(GameState.IN_PROGRESS)) {
            // display
            gameController.display(game);
            // make moves
            gameController.makeMove(game);

            // presses
        }

        if(gameController.getGameState(game).equals(GameState.COMPLETED)) {
            Player winner = gameController.getWinner(game);
            System.out.println(winner.getName() + "has WON !!");
        }
        else {
            gameController.display(game);
            System.out.println("Game Drawn");
        }


    }
}
