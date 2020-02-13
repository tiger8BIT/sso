package artezio.vkolodynsky.controller.administration;

import artezio.vkolodynsky.model.App;
import artezio.vkolodynsky.model.data.AppData;
import artezio.vkolodynsky.model.response.ServerResponse;
import artezio.vkolodynsky.service.AppService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.PersistenceException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@RestController
@RequestMapping("administration/apps")
public class AppsController {
    @Autowired
    private AppService appService;
    @GetMapping
    public @ResponseBody
    ResponseEntity getAppsList() {
        List<AppData> apps = appService.findAll().stream().map(AppData::from).collect(Collectors.toList());
        return ServerResponse.success(apps);
    }
    @GetMapping("{id}")
    public @ResponseBody
    ResponseEntity getApp(@PathVariable Integer id) {
        Optional<App> app = appService.findByID(id);
        if (app.isPresent()) {
            AppData appData = AppData.from(app.get());
            return ServerResponse.success(appData);
        }
        else return ServerResponse.error("App not found by id " + id);
    }
    @DeleteMapping("{id}")
    public @ResponseBody
    ResponseEntity deleteApp(@PathVariable Integer id) {
        try {
            appService.deleteByID(id);
        } catch (PersistenceException e) {
            log.error(e.getMessage());
            return ServerResponse.error("Server Error");
        }
        return ServerResponse.success(null);
    }
    @PutMapping("{id}")
    public @ResponseBody
    ResponseEntity updateApp(@PathVariable Integer id, @RequestBody AppData appData) {
        Optional<App> app = appService.findByID(id);
        if (app.isPresent()) {
            try {
                app.get().setUrl(appData.getUrl());
                app.get().setName(appData.getName());
                App updatedApp = appService.save(app.get());
                return ServerResponse.success(AppData.from(updatedApp));
            } catch (PersistenceException e) {
                log.error(e.getMessage());
                return ServerResponse.error("Server Error");
            }
        }
        else return ServerResponse.error("App not found by id " + id);

    }
    @PostMapping
    public @ResponseBody
    ResponseEntity putApp(@RequestBody AppData appData) {
        App app = App.from(appData);
        try {
            App newApp = appService.save(app);
            return ServerResponse.success(AppData.from(newApp));
        } catch (PersistenceException e) {
            log.error(e.getMessage());
            return ServerResponse.error("Server Error");
        }
    }
}
