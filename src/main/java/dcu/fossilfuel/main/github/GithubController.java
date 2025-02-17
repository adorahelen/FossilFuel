package dcu.fossilfuel.main.github;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:5500") // CORS 허용 (HTML을 로컬에서 실행할 경우 필요)
public class GithubController {
    private final GithubApiService githubApiService;

    public GithubController(GithubApiService githubApiService) {
        this.githubApiService = githubApiService;
    }

    @GetMapping("/api/commits")
    public List<String> getCommits(@RequestParam String userId) {
        return githubApiService.getCommits(userId);
    }
}
