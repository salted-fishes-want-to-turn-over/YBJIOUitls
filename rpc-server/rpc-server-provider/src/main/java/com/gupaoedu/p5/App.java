package com.gupaoedu.p5;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        IHelloService service=new HelloServiceImpl();
        RpcProxyServer proxyServer=new RpcProxyServer();
        proxyServer.publisher(service,8888);
        System.out.println( "Hello World!" );
    }
}
