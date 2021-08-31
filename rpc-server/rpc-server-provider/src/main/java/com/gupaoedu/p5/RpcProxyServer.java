package com.gupaoedu.p5;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/12/16-18:06
 */
public class RpcProxyServer {

    private final ExecutorService executorService= Executors.newCachedThreadPool();

    public void publisher(Object service,int port){
        ServerSocket serverSocket=null;
        try {
            serverSocket=new ServerSocket(port);
            while(true){
                final Socket socket=serverSocket.accept();
                executorService.execute(new ProcessorHandler(socket,service));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            //TODO
        }
    }
}
