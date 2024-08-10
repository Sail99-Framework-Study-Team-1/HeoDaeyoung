package com.board_study.board.service;

import com.board_study.board.repository.BoardRepository;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BoardServiceTest {

	@Mock
	BoardRepository boardRepository;

	@InjectMocks
	BoardService boardService;

}
