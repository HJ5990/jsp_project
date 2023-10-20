package com.kh.board.model.service;

import static com.kh.common.JDBCTemplate.*;

import java.sql.Connection;
import java.util.ArrayList;

import com.kh.board.model.dao.BoardDao;
import com.kh.board.model.vo.Board;
import com.kh.board.model.vo.Category;
import com.kh.common.model.vo.Attachment;
import com.kh.common.model.vo.PageInfo;

public class BoardService {
	
	public int selectListCount() {
		Connection conn = getConnection();		
		int listCount = new BoardDao().selectListCount(conn);
		close(conn);
		return listCount;
	}
	
	public ArrayList<Board> selectList(PageInfo pi){
		Connection conn = getConnection();		
		ArrayList<Board> list = new BoardDao().selectList(conn, pi);
		close(conn);
		return list;
	}
	
	public ArrayList<Category> selectCategoryList(){
		Connection conn = getConnection();		
		ArrayList<Category> list = new BoardDao().selectCategoryList(conn);
		close(conn);
		return list;
	}
	
	public int insertBoard(Board b, Attachment at) {
		// Board 먼저처리 (부모키를 먼저 insert)
		Connection conn = getConnection();		
		
		// Board insert 처리
		int result1 = new BoardDao().insertBoard(conn, b);
		int result2 = 1;
		
		// Attachment insert 처리
		if (at != null) {
			result2 = new BoardDao().insertAttachment(conn, at);		
		}
		
		if (result1 > 0 && result2 > 0) {
			commit(conn);
		} else {
			rollback(conn);
		}
		return result1 * result2;
	}
	
	

}