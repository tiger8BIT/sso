package artezio.vkolodynsky.model.data;

import artezio.vkolodynsky.model.App;
import artezio.vkolodynsky.model.User;
import com.sun.istack.Nullable;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserData {
    private Integer id;
    private String info;
    private String login;
    @Nullable private String password;
    public UserData(User user) {
        id = user.getId();
        info = user.getInfo();
        login = user.getLogin();
    }
}
