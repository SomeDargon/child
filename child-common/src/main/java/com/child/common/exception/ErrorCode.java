package com.child.common.exception;


public enum ErrorCode {
    INTERNAL_ERROR(500, 1000, "网络或服务器有点忙，请稍后再试。"),
    AUTHTOKEN_ERROR(401, 2000, "AuthToken错误或过期"),
    DATE_FORMAT_ERROR(400, 9000, "日期格式化错误"),
    ABNORMAL_TIME(400, 10040, "abnormal time is error."),
    DATA_NOT_EXIST(400, 10041, "数据不存在"),
    BIRTHDAY_NOT_OVER_NOW(400, 10042, "生日不能超过当前日期"),
    NOT_EXIST_ACCOUNT(400, 3008, "账号不存在"), //
    USERNAME_OR_PASSWORD_ERROR(400, 3009, "用户名或密码错误"), //
    NOT_OURSELF_ACCOUNT(400, 3010, "账号与登录人不匹配"), //
    NOT_DELETE_OTHERS(400, 3010, "不能删除别人的数据"), //
    CONSULT_COUNT_ZERO(400, 3011, "咨询次数不够,请购买"), //
    WXPAY_NOTIFY_MONEY_VERIFY_ERROR(400,30012, "微信支付异步通知金额校验不通过"),
    DOCTOR_REPLY_ERROR(400,30013, "医生已经回复内容不能申请退款"),
    TIME_ERROR(400,30014, "咨询时间不足两小时不能申请退款"),
    ;

    private int httpStatusCode;
    private int errorCode;
    private String message;

    private static final int DEFAULT_STATUS_CODE = 400;

    private ErrorCode(int httpStatusCode, int errorCode, String message) {
        this.httpStatusCode = httpStatusCode;
        this.errorCode = errorCode;
        this.message = message;
    }

    private ErrorCode(int errorCode, String message) {
        this.httpStatusCode = DEFAULT_STATUS_CODE;
        this.errorCode = errorCode;
        this.message = message;
    }

    private ErrorCode(int errorCode) {
        this.httpStatusCode = DEFAULT_STATUS_CODE;
        this.errorCode = errorCode;
        this.message = "";
    }

    public int getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(int httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String toJson() {
        return new StringBuilder("{\"errorCode\":")//
                .append(errorCode)//
                .append(",\"message\":\"")//
                .append(message)//
                .append("\" }")//
                .toString();
    }


}
