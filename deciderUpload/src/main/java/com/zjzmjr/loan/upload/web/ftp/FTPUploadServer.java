package com.zjzmjr.loan.upload.web.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.jws.WebParam;
import javax.jws.WebService;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zjzmjr.core.base.model.FileUploadManage;

/**
 * 
 * 
 * @author oms
 * @version $Id: FTPUploadServer.java, v 0.1 2017-6-14 下午2:11:11 oms Exp $
 */
@WebService(endpointInterface = "com.zjzmjr.core.base.model.FileUploadManage", serviceName = "fileUpload")
public class FTPUploadServer implements FileUploadManage {

    private static final Logger logger = LoggerFactory.getLogger(FTPUploadServer.class);

    /**
     * 
     * @see com.zjzmjr.loan.upload.web.ftp.FileUploadManage#ftpUpload(java.io.File, java.lang.String, java.lang.String)
     */
    @Override
    public boolean ftpUpload(@WebParam(name = "file") File srcfile, @WebParam(name = "path") String path, @WebParam(name = "name") String fileName) {
        try {
            FileUtils.copyInputStreamToFile( new FileInputStream(srcfile), new File(path, fileName));
            return true;
        } catch (FileNotFoundException e) {
            logger.error("ftp文件上传失败", e);
        } catch (IOException e) {
            logger.error("ftp文件上传失败", e);
        }
        
        return false;
    }

//    /**
//     * FTP上传文件，先把文件上传到本地服务器，在把该文件上传到ftp服务器上
//     * 
//     * @param multipartRequest
//     * @return
//     */
//    public String uploadFtp(MultipartFile multipartRequest) {
//        String accessUrl = uploadApk(multipartRequest);
//        Thread thread = new Thread(new Runnable() {
//            
//            @Override
//            public void run() {
//                FileUploadManage manager = (FileUploadManage) SpringContextUtil.getBean("ftpUpload");
//                File srcFile = new File(uploadFileUrl, originFileName);
//                if(manager.ftpUpload(srcFile, uploadFileUrl, originFileName)){
//                    srcFile.delete();
//                }
//            }
//        });
//        thread.run();
//        return accessUrl;
//    }

//     直接在代码中访问
//    public static void main(String[] args) {
//        // 调用WebService
//        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
//        factory.setServiceClass(IComplexUserService.class);
//        factory.setAddress("http://localhost:8080/CXFWebService/Users");
//
//        IComplexUserService service = (IComplexUserService) factory.create();
//
//        System.out.println("#############Client getUserByName##############");
//        User user = service.getUserByName("hoojo");
//        System.out.println(user);
//
//        user.setAddress("China-Guangzhou");
//        service.setUser(user);
//    }
    
}
