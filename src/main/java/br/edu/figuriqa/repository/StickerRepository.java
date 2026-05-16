package br.edu.figuriqa.repository;

import br.edu.figuriqa.model.Sticker;
import br.edu.figuriqa.model.Team;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StickerRepository extends JpaRepository<Sticker, Long> {
    List<Sticker> findByTeamOrderByCode(Team team);
}
