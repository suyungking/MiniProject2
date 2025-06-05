package org.mini.member;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDAO {

	public Member getUserId(String userId);
	
	


	public int insert(Member form);

	public int update(Member form);
	public int delete(Member form);

	
	public List<Member> getList(Map<String, Object> map);

	public int getTotalCount(Map<String, Object> map);



	public void setLoginTime(String userId);

	public void fail(Member member);

}
