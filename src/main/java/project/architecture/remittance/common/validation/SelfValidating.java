package project.architecture.remittance.common.validation;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.NoProviderFoundException;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

public abstract class SelfValidating<T> {

    private Validator validator;

    protected SelfValidating() {
        ValidatorFactory factory;
        try {
            factory = Validation.buildDefaultValidatorFactory();
            validator = factory.getValidator();
        } catch (NoProviderFoundException e) {

        } catch (ValidationException e) {

        }
    }

    protected void validateSelf() {
        Set<ConstraintViolation<T>> violations = validator.validate((T) this);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
