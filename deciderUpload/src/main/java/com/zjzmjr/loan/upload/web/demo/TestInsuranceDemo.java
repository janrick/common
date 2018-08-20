package com.zjzmjr.loan.upload.web.demo;

import java.rmi.RemoteException;

import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;

public class TestInsuranceDemo {

    public String invokeRemoteFuc() {
        String endpoint = "https://pcistest.zsins.com/nonvhl/service/policyWebService?wsdl";
        String result = "no result!";
        Service service = new Service();
        Call call;
        Object[] object = new Object[1];
        object[0] = "<![CDATA[<?xml version='1.0' encoding='UTF-8'?>"+
                "<PACKET type='REQUEST' version='1.0'>"+
                "<HEAD>"+
                    "<REQUEST_TYPE>0000-30</REQUEST_TYPE><SYSCODE>13</SYSCODE><SYSNAME>旅游意外险申请核保</SYSNAME><FLOWID></FLOWID><SERVICENAME></SERVICENAME><SERVICE_NO></SERVICE_NO><USERID>123</USERID><PASSWORD>3213</PASSWORD>"+
            "<INTERFACEID>0000-30</INTERFACEID>"+
                "</HEAD>"+
                "<BODY>"+
                    "<BASE>"+
                    "<QUERY_TYPE>N</QUERY_TYPE>"+
                    "<DOC_NO>299330106102011000005</DOC_NO>"+
            "</BASE>"+
                "</BODY>"+
            "</PACKET>]]>";// Object是用来存储方法的参数
        try {
            call = (Call) service.createCall();
            call.setTargetEndpointAddress(endpoint);// 远程调用路径
            call.setOperationName("queryPolicy");// 调用的方法名

            // 设置参数名:
            call.addParameter("arg0", // 参数名
                    XMLType.XSD_STRING,// 参数类型:String
                    ParameterMode.IN);// 参数模式：'IN' or 'OUT'

            // 设置返回值类型：
            call.setReturnType(XMLType.XSD_STRING);// 返回值类型：String

            result = (String) call.invoke(object);// 远程调用
        } catch (ServiceException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        TestInsuranceDemo t = new TestInsuranceDemo();
        String result = t.invokeRemoteFuc();
        System.out.println(result);
    }

}
