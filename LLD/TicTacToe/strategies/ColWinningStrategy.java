package TicTacToe.strategies;

import TicTacToe.models.Move;

import java.util.HashMap;

public class ColWinningStrategy implements WinningStrategy{
    int size;
    HashMap<String, Integer> colMap[];
    // Constructor
    public ColWinningStrategy(int size){
        this.size = size;
        colMap = new HashMap[size];
        for(int i=0; i<size; i++){
            colMap[i] = new HashMap<>();
        }
    }

    public boolean checkWinner(Move move){
        int col = move.getCell().getCol();
        HashMap<String, Integer> currentMap = colMap[col];

        String currentSymbol = move.getPlayer().getSymbol().getSymbolName();
        if(!currentMap.containsKey(currentSymbol)){
            currentMap.put(currentSymbol, 1);
        }else{
            currentMap.put(currentSymbol, currentMap.get(currentSymbol)+1);
        }
        if(currentMap.get(currentSymbol) == size){
            return true;
        }
        return false;
    }
}
