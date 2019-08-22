package com.woowacourse.zzinbros.comment.controller;

import com.woowacourse.zzinbros.comment.domain.Comment;
import com.woowacourse.zzinbros.comment.dto.CommentRequestDto;
import com.woowacourse.zzinbros.comment.service.CommentService;
import com.woowacourse.zzinbros.post.domain.Post;
import com.woowacourse.zzinbros.post.service.PostService;
import com.woowacourse.zzinbros.user.domain.User;
import com.woowacourse.zzinbros.user.dto.UserResponseDto;
import com.woowacourse.zzinbros.user.service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;
    private final PostService postService;
    private final UserService userService;

    public CommentController(final CommentService commentService, final PostService postService, final UserService userService) {
        this.commentService = commentService;
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping
    public Comment add(@RequestBody final CommentRequestDto dto, final UserResponseDto loginUserDto) {
        final User user = userService.findLoggedInUser(loginUserDto);
        final Post post = postService.read(dto.getPostId());
        final String contents = dto.getContents();
        return commentService.add(user, post, contents);
    }

    @PutMapping
    public Comment edit(@RequestBody final CommentRequestDto dto, final UserResponseDto loginUserDto) {
        final User user = userService.findLoggedInUser(loginUserDto);
        return commentService.update(dto.getCommentId(), dto.getContents(), user);
    }

    @DeleteMapping
    public boolean delete(@RequestBody final CommentRequestDto dto, final UserResponseDto loginUserDto) {
        final User user = userService.findLoggedInUser(loginUserDto);
        try {
            commentService.delete(dto.getCommentId(), user);
            return true;
        } catch (final Exception e) {
            return false;
        }
    }
}
