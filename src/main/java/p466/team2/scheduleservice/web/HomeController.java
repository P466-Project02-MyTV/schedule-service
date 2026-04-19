package p466.team2.scheduleservice.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import p466.team2.scheduleservice.config.MyTVProperties;

@RestController
public class HomeController {
    private final MyTVProperties myTVProperties;

    public HomeController(MyTVProperties myTVProperties) {
        this.myTVProperties = myTVProperties;
    }

    @GetMapping("/")
    public String getGreeting() {
        return myTVProperties.getGreeting();
    }

}
