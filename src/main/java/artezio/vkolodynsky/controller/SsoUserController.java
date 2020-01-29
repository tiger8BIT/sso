package artezio.vkolodynsky.controller;

import artezio.vkolodynsky.model.User;
import artezio.vkolodynsky.model.data.UserData;
import artezio.vkolodynsky.model.response.ServerResponse;
import artezio.vkolodynsky.service.UserService;
import artezio.vkolodynsky.validation.EmailExistsException;
import artezio.vkolodynsky.validation.LoginExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PersistenceException;

@Slf4j
@RestController
@RequestMapping
public class SsoUserController {
    @Autowired
    private UserService userService;
    @PostMapping("registration")
    public @ResponseBody
    ResponseEntity addUser(@RequestBody UserData userData) {
        try {
            User newUser = userService.registerNewUserAccount(userData);
            return ServerResponse.success(null);
        } catch (PersistenceException e) {
            log.error(e.getMessage());
            return ServerResponse.error("Server Error");
        } catch (LoginExistsException | EmailExistsException e) {
            log.error(e.getMessage());
            return ServerResponse.error(e.getMessage());
        }
    }
}
