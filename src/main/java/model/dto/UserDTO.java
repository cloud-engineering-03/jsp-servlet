package model.dto;

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
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
	private String name;
	private String id;
	private String password;
	private String nickName;
	private ArrayList<PostDTO> postList;
	private ArrayList<CommentDTO> commentList;
}
