package UserInterface.Frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Scanner;

public class SkillTree extends JFrame {
    private static final Color back = new Color(0x9A1A03);
    private static final Color fore = new Color(0xFB8B24);
    private static final Color anotherFore = new Color(0x074E9C);

    public SkillTree() {
        getContentPane().setBackground(new Color(0x000000));
        setTitle("Skill Tree");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 700);
        setLocationRelativeTo(null);
        setLayout(null);
        setVisible(true);
        setResizable(false);

        JLabel label = new JLabel("Attack");JLabel labell = new JLabel("Writ of Ares");
        label.setHorizontalAlignment(JLabel.CENTER);labell.setHorizontalAlignment(JLabel.CENTER);
        label.setBounds(100, 110, 300, 100);labell.setBounds(100, 140, 300, 100);
        label.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25));labell.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25));
        label.setForeground(anotherFore);labell.setForeground(new Color(0xFB8B24));
        add(label);add(labell);

        JLabel label1 = new JLabel("Defence");JLabel labell1 = new JLabel("Writ of Aceso");
        label1.setHorizontalAlignment(JLabel.CENTER);labell1.setHorizontalAlignment(JLabel.CENTER);
        label1.setBounds(400, 110, 300, 100);labell1.setBounds(400, 140, 300, 100);
        label1.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25));labell1.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25));
        label1.setForeground(anotherFore);labell1.setForeground(fore);
        add(label1);add(labell1);

        JLabel label2 = new JLabel("Shape Shift");JLabel labell2 = new JLabel("Writ of Proteus");
        label2.setHorizontalAlignment(JLabel.CENTER);labell2.setHorizontalAlignment(JLabel.CENTER);
        label2.setBounds(700, 110, 300, 100);labell2.setBounds(700, 140, 300, 100);
        label2.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25));labell2.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25));
        label2.setForeground(anotherFore);labell2.setForeground(fore);
        add(label2);add(labell2);

        JLabel label3 = new JLabel("XP : ");
        label3.setHorizontalAlignment(JLabel.CENTER);
        label3.setBounds(400, 10, 300, 50);
        label3.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25));
        label3.setForeground(new Color(0xFB8B24));
        add(label3);

        JButton button1 = new JButton("500 XP");
        button1.setBounds(450, 220, 200, 200);
        button1.setFocusable(false);
        button1.setHorizontalAlignment(JButton.CENTER);
        button1.setHorizontalTextPosition(JButton.CENTER);
        button1.setBackground(back);
        button1.setForeground(fore);
        button1.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25));
        add(button1);

        JButton button2 = new JButton("750 XP");
        button2.setBounds(150, 220, 200, 200);
        button2.setFocusable(false);
        button2.setHorizontalAlignment(JButton.CENTER);
        button2.setHorizontalTextPosition(JButton.CENTER);
        button2.setBackground(back);
        button2.setForeground(fore);
        button2.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25));
        add(button2);

        JButton button3 = new JButton("1000 XP");
        button3.setBounds(750, 220, 200, 200);
        button3.setFocusable(false);
        button3.setHorizontalAlignment(JButton.CENTER);
        button3.setHorizontalTextPosition(JButton.CENTER);
        button3.setBackground(back);
        button3.setForeground(fore);
        button3.setFont(new Font("HelveticaNeue-CondensedBlack", Font.BOLD, 25));
        add(button3);

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
