package view.images;

import controller.util.Constants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class WallpaperPainter extends JPanel {
    public WallpaperPainter() {
        setBounds(0, 0, Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
    }

    public void paint(Graphics g) {
        super.paintComponent(g);

        try {
            g.drawImage(ImageIO.read(Constants.WALLPAPER), 0, 0, this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
