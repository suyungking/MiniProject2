package org.mini.board;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Board {
	
	private Integer boardNumber;
	private String boardTitle;
	private String boardContent;
	private String boardWriter;
	private String boardRegisterTime;
	private String boardPasswd;
	private String boardDelete;
	private String viewCount;
	private String boardTypeId;

}
