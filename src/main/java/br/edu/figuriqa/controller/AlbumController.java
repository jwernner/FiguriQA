package br.edu.figuriqa.controller;

import br.edu.figuriqa.repository.TeamRepository;
import br.edu.figuriqa.service.CollectionService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AlbumController extends BaseController {
    private final TeamRepository teamRepository;
    private final CollectionService collectionService;

    public AlbumController(TeamRepository teamRepository, CollectionService collectionService) {
        this.teamRepository = teamRepository;
        this.collectionService = collectionService;
    }

    @GetMapping("/album")
    public String album(@RequestParam(defaultValue = "todas") String filtro,
                        @RequestParam(defaultValue = "") String busca,
                        HttpSession session, Model model) {
        model.addAttribute("user", currentUser(session));
        model.addAttribute("teams", teamRepository.findAll());
        model.addAttribute("stickers", collectionService.stickerViews(currentUser(session), filtro, busca));
        model.addAttribute("filtro", filtro);
        model.addAttribute("busca", busca);
        return "album";
    }
}
