package br.edu.figuriqa.service;

import br.edu.figuriqa.model.User;

public record RankingEntry(User user, CollectionStats stats) {
}
