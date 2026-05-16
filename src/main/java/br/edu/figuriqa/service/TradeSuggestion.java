package br.edu.figuriqa.service;

import br.edu.figuriqa.model.Sticker;
import br.edu.figuriqa.model.User;

public record TradeSuggestion(User receiver, Sticker iGive, Sticker iReceive, String note) {
}
