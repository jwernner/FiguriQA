package br.edu.figuriqa;

import br.edu.figuriqa.model.Role;
import br.edu.figuriqa.repository.StickerRepository;
import br.edu.figuriqa.repository.TeamRepository;
import br.edu.figuriqa.repository.UserRepository;
import br.edu.figuriqa.service.AuthService;
import br.edu.figuriqa.service.CollectionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class FiguriqaApplicationTests {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TeamRepository teamRepository;
    @Autowired
    private StickerRepository stickerRepository;
    @Autowired
    private AuthService authService;
    @Autowired
    private CollectionService collectionService;

    @Test
    void seedCreatesCoreCatalogAndDemoUsers() {
        assertThat(userRepository.findByUsername("ana")).isPresent();
        assertThat(userRepository.findByUsername("admin")).hasValueSatisfying(user -> assertThat(user.getRole()).isEqualTo(Role.ADMIN));
        assertThat(teamRepository.count()).isEqualTo(8);
        assertThat(stickerRepository.count()).isEqualTo(96);
    }

    @Test
    void authenticatesDemoCollector() {
        assertThat(authService.authenticate("ana", "123456")).isPresent();
        assertThat(authService.authenticate("ana", "senha-errada")).isEmpty();
    }

    @Test
    void collectionStatsHaveBasicTotals() {
        var ana = userRepository.findByUsername("ana").orElseThrow();
        var stats = collectionService.stats(ana);
        assertThat(stats.totalStickers()).isEqualTo(96);
        assertThat(stats.unique()).isGreaterThan(0);
        assertThat(stats.missing()).isGreaterThan(0);
    }
}
