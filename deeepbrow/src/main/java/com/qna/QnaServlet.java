package com.qna;

import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.util.MyUploadServlet;


@WebServlet("/qna/*")
public class QnaServlet extends MyUploadServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf-8");
		
		String uri = req.getRequestURI();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");

		if (uri.indexOf("list.do") != -1) {
			list(req, resp);
		} else if(uri.indexOf("write.do")!=-1) {
			if(info==null) {
				forward(req, resp, "/WEB-INF/views/member/login.jsp");
			}
			writeForm(req, resp);
		}
	}
	
	private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// qna 리스트

		forward(req, resp, "/WEB-INF/views/qna/list.jsp");
	}
	private void writeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}	
	
}
