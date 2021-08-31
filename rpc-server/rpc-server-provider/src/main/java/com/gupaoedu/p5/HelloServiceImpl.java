package com.gupaoedu.p5;

/**

 * create-date: 2019/12/16-18:04
 */
public class HelloServiceImpl implements IHelloService{

    @Override
    public String sayHello(String content) {
        return "Hello Content:"+content;
    }
}
