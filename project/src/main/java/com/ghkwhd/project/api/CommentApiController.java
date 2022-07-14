package com.ghkwhd.project.api;

import com.ghkwhd.project.dto.CommentDto;
import com.ghkwhd.project.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentApiController {

    @Autowired // RestController가 Service와 함께 협업할 수 있도록 선언
    private CommentService commentService;
    
    
    // 댓글 목록 조회
    @GetMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<List<CommentDto>> comments(@PathVariable Long articleId) {

        // Service에게 위임 (일을 시킴)
        List<CommentDto> dtos = commentService.comments(articleId);

        // 결과 응답 (무조건 성공할 것이라고 가정한 코드)
        return ResponseEntity.status(HttpStatus.OK).body(dtos);

    }
    
    // 댓글 생성
    @PostMapping("/api/articles/{articleId}/comments")
    public ResponseEntity<CommentDto> create(@PathVariable Long articleId, @RequestBody CommentDto dto) {

        // Service에게 위임
        CommentDto createDto = commentService.create(articleId, dto);

        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(createDto);
    }

    // 댓글 수정
    @PatchMapping("api/comments/{id}")
    public ResponseEntity<CommentDto> update(@PathVariable Long id, @RequestBody CommentDto dto) {

        // Service에게 위임
        CommentDto updateDto = commentService.update(id, dto);

        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(updateDto);
    }


    // 댓글 삭제
    @DeleteMapping("api/comments/{id}")
    public ResponseEntity<CommentDto> delete(@PathVariable Long id) {

        // Service에게 위임
        CommentDto deleteDto = commentService.delete(id);

        // 결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(deleteDto);
    }

}
