package tyco.tictactoe.board;

public class TicTacToeBoard {
    private final State[][] spaces;
    private int filledSpaces = 0;

    public TicTacToeBoard() {
        spaces = new State[3][3];
        for (int i = 0; i < spaces.length; i++) {
            for (int j = 0; j < spaces[i].length; j++) {
                spaces[i][j] = State.EMPTY;
            }
        }
    }

    public boolean isDraw() {
        return filledSpaces == 9 && !isWin();
    }

    private boolean isWin() {
        return filledSpaces >= 5 && getWinner() != State.EMPTY;
    }

    public State getWinner() {
        State winningDiagonal = getWinningDiagonal();
        if (winningDiagonal != State.EMPTY) {
            return winningDiagonal;
        }
        State winningReverseDiagonal = getWinningReverseDiagonal();
        if (winningReverseDiagonal != State.EMPTY) {
            return winningReverseDiagonal;
        }
        State winningRow = getWinningRow();
        if (winningRow != State.EMPTY) {
            return winningRow;
        }
        State winningColumn = getWinningColumn();
        if (winningColumn != State.EMPTY) {
            return winningColumn;
        }
        return State.EMPTY;
    }

    private State getWinningRow() {
        for (int i = 0; i < 3; i++) {
            State state = spaces[i][0];
            if (state != State.EMPTY && state == spaces[i][1] && state == spaces[i][2]) {
                return state;
            }
        }
        return State.EMPTY;
    }

    private State getWinningColumn() {
        for (int i = 0; i < 3; i++) {
            State state = spaces[0][i];
            if (state != State.EMPTY && state == spaces[1][i] && state == spaces[2][i]) {
                return state;
            }
        }
        return State.EMPTY;
    }

    private State getWinningDiagonal() {
        State state = spaces[0][0];
        if (state != State.EMPTY && state == spaces[1][1] && state == spaces[2][2]) {
            return state;
        }
        return State.EMPTY;
    }

    private State getWinningReverseDiagonal() {
        State state = spaces[2][0];
        if (state != State.EMPTY && state == spaces[1][1] && state == spaces[0][2]) {
            return state;
        }
        return State.EMPTY;
    }

    public boolean isWinOrDraw() {
        return isDraw() || isWin();
    }

    public int filledSpaces() {
        return filledSpaces;
    }

    public void place(State state, int x, int y) {
        if (spaces[x][y] == State.EMPTY) {
            spaces[x][y] = state;
            filledSpaces++;
        }
    }

    public State getState(int x, int y) {
        return spaces[x][y];
    }

    public TicTacToeBoard newBoardState() {
        TicTacToeBoard board = new TicTacToeBoard();
        for (int i = 0; i < 3; i++) {
            System.arraycopy(spaces[i], 0, board.spaces[i], 0, 3);
        }
        board.filledSpaces = filledSpaces;
        return board;

    }

    public enum State {
        EMPTY(""),
        X("X"),
        O("O");

        private final String text;

        private State(String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }
}
