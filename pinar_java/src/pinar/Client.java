package pinar;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.*;

class Client
{
   private String ipAddress;
   private int serverPort;
   private String name;
   
   public  Client(String name, String ipAddress, int serverPort){
      this.name = name;
      this.ipAddress = ipAddress;
      this.serverPort = serverPort;
   }
   
   public void sendMessage(String msg) {
      Socket socket;
      try {
         socket = new Socket(ipAddress, serverPort);
         //InetAddress inetAddress = InetAddress.getByName(ipAddress);
         BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
         ObjectOutputStream os = new ObjectOutputStream(bos);
         Message message = new Message(this.name, msg);
         //byte[] byteArray = Message.getAsByteArray(message);
         //DatagramPacket sendPacket = new DatagramPacket(byteArray, byteArray.length, inetAddress, serverPort);
         //clientSocket.send(sendPacket);
         //clientSocket.close();
         os.flush();
         os.writeObject(message);
         os.flush();
         System.out.println("sent message: " + msg + "to ip:" + ipAddress + " port:" + serverPort);
      } catch (SocketException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      } catch (UnknownHostException e) {
         e.printStackTrace();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}