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
        List<UserData> users = userService.findAll().stream().map(UserData::new).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(ServerResponse.success(users));
    }
    @GetMapping("{id}")
    public @ResponseBody
    ResponseEntity getUser(@PathVariable Integer id) {
        Optional<User> user = userService.findByID(id);
        if(user.isPresent()) {
            UserData userData = new UserData(user.get());
            return ResponseEntity.status(HttpStatus.OK).body(ServerResponse.success(userData));
        }
        else return ResponseEntity.status(HttpStatus.OK).body(ServerResponse.error("User not found by id " + id));
    }
    @DeleteMapping("{id}")
    public @ResponseBody
    ResponseEntity deleteUser(@PathVariable Integer id) {
        try {
            userService.deleteByID(id);
        } catch (PersistenceException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.OK).body(ServerResponse.error("Server Error"));
        }
        return ResponseEntity.status(HttpStatus.OK).body(ServerResponse.success(null));
    }
    @PutMapping("{id}")
    public @ResponseBody
    ResponseEntity updateUser(@PathVariable Integer id, @RequestBody UserData userData) {
        Optional<User> user = userService.findByID(id);
        if (user.isPresent()){
            try {
                user.get().setData(userData);
                User updatedUser = userService.save(user.get());
                return ResponseEntity.status(HttpStatus.OK).body(ServerResponse.success(new UserData(updatedUser)));
            } catch (PersistenceException e) {
                log.error(e.getMessage());
                return ResponseEntity.status(HttpStatus.OK).body(ServerResponse.error("Server Error"));
            }
        }
        else return ResponseEntity.status(HttpStatus.OK).body(ServerResponse.error("User not found by id " + id));
    }
    @PostMapping
    public @ResponseBody
    ResponseEntity addUser(@RequestBody UserData userData) {
        User user = new User(userData);
        try {
            User newUser = userService.save(user);
            return ResponseEntity.status(HttpStatus.OK).body(ServerResponse.success(new UserData(newUser)));
        } catch (PersistenceException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.OK).body(ServerResponse.error("Server Error"));
        }
    }
    @GetMapping("{id}/roles")
    public @ResponseBody
    ResponseEntity getRolesOfUser(@PathVariable Integer id) {
        Optional<User> user = userService.findByID(id);
        if (user.isPresent()) {
            List<Role> roles = roleService.findByUser(user.get());
            if (roles != null && !roles.isEmpty()) {
                List<RoleData> rolesData = roles.stream().map(RoleData::new).collect(Collectors.toList());
                return ResponseEntity.status(HttpStatus.OK).body(ServerResponse.success(rolesData));
            }
            else {
                return ResponseEntity.status(HttpStatus.OK).body(ServerResponse.success(Collections.EMPTY_LIST));
            }
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ServerResponse.error("User not found by id " + id));
        }
    }
    @PostMapping("{userId}/roles/{roleId}")
    public @ResponseBody
    ResponseEntity userRolesAdd(@PathVariable Integer userId, @PathVariable Integer roleId) {
        Optional<Role> role = roleService.findByID(roleId);
        if (!userService.existsById(userId)) return ResponseEntity.status(HttpStatus.OK).body(ServerResponse.error("User not found by id " + userId));
        if (role.isEmpty()) return ResponseEntity.status(HttpStatus.OK).body(ServerResponse.error("Role not found by id " + roleId));
        try {
            User updatedUser = userService.addRole(userId, role.get());
            return ResponseEntity.status(HttpStatus.OK).body(ServerResponse.success(new UserData(updatedUser)));
        } catch (PersistenceException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.OK).body(ServerResponse.error("Server Error"));
        }
    }
    @DeleteMapping("{userId}/roles/{roleId}")
    public @ResponseBody
    ResponseEntity userRolesDelete(@PathVariable Integer userId, @PathVariable Integer roleId) {
        Optional<Role> role = roleService.findByID(roleId);
        if (!userService.existsById(userId)) return ResponseEntity.status(HttpStatus.OK).body(ServerResponse.error("User not found by id " + userId));
        if (role.isEmpty()) return ResponseEntity.status(HttpStatus.OK).body(ServerResponse.error("Role not found by id " + roleId));
        if(!userService.containsRole(userId, role.get())) return ResponseEntity.status(HttpStatus.OK).body(ServerResponse.error("User haven't role with id " + roleId));
        try {
            User updatedUser = userService.deleteRole(userId, role.get());
            return ResponseEntity.status(HttpStatus.OK).body(ServerResponse.success(new UserData(updatedUser)));
        } catch (PersistenceException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.OK).body(ServerResponse.error("Server Error"));
        }
    }
}
