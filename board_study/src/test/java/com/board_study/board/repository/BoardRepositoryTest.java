package com.board_study.board.repository;

import com.board_study.board.entity.Board;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Transactional
@TestPropertySource(locations = "classpath:application-local-test.yml")
@DisplayName("레파지토리 계층 테스트")
public class BoardRepositoryTest {

	@Autowired
	private BoardRepository boardRepository;

	@Test
	@DisplayName("Repository Not Null 테스트")
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
		System.out.println(result.getCreatedAt());
	}

	@Test
	@DisplayName("게시글 전체 조회 테스트")
	void findAllBoardTest(){

		// given
		final Board board = Board.builder()
				.author("Saru")
				.password("비밀이야")
				.title("테스트 입니다만?")
				.content("오쪼로고요")
				.build();
		final Board result = boardRepository.save(board);

		// when
		final List<Board> boards = boardRepository.findAllByOrderByCreatedAtDesc();
		System.out.println(boards.get(0).getAuthor());
	}
}
