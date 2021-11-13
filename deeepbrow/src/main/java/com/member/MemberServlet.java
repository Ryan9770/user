package com.member;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.util.MyServlet;
import com.util.MyUtil;

@WebServlet("/member/*")
public class MemberServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri = req.getRequestURI();
		
		if (uri.indexOf("login.do") != -1) {
			loginForm(req, resp);
		} else if (uri.indexOf("login_ok.do") != -1) {
			loginSubmit(req, resp);
		} else if (uri.indexOf("logout.do") != -1) {
			logout(req, resp);
		} else if (uri.indexOf("member.do") != -1) {
			memberForm(req, resp);
		} else if (uri.indexOf("member_ok.do") != -1) {
			memberSubmit(req, resp);
		} else if (uri.indexOf("pwd.do") != -1) {
			pwdForm(req, resp);
		} else if (uri.indexOf("pwd_ok.do") != -1) {
			pwdSubmit(req, resp);
		} else if (uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req, resp);
		} else if (uri.indexOf("userIdCheck.do") != -1) {
			userIdCheck(req, resp);
		} else if (uri.indexOf("list.do") != -1) {
			list(req, resp);
		} else if (uri.indexOf("delete.do") != -1) {
			deleteMember(req, resp);
		}
	}
	private void loginForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = "/WEB-INF/views/member/login.jsp";
		forward(req, resp, path);
		
	}
	
	private void loginSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		
		MemberDAO dao = new MemberDAO();
		String cp = req.getContextPath();
		
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/");
			return;
		}
		
		String mId = req.getParameter("mId");
		String mPassword = req.getParameter("mPassword");
		
		MemberDTO dto = dao.loginMember(mId, mPassword);
		if(dto != null) {
			
			session.setMaxInactiveInterval(20 * 60);
			
			
			SessionInfo info = new SessionInfo();
			info.setUserId(dto.getmId());
			info.setUserName(dto.getmName());
			
			
			session.setAttribute("member", info);
			
			
			resp.sendRedirect(cp + "/");
			return;
		}
		
		
		String msg = "아이디 또는 패스워드가 일치하지 않습니다.";
		req.setAttribute("message", msg);

		forward(req, resp, "/WEB-INF/views/member/login.jsp");
		
	}
	
	private void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		String cp = req.getContextPath();

		
		session.removeAttribute("member");

		
		session.invalidate();

		
		resp.sendRedirect(cp + "/");
	}
	
	
	private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		MemberDAO dao = new MemberDAO();
		MyUtil util = new MyUtil();
		
		String cp = req.getContextPath();
		
		try {
			String page = req.getParameter("page");
			int current_page = 1;
			if(page != null) {
				current_page = Integer.parseInt(page);
			}
			
			// 검색
			String condition = req.getParameter("condition");
			String keyword = req.getParameter("keyword");
			if(condition == null) {
				condition = "mId";
				keyword="";
			}
			
			if(req.getMethod().equalsIgnoreCase("GET")) {
				keyword = URLDecoder.decode(keyword, "utf-8");
			}
			
			// 전체 데이터 개수
			int dataCount;
			if (keyword.length() == 0) {
				dataCount = dao.dataCount();
			} else {
				dataCount = dao.dataCount(condition, keyword);
			}
			
			// 전체 페이지 수
			int rows = 11;
			int total_page = util.pageCount(rows, dataCount);
			if(current_page > total_page) {
				current_page = total_page;
			}
			
			int start = (current_page - 1) * rows + 1;
			int end = current_page * rows;
			
			// 게시물 가져오기
			List<MemberDTO> list = null;
			if (keyword.length() == 0) {
				list = dao.listMember(start, end);
			} else {
				list = dao.listMember(start, end, condition, keyword);
			}
			
			// 리스트 글번호
			int listNum, n = 0;
			for (MemberDTO dto : list) {
				listNum = dataCount = (start + n -1);
				dto.setListNum(listNum);
				n++;
			}
			
			String query = "";
			if(keyword.length() != 0) {
				query = "condition=" + condition + "&keyword=" + URLEncoder.encode(keyword, "utf-8");
			}
			
			// 페이징
			String listUrl = cp + "/member/list.do";
			if(query.length() != 0) {
				listUrl += "?" + query;
				
			}
			
			String paging = util.paging(current_page, total_page, listUrl);
			
			// 포워딩할 jsp에 전달할 속성
			req.setAttribute("list", list);
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("paging", paging);
			req.setAttribute("condition", condition);
			req.setAttribute("keyword", keyword);

			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		forward(req, resp, "/WEB-INF/views/member/list.jsp");
	
	}
	
	private void memberForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		req.setAttribute("title", "회원 가입");
		req.setAttribute("mode", "member");
		
		forward(req, resp, "/WEB-INF/views/member/member.jsp");
		
	}
	
	private void memberSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		MemberDAO dao = new MemberDAO();

		String cp = req.getContextPath();
		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/");
			return;
		}

		String message = "";
		try {
			MemberDTO dto = new MemberDTO();
			dto.setmId(req.getParameter("mId"));
			dto.setmPassword(req.getParameter("mPassword"));
			dto.setmName(req.getParameter("mName"));

			String mBirth = req.getParameter("mBirth").replaceAll("(\\.|\\-|\\/)", "");
			dto.setmBirth(mBirth);

			String mEmail1 = req.getParameter("mEmail1");
			String mEmail2 = req.getParameter("mEmail2");
			dto.setmEmail(mEmail1 + "@" + mEmail2);

			String mTel1 = req.getParameter("mTel1");
			String mTel2 = req.getParameter("mTel2");
			String mTel3 = req.getParameter("mTel3");
			dto.setmTel(mTel1 + "-" + mTel2 + "-" + mTel3);

			dto.setmZipcode(req.getParameter("mZipcode"));
			dto.setmAddr1(req.getParameter("mAddr1"));
			dto.setmAddr2(req.getParameter("mAddr2"));

			dao.insertMember(dto);
			resp.sendRedirect(cp + "/");
			return;
		} catch (SQLException e) {
			if (e.getErrorCode() == 1)
				message = "아이디 중복으로 회원 가입이 실패 했습니다.";
			else if (e.getErrorCode() == 1400)
				message = "필수 사항을 입력하지 않았습니다.";
			else if (e.getErrorCode() == 1840 || e.getErrorCode() == 1861)
				message = "날짜 형식이 일치하지 않습니다.";
			else
				message = "회원 가입이 실패 했습니다.";
			
		} catch (Exception e) {
			message = "회원 가입이 실패 했습니다.";
			e.printStackTrace();
		}

		req.setAttribute("title", "회원 가입");
		req.setAttribute("mode", "member");
		req.setAttribute("message", message);
		forward(req, resp, "/WEB-INF/views/member/member.jsp");
	}
	
	private void pwdForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		if (info == null) {
			
			resp.sendRedirect(cp + "/member/login.do");
			return;
		}

		String mode = req.getParameter("mode");
		if (mode.equals("update")) {
			req.setAttribute("title", "회원 정보 수정");
		} else {
			req.setAttribute("title", "회원 탈퇴");
		}
		req.setAttribute("mode", mode);

		forward(req, resp, "/WEB-INF/views/member/pwd.jsp");
	}
	
	private void pwdSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		MemberDAO dao = new MemberDAO();
		HttpSession session = req.getSession();
		
		String cp = req.getContextPath();

		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/");
			return;
		}

		try {
			SessionInfo info = (SessionInfo) session.getAttribute("member");
			if (info == null) { 
				resp.sendRedirect(cp + "/member/login.do");
				return;
			}

			
			MemberDTO dto = dao.readMember(info.getUserId());
			if (dto == null) {
				session.invalidate();
				resp.sendRedirect(cp + "/");
				return;
			}

			String mPassword = req.getParameter("mPassword");
			String mode = req.getParameter("mode");
			if (!dto.getmPassword().equals(mPassword)) {
				if (mode.equals("update")) {
					req.setAttribute("title", "회원 정보 수정");
				} else {
					req.setAttribute("title", "회원 탈퇴");
				}

				req.setAttribute("mode", mode);
				req.setAttribute("message", "패스워드가 일치하지 않습니다.");
				forward(req, resp, "/WEB-INF/views/member/pwd.jsp");
				return;
			}

			if (mode.equals("delete")) {
				// 회원탈퇴
				dao.deleteMember(info.getUserId());

				session.removeAttribute("member");
				session.invalidate();

				resp.sendRedirect(cp + "/");
				return;
			}

			// 회원정보수정 - 회원수정폼으로 이동
			req.setAttribute("title", "회원 정보 수정");
			req.setAttribute("dto", dto);
			req.setAttribute("mode", "update");
			forward(req, resp, "/WEB-INF/views/member/member.jsp");
			return;

		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/");
	}
	
	private void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		MemberDAO dao = new MemberDAO();
		HttpSession session = req.getSession();

		String cp = req.getContextPath();
		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/");
			return;
		}

		try {
			SessionInfo info = (SessionInfo) session.getAttribute("member");
			if (info == null) { 
				resp.sendRedirect(cp + "/member/login.do");
				return;
			}

			MemberDTO dto = new MemberDTO();

			dto.setmId(req.getParameter("mId"));
			dto.setmPassword(req.getParameter("mPassword"));
			dto.setmName(req.getParameter("mName"));

			String mBirth = req.getParameter("mBirth").replaceAll("(\\.|\\-|\\/)", "");
			dto.setmBirth(mBirth);

			String mEmail1 = req.getParameter("mEmail1");
			String mEmail2 = req.getParameter("mEmail2");
			dto.setmEmail(mEmail1 + "@" + mEmail2);

			String mTel1 = req.getParameter("mTel1");
			String mTel2 = req.getParameter("mTel2");
			String mTel3 = req.getParameter("mTel3");
			dto.setmTel(mTel1 + "-" + mTel2 + "-" + mTel3);

			dto.setmZipcode(req.getParameter("mZipcode"));
			dto.setmAddr1(req.getParameter("mAddr1"));
			dto.setmAddr2(req.getParameter("mAddr2"));

			dao.updateMember(dto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		resp.sendRedirect(cp + "/");
	}
	
	private void userIdCheck(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		MemberDAO dao = new MemberDAO();
		
		String mId = req.getParameter("mId");
		MemberDTO dto = dao.readMember(mId);
		
		String passed ="false";
		if(dto == null) {
			passed = "true";
		}
		
		JSONObject job = new JSONObject();
		job.put("passed", passed);
		
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		out.print(job.toString());
		
	}
	
	private void deleteMember(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
			      MemberDAO dao = new MemberDAO();
			      
			      String cp = req.getContextPath();
			      
			      
			      try {
			         String mId = req.getParameter("mId");
			         dao.deleteMember(mId);
			      } catch (Exception e) {
			         e.printStackTrace();
			      }
			      
			      resp.sendRedirect(cp + "/member/list.do");
			   }
			   
	
}
