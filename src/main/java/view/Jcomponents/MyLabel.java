package view.Jcomponents;

import Controller.Constants;

import javax.swing.*;
import java.awt.*;

public class MyLabel extends JLabel {

    public MyLabel(String text , int x , int y , int width, int height) {
        super(text);
        setText(text);
        setHorizontalAlignment(JLabel.CENTER);
        setBounds(x, y, width, height);
        setFont(Constants.BOLD_25);
        setForeground(Constants.FORE_COLOR);
    }
}
