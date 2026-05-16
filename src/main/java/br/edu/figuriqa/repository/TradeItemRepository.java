package br.edu.figuriqa.repository;

import br.edu.figuriqa.model.Trade;
import br.edu.figuriqa.model.TradeItem;
import br.edu.figuriqa.model.Sticker;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeItemRepository extends JpaRepository<TradeItem, Long> {
    List<TradeItem> findByTrade(Trade trade);

    void deleteBySticker(Sticker sticker);
}
