package tyco.tictactoe.players;

import tyco.tictactoe.board.TicTacToeBoard;

import java.util.SortedMap;
import java.util.TreeMap;

public class AIPlayer extends Player {
    public AIPlayer(Piece piece, TicTacToeBoard board) {
        super(piece, board);
    }

    public int[] getNextMove() {
        int baseScore = 9 - board.filledSpaces() + 1;
        int bound = baseScore + 1;
        return minmax(this.board, 0, -1 * bound, bound, baseScore).move;
    }

    private ScoreMovePair minmax(TicTacToeBoard board, int depth, int lower_bound, int upper_bound, int baseScore) {
        if (board.isWinOrDraw()) {
            return evaluate(board, depth, baseScore);
        }
        boolean isXnext = board.filledSpaces() % 2 == 0;
        boolean amINext = (isXnext && piece == Piece.X) || (!isXnext && piece == Piece.O);
        SortedMap<Integer, ScoreMovePair> pairs = new TreeMap<Integer, ScoreMovePair>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board.getState(i, j) == TicTacToeBoard.State.EMPTY) {
                    TicTacToeBoard new_board_state = board.newBoardState();
                    new_board_state.place(amINext ? stateFromPiece() : stateFromOpposingPiece(), i, j);
                    ScoreMovePair pair = minmax(new_board_state, depth + 1, lower_bound, upper_bound, baseScore);
                    pair.move = new int[]{i, j};
                    if (amINext) {
                        pairs.put(pair.score, pair);
                        if (pair.score > lower_bound) lower_bound = pair.score;
                    } else {
                        if (pair.score < upper_bound) upper_bound = pair.score;
                    }
                    if (upper_bound < lower_bound)
                        break;
                }
            }
            if (upper_bound < lower_bound)
                break;
        }
        if (amINext) {
            int[] nextMove = pairs.get(pairs.lastKey()).move;
            return new ScoreMovePair(lower_bound, nextMove);
        }
        return new ScoreMovePair(upper_bound, null);
    }

    private ScoreMovePair evaluate(TicTacToeBoard board, int depth, int baseScore) {
        if (board.getWinner() == stateFromPiece())
            return new ScoreMovePair(baseScore - depth, null);
        else if (board.getWinner() == stateFromOpposingPiece())
            return new ScoreMovePair(depth - baseScore, null);
        return new ScoreMovePair(0, null);
    }

    private class ScoreMovePair {
        public final int score;
        public int[] move;

        public ScoreMovePair(int score, int[] move) {
            this.score = score;
            this.move = move;
        }
    }
}
