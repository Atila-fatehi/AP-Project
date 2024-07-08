package view.Jcomponents;

import controller.Constants;

import javax.swing.*;

public class MyLabel extends JLabel {

    public MyLabel(String text , int x , int y , int width, int height) {
        super(text);
        setText(text);
        setHorizontalAlignment(JLabel.CENTER);
        setBounds(x, y, width, height);
        setFont(Constants.BOLD_20);
        setForeground(Constants.FORE_COLOR);
    }
}
