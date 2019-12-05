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
public class RestTestController {
    @Autowired
    private AppService appService;
    @GetMapping("/apps")
    public @ResponseBody
    ResponseEntity getAppsList() {
        List<AppData>  apps = appService.findAll().stream().map(AppData::new).collect(Collectors.toList());
        log.info("Response: {}", apps);
        return ResponseEntity.status(HttpStatus.OK).body(apps);
    }
    @PostMapping("add/app")
    public @ResponseBody
    ResponseEntity putApp(@RequestBody AppData appData) {
        App app = new App(appData);
        log.info("--------------------------- {}", app);
        appService.save(app);
        return ResponseEntity.status(HttpStatus.OK).body(app.getId());
    }
}
