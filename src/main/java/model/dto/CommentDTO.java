package model.dto;

import java.sql.Date;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class CommentDTO {
	private int commentId;
	private String content;
	private Date createdAt;
	private int likeCnt;
	private String userName;
	private int postId;
}
