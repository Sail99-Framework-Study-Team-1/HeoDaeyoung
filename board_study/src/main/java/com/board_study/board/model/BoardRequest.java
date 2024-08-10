package com.board_study.board.model;

import com.board_study.board.entity.Board;

public record BoardRequest(
    String title,
    String content,
    String author,
    String password
) {
    public Board toEntity() {
        return Board.builder()
                .title(title)
                .content(content)
                .author(author)
                .password(password)
                .build();
    }
}