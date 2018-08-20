package com.zjzmjr.decider.upload.web.file.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjzmjr.decider.upload.web.file.form.FileManageForm;
import com.zjzmjr.web.mvc.controller.BaseController;

@Controller
@RequestMapping("/fileManage")
public class FileManageController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(FileManageController.class);

//    @Resource(name = "fileManageFacade")
//    private IFileManageFacade fileManageFacade;

    /**
     * 附件上传
     * 
     * @param resp
     * @param req
     * @param form
     */
    @RequestMapping(value = "/fileUpLoad.htm", method = RequestMethod.POST)
    public void fileUpLoad(HttpServletResponse resp, HttpServletRequest req, FileManageForm form) {
//        Map<String, Object> model = new HashMap<String, Object>();
//        FileManage query = new FileManage();
//        try {
//            Assert.isTrue(form.getCustId() != null, "电审表主键不能为空");
//            Assert.isTrue(StringUtils.isNotBlank(form.getFileAddress().getOriginalFilename()), "请选择上传的文件");
//            
//            resp.setHeader("Access-Control-Allow-Origin", "*");
//            resp.setHeader("Access-Control-Allow-Methods", "*");
//            resp.setHeader("Access-Control-Allow-Headers", "x-requested-with,content-type");
//            resp.setHeader("Access-Control-Max-Age", "1000");
//            resp.setContentType("application/json");
//            resp.setCharacterEncoding("utf-8");
//            query.setSourceType(form.getSourceType());
//            query.setCustId(form.getCustId());
//            query.setFileName(form.getFileAddress().getOriginalFilename());
//            query.setFileAddress(FileUploadUtil2.getInstance().uploadApk(form.getFileAddress()));
//            query.setCreateTime(new Date());
//            query.setCreateUserId(form.getCreateUserId());
//            ResultEntry<Integer> result = fileManageFacade.insertFileManage(query);
//            if (result.isSuccess()) {
//            	model.put("success", true);
//                model.put("data", "");
//            } else {
//            	model.put("success", false);
//                model.put("resultMsg", result.getErrorMsg());
//                model.put("code", result.getErrorCode());
//            }
//            resp.sendRedirect(req.getParameter("redirectUrl") + "?model=" + model);
//        } catch (Exception ex) {
//            logger.error("附件上传出错：", ex);
//            putError(model, ex.getMessage());
//        }
//        responseAsJson(model, resp);
//        responseAsText("true", resp);
    }
    
}
