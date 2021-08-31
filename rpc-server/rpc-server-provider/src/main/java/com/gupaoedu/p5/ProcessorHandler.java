package com.gupaoedu.p5;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * 通过
 */
public class ProcessorHandler implements Runnable{
    Socket socket;
    Object service;

    public ProcessorHandler(Socket socket, Object service) {
        this.socket = socket;
        this.service = service;
    }

    @Override
    public void run() {
        ObjectInputStream inputStream=null;
        ObjectOutputStream outputStream=null;

        try {
            inputStream=new ObjectInputStream(socket.getInputStream());
            //可以拿到那些信息？需要那些信息
            //反序列化
            RpcRequest request=(RpcRequest)inputStream.readObject();
            /***
             * 代码标注: 客户端动态代理调用后真正执行到的方法
             * stream流的作用：传递requst序列化请求
             */
            Object result=inovke(request);  //
            outputStream=new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(result);
            outputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //TODO 关闭流
        }
    }

    private Object inovke(RpcRequest request) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class clazz=Class.forName(request.getClassName());
        Method method=clazz.getMethod(request.getMethodName(),request.getTypes());
        /***
         * 代码标注：远程客户端sevice.sayHello() 实际调用server方法
         */
        Object result=method.invoke(service,request.getParameters());  //
        return result;
    }
}
