package org.mini.board;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardDAO {

	
	public List<Board> getList(Map<String, Object> map);

	public int getTotalCount(Map<String, Object> map);


	public Board getBoardNum(String num);

	public int insert(Board form);

	public int update(Board form);

	public int delete(String num);

	public int viewCount(int num);
	

}
