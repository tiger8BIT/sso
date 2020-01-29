package artezio.vkolodynsky.model.data;
import artezio.vkolodynsky.model.User;
import artezio.vkolodynsky.validation.PasswordMatches;
import artezio.vkolodynsky.validation.ValidEmail;
import artezio.vkolodynsky.validation.ValidLogin;
import artezio.vkolodynsky.validation.ValidPassword;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@PasswordMatches
@Data
@NoArgsConstructor
public class UserData {
    private Integer id;
    private String info;

    @NotNull
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

    public UserData(User user) {
        id = user.getId();
        info = user.getInfo();
        login = user.getLogin();
        email = user.getEmail();
    }
    public User getUser() {
        User user = new User();
        return updateUser(user);
    }
    public User updateUser(User user){
        user.setLogin(login);
        user.setInfo(info);
        user.setEmail(email);
        user.setPassword(password);
        return user;
    }
}
