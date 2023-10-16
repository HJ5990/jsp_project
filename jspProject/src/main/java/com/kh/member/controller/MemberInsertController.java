package com.kh.member.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kh.member.model.service.MemberService;
import com.kh.member.model.vo.Member;

/**
 * Servlet implementation class MemberInsertController
 */
@WebServlet("/insert.me")
public class MemberInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		// 1) 인코딩
		request.setCharacterEncoding("UTF-8");
		
		// 2) 요청시 전달값 추출해서 변수 및 객체 저장
		String userId = request.getParameter("userId"); // "user03"
		String userPwd = request.getParameter("userPwd"); // "pass03"
		String userName = request.getParameter("userName"); // "손흥민"
		String phone = request.getParameter("phone"); // "010-xxxx-xxxx" | ""
		String email = request.getParameter("email"); // "djdjf@jasdjad" | ""
		String address = request.getParameter("address"); // "서울시 송파구" | ""
		String[] interestArr = request.getParameterValues("interest"); // ["운동", "낚시"] | null
		
		// String[] --> String로 변환
		// ["운동", "낚시"] --> "운동, 낚시"
		
		String interest = "";
		if (interestArr != null) {
			interest = String.join(", ", interestArr);
		}
		
		// 선택1. 기본생성자로 생성 후 setter메서드 사용해서 담기
		// 선택2. 매개변수 생성자 이용해서 생성과 동시에 값 담아주기
		Member m = new Member(userId, userPwd, userName, phone, email, address, interest);
		
		// 3) sql요청 => service -> dao => spl
		new MemberService().insertMember(m);
		
	
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
