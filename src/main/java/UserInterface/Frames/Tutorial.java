package UserInterface.Frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Tutorial extends JFrame {
    private static final Color back = new Color(0x9A1A03);
    private static final Color fore = new Color(0xFB8B24);
    private static final Color anotherFore = new Color(0x074E9C);

    public Tutorial() {
        getContentPane().setBackground(new Color(0x000000));
        setTitle("Skill Tree");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
        setResizable(false);

        JLabel label1 = new JLabel("PEACE WAS NEVER AN OPTION");
        label1.setHorizontalAlignment(JLabel.CENTER);
        label1.setBounds(300, 110, 500, 100);
        label1.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25));
        label1.setForeground(anotherFore);
        add(label1);

        JButton button4 = new JButton("Back");
        button4.setBounds(400, 550, 300, 50);
        button4.setFocusable(false);
        button4.setHorizontalAlignment(JButton.CENTER);
        button4.setHorizontalTextPosition(JButton.CENTER);
        button4.setBackground(back);
        button4.setForeground(fore);
        button4.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25));
        add(button4);
        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new MainMenu();
            }
        });
    }
}
