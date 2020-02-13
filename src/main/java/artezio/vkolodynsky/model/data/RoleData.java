package artezio.vkolodynsky.model.data;

import artezio.vkolodynsky.model.App;
import artezio.vkolodynsky.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleData {
    private Integer id;
    private String description;
    private String roleName;
    private Integer appId;
    public static RoleData from(Role role) {
        RoleData roleData = new RoleData();
        roleData.id = role.getId();
        roleData.description = role.getDescription();
        roleData.roleName = role.getRoleName();
        roleData.appId = role.getApp().getId();
        return roleData;
    }
}
