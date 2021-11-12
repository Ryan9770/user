package com.qna;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.member.SessionInfo;
import com.product.ProductDAO;
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
		} else if(uri.indexOf("search") != -1) {
			// 상품선택 모달
			search(req, resp);
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
		req.setAttribute("mode", "write");
		forward(req, resp, "/WEB-INF/views/qna/write.jsp");
	}
	
	private void writeSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QnaDAO dao = new QnaDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();
		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/qna/list.do");
			return;
		}
		
		try {
			QnaDTO dto = new QnaDTO();
			
			dto.setmId(info.getUserId());
			
			dto.setqSubject(req.getParameter("qSubject"));
			dto.setqContent(req.getParameter("qContent"));
			dto.setqCategory(req.getParameter("qCategory"));
			dto.setpNo(Integer.parseInt(req.getParameter("pNo")));
			
			dao.insertQna(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp+"/qna/list.do");
	}
	
	private void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QnaDAO dao = new QnaDAO();
		MyUtil util = new MyUtil();
		
		String cp = req.getContextPath();
		
		String page = req.getParameter("page");
		String rows = req.getParameter("rows");
		String query = "page=" + page+"&rows="+rows;
		
		try {
			int num = Integer.parseInt(req.getParameter("qNo"));
			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			
			if (condition == null) {
				condition = "all";
				keyword = "";
			}
			keyword = URLDecoder.decode(keyword, "utf-8");

			if (keyword.length() != 0) {
				query += "&condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
			}
			
			QnaDTO dto = dao.readQna(num);
			if(dto == null) {
				resp.sendRedirect(cp+"/qna/list.do?"+query);
			}
			dto.setqContent(util.htmlSymbols(dto.getqContent()));
			
			QnaDTO preReadDto = dao.preReadQna(dto.getqNo(), condition, keyword);
			QnaDTO nextReadDto = dao.nextReadQna(dto.getqNo(), condition, keyword);
			
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("query", query);
			req.setAttribute("rows", rows);
			req.setAttribute("preReadDto", preReadDto);
			req.setAttribute("nextReadDto", nextReadDto);
			
			forward(req, resp, "/WEB-INF/views/qna/article.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/qna/list.do?"+query);
	}
	
	private void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QnaDAO dao = new QnaDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();

		String page = req.getParameter("page");
		String rows = req.getParameter("rows");
		
		try {
			int num = Integer.parseInt(req.getParameter("qNo")); 
			QnaDTO dto = dao.readQna(num);
			
			if(dto== null) {
				resp.sendRedirect(cp+"/qna/list.do?page="+page+"&rows="+rows);
				return;
			}
			if(! dto.getmId().equals(info.getUserId())) {
				resp.sendRedirect(cp+"/qna/list.do?page="+page+"&rows="+rows);
				return;
			}
			
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("rows", rows);
			req.setAttribute("mode", "update");
			
			forward(req, resp, "/WEB-INF/views/qna/write.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp+"/qna/list.do?page="+page);

	}
	
	private void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QnaDAO dao = new QnaDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();
		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/qna/list.do");
			return;
		}
		
		String page = req.getParameter("page");
		try {
			QnaDTO dto = new QnaDTO();
			
			dto.setmId(info.getUserId());
			
			dto.setqSubject(req.getParameter("qSubject"));
			dto.setqContent(req.getParameter("qContent"));
			dto.setqCategory(req.getParameter("qCategory"));
			dto.setpNo(Integer.parseInt(req.getParameter("pNo")));
			
			dao.updateQna(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp+"/qna/list.do?page="+page);
	}
	
	private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QnaDAO dao = new QnaDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");

		String cp = req.getContextPath();

		String page = req.getParameter("page");
		String query = "page=" + page;
		
		try {
			int qNo = Integer.parseInt(req.getParameter("qNo"));
			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			if (condition == null) {
				condition = "all";
				keyword = "";
			}
			keyword = URLDecoder.decode(keyword, "utf-8");

			if (keyword.length() != 0) {
				query += "&condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "UTF-8");
			}
			dao.deleteQna(qNo, info.getUserId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/qna/list.do?"+query);
	}
	
	private void insertQm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QnaDAO dao = new QnaDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String state = "false";
		try {
			QnamanageDTO dto = new QnamanageDTO();
			
			int num = Integer.parseInt(req.getParameter("qNo"));
			dto.setqNo(num);
			dto.setmId(info.getUserId());
			dto.setQmContent(req.getParameter("qmContent"));
			
			dao.insertQm(dto);
			state = "true";

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONObject job = new JSONObject();
		job.put("state", state);
		
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		out.print(job.toString());
	}
	
	private void listQm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QnaDAO dao = new QnaDAO ();
		MyUtil util = new MyUtil();
		
		try {
			int qNo = Integer.parseInt(req.getParameter("qNo"));
			String pageNo = req.getParameter("pageNo");
			int current_page = 1;
			if(pageNo != null)
				current_page = Integer.parseInt(pageNo);
				
			int rows = 5;
			int total_page = 0;
			int qmCount = 0;
			
			qmCount = dao.dataCountQm(qNo);
			total_page = util.pageCount(rows, qmCount);
			if (current_page > total_page) {
				current_page = total_page;
			}

			int start = (current_page - 1) * rows + 1;
			int end = current_page * rows;
			
			List<QnamanageDTO> listQm = dao.listQm(qNo, start, end);
			
			for(QnamanageDTO dto : listQm) {
				dto.setQmContent(dto.getQmContent().replaceAll("\n", "<br>"));
			}
			
			String paging = util.pagingMethod(current_page, total_page, "listPage");
			
			req.setAttribute("listQm", listQm);
			req.setAttribute("pageNo", current_page);
			req.setAttribute("qmCount", qmCount);
			req.setAttribute("total_page", total_page);
			req.setAttribute("paging", paging);
			
			forward(req, resp, "/WEB-INF/views/qna/listQm.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendError(405);
	}
	
	private void deleteQm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		QnaDAO dao = new QnaDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		String state = "false";
		
		try {
			int qmNo = Integer.parseInt(req.getParameter("qmNo"));
			dao.deleteQm(qmNo, info.getUserId());
			state = "true";
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		JSONObject job = new JSONObject();
		job.put("state", state);

		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		out.print(job.toString());
	}
	
	private void search(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ProductDAO dao = new ProductDAO();
		String state = "false";
		
		
		JSONObject job = new JSONObject();
		job.put("state", state);

		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		out.print(job.toString());
	}
}
