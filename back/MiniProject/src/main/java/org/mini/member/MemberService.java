package org.mini.member;

import java.util.HashMap;
import java.util.Map;

import org.mini.page.PageResponseVO;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberDAO memberDAO;
	
	public Member login(String userId, String userPasswd) {
		
		Member member = memberDAO.getUserId(userId);
		
		if ("Y".equals(member.getUserLocked())) {
	        return member;
	    }
		
		if ("Y".equals(member.getUserDelete())) {
		    return member; 
		}
		
		boolean result = member.getUserPasswd().equals(userPasswd);
				
		if (result) {
	        member.setFailCount(0);
	        member.setUserLocked("N");
	        memberDAO.fail(member);
	        memberDAO.setLoginTime(userId);
	        return member;
	    } else {
	        int failCount = member.getFailCount() + 1;
	        member.setFailCount(failCount);

	        if (failCount >= 5) {
	            member.setUserLocked("Y");
	        } else {
	            member.setUserLocked("N");
	        }

	        memberDAO.fail(member);
	        return null;
	    }
		
	}

	public Member getUserId(String userId) {
		return memberDAO.getUserId(userId);
	}

	public boolean insert(Member form) {
		int result = memberDAO.insert(form);
		return result > 0;
	}

	public boolean update(Member form) {
		int result = memberDAO.update(form);
		return result > 0;
	}
	
	public boolean delete(Member form) {
		int result = memberDAO.delete(form);
		return result > 0;
	}

	public PageResponseVO<Member> getList(int pageNo,int size,String searchValue) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", (pageNo-1) * size );
		map.put("size", size);
		map.put("searchValue", searchValue);
		
		return new PageResponseVO<Member>(pageNo
				, memberDAO.getList(map)
				, memberDAO.getTotalCount(map)
				, size); 
	}

}
