package com.qna;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.util.MyUploadServlet;
import com.util.MyUtil;

@WebServlet("/qna/*")
public class QnaServlet extends MyUploadServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri = req.getRequestURI();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");

		if (uri.indexOf("list.do") != -1) {
			list(req, resp);
		} else if(uri.indexOf("write.do")!=-1) {
			if(info==null) {
				forward(req, resp, "/WEB-INF/views/member/login.jsp");
				return;
			}
			writeForm(req, resp);
		} else if(uri.indexOf("write_ok.do") != -1) {
			writeSubmit(req, resp);
		} else if(uri.indexOf("article.do") != -1) {
			article(req, resp);
		} else if(uri.indexOf("update.do") != -1) {
			updateForm(req, resp);
		} else if(uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req, resp);
		} else if(uri.indexOf("delete.do") != -1) {
			delete(req, resp);
		} else if(uri.indexOf("insertQm") != -1) {
			// qna 답변 추가
			insertQm(req, resp);
		} else if(uri.indexOf("listQm") != -1) {
			// qna 답변 리스트
			listQm(req, resp);
		} else if(uri.indexOf("deleteQm") != -1) {
			// qna 답변 삭제
			deleteQm(req, resp);
		}
	}
	
	private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QnaDAO dao = new QnaDAO();
		MyUtil util = new MyUtil();
		String cp = req.getContextPath();
		
		try {
			String page = req.getParameter("page");
			int current_page = 1;
			if(page!=null) {
				current_page = Integer.parseInt(page);
			}
			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			if (condition == null) {
				condition = "all";
				keyword = "";
			}
			if (req.getMethod().equalsIgnoreCase("GET")) {
				keyword = URLDecoder.decode(keyword, "utf-8");
			}
			
			String numPerPage = req.getParameter("rows");
			int rows = numPerPage == null ? 10 : Integer.parseInt(numPerPage);

			int dataCount, total_page;

			if (keyword.length() != 0) {
				dataCount = dao.dataCount(condition, keyword);
			} else {
				dataCount = dao.dataCount();
			}
			total_page = util.pageCount(rows, dataCount);

			if (current_page > total_page) {
				current_page = total_page;
			}

			int start = (current_page - 1) * rows + 1;
			int end = current_page * rows;

			List<QnaDTO> list;
			if (keyword.length() != 0) {
				list = dao.listQna(start, end, condition, keyword);
			} else {
				list = dao.listQna(start, end);
			}
			
			// 글번호 만들기
			int listNum, n = 0;
			for (QnaDTO dto : list) {
				listNum = dataCount - (start + n - 1);
				dto.setListNum(listNum);
				dto.setqReg_date(dto.getqReg_date().substring(0, 10));
				n++;
			}
			
			String query = "";
			String listUrl;
			String articleUrl;

			listUrl = cp + "/qna/list.do?rows=" + rows;
			articleUrl = cp + "/qna/article.do?page=" + current_page + "&rows=" + rows;
			if (keyword.length() != 0) {
				query = "condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");

				listUrl += "&" + query;
				articleUrl += "&" + query;
			}

			String paging = util.paging(current_page, total_page, listUrl);

			// 포워딩 jsp에 전달할 데이터
			req.setAttribute("list", list);
			req.setAttribute("articleUrl", articleUrl);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("paging", paging);
			req.setAttribute("condition", condition);
			req.setAttribute("keyword", keyword);
			req.setAttribute("rows", rows);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		forward(req, resp, "/WEB-INF/views/qna/list.jsp");
	}
	
	
	private void writeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String rows = req.getParameter("rows");
		req.setAttribute("mode", "write");
		req.setAttribute("rows", rows);
		forward(req, resp, "/WEB-INF/views/qna/write.jsp");
	}
	
	private void writeSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	private void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	private void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	private void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	private void insertQm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	private void listQm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
	private void deleteQm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
	
}
