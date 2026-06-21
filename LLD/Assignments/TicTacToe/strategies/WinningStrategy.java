package TicTacToe.strategies;

import TicTacToe.models.Move;

public interface WinningStrategy {
    public boolean checkWinner(Move move);
//    void handleUndo(Move move);
}
