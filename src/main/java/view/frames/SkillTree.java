package view.frames;

import controller.util.Constants;
import controller.FileController;
import view.Jcomponents.MyLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.PrintWriter;
import java.nio.file.Paths;

public class SkillTree extends JFrame {
    //TODO CLEAN THIS WHEN SKILL TREE
    private static final Color back = new Color(0x9A1A03);
    private static final Color fore = new Color(0xFB8B24);
    private static final Color anotherFore = new Color(0x074E9C);
    private int XP = 0;

    public SkillTree() {
        getContentPane().setBackground(Constants.DARK_BLUE);
        setTitle("Skill Tree");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Constants.SKILL_TREE_FRAME_WIDTH, Constants.SKILL_TREE_FRAME_HEIGHT);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        setVisible(true);

        MyLabel attack = new MyLabel("Attack" , 100 , 110 , Constants.LABEL_WIDTH , Constants.LABEL_HEIGHT);
        MyLabel writOfAres = new MyLabel("Writ of Ares" , 100 , 140 , Constants.LABEL_WIDTH , Constants.LABEL_HEIGHT);
        add(attack);
        add(writOfAres);

        MyLabel defence = new MyLabel("Defence" , 400 , 110 , Constants.LABEL_WIDTH , Constants.LABEL_HEIGHT);
        MyLabel writOfAceso = new MyLabel("Writ of Aceso" , 400 , 140 , Constants.LABEL_WIDTH , Constants.LABEL_HEIGHT);
        add(defence);
        add(writOfAceso);

        MyLabel shape = new MyLabel("Shape Shift" , 700 , 110 , Constants.LABEL_WIDTH , Constants.LABEL_HEIGHT);
        MyLabel writOfProteus = new MyLabel("Writ of Proteus" , 700 , 140 , Constants.LABEL_WIDTH , Constants.LABEL_HEIGHT);
        add(shape);
        add(writOfProteus);

        XP = Integer.parseInt(FileController.readXP());
        MyLabel xp = new MyLabel("XP : " + XP , 400 , 10, Constants.LABEL_WIDTH , Constants.LABEL_HEIGHT);
        add(xp);

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
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(XP >= 500) {
                    writeToFile(21);
                    XP -= 500;
                    File file = new File(Paths.get("").toAbsolutePath() + "/src/main/java/dataBase/XP.txt");
                    try {
                        PrintWriter printWriter = new PrintWriter(file);
                        printWriter.println(String.valueOf(XP));
                        printWriter.flush();
                        printWriter.close();
                    } catch (Exception eee) {

                    }
                    button1.setEnabled(false);
                    button2.setEnabled(true);
                    button3.setEnabled(true);
                    xp.setText("XP : " + XP);
                }
            }
        });
        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(XP >= 750) {
                    writeToFile(11);
                    XP -= 750;
                    button2.setEnabled(false);
                    button1.setEnabled(true);
                    button3.setEnabled(true);
                    xp.setText("XP : " + XP);
                    File file = new File(Paths.get("").toAbsolutePath() + "/src/main/java/dataBase/XP.txt");
                    try {
                        PrintWriter printWriter = new PrintWriter(file);
                        printWriter.println(String.valueOf(XP));
                        printWriter.flush();
                        printWriter.close();
                    } catch (Exception eee) {

                    }
                }
            }
        });
        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(XP >= 1000) {
                    writeToFile(31);
                    XP -= 1000;
                    button3.setEnabled(false);
                    button2.setEnabled(true);
                    button1.setEnabled(true);
                    xp.setText("XP : " + XP);
                    File file = new File(Paths.get("").toAbsolutePath() + "/src/main/java/dataBase/XP.txt");
                    try {
                        PrintWriter printWriter = new PrintWriter(file);
                        printWriter.println(String.valueOf(XP));
                        printWriter.flush();
                        printWriter.close();
                    } catch (Exception eee) {

                    }
                }
            }
        });
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
    public void writeToFile(int val){
        File file = new File(Constants.ABILITY_PATH);
        try {
            PrintWriter printWriter = new PrintWriter(file);
            printWriter.println(val);
            printWriter.flush();
            printWriter.close();
        }catch (Exception e){

        }


    }
}
