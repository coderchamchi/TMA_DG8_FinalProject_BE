package com.bezkoder.springjwt.dto;

import com.bezkoder.springjwt.exception.SysError;
import com.bezkoder.springjwt.payload.response.JwtResponse;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;
import java.io.Serializable;
import java.util.HashMap;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseJson<T> implements Serializable {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;
    private HashMap<String, String> hashmap;
    private String message;
    private transient T data;
    private SysError sysError;
    private HttpStatus status;

    private JwtResponse jwtResponse;

    public ResponseJson() {

    }

    public ResponseJson(String message, T data, SysError sysError, HttpStatus status) {
        super();
        this.message = message;
        this.data = data;
        this.sysError = sysError;
        this.status = status;
    }

    public ResponseJson(String message, SysError sysError) {
        super();
        this.message = message;
        this.sysError = sysError;
    }

    public ResponseJson(SysError sysError) {
        super();
        this.sysError = sysError;
    }

    public ResponseJson(T data) {
        this.data = data;
    }

    public  ResponseJson(T data, HttpStatus status, String message) {
        this.data = data;
        this.status = status;
        this.message = message;
    }

    public  ResponseJson(T data, HttpStatus status, HashMap hashmap) {
        this.data = data;
        this.status = status;
        this.hashmap = hashmap;
    }

    public ResponseJson(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public ResponseJson(String message) {
        super();
        this.message = message;
    }

    public ResponseJson(JwtResponse jwtResponse, String message) {
        this.jwtResponse = jwtResponse;
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public SysError getSysError() {
        return sysError;
    }

    public void setSysError(SysError sysError) {
        this.sysError = sysError;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public JwtResponse getJwtResponse() {
        return jwtResponse;
    }

    public void setJwtResponse(JwtResponse jwtResponse) {
        this.jwtResponse = jwtResponse;
    }
}