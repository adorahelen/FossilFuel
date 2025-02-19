package dcu.fossilfuel.main.repository;

import dcu.fossilfuel.main.domain.CommitEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface CommitRepository extends JpaRepository<CommitEntity, Long> {

    CommitEntity findByUsername(String username);
    List<CommitEntity> findAllByOrderByCommitCountDesc(); // 커밋 개수 내림차순 정렬
}