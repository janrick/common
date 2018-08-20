/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.zjzmjr.security.web.userdetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 
 * @author hao
 * @version $Id: UserDetailServiceImpl.java, v 0.1 Jun 10, 2015 12:52:00 PM hao Exp $
 */
public class UserDetailServiceImpl implements AuthenticationUserDetailsService<Authentication> {

    /**
     * @see org.springframework.security.core.userdetails.AuthenticationUserDetailsService#loadUserDetails(org.springframework.security.core.Authentication)
     */
    @SuppressWarnings("rawtypes")
    @Override
    public UserDetails loadUserDetails(Authentication token) throws UsernameNotFoundException {
        if (token instanceof CasAssertionAuthenticationToken) {
            AttributePrincipal ap =
                    (AttributePrincipal) ((CasAssertionAuthenticationToken) token).getAssertion().getPrincipal();
            Map attrMap = ap.getAttributes();
            List<GrantedAuthority> authGranted = new ArrayList<GrantedAuthority>();
            if(attrMap.get("auths") != null ){
                List<String> auths = Arrays.asList(attrMap.get("auths").toString().split(","));
                if (auths.size() > 0) {
                    for (String authStr : auths) {
                        if (StringUtils.isNotBlank(authStr)) {
                            GrantedAuthority ga = new SimpleGrantedAuthority(authStr);
                            authGranted.add(ga);
                        }
                    }
                }
            }
            Integer userId = attrMap.get("userId") != null ? Integer.valueOf(attrMap.get("userId").toString()) :null;
            String username = attrMap.get("username") != null? attrMap.get("username").toString() : null;
            String password = attrMap.get("password")!= null?  attrMap.get("password").toString() :null;
            Boolean accountNonExpired =attrMap.get("accountNonExpired") != null ? Boolean.valueOf(attrMap.get("accountNonExpired").toString()) : false;
            Boolean accountNonLocked =attrMap.get("accountNonLocked") != null? Boolean.valueOf(attrMap.get("accountNonLocked").toString()): false;
            Boolean credentialsNonExpired = attrMap.get("credentialsNonExpired") != null? Boolean.valueOf(attrMap.get("credentialsNonExpired").toString()): false;
            Boolean sourceSecure = attrMap.get("sourceSecure") != null ? Boolean.valueOf(attrMap.get("sourceSecure").toString()) : false;
            Boolean enabled = attrMap.get("enabled") != null ? Boolean.valueOf(attrMap.get("enabled").toString()) : false;
            UserLoginResult ud =
                    new UserLoginResult(userId, authGranted, username, password, accountNonExpired, accountNonLocked,
                            credentialsNonExpired, sourceSecure, enabled);
            return ud;
        }
        return null;
    }
}
