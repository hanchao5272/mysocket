package pers.hanchao.mysocket.demo00;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * <p>InetAddress简单示例</p>
 *
 * @author hanchao 2018/5/3 22:17
 **/
public class InetAddressDemo {
    /**
     * <p></p>
     *
     * @author hanchao 2018/5/3 22:18
     **/
    public static void main(String[] args) {
        //注意需要异常处理，抛出UnknownHostException
        try {
            //通过域名获取网络地址==》一个域名一定对应一个IP地址
            InetAddress inetAddress1 = InetAddress.getByName("www.baidu.com");
            //获取域名+IP地址
            System.out.println(inetAddress1);
            //获取域名
            System.out.println(inetAddress1.getHostName());
            //获取IP地址
            System.out.println(inetAddress1.getHostAddress());

            System.out.println();
            //通过IP地址获取网络地址=》一个IP地址可能对应多个域名
            InetAddress inetAddress2 = InetAddress.getByName("61.135.169.125");
            //获取域名+地址
            System.out.println(inetAddress2);
            //获取域名
            System.out.println(inetAddress2.getHostName());
            //获取地址
            System.out.println(inetAddress2.getHostAddress());

            System.out.println();
            //获取本机IP地址
            InetAddress inetAddress3 = InetAddress.getLocalHost();
            System.out.println(inetAddress3);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }
}
