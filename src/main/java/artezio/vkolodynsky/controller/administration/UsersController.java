package artezio.vkolodynsky.controller.administration;

import artezio.vkolodynsky.model.Role;
import artezio.vkolodynsky.model.User;
import artezio.vkolodynsky.model.data.RoleData;
import artezio.vkolodynsky.model.data.UserData;
import artezio.vkolodynsky.model.request.UpdateAction;
import artezio.vkolodynsky.model.response.ServerResponse;
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
@RequestMapping("administration/users")
public class UsersController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @GetMapping
    public @ResponseBody
    ResponseEntity getUsersList() {
        List<UserData> users = userService.findAll().stream().map(UserData::from).collect(Collectors.toList());
        return ServerResponse.success(users);
    }
    @GetMapping("{id}")
    public @ResponseBody
    ResponseEntity getUser(@PathVariable Integer id) {
        Optional<User> user = userService.findByID(id);
        if(user.isPresent()) {
            UserData userData = UserData.from(user.get());
            return ServerResponse.success(userData);
        }
        else return ServerResponse.error("User not found by id " + id);
    }
    @DeleteMapping("{id}")
    public @ResponseBody
    ResponseEntity deleteUser(@PathVariable Integer id) {
        try {
            userService.deleteByID(id);
        } catch (PersistenceException e) {
            log.error(e.getMessage());
            return ServerResponse.error("Server Error");
        }
        return ServerResponse.success(null);
    }
    @PutMapping("{id}")
    public @ResponseBody
    ResponseEntity updateUser(@PathVariable Integer id, @RequestBody UserData userData) {
        Optional<User> user = userService.findByID(id);
        if (user.isPresent()){
            try {
                user.get().setData(userData);
                User updatedUser = userService.save(user.get());
                return ServerResponse.success(UserData.from(updatedUser));
            } catch (PersistenceException e) {
                log.error(e.getMessage());
                return ServerResponse.error("Server Error");
            }
        }
        else return ServerResponse.error("User not found by id " + id);
    }
    @PostMapping
    public @ResponseBody
    ResponseEntity addUser(@RequestBody UserData userData) {
        User user = User.from(userData);
        try {
            User newUser = userService.save(user);
            return ServerResponse.success(UserData.from(newUser));
        } catch (PersistenceException e) {
            log.error(e.getMessage());
            return ServerResponse.error("Server Error");
        }
    }
    @GetMapping("{id}/roles")
    public @ResponseBody
    ResponseEntity getRolesOfUser(@PathVariable Integer id) {
        Optional<User> user = userService.findByID(id);
        if (user.isPresent()) {
            List<Role> roles = roleService.findByUser(user.get());
            if (roles != null && !roles.isEmpty()) {
                List<RoleData> rolesData = roles.stream().map(RoleData::from).collect(Collectors.toList());
                return ServerResponse.success(rolesData);
            }
            else {
                return ServerResponse.success(Collections.EMPTY_LIST);
            }
        }
        else {
            return ServerResponse.error("User not found by id " + id);
        }
    }
    @PostMapping("{userId}/roles/{roleId}")
    public @ResponseBody
    ResponseEntity userRolesAdd(@PathVariable Integer userId, @PathVariable Integer roleId) {
        Optional<Role> role = roleService.findByID(roleId);
        if (!userService.existsById(userId)) return ServerResponse.error("User not found by id " + userId);
        if (role.isEmpty()) return ServerResponse.error("Role not found by id " + roleId);
        try {
            User updatedUser = userService.addRole(userId, role.get());
            return ServerResponse.success(UserData.from(updatedUser));
        } catch (PersistenceException e) {
            log.error(e.getMessage());
            return ServerResponse.error("Server Error");
        }
    }
    @DeleteMapping("{userId}/roles/{roleId}")
    public @ResponseBody
    ResponseEntity userRolesDelete(@PathVariable Integer userId, @PathVariable Integer roleId) {
        Optional<Role> role = roleService.findByID(roleId);
        if (!userService.existsById(userId)) return ServerResponse.error("User not found by id " + userId);
        if (role.isEmpty()) return ServerResponse.error("Role not found by id " + roleId);
        if(!userService.containsRole(userId, role.get())) return ServerResponse.error("User haven't role with id " + roleId);
        try {
            User updatedUser = userService.deleteRole(userId, role.get());
            return ServerResponse.success(UserData.from(updatedUser));
        } catch (PersistenceException e) {
            log.error(e.getMessage());
            return ServerResponse.error("Server Error");
        }
    }
}
