package view.frames;

import controller.server.request.LoginRequest;
import controller.server.sender.RequestSender;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {

    private static LoginFrame instance;
    private JButton loginButton;
    private JButton signupButton;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPanel mainPanel;
    private boolean isLoginReq = false;

    public static LoginFrame getInstance() {
        if (instance == null) {
            try {
                instance = new LoginFrame();
                return instance;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    public LoginFrame() {
        createUI();
    }

    private void createUI() {
        setTitle("Login/Signup");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        loginButton = new JButton("Login");
        signupButton = new JButton("Signup");

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLoginSignupFields();
                isLoginReq = true;
            }
        });

        signupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLoginSignupFields();
                isLoginReq = false;
            }
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        mainPanel.add(loginButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(signupButton, gbc);

        add(mainPanel);
    }

    private void showLoginSignupFields() {
        mainPanel.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel usernameLabel = new JLabel("Username:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(usernameLabel, gbc);

        usernameField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        mainPanel.add(passwordField, gbc);

        JButton submitButton = new JButton("Submit");
        gbc.gridx = 1;
        gbc.gridy = 2;
        mainPanel.add(submitButton, gbc);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleSubmit();
            }
        });

        JButton backButton = new JButton("Back");
        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(backButton, gbc);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showInitialButtons();
            }
        });

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void showInitialButtons() {
        mainPanel.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        mainPanel.add(loginButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        mainPanel.add(signupButton, gbc);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void handleSubmit() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        try {
            if (isLoginReq) {
                String response = RequestSender.getInstance().sendRequest(new LoginRequest(username, password));
//                if (!response.equals("User Not Found!")) {
//                    JOptionPane.showMessageDialog(this, "Welcome!");
//                    dispose();
//                    return;
//                }
                JOptionPane.showMessageDialog(this, response);
            } else {
//                String response = RequestSender.getInstance().sendRequest(new SignUpRequest(username, password));
//                JOptionPane.showMessageDialog(this, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
