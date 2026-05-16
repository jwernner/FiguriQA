package br.edu.figuriqa.service;

import br.edu.figuriqa.model.Role;
import br.edu.figuriqa.model.User;
import br.edu.figuriqa.repository.UserRepository;
import java.util.Comparator;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RankingService {
    private final UserRepository userRepository;
    private final CollectionService collectionService;

    public RankingService(UserRepository userRepository, CollectionService collectionService) {
        this.userRepository = userRepository;
        this.collectionService = collectionService;
    }

    public List<RankingEntry> ranking() {
        return userRepository.findByRole(Role.COLECIONADOR).stream()
                .map(user -> new RankingEntry(user, collectionService.stats(user)))
                .sorted(Comparator.comparingDouble((RankingEntry entry) -> entry.stats().unique() + entry.stats().repeated()).reversed())
                .toList();
    }

    public String exportCsv() {
        StringBuilder csv = new StringBuilder("usuario,percentual,unicas,repetidas,faltantes\n");
        for (RankingEntry entry : ranking()) {
            CollectionStats stats = entry.stats();
            csv.append(entry.user().getUsername()).append(',')
                    .append(String.format(java.util.Locale.US, "%.1f", stats.percent())).append(',')
                    .append(stats.unique()).append(',')
                    .append(stats.missing()).append(',')
                    .append(stats.repeated()).append('\n');
        }
        return csv.toString();
    }
}
