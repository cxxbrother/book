package com.book.portal.common;

public class ErrorCode {
	public final static int CODE_AUTH = 10;// session校验失败,请重新登录！
	public final static int CODE_PARAM = 11;// 请求参数校验失败！
	public final static int CODE_JAVA = 12;// 服务器异常！
	public final static int CODE_AUTHORITY = 13;// 没有操作权限

	public final static int CODE_ACCOUNT = 101;// 账号不正确
	public final static int CODE_PHONE_NUM = 102;// 该账号没有手机号
	public final static int CODE_FILE = 103;// 上传的文件格式错误
	public final static int CODE_PASSWORD = 104;// 用户名或密码错误
	public final static int CODE_VALIDATE_CODE = 105;// 验证码错误
}
