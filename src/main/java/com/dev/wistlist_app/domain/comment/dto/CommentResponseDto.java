package com.dev.wistlist_app.domain.comment.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {
	private Long userId;
	private String username;
	private Long commentId;
	private String content;

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class CommentWithReplyResponseDto {
		private Long userId;
		private String username;
		private Long commentId;
		private String content;
		private List<ReplyResponseDto> replies;

	}

	@Getter
	@Builder
	@NoArgsConstructor
	@AllArgsConstructor
	public static class ReplyResponseDto {
		private Long userId;
		private String username;
		private Long replyId;
		private String content;
	}

}
