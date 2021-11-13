package com.member;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.util.MyServlet;

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
		} else if (uri.indexOf("basket.do") != -1) {
			basket(req, resp);
		}
	}
	
	private void basket(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = "/WEB-INF/views/member/basket.jsp";
		forward(req, resp, path);
		
	}
	
	private void loginForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = "/WEB-INF/views/member/login.jsp";
		forward(req, resp, path);
		
	}
	
	private void loginSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 로그인 처리 
		// 세션객체. 세션 정보는 서버에 저장(로그인 정보, 권한 등을 저장)
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
			// 세션의 유지시간 20분 설정
			session.setMaxInactiveInterval(20 * 60);
			
			// 세션에 저장할 내용
			SessionInfo info = new SessionInfo();
			info.setUserId(dto.getmId());
			info.setUserName(dto.getmName());
			
			// 세션에 member 라는 이름으로 저장
			session.setAttribute("member", info);
			
			// 메인화면으로 리다이렉트
			resp.sendRedirect(cp + "/");
			return;
		}
		
		// 로그인 실패인 경우(다시 로그인 폼으로)
		String msg = "아이디 또는 패스워드가 일치하지 않습니다.";
		req.setAttribute("message", msg);

		forward(req, resp, "/WEB-INF/views/member/login.jsp");
		
	}
	
	private void logout(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 로그아웃
		HttpSession session = req.getSession();
		String cp = req.getContextPath();

		// 세션에 저장된 정보를 지운다.
		session.removeAttribute("member");

		// 세션에 저장된 모든 정보를 지우고 세션을 초기화 한다.
		session.invalidate();

		// 루트로 리다이렉트
		resp.sendRedirect(cp + "/");
	}
	
	
	private void memberForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 회원가입 폼
		req.setAttribute("title", "회원 가입");
		req.setAttribute("mode", "member");
		
		forward(req, resp, "/WEB-INF/views/member/member.jsp");
		
	}
	
	private void memberSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 회원가입 처리
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
		// 패스워드 확인 폼
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		String cp = req.getContextPath();
		if (info == null) {
			// 로그 아웃 상태이면
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
		// 패스워드 확인
		MemberDAO dao = new MemberDAO();
		HttpSession session = req.getSession();
		
		String cp = req.getContextPath();

		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/");
			return;
		}

		try {
			SessionInfo info = (SessionInfo) session.getAttribute("member");
			if (info == null) { // 로그아웃 된 경우
				resp.sendRedirect(cp + "/member/login.do");
				return;
			}

			// DB에서 해당 회원 정보 가져오기
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
		// 회원정보 수정 완료
		MemberDAO dao = new MemberDAO();
		HttpSession session = req.getSession();

		String cp = req.getContextPath();
		if (req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/");
			return;
		}

		try {
			SessionInfo info = (SessionInfo) session.getAttribute("member");
			if (info == null) { // 로그아웃 된 경우
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
		// 아이디 중복 검사
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
	
}
