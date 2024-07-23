package controller.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public abstract class Constants {
    public static final Color BACK_COLOR = new Color(0x9A1A03);
    public static final Color FORE_COLOR = new Color(0xFB8B24);
    public static final Color ANOTHER_FORE_COLOR = new Color(0x074E9C);
    public static final Color DARK_BLUE = new Color(0x011022);
    public static final Color TRI_YELLOW = new Color(0xFFD900);
    public static final Color SQUA_GREEN = new Color(0x22FF00);
    public static final Color OMEN_PINK = new Color(0xFF07BB);
    public static final Color WYRM_PINK = new Color(0xFF078F);
    public static final Color ARCH_RED = new Color(0xFF0734);
    public static final Color ARCH_DARKER_RED = new Color(0x630013);
    public static final Color EPSILON_COLOR = new Color(0x38C1F1);
    public static final Color STRING_COLOR = new Color(0x8A26FF);
    public static final Color ANOTHER_STRING_COLOR = new Color(0xA826FF);
    public static final Color GOLD = new Color(0xEBB000);
    public static final Color TRANSPARENT = new Color(0, 0, 0, 0);

    public static final int SCREEN_WIDTH = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
    public static final int SCREEN_HEIGHT = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();

    public static final double INITIAL_EPSILON_POSX = 950;
    public static final double INITIAL_EPSILON_POSY = 550;

    public static final int INITIAL_PANEL_X = 600;
    public static final int INITIAL_PANEL_Y = 200;
    public static final int INITIAL_PANEL_WIDTH = 700;
    public static final int INITIAL_PANEL_HEIGHT = 700;

//    public static final int INITIAL_PANEL_X = 0;
//    public static final int INITIAL_PANEL_Y = 0;
//    public static final int INITIAL_PANEL_WIDTH = 1920;
//    public static final int INITIAL_PANEL_HEIGHT = 1080;

    public static final int BUTTON_WIDTH = 300;
    public static final int BUTTON_HEIGHT = 50;
    public static final int LABEL_WIDTH = 300;
    public static final int LABEL_HEIGHT = 100;
    public static final int BUTTON_INITIAL_X = 150;
    public static final int BUTTON_INITIAL_Y = 300;
    public static final int BUTTON_MARGIN = 70;

    public static final int FRAMES_WIDTH = 500;
    public static final int FRAMES_HEIGHT = 700;


    public static final int SKILL_TREE_FRAME_WIDTH = 1100;
    public static final int SKILL_TREE_FRAME_HEIGHT = 700;

    public static final Font BOLD_15 = new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 15);
    public static final Font BOLD_20 = new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 20);
    public static final Font BOLD_25 = new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25);
    public static final Font BOLD_35 = new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 35);

    public static final String path = "";

    //Paths
    public static final File WALLPAPER = new File(Paths.get("").toAbsolutePath() + "/src/main/java/view/images/wallpaper.jpg");
    public static final File NECRO_PICK = new File(Paths.get("").toAbsolutePath() + "/src/main/java/view/images/Necropick.PNG");
    public static final File WYRM_PIC = new File(Paths.get("").toAbsolutePath() + "/src/main/java/view/images/wyrm.PNG");
    public static final File BARRI_PIC = new File(Paths.get("").toAbsolutePath() + "/src/main/java/view/images/barricados.PNG");
    public static final File ORB_PIC_FILE = new File(Paths.get("").toAbsolutePath() + "/src/main/java/view/images/orb.png");
    public static final File SMILEY_PIC = new File(Paths.get("").toAbsolutePath() + "/src/main/java/view/images/smiley.PNG");
    public static final File HAND_PIC = new File(Paths.get("").toAbsolutePath() + "/src/main/java/view/images/h1.PNG");
    public static final File HAND_SECOND_PIC = new File(Paths.get("").toAbsolutePath() + "/src/main/java/view/images/h2.PNG");

    public static final Image ORB_IMG;
    static {
        try {
            ORB_IMG = ImageIO.read(Constants.ORB_PIC_FILE).getScaledInstance(100,100 , Image.SCALE_DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static final Image BARRI_IMG;
    static {
        try {
            BARRI_IMG = ImageIO.read(Constants.BARRI_PIC).getScaledInstance(200,200 , Image.SCALE_DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static final Image WYRM_IMG;
    static {
        try {
            WYRM_IMG = ImageIO.read(Constants.WYRM_PIC).getScaledInstance(90,70 , Image.SCALE_DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static final Image NECRO_IMG;
    static {
        try {
            NECRO_IMG = ImageIO.read(Constants.NECRO_PICK).getScaledInstance(50,50 , Image.SCALE_DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static final Image SMILEY_IMG;
    static {
        try {
            SMILEY_IMG = ImageIO.read(Constants.SMILEY_PIC).getScaledInstance(250,250 , Image.SCALE_DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static final Image HAND_IMG;
    static {
        try {
            HAND_IMG = ImageIO.read(Constants.HAND_PIC).getScaledInstance(180,250 , Image.SCALE_DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static final Image HAND_SECOND_IMG;
    static {
        try {
            HAND_SECOND_IMG = ImageIO.read(Constants.HAND_SECOND_PIC).getScaledInstance(200,250 , Image.SCALE_DEFAULT);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static final String SETTING_PATH = Paths.get("").toAbsolutePath() + "/src/main/java/model/dataBase/settings.txt";
    public static final String ABILITY_PATH = Paths.get("").toAbsolutePath() + "/src/main/java/model/dataBase/abilityCode.txt";
    public static final String XP_PATH = Paths.get("").toAbsolutePath() + "/src/main/java/model/dataBase/XP.txt";
    public static final String KEYS_PATH = Paths.get("").toAbsolutePath() + "/src/main/java/model/dataBase/keys.txt";
    public static final String DAVE_PATH = Paths.get("").toAbsolutePath() + "/src/main/java/controller/audio/CrazyDave.wav";
    public static final String WATERY_GRAVES_PATH = Paths.get("").toAbsolutePath() + "/src/main/java/controller/audio/WateryGraves.wav";
    public static final String GAME_STATE_PATH = Paths.get("").toAbsolutePath() + "/src/main/java/model/dataBase/gameState.ser";
    public static final String CHECKPOINT_PATH = Paths.get("").toAbsolutePath() + "/src/main/java/model/dataBase/checkPoint.ser";

    public static final int SQUARANTINE_SIZE = 25;
    public static final int TRIGORATH_SIZE = 30;
    public static final int OMENOCT_SIZE = 20;
}
