package com.board_study.board.repository;

import com.board_study.board.entity.Board;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@DataJpaTest
@DisplayName("레파지토리 계층 테스트")
@ActiveProfiles("local-test")
public class BoardRepositoryTest {

	private static final Logger log = LoggerFactory.getLogger(BoardRepositoryTest.class);

	@Autowired
	private BoardRepository boardRepository;

	@Test
	@DisplayName("1. Repository Not Null 테스트")
	@Transactional
	void isNotNullTest() {
		assertThat(boardRepository).isNotNull();
	}

	@Test
	@DisplayName("게시글 저장 테스트")
	void saveBoardTest() {

		// given
		final Board board = Board.builder()
				.author("Saru")
				.password("비밀이야")
				.title("테스트 입니다만?")
				.content("오쪼로고요")
				.build();

		// when
		final Board result = boardRepository.save(board);

		// then
		assertThat(result.getId()).isNotNull();
		assertThat(result.getAuthor()).isEqualTo("Saru");
		assertThat(result.getPassword()).isEqualTo("비밀이야");
		assertThat(result.getTitle()).isEqualTo("테스트 입니다만?");
		assertThat(result.getContent()).isEqualTo("오쪼로고요");
		assertThat(result.getCreatedAt()).isNotNull();
		assertThat(result.getUpdatedAt()).isNotNull();
		assertThat(result.getViewCount()).isEqualTo(0);
		assertThat(result.getDeleteYn()).isEqualTo("N");
	}

	@Test
	@DisplayName("게시글 전체 조회 및 등록순 순서 테스트")
	void findAllBoardTest(){

		// given
		final Board board1 = Board.builder()
				.author("Saru")
				.password("1111")
				.title("제목11111")
				.content("내용11111")
				.build();

		final Board board2 = Board.builder()
				.author("Saru")
				.password("2222")
				.title("제목22222")
				.content("제목2222")
				.build();

		final Board board3 = Board.builder()
				.author("Saru")
				.password("3333")
				.title("제목33333")
				.content("제목33333")
				.build();

		boardRepository.save(board1);
		boardRepository.save(board2);
		boardRepository.save(board3);

		// when
		final List<Board> boards = boardRepository.findAllByOrderByCreatedAtDesc();
		assertThat(boards.size()).isEqualTo(3);
		assertThat(boards.get(0).getTitle()).isEqualTo("제목33333");
		assertThat(boards.get(1).getTitle()).isEqualTo("제목22222");
		assertThat(boards.get(2).getTitle()).isEqualTo("제목11111");
		boards.stream().forEach(board -> log.info("내용: {}, 시간: {}", board.getContent(), board.getCreatedAt()));
	}

	@Test
	@DisplayName("선택한 게시글 조회")
	void findBoardByContent() {

		// given
		final Board board1 = Board.builder()
				.author("Saru")
				.password("111")
				.title("111")
				.content("111")
				.build();

		final Board board2 = Board.builder()
				.author("Saru")
				.password("222")
				.title("222")
				.content("222")
				.build();

	    // when
		boardRepository.save(board1);
		boardRepository.save(board2);

		// then
		List<Board> allBoards = boardRepository.findAll();
		allBoards.forEach(board -> log.info("Board ID: {}, Title: {}", board.getId(), board.getTitle()));

		Board findBoardByContent = boardRepository.findFirstByContentContaining("22");
		assertThat(findBoardByContent).isNotNull();
		assertThat(findBoardByContent.getContent()).contains("22");
	}

}
