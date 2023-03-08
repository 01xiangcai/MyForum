package com.yao.tools;

import com.yao.shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;

/**
 * @className: ShiroUtil
 * @Description: TODO
 * @author: long
 * @date: 2023/3/7 0:05
 */
public class ShiroUtil {
    public static AccountProfile getProfile() {
        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }
}
