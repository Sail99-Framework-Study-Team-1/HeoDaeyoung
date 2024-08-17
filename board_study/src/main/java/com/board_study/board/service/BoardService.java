package com.board_study.board.service;

import com.board_study.board.entity.Board;
import com.board_study.board.model.BoardRequest;
import com.board_study.board.model.BoardResponse;
import com.board_study.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    // 글 전체 조회
    @Transactional(readOnly = true)
    public List<BoardResponse> getAllBoards() {
        return boardRepository.findAll().stream()
                .filter(board -> "N".equals(board.getDeleteYn()))
                .map(BoardResponse::fromEntity)
                .collect(Collectors.toList());
    }

    // 글 생성
    @Transactional
    public BoardResponse createBoard(BoardRequest boardRequest) {
        Board board = boardRequest.toEntity();
        Board savedBoard = boardRepository.save(board);
        return BoardResponse.fromEntity(savedBoard);
    }

    // 글 단건 조회 및 조회수 증가
    @Transactional
    public BoardResponse getBoard(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다. " + id));

        // 조회수 증가
        board.increaseViewCount();
        return BoardResponse.fromEntity(board);
    }

    // 글 수정
    @Transactional
    public BoardResponse updateBoard(Long id, BoardRequest boardRequest) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다. " + id));

        validateBoard(board, boardRequest.password());
        board.update(boardRequest.author(), boardRequest.title(), boardRequest.password());

        return BoardResponse.fromEntity(board);
    }

    // 글 삭제 (deleteYn을 "Y"로 변경)
    @Transactional
    public void deleteBoard(Long id, String password) {
        Board board = boardRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다. " + id));

        validateBoard(board, password);
        board.updateDelete();
    }

    // 비밀번호 검증
    private void validateBoard(Board board, String password) {
        if (!board.getPassword().equals(password)) {
            throw new RuntimeException("비밀번호가 틀립니다.");
        }
    }
}
