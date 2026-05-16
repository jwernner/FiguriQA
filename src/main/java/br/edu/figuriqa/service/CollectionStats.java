package br.edu.figuriqa.service;

public record CollectionStats(long totalStickers, long unique, long missing, long repeated, double percent) {
}
