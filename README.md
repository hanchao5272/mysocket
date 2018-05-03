# mysocket
Java网络编程的简单学习

## demo00-InetAddress简单学习

- InetAddress使用注意需要异常处理，抛出UnknownHostException
- InetAddress.getByName(String name):通过域名获取网络地址
- InetAddress.getByName(String ip):通过IP地址获取网络地址
- System.out.println(inetAddress):获取域名+IP地址
- inetAddress.getHostName():获取域名,无域名则返回IP地址
- inetAddress..getHostAddress():获取IP地址
- InetAddress.getLocalHost():获取本机网络地址

## demo01-原始echo
