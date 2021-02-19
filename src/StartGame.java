import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Class of the beginning of the game
 * @author Dmitriy Stepanov
 */
public class StartGame extends JFrame {
    private final JButton open;
    private final JRadioButton easy;
    private final JRadioButton medium;
    private final JRadioButton hard;
    private final JRadioButton difficult;
    private BufferedImage image = null;

    private static final Font labFont = new Font("Verdana", Font.BOLD, 14);
    private static final Font butFont = new Font("Verdana", Font.BOLD, 14);

    /**
     * Constructor - creating a new start game
     * @param windowIcon original icon application
     * @see StartGame#StartGame(Image)
     */
    public StartGame(Image windowIcon){
        setTitle("New puzzle");
        setSize(900, 615);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel main = new JPanel();
        main.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.setIconImage(windowIcon);

        JPanel supp = new JPanel();
        supp.setLayout(new FlowLayout(FlowLayout.CENTER));

        JLabel puzzleImage = new JLabel("Select an image and difficulty level");
        puzzleImage.setLayout(new FlowLayout(FlowLayout.CENTER));
        puzzleImage.setFont(labFont);

        open = new JButton(new ImageIcon(Puzzle.loadImage("/fonsaw.png").
                getScaledInstance(800, 490, Image.SCALE_DEFAULT)));
        open.setName("Open");
        open.setContentAreaFilled(false);
        open.setBorderPainted(false);
        open.addActionListener(e -> {
            JFileChooser filch = new JFileChooser();
            int action = filch.showOpenDialog(null);
            if(action == JFileChooser.APPROVE_OPTION){
                File file = filch.getSelectedFile();
                try {
                    image = ImageIO.read(file);
                    open.setIcon(new ImageIcon(image.getScaledInstance(800, 490, Image.SCALE_DEFAULT)));
                } catch (IOException e1) {
                    System.out.println("You must select an image");
                }
            }
        });

        easy = new JRadioButton("Easy");
        easy.setFont(butFont);
        medium = new JRadioButton("Medium");
        medium.setFont(butFont);
        hard = new JRadioButton("Hard");
        hard.setFont(butFont);
        difficult = new JRadioButton("Difficult");
        difficult.setFont(butFont);

        ButtonGroup group = new ButtonGroup();
        group.add(easy);
        group.add(medium);
        group.add(hard);
        group.add(difficult);

        JButton start = new JButton("start");
        start.addActionListener(e -> startGame());
        start.setFont(butFont);
        start.setBackground(Color.lightGray);

        main.add(puzzleImage);
        main.add(open);
        main.add(supp);
        supp.add(easy);
        supp.add(medium);
        supp.add(hard);
        supp.add(difficult);
        supp.add(start);
        add(main);
        setVisible(true);
    }

    /**
     * The option to start a game
     */
    private void startGame(){
        int level;
        if (!easy.isSelected()) {
            if(medium.isSelected()){
                level = 4;
            } else if(hard.isSelected()) {
                level = 5;
            } else if(difficult.isSelected()) {
                level = 6;
            } else return;
        } else level = 3;
        if(image == null) return;
        BufferedImage puzzleImage = ImageResized.resizeImage(image, 700, 600);
        BufferedImage miniImage = ImageResized.resizeImage(image, 460, 380);
        Puzzle.start(puzzleImage, level, miniImage);
        this.dispose();
    }
}