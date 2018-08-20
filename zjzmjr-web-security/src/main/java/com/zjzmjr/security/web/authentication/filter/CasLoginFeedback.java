/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.zjzmjr.security.web.authentication.filter;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.springframework.web.filter.OncePerRequestFilter;


/**
 * cas 登录回馈
 * 
 * @author hao
 * @version $Id: AutoSignOnFilter.java, v 0.1 Jun 3, 2015 6:19:16 AM hao Exp $
 */
public class CasLoginFeedback extends OncePerRequestFilter {
	
	

	/** 
	 * @see org.springframework.web.filter.OncePerRequestFilter#doFilterInternal(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, javax.servlet.FilterChain)
	 */
	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

        if (request.getRequestURI().equals("/transit.htm")) {

            String msg = request.getParameter("msg");
            String login = request.getParameter("login");
            String execution = request.getParameter("execution");
            String lt = request.getParameter("lt");
            Map<String, Object> result = new HashMap<String, Object>();
            result.put("msg", msg);
            result.put("login", login);
            if ("failed".equals(login)) {
                result.put("execution", execution);
                result.put("lt", lt);
            }
            String rJson = JSONObject.fromObject(result).toString();

            if (!response.isCommitted()) {
                response.setCharacterEncoding("utf-8");
                response.setContentType("text/html");

                OutputStream outs = null;
                try {
                    outs = response.getOutputStream();
                    outs.write(outputHtml(rJson).getBytes("utf-8"));
                } catch (IOException e) {
                } finally {
                    IOUtils.closeQuietly(outs);
                }
            }
        } else {
            filterChain.doFilter(request, response);
        }

	}
	
    private String outputHtml(String jsonResult) {
        StringBuilder out = new StringBuilder();
        out.append("<html>");
        out.append("<head>");
        out.append("<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>");
        out.append("<title>transit</title>");
        out.append("</head>");
        out.append("<body>");
        out.append("<script type='text/javascript'>");
        out.append("window.parent.X.common.user.feedBackUrlCallBack("+jsonResult+");");
        out.append("</script>");
        out.append("</body>");
        out.append("</html>");
        return out.toString();

    }

	
}
