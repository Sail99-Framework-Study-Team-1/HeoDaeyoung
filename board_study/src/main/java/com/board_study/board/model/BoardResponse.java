package com.board_study.board.model;

import com.board_study.board.entity.Board;

import java.time.LocalDateTime;

public record BoardResponse(
    Long id,
    String title,
    String content,
    String author,
    LocalDateTime createdAt,
    LocalDateTime updatedAt,
    String deleteYn,
    Long viewCount
) {
    public static BoardResponse fromEntity(Board board) {
        return new BoardResponse(
            board.getId(),
            board.getTitle(),
            board.getContent(),
            board.getAuthor(),
            board.getCreatedAt(),
            board.getUpdatedAt(),
            board.getDeleteYn(),
            board.getViewCount()
        );
    }
}