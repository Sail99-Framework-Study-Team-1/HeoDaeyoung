package com.board_study.board.service;

import com.board_study.board.entity.Board;
import com.board_study.board.model.BoardRequest;
import com.board_study.board.model.BoardResponse;
import com.board_study.board.repository.BoardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class BoardServiceTest {

	@Mock
	BoardRepository boardRepository;

	@InjectMocks
	BoardService boardService;

	Board testBoard;

	@BeforeEach
	void setUp (){
		BoardRequest boardRequest = new BoardRequest("Saru", "1234", "제목111", "내용111");
		testBoard = spy(boardRequest.toEntity());
	}

	@Test
	@DisplayName("전체 게시글 조회")
	void findAllBoardTest() {
	
	    // given
		List<BoardRequest> requests = new ArrayList<>();
		BoardRequest boardRequest1 = new BoardRequest("Saru", "1234", "제목111", "내용111");
		BoardRequest boardRequest2 = new BoardRequest("Saru", "1234", "제목222", "내용222");
	    requests.add(boardRequest1);
	    requests.add(boardRequest2);

		List<Board> boardList = requests.stream()
				.map(BoardRequest::toEntity)
				.collect(Collectors.toList());

		given(boardRepository.findAll()).willReturn(boardList);

	    // when
	    List<BoardResponse> boardResponseList = boardService.getAllBoards();

	    // then
		assertThat(boardResponseList.size()).isEqualTo(2);
		assertThat(boardResponseList.get(0).title()).isEqualTo("제목111");
		assertThat(boardResponseList.get(1).title()).isEqualTo("제목222");
		then(boardRepository).should(times(1)).findAll();
	}


	@Test
	@DisplayName("게시글 단건 조회 및 조회수 증가 테스트")
	void getBoardTest() {

	    // given
		given(boardRepository.findById(anyLong())).willReturn(Optional.of(testBoard));

	    // when
		BoardResponse boardResponse = boardService.getBoard(1L);

	    // then
		assertThat(boardResponse.title()).isEqualTo("제목111");
		assertThat(boardResponse.viewCount()).isEqualTo(1L);
		then(boardRepository).should(times(1)).findById(1L);
		then(testBoard).should(times(1)).increaseViewCount();
	}

	@Test
	@DisplayName("게시글 수정 테스트(비번 검증O)")
	void updateBoardWithCorrectPasswordTest() {

		given(boardRepository.findById(anyLong())).willReturn(Optional.of(testBoard));
		String correctPassword = "1234";
		BoardRequest updateRequest = new BoardRequest("Saru", correctPassword, "제목222", "내용222");
		
		// when
		BoardResponse boardResponse = boardService.updateBoard(1L, updateRequest);

	    // then
		assertThat(boardResponse.title()).isEqualTo("제목222");
		then(boardRepository).should(times(1)).findById(1L);
	}

	@Test
	@DisplayName("게시글 수정 테스트(비번 검증X)")
	void updateBoardWithWrongPasswordTest() {

		given(boardRepository.findById(anyLong())).willReturn(Optional.of(testBoard));
		String wrongPassword = "틀렸지";
		BoardRequest updateRequest = new BoardRequest("Saru", wrongPassword, "제목222", "내용222");
		
		// when & then
		assertThatThrownBy(() -> boardService.updateBoard(1L, updateRequest))
					.isInstanceOf(RuntimeException.class)
					.hasMessageContaining("비밀번호가 틀립니다.");
		then(boardRepository).should(times(1)).findById(1L);
	}

	@Test
	@DisplayName("게시글 삭제 테스트 - 성공, 게시물 존재X")
	void deleteTest() {

		given(boardRepository.findById(1L)).willReturn(Optional.of(testBoard)); // 존재하는 게시글
		given(boardRepository.findById(2L)).willReturn(Optional.empty()); // 존재하지 않는 게시글

	    // when
		boardService.deleteBoard(1L, "1234");

	    // then
		assertThatThrownBy(() -> boardService.deleteBoard(2L, "1234"))
						.isInstanceOf(RuntimeException.class)
						.hasMessageContaining("게시글이 존재하지 않습니다.");
		assertThat(testBoard.getDeleteYn()).isEqualTo("Y");
		then(boardRepository).should(times(1)).findById(1L);
	}

	@Test
	@DisplayName("게시글 삭제 테스트 - 비밀번호X")
	void deleteWithWrongPasswordTest() {

		given(boardRepository.findById(anyLong())).willReturn(Optional.of(testBoard));

		// when
		assertThatThrownBy(() -> boardService.deleteBoard(1L, "2234"))
				.isInstanceOf(RuntimeException.class)
				.hasMessageContaining("비밀번호가 틀립니다.");

		// then
		then(boardRepository).should(times(1)).findById(1L);

	}
}
