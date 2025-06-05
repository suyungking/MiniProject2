package org.mini.member;

import java.util.HashMap;
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
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class MemberController {

 
	private final MemberService memberService;
	
	
	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody Member info){
		Member member =  memberService.login(info.getUserId(),info.getUserPasswd());
		
		if (member != null) {
			Map<String, Object> result = new HashMap<>();
	        result.put("userid", member.getUserId());
	        result.put("admin", member.getUserAdmin());
	        result.put("locked", member.getUserLocked());
	        result.put("leave", member.getUserDelete());
	        
	        return ResponseEntity.ok(result);
	    } else {
	        return ResponseEntity.status(401).body("아이디 또는 비밀번호가 틀렸습니다.");
	    }
	}
	
	
	@GetMapping("/{userId}")
	public ResponseEntity<Object> getMember(@PathVariable("userId") String userId){
		Member member = memberService.getUserId(userId);
		if(member != null) {
			return ResponseEntity.ok(member);
		}
		else {
			return ResponseEntity.status(404).build();
		}
	}
	
//	@GetMapping("/user/{userId}")
//	public ResponseEntity<?> getSafeInfo(@PathVariable("userId") String id) {
//	    Member member = memberService.getUserId(id);
//	    if (member == null) return ResponseEntity.notFound().build();
//
//	    Map<String, Object> result = new HashMap<>();
//	    result.put("userId", member.getUserId());
//	    return ResponseEntity.ok(result);
//	}

	
	@PostMapping("/check")
	public ResponseEntity<Object> checkUserId(@RequestBody Map<String, String> map){
		Member member = memberService.getUserId(map.get("userId"));
		
		boolean exists = (member !=null);
		
		Map<String, Boolean> result = new HashMap<>();
	    result.put("exists", exists);
	    
	    
	    return ResponseEntity.ok(result);
		
	}
	
	@PostMapping("/insert")
	public ResponseEntity<Object> insert(@RequestBody Member form){
		boolean result = memberService.insert(form);
		
		if(result) {
			return ResponseEntity.ok(result);
		}
		else {
			return ResponseEntity.status(400).body("회원가입실패");
		}		
	}
	
	@PutMapping("/update/{userId}")
	public ResponseEntity<Object> update(@PathVariable("userId") String userId, @RequestBody Member form){
		if(!userId.equals(form.getUserId()) ) {
			return ResponseEntity.status(401).body("회원 수정 실패");
		}
	
		boolean result = memberService.update(form);
		
		if(result) {
			return ResponseEntity.ok(result);
		}
		else {
			return ResponseEntity.status(401).body("회원 수정 실패");
		}
	}
	
	@PutMapping("/delete/{userId}")
	public ResponseEntity<Object> delete(@PathVariable("userId") String userId, @RequestBody Member form){
		if(!userId.equals(form.getUserId()) ) {
			return ResponseEntity.status(401).body("회원 삭제 실패");
		}
	
		boolean result = memberService.delete(form);
		
		if(result) {
			return ResponseEntity.ok(result);
		}
		else {
			return ResponseEntity.status(401).body("회원 수정 실패");
		}
	}
	
	@GetMapping("/list")
	public ResponseEntity<Object> list(
		    @RequestParam(name = "page", defaultValue = "1") int pageNo,
		    @RequestParam(name = "size", defaultValue = "10") int size,
		    @RequestParam(name = "searchValue", defaultValue = "") String searchValue){
		
		PageResponseVO<Member> memberList = memberService.getList(pageNo,size,searchValue);
		return ResponseEntity.ok(memberList);
	}
	
	@PostMapping("/checkUpdate/{userId}")
	public ResponseEntity<Object> checkUpdate(@PathVariable("userId") String userid ,@RequestBody Map<String, String> pass){
		Member member = memberService.getUserId(userid);
		
		String passwd = pass.get("pass");
		
		boolean result = member.getUserPasswd().equals(passwd);
		
		
		return ResponseEntity.ok(result);
		
	}
	
	}


