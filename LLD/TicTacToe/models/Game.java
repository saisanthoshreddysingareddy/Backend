package TicTacToe.models;

import TicTacToe.exceptions.InvalidPlayerException;
import TicTacToe.models.enums.CellState;
import TicTacToe.models.enums.GameState;
import TicTacToe.strategies.WinningStrategy;

import java.util.ArrayList;
import java.util.List;

public class Game {
    Board board;
    List<Player> players;
    GameState gameState;
    Player winner;
    List<WinningStrategy> winningStrategies;
    List<Move> moves;
    int nextPlayerMove;

    // Constructor
    public Game(int size, List<Player> players, List<WinningStrategy> winningStrategies) {
        this.board = new Board(size);
        this.players = players;
        this.gameState = GameState.IN_PROGRESS;
        this.nextPlayerMove = 0;
        this.moves = new ArrayList<>();
        this.winningStrategies = winningStrategies;
    }

    // methods
    public void makeMove(){
        Player currPlayer = players.get(nextPlayerMove);
        System.out.println("It's "+ currPlayer.getName()+ " turn");
        Move move = currPlayer.makeMove(board);

        int row = move.getCell().getRow();
        int col = move.getCell().getCol();
        Cell cell = board.getCells().get(row).get(col);
        cell.setPlayer(currPlayer);
        cell.setCellState(CellState.FILLED);
        moves.add(move);

        nextPlayerMove = (nextPlayerMove+1)%players.size();
        if(checkWinner(move)){
            this.winner = currPlayer;
            this.gameState = GameState.COMPLETED;
        }else if(moves.size() == board.getSize()*board.getSize()){
            this.gameState = GameState.DRAW;
        }
    }

    public void undo(){
        if(moves.isEmpty()){
            return;
        }
        Move lastMove = moves.remove(moves.size()-1);

        int row = lastMove.getCell().getRow();
        int col = lastMove.getCell().getCol();

        Cell actualCell = board.getCells().get(row).get(col);

        actualCell.setCellState(CellState.EMPTY);
        actualCell.setPlayer(null);
        this.nextPlayerMove = (nextPlayerMove - 1 + players.size()) % players.size();
        winner = null;
        gameState = GameState.IN_PROGRESS;

        for(WinningStrategy ws : winningStrategies){
            ws.handleUndo(lastMove);
        }
    }

    public static Builder getBuilder() {
        return new Builder();
    }

    public static class Builder {
        int size;
        List<Player> players;
        List<WinningStrategy> winningStrategies;

        public Builder setSize(int size) {
            this.size = size;
            return this;
        }

        public Builder setPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        public Builder setWinningStrategies(List<WinningStrategy> winningStrategies) {
            this.winningStrategies = winningStrategies;
            return this;
        }

        void validate() {
            validateNumOfplayers();
//            valdiateBotCount();
//            validateUniquePlayerSymbols();
        }

        private void validateNumOfplayers() {
            if(players.size()!=size-1) {
                throw new InvalidPlayerException("Player count should be less than"+size+"-1");
            }
        }

        public Game build() {
            validate();
            return new Game(size,players,winningStrategies);
        }
    }


    public boolean checkWinner(Move move){
        for(WinningStrategy winningStrategy : winningStrategies){
            if(winningStrategy.checkWinner(move)){
                return true;
            }
        }
        return false;
    }

    // Getters
    public Board getBoard() {
        return board;
    }
    public List<Player> getPlayers() {
        return players;
    }
    public GameState getGameState() {
        return gameState;
    }
    public Player getWinner() {
        return winner;
    }
    public List<WinningStrategy> getWinningStrategy() {
        return this.winningStrategies;
    }
    public List<Move> getMoves() {
        return moves;
    }
    public int getNextPlayerMove() {
        return nextPlayerMove;
    }

    // Setters
    public void setBoard(Board board) {
        this.board = board;
    }
    public void setPlayers(List<Player> players) {
        this.players = players;
    }
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
    public void setWinner(Player winner) {
        this.winner = winner;
    }
    public void setWinningStrategy(List<WinningStrategy> winningStrategies) {
        this.winningStrategies = winningStrategies;
    }
    public void setMoves(List<Move> moves) {
        this.moves = moves;
    }
    public void setNextPlayerMove(int nextPlayerMove) {
        this.nextPlayerMove = nextPlayerMove;
    }
}
