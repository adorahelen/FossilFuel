package dcu.fossilfuel.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/main")
    public String mains() {
        return "main/forSmartPhone2";
    } // 베이스

    @GetMapping("/main1")
    public String mains1() {
        return "main/completeGitHub";
    } // 완성본

}
