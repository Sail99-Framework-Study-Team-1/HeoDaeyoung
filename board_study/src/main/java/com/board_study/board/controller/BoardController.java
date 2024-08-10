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
        List<BoardResponse> response = boardService.getAllBoards();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 글 생성
    @PostMapping
    public ResponseEntity<BoardResponse> createBoard(@RequestBody BoardRequest request) {
        BoardResponse response = boardService.createBoard(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // 글 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<BoardResponse> getBoard(@PathVariable Long id) {
        BoardResponse response = boardService.getBoard(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // 글 수정
    @PutMapping("/{id}")
    public ResponseEntity<BoardResponse> updateBoard(@PathVariable Long id, @RequestBody BoardRequest request) {
        try {
            BoardResponse response = boardService.updateBoard(id, request);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    // 글 삭제 (deleteYn을 "Y"로 변경)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
