package artezio.vkolodynsky.controller.administration;

import artezio.vkolodynsky.model.App;
import artezio.vkolodynsky.model.Role;
import artezio.vkolodynsky.model.User;
import artezio.vkolodynsky.model.data.AppData;
import artezio.vkolodynsky.model.data.RoleData;
import artezio.vkolodynsky.model.data.UserData;
import artezio.vkolodynsky.model.request_body.UpdateAction;
import artezio.vkolodynsky.service.AppService;
import artezio.vkolodynsky.service.RoleService;
import artezio.vkolodynsky.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("administration/users")
public class UsersController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @GetMapping
    public @ResponseBody
    ResponseEntity getUsersList() {
        List<UserData> roles = userService.findAll().stream().map(UserData::new).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(roles);
    }
    @PostMapping("delete")
    public @ResponseBody
    ResponseEntity deleteUser(@RequestBody Integer id) {
        userService.deleteByID(id);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }
    @PostMapping("update")
    public @ResponseBody
    ResponseEntity updateUser(@RequestBody UserData userData) {
        User user = userService.findByID(userData.getId()).get();
        user.setData(userData);
        userService.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(user.getId());
    }
    @PostMapping("update/roles/add")
    public @ResponseBody
    ResponseEntity updateUserRolesAdd(@RequestBody UpdateAction updateAction) {
        Optional<User> user = userService.findByID(updateAction.targetItemId);
        if (user.isPresent()) {
            Optional<Role> role = roleService.findByID(updateAction.actionItemId);
            if(role.isPresent()) {
                userService.addRole(user.get().getId(), role.get());
                return ResponseEntity.status(HttpStatus.OK)
                        .body("Role is added");
            }
            else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Server can't found role with id " + updateAction.actionItemId);
            }
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Server can't found user with id " + updateAction.targetItemId);
        }

    }
    @PostMapping("add")
    public @ResponseBody
    ResponseEntity putApp(@RequestBody UserData userData) {
        User user = new User(userData);
        userService.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(user.getId());
    }
}
