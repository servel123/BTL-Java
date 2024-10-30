/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.project.fashion.dto.response;

/**
 *
 * @author Vu
 */
@SuppressWarnings("rawtypes")
public class ResponseError extends ResponseData {

    public ResponseError(int status, String message) {
        super(status, message);
    }
}
