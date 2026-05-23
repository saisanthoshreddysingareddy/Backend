package TicTacToe.controllers;

import TicTacToe.models.Board;
import TicTacToe.models.Cell;
import TicTacToe.models.Game;
import TicTacToe.models.Player;
import TicTacToe.models.enums.GameState;
import TicTacToe.strategies.WinningStrategy;

import java.util.List;

public class GameController {
    public Game startGame(int size,
                          List<Player> players,
                          List<WinningStrategy> winningStrategies) {
        return Game.getBuilder()
                .setSize(size)
                .setWinningStrategies(winningStrategies)
                .setPlayers(players)
                .build();
    }

    public GameState getGameState(Game game) {
        return game.getGameState();
    }

    public void display(Game game){
        game.getBoard().display();
    }

    public Player getWinner(Game game) {
        return game.getWinner();
    }

    public void makeMove(Game game){
        game.makeMove();
    }
}
