package br.edu.figuriqa.controller;

import br.edu.figuriqa.service.CollectionService;
import br.edu.figuriqa.service.TradeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController extends BaseController {
    private final CollectionService collectionService;
    private final TradeService tradeService;

    public DashboardController(CollectionService collectionService, TradeService tradeService) {
        this.collectionService = collectionService;
        this.tradeService = tradeService;
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session, Model model) {
        var user = currentUser(session);
        model.addAttribute("user", user);
        model.addAttribute("stats", collectionService.stats(user));
        model.addAttribute("trades", tradeService.dashboardTrades(user));
        return "dashboard";
    }
}
