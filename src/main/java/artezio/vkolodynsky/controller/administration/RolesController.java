package artezio.vkolodynsky.controller.administration;

import artezio.vkolodynsky.model.App;
import artezio.vkolodynsky.model.Role;
import artezio.vkolodynsky.model.User;
import artezio.vkolodynsky.model.data.AppData;
import artezio.vkolodynsky.model.data.RoleData;
import artezio.vkolodynsky.model.response.ServerResponse;
import artezio.vkolodynsky.service.AppService;
import artezio.vkolodynsky.service.RoleService;
import artezio.vkolodynsky.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PersistenceException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("administration/roles")
public class RolesController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private AppService appService;
    @Autowired
    private UserService userService;
    @GetMapping
    public @ResponseBody
    ResponseEntity getAppsList() {
        List<RoleData>  roles = roleService.findAll().stream().map(RoleData::from).collect(Collectors.toList());
        return ServerResponse.success(roles);
    }
    @GetMapping("{id}")
    public @ResponseBody
    ResponseEntity getAppsList(@PathVariable Integer id) {
        Optional<Role> role = roleService.findByID(id);
        if (role.isPresent()) {
            return ServerResponse.success(RoleData.from(role.get()));
        }
        else return ServerResponse.error("Role not found by id " + id);
    }
    @DeleteMapping("{id}")
    public @ResponseBody
    ResponseEntity deleteApp(@PathVariable Integer id) {
        roleService.deleteByID(id);
        return ServerResponse.success(null);
    }
    @PutMapping("{id}")
    public @ResponseBody
    ResponseEntity updateApp(@PathVariable Integer id, @RequestBody RoleData roleData) {
        Role updatedRole = roleService.save(roleData);
        return ServerResponse.success(RoleData.from(updatedRole));
    }

    @PostMapping
    public @ResponseBody
    ResponseEntity addRole(@RequestBody RoleData roleData) {
        Role updatedRole = roleService.save(roleData);
        return ServerResponse.success(RoleData.from(updatedRole));
    }
}
