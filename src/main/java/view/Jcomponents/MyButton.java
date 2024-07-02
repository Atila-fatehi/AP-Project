package view.Jcomponents;

import Controller.Constants;

import javax.swing.*;
import java.awt.event.ActionListener;

public class MyButton extends JButton {
    private int x;
    private int y;
    private int width;
    private int height;

    public MyButton(String text, int x, int y, int width, int height, ActionListener actionListener) {
        super(text);
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        super.addActionListener(actionListener);
        setText(text);
        setBounds(x, y, width, height);
        setFocusable(false);
        setHorizontalAlignment(JButton.CENTER);
        setHorizontalTextPosition(JButton.CENTER);
        setBackground(Constants.BACK_COLOR);
        setForeground(Constants.FORE_COLOR);
        setFont(Constants.BOLD_25);
    }
}
