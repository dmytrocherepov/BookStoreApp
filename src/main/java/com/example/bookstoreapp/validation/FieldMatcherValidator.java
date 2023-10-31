package com.example.bookstoreapp.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanWrapperImpl;

@RequiredArgsConstructor
public class FieldMatcherValidator implements ConstraintValidator<FieldMatch,Object> {
    private String field;
    private String fieldMatch;

    public void initialize(FieldMatch fieldMatch) {
        this.field = fieldMatch.field();
        this.fieldMatch = fieldMatch.fieldMatch();
    }

    @Override
    public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
        Object password = new BeanWrapperImpl(object).getPropertyValue(field);
        Object repeatPassword = new BeanWrapperImpl(object).getPropertyValue(fieldMatch);
        if (password != null) {
            return password.equals(repeatPassword);
        } else {
            return false;
        }
    }
}
