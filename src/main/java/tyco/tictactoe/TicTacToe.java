package tyco.tictactoe;

import tyco.tictactoe.board.TicTacToeBoard;
import tyco.tictactoe.players.AIPlayer;
import tyco.tictactoe.players.HumanPlayer;
import tyco.tictactoe.players.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToe {
    private final TicTacToeBoard board;
    private Player player1, player2;

    public TicTacToe() {
        board = new TicTacToeBoard();

    }

    public void pickGameType() {
        GameTypeDialog dialog = new GameTypeDialog();
        dialog.pack();
        while (dialog.getResponse() == null)
            dialog.setVisible(true);
        GameType type = dialog.getResponse();
        switch (type) {
            case HumanVsAI:
                player1 = new HumanPlayer(Player.Piece.X, board);
                player2 = new AIPlayer(Player.Piece.O, board);
                break;
            case HumanVsHuman:
                player1 = new HumanPlayer(Player.Piece.X, board);
                player2 = new HumanPlayer(Player.Piece.O, board);
                break;
            case AIVsHuman:
                player1 = new AIPlayer(Player.Piece.X, board);
                player2 = new HumanPlayer(Player.Piece.O, board);
                break;
        }
    }

    public void go() {
        for (int i = 0; i < Integer.MAX_VALUE && board.filledSpaces() < 9 && !board.isWinOrDraw(); i++) {
            Player currentPlayer = (i % 2) == 0 ? player1 : player2;
            int[] move = currentPlayer.getNextMove();
            board.place(currentPlayer.stateFromPiece(), move[0], move[1]);
        }
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel(new GridLayout(1, 1));
        String text;
        if (board.isDraw()) {
            text = "It was a draw!";
        } else {
            TicTacToeBoard.State winner = board.getWinner();
            text = winner.toString() + " won!";
        }
        panel.add(new JLabel(text));
        frame.add(panel);
        frame.pack();
        frame.setVisible(true);
    }

    private enum GameType {
        HumanVsHuman,
        HumanVsAI,
        AIVsHuman
    }

    public class GameTypeDialog extends JDialog implements ActionListener {
        private final JButton hva = new JButton("Human Vs AI"),
                hvh = new JButton("Human Vs Human"),
                avh = new JButton("AI Vs Human");
        private TicTacToe.GameType type;

        public GameTypeDialog() {
            super((Frame) null, true);
            setLayout(new GridLayout(1, 3));
            add(hva);
            add(hvh);
            add(avh);
            hva.addActionListener(this);
            hvh.addActionListener(this);
            avh.addActionListener(this);
        }

        public TicTacToe.GameType getResponse() {
            return type;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == hva) {
                type = GameType.HumanVsAI;
            } else if (e.getSource() == hvh) {
                type = GameType.HumanVsHuman;
            } else if (e.getSource() == avh) {
                type = GameType.AIVsHuman;
            } else {
                return;
            }
            setVisible(false);
        }
    }
}
