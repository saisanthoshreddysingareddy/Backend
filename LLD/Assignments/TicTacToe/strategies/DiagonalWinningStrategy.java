package TicTacToe.strategies;

import TicTacToe.models.Move;

import java.util.HashMap;

public class DiagonalWinningStrategy implements WinningStrategy{
    HashMap<String, Integer> mainDiagonalMap;
    HashMap<String, Integer> antiDiagonalMap;
    int size;
    // Constructor
    public DiagonalWinningStrategy(int size){
        this.size = size;
        mainDiagonalMap = new HashMap<>();
        antiDiagonalMap = new HashMap<>();
    }
    // Interface method
    public boolean checkWinner(Move move){
        int row = move.getCell().getRow();
        int col = move.getCell().getCol();
        String symbolName = move.getPlayer().getSymbol().getSymbolName();
        if(row == col){
            if(!mainDiagonalMap.containsKey(symbolName)){
                mainDiagonalMap.put(symbolName, 1);
            }else{
                mainDiagonalMap.put(symbolName, mainDiagonalMap.get(symbolName)+1);
            }
            if(mainDiagonalMap.get(symbolName) == size){
                return true;
            }
        }
        if(row+col == size-1){
            if(!antiDiagonalMap.containsKey(symbolName)){
                antiDiagonalMap.put(symbolName,1);
            }else{
                antiDiagonalMap.put(symbolName, antiDiagonalMap.get(symbolName)+1);
            }
            if(antiDiagonalMap.get(symbolName) == size){
                return true;
            }
        }

        return false;

    }

}
