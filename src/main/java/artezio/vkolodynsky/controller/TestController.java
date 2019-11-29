package artezio.vkolodynsky.controller;
import artezio.vkolodynsky.model.App;
import artezio.vkolodynsky.repository.AppRepository;
import artezio.vkolodynsky.service.AppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Collections;

@Controller
public class TestController {
    @Autowired
    private AppService repository;
    @GetMapping("/test")
    public String getTestPage(){
        repository.findAll();
        App app = new App();
        app.setUrl("app");
        repository.save(app);
        return "test";
    }
    @GetMapping("/index")
    public String getIndexPage(Model model) {
        model.addAttribute("apps",repository.findAll());
        return "index";
    }
}
