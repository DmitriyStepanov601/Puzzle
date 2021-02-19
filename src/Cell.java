/**
 * Cell class with properties <b>x</b>, <b>y</b> и <b>figure</b>.
 * @author Dmitriy Stepanov
 */

public class Cell {
    private final int x, y;     /** the coordinates of the cell */
    private Figure figure;      /** a fragment of the cell */

    /**
     * Constructor - creating a new cell with specific values
     * @param x x coordinate
     * @param y y coordinate
     * @param figure fragment in the form of a button
     * @see Cell#Cell(int,int,Figure)
     */
    public Cell(int x, int y, Figure figure) {
        this.x = x;
        this.y = y;
        this.figure = figure;
    }

    /**
     * Constructor - creating a new cell with coordinates
     * @param x x coordinate
     * @param y y coordinate
     * @see Cell#Cell(int,int)
     */
    public Cell(int x, int y) {
        this.x = x;
        this.y = y;
        figure = null;
    }

    // getters and setters for fields
    public Figure getFigure(){
        return figure;
    }
    public void setFigure(Figure figure){
        this.figure = figure;
    }
    public int getX() { return x; }
    public int getY() { return y; }
}
