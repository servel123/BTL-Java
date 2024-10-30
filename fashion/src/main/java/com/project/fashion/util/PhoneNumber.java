package com.project.fashion.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PhoneValidator.class) // ràng buọc phải gọi sang PhoneValidator
@Target({ ElementType.FIELD }) // áp dụng cho field;
@Retention(RetentionPolicy.RUNTIME) // chạy trên môi trường runtime
public @interface PhoneNumber {
    String message() default "Invalid phone number";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
