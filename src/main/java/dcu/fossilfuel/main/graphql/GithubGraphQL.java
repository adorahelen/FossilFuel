package dcu.fossilfuel.main.graphql;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@RestController
public class GithubGraphQL {

    @Value("${github.token}")
    private String token;  // application.yml 파일에서 토큰을 읽어옴

    @GetMapping("/graphql/user/{username}")
    public String getUserContributions(@PathVariable("username") String username) {
        String query = "{ user(login: \"" + username + "\") { contributionsCollection { contributionCalendar { weeks { contributionDays { date contributionCount }}}}}}";
        return sendGraphQLRequest(query);
    }

    private String sendGraphQLRequest(String query) {
        try {
            URL url = new URL("https://api.github.com/graphql");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Authorization", "Bearer " + token);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            // GraphQL 쿼리 JSON 변환
            String jsonInputString = "{ \"query\": \"" + query.replace("\"", "\\\"") + "\" }";

            // 요청 데이터 전송
            try (OutputStream os = conn.getOutputStream()) {
                byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            // 응답 데이터 읽기
            StringBuilder response = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }

            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
