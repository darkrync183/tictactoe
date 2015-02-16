package tyco.tictactoe.players;

import tyco.tictactoe.board.TicTacToeBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class HumanPlayer extends Player {
    public HumanPlayer(Piece piece, TicTacToeBoard board) {
        super(piece, board);
    }

    public int[] getNextMove() {
        TicTacToeBoardDialog dialog = new TicTacToeBoardDialog(board);
        dialog.pack();
        while (dialog.getResponse() == null) {
            dialog.setVisible(true);
        }
        return dialog.getResponse();
    }

    private class TicTacToeBoardDialog extends JDialog implements ActionListener {

        private final TicTacToeBoard board;

        private final JButton[][] buttons = new JButton[3][3];
        private final HashMap<JButton, Coordinate> buttonMap = new HashMap<JButton, Coordinate>(9);
        public int[] response = null;

        public TicTacToeBoardDialog(TicTacToeBoard board) {
            super((Frame) null, true);
            this.board = board;
            setLayout(new GridLayout(3, 3));
            for (int i = 0; i < buttons.length; i++) {
                for (int j = 0; j < buttons[i].length; j++) {
                    buttons[i][j] = new JButton(board.getState(i, j).toString());
                    buttons[i][j].addActionListener(this);
                    buttonMap.put(buttons[i][j], new Coordinate(i, j));
                    add(buttons[i][j]);
                }
            }
        }

        public int[] getResponse() {
            return response;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            Object source = e.getSource();
            if (source instanceof JButton) {
                JButton button = (JButton) source;
                Coordinate coords = buttonMap.get(button);
                if (board.getState(coords.x, coords.y) == TicTacToeBoard.State.EMPTY) {
                    response = new int[]{coords.x, coords.y};
                    setVisible(false);
                }
            }
        }

        private class Coordinate {
            private final int x, y;

            public Coordinate(int x, int y) {
                this.x = x;
                this.y = y;
            }
        }
    }
}
