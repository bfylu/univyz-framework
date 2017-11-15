package cn.univyz.framework;

/**
 * 提供相关配置项常量
 * @author bfy
 * @since 1.0.0
 */
public interface ConfigConstant {

    String CONFIG_FILE = "univyz.properties";

    String JDBC_DRIVER = "univyz.frameework.jdbc.driver";
    String JDBC_URL="univyz.frameework.jdbc.url";
    String JDBC_USERNAME="univyz.frameework.jdbc.username";
    String JDBC_PASSWORD="univyz.frameework.jdbc.password";

    String APP_BASE_PACKAGE="univyz.frameework.app.base_package";
    String APP_JSP_PATH="univyz.frameework.app.jsp_path";
    String APP_ASSET_PATH = "univyz.frameework.app.asset_path";

}
