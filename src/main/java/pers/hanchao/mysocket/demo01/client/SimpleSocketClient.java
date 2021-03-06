package pers.hanchao.mysocket.demo01.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * <p>最简单的Socket客户端-进行一次会话</p>
 *
 * @author hanchao 2018/5/3 22:35
 **/
public class SimpleSocketClient {
    /**
     * <p></p>
     *
     * @author hanchao 2018/5/3 22:35
     **/
    public static void main(String[] args) {
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
            //获取输出流通道
            os = clientSocket.getOutputStream();
            //向服务端发送hello world
            os.write("Hello World!".getBytes());
            //获取输入流通道
            is = clientSocket.getInputStream();
            //获取服务的返回的数据
            byte[] bytes = new byte[1024];
            int length = is.read(bytes);
            //输出从服务的获取的数据
            System.out.println("服务端的反馈信息：" + new String(bytes, 0, length));
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
