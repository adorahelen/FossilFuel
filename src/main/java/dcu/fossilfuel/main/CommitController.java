package dcu.fossilfuel.main;

import dcu.fossilfuel.main.service.CommitService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/commits")
public class CommitController {

    private final CommitService commitService;

    public CommitController(CommitService commitService) {
        this.commitService = commitService;
    }

    // 새로운 커밋 데이터 저장
    @PostMapping("/save")
    public CommitDTO saveCommit(@RequestBody CommitDTO commitDTO) {
        return commitService.saveCommitData(commitDTO.getUsername(), commitDTO.getCommitCount());
    }

    // 랭킹 조회 API
    @GetMapping("/ranking")
    public List<CommitDTO> getCommitRanking() {
        return commitService.getRanking();
    }

}

