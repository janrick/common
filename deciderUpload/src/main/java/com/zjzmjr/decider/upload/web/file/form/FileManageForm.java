package com.zjzmjr.decider.upload.web.file.form;

import java.io.Serializable;
import java.util.Date;

import org.springframework.web.multipart.MultipartFile;

/**
 * 附件上传FORM
 * 
 * @author Administrator
 * @version $Id: FileManageForm.java, v 0.1 2016-11-21 上午10:35:30 Administrator
 *          Exp $
 */
public class FileManageForm implements Serializable {

    private static final long serialVersionUID = -6111287125670364167L;

    private Integer id;

    private Integer custId;

    private String fileName;

    private MultipartFile fileAddress;

    private Integer status;

    /**
     * 上传人姓名
     */
    private Integer name;

    /**
     * 数据来源
     */
    private Integer sourceType;

    private Date createTime;

    private Integer createUserId;

    private Date updateTime;

    private Integer updateUserId;

    private Integer version;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCustId() {
        return custId;
    }

    public void setCustId(Integer custId) {
        this.custId = custId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName == null ? null : fileName.trim();
    }

    public MultipartFile getFileAddress() {
        return fileAddress;
    }

    public void setFileAddress(MultipartFile fileAddress) {
        this.fileAddress = fileAddress;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getName() {
        return name;
    }

    public void setName(Integer name) {
        this.name = name;
    }

    public Integer getSourceType() {
        return sourceType;
    }

    public void setSourceType(Integer sourceType) {
        this.sourceType = sourceType;
    }

    @Override
    public String toString() {
        return "FileManageForm [id=" + id + ", custId=" + custId + ", fileName=" + fileName + ", fileAddress=" + fileAddress + ", status=" + status + ", name=" + name + ", sourceType=" + sourceType + ", createTime=" + createTime + ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", updateUserId=" + updateUserId + ", version=" + version + "]";
    }

}