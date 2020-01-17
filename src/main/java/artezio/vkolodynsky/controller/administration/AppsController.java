package artezio.vkolodynsky.controller.administration;

import artezio.vkolodynsky.model.App;
import artezio.vkolodynsky.model.data.AppData;
import artezio.vkolodynsky.service.AppService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping("administration/apps")
public class AppsController {
    @AllArgsConstructor
    class IdResponse {
        public int id;
    }
    @Autowired
    private AppService appService;
    @GetMapping
    public @ResponseBody
    ResponseEntity getAppsList() {
        List<AppData> apps = appService.findAll().stream().map(AppData::new).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(apps);
    }
    @PostMapping("delete")
    public void deleteApp(@RequestBody Integer id) {
        appService.deleteByID(id);
    }
    @PostMapping("update")
    public @ResponseBody
    ResponseEntity updateApp(@RequestBody AppData appData) {
        App app = appService.findByID(appData.getId());
        app.setUrl(appData.getUrl());
        app.setName(appData.getName());
        appService.save(app);
        return ResponseEntity.status(HttpStatus.OK).body(app.getId());
    }
    @PostMapping("add")
    public @ResponseBody
    ResponseEntity putApp(@RequestBody AppData appData) {
        App app = new App(appData);
        return ResponseEntity.status(HttpStatus.OK).body(new AppData(appService.save(app)));
    }
}
