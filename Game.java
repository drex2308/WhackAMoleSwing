import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
/**
 * Program for a Whack-A-Mole Game GUI application
 * @author DHANUSH VENKATARAMU.
 */
public final class Game {
    /**
     * Instance variable button for starting the game.
     */
    private JButton startButton;
    /**
     * Instance variable textfield for game time display.
     */
    private JTextField timeField;
    /**
     * Instance variable texfield for game score.
     */
    private JTextField scoreField;
    /**
     * Instance variable button array for moles in the game.
     */
    private JButton[] moleButton;
    /**
     * Instance variable for recording the state of the game.
     */
    private GameStatus gameState;
    /**
     * Instance variable hasmap to store the status of each mole.
     */
    private Map<JButton, MoleConfig> moleStatus;
    /**
     * instance variable integer to track user game score.
     */
    private int userScore;
    /**
     * Constant indicating game duration.
     */
    private static final int GAMETIME = 20;
    /**
     * Constant indicating the number of moles in the game.
     */
    private static final int MOLES = 64;
    /**
     * Enumeration with list of mole configurations.
     */
    private enum MoleConfig {
    /**
     * list of enum variables.
     */
        UP, DOWN, HIT;
    }
    /**
     * Enumeration with list of status of the game.
     */
    private enum GameStatus {
    /**
     * list of enum variables.
     */
        RUNNING, STOP;
    }
    /**
     * enumeration with list of uptime for moles for fine tuning further.
     */
    private enum UpRange {
    /**
     * list of enum variables.
     */
        MIN(500), MAX(4000);
    /**
     * number indicating the seconds value for each enum memeber.
     */
        private int seconds;
    /**
     * constructor to intialize enum objects.
     * @param secs specifies the seconds.
     */
        UpRange(int secs) {
            seconds = secs;
        }
    /**
     * method to get the seconds value for specific enum.
     * @return returns the seconds value with respect to the enum object.
     */
        private int getSecs() {
            return seconds;
        }
    }
    /**
     * Constructor for the class Game, to render GUI.
     */
    public Game() {
        gameState = GameStatus.STOP;
        JFrame frame = new JFrame("Whack-A-Mole Game GUI");
        frame.setSize(650, 630);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel pane = new JPanel(new BorderLayout());

        JPanel buttonPane = new JPanel();
        startButton = new JButton("START");
        buttonPane.add(startButton);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startButton.setEnabled(false);

                Runnable timer = new TimeRunnable(GAMETIME);
                Thread timethThread = new Thread(timer);
                timethThread.start();

                for (JButton mole: moleButton) {
                    Thread moleThread = new Thread(new MoleRunnable(mole));
                    moleThread.start();
                }
            }
        });

        buttonPane.add(new JLabel("Time Left: "));
        timeField = new JTextField(10);
        timeField.setEditable(false);
        buttonPane.add(timeField);
        buttonPane.add(new JLabel("Score: "));
        scoreField = new JTextField(10);
        scoreField.setEditable(false);
        buttonPane.add(scoreField);
        pane.add(buttonPane, BorderLayout.NORTH);

        JPanel molePane = new JPanel();
        moleButton = new JButton[MOLES];
        moleStatus = new HashMap<JButton, MoleConfig>();
        for (int i = 0; i < MOLES; i++) {
            moleButton[i] = new JButton("   ");
            moleButton[i].setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
            moleButton[i].setOpaque(true);
            molePane.add(moleButton[i]);
            setButton(moleButton[i], MoleConfig.DOWN);
            moleButton[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    if (gameState == GameStatus.STOP) {
                        return;
                    }

                    if (!(e.getSource() instanceof JButton)) {
                        throw  new AssertionError("somethings wrong! please check");
                    }

                    if (moleStatus.get(e.getSource()) == MoleConfig.UP) {
                        setButton((JButton) e.getSource(), MoleConfig.HIT);
                        userScore = userScore + 1;
                        scoreField.setText(String.valueOf(userScore));
                    }
                }
            });
        }
        pane.add(molePane, BorderLayout.CENTER);

        frame.setContentPane(pane);
        frame.setVisible(true);
    }
    /**
     * function to set the button states, reder thie GUI state
     * and update state in map.
     * This section contains access to moleStatus and button GUI rendering by
     * multiple threads, but for a given button object and key,
     * its accessed by only one thread (specific to mole),
     *  hence it is not synchrnized.
     * @param b the mole to be set/updated.
     * @param config the status to be updated.
     */
    private synchronized void setButton(JButton b, MoleConfig config) {
        switch (config) {
            case UP:
                b.setText(":-)");
                b.setBackground(Color.GREEN);
                moleStatus.put(b, config);
                break;
            case DOWN:
                b.setText("   ");
                b.setBackground(Color.LIGHT_GRAY);
                moleStatus.put(b, config);
                break;
            case HIT:
                b.setText(":-(");
                b.setBackground(Color.RED);
                moleStatus.put(b, config);
                break;
            default:
                System.out.println("Something wrong in switch !");
        }
    }
    /**
     * Nested classs Time runnable to implement timer task for the game.
     * @author Andrew ID: dhanushv NAME: DHANUSH VENKATARAMU
     */
    public class TimeRunnable implements Runnable {
        /**
         * instance variable totalTime to store game length.
         */
        private int totalTime;
        /**
         * constructor for class TimeRunnable.
         * @param gameLenghth specifies the total length of the game.
         */
        public TimeRunnable(int gameLenghth) {
            totalTime = gameLenghth;
        }
        /**
         * overriden run method with job details for the timer thread.
         */
        @Override
        public void run() {
            try {
                gameState = GameStatus.RUNNING;
                userScore = 0;
                scoreField.setText(String.valueOf(userScore));
                timeField.setText(String.valueOf(totalTime));
                while (totalTime != 0) {
                    Thread.sleep(1000);
                    totalTime = totalTime - 1;
                    timeField.setText(String.valueOf(totalTime));
                }
                gameState = GameStatus.STOP;
                Thread.sleep(5000);
                startButton.setEnabled(true);
                timeField.setText("");
                scoreField.setText("");
            } catch (InterruptedException e) {
                System.out.println("Thread timer interrupted");
            }
        }

    }
    /**
     * Class Molerunnable for specifying jobs for mole threads.
     * @author Andrew ID: dhanushv NAME; DHANUSH VENKATARAMU
     */
    public class MoleRunnable implements Runnable {
        /**
         * instance variable mymole to track mole for the thread.
         */
        private JButton mymole;
        /**
         * Constructor for MoleRunnable class.
         * @param mole specifies mole to be updated for game.
         */
        public MoleRunnable(JButton mole) {
            mymole = mole;
        }
        /**
         * Overriden method run() for specifying job for Mole threads.
         */
        @Override
        public void run() {
            try {
                 Random random = new Random();
                 Thread.sleep(random.nextInt(2000) + 500);
            while (gameState == GameStatus.RUNNING) {
                setButton(mymole, MoleConfig.UP);
                int randomMoleNumUp = random.nextInt(UpRange.MAX.getSecs()
                - UpRange.MIN.getSecs()) + UpRange.MIN.getSecs();
                Thread.sleep(randomMoleNumUp);
                setButton(mymole, MoleConfig.DOWN);
                int randomMoleNumDown = random.nextInt(UpRange.MAX.getSecs()
                - 2000) + 2000;
                Thread.sleep(randomMoleNumDown);
            }
            } catch (InterruptedException e) {
                System.out.println("A mole thread is interrupted");
            }
        }
    }
}
