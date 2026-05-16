package br.edu.figuriqa.repository;

import br.edu.figuriqa.model.Sticker;
import br.edu.figuriqa.model.User;
import br.edu.figuriqa.model.UserSticker;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserStickerRepository extends JpaRepository<UserSticker, Long> {
    List<UserSticker> findByUser(User user);

    Optional<UserSticker> findByUserAndSticker(User user, Sticker sticker);

    void deleteByUser(User user);

    void deleteBySticker(Sticker sticker);
}
