package br.edu.figuriqa.controller;

import br.edu.figuriqa.repository.SwapPointRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SwapPointController extends BaseController {
    private final SwapPointRepository swapPointRepository;

    public SwapPointController(SwapPointRepository swapPointRepository) {
        this.swapPointRepository = swapPointRepository;
    }

    @GetMapping("/pontos")
    public String points(@RequestParam(defaultValue = "") String cidade, HttpSession session, Model model) {
        model.addAttribute("user", currentUser(session));
        model.addAttribute("cidade", cidade);
        model.addAttribute("points", cidade.isBlank() ? swapPointRepository.findAll() : swapPointRepository.findByCityContainingIgnoreCase(cidade));
        return "pontos";
    }
}
