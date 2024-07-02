package view.images;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

public class WallpaperPainter extends JPanel {
    public void paint(Graphics g) {
        super.paintComponent(g);

        try {
            g.drawImage(ImageIO.read(new File(Paths.get("").toAbsolutePath() + "\\src\\main\\java\\view\\images\\wallpaper.jpg")), 0, 0, this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
