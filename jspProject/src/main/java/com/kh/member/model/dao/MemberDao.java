package com.kh.member.model.dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import com.kh.common.JDBCTemplate;
import com.kh.member.model.vo.Member;

public class MemberDao {
	
	private Properties prop = new Properties();
	
	public MemberDao() {
		String filepath = MemberDao.class.getResource("/db/sql/member-mapper.xml").getPath();
		try {
			prop.loadFromXML(new FileInputStream(filepath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	
	public Member loginMember(Connection conn, String userId, String userPwd) {
		// select문 => Member객체(한행) 조회 => ResultSet객체
		
		Member m = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String sql = prop.getProperty("loginMember");
		
		try {
			pstmt = conn.prepareStatement(sql); // 미완성 sql문
			pstmt.setString(1, userId);
			pstmt.setString(2, userPwd);
			
			rset = pstmt.executeQuery(); // 조회에대한 결과값이 있다면 한행 반환 | 없으면 아무것도 반환되지 않는다
			
			if(rset.next()) {
				m = new Member(
							rset.getInt("USER_NO"),
							rset.getString("USER_ID"),
							rset.getString("USER_PWD"),
							rset.getString("USER_NAME"),
							rset.getString("PHONE"),
							rset.getString("EMAIL"),
							rset.getString("ADDRESS"),
							rset.getString("INTEREST"),
							rset.getDate("ENROLL_DATE"),
							rset.getDate("MODIFY_DATE"),
							rset.getString("STATUS")	
						);
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return m;
	}
	
	public void insertMember(Connection conn, Member m) {
		// insert문 => 처리된 행수 => 트랜잭션처리
		
		int serult = 0;
		PreparedStatement pstmt = null;
		
		String sql = prop.getProperty("insertMember");
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, m.getUserId());
			pstmt.setString(2, m.getUserPwd());
			pstmt.setString(3, m.getUserName());
			pstmt.setString(4, m.getPhone());
			pstmt.setString(5, m.getEmail());
			pstmt.setString(6, m.getUserId());
			pstmt.setString(7, m.getUserId());
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
}
