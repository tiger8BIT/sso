package artezio.vkolodynsky.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginValidator implements ConstraintValidator<ValidEmail, String> {
    private Pattern pattern;
    private Matcher matcher;
    private static final String LOGIN_PATTERN = "[A-Za-z0-9\\-_]{3,24}";

    @Override
    public void initialize(ValidEmail constraintAnnotation) {

    }

    @Override
    public boolean isValid(final String username, final ConstraintValidatorContext context) {
        return (validateLogin(username));
    }

    private boolean validateLogin(final String email) {
        pattern = Pattern.compile(LOGIN_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
