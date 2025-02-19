package dcu.fossilfuel.main;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommitDTO {

    private String username;
    private int commitCount;

}