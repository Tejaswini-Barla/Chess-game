import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ChessGame {
    private static final int SIZE = 8;
    private JFrame frame;
    private JPanel boardPanel;
    private JButton[][] squares = new JButton[SIZE][SIZE];
    private String currentTurn = "White";
    private JButton selectedButton = null;

    public ChessGame() {
        frame = new JFrame("Chess Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.setLayout(new BorderLayout());

        boardPanel = new JPanel(new GridLayout(SIZE, SIZE));
        initializeBoard();

        frame.add(boardPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private void initializeBoard() {
        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                JButton square = new JButton();
                square.setFont(new Font("Arial", Font.BOLD, 30));
                square.setBackground((row + col) % 2 == 0 ? Color.WHITE : Color.GRAY);
                square.addActionListener(new MoveListener(row, col));
                squares[row][col] = square;
                boardPanel.add(square);
            }
        }
        setupPieces();
    }

    private void setupPieces() {
        String[] initialRow = {"R", "N", "B", "Q", "K", "B", "N", "R"};
        for (int col = 0; col < SIZE; col++) {
            squares[0][col].setText("B" + initialRow[col]); // Black Pieces
            squares[1][col].setText("BP"); // Black Pawns
            squares[6][col].setText("WP"); // White Pawns
            squares[7][col].setText("W" + initialRow[col]); // White Pieces
        }
    }

    private class MoveListener implements ActionListener {
        private int row, col;

        public MoveListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton clickedButton = squares[row][col];

            if (selectedButton == null) {
                if (!clickedButton.getText().isEmpty() && clickedButton.getText().startsWith(currentTurn.substring(0, 1))) {
                    selectedButton = clickedButton;
                    selectedButton.setBackground(Color.YELLOW);
                }
            } else {
                if (selectedButton == clickedButton) {
                    resetSelection();
                } else {
                    clickedButton.setText(selectedButton.getText());
                    selectedButton.setText("");
                    resetSelection();
                    switchTurn();
                }
            }
        }
    }

    private void resetSelection() {
        if (selectedButton != null) {
            int row = -1, col = -1;
            for (int i = 0; i < SIZE; i++) {
                for (int j = 0; j < SIZE; j++) {
                    if (squares[i][j] == selectedButton) {
                        row = i;
                        col = j;
                    }
                }
            }
            squares[row][col].setBackground((row + col) % 2 == 0 ? Color.WHITE : Color.GRAY);
        }
        selectedButton = null;
    }

    private void switchTurn() {
        currentTurn = currentTurn.equals("White") ? "Black" : "White";
        System.out.println("Current turn: " + currentTurn);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(ChessGame::new);
    }
}
