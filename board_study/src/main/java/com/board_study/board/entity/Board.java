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

	private String deleteYn = "N";

	private Long viewCount = 0L;

	@Builder
	public Board(String title, String author, String password, String content) {
		this.title = title;
		this.author = author;
		this.password = password;
		this.content = content;
	}

	// 게시물 수정
	public void update(String author, String title, String content) {
		this.author = author;
		this.title = title;
		this.content = content;
	}

	// 조회수 증가
	public void increaseViewCount() {
		this.viewCount += 1;
	}

	// 게시물 삭제
	public void updateDelete() {
		this.deleteYn = "Y";
	}
}
