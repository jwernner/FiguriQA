package br.edu.figuriqa.repository;

import br.edu.figuriqa.model.Trade;
import br.edu.figuriqa.model.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TradeRepository extends JpaRepository<Trade, Long> {
    List<Trade> findByRequesterOrReceiverOrderByCreatedAtDesc(User requester, User receiver);
}
