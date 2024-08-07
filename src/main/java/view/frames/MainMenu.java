package view.frames;

import controller.FileController;
import controller.FrameController;
import controller.logic.GameManager;
import controller.audio.players.GameMusicPlayer;
import controller.audio.players.MenuMusicPlayer;
import controller.logic.GameState;
import controller.server.RequestManager;
import controller.server.request.ShowLeaderBoardRequest;
import controller.server.sender.RequestSender;
import controller.util.Constants;
import view.Jcomponents.MyButton;
import view.Jcomponents.MyLabel;
import view.gameGUI.GameFrame;
import view.gameGUI.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainMenu extends JFrame {
    private static MainMenu instance;

    public static MainMenu getInstance() {
        if (instance == null) instance = new MainMenu();
        return instance;
    }

    public String serverStatus = "";
    MyLabel status;

    public MainMenu() {
        setTitle("WindowKill");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        MyButton newGame = new MyButton("New Game", Constants.BUTTON_INITIAL_X, Constants.BUTTON_INITIAL_Y, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                MenuMusicPlayer.getInstance().stop();
                GameMusicPlayer.getInstance().start();
                FrameController.minimizeAllWindows();

                GameFrame.makeInstance();
                GameFrame.getInstance().addPanel();
                GameState.initiateNewGame();
                GameState.panels.add(GamePanel.getInstance());
                GameManager.initiateNewGame();
            }
        });
        MyButton load = new MyButton("Load Previous Game", Constants.BUTTON_INITIAL_X + Constants.BUTTON_WIDTH + 20, Constants.BUTTON_INITIAL_Y, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                MenuMusicPlayer.getInstance().stop();
                GameMusicPlayer.getInstance().start();
                FrameController.minimizeAllWindows();

                GameState.initiateNewGame();
                FileController.deserializeGameState();

                GameFrame.makeInstance();
                GameFrame.getInstance().addSavedPanels();

                GameManager.initiateNewGame();
                GameManager.getInstance().stopTimers();
                java.util.Timer timer = new java.util.Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        GameManager.getInstance().continueTimers();
                        timer.cancel();
                    }
                }, 3000, 1111);
            }
        });
        MyButton setting = new MyButton("Setting", Constants.BUTTON_INITIAL_X + Constants.BUTTON_WIDTH / 2, Constants.BUTTON_INITIAL_Y + Constants.BUTTON_MARGIN, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Setting();
            }
        });
        MyButton tutorial = new MyButton("Tutorial", Constants.BUTTON_INITIAL_X + Constants.BUTTON_WIDTH / 2, Constants.BUTTON_INITIAL_Y + Constants.BUTTON_MARGIN * 2, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Tutorial();
            }
        });
        MyButton skillTree = new MyButton("Skill Tree", Constants.BUTTON_INITIAL_X + Constants.BUTTON_WIDTH / 2, Constants.BUTTON_INITIAL_Y + Constants.BUTTON_MARGIN * 3, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new SkillTree();
            }
        });
        MyButton credit = new MyButton("Credit", Constants.BUTTON_INITIAL_X + Constants.BUTTON_WIDTH / 2, Constants.BUTTON_INITIAL_Y + Constants.BUTTON_MARGIN * 4, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        MyButton exit = new MyButton("Exit", Constants.BUTTON_INITIAL_X + Constants.BUTTON_WIDTH / 2, Constants.BUTTON_INITIAL_Y + Constants.BUTTON_MARGIN * 5, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        MyButton connect = new MyButton("Connect to Server", Constants.BUTTON_INITIAL_X + 1200, Constants.BUTTON_INITIAL_Y, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (RequestManager.connect()) serverStatus = "Connected.";
                else serverStatus = "Connection failed.";

                refresh();
            }
        });
        MyButton disconnect = new MyButton("disconnect from Server", Constants.BUTTON_INITIAL_X + 1200, Constants.BUTTON_INITIAL_Y + Constants.BUTTON_MARGIN, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                RequestManager.close();
                serverStatus = "Disconnected.";
                refresh();
            }
        });
        MyButton refresh = new MyButton("Show LeaderBoard", Constants.BUTTON_INITIAL_X + 1200, Constants.BUTTON_INITIAL_Y + Constants.BUTTON_MARGIN * 2, Constants.BUTTON_WIDTH, Constants.BUTTON_HEIGHT, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!RequestManager.socket.isClosed()) {
                    JPanel panel = new JPanel(new BorderLayout());
                    ArrayList<String> myList = RequestSender.getInstance().sendRequest(new ShowLeaderBoardRequest()).getList();
                    final JList<String> list = new JList<String>(myList.toArray(new String[myList.size()]));
                    JScrollPane scrollPane = new JScrollPane();
                    scrollPane.setViewportView(list);
                    list.setLayoutOrientation(JList.VERTICAL);
                    panel.add(scrollPane);
                    JFrame frame = new JFrame("Clients list");
                    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    frame.add(panel);
                    frame.setSize(500, 250);
                    frame.setLocationRelativeTo(null);
                    frame.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(getInstance(), "You are not connected.", "Message", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });
        status = new MyLabel("Status : " + serverStatus, Constants.BUTTON_INITIAL_X + 1200, Constants.BUTTON_INITIAL_Y - Constants.BUTTON_MARGIN, Constants.LABEL_WIDTH, Constants.LABEL_HEIGHT);

        getContentPane().add(newGame);
        getContentPane().add(setting);
        getContentPane().add(tutorial);
        getContentPane().add(skillTree);
        getContentPane().add(credit);
        getContentPane().add(exit);
        getContentPane().add(load);
        getContentPane().add(connect);
        getContentPane().add(disconnect);
        getContentPane().add(refresh);
        getContentPane().add(status);
        getContentPane().add(new WallpaperPainter());

        setVisible(true);
    }

    public void refresh() {
        status.setText("Status : " + serverStatus);
    }
}