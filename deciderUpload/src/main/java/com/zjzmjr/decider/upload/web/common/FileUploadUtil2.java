package com.zjzmjr.decider.upload.web.common;

import java.io.File;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import com.zjzmjr.common.util.DateUtil;
import com.zjzmjr.core.base.util.PropertyUtils;
import com.zjzmjr.security.web.util.SpringSecurityUtil;

/**
 * 
 * 
 * @author Administrator
 * @version $Id: FileUploadUtil.java, v 0.1 2016-5-12 下午7:16:40 Administrator Exp $
 */
public class FileUploadUtil2{

    private static final Logger logger = LoggerFactory.getLogger(FileUploadUtil2.class);

    private static final String PATH = "upload/decider";

    private final String resouce_path = "file.url";

    private final String resouce_url = "img.url";
    private String business;
    private static volatile FileUploadUtil2 instance = null;
    private FileUploadUtil2(){
    }

    /**
     * 实例化
     * 
     * @return
     */
    public static FileUploadUtil2 getInstance(){
        if(instance == null){
            synchronized (FileUploadUtil2.class) {
                if(instance == null){
                    instance = new FileUploadUtil2();
                }
            }
        }
        return instance;
    }
    
    /**
     * 
     * 
     * @param multipartRequest
     * @param resp
     */
    public String uploadApk(MultipartFile multipartRequest) {
        String filePath = StringUtils.EMPTY;
        try{
        	int index = 1;
        	String uploadPath = getUploadPath(business);
			String uploadFileUrl = getUploadFileUrl(uploadPath);
			if (multipartRequest.getOriginalFilename().length() > 0) {
                String originFileName = getOriginFileName(multipartRequest.getOriginalFilename(), index++);
                //改成自己的对象哦！
                //Constant.UPLOAD_GOODIMG_URL 是一个配置文件路径
                try {
                  // 这里使用Apache的FileUtils方法来进行保存
                  FileUtils.copyInputStreamToFile(multipartRequest.getInputStream(), new File(uploadFileUrl, originFileName));
                  filePath = getViewWebUrl(uploadPath) + originFileName;
                } catch (Exception e) {
                  logger.error("上传文件出错：", e);
                }
            }
        }catch(Exception ex){
            logger.error("上传文件出错：", ex);
        }
        return filePath;
    }
    
    /**
     * 重新生成文件名
     * 
     * @param originFileName
     * @return
     */
    private String getOriginFileName(String originFileName, int index){
        StringBuilder builder = new StringBuilder("zmjr");
        builder.append(SpringSecurityUtil.getIntPrincipal());
        builder.append(index);
        builder.append(System.currentTimeMillis());
        builder.append(originFileName.substring(originFileName.lastIndexOf("."), originFileName.length()));
        return builder.toString();
    }
    
    /**
     * 上传的地址
     * 
     * @return
     */
    private String getUploadPath(String business){
        StringBuilder builder = new StringBuilder();
        builder.append(PATH).append("/");
        builder.append(DateUtil.format(new Date(), "yyyyMMdd"));
        if(StringUtils.isNotBlank(business)){
            builder.append("/").append(business);
        }
        
        return builder.toString();
    }
    
    /**
     * 
     * 
     * @param multipartRequest
     * @param uploadPath
     * @return
     */
    private String getUploadFileUrl(String uploadPath){
        String uploadFileUrl = PropertyUtils.getPropertyFromFile(resouce_path);
        uploadFileUrl = uploadFileUrl.concat(uploadPath);
        File filePath = new File(uploadFileUrl);
        if(!filePath.exists()){
            filePath.mkdirs();
        }
        
        return uploadFileUrl;
    }
    
    /**
     * 
     * 
     * @return
     */
    private String getViewWebUrl(String uploadPath){
        StringBuilder viewUrl = new StringBuilder(PropertyUtils.getPropertyFromFile(resouce_url));
        viewUrl.append(uploadPath).append("/");
        
        return viewUrl.toString();
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

}
