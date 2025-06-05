package org.mini.board;

import java.util.Map;

import org.mini.page.PageResponseVO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

private final BoardService boardService;

    
	@GetMapping("/list/{type}")
	public ResponseEntity<Object> list(
			@PathVariable("type") int type,
		    @RequestParam(name = "page", defaultValue = "1") int pageNo,
		    @RequestParam(name = "size", defaultValue = "10") int size,
		    @RequestParam(name = "searchValue", defaultValue = "") String searchValue){
		
		PageResponseVO<Board> boardList = boardService.getList(type,pageNo,size,searchValue);
		return ResponseEntity.ok(boardList);
	}
	
	
	@GetMapping("/{num}")
	public ResponseEntity<Object> getMember(@PathVariable("num") String num){
		Board board = boardService.getBoardNum(num);
		if(board != null) {
			return ResponseEntity.ok(board);
		}
		else {
			return ResponseEntity.status(404).build();
		}
	}
	
	@PostMapping("/insert")
	public ResponseEntity<Object> insert(@RequestBody Board form){
		int boardNumber = boardService.insert(form);
		
		if(boardNumber > 0) {
			return ResponseEntity.ok(Map.of("boardNumber",boardNumber));
		}
		else {
			return ResponseEntity.status(400).body("게시글 등록 실패");
		}		
	}
	
	@PutMapping("/update/{num}")
	public ResponseEntity<Object> update(@PathVariable("num") String num, @RequestBody Board form){
		boolean result = boardService.update(form);
		
		if(result) {
			return ResponseEntity.ok(result);
		}
		else {
			return ResponseEntity.status(401).body("게시글 업데이트 실패");
		}
	}
	
	@PostMapping("/delete/{num}")
	public ResponseEntity<Object> delete(@PathVariable("num") String num ,@RequestBody Map<String, String> pass){
		Board board = boardService.getBoardNum(num);
		String passwd = pass.get("pass");
		
		if (!board.getBoardPasswd().equals(passwd)) {
	        return ResponseEntity.ok(false);
	    }
		
		boolean result = boardService.delete(num);
		
		if(result) {
			return ResponseEntity.ok(result);
		}
		else {
			return ResponseEntity.status(500).body("게시글 삭제 실패");
		}
	}
	
	@PostMapping("/checkUpdate/{num}")
	public ResponseEntity<Object> checkUpdate(@PathVariable("num") String num ,@RequestBody Map<String, String> pass){
		Board board = boardService.getBoardNum(num);
		
		String passwd = pass.get("pass");
		
		
		
		boolean result = board.getBoardPasswd().equals(passwd);
		
		
		return ResponseEntity.ok(result);
		
		
	}
	
	@PostMapping("view/{num}")
	public ResponseEntity<Object> view(@PathVariable("num") int num){
		boardService.viewCount(num);
		return ResponseEntity.ok().build();		
	}
	
}
