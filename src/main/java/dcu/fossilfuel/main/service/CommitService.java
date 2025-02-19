package dcu.fossilfuel.main.service;

import dcu.fossilfuel.main.CommitDTO;
import dcu.fossilfuel.main.domain.CommitEntity;
import dcu.fossilfuel.main.repository.CommitRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommitService {

    private final CommitRepository commitRepository;

    public CommitService(CommitRepository commitRepository) {
        this.commitRepository = commitRepository;
    }

    @Transactional
    public CommitDTO saveCommitData(String username, int commitCount) {
        CommitEntity existingUser = commitRepository.findByUsername(username);

        if (existingUser != null) {
            existingUser.setCommitCount(commitCount);  // 기존 사용자 업데이트
        } else {
            existingUser = new CommitEntity(username, commitCount);  // 신규 사용자 저장
        }

        commitRepository.save(existingUser);
        return new CommitDTO(existingUser.getUsername(), existingUser.getCommitCount());
    }


    public List<CommitDTO> getRanking() {
        return commitRepository.findAllByOrderByCommitCountDesc()
                .stream()
                .map(entity -> new CommitDTO(entity.getUsername(), entity.getCommitCount()))
                .collect(Collectors.toList());
    }


}
