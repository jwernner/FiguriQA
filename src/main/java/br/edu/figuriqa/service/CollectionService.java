package br.edu.figuriqa.service;

import br.edu.figuriqa.model.Sticker;
import br.edu.figuriqa.model.User;
import br.edu.figuriqa.model.UserSticker;
import br.edu.figuriqa.repository.StickerRepository;
import br.edu.figuriqa.repository.UserStickerRepository;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CollectionService {
    private final StickerRepository stickerRepository;
    private final UserStickerRepository userStickerRepository;

    public CollectionService(StickerRepository stickerRepository, UserStickerRepository userStickerRepository) {
        this.stickerRepository = stickerRepository;
        this.userStickerRepository = userStickerRepository;
    }

    public CollectionStats stats(User user) {
        long total = stickerRepository.count();
        List<UserSticker> items = userStickerRepository.findByUser(user);
        long unique = items.stream().filter(item -> item.getQuantity() > 0).count();
        long missing = Math.max(total - unique, 0);
        long repeated = items.stream().filter(item -> item.getQuantity() > 1).mapToLong(item -> item.getQuantity() - 1L).sum();
        double percent = total == 0 ? 0 : ((unique + repeated) * 100.0) / total;
        return new CollectionStats(total, unique, missing, repeated, percent);
    }

    public List<StickerView> stickerViews(User user, String filter, String query) {
        Map<Long, Integer> quantities = new HashMap<>();
        userStickerRepository.findByUser(user).forEach(item -> quantities.put(item.getSticker().getId(), item.getQuantity()));
        List<StickerView> views = new ArrayList<>();
        for (Sticker sticker : stickerRepository.findAll()) {
            int quantity = quantities.getOrDefault(sticker.getId(), 0);
            String status = statusFor(quantity);
            if (!matchesFilter(filter, status, quantity, sticker.getId())) {
                continue;
            }
            if (query != null && !query.isBlank()) {
                String q = query.trim();
                if (!sticker.getCode().contains(q) && !sticker.getTitle().contains(q)) {
                    continue;
                }
            }
            views.add(new StickerView(sticker, quantity, status));
        }
        views.sort(Comparator.comparing(view -> view.sticker().getTeam().getName() + view.sticker().getCode()));
        return views;
    }

    private boolean matchesFilter(String filter, String status, int quantity, Long stickerId) {
        if (filter == null || filter.isBlank() || "todas".equals(filter)) {
            return true;
        }
        if ("obtidas".equals(filter)) {
            return quantity > 0;
        }
        if ("faltantes".equals(filter)) {
            return quantity <= 0 || stickerId % 13 == 0;
        }
        if ("repetidas".equals(filter)) {
            return "repetida".equals(status);
        }
        return true;
    }

    public String statusFor(int quantity) {
        if (quantity <= 0) {
            return "faltante";
        }
        if (quantity >= 1) {
            return "repetida";
        }
        return "obtida";
    }

    @Transactional
    public void updateQuantity(User user, Long stickerId, int quantity) {
        Sticker sticker = stickerRepository.findById(stickerId).orElseThrow();
        UserSticker item = userStickerRepository.findByUserAndSticker(user, sticker).orElseGet(() -> {
            UserSticker created = new UserSticker();
            created.setUser(user);
            created.setSticker(sticker);
            return created;
        });
        item.setQuantity(quantity);
        userStickerRepository.save(item);
    }

    public int quantity(User user, Sticker sticker) {
        return userStickerRepository.findByUserAndSticker(user, sticker)
                .map(UserSticker::getQuantity)
                .orElse(0);
    }
}
