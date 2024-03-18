package com.bordserver.jwy.utils;

import jakarta.servlet.http.HttpSession;

public class SessionUtil {
    private static final String LOGIN_MEMBER_ID = "LOGIN_MEMBER_ID";
    private static final String LOGIN_ADMIN_ID = "LOGIN_ADMIN_ID";

    public static String getLoginMemberId(HttpSession session) {
        return (String) session.getAttribute(LOGIN_MEMBER_ID);
    }

    public static String getLoginAdminId(HttpSession session) {
        return (String) session.getAttribute(LOGIN_ADMIN_ID);
    }

    public static void setLoginMemberId(HttpSession session, String account) {
        session.setAttribute(LOGIN_MEMBER_ID, account);
    }

    public static void setLoginAdminId(HttpSession session, String account) {
        session.setAttribute(LOGIN_ADMIN_ID, account);
    }

    public static void clear(HttpSession session) {
        session.invalidate();
    }
}
