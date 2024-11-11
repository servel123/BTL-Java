/*
* Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
* Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
*/
package com.project.fashion.dto.response;

import java.io.Serializable;
import lombok.*;

/**
 *
 * @author Vu
 */
@Getter
@Setter
public class PaymentResDTO implements Serializable {
    private Integer status;
    private String message;
    private String url;
}
