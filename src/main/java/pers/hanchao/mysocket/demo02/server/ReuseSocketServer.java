package pers.hanchao.mysocket.demo02.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * <p>可复用的Socket服务端-提供多次会话</p>
 *
 * @author hanchao 2018/5/3 22:43
 **/
public class ReuseSocketServer {
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
        //定义输入输出流
        InputStream is = null;
        OutputStream os = null;
        //抛出异常IOException
        try {
            //建立服务端监听，对端口号9527的socket请求进行监听
            serverListener = new ServerSocket(9527);
            //阻塞的去获取一次连接
            onceSocket = serverListener.accept();
            //获取这次连接的输入流/输出流
            is = onceSocket.getInputStream();
            os = onceSocket.getOutputStream();
            //定义是否断开连接
            boolean disconnect = false;
            //如果没超时，就一直等待连接
            while(!disconnect){
                //接收客户端发送来的信息
                byte[] bytes = new byte[1024];
                int length = is.read(bytes);
                //如果读取的byte数组长度为-1，表示客户端关闭了本次连接，服务端也进行关闭
                if (-1 == length){
                    System.out.println("客户端断开本次连接...");
                    disconnect = true;
                }else{
                    //打印信息
                    System.out.println("接收信息：" + new String(bytes, 0, length));
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
                //关闭服务端端口监听
                serverListener.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
