package artezio.vkolodynsky.model.data;
import artezio.vkolodynsky.model.User;
import artezio.vkolodynsky.validation.PasswordMatches;
import artezio.vkolodynsky.validation.ValidEmail;
import artezio.vkolodynsky.validation.ValidLogin;
import artezio.vkolodynsky.validation.ValidPassword;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@PasswordMatches
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserData {
    private Integer id;
    private String info;

    @Size(min = 1)
    @ValidLogin
    private String login;

    @ValidPassword
    private String password;

    @NotNull
    @Size(min = 1)
    private String matchingPassword;

    @ValidEmail
    @NotNull
    @Size(min = 1)
    private String email;

    public static UserData from(User user) {
        UserData userData = new UserData();
        userData.id = user.getId();
        userData.info = user.getInfo();
        userData.login = user.getLogin();
        userData.email = user.getEmail();
        userData.password = user.getPassword();
        userData.matchingPassword = user.getPassword();
        return userData;
    }
}
