package dcu.fossilfuel.main.controller;

import dcu.fossilfuel.main.dto.CommitDTO;
import dcu.fossilfuel.main.service.CommitService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommitController {

    private final CommitService commitService;

    public CommitController(CommitService commitService) {
        this.commitService = commitService;
    }

    // 새로운 커밋 데이터 저장
    @PostMapping("/api/commits/save")
    public CommitDTO saveCommit(@RequestBody CommitDTO commitDTO) {
        return commitService.saveCommitData(commitDTO.getUsername(), commitDTO.getCommitCount());
    }

    // 랭킹 조회 API
    @GetMapping("/api/commits/ranking")
    public List<CommitDTO> getCommitRanking() {
        return commitService.getRanking();
    }

}

