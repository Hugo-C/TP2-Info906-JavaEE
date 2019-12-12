package fr.usmb.m2isc.mesure.bean;

import fr.usmb.m2isc.mesure.servlet.CookieHelper;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name="userBean")
@RequestScoped
public class UserBean {

    public static final String USERNAME_COOKIE_NAME = "Username";
    private String loginName;

    public UserBean() {
        loginName = "";
    }

    public String login(){
        CookieHelper.setCookie(USERNAME_COOKIE_NAME, loginName, -1);  // expire after web browser close
        return "display_columns.xhtml?faces-redirect=true";
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
}
