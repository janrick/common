package com.zjzmjr.loan.upload.web.demo;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * 
 * 
 * @author oms
 * @version $Id: HelloWorld.java, v 0.1 2017-6-1 下午3:50:23 oms Exp $
 */
@WebService
public interface HelloWorld {

    String sayHi(@WebParam(name = "text") String text);
    // String sayHiToUser(User user);
    // String[] SayHiToUserList(List<User> userList);
}
