package tyco.tictactoe.players;

import tyco.tictactoe.board.TicTacToeBoard;

public abstract class Player {
    protected final TicTacToeBoard board;
    protected final Piece piece;

    public Player(Piece piece, TicTacToeBoard board) {
        this.piece = piece;
        this.board = board;
    }

    public abstract int[] getNextMove();

    public TicTacToeBoard.State stateFromPiece() {
        switch (piece) {
            case X:
                return TicTacToeBoard.State.X;
            default:
                return TicTacToeBoard.State.O;
        }
    }

    public TicTacToeBoard.State stateFromOpposingPiece() {
        switch (piece) {
            case X:
                return TicTacToeBoard.State.O;
            default:
                return TicTacToeBoard.State.X;
        }
    }

    public enum Piece {
        X,
        O
    }

}
