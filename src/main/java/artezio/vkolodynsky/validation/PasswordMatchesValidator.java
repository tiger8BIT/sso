package artezio.vkolodynsky.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import artezio.vkolodynsky.model.data.UserData;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {
        //
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final UserData user = (UserData) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }

}
