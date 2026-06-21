package TicTacToe.strategies;

import TicTacToe.models.Move;

import java.util.HashMap;

public class RowWinningStrategy implements WinningStrategy{
    int size;
    private HashMap<String, Integer> rowCount[];

    // Constructor method
    public RowWinningStrategy(int size){
        this.size = size;
        rowCount = new HashMap[size];
        for(int i=0; i<size; i++){
            rowCount[i] = new HashMap<>();
        }
    }

    // Interface method
    public boolean checkWinner(Move move){
        int row = move.getCell().getRow();
        HashMap<String, Integer> currentMap = rowCount[row];
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
