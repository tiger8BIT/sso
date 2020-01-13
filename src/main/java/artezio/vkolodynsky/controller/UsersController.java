package artezio.vkolodynsky.controller;

import artezio.vkolodynsky.model.App;
import artezio.vkolodynsky.model.User;
import artezio.vkolodynsky.model.data.AppData;
import artezio.vkolodynsky.model.data.UserData;
import artezio.vkolodynsky.service.AppService;
import artezio.vkolodynsky.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@CrossOrigin(origins = {"http://localhost:3000"})
public class UsersController {
    @Autowired
    private UserService userService;
    @GetMapping("/users")
    public @ResponseBody
    ResponseEntity getUserssList() {
        List<UserData> apps = userService.findAll().stream().map(UserData::new).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(apps);
    }

    @PostMapping("delete/user")
    public @ResponseBody
    ResponseEntity deleteUser(@RequestBody Integer id) {
        userService.deleteByID(id);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }
    @PostMapping("update/user")
    public @ResponseBody
    ResponseEntity updateUser(@RequestBody UserData userData) {
        User user = userService.findByID(userData.getId());
        user.setData(userData);
        userService.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(user.getId());
    }
    @PostMapping("add/user")
    public @ResponseBody
    ResponseEntity putApp(@RequestBody UserData userData) {
        User user = new User(userData);
        userService.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(user.getId());
    }
}
