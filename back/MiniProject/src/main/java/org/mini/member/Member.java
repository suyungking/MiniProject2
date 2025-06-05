package org.mini.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Member {
	private Integer userNumber;
	private String userId;
	private String userPasswd;
	private String userName;
	private String userBirthday;
	private String userEmail;
	private String userPhone;
	private String addressNumber;
	private String address;
	private String addressDetail;
	private String gender;
	private String habit;
	private String registerTime;
	private String loginTime;
	private int failCount;
	private String userLocked;
	private String userDelete;
	private String userAdmin;
	
	

}


