import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

/**
 * The fragment class 
 * @author Dmitriy Stepanov
 */

public class Figure extends JButton {
    private int posX;
    private int posY;
    private final int solutionPosX;     
    private final int solutionPosY;     
    private final int dimension;

    /**
     * Constructor - creating a new fragment with certain values
     * @param solPosX position by X
     * @param solPosY position by Y
     * @param figure the part of the picture
     * @param dimension dimension
     * @see Figure#Figure(int,int,ImageIcon,int)
     */
    public Figure(int solPosX, int solPosY, ImageIcon figure, int dimension) {
        this.dimension = dimension;
        this.solutionPosX = solPosX;
        this.solutionPosY = solPosY;
        this.posX = solPosX;
        this.posY = solPosY;
        this.setIcon(figure);
        this.setPreferredSize(new Dimension(figure.getIconWidth(), figure.getIconHeight()));
        this.addActionListener(e -> Move());

        // handler for mouse actions
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBorder(BorderFactory.createLineBorder(Color.yellow, 2));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBorder(BorderFactory.createLineBorder(Color.gray));
            }
        });
    }

    public int getPosX() {
        return posX;
    }
    public void setPos(int posX, int posY) {
        this.posX = posX;
        this.posY = posY;
    }

    public int getPosY() {
        return posY;
    }
    public int getSolutionPosX() {
        return solutionPosX;
    }
    public int getSolutionPosY() {
        return solutionPosY;
    }

    private void Move() {
        Cell[][] board = Board.board;
        try {
            if(board[posX][posY + 1].getFigure() == null) {
                Board.board[posX][posY + 1].setFigure(this);
                Board.board[posX][posY].setFigure(null);
                posY++;
                Puzzle.getBoard().removeAll();
                Puzzle.getBoard().updateBoard();
                CheckAnswer();
                Puzzle.add();
                return;
            }
        } catch(ArrayIndexOutOfBoundsException e) {
            System.err.println();
        }

        try {
            if(board[posX + 1][posY].getFigure() == null) {
                Board.board[posX + 1][posY].setFigure(this);
                Board.board[posX][posY].setFigure(null);
                posX++;
                Puzzle.getBoard().remover();
                CheckAnswer();
                Puzzle.add();
                return;
            }
        } catch(ArrayIndexOutOfBoundsException e) {
            System.err.println();
        }

        try {
            if(board[posX - 1][posY].getFigure() == null) {
                Board.board[posX - 1][posY].setFigure(this);
                Board.board[posX][posY].setFigure(null);
                posX--;
                Puzzle.getBoard().remover();
                CheckAnswer();
                Puzzle.add();
                return;
            }
        } catch(ArrayIndexOutOfBoundsException e) {
            System.err.println();
        }

        try {
            if(board[posX][posY - 1].getFigure() == null) {
                Board.board[posX][posY - 1].setFigure(this);
                Board.board[posX][posY].setFigure(null);
                posY--;
                Puzzle.getBoard().remover();
                CheckAnswer();
                Puzzle.add();
            }
        } catch(ArrayIndexOutOfBoundsException e) {
            System.err.println();
        }
    }

    private void CheckAnswer() {
        Figure figure;
        for(int i = 0; i < dimension; i++) {
            for(int j = 0; j < dimension; j++) {
                figure = Board.board[i][j].getFigure();
                if(figure == null)
                    continue;
                if(figure.getPosX() != figure.getSolutionPosX() || figure.getPosY() != figure.getSolutionPosY()) {
                    return;
                }
            }
        }

        Image windowIcon = null;
        try {
            windowIcon = ImageIO.read(Figure.class.getResource("/win.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ImageIcon win = new ImageIcon(windowIcon);
        Puzzle.getTimer().stop();
        JOptionPane.showMessageDialog(new JPanel(), "Congratulations! You have completed the puzzle",
                "Victory", JOptionPane.INFORMATION_MESSAGE, win);
    }
}
