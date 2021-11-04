package com.qna;

import java.io.File;
import java.io.IOException;


import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.util.MyUploadServlet;


@MultipartConfig
@WebServlet("/qna/*")
public class QnaServlet extends MyUploadServlet {
	private static final long serialVersionUID = 1L;
	
	private String pathname;
	
	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf-8");
		
		String uri = req.getRequestURI();
		String cp = req.getContextPath();

		HttpSession session = req.getSession();

		
		// 파일을 저장할 경로(pathname)
		String root = session.getServletContext().getRealPath("/");
		pathname = root + "uploads" + File.separator + "notice";
		
		if (uri.indexOf("qna_list.do") != -1) {
			qna_list(req, resp);
		} 
	}
	
	private void qna_list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// qna 리스트
		

		// JSP로 포워딩
		forward(req, resp, "/WEB-INF/views/qna/qna_list.jsp");
	}
	
	
}
