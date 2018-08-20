package com.zjzmjr.loan.upload.web.file;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zjzmjr.loan.upload.web.util.ConetonSignHelper;
import com.zjzmjr.loan.upload.web.util.FileUploadUtil;


public class FileUploadServlet extends HttpServlet {

    private static final Logger logger = LoggerFactory.getLogger(FileUploadServlet.class);

    /**  */
    private static final long serialVersionUID = -6700543434069345147L;

    /**
     * Constructor of the object.
     */
    public FileUploadServlet() {
        super();
    }

    /**
     * Destruction of the servlet. <br>
     */
    public void destroy() {
        super.destroy(); // Just puts "destroy" string in log
        // Put your code here
    }

    /**
     * The doGet method of the servlet. <br>
     *
     * This method is called when a form has its tag value method equals to get.
     * 
     * @param request the request send by the client to the server
     * @param response the response send by the server to the client
     * @throws ServletException if an error occurred
     * @throws IOException if an error occurred
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        uploadFile(request, response);
    }

    /**
     * The doPost method of the servlet. <br>
     *
     * This method is called when a form has its tag value method equals to post.
     * 
     * @param request the request send by the client to the server
     * @param response the response send by the server to the client
     * @throws ServletException if an error occurred
     * @throws IOException if an error occurred
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    /**
     * Initialization of the servlet. <br>
     *
     * @throws ServletException if an error occurs
     */
    public void init() throws ServletException {
        // Put your code here
    }

    @SuppressWarnings({ "unchecked", "static-access" })
    public void uploadFile(HttpServletRequest req, HttpServletResponse resp) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {

            resp.setHeader("Access-Control-Allow-Origin", "*");
            resp.setHeader("Access-Control-Allow-Methods", "*");
            resp.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
            resp.setHeader("Access-Control-Max-Age", "10000");
            resp.setContentType("application/json");

            // 1.创建DiskFileItemFactory对象，配置缓存用
            DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();

            // 2. 创建 ServletFileUpload对象
            ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);

            boolean isMultipart = servletFileUpload.isMultipartContent(req);
            if(isMultipart){
                // 3. 设置文件名称编码
                servletFileUpload.setHeaderEncoding("utf-8");
                logger.info("文件开始上传");
                // 4. 开始解析文件
                FileUploadUtil fileUploadUtil = FileUploadUtil.getInstance(FileUploadUtil.SavePathEnums.PATH_ADMIN);
                List<FileItem> items = servletFileUpload.parseRequest(req);
                StringBuilder builder = new StringBuilder();
                StringBuilder fileNames = new StringBuilder();
                String accessUrl = "";
                logger.info("解释请求参数");
                for (FileItem fileItem : items) {
                    logger.info("循环参数" + fileItem.getFieldName());
                    if (fileItem.isFormField()) { // >> 普通数据
                        model.put(fileItem.getFieldName(), fileItem.getString("utf-8"));
                    } else { // >> 文件
                        // 1. 获取文件名称
                        String name = fileItem.getName();
                        // 2. 获取文件的实际内容
                        InputStream is = fileItem.getInputStream();
                        // 3. 保存文件
                        accessUrl = fileUploadUtil.uploadApk(is, name);
                        if(builder.length() > 0){
                            builder.append(",").append(accessUrl);
                            fileNames.append(",").append(name);
                        }else{
                            builder.append(accessUrl);
                            fileNames.append(name);
                        }
                    }
                }
                model.put("fileAccessUrl", builder.toString());
                model.put("fileName", fileNames.toString());
                
                logger.info("传递过去的参数:" + model);
                
                resp.sendRedirect(req.getHeader("Origin") + "/fileManage/fileUpLoad.htm?" + ConetonSignHelper.createLinkString(model));
                putSuccess(model, "", "");
            }else{
                logger.error("上传的form没有设置multipart/form-data");
                putError(model, "", "提价的form设置不对");
            }
        } catch (Exception ex) {
            logger.error("附件上传出错：", ex);
            putError(model, "", ex.getMessage());
        }

        responseAsJson(model, resp);

    }

    protected Map<String, Object> putSuccess(Map<String, Object> model,String code,String msg){
        if(model!=null){
            model.put("success", true);
            model.put("code", StringUtils.trimToEmpty(code));
            model.put("resultMsg", StringUtils.trimToEmpty(msg));
        }
        return model;
    }
    
    /**
     * 写入错误信息
     * 
     * @param model
     * @param code  错误码
     * @param msg
     * @return
     */
    protected Map<String, Object> putError(Map<String, Object> model,String code,String msg){
        if(model!=null){
            model.put("success", false);
            model.put("resultMsg", StringUtils.trimToEmpty(msg));
            model.put("code", StringUtils.trimToEmpty(code));
        }
        return model;
    }

    /**
     * 以json方式返回结果
     * 
     * @param model   数据模型
     * @param resp    Http响应
     */
    protected void responseAsJson(Map<String, Object> model,HttpServletResponse resp){
        //resp.reset();
        if(!resp.isCommitted()){
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("text/plain");

            OutputStream outs = null;
            try {
                outs = resp.getOutputStream();
                outs.write(JSONObject.fromObject(model).toString().getBytes("UTF-8"));
            } catch (IOException e) {
            }finally{
                IOUtils.closeQuietly(outs);
            }
        }
    }

}
