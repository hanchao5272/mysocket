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

- 服务端
    1. 建立服务端监听：对端口号9527的socket请求进行监听：
        ```
        serverListener = new ServerSocket(9527);
        ```
    2. 阻塞的去获取一次连接：
        ```
        onceSocket = serverListener.accept();
        ```
    3. 获取输入流，接收数据=》处理数据=》获取输出流，返回数据=》关闭输出输入流
    4. 关闭本次连接
        ```
        onceSocket.close();
        ```
    5. 关闭服务监听
        ```
        serverListener.close();
        ```
- 客户端
    1. 建立客户端网络连接：建立一个到主机"127.0.0.1"的9527端口号的网络连接：
        ```
        clientSocket = new Socket("127.0.0.1", 9527);
        ```
    2. 获取输出流，发送数据=》获取输入流，接收数据=》关闭输入输出流
    3. 关闭客户端连接
        ```
        clientSocket.close();
        ```

## demo02-复用Socket连接
网络连接需要解决的第1个问题：如何复用Socket连接，到达一次连接多次会话的目的

方法：客户端多次发送，服务端轮询获取。

- 客户端
    1. 定义是否断开连接：
        ```
        boolean disconnect = false;
        ```
    2. 如果没超时，就一直等待连接：
        ```
        while(!disconnect){//...}
        ```
    3. 接收客户端发送来的信息
        ```
        byte[] bytes = new byte[1024];
        int length = is.read(bytes);
        ```
    4. 如果读取的byte数组长度为-1，表示客户端关闭了本次连接，服务端也进行关闭
        ```
        if (-1 == length){ disconnect = true;}
        ```
- 客户端
    - 多次进行消息发送，直到无需再发送消息

## demo03-多路复用Socket连接
多个客户端，每个客户端进行多次会话；提供多个客户端连接，每个客户端连接可以进行多次会话

实现方式：多线程

- 服务端
    ```
    try{
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
    }
    ```

