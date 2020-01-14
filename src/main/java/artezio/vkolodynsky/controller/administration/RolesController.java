package artezio.vkolodynsky.controller.administration;

import artezio.vkolodynsky.model.App;
import artezio.vkolodynsky.model.Role;
import artezio.vkolodynsky.model.data.AppData;
import artezio.vkolodynsky.model.data.RoleData;
import artezio.vkolodynsky.service.AppService;
import artezio.vkolodynsky.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("administration/roles")
public class RolesController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private AppService appService;
    @GetMapping
    public @ResponseBody
    ResponseEntity getAppsList() {
        List<RoleData>  apps = roleService.findAll().stream().map(RoleData::new).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(apps);
    }
    @PostMapping("delete")
    public @ResponseBody
    ResponseEntity deleteApp(@RequestBody Integer id) {
        roleService.deleteByID(id);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }
    @PostMapping("update")
    public @ResponseBody
    ResponseEntity updateApp(@RequestBody RoleData roleData) {
        Role role = roleService.findByID(roleData.getId());
        role.setData(roleData, appService.findByID(roleData.getAppId()));
        roleService.save(role);
        return ResponseEntity.status(HttpStatus.OK).body(role.getId());
    }
    @PostMapping("add")
    public @ResponseBody
    ResponseEntity putApp(@RequestBody RoleData roleData) {
        App app = appService.findByID(roleData.getAppId());
        Role role = new Role(roleData, app);
        roleService.save(role);
        return ResponseEntity.status(HttpStatus.OK).body(role.getId());
    }
}
