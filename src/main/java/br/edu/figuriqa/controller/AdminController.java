package br.edu.figuriqa.controller;

import br.edu.figuriqa.config.SeedData;
import br.edu.figuriqa.model.Sticker;
import br.edu.figuriqa.model.Team;
import br.edu.figuriqa.repository.StickerRepository;
import br.edu.figuriqa.repository.TeamRepository;
import br.edu.figuriqa.repository.TradeItemRepository;
import br.edu.figuriqa.repository.UserRepository;
import br.edu.figuriqa.repository.UserStickerRepository;
import jakarta.transaction.Transactional;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AdminController extends BaseController {
    private final TeamRepository teamRepository;
    private final StickerRepository stickerRepository;
    private final UserRepository userRepository;
    private final UserStickerRepository userStickerRepository;
    private final TradeItemRepository tradeItemRepository;
    private final SeedData seedData;

    public AdminController(TeamRepository teamRepository, StickerRepository stickerRepository,
                           UserRepository userRepository, UserStickerRepository userStickerRepository,
                           TradeItemRepository tradeItemRepository, SeedData seedData) {
        this.teamRepository = teamRepository;
        this.stickerRepository = stickerRepository;
        this.userRepository = userRepository;
        this.userStickerRepository = userStickerRepository;
        this.tradeItemRepository = tradeItemRepository;
        this.seedData = seedData;
    }

    @GetMapping("/admin")
    public String admin(HttpSession session, Model model) {
        model.addAttribute("user", currentUser(session));
        model.addAttribute("teams", teamRepository.findAll());
        model.addAttribute("stickers", stickerRepository.findAll());
        model.addAttribute("users", userRepository.findAll());
        return "admin/index";
    }

    @PostMapping("/admin/teams")
    public String createTeam(@RequestParam String name, @RequestParam String groupName, @RequestParam String fictionalConfederation) {
        Team team = new Team();
        team.setName(name);
        team.setGroupName(groupName);
        team.setFictionalConfederation(fictionalConfederation);
        teamRepository.save(team);
        return "redirect:/admin";
    }

    @PostMapping("/admin/stickers")
    public String createSticker(@RequestParam String code, @RequestParam String title, @RequestParam String type,
                                @RequestParam String rarity, @RequestParam Long teamId) {
        Sticker sticker = new Sticker();
        sticker.setCode(code);
        sticker.setTitle(title);
        sticker.setType(type);
        sticker.setRarity(rarity);
        sticker.setTeam(teamRepository.findById(teamId).orElseThrow());
        stickerRepository.save(sticker);
        return "redirect:/admin";
    }

    @PostMapping("/admin/stickers/delete")
    @Transactional
    public String deleteSticker(@RequestParam Long id) {
        stickerRepository.findById(id).ifPresent(sticker -> {
            tradeItemRepository.deleteBySticker(sticker);
            userStickerRepository.deleteBySticker(sticker);
            stickerRepository.delete(sticker);
        });
        return "redirect:/admin";
    }

    @PostMapping("/admin/reset")
    public String reset() {
        seedData.reset();
        return "redirect:/login";
    }
}
