package view;

import controller.*;
import model.MinesweeperModel;
import model.Tile;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.BlockingQueue;

/**
 * Contains information for what the user will see when they run and interact with the program.
 */

public class MinesweeperView {
    private final JButton[][] Minefield;
    private final int ROWS;
    private final int COLUMNS;
    ImageIcon f = new ImageIcon("src/minesweeper_images/images/bomb_flagged.gif");
    ImageIcon s = new ImageIcon("src/minesweeper_images/images/face_smile.gif");
    /**
     * icon's height
     */
    int buttonHeight = f.getIconHeight();
    /**
     * icon's width
     */
    int buttonWidth = f.getIconWidth();
    JFrame frame;
    JPanel panel1;
    JPanel panel2;
    JPanel container;
    JPanel panel;
    JButton face;
    private final BlockingQueue<Message> queue;
    private int numberOfMoves;

    /**
     * initializing minesweeperview
     * @param queue
     * @param args
     * @return MinesweeperView
     */

    public static MinesweeperView init(BlockingQueue<Message> queue, int[] args) {
        return new MinesweeperView(queue, args[0], args[1]);
    }

    /**
     * setting icon heights and widths for view, as well as creating new JFrames and panels
     * @param queue
     * @param width
     * @param height
     */

    private MinesweeperView(BlockingQueue<Message> queue, int width, int height){
        this.queue = queue;
        this.ROWS = height;
        this.COLUMNS = width;
        numberOfMoves = 0;
        face = new JButton(s);
        face.setPreferredSize(new Dimension(s.getIconWidth(), s.getIconHeight()));
        face.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                face.setIcon(s);
                queue.add(new NewGameMessage());
            }
        });
        frame = new JFrame("Minesweeper");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel1 = new JPanel();
        panel2 = new JPanel(new FlowLayout());
        panel2.add(face);
        panel2.setBackground(new Color(179, 179, 179));
        container = new JPanel(new BorderLayout());
        panel1.setSize(500, 500);
        panel1.setLayout(new GridLayout(ROWS, COLUMNS));
        panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        container.add(panel2, BorderLayout.NORTH);
        container.add(panel1, BorderLayout.CENTER);
        panel.add(container);

        Minefield = new JButton[ROWS][COLUMNS];
        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }


    /**
     * this method updates and repaints the view using switch statements to check for different possible cases
     * @param model
     */

    public void repaintView(MinesweeperModel model){
        panel1.removeAll();
        Tile[][] grid = model.getGrid();
        for (int r = 0; r < ROWS; r++){
            for (int c = 0; c < COLUMNS; c++){
                try {
                    switch (grid[r][c].getState()) {
                        case Tile.COVERED:
                            Minefield[r][c] = new JButton(new ImageIcon("src/minesweeper_images/images/blank.gif"));
                            break;
                        case Tile.FLAGGED:
                            Minefield[r][c] = new JButton(new ImageIcon("src/minesweeper_images/images/bomb_flagged.gif"));
                            break;
                        case Tile.QUESTION:
                            Minefield[r][c] = new JButton(new ImageIcon("src/minesweeper_images/images/bomb_question.gif"));
                            break;
                        default:
                            switch (grid[r][c].getType()) {
                                case Tile.BLANK :
                                    Minefield[r][c] = new JButton(new ImageIcon("src/minesweeper_images/images/num_0.gif"));
                                    break;
                                case Tile.MINE :
                                    Minefield[r][c] = new JButton(new ImageIcon("src/minesweeper_images/images/bomb_death.gif"));
                                    break;
                                case Tile.NUMBER :
                                    Minefield[r][c] = new JButton(new ImageIcon("src/minesweeper_images/images/num_" + grid[r][c].getNumSurroundingMines() + ".gif"));
                                    break;
                            }
                    }
                    Minefield[r][c].setPreferredSize(new Dimension(buttonWidth, buttonHeight));
                    int finalR = r;
                    int finalC = c;
                    Minefield[r][c].addMouseListener(new MouseAdapter() {
                        @Override
                        public void mousePressed(MouseEvent e) {
                            hitButtonPressed(e);
                        }

                        @Override
                        public void mouseReleased(MouseEvent e) {
                            hitButtonReleased(e, finalR, finalC, grid);
                        }
                    });
                } catch (Exception e){
                    e.printStackTrace();
                }
                panel1.add(Minefield[r][c]);
            }
        }
        frame.repaint();
        frame.pack();
    }


    /**
     * updating view based on user action
     * @param e
     */


    void hitButtonPressed(MouseEvent e){
        if (SwingUtilities.isLeftMouseButton(e))
            face.setIcon(new ImageIcon("src/minesweeper_images/images/face_ooh.gif"));
    }

    /**
     * updating view based on user click type
     * @param e
     * @param r
     * @param c
     * @param grid
     */

    void hitButtonReleased(MouseEvent e, int r, int c, Tile[][] grid){
        if (grid[r][c].getState() != Tile.REVEALED){
            if (SwingUtilities.isLeftMouseButton(e)){
                numberOfMoves++;
                queue.add(new RevealMessage(r, c));
                if (grid[r][c].getType() == Tile.MINE){
                    Minefield[r][c].setIcon(new ImageIcon("src/minesweeper_images/images/bomb_death.gif"));
                    if (numberOfMoves > 1)
                        face.setIcon(new ImageIcon("src/minesweeper_images/images/face_dead.gif"));
                    else {
                        face.setIcon(s);
                    }
                } else {
                    face.setIcon(s);
                }
            }
            else if (SwingUtilities.isRightMouseButton(e))
                queue.add(new FlagMessage(r, c));
            else if (SwingUtilities.isMiddleMouseButton(e))
                queue.add(new QuestionMessage(r, c));
        } else  face.setIcon(s);
    }

    /**
     * resets the number of moves
     */
    public void reset(){
        numberOfMoves = 0;
        face.setIcon(s);
    }

    /**
     * disposes of JFrame frame
     */
    public void dispose() {
        frame.dispose();
    }

    /**
     * updates view when game is over
     */

    public void gameOver() {
        for(JButton[] b : Minefield){
            for (JButton j: b){
                //j.removeMouseListener(j.getMouseListeners()[0]);
                j.removeMouseListener(j.getMouseListeners()[1]);
            }
        }
    }

    /**
     * changing the image at the top to a game winning image
     */

    public void gameWon() {
        face.setIcon(new ImageIcon("src/minesweeper_images/images/face_win.gif"));
        for(JButton[] b : Minefield){
            for (JButton j: b){
                //j.removeMouseListener(j.getMouseListeners()[0]);
                j.removeMouseListener(j.getMouseListeners()[1]);
            }
        }
    }
}