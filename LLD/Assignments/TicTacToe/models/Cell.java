package TicTacToe.models;

import TicTacToe.models.enums.CellState;

public class Cell {
    private int row;
    private int col;
    private CellState cellState;
    private Player player;

    // Constructor
    public Cell(int row, int col){
        this.row = row;
        this.col = col;
        this.cellState = CellState.EMPTY;
    }

    // Getters
    public int getRow(){
        return this.row;
    }
    public int getCol(){
        return this.col;
    }
    public CellState getCellState() {
        return this.cellState;
    }
    public Player getPlayer(){
        return this.player;
    }

    // Setters
    public void setRow(int row){
        this.row = row;
    }
    public void setCol(int col){
        this.col = col;
    }
    public void setCellState(CellState cellState){
        this.cellState = cellState;
    }
    public void setPlayer(Player player){
        this.player = player;
    }

}
