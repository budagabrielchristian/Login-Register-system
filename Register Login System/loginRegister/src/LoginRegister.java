import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginRegister implements ActionListener {
   private JFrame frame;
   private JButton registerButton, alreadyHaveAccount, loginButton, noAccount;
   private JTextField usernameField;
   private JPasswordField passwordField;
   private JLabel confirmation;
   public Font textFont = new Font("Palatino Linotype", Font.PLAIN, 35);
   public Font smallFont = new Font("Palatino Linotype", Font.PLAIN, 20);
   private int addedAccounts = 0;
   private HashMap<String, String> checkData = new HashMap<>();
   private File passwordsFile = new File("passwords.txt");
   private File usernamesFile = new File("usernames.txt");
   LoginRegister() {
      confirmation = new JLabel();
      confirmation.setFont(smallFont);

      registerButton = new JButton("Register");
      setupButton(registerButton, true);

      noAccount = new JButton("Don't have an account");
      setupButton(noAccount, false);

      alreadyHaveAccount = new JButton("Already have an account");
      setupButton(alreadyHaveAccount, true);

      loginButton = new JButton("Login");
      setupButton(loginButton, false);

      usernameField = new JTextField("Username");
      usernameField.setFont(textFont);

      passwordField = new JPasswordField("Password");
      passwordField.setFont(textFont);

      frame = new JFrame();
      frame.setTitle("Register");
      frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
      frame.setSize(new Dimension(600, 420));
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setResizable(false);

      frame.add(usernameField);
      frame.add(passwordField);
      frame.add(registerButton);
      frame.add(noAccount);
      frame.add(alreadyHaveAccount);
      frame.add(loginButton);
      frame.add(confirmation);
      frame.setVisible(true);
   }

  public boolean containsSpaces(String str) {
      for (int i = 0; i < str.length(); ++i) {
         if (Character.isWhitespace(str.charAt(i))) {
            return true;
         }
      }
      return false;
   }
  public void setupButton(JButton button, boolean setVisible) {
      button.addActionListener(this);
      button.setFocusable(false);
      button.setFont(smallFont);
      button.setBackground(Color.white);
      button.setFont(smallFont);
      if (!setVisible) {
         button.setVisible(false);
      }
   }
  public void addAccounts() {
      if (addedAccounts == 0) {
         try {
            Scanner usernameScan = new Scanner(usernamesFile);
            Scanner passwordScan = new Scanner(passwordsFile);
            while (usernameScan.hasNextLine() && passwordScan.hasNextLine()) {
               String user = usernameScan.nextLine();
               String pass = passwordScan.nextLine();
               checkData.put(user, pass);
            }
            usernameScan.close();
            passwordScan.close();

         } catch (FileNotFoundException e1) {
            e1.printStackTrace();
         }
      }
      addedAccounts++;
   }
   @SuppressWarnings("resource") 
   @Override 
   public void actionPerformed(ActionEvent e) {
      String username = usernameField.getText();
      String password = passwordField.getText();

      if (e.getSource() == registerButton) {
         frame.setTitle("Register");
         confirmation.setText("");
         addAccounts();

         if (checkData.keySet().contains(username)) {
            confirmation.setText(
                "Account exists already, try this username: " + username + "1");
            return;
         }
         if (containsSpaces(username) || containsSpaces(password)) {
            confirmation.setText("No spaces allowed.");
            return;
         }
         try {
            BufferedWriter usernameData =
                new BufferedWriter(new FileWriter(usernamesFile, true));
            usernameData.write(username + "\n");
            usernameData.close();

            BufferedWriter passwordData =
                new BufferedWriter(new FileWriter(passwordsFile, true));
            passwordData.write(password + "\n");
            passwordData.close();

            confirmation.setText("Registration successful.");
         } catch (IOException e1) {
            e1.printStackTrace();
         }
      }
      if (e.getSource() == noAccount) {
         usernameField.setText("Username");
         confirmation.setText("");
         frame.setTitle("Register");
         loginButton.setVisible(false);
         registerButton.setVisible(true);
         alreadyHaveAccount.setVisible(true);
         noAccount.setVisible(false);
      }
      if (e.getSource() == alreadyHaveAccount) {
         usernameField.setText("Username");
         addedAccounts = 0;
         frame.setTitle("Login");
         confirmation.setText("");
         loginButton.setVisible(true);
         registerButton.setVisible(false);
         alreadyHaveAccount.setVisible(false);
         noAccount.setVisible(true);
      }
      if (e.getSource() == loginButton) {
         confirmation.setText("");
         username = usernameField.getText();
         password = passwordField.getText();
         addAccounts();
         for (String key : checkData.keySet()) {
            if (checkData.get(key).equals(password) && key.equals(username)) {
               confirmation.setText("Login successful! Welcome, " + username + ".");
               return;
            } else {
               confirmation.setText("Incorrect password or username.");
            }
         }
      }
   }
}