package pers.hanchao.mysocket.demo03.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

/**
 * <p>可复用的Socket服务端-提供多个客户端连接，每个客户端连接可以进行多次会话</p>
 *
 * @author hanchao 2018/5/3 22:43
 **/
public class MultiConnectionSocketServer {
    /**
     * <p>一次连接</p>
     *
     * @author hanchao 2018/5/3 23:42
     **/
    static class ConnectionSocket implements Runnable {
        private Socket onceSocket;

        public ConnectionSocket(Socket socket) {
            this.onceSocket = socket;
        }

        @Override
        public void run() {
            //定义输入输出流
            InputStream is = null;
            OutputStream os = null;
            //抛出异常IOException
            try {
                //获取这次连接的输入流/输出流
                is = onceSocket.getInputStream();
                os = onceSocket.getOutputStream();
                //定义是否断开连接
                boolean disconnect = false;
                //如果没超时，就一直等待连接
                while (!disconnect) {
                    //接收客户端发送来的信息
                    byte[] bytes = new byte[1024];
                    int length = is.read(bytes);
                    //如果读取的byte数组长度为-1，表示客户端关闭了本次连接，服务端也进行关闭
                    if (-1 == length) {
                        System.out.println(Thread.currentThread() + "-客户端断开本次连接...");
                        disconnect = true;
                    } else {
                        //打印信息
                        System.out.println(Thread.currentThread() + "-接收信息：" + new String(bytes, 0, length));
                        //向客户端发送数据
                        os.write(bytes, 0, length);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    //关闭输出输入流
                    os.close();
                    is.close();
                    //关闭这次连接
                    onceSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * <p></p>
     *
     * @author hanchao 2018/5/3 22:44
     **/
    public static void main(String[] args) {
        //定义服务端网络连接
        ServerSocket serverListener = null;
        //定义一次服务连接
        Socket onceSocket = null;
        //抛出异常IOException
        try {
            //建立服务端监听，对端口号9527的socket请求进行监听
            serverListener = new ServerSocket(9527);
            //如果等待5秒没有接受到消息，则关闭监听
            serverListener.setSoTimeout(5000);
            //一直去监听
            while (true) {
                //阻塞的去获取一次连接
                onceSocket = serverListener.accept();
                //每个连接建立一个线程去处理
                new Thread(new ConnectionSocket(onceSocket)).start();
            }
        } catch (SocketTimeoutException e) {
            //等待时间超过5秒，不再等待，关闭连接
            //e.printStackTrace();
            System.out.println("等待超时...");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭服务端端口监听
                System.out.println("服务端关闭监听.");
                serverListener.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
