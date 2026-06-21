package TicTacToe.models;

import TicTacToe.models.enums.PlayerType;

import java.util.Scanner;

public class HumanPlayer extends Player{
    int age;
    Scanner sc = new Scanner(System.in);

    // Constructor
    public HumanPlayer(int id, String name, Symbol symbol, int age){
        super(id, name, symbol, PlayerType.HUMAN);
        this.age = age;
    }

    // abstract method impl
    public Move makeMove(Board board){
        System.out.println("Enter row and col of Cell : ");
        int row = sc.nextInt();
        int col = sc.nextInt();
        Cell cell = new Cell(row,col);
        return new Move(this, cell);
    }
}
