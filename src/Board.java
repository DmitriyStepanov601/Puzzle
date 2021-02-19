import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

/**
 * Class of the board for the picture
 * @author Dmitriy Stepanov
 */
public class Board extends JPanel {
    public static Cell[][] board;
    private ArrayList<Cell> completeBoard = new ArrayList<>();
    public final int dimension;
    private final int figureWidth, figureHeight;
    private static final Color BORDER_COLOR = new Color(193, 11, 243, 245);

    /**
     * Constructor - creating a new Board with specific values
     * @param dimension dimension picture
     * @param puzzle picture
     * @see Board#Board(int,BufferedImage)
     */
    public Board(int dimension, BufferedImage puzzle) {
        this.setPreferredSize(new Dimension(710, 440));
        this.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 5));
        this.setBackground(Color.lightGray);
        this.dimension = dimension;
        board = new Cell[dimension][dimension];
        int x = 0;
        int y = 0;

        figureWidth = puzzle.getWidth()/dimension;
        figureHeight = puzzle.getHeight()/dimension;
        this.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        for(int i = 0; i < dimension; i++) {
            for(int j = 0; j < dimension; j++) {
                if(i == dimension - 1 && j == dimension - 1) {
                    continue;
                }
                completeBoard.add(new Cell(i, j, new Figure(i, j, new ImageIcon(puzzle.getSubimage(x, y,
                        figureWidth, figureHeight)), dimension)));
                x += figureWidth;
            }
            x = 0;
            y += figureHeight;
        }
        messBoard();
        remover();
    }

    /**
     * Shuffle the fragments on the board
     */
    public void messBoard() {
        Random randomGenerator = new Random();
        ArrayList<Cell> cellStore = new ArrayList<>(completeBoard);
        for(int i = 0; i < dimension; i++) {
            for(int j = 0; j < dimension; j++) {
                if(i == dimension - 1 && j == dimension - 1) {
                    board[i][j] = new Cell(i, j);
                    continue;
                }

                int randomIndex = randomGenerator.nextInt(completeBoard.size());
                completeBoard.get(randomIndex).getFigure().setPos(i, j);
                board[i][j] = new Cell(i, j, completeBoard.get(randomIndex).getFigure());
                completeBoard.remove(randomIndex);

            }
        }
        completeBoard = cellStore;
        remover();
    }

    /**
     * Update board
     */
    public void updateBoard() {
        for(int i = 0; i<dimension; i++) {
            for(int j = 0; j<dimension; j++) {
                if(board[i][j].getFigure() == null) {
                    JLabel label = new JLabel();
                    label.setPreferredSize(new Dimension(figureWidth, figureHeight));
                    this.add(label);
                    continue;
                }
                this.add(board[i][j].getFigure());
            }
        }

        Puzzle.getContainer().validate();
    }

    /**
     * Remove all cells in the board
     */
    public void remover() {
        this.removeAll();
        updateBoard();
    }
}
