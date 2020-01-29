package artezio.vkolodynsky.model.data;

import artezio.vkolodynsky.model.App;
import artezio.vkolodynsky.model.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
public class RoleData {
    private Integer id;
    private String description;
    private String roleName;
    private Integer appId;
    public RoleData(Role role) {
        id = role.getId();
        description = role.getDescription();
        roleName = role.getRoleName();
        appId = role.getApp().getId();
    }
    public Role getRole(App app) {
        Role role = new Role();
        return updateRole(role, app);
    }
    public Role updateRole(Role role, App app){
        role.setRoleName(roleName);
        role.setApp(app);
        role.setDescription(description);
        return role;
    }
}
