package artezio.vkolodynsky.controller;

import artezio.vkolodynsky.model.App;
import artezio.vkolodynsky.model.data.AppData;
import artezio.vkolodynsky.service.AppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@Slf4j
public class AppsController {
    @Autowired
    private AppService appService;
    @GetMapping("/apps")
    public @ResponseBody
    ResponseEntity getAppsList() {
        List<AppData>  apps = appService.findAll().stream().map(AppData::new).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(apps);
    }
    @PostMapping("delete/app")
    public @ResponseBody
    ResponseEntity deleteApp(@RequestBody Integer id) {
        appService.deleteByID(id);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }
    @PostMapping("update/app")
    public @ResponseBody
    ResponseEntity updateApp(@RequestBody AppData appData) {
        App app = appService.findByID(appData.getId());
        app.setUrl(appData.getUrl());
        app.setName(appData.getName());
        appService.save(app);
        return ResponseEntity.status(HttpStatus.OK).body(app.getId());
    }
    @PostMapping("add/app")
    public @ResponseBody
    ResponseEntity putApp(@RequestBody AppData appData) {
        App app = new App(appData);
        appService.save(app);
        return ResponseEntity.status(HttpStatus.OK).body(app.getId());
    }
}
