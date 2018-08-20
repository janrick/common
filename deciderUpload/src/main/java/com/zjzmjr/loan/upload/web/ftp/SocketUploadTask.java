package com.zjzmjr.loan.upload.web.ftp;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zjzmjr.common.socket.SocketUploadDispatcher;
import com.zjzmjr.core.base.util.PropertyUtils;
import com.zjzmjr.core.base.util.StringUtil;

/**
 * 服务器端接收客户端的文件传输服务
 * 
 * @author oms
 * @version $Id: SocketUploadTask.java, v 0.1 2017-9-18 上午10:29:18 oms Exp $
 */
public class SocketUploadTask {

    private static final Logger logger = LoggerFactory.getLogger(SocketUploadTask.class);

    /** socket端口 */
    private static final String socketPort = PropertyUtils.getPropertyFromFile("system", "socket.port");
    
    /** socket文件保存地址 */
    private static final String socketSavePath = PropertyUtils.getPropertyFromFile("system", "socket.upload.savepath");

    private static SocketUploadDispatcher dispatcher = null;
    
    static {
        // 实例化成一个对象，防治多次启动
        dispatcher = new SocketUploadDispatcher(StringUtil.nullToInteger(socketPort), socketSavePath);
    }
    
    /**
     * 
     */
    public void execute(){
        ExecutorService service = null;
        try {
            logger.info("启动服务器中。。。");
            if(StringUtils.isNotBlank(socketSavePath)){
                service = Executors.newSingleThreadExecutor();
                service.execute(new Runnable() {
                    @Override
                    public void run() {
                        if (dispatcher.isShutdown()) {
                            dispatcher.start();
                        }
                    }
                });
                logger.info("上传服务器启动成功");
            }else{
                logger.error("文件的上传保存路径没有设置");
            }
        }catch(Exception ex){
            logger.error("文件上传的服务启动出错:", ex);
            if(service != null ) {
                if(!service.isShutdown()){
                    service.shutdown();
                }
                service = null;
            }
        }
    }
    
}
