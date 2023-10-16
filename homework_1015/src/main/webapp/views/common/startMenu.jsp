<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<% String contextPath = request.getContextPath(); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h2>HomeWork_1015</h2>
		<div>
		<form action="<%=contextPath %>/result.me" method="POST">
			<table>
                <tr>
                    <th>이름</th>
                    <td><input type="text" name="name" required></td>
                </tr>
                <tr>
                    <th>학번</th>
                    <td><input type="text" name="number" required></td>
                </tr>
                <tr>
                    <th>국어</th>
                    <td><input type="number" name="test1" required></td>
                </tr>
                <tr>
                    <th>수학</th>
                    <td><input type="number" name="test2" required></td>
                </tr>
                <tr>
                    <th>영어</th>
                    <td><input type="number" name="test3" required></td>
                </tr>
                <tr>
                    <th>과학</th>
                    <td><input type="number" name="test4" required></td>
                </tr>
                <tr>
                    <th colspan="2">
                        <button type="submit">제출</button>
                    </th>
                </tr>
                
            </table>
		</form>
	</div>
</body>
</html>