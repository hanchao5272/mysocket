package pers.hanchao.mysocket.demo03.client;

import org.apache.commons.lang3.RandomUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>可复用的多个Socket客户端-多个客户端，每个客户端进行多次会话</p>
 *
 * @author hanchao 2018/5/3 22:35
 **/
public class MultiConnectionSocketClient {
    /**
     * <p>
     *     一个客户端连接
     * </p>
     * @author hanchao 2018/5/3 23:37
     **/
    static class ClientSocket implements Runnable{

        @Override
        public void run() {
            //定义客户端连接
            Socket clientSocket = null;
            //定义输出流
            OutputStream os = null;
            //定义输入流
            InputStream is = null;
            //注意抛出异常：IOException
            try {
                //建立客户端网络连接：建立一个到主机"127.0.0.1"的9527端口号的网络连接
                clientSocket = new Socket("127.0.0.1", 9527);
                //获取输出流/输入流通道
                os = clientSocket.getOutputStream();
                is = clientSocket.getInputStream();
                //随机进行若干次会话
                int number = RandomUtils.nextInt(5,10);
                for (int i = 0; i < number; i++) {
                    //向服务端发送hello world
                    String message = Thread.currentThread() + "-" + RandomUtils.nextInt(1000,2000);
                    os.write(message.getBytes());
                    //获取服务的返回的数据
                    byte[] bytes = new byte[1024];
                    int length = is.read(bytes);
                    //输出从服务的获取的数据
                    System.out.println("服务端的反馈信息：" + new String(bytes, 0, length));
                    try {
                        //发送完成之后，休息1到2秒
                        Thread.sleep(RandomUtils.nextInt(1000,2000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    //关闭输入输出流
                    is.close();
                    os.close();
                    //关闭客户端连接
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * <p></p>
     *
     * @author hanchao 2018/5/3 22:35
     **/
    public static void main(String[] args) throws InterruptedException {
        //建立一个大小为5的线程
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for (int i = 0; i < 10; i++) {
            System.out.println("建立第" + i + "个客户端网络连接...");
            executorService.submit(new ClientSocket());
            Thread.sleep(RandomUtils.nextInt(1000,1500));
        }
        executorService.shutdown();
    }
}
