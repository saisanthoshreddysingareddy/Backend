package TicTacToe.models;

public class Move {
    Player player;
    Cell cell;

    // Constructor
    public Move(Player player, Cell cell){
        this.player = player;
        this.cell = cell;
    }

    // Getters
    public Player getPlayer() {
        return this.player;
    }
    public Cell getCell(){
        return this.cell;
    }

    // Setters
    public void setPlayer(Player player){
        this.player = player;
    }
    public void setCell(Cell cell){
        this.cell = cell;
    }
}
