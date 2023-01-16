package com.example.root.custom_annotation_handler;

import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.Objects;

public class AdressListValidator implements ConstraintValidator <ValidAdressList, List<? extends Object>> {

    @Override
    public boolean isValid(List<? extends Object> value, ConstraintValidatorContext context) {
        System.out.println(value);
        return !(value == null || value.isEmpty() || value.stream().anyMatch(Objects::isNull));
    }
}
