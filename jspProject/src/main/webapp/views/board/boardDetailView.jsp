<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.kh.board.model.vo.Board, com.kh.common.model.vo.Attachment"%>
<%
	Board b = (Board)request.getAttribute("b");
	Attachment at = (Attachment)request.getAttribute("at");			
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
    .outer{
        background: black;
        color: white;
        width: 1000px;
        height: auto;
        margin: auto;
        margin-top: 50px;
    }
    .outer table{
        border: 1px solid white;
        border-collapse: collapse;
    }
    .outer table tr, .outer > table td{
        border: 1px solid white;
    }
</style>
</head>
<body>
    <%@ include file="../common/menubar.jsp" %>

    <div class="outer">
        <br>
        <h2 align="center">일반게시판 상세보기</h2>
        <br>

        <table id="detail-area" border="1" align="center">
            <tr>
                <th width="70">카테고리</th>
                <td width="70"><%=b.getCategory() %></td>
                <th width="70">제목</th>
                <td width="350"><%=b.getBoardTitle() %></td>
            </tr>
            <tr>
                <th>작성자</th>
                <td><%=b.getBoardWriter() %></td>
                <th>작성일</th>
                <td><%=b.getCreateDate() %></td>
            </tr>
            <tr>
                <th>내용</th>
                <td colspan="3">
                    <p style="height: 200px;"><%=b.getBoardContent() %></p>
                </td>
            </tr>
            <tr>
                <th>첨부파일</th>
                <td colspan="3">
                <%if (at == null){ %>
                    <!-- 첨부파일이 없을 경우-->
                    첨부파일이 없습니다
                <% } else { %>
                    <!-- 첨부파일이 있을 경우-->
                    <a download=<%=at.getOriginName() %> href="<%=contextPath%>/<%=at.getFilePath() + at.getChangeName()%>"><%=at.getOriginName() %></a>
                <%} %>
                </td>
            </tr>
        </table>
        <br>

        <div align="center">
            <a href="<%=contextPath %>/list.bo?cpage=1" class="btn btn-sm btn-secondary">목록가기</a>

			<%if (loginUser != null && loginUser.getUserId().equals(b.getBoardWriter())) {%>
            <!-- 로그인한 사용자와 게시글 작성자가 동일할 경우-->
            <a href="<%=contextPath %>/updateForm.bo?bno=<%=b.getBoardNo() %>" class="btn btn-sm btn-warning">수정하기</a>
            <a href="" class="btn btn-sm btn-danger">삭제하기</a>
            <%} %>
        </div>

        <br>

        <div id="reply-area">
            <table align="center">
                <thead>
                    <tr>
                        <th>댓글작성</th>
                        <%if(loginUser != null) { %>
                        <td>
                            <textarea id="reply-content" cols="50" rows="3"></textarea>
                        </td>
                        <td>
                            <button onclick="insertReply()">댓글등록</button>
                        </td>
                        <%} else { %>
                         <td>
                            <textarea id="reply-content" cols="50" rows="3" readonly>로그인 후 댓글작성 가능합니다</textarea>
                        </td>
                        <td>
                            <button disabled>댓글등록</button>
                        </td>
                        <%} %>
                    </tr>
                </thead>
                <tbody>

                </tbody>
            </table>

            <script>
                window.onload = function(){
                    // 댓글 가져와서 그려주기
                    selectReplyList();
                    setInterval(selectReplyList, 10000);  // 10초마다 로드
                }
                
                function selectReplyList(){
                    $.ajax({
                        url : "rlist.bo",
                        data : {
                            bno : <%=b.getBoardNo()%>
                        },
                        success: function(res){
                        	// 위에서가 아닌 success에서 그려줘야 화면 새로고침안하고 만들어짐 
                            let str = ""
                            
                            for (let reply of res){
                            	console.log(reply)
                            	str += "<tr>"
                            	+"<td>" + reply.replyWriter + "</td>"
                            	+"<td>" + reply.replyContent + "</td>"
                            	+"<td>" + reply.createDate + "</td>"
                            	+"</tr>"
                            }
                        	document.querySelector("#reply-area tbody").innerHTML = str;

                        },
                        error : function(){
                            console.log("댓글목록 조회 중 ajax 통신 실패");
                        }
                    })
                }
                
                function insertReply(){
                	$.ajax({
                		url : "rinsert.bo",
                		data : {
                			content: document.getElementById("reply-content").value,
                            bno: <%=b.getBoardNo()%>
                		},
                        type: "post",
                        success: function(res){
                            if(res > 0){  // 댓글작성 성공
                            	document.getElementById("reply-content").value = "";
                            	selectReplyList();
                            }
                        },
                        error: function(res){
							console.log("댓글 작성 중 ajax 통신 실패")
                        }
                	})
                }


            </script>

        </div>


    </div>

</body>
</html>