package dcu.fossilfuel.main;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/main")
    public String mains() {
        return "main/github";
    } // 404

    @GetMapping("/main1")
    public String mains1() {
        return "main/github1";
    } // 검은 잔디 (오와열 조금 깨짐)

    @GetMapping("/main2")
    public String mains2() {
        return "main/github2";
    } // 그냥 커밋 리스트 (히스토리)

    @GetMapping("/main3")
    public String mains3() {
        return "main/github3";
    } // 그냥 커밋 리스트 (히스토리)

    @GetMapping("/main4")
    public String gQlg() {
        return "main/github4";
    } // 그냥 커밋 리스트 (히스토리)


}
