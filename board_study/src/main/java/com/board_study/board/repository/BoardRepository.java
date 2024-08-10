package com.board_study.board.repository;

import com.board_study.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
	List<Board> findAllByOrderByCreatedAtDesc();
	Board findFirstByContentContaining(String content);
}