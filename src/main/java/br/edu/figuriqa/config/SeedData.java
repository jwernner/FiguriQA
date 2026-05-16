package br.edu.figuriqa.config;

import br.edu.figuriqa.model.Role;
import br.edu.figuriqa.model.Sticker;
import br.edu.figuriqa.model.SwapPoint;
import br.edu.figuriqa.model.Team;
import br.edu.figuriqa.model.Trade;
import br.edu.figuriqa.model.TradeDirection;
import br.edu.figuriqa.model.TradeItem;
import br.edu.figuriqa.model.TradeStatus;
import br.edu.figuriqa.model.User;
import br.edu.figuriqa.model.UserSticker;
import br.edu.figuriqa.repository.StickerRepository;
import br.edu.figuriqa.repository.SwapPointRepository;
import br.edu.figuriqa.repository.TeamRepository;
import br.edu.figuriqa.repository.TradeItemRepository;
import br.edu.figuriqa.repository.TradeRepository;
import br.edu.figuriqa.repository.UserRepository;
import br.edu.figuriqa.repository.UserStickerRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SeedData implements CommandLineRunner {
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final StickerRepository stickerRepository;
    private final UserStickerRepository userStickerRepository;
    private final SwapPointRepository swapPointRepository;
    private final TradeRepository tradeRepository;
    private final TradeItemRepository tradeItemRepository;

    public SeedData(UserRepository userRepository, TeamRepository teamRepository, StickerRepository stickerRepository,
                    UserStickerRepository userStickerRepository, SwapPointRepository swapPointRepository,
                    TradeRepository tradeRepository, TradeItemRepository tradeItemRepository) {
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.stickerRepository = stickerRepository;
        this.userStickerRepository = userStickerRepository;
        this.swapPointRepository = swapPointRepository;
        this.tradeRepository = tradeRepository;
        this.tradeItemRepository = tradeItemRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        reset();
    }

    @Transactional
    public void reset() {
        tradeItemRepository.deleteAll();
        tradeRepository.deleteAll();
        userStickerRepository.deleteAll();
        stickerRepository.deleteAll();
        teamRepository.deleteAll();
        swapPointRepository.deleteAll();
        userRepository.deleteAll();

        User ana = user("Ana Ribeiro", "ana", "123456", Role.COLECIONADOR, "Curitiba", "AnaGol");
        User bruno = user("Bruno Lima", "bruno", "123456", Role.COLECIONADOR, "Sao Paulo", "B10");
        User carla = user("Carla Souza", "carla", "123456", Role.COLECIONADOR, "Recife", "Carla <b>Craque</b>");
        User admin = user("Admin FiguriQA", "admin", "admin123", Role.ADMIN, "Campinas", "Admin");
        userRepository.saveAll(List.of(ana, bruno, carla, admin));

        List<Team> teams = List.of(
                team("Aurora FC", "A", "Liga Boreal"),
                team("Monte Azul", "A", "Liga Continental"),
                team("Costa Coral", "B", "Liga Atlantica"),
                team("Vale Verde", "B", "Liga Sulina"),
                team("Estrela Norte", "C", "Liga Boreal"),
                team("Rio Dourado", "C", "Liga Tropical"),
                team("Serra Prata", "D", "Liga Continental"),
                team("Ilha Violeta", "D", "Liga Atlantica")
        );
        teamRepository.saveAll(teams);

        List<Sticker> stickers = new ArrayList<>();
        String[] types = {"Escudo ficticio", "Atleta inventado", "Mascote", "Estadio imaginario"};
        String[] rarities = {"Comum", "Especial", "Brilhante"};
        for (Team team : teams) {
            for (int i = 1; i <= 12; i++) {
                Sticker sticker = new Sticker();
                sticker.setTeam(team);
                sticker.setCode(team.getName().substring(0, 2).toUpperCase().replace(" ", "") + "-" + String.format("%02d", i));
                sticker.setTitle("Figurinha " + i + " de " + team.getName());
                sticker.setType(types[i % types.length]);
                sticker.setRarity(rarities[i % rarities.length]);
                stickers.add(sticker);
            }
        }
        stickerRepository.saveAll(stickers);

        seedCollection(ana, stickers, 0, 2);
        seedCollection(bruno, stickers, 1, 3);
        seedCollection(carla, stickers, 2, 4);

        swapPointRepository.saveAll(List.of(
                point("Centro Comunitario Bola Cheia", "Curitiba", "Rua das Copas, 101", true),
                point("Biblioteca Arena do Saber", "Sao Paulo", "Avenida Album Novo, 55", true),
                point("Praca Encontro dos Colecionadores", "Recife", "Travessa da Figurinha, 8", false)
        ));

        createTrade(ana, bruno, TradeStatus.PENDENTE, stickers.get(3), stickers.get(19), 1);
        createTrade(bruno, carla, TradeStatus.ACEITA, stickers.get(24), stickers.get(41), 2);
        createTrade(carla, ana, TradeStatus.CANCELADA, stickers.get(55), stickers.get(7), 3);
        createTrade(ana, carla, TradeStatus.CONCLUIDA, stickers.get(61), stickers.get(13), 4);
    }

    private void seedCollection(User user, List<Sticker> stickers, int offset, int repeatEvery) {
        for (int i = 0; i < stickers.size(); i++) {
            if ((i + offset) % 5 == 0) {
                continue;
            }
            UserSticker item = new UserSticker();
            item.setUser(user);
            item.setSticker(stickers.get(i));
            item.setQuantity((i + offset) % repeatEvery == 0 ? 3 : 1);
            userStickerRepository.save(item);
        }
    }

    private void createTrade(User requester, User receiver, TradeStatus status, Sticker give, Sticker receive, int daysAgo) {
        Trade trade = new Trade();
        trade.setRequester(requester);
        trade.setReceiver(receiver);
        trade.setStatus(status);
        trade.setConfirmationCode("TRD-SEED" + daysAgo);
        trade.setCreatedAt(LocalDateTime.now().minusDays(daysAgo));
        if (status == TradeStatus.CONCLUIDA) {
            trade.setCompletedAt(LocalDateTime.now().minusDays(daysAgo - 1));
        }
        tradeRepository.save(trade);
        item(trade, give, TradeDirection.REQUESTER_GIVES);
        item(trade, receive, TradeDirection.RECEIVER_GIVES);
    }

    private void item(Trade trade, Sticker sticker, TradeDirection direction) {
        TradeItem item = new TradeItem();
        item.setTrade(trade);
        item.setSticker(sticker);
        item.setDirection(direction);
        item.setQuantity(1);
        tradeItemRepository.save(item);
    }

    private User user(String name, String username, String password, Role role, String city, String nickname) {
        User user = new User();
        user.setName(name);
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        user.setCity(city);
        user.setNickname(nickname);
        return user;
    }

    private Team team(String name, String groupName, String confederation) {
        Team team = new Team();
        team.setName(name);
        team.setGroupName(groupName);
        team.setFictionalConfederation(confederation);
        return team;
    }

    private SwapPoint point(String name, String city, String address, boolean active) {
        SwapPoint point = new SwapPoint();
        point.setName(name);
        point.setCity(city);
        point.setAddress(address);
        point.setActive(active);
        return point;
    }
}
