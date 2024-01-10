package com.xprotec.app.type;

public enum AppHttpStatusCode {
    OK(200, "OK"),
    FUNCTIONAL_ERROR(209, "Functional Error"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    NOT_FOUND(404, "NOT FOUND"),
    REQUEST_TIMEOUT(408, "Request Timeout"),
    PRE_CONDITION_FAILED(412, "Precondition failed"),
    PRE_CONDITION_REQUIRED(428, "Precondition Required"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    SERVICE_UNAVAILABLE(503, "Service Unavailable"),
    GATEWAY_TIMEOUT(504, "Gateway Timeout"),
    HTTP_STATUS_CODE_NOT_MAPPED(0, "Http status code not mapped");

    private int code;
    private String message;

    private AppHttpStatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static AppHttpStatusCode getHttpStatusFromCode(int code) {
        AppHttpStatusCode[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            AppHttpStatusCode httpStatusCode = var1[var3];
            if (code == httpStatusCode.code) {
                return httpStatusCode;
            }
        }

        HTTP_STATUS_CODE_NOT_MAPPED.setCode(code);
        return HTTP_STATUS_CODE_NOT_MAPPED;
    }
}
