package com.board_study.board.controller;

import com.board_study.board.model.BoardRequest;
import com.board_study.board.model.BoardResponse;
import com.board_study.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards")
public class BoardController {

    private final BoardService boardService;

    // 글 전체 조회
    @GetMapping
    public ResponseEntity<List<BoardResponse>> getAllBoards() {
        List<BoardResponse> boardResponse = boardService.getAllBoards();
        return new ResponseEntity<>(boardResponse, HttpStatus.OK);
    }

    // 글 생성
    @PostMapping
    public ResponseEntity<BoardResponse> createBoard(@RequestBody BoardRequest boardRequest) {
        BoardResponse boardResponse = boardService.createBoard(boardRequest);
        return new ResponseEntity<>(boardResponse, HttpStatus.CREATED);
    }

    // 글 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<BoardResponse> getBoard(@PathVariable Long id) {
        BoardResponse boardResponse = boardService.getBoard(id);
        return new ResponseEntity<>(boardResponse, HttpStatus.OK);
    }

    // 글 수정
    @PutMapping("/{id}")
    public ResponseEntity<BoardResponse> updateBoard(@PathVariable Long id, @RequestBody BoardRequest boardRequest) {
        BoardResponse boardResponse = boardService.updateBoard(id, boardRequest);
        return new ResponseEntity<>(boardResponse, HttpStatus.OK);
    }

    // 글 삭제 (deleteYn을 "Y"로 변경)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id, @RequestBody BoardRequest boardRequest) {
        boardService.deleteBoard(id, boardRequest.password());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
