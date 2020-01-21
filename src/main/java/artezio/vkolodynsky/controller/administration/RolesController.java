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
        List<RoleData>  roles = roleService.findAll().stream().map(RoleData::new).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(ServerResponse.success(roles));
    }
    @GetMapping("{id}")
    public @ResponseBody
    ResponseEntity getAppsList(@PathVariable Integer id) {
        Optional<Role> role = roleService.findByID(id);
        if (role.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(ServerResponse.success(new RoleData(role.get())));
        }
        else return ResponseEntity.status(HttpStatus.OK).body(ServerResponse.error("Role not found by id " + id));
    }
    @DeleteMapping("{id}")
    public @ResponseBody
    ResponseEntity deleteApp(@PathVariable Integer id) {
        try {
            roleService.deleteByID(id);
        } catch (PersistenceException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.OK).body(ServerResponse.error("Server Error"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(ServerResponse.success(null));
    }
    @PutMapping("{id}")
    public @ResponseBody
    ResponseEntity updateApp(@PathVariable Integer id, @RequestBody RoleData roleData) {
        Optional<Role> role = roleService.findByID(roleData.getId());
        if(role.isPresent()) {
            Optional<App> app = appService.findByID(roleData.getAppId());
            if (app.isPresent()) {
                role.get().setData(roleData, app.get());
                try {
                    Role updatedRole = roleService.save(role.get());
                    return ResponseEntity.status(HttpStatus.OK).body(ServerResponse.success(new RoleData(updatedRole)));
                } catch (PersistenceException e) {
                    log.error(e.getMessage());
                    return ResponseEntity.status(HttpStatus.OK).body(ServerResponse.error("Server Error"));
                }
            }
            else return ResponseEntity.status(HttpStatus.OK).body(ServerResponse.error("App not found by id " + roleData.getAppId()));
        }
        else return ResponseEntity.status(HttpStatus.OK).body(ServerResponse.error("Role not found by id " + id));
    }
    @PostMapping
    public @ResponseBody
    ResponseEntity addRole(@RequestBody RoleData roleData) {
        Optional<App> app = appService.findByID(roleData.getAppId());
        if(app.isPresent()) {
            Role role = new Role(roleData, app.get());
            try {
                Role newRole = roleService.save(role);
                return ResponseEntity.status(HttpStatus.OK).body(ServerResponse.success(new RoleData(newRole)));
            } catch (PersistenceException e) {
                log.error(e.getMessage());
                return ResponseEntity.status(HttpStatus.OK).body(ServerResponse.error("Server Error"));
            }
        }
        else return ResponseEntity.status(HttpStatus.OK).body(ServerResponse.error("App not found by id " + roleData.getAppId()));
    }
}
