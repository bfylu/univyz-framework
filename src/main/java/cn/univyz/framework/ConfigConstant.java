package cn.univyz.framework;

/**
 * 提供相关配置项常量
 * @author bfy
 * @since 1.0.0
 */
public interface ConfigConstant {

    String CONFIG_FILE = "univyz.properties";

    String JDBC_DRIVER = "univyz.framework.jdbc.driver";
    String JDBC_URL="univyz.framework.jdbc.url";
    String JDBC_USERNAME="univyz.framework.jdbc.username";
    String JDBC_PASSWORD="univyz.framework.jdbc.password";

    String APP_BASE_PACKAGE="univyz.framework.app.base_package";
    String APP_JSP_PATH="univyz.framework.app.jsp_path";
    String APP_ASSET_PATH = "univyz.framework.app.asset_path";
    String APP_UPLOAD_LIMIT = "univyz.framework.app.upload_limit";

}
