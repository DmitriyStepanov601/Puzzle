import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Game Interface Class
 * @author Dmitriy Stepanov
 */
public class Puzzle extends JFrame {
    private static JPanel puzzleArea;
    private static final BufferedImage def = loadImage("/default.png");
    private static int moveCount = 0;
    private static final JLabel moves = new JLabel(" " + moveCount + " ");
    private static final JButton miniImage = new JButton(new ImageIcon(def.getScaledInstance(460,
            380, Image.SCALE_DEFAULT)));
    private static Board board = null;
    private static Timer chronometer;
    private static Container container;
    private static Image windowIcon;
    private static final Font bFont = new Font("Verdana", Font.BOLD, 24);
    private static final Color FOREGROUND_COLOR = new Color(11, 31, 243, 245);

    private final JLabel time = new JLabel(" 0 : 0 : 0 ");
    private final int iconSize = 30;
    private int seconds = 0;
    private int minutes = 0;
    private int hours = 0;

    /**
     * Constructor - create a new interface of the game
     * @see Puzzle#Puzzle()
     */
    public Puzzle() {
        this.setTitle("Puzzle");
        int width = 1220;
        int height = 662;
        this.setSize(width, height);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setMinimumSize(new Dimension(width, height));
        this.setMaximumSize(new Dimension(width, height));
        this.setResizable(false);

        windowIcon = loadImage("/puzzle.png");
        this.setIconImage(windowIcon);
        container = this.getContentPane();
        int delay = 1000;
        chronometer = new Timer(delay, new TimerListener());

        createMenu();
        puzzleArea = new JPanel();
        puzzleArea.setOpaque(true);
        puzzleArea.setBackground(Color.gray);

        JPanel timeMoves = new JPanel();
        timeMoves.setOpaque(true);
        miniImage.setContentAreaFilled(false);
        miniImage.setBorderPainted(false);
        timeMoves.add(miniImage);

        time.setForeground(FOREGROUND_COLOR);
        time.setFont(bFont);
        moves.setForeground(FOREGROUND_COLOR);
        moves.setFont(bFont);

        puzzleArea.add(new JLabel(new ImageIcon(def)));
        JLabel timeTitle = new JLabel();
        timeTitle.setIcon(new ImageIcon(loadImage("/timer.png").
                getScaledInstance(80, 100, Image.SCALE_DEFAULT)));
        JLabel movesTitle = new JLabel();
        movesTitle.setIcon(new ImageIcon(loadImage("/moves.png").
                getScaledInstance(90, 100, Image.SCALE_DEFAULT)));

        JPanel imPanel = new JPanel(new GridLayout(1, 1));
        timeMoves.add(imPanel);

        JPanel timer = new JPanel();
        imPanel.add(timer);
        timer.add(timeTitle);
        timer.add(time);

        JPanel mover = new JPanel();
        imPanel.add(mover);
        mover.add(movesTitle);
        mover.add(moves);
        container.add(puzzleArea, BorderLayout.WEST);
        container.add(timeMoves, BorderLayout.CENTER);
        setVisible(true);
    }

    private void createMenu() {
        JMenuBar gameMenu = new JMenuBar();
        JMenu game = new JMenu("Game");
        gameMenu.add(game);

        JMenuItem newGame = new JMenuItem("New game");
        KeyStroke ctrlNKeyStroke = KeyStroke.getKeyStroke("control N");
        newGame.setAccelerator(ctrlNKeyStroke);
        game.add(newGame);
        newGame.addActionListener(e -> {
            newGame();
            new TimerListener();
        });

        JMenu options = new JMenu("Options");
        game.add(options);
        game.addSeparator();

        JMenuItem retryGame = new JMenuItem("Retry");
        KeyStroke ctrlRKeyStroke = KeyStroke.getKeyStroke("control R");
        retryGame.setAccelerator(ctrlRKeyStroke);
        options.add(retryGame);
        retryGame.addActionListener(e -> {
            retryGame();
            new TimerListener();
        });

        JMenuItem playGame = new JMenuItem("Play");
        KeyStroke ctrlPKeyStroke = KeyStroke.getKeyStroke("control P");
        playGame.setAccelerator(ctrlPKeyStroke);
        options.add(playGame);
        playGame.addActionListener(e -> {
            playGame();
            new TimerListener();
        });

        JMenuItem stopGame = new JMenuItem("Stop");
        KeyStroke ctrlSKeyStroke = KeyStroke.getKeyStroke("control S");
        stopGame.setAccelerator(ctrlSKeyStroke);
        options.add(stopGame);
        stopGame.addActionListener(e -> {
            stopGame();
            new TimerListener();
        });

        JMenuItem exitGame = new JMenuItem("Exit");
        KeyStroke ctrlQKeyStroke = KeyStroke.getKeyStroke("control Q");
        exitGame.setAccelerator(ctrlQKeyStroke);
        game.add(exitGame);
        exitGame.addActionListener(e -> System.exit(0));
        setJMenuBar(gameMenu);
    }

    /**
     * Loading images with the game
     * @param pathImage passes the path along which the images are located
     * @return an image of the BufferedImage type
     */
    public static BufferedImage loadImage(String pathImage) {
        try {
            return ImageIO.read(Puzzle.class.getResource(pathImage));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        return null;
    }

    private void newGame(){
        StartGame start = new StartGame(windowIcon);
    }

    private void playGame() {
        if (board != null) {
            chronometer.start();
            board.setVisible(true);
        }
    }

    private void retryGame() {
        moveCount = 0;
        seconds = 0;
        minutes = 0;
        hours = 0;
        time.setText(" " + hours + " : " + minutes + " : " + seconds + " ");
        moves.setText(" " + moveCount + " ");
        if (board != null) {
            board.messBoard();
        }
    }

    private void stopGame (){
        if (board != null) {
            chronometer.stop();
            board.setVisible(false);
        }
    }

    class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
                seconds++;
                if(seconds == 60) {
                    seconds = 0;
                    minutes++;
                    if(minutes == 60) {
                        minutes = 0;
                        hours++;
                    }
                }
                time.setText(" " + hours + " : " + minutes + " : " + seconds + " ");
        }
    }

    public static void start(BufferedImage img, int level, BufferedImage mini) {
        miniImage.setIcon(new ImageIcon(mini));
        if(board != null)
            container.remove(board);
        board = new Board(level, img);
        chronometer.start();
        container.remove(puzzleArea);
        container.add(board, BorderLayout.WEST);
        container.validate();
    }

    public static void add() {
        moveCount++;
        moves.setText(" " + moveCount + " ");
    }

    public static Board getBoard() {
        return board;
    }
    public static Timer getTimer(){
        return chronometer;
    }
    public static Container getContainer(){
        return container;
    }

    public static void main(String[] args) {
        Puzzle puzzle = new Puzzle();
    }
}
