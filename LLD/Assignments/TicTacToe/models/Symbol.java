package TicTacToe.models;

public class Symbol {
    private String symbolName;

    // Constructor
    public Symbol(String symbolName){
        this.symbolName = symbolName;
    }

    // getters
    public String getSymbolName(){
        return this.symbolName;
    }

    // setters
    public void setSymbolName(String symbolName){
        this.symbolName = symbolName;
    }
}
