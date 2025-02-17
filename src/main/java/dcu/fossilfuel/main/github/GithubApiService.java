package dcu.fossilfuel.main.github;

import org.kohsuke.github.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GithubApiService {

//    github:
//    token: "your_github_personal_access_token"

    private final GitHub github;

    public GithubApiService(@Value("${github.token}") String token) throws IOException {
        if (token == null || token.isEmpty()) {
            throw new IllegalStateException("GitHub OAuth 토큰이 설정되지 않았습니다.");
        }
        this.github = new GitHubBuilder().withOAuthToken(token).build();
    }

    public List<String> getCommits(String userId) {
        List<String> commitMessages = new ArrayList<>();
        try {
            GHCommitSearchBuilder builder = github.searchCommits()
                    .author(userId)
                    .sort(GHCommitSearchBuilder.Sort.AUTHOR_DATE);

            for (GHCommit commit : builder.list().withPageSize(7)) {
                commitMessages.add(commit.getCommitShortInfo().getMessage());
            }
        } catch (IOException e) {
            throw new RuntimeException("GitHub API 요청 중 오류 발생: " + e.getMessage());
        }

        return commitMessages;
    }
}
