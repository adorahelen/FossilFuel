package dcu.fossilfuel.gate.controller;


import dcu.fossilfuel.gate.domain.Guestbook;
import dcu.fossilfuel.gate.service.GuestbookService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/guestbook")
public class GuestbookController {

    private final GuestbookService guestbookService;

    public GuestbookController(GuestbookService guestbookService) {
        this.guestbookService = guestbookService;
    }

    // 방명록 목록 조회 (GET)
    @GetMapping("/get")
    public List<Guestbook> getAllGuestbookEntries() {
        return guestbookService.getAllGuestbookEntries();
    }

    // 방명록 추가 (POST)
    // @Valid : 컨트롤러에서 유효성 검사 적용, dto 없지만 방명록이니
    @PostMapping("/post")
    public Guestbook addGuestbookEntry(@Valid @RequestBody Guestbook entry) {
        return guestbookService.addGuestbookEntry(entry.getContent());
    }
}

//	•	@GetMapping("/api/guestbook") → 전체 방명록을 조회
//	•	@PostMapping("/api/guestbook") → 방명록에 새 글을 추가