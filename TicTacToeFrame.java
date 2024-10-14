import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TicTacToeFrame extends JFrame {
    private TicTacToeButton[][] Buttons;
    private JButton QuitButton;
    private String CurrentPlayer;
    private int MoveCounter;

    public TicTacToeFrame() {
        setTitle("Danielle's Tic-Tac-Toe");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        InitializeGame();
        CreateButtons();
        AddQuitButton();

        setMinimumSize(new Dimension(200, 250));
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void InitializeGame() {
        CurrentPlayer = "X";
        MoveCounter = 0;
        Buttons = new TicTacToeButton[3][3];
    }

    private void CreateButtons() {
        JPanel BoardPanel = new JPanel(new GridLayout(3, 3, 5, 5));
        BoardPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Buttons[row][col] = new TicTacToeButton(row, col);
                Buttons[row][col].setPreferredSize(new Dimension(80, 80));
                Buttons[row][col].addActionListener(new TicTacToeListener());
                BoardPanel.add(Buttons[row][col]);
            }
        }
        add(BoardPanel, BorderLayout.CENTER);
    }

    private void AddQuitButton() {
        QuitButton = new JButton("Quit");
        QuitButton.addActionListener(e -> System.exit(0));
        JPanel BottomPanel = new JPanel();
        BottomPanel.add(QuitButton);
        BottomPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        add(BottomPanel, BorderLayout.SOUTH);
    }

    private class TicTacToeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            TicTacToeButton Button = (TicTacToeButton) e.getSource();
            if (Button.getText().equals("")) {
                Button.setText(CurrentPlayer);
                MoveCounter++;

                if (CheckForWin()) {
                    JOptionPane.showMessageDialog(TicTacToeFrame.this, "Player " + CurrentPlayer + " Wins!", "Winner", JOptionPane.INFORMATION_MESSAGE);
                    if (PlayAgain()) {
                        ResetGame();
                    } else {
                        System.exit(0);
                    }
                } else if (Tie()) {
                    JOptionPane.showMessageDialog(TicTacToeFrame.this, "Tie");
                    if (PlayAgain()) {
                        ResetGame();
                    } else {
                        System.exit(0);
                    }
                } else {
                    CurrentPlayer = (CurrentPlayer.equals("X")) ? "O" : "X";
                }
            } else {
                JOptionPane.showMessageDialog(TicTacToeFrame.this, "Invalid Move! Try Again!");
            }
        }
    }

    private boolean CheckForWin() {
        return CheckRows() || CheckColumns() || CheckDiagonals();
    }

    private boolean CheckRows() {
        for (int row = 0; row < 3; row++) {
            if (CheckLine(Buttons[row][0], Buttons[row][1], Buttons[row][2])) {
                return true;
            }
        }
        return false;
    }

    private boolean CheckColumns() {
        for (int col = 0; col < 3; col++) {
            if (CheckLine(Buttons[0][col], Buttons[1][col], Buttons[2][col])) {
                return true;
            }
        }
        return false;
    }

    private boolean CheckDiagonals() {
        return CheckLine(Buttons[0][0], Buttons[1][1], Buttons[2][2]) ||
                CheckLine(Buttons[0][2], Buttons[1][1], Buttons[2][0]);
    }

    private boolean CheckLine(TicTacToeButton B1, TicTacToeButton B2, TicTacToeButton B3) {
        return !B1.getText().equals("") &&
                B1.getText().equals(B2.getText()) &&
                B2.getText().equals(B3.getText());
    }

    private boolean Tie() {
        if (MoveCounter < 5) return false;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (Buttons[row][col].getText().equals("")) {
                    if (WouldWin("X", row, col) || WouldWin("O", row, col)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean WouldWin(String player, int row, int col) {
        Buttons[row][col].setText(player);
        boolean win = CheckForWin();
        Buttons[row][col].setText("");
        return win;
    }

    private void ResetGame() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                Buttons[row][col].setText("");
            }
        }
        CurrentPlayer = "X";
        MoveCounter = 0;
    }

    private boolean PlayAgain() {
        int response = JOptionPane.showConfirmDialog(this, "Do you want to play Danielle's Tic-Tac-Toe Game Again?", "Play Again?", JOptionPane.YES_NO_OPTION);
        return response == JOptionPane.YES_OPTION;
    }

    public static void main(String[] args) {
        new TicTacToeFrame();
    }
}
