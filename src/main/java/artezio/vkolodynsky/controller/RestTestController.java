package artezio.vkolodynsky.controller;

import artezio.vkolodynsky.model.data.AppData;
import artezio.vkolodynsky.service.AppService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
@Slf4j
public class RestTestController {
    @Autowired
    private AppService appService;
    @CrossOrigin
    @PostMapping("/search")
    public @ResponseBody
    ResponseEntity search() {
        List<AppData>  apps = appService.findAll().stream().map(AppData::new).collect(Collectors.toList());
        log.info("Response: {}", apps);
        return ResponseEntity.status(HttpStatus.OK).body(apps);
    }
}
