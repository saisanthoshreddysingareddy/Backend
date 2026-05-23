package TicTacToe.models;

import TicTacToe.models.enums.PlayerType;

public abstract class Player {
    private int id;
    private String name;
    private Symbol symbol;
    private PlayerType playerType;

    // Constructor
    public Player(int id, String name, Symbol symbol, PlayerType playerType){
        this.id = id;
        this.name = name;
        this.symbol = symbol;
        this.playerType = playerType;
    }

    // abstract method
    public abstract Move makeMove(Board board);

    // Getters
    public int getId(){
        return this.id;
    }
    public String getName(){
        return this.name;
    }
    public Symbol getSymbol(){
        return this.symbol;
    }
    public PlayerType getPlayerType(){
        return this.playerType;
    }

    // Setters
    public void setId(int id){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setSymbol(Symbol symbol){
        this.symbol = symbol;
    }
    public void setPlayerType(PlayerType playerType){
        this.playerType = playerType;
    }
}
