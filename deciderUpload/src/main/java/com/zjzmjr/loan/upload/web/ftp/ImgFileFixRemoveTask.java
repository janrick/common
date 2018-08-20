package com.zjzmjr.loan.upload.web.ftp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zjzmjr.common.socket.ClientSocketAcceptor;

/**
 * 定期将阿里云服务器上的文件迁移到本地服务器上
 * 
 * @author oms
 * @version $Id: ImgFileFixRemove.java, v 0.1 2017-9-15 下午1:18:22 oms Exp $
 */
public class ImgFileFixRemoveTask {

    private static final Logger logger = LoggerFactory.getLogger(ImgFileFixRemoveTask.class);
    
    public void execute() {
        System.out.println("连接服务器");
        logger.info("连接服务器");
        ClientSocketAcceptor acceptor = new ClientSocketAcceptor("115.236.166.66", 8099);
        boolean isConnect = acceptor.sendFile2Server("D:\\FTP\\1393583130719.jpg");
        if(isConnect){
            System.out.println("连接成功");
            logger.info("连接成功");
        }else{
            System.out.println("连接失败");
            logger.error("连接失败");
        }
    }
}
