package TicTacToe.models;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private int size;
    private List<List<Cell>> cells;

    // Constructor
    public Board(int size){
        this.size = size;
        cells = new ArrayList<>();
        for(int i=0; i<size; i++){
            cells.add(new ArrayList<>());
            for(int j=0; j<size; j++){
                cells.get(i).add(new Cell(i, j));
            }
        }
    }

    // Methods
    public void display(){
        for(int i=0; i<size; i++){
            System.out.print("[");
            for(int j=0; j<size; j++){
                Cell cell =  cells.get(i).get(j);
                System.out.print("("+cell.getRow() + ","+ cell.getCol()+").Cell state is: "+cell.getCellState()+". ");
            }
            System.out.println("]");
        }
    }

    // Getters
    public int getSize() {
        return this.size;
    }
    public List<List<Cell>> getCells(){
        return this.cells;
    }

    // Setters
    public void setSize(int size){
        this.size = size;
    }
    public void setCells(List<List<Cell>> cells){
        this.cells = cells;
    }
}
