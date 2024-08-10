package com.board_study.board.service;

import com.board_study.board.entity.Board;
import com.board_study.board.model.BoardRequest;
import com.board_study.board.model.BoardResponse;
import com.board_study.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    // 글 전체 조회
    public List<BoardResponse> getAllBoards() {
        return boardRepository.findAll().stream()
                .filter(board -> "N".equals(board.getDeleteYn()))
                .map(BoardResponse::fromEntity)
                .collect(Collectors.toList());
    }

    // 글 생성
    public BoardResponse createBoard(BoardRequest request) {
        Board board = request.toEntity();
        Board savedBoard = boardRepository.save(board);
        return BoardResponse.fromEntity(savedBoard);
    }

    // 글 단건 조회 및 조회수 증가
    public BoardResponse getBoard(Long id) {
        Optional<Board> optionalBoard = boardRepository.findById(id);

        if (optionalBoard.isPresent()) {
            Board board = optionalBoard.get();
            // 조회수 증가
            board.setViewCount(board.getViewCount() + 1);
            boardRepository.save(board);
            return BoardResponse.fromEntity(board);
        }

        throw new RuntimeException("아이디 없어요 " + id);
    }

    // 글 수정
    public BoardResponse updateBoard(Long id, BoardRequest request) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if (optionalBoard.isPresent()) {
            Board board = optionalBoard.get();
            // 비밀번호 확인
            if (board.getPassword().equals(request.password())) {
                board.setTitle(request.title());
                board.setContent(request.content());
                board.setAuthor(request.author());
                boardRepository.save(board);
                return BoardResponse.fromEntity(board);
            } else {
                throw new RuntimeException("비번틀림.");
            }
        }
        // 예외 처리
        throw new RuntimeException("아이디 없어요: " + id);
    }

    // 글 삭제 (deleteYn을 "Y"로 변경)
    public void deleteBoard(Long id) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if (optionalBoard.isPresent()) {
            Board board = optionalBoard.get();
            board.setDeleteYn("Y");
            boardRepository.save(board);
        } else {
            // 예외 처리
            throw new RuntimeException("아이디 없어요: " + id);
        }
    }
}
