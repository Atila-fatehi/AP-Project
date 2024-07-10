package view.gameGUI;

import controller.KeyController;
import controller.MouseController;
import controller.util.Constants;

import javax.swing.*;

public class AdditionalPanel extends JPanel {
    public AdditionalPanel() {
        setFocusable(true);
        setLayout(null);
        setBackground(Constants.DARK_BLUE);
        setBounds(100, 100, 200,200);
    }
}
