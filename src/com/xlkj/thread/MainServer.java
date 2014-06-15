/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.xlkj.thread;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
/**
 *
 * @author Administrator
 */
public class MainServer {
   private static final int port=3690; 
    
    public static void main(String ars[])throws Exception{
        MainServer ms=new MainServer();
        ms.startServer();
    }
    /*服务启动*/
    public void startServer() throws Exception{
         ServerSocket server=new ServerSocket(port);
          new MyServer(server).start();
        System.out.println("服务器开启等待连接");
    }
    /*接受socke线程*/
    class MyServer extends Thread{
         private ServerSocket ss;
        public MyServer(ServerSocket ss) {
            this.ss = ss;
        } 
        public void run(){
          while(true){  
          try{
              Socket socket=ss.accept();
              System.out.println("新客户端连接 "+socket.getPort());
              /*交给新的收发线程处理*/
              MyThread mt=new MyThread(socket);
              mt.startRecSend();
              }catch(Exception e){
                
               e.printStackTrace();
               
              }
            
            
        }
        }
    }
}
