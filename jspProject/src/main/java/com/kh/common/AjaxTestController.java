package com.kh.common;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.kh.member.model.vo.Member;

/**
 * Servlet implementation class AjaxTestController
 */
@WebServlet("/jqAjax.do")
public class AjaxTestController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxTestController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		
		String name = request.getParameter("name");
		String age = request.getParameter("age");
		
		System.out.println(name + " : " + age);
		
		// 아래의 코드로는 여러개의 값을 응답 할 수 없음 => 해결법 json-simple 라이브러리 (.jar 다운)
		// response.setContentType("text/html; charset=UTF-8");
		// response.getWriter().print(name);
		// response.getWriter().print(age);
		
		/*
		 * JSON(자바스크립트 객체 표기법)
		 * ajax 통신시 데이터 전송에 자주 사용되는 포맷형식 중 하나
		 * 
		 *  > [value, value, value] <= 자바스크립트에서 배열 => JSONArray
		 *  > {key:value, key:value} <= 자바스크립트에서 일반 객체 => JSONObject
		 */
		
		
		// JSONArray 방식
//		JSONArray jArr = new JSONArray();
//		jArr.add(name);
//		jArr.add(age);
//		response.setContentType("text/html; charset=UTF-8");
//		response.getWriter().print(jArr);

		// JSONObject 방식
//		JSONObject jobj = new JSONObject();  //{}
//		jobj.put("name", name);  // {name:김개똥}
//		jobj.put("age", age);  // {name:김개똥, age:10}
//		response.setContentType("text/html; charset=UTF-8");
//		responsArrayList<E>er().print(jobj);
		
		
		
//		// ex. 아래의 배열을 JSON 형식으로 바꾸기
		ArrayList<Member> list = new ArrayList<>();
		list.add(new Member(1, "김개똥", "01011111111"));
		list.add(new Member(2, "최개똥", "01011111111"));
		list.add(new Member(3, "이개똥", "01011111111"));
//		
//		// [{}, {}, {}]
//		JSONArray jArr2 = new JSONArray();
//		for (Member m : list) {
//			JSONObject jobj2 = new JSONObject();
//			jobj2.put("userNo", m.getUserName());
//			jobj2.put("userName", m.getUserName());
//			jobj2.put("phone", m.getPhone());
//			jArr2.add(jobj2);
//		}
//		response.setContentType("text/html; charset=UTF-8");
//		response.getWriter().print(jArr2);
		//위의 코드를 간단하게 하는법 => gson.jar
		
		
		//gson.jar 방식  (자동으로 JSON형식 배열로 변환해줌)
		response.setContentType("text/html; charset=UTF-8");
		new Gson().toJson(list, response.getWriter());
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
