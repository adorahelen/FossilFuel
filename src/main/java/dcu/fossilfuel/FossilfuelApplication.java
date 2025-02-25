package dcu.fossilfuel;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

@SpringBootApplication
public class FossilfuelApplication {

    public static void main(String[] args) {
        createLogDirectory(); // 로그 디렉토리 생성
        SpringApplication.run(FossilfuelApplication.class, args);
    }

    private static void createLogDirectory() {
        File logDir = new File("logs");
        if (!logDir.exists()) {
            boolean created = logDir.mkdirs(); // 디렉토리 생성
            if (created) {
                System.out.println("✅ logs 디렉토리 생성 완료!");
            } else {
                System.out.println("⚠️ logs 디렉토리 생성 실패!");
            }
        }
    }
}