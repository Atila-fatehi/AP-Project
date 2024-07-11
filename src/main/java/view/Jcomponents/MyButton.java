package view.Jcomponents;

import controller.util.Constants;

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

    public void changeColor() {
        setBackground(Constants.ANOTHER_FORE_COLOR);
    }

    public void resetColor(){
        setBackground(Constants.BACK_COLOR);
    }
}
