package view.Jcomponents;

import controller.Constants;

import javax.swing.*;
import java.awt.event.ActionListener;

public class MyButton extends JButton {

    public MyButton(String text, int x, int y, int width, int height, ActionListener actionListener) {
        super(text);
        super.addActionListener(actionListener);
        setText(text);
        setBounds(x, y, width, height);
        setFocusable(false);
        setHorizontalAlignment(JButton.CENTER);
        setHorizontalTextPosition(JButton.CENTER);
        setBackground(Constants.BACK_COLOR);
        setForeground(Constants.FORE_COLOR);
        setFont(Constants.BOLD_20);
    }
}
