/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2013 All Rights Reserved.
 */
package com.zjzmjr.security.web.userdetails;

import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 
 * @author elliott
 * @version $Id: UserLoginService.java, v 1.0 2013-12-31 下午2:07:12 elliott Exp $
 */
public interface UserLoginService {

	/**
	 * 加载用户信息
	 * 
	 * @param identity  用户身份码，用户名或者手机等
	 * @param source	用户来源，一般指IP或者相关信息
	 * @return
	 */
	public UserLoginResult loadUser(String identity,String source, String token) throws UsernameNotFoundException;
}
