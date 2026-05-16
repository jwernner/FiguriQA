package br.edu.figuriqa.controller;

import br.edu.figuriqa.repository.TradeRepository;
import br.edu.figuriqa.service.TradeService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TradeController extends BaseController {
    private final TradeService tradeService;
    private final TradeRepository tradeRepository;

    public TradeController(TradeService tradeService, TradeRepository tradeRepository) {
        this.tradeService = tradeService;
        this.tradeRepository = tradeRepository;
    }

    @GetMapping("/trocas/sugestoes")
    public String suggestions(HttpSession session, Model model) {
        model.addAttribute("user", currentUser(session));
        model.addAttribute("suggestions", tradeService.suggestions(currentUser(session)));
        return "trocas-sugestoes";
    }

    @PostMapping("/trocas/propor")
    public String propose(@RequestParam Long receiverId, @RequestParam Long giveStickerId,
                          @RequestParam Long receiveStickerId, HttpSession session) {
        var trade = tradeService.createProposal(currentUser(session), receiverId, giveStickerId, receiveStickerId);
        return "redirect:/trocas/" + trade.getId();
    }

    @GetMapping("/trocas")
    public String trades(HttpSession session, Model model) {
        model.addAttribute("user", currentUser(session));
        model.addAttribute("trades", tradeService.tradesFor(currentUser(session)));
        return "trocas";
    }

    @GetMapping("/trocas/{id}")
    public String detail(@PathVariable Long id, HttpSession session, Model model) {
        var trade = tradeRepository.findById(id).orElseThrow();
        model.addAttribute("user", currentUser(session));
        model.addAttribute("trade", trade);
        model.addAttribute("items", tradeService.items(trade));
        return "troca-detalhe";
    }

    @PostMapping("/trocas/{id}/aceitar")
    public String accept(@PathVariable Long id) {
        tradeService.accept(id);
        return "redirect:/trocas/" + id;
    }

    @PostMapping("/trocas/{id}/cancelar")
    public String cancel(@PathVariable Long id) {
        tradeService.cancel(id);
        return "redirect:/trocas/" + id;
    }

    @PostMapping("/trocas/{id}/concluir")
    public String complete(@PathVariable Long id, @RequestParam String confirmationCode, Model model) {
        tradeService.complete(id, confirmationCode);
        return "redirect:/trocas/" + id;
    }
}
