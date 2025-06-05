package org.mini.board;

import java.util.HashMap;
import java.util.Map;

import org.mini.page.PageResponseVO;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BoardService {	
	
	private final BoardDAO boardDAO;

	public PageResponseVO<Board> getList(int type,int pageNo,int size,String searchValue) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", (pageNo-1) * size );
		map.put("size", size);
		map.put("searchValue", searchValue);
		map.put("type", type);
		
		return new PageResponseVO<Board>(pageNo
				, boardDAO.getList(map)
				, boardDAO.getTotalCount(map)
				, size); 
	}

	public Board getBoardNum(String num) {
		return boardDAO.getBoardNum(num);
	}

	public int insert(Board form) {
		boardDAO.insert(form);
		return form.getBoardNumber();
	}

	public boolean update(Board form) {
		int result = boardDAO.update(form);
		return result > 0;
	}

	public boolean delete(String num) {
		int result = boardDAO.delete(num);
		return result > 0;
		
	}

	public int viewCount(int num) {
		return boardDAO.viewCount(num);
		
	}
	
	
}
