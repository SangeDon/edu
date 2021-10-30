package cn.sangedon.boot.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * TODO
 *
 * @author dongliangqiong 2021-10-26 21:01
 */
@Data
@ToString
@Configuration
@PropertySource("${config.path}")
@ConfigurationProperties(prefix = "joif.uc")
public class UserConfig {
    /**
     * 企业域
     */
    private String domain;

    /**
     * 前台是否对接用户中心：true-是，false-否，缺省值为true
     */
    private Boolean useuc = true;

    /**
     * 后台是否对接用户中心：true-是，false-否，缺省值为false
     */
    private Boolean useucManage = false;

    /**
     * 系统首页ip:port
     */
    private String sysUrl = "default";

    /**
     * 系统管理页ip:port
     */
    private String sysManageUrl;

    /**
     * 用户中心地址ip:port
     */
    private String sysPreContext;

    /**
     * 用户中心地址ip:port
     */
    private String ucUrl;

    private String ucIp = "default";

    /**
     * 用户中心地址ip:port
     */
    private String indexRouter;

    /**
     * 用户中心登录接口密码是base64编码，缺省值为true
     */
    private Boolean pwdBase64 = true;

    private String loginCallBack = "/ucs/login_callback";
}
