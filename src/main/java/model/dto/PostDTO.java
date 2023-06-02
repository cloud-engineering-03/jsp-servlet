package model.dto;

import java.sql.Date;
import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
	private int postId;
	private String title;
	private String content;
	private int viewCnt;
	private Date createdAt;
	private String userName;
	private ArrayList<CommentDTO> commentList;
}
