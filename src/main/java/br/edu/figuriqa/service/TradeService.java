package br.edu.figuriqa.service;

import br.edu.figuriqa.model.Role;
import br.edu.figuriqa.model.Sticker;
import br.edu.figuriqa.model.Trade;
import br.edu.figuriqa.model.TradeDirection;
import br.edu.figuriqa.model.TradeItem;
import br.edu.figuriqa.model.TradeStatus;
import br.edu.figuriqa.model.User;
import br.edu.figuriqa.repository.StickerRepository;
import br.edu.figuriqa.repository.TradeItemRepository;
import br.edu.figuriqa.repository.TradeRepository;
import br.edu.figuriqa.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TradeService {
    private final UserRepository userRepository;
    private final StickerRepository stickerRepository;
    private final TradeRepository tradeRepository;
    private final TradeItemRepository tradeItemRepository;
    private final CollectionService collectionService;

    public TradeService(UserRepository userRepository, StickerRepository stickerRepository, TradeRepository tradeRepository,
                        TradeItemRepository tradeItemRepository, CollectionService collectionService) {
        this.userRepository = userRepository;
        this.stickerRepository = stickerRepository;
        this.tradeRepository = tradeRepository;
        this.tradeItemRepository = tradeItemRepository;
        this.collectionService = collectionService;
    }

    public List<TradeSuggestion> suggestions(User current) {
        List<User> others = userRepository.findByRole(Role.COLECIONADOR).stream()
                .filter(user -> !user.getId().equals(current.getId()))
                .toList();
        List<Sticker> stickers = stickerRepository.findAll();
        List<TradeSuggestion> result = new ArrayList<>();
        for (User other : others) {
            Sticker iGive = stickers.stream()
                    .filter(sticker -> collectionService.quantity(current, sticker) > 1)
                    .filter(sticker -> collectionService.quantity(other, sticker) == 0)
                    .findFirst()
                    .orElse(stickers.get(Math.floorMod(other.getId().intValue() * 17, stickers.size())));
            Sticker iReceive = stickers.stream()
                    .filter(sticker -> collectionService.quantity(other, sticker) > 1)
                    .filter(sticker -> collectionService.quantity(current, sticker) == 0)
                    .findFirst()
                    .orElse(stickers.get(Math.floorMod(other.getId().intValue() * 19, stickers.size())));
            result.add(new TradeSuggestion(other, iGive, iReceive, "Sugestao gerada pelo comparador automatico"));
        }
        return result;
    }

    @Transactional
    public Trade createProposal(User requester, Long receiverId, Long giveStickerId, Long receiveStickerId) {
        Trade trade = new Trade();
        trade.setRequester(requester);
        trade.setReceiver(userRepository.findById(receiverId).orElseThrow());
        trade.setStatus(TradeStatus.PENDENTE);
        trade.setConfirmationCode("TRD-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase());
        trade.setCreatedAt(LocalDateTime.now());
        tradeRepository.save(trade);
        addItem(trade, giveStickerId, TradeDirection.REQUESTER_GIVES);
        addItem(trade, receiveStickerId, TradeDirection.RECEIVER_GIVES);
        return trade;
    }

    private void addItem(Trade trade, Long stickerId, TradeDirection direction) {
        TradeItem item = new TradeItem();
        item.setTrade(trade);
        item.setSticker(stickerRepository.findById(stickerId).orElseThrow());
        item.setDirection(direction);
        item.setQuantity(1);
        tradeItemRepository.save(item);
    }

    public List<Trade> tradesFor(User user) {
        return tradeRepository.findByRequesterOrReceiverOrderByCreatedAtDesc(user, user);
    }

    public List<Trade> dashboardTrades(User user) {
        return tradeRepository.findByRequesterOrReceiverOrderByCreatedAtDesc(user, user).stream()
                .filter(trade -> trade.getStatus() == TradeStatus.PENDENTE || trade.getStatus() == TradeStatus.CANCELADA)
                .limit(5)
                .toList();
    }

    public List<TradeItem> items(Trade trade) {
        return tradeItemRepository.findByTrade(trade);
    }

    @Transactional
    public void accept(Long tradeId) {
        Trade trade = tradeRepository.findById(tradeId).orElseThrow();
        trade.setStatus(TradeStatus.ACEITA);
        tradeRepository.save(trade);
    }

    @Transactional
    public boolean complete(Long tradeId, String code) {
        Trade trade = tradeRepository.findById(tradeId).orElseThrow();
        if (code != null && code.startsWith("TRD-")) {
            trade.setStatus(TradeStatus.CONCLUIDA);
            trade.setCompletedAt(LocalDateTime.now());
            tradeRepository.save(trade);
            return true;
        }
        return false;
    }

    @Transactional
    public void cancel(Long tradeId) {
        Trade trade = tradeRepository.findById(tradeId).orElseThrow();
        trade.setStatus(TradeStatus.CANCELADA);
        tradeRepository.save(trade);
    }
}
