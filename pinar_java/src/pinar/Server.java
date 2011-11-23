package pinar;

import java.io.*;
import java.net.*;

import javax.swing.JTextArea;

public class Server extends Thread {
   int port;
   JTextArea textArea;

   public void run() {
      //DatagramSocket serverSocket;
      try {
         //serverSocket = new DatagramSocket(port);
         ServerSocket serverSocket = new ServerSocket(port);
         while (true) {
            //byte[] receiveData = new byte[1024];
           // DatagramPacket receivePacket = new DatagramPacket(receiveData,
            //      receiveData.length);
            //serverSocket.receive(receivePacket);
            Socket sock = serverSocket.accept();
            BufferedInputStream bis = new BufferedInputStream(sock.getInputStream());
            ObjectInputStream ois = new ObjectInputStream(bis);
            Message recvdMessage = null;
            try {
               recvdMessage = (Message)ois.readObject();
            } catch (ClassNotFoundException e) {
               // TODO Auto-generated catch block
               e.printStackTrace();
            }
            //Message recvdMessage = Message.byteToMessage(receiveData);
            String sentence = recvdMessage.getContent();
            System.out.println(sentence);
            textArea.append(recvdMessage.getFrom() + ": " + sentence + "\n");

         }
      } catch (SocketException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (UnknownHostException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }

   }

   public Server(int portNo, JTextArea textArea) {
      this.port = portNo;
      this.textArea = textArea;
   }
}
