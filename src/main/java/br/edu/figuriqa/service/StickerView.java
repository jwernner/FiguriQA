package br.edu.figuriqa.service;

import br.edu.figuriqa.model.Sticker;

public record StickerView(Sticker sticker, int quantity, String status) {
}
