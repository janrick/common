package com.zjzmjr.loan.upload.web.demo;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;


public class webServiceApp {

//    public static void main(String[] args) {
//        System.out.println("web service start");
//        HelloWorldImpl implementor = new HelloWorldImpl();
//        String address = "http://localhost/helloWorld";
//        Endpoint.publish(address, implementor);
//        System.out.println("web service started");
//    }
    
/*    public static void main(String[] args) {
        // 调用WebService
        JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
        factory.setServiceClass(FileUploadManage.class);
        factory.setAddress("http://localhost/webservice/fileUpload");

        FileUploadManage service = (FileUploadManage) factory.create();
        service.ftpUpload(new File("d:\finance.sql"), "d:\\logs", "finance");
//        System.out.println("#############Client getUserByName##############");
//        User user = service.getUserByName("hoojo");
//        System.out.println(user);

//        user.setAddress("China-Guangzhou");
//        service.setUser(user);
    }*/
    
    public static void main(String[] args) {
        StringBuffer xmlInput = new StringBuffer("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:ser=\"http://service.policy.core.interfaces.isoftstone.com/\">");
        xmlInput.append("<soapenv:Header/>");
        xmlInput.append("<soapenv:Body>");
        xmlInput.append("<ser:queryPolicy>");
        xmlInput.append("<!--Optional:-->");
        xmlInput.append("<arg0>test</arg0>");
        xmlInput.append("</ser:queryPolicy>");
        xmlInput.append("</soapenv:Body>");
        xmlInput.append("</soapenv:Envelope>");
        JaxWsDynamicClientFactory factory = JaxWsDynamicClientFactory.newInstance();
        String wsdlUrl = "https://pcistest.zsins.com/nonvhl/service/policyWebService?wsdl";
        String method = "queryPolicy";
        Client client = factory.createClient(wsdlUrl);
        try {
            client.invoke(method, xmlInput.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
}
