package pinar;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Date;


public class Message implements Serializable{
   /**
    * 
    */
   private static final long serialVersionUID = 1L;
   private String content;
   private String from;
   private Date timestamp;
   
   public Message(String from, String msg) {
      this.from = from;
      content = msg;
   }
   
   public String getContent() {
      return content;
   }
   
   public String getFrom() {
      return from;
   }
   public void setFrom(String from) {
      this.from = from;
   }

   public Date getTimestamp() {
      return timestamp;
   }
   
   public void setTimestamp(Date timestamp) {
      this.timestamp = timestamp;
   }
   
   private void writeObject(ObjectOutputStream out) 
   {
      try {
         out.defaultWriteObject();
      } catch (IOException e) {
         e.printStackTrace();
      }
   }

   private void readObject(ObjectInputStream in)
   {
     try {
        in.defaultReadObject();
     } catch (IOException e) {
        e.printStackTrace();
     } catch (ClassNotFoundException e) {
        e.printStackTrace();
     }
   }
   
   public static Message byteToMessage(byte[] bytes) {
      if (bytes == null) {
         return null;
      }
      
      Message message = null;
      
      try {
         ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
         message = (Message) new ObjectInputStream(bais).readObject();
      } catch (ClassNotFoundException e) {
         e.printStackTrace();
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      
      return message;
   }
   
   public static byte[] getAsByteArray(Message m) {
      ByteArrayOutputStream bArrayOutputStream = new ByteArrayOutputStream();
      ObjectOutputStream oStream;
      try {
         oStream = new ObjectOutputStream(bArrayOutputStream);
         oStream.writeObject (m);
      } catch (IOException e) {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      
      return bArrayOutputStream.toByteArray();
   }
}