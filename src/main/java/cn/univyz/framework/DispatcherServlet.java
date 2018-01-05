package cn.univyz.framework;

import cn.univyz.framework.bean.Data;
import cn.univyz.framework.bean.Handler;
import cn.univyz.framework.bean.Param;
import cn.univyz.framework.bean.View;
import cn.univyz.framework.helper.*;
import cn.univyz.framework.util.JsonUtil;
import cn.univyz.framework.util.ReflectionUtil;
import cn.univyz.framework.util.StringUtil;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.Map;


@WebServlet(urlPatterns = "/*", loadOnStartup = 0)
public class DispatcherServlet extends HttpServlet{

    @Override
    public void init(ServletConfig servletConfig) {
            System.out.println("DispatcherServlet001---------->>>" +servletConfig.getServletName());
            HelperLoader.init();
            // 获取 ServletContext 对象（用于注册 Servlet）
            ServletContext servletContext = servletConfig.getServletContext();
            registerServlet(servletContext);
            UploadHelper.init(servletContext);
    }

    private void registerServlet(ServletContext servletContext) {
        ConfigHelper configHelper = new ConfigHelper();
        System.out.println("DispatcherServlet002----------0>>>");
        //注册处理 JSP 的 Servlet
        ServletRegistration jspServlet = servletContext.getServletRegistration("jsp");
        jspServlet.addMapping("/index.jsp");
        System.out.println("DispatcherServlet002----------2>>>");
        jspServlet.addMapping(configHelper.getAppJspPath() + "*");
        System.out.println("DispatcherServlet002---------->>>3"+configHelper.getAppJspPath());
        //注册处理静态资源的默认 Servlet
        ServletRegistration defaultServlet = servletContext.getServletRegistration("default");
        //defaultServlet.addMapping("/favicon.ico");
        defaultServlet.addMapping(ConfigHelper.getAppAssetPath() + "*");
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletHelper.init(request, response);
        System.out.println("DispatcherServlet003---------->>>");
        try {
            // 获取请求方法与请求路径
            String requestMethod = request.getMethod().toLowerCase();
            System.out.println("DispatcherServlet003--请求方法-------->>>"+requestMethod);
            String requestPath = request.getPathInfo();
            System.out.println("DispatcherServlet003---请求路径------->>>"+requestPath);

            // 获取 Action 处理器
            Handler handler = ControllerHelper.getHandler(requestMethod,requestPath);
            System.out.println("DispatcherServlet003---Action 处理器------->>>");
            if (handler != null) {
                // 获取 Controller 类及其 Bean 实例

                Class<?> controllerClass = handler.getControllerClass();
                System.out.println("DispatcherServlet003---BeanHelper------->>>");
                Object controllerBean = BeanHelper.getBean(controllerClass);
                //创建请求参数对象
                Param param;
                System.out.println("DispatcherServlet003---创建请求参数对象------->>>");
                if (UploadHelper.isMultipart(request)){
                    System.out.println("DispatcherServlet003---创建请求参数对象1------->>>");
                    param = UploadHelper.createParam(request);
                }else {
                    System.out.println("DispatcherServlet003---创建请求参数对象2------->>>");
                    param = RequestHelper.createParam(request);
                }

                Object result;
                Method actionMethod = handler.getActionMethod();
                System.out.println("DispatcherServlet003---actionMethod------->>>");
                if (param.isEmpty()) {
                    System.out.println("DispatcherServlet003---actionMethod--1------->>>");
                    result = ReflectionUtil.invokeMethod(controllerBean, actionMethod);
                }else {
                    System.out.println("DispatcherServlet003---actionMethod--2------->>>");
                    result = ReflectionUtil.invokeMethod(controllerBean, actionMethod, param);
                }

                if (result instanceof View) {
                    View view = (View) result;
                    handleViewResult(view, request, response );
                } else  if (result instanceof Data) {
                    handleDataResult((Data) result, response);
                }
            }
        } finally {
            ServletHelper.destroy();
        }
    }

    //返回JSP页面
    private void handleViewResult(View view, HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException {
        System.out.println("DispatcherServlet004---------->>>");
        String path = view.getPath();
        System.out.println("DispatherServlet-----view-->"+view.getPath());
        if (StringUtil.isNotEmpty(path)) {
            if (path.startsWith("/"))
                response.sendRedirect(request.getContextPath() + path);
        }else  {
            Map<String, Object> model = view.getModel();
            for (Map.Entry<String, Object> entry : model.entrySet()) {
                request.setAttribute(entry.getKey(), entry.getValue());
            }
            request.getRequestDispatcher(ConfigHelper.getAppJspPath() + path).forward(request, response);
            System.out.println("DispatcherServlet--JSP2----->" +ConfigHelper.getAppJspPath() + path+" request "+ request);
        }
    }

    //返回JSON数据
    private void handleDataResult(Data data, HttpServletResponse response) throws IOException {
        Object model = data.getModel();
        if (model != null){
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();
            String json = JsonUtil.toJson(model);
            writer.write(json);
            writer.flush();
            writer.close();

        }
    }

}
