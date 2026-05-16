package br.edu.figuriqa.controller;

import br.edu.figuriqa.service.CollectionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CollectionController extends BaseController {
    private final CollectionService collectionService;

    public CollectionController(CollectionService collectionService) {
        this.collectionService = collectionService;
    }

    @GetMapping("/colecao")
    public String collection(@RequestParam(defaultValue = "todas") String filtro,
                             @RequestParam(defaultValue = "") String busca,
                             HttpSession session, Model model) {
        model.addAttribute("user", currentUser(session));
        model.addAttribute("stickers", collectionService.stickerViews(currentUser(session), filtro, busca));
        model.addAttribute("filtro", filtro);
        model.addAttribute("busca", busca);
        return "colecao";
    }

    @PostMapping("/colecao/quantidade")
    public String update(@RequestParam Long stickerId, @RequestParam int quantity, HttpSession session) {
        collectionService.updateQuantity(currentUser(session), stickerId, quantity);
        return "redirect:/colecao";
    }
}
