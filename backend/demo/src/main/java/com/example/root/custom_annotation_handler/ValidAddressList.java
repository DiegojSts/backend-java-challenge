package com.example.root.custom_annotation_handler;

import javax.validation.Constraint;
import javax.validation.Payload;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({
        ElementType.FIELD,
        ElementType.PARAMETER,
        ElementType.ANNOTATION_TYPE
})
@Constraint(validatedBy = AddressListValidator.class)
public @interface ValidAddressList {
    String message() default "Deve existir ao menos 1 endereço e campos de endereço são obrigatórios!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
