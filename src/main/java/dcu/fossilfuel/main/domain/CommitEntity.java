package dcu.fossilfuel.main.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "github_commits")
public class CommitEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private int commitCount;

    public CommitEntity(String username, int commitCount) {
        this.username = username;
        this.commitCount = commitCount;
    }
}