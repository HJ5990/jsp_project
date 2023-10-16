<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.kh.member.model.vo.Member" %>
 <%
 	String contextPath = request.getContextPath();
 	Member loginUser = (Member)session.getAttribute("loginUser");
 	// 로그인 시도 전 menubar.jsp 로딩시 해당객체 : null
 	// 로그인 성공 후 menubar.jsp 로딩시 해당객체 : 로그인 성공한 회원의 정보
 
 %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style>
    .login-area > *{
        float: right;
    }
    .nav-area{
        background: black;
    }
    .menu{
        display: table-cell;
        width: 150px;
        height: 50px;
    }
    .menu a{
        text-decoration: none;
        color: white;
        font-size: 20px;
        font-weight: bold;
        display: block;
        width: 100%;
        height: 100%;
        line-height: 50px;
        text-align: center;
    }
    .login-area a{
        text-decoration: none;
        color: black;
        font-size: 12px;
        
    }
</style>
</head>
<body>
    <h1 align="center">Welcome HJ World</h1>

    <div class="login-area">
    
        <% if(loginUser == null) {%>
        <!-- case1. 로그인 전-->
        <form action="<%=contextPath %>/login.me" method="POST">
            <table>
                <tr>
                    <th>아이디</th>
                    <td><input type="text" name="userId" required></td>
                </tr>
                <tr>
                    <th>비밀번호</th>
                    <td><input type="password" name="userPwd" required></td>
                </tr>
                <tr>
                    <th colspan="2">
                        <button type="submit">로그인</button>
                        <button type="button" onclick="enrollPage();">회원가입</button>
                    </th>
                </tr>
            </table>
            
            <script>
            	// 회원가입 페이지를 요청
            	function enrollPage() {
					// location.href = "<%=contextPath %>/views/member/memberEnrollForm.jsp";
					// ㄴ 웹 애플리케이션의 디렉토리 구조가 url에 노출되면 보안에 취약
					// 그러므로 단순한 페이지 요청도 servlet 호출해서 servlet 거쳐 갈 것 (즉, url에는 서블릿 맵핑값만 나타나도록)
            		location.href = "<%=contextPath %>/enrollForm.me";
					
            	
            	}
            </script>
            
        </form>
        <% } else { %>


        <!-- case1. 로그인 후-->
        <div>
            <b><%=loginUser.getUserName() %>님</b>의 방문을 환영합니다.<br><br>
            <div align="center">
                <a href="">마이페이지</a>
                <a href="<%=contextPath %>/logout.me">로그아웃</a>
            </div>
        </div>
        <% } %>
        
    </div>

    <br clear="both"><br>

    <div class="nav-area">
        <div class="menu"><a href="">HOME</a></div>
        <div class="menu"><a href="">공지사항</a></div>
        <div class="menu"><a href="">일반게시판</a></div>
        <div class="menu"><a href="">사진게시판</a></div>
    </div>

</body>
</html>