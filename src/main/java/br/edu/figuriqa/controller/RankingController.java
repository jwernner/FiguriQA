package br.edu.figuriqa.controller;

import br.edu.figuriqa.service.RankingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RankingController extends BaseController {
    private final RankingService rankingService;

    public RankingController(RankingService rankingService) {
        this.rankingService = rankingService;
    }

    @GetMapping("/ranking")
    public String ranking(HttpSession session, Model model) {
        model.addAttribute("user", currentUser(session));
        model.addAttribute("ranking", rankingService.ranking());
        return "ranking";
    }

    @GetMapping("/ranking.csv")
    public ResponseEntity<String> csv() {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=ranking-figuriqa.csv")
                .contentType(MediaType.valueOf("text/csv"))
                .body(rankingService.exportCsv());
    }
}
