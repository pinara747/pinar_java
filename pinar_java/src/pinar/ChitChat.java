package pinar;


/**
 * To run as server:
 *    java pinar/ChitChat server
 * To run as client:
 *    java pinar/ChitChat
 *    
 *    Server listens at port 12345 by default.
 * 
 */
import java.awt.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.*;
import java.util.Random;

import javax.swing.*;

public class ChitChat extends JPanel implements ActionListener {
   private Client client = null;
   private Server server = null;
   private final static String QUIT = "quit";
   protected JTextField textField;
   protected JTextArea textArea;
   protected JTextArea textArea2;
   private static boolean isServer = false;
   private static int serverPort = 12345;
   private static JFrame frame;

   public ChitChat() {
      super(new GridBagLayout());

      if (!isServer) {
         textField = new JTextField(30);
         textField.addActionListener(this);
         textField.setText("enter messages here...");
         textField.setForeground(Color.LIGHT_GRAY);
      }
      textArea = new JTextArea(10, 30);
      textArea.setForeground(Color.YELLOW);
      textArea.setBackground(Color.DARK_GRAY);
      Font font = new Font("Verdana", Font.ITALIC, 11);
      textArea.setFont(font);
      textArea.setEditable(false);
      JScrollPane scrollPane = new JScrollPane(textArea);
      GridBagConstraints c = new GridBagConstraints();
      c.gridwidth = GridBagConstraints.REMAINDER;
      c.fill = GridBagConstraints.HORIZONTAL;

      if (!isServer) {
         add(textField, c);
      }

      c.fill = GridBagConstraints.BOTH;
      c.weightx = 1.0;
      c.weighty = 1.0;
      add(scrollPane, c);

      try {
         if (isServer) {
            textArea.setText("server listening at port " + serverPort + "...\n");
            server = new Server(serverPort, textArea);
            server.start();

         } else {
            Random r = new Random(System.currentTimeMillis());
            int next = r.nextInt(100);
            String clientName = "client" + next;
            client = new Client(clientName, "localhost", serverPort);
            frame.setTitle(clientName);
         }
      } catch (Exception e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }

   }

   public void actionPerformed(ActionEvent evt) {
      String text = textField.getText();
      if (text.equalsIgnoreCase(QUIT)) {
         System.exit(0);
      } else {
         if (client != null) {
            client.sendMessage(text);
         }
      }

      textField.setText("");
      textArea.append(": " + text + "\n");
      textArea.setCaretPosition(textArea.getDocument().getLength());
   }

   private static void createAndShowGUI() {
      String title = "client";
      if (isServer) {
         title = "server";
      }
      frame = new JFrame(title);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.add(new ChitChat());
      frame.pack();
      frame.setVisible(true);

   }

   public static void main(String[] args) {
      if (args.length > 0) {
         isServer = true;
      }

      javax.swing.SwingUtilities.invokeLater(new Runnable() {
         public void run() {
            createAndShowGUI();
         }
      });
   }
}