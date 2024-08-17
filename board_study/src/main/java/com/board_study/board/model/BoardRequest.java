package com.board_study.board.model;

import com.board_study.board.entity.Board;

public record BoardRequest(
    String author,
    String password,
    String title,
    String content
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