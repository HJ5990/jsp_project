package com.kh.board.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.kh.board.model.service.BoardService;
import com.kh.board.model.vo.Board;
import com.kh.common.MyFileRenamePolicy;
import com.kh.common.model.vo.Attachment;
import com.oreilly.servlet.MultipartRequest;

/**
 * Servlet implementation class BoardInsertController
 */
@WebServlet("/insert.bo")
public class BoardInsertController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardInsertController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		// 일반방식이 아닌 multipart/form-data로 전송하는 경우 request로부터 값 추출이 불가
//		String boradTitle = request.getParameter("title");
//		System.out.println(boradTitle);
		
		// enctype이 multipart/form-data로 잘 전송되었을 경우 전반적인 내용이 실행되도록 코드변경이 필요 
		if (ServletFileUpload.isMultipartContent(request)) {   // 잘 전송되었을 경우 true반환
			// 파일 업로드를 위한 라이브러리 : cos.jar(com.oreilly.servlet의 약자)
			// www.servlets.com cos다운 받아 lib에 넣어줌
			
			// 1. 전달되는 파일을 처리할 작업내용 (전달되는 파일의 용량제한, 저장시킬 폴더경로 설정)
			// 1_1) 전달되는 파일의 용량제한(byte단위)
			
			// byte => kbyte => mbyte => gbyte => tbyte ....
			// 10mbyte로 제한
			int maxSize = 1024 * 1024 * 10;
			
			// 1_2) 전달된 파일을 저장시킬 폴더의 경로 알아내기
			// request.getSession().getServletContext().getRealPath("/");   // 이렇게하면 webapp
			String savePath = request.getSession().getServletContext().getRealPath("/resources/board_upfile/");
			
			
			/*
			 * 2. 전달된 파일의 파일명 수정 및 서버에 업로드 작업 (폴더에 저장)
			 * 
			 * >> HttpServletRequest request => MultipartRequest 변환
			 * 
			 * 위 구문 한줄 실행만으로, 넘어온 첨부파일이 해당 폴더에 무조건 업로드 된다.
			 * 단, 업로드시 파일명을 수정해주는 것이 일반적이다. 그래서 파일명을 수정시켜주는 객체를 제시해야한다.
			 * => 같은 파일명이 존재할 경우 덮어씌워질 수 있고, 파일명에 한글/특수문자/띄어쓰기가 포함될 경우 서버에 따라서 문제발생
			 * 
			 * 기본적으로 파일명이 겹치지 않도록 수정작업해주는 객체가 있다.
			 * => DefaultFileRenamePolicy 객체(cos에서 제공)
			 * => 내부적으로 해당 클래스에서 rename()메서드가 실행되면서 파일명 수정된 후 업로드
			 * 
			 * rename(원본파일){
			 * 		기존에 동일한 파일명이 존재할 경우
			 * 		-> 파일명뒤에 카운팅된 숫자를 붙여준다
			 * 		ex) aaa.jpg, aaa1.jpg, aaa2.jpg
			 * 			꽃.jpg, 꽃1.jpg, 꽃2.jpg
			 * 		reutrn 수정파일
			 * }
			 * 한글 또는 특수문자가 들어가면 서버에 따라서 읽지 못할 수 있음
			 * 위와같은 방식이기 때문에 사용하지 않는다.
			 * 
			 * 나만의 방식대로 절대 겹치지 않도록 rename할 수 있는 fileRenamePolicy 클래스 만들기
			 * com.kh.common.MyFileRenamePolicy
			 * 
			 */

			// new MultipartReqest(request, 저장시킬폴더의경로, 용량제한, 인코딩값, 파일명을수정시켜주는객체)로 변환
			MultipartRequest multiRequest = new MultipartRequest(request, savePath, maxSize, "UTF-8", new MyFileRenamePolicy());
			
			// 3. DB에 기록할 데이터를 추출해서 VO에 차곡차곡 담아주자
			// > 카테고리번호, 제목, 내용, 작성회원번호
			// > 첨부파일이 있다면 원본명, 수정명, 저장폴더경로
			
			String category = multiRequest.getParameter("category");
			String boardTitle = multiRequest.getParameter("title");
			String boardContent = multiRequest.getParameter("content");
			String boardWriter = multiRequest.getParameter("userNo");
			
			Board b = new Board();
			b.setCategory(category);
			b.setBoardTitle(boardTitle);
			b.setBoardContent(boardContent);
			b.setBoardWriter(boardWriter);
			
			Attachment at = null;  // null로 초기화 후 넘어온 첨부파일이 있다면 생성
			
			// multiRequest.getOriginalFileName("키") : "키"로 넘어온 첨부파일이 있을경우 원본파일명 | null
			if (multiRequest.getOriginalFileName("upfile") != null) {  
				at = new Attachment();
				at.setOriginName(multiRequest.getOriginalFileName("upfile"));
				at.setChangeName(multiRequest.getFilesystemName("upfile"));
				at.setFilePath("resources/board_upfile/");
			}
			
			// 4. 서비스요청
			int result = new BoardService().insertBoard(b, at);
			
			// 5. 응답뷰 요청
			if (result > 0) { //성공 => 목록페이지 (jsp/list.bo?cpage=1)
				request.getSession().setAttribute("alertMsg", "일반게시글 작성 성공");
				response.sendRedirect(request.getContextPath() + "/list.bo?cpage=1");
				
			} else { // 실패 => 업로드된 파일 삭제해주고 errorPage
				if (at != null) {
					new File(savePath + at.getChangeName()).delete();
				}
				request.setAttribute("errorMsg", "일반게시글 작성 실패");
				request.getRequestDispatcher("views/common/errorPage.jsp").forward(request, response);
			}
			
			
		}

		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
