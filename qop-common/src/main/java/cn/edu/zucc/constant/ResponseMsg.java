package cn.edu.zucc.constant;

/**
 * 接口响应常数
 */
public class ResponseMsg {
    private ResponseMsg() {
    }

    public static final String SUCCESS = "success"; // 调用成功
    public static final String REQUEST_INFO_ERROR = "提交信息有误";
    public static final String GROUP_NOT_FOUND = "小组未找到";
    public static final String NOT_GROUP_OWNER = "你不是群主(￣_,￣ )";
    public static final String NOT_FOUND_USER = "账号不存在";
}
