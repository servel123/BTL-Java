/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.fashion.dto.response;

/**
 *
 * @author Vu
 */
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class ResponseData<T> implements Serializable {
    private final int status;
    private final String message;
    @JsonInclude(JsonInclude.Include.NON_NULL) // neu data null thi khong can hien thi
    private T data;

    /**
     * Response data for the API to retrieve data successfully. For GET, POST only
     * 
     * @param status
     * @param message
     * @param data
     */
    // GET POST
    public ResponseData(int status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    /**
     * Response data when the API executes successfully or getting error. For PUT,
     * PATCH, DELETE
     * 
     * @param status
     * @param message
     */
    // PUT PATCH DELETE
    public ResponseData(int status, String message) {
        this.status = status;
        this.message = message;
    }
}
