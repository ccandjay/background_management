package com.marker.exception;

import lombok.Getter;

@Getter
public class ServiceException extends RuntimeException {
    private String code;

    /**
     * 如果抛出ServiceException异常,就执行该方法
     *
     * @param code 状态码
     * @param msg  错误消息
     */
    public ServiceException(String code, String msg) {
        super(msg);
        this.code = code;
    }
}
