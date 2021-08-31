package com.gupaoedu.p5;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 腾讯课堂搜索【咕泡学院】
 * 官网：www.gupaoedu.com
 * 风骚的Mic 老师
 * create-date: 2019/12/16-18:17
 */
public class RemoteInovcationHandler implements InvocationHandler {

    private String host;
    private int port;

    public RemoteInovcationHandler(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest request=new RpcRequest();
        request.setClassName(method.getDeclaringClass().getName());
        request.setMethodName(method.getName());
        request.setParameters(args);
        request.setTypes(method.getParameterTypes());
        RpcNetTransport rpcNetTransport=new RpcNetTransport(host,port);
        /***
         * 代码标注:
         * stream流的作用：传递requst序列化请求
         * 发送request请求(序列化的对象)给server，server收到后依照request去server调用相关方法
         */
        Object result=rpcNetTransport.send(request);  //
        return result;
    }
}
