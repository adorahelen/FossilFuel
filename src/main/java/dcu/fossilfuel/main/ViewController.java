package dcu.fossilfuel.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/main")
    public String mains() {
        return "main/added";
    }
}
