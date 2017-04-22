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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties
public class ProcessoMQ {

    private final static String QUEUE_NAME = "codaProcessi";
    private static String rabbitHost;
    private static int rabbitPort;
    private static String rabbitUser;
    private static String rabbitPass;  
  
  public static boolean write(long id) throws Exception {
    
      try{
        
          
          
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(rabbitHost);
        factory.setUsername(rabbitUser);
        factory.setPassword(rabbitPass);
        factory.setPort(rabbitPort);
        
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
  
    @Value("${rabbit.host}")
    public  void setRabbitHost(String rabbitHost) {
        ProcessoMQ.rabbitHost = rabbitHost;
    }
    
    @Value("${rabbit.port}")
    public void setRabbitPort(int rabbitPort) {
        ProcessoMQ.rabbitPort = rabbitPort;
    }

    @Value("${rabbit.user}")
    public void setRabbitUser(String rabbitUser) {
        ProcessoMQ.rabbitUser = rabbitUser;
    }

    @Value("${rabbit.pass}")
    public void setRabbitPass(String rabbitPass) {
        ProcessoMQ.rabbitPass = rabbitPass;
    }
  
  
  
}
