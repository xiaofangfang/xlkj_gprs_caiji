/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.xlkj.thread;
import com.xlkj.test.TestLog;
import java.lang.Runnable;
import java.io.*;
import java.net.Socket;
import org.apache.log4j.Logger;
/**
 *
 * @author yuhaifang
 */
public class MyThread {
     private static Logger logger = Logger.getLogger(TestLog.class);
     private static Logger loggevent = Logger.getLogger("logevent");
    private Socket socket;
    private Thread t1;
    private Thread t2;
    public MyThread(Socket socket) {
        this.socket = socket;
    }
    /*发送线程*/
    public void startRecSend() throws Exception{
         t1=new Thread(new SendThread(socket.getOutputStream()));
         t2=new Thread(new RecThread(socket.getInputStream()));
         t1.start();
         t2.start();
         logger.info("输入输出流启动");
         loggevent.info("loggevent begain to star lgo");
       
    }
    public void closeSocket(Socket s)throws Exception{
        s.close();
    }
    public static void main(String ags[]){ 
        
    }
 
    class SendThread implements Runnable{

        public OutputStream getOs() {
            return os;
        }

        public void setOs(OutputStream os) {
            this.os = os;
        }
        private OutputStream os;
        public SendThread(OutputStream os){
            this.os=os;
        }
        public void run(){
            while(true){
                String str="hello i coming";
                try{
                    os.write(str.getBytes());
                    os.flush();
                    logger.info("发送数据为:"+str);
                    loggevent.info("the send message is "+str);
                    Thread.sleep(5000);
                    
                }
                catch(Exception e){
                    e.printStackTrace();
                    try{
                        os.close();
                        socket.close();
                        logger.info("发送异常+"+e.getMessage());
                       
                    }catch(Exception e2){
                        break;
                    }
                   break;
                    
                }
            }
        }
    }
    /*接受线程*/
    class RecThread implements Runnable{

        public InputStream getIs() {
            return is;
        }

        public void setIs(InputStream is) {
            this.is = is;
        }

        public RecThread(InputStream is) {
            this.is = is;
        }
        private InputStream is;
        
        public void run(){
            while(true){
            int count=0;
     try{
            while(count==0)
          {
                 count=is.available();
          }
          byte[] b=new byte[count];  
          is.read(b);
          String str=new String(b);
          System.out.println("the reciver str is  "+str);
          logger.info("接受数据:"+str);
       }
        catch(Exception e){
            e.printStackTrace();
           try{
                is.close();
                socket.close();
                logger.info("接受异常:"+e.getMessage());
            }
            catch(Exception e2){
                e2.printStackTrace();
                break;
            }
            
               break;      
           }
        
      }
        }
    }
}
