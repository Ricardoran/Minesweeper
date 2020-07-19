import javax.swing.*;

public class MineFieldTester {

    private static final int FRAME_WIDTH = 800;
    private static final int FRAME_HEIGHT = 425;

    private static int SIDE_LENGTH = 9;
    private static int NUM_MINES = 10;

    public static void main(String[] args){
        JFrame frame = new JFrame();

        frame.setTitle("Minesweeper");

        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

        GameBoardPanel gameBoard = new GameBoardPanel(SIDE_LENGTH, 20, NUM_MINES);

        frame.add(gameBoard);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);

    }
}
