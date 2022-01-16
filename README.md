https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white
# Login-Register-system
Create your account, or login if you have one! 
This project is regarding a register/login system. It takes in 2 files [usernames.txt, passwords.txt] and makes pairs in a HashMap<String, String>.

I have replaced multiple repeated lines of code with functions, as you may notice with the buttons and adding the accounts in the accounts Hashmap. This is currently nothing
but a small experiment of mine.

[The functions I mentioned above are the following: ]
```
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
```

If you want to use it for logins only you can remove the register function and make a new JPanel of your own once you login, a new program perhaps..
