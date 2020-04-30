package soen487.foodmarket.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import soen487.foodmarket.error.BusinessException;
import soen487.foodmarket.error.EmBusinessError;

@Slf4j
public class ParamValidator {

    public static void paramError(BindingResult bindingResult) {
        log.error("Parameter invalid");
        if (bindingResult.getFieldError() == null) {
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR,
                bindingResult.getFieldError().getDefaultMessage());
    }
}
