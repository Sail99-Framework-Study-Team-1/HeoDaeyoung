package com.board_study.board.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	private String content;

	private String author;

	private String password;

	private final String deleteYn = "N";

	private final Long viewCount = 0L;

	@Builder
	public Board(String title, String author, String password, String content) {
		this.title = title;
		this.author = author;
		this.password = password;
		this.content = content;
	}



}
