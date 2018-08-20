package com.zjzmjr.loan.upload.web.demo;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * 
 * 
 * @author oms
 * @version $Id: HelloWorldImpl.java, v 0.1 2017-6-1 下午3:52:59 oms Exp $
 */
@WebService(endpointInterface = "com.zjzmjr.decider.upload.web.demo.HelloWorld", serviceName = "HelloWorld")
public class HelloWorldImpl implements HelloWorld {

    @Override
    public String sayHi(@WebParam(name = "text")String text) {
        return "hello" + text;
    }

}
