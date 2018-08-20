package com.zjzmjr.loan.upload.web.demo;

import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class HelloWorldClient {

    public static void main(String[] args) {
//        JaxWsProxyFactoryBean svr = new JaxWsProxyFactoryBean();
//        svr.setServiceClass(HelloWorld.class);
//        svr.setAddress("http://localhost/helloWorld");
//        HelloWorld hw = (HelloWorld) svr.create();
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("../applicationContext.xml");
        HelloWorld hw = (HelloWorld) context.getBean("client");
        // User user = new User();
        // user.setName("Tony");
        // user.setDescription("test");
        System.out.println(hw.sayHi("world"));
    }
}
