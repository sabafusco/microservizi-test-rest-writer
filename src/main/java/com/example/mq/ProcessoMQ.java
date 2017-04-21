/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.mq;

/**
 *
 * @author sabatinopc
 */
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class ProcessoMQ {

  private final static String QUEUE_NAME = "codaProcessi";

  public static boolean write(long id) throws Exception {
    
      try{
          
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.13.62");
        factory.setUsername("guest");
        factory.setPassword("guest");
        factory.setPort(5672);
        
        Connection connection = factory.newConnection();
        
        Channel channel = connection.createChannel();
        
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        
        String message = String.valueOf(id);
        
        channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
        System.out.println(" Lasciato messaggio sulla coda. Id processo-> '" + message + "'");

        channel.close();
        connection.close();
        return true;
        
      }catch(Exception e){
        
        e.printStackTrace();
        return false;

      }
        
  }
}
