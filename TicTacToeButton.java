import javax.swing.JButton;
import java.awt.Font;

public class TicTacToeButton extends JButton {
    private int row;
    private int col;

    public TicTacToeButton(int row, int col) {
        this.row = row;
        this.col = col;
        setFont(new Font("Courier", Font.BOLD, 40));
    }
    public int getRow() {
        return row;
    }
    public int getCol() {
        return col;
    }
}
