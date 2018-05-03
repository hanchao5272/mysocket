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
最简单的echo服务器：将客户端发送的内容再原封不动的反馈给客户端，只进行一次。
服务端
01.建立服务端监听：对端口号9527的socket请求进行监听
   serverListener = new ServerSocket(9527);
02.阻塞的去获取一次连接
   onceSocket = serverListener.accept();
03.获取输入流，接收数据=》处理数据=》获取输出流，返回数据=》关闭输出输入流
04.关闭本次连接onceSocket.close();
05.关闭服务监听serverListener.close();
客户端
01.建立客户端网络连接：建立一个到主机"127.0.0.1"的9527端口号的网络连接
   clientSocket = new Socket("127.0.0.1", 9527);
02.获取输出流，发送数据=》获取输入流，接收数据=》关闭输入输出流
03.关闭客户端连接clientSocket.close();