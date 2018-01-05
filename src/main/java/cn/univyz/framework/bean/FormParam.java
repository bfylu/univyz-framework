package cn.univyz.framework.bean;


/**
 * 封装表单参数
 *
 * @author bfy
 * @version 1.0.0
 */
public class FormParam {
    //文件表单的字段名()
    private String fieldName;
    private Object filedValue;

    public FormParam(String fieldName, Object filedValue) {
        this.fieldName = fieldName;
        this.filedValue = filedValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getFiledValue() {
        return filedValue;
    }
}
