package com.order;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.MemberDAO;
import com.member.MemberDTO;
import com.member.SessionInfo;
import com.product.ProductDAO;
import com.product.ProductDTO;
import com.qna.QnaDAO;
import com.util.MyServlet;

@WebServlet("/buy/*")
public class BuyServlet extends MyServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri = req.getRequestURI();
		
		if(uri.indexOf("buymain.do") != -1) {
			buyMain(req, resp);
		} else if(uri.indexOf("buy_ok.do") != -1) {
			buySubmit(req, resp);
		}
		
	}
	
	protected void buyMain(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// buymain.jsp
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		ProductDAO dao = new ProductDAO();
		MemberDAO dao2 = new MemberDAO();
		BuyDAO dao3 = new BuyDAO();
		
		String cp = req.getContextPath();
		String page = req.getParameter("page");
		String quantity = req.getParameter("quantity");
		String price = req.getParameter("price");
		
		String query = "page=" + page;
		
		
		
		try {
			
			int pNum = Integer.parseInt(req.getParameter("pNo"));
			ProductDTO dto = dao.readProduct(pNum);
			// ProductDTO dto2 = dao.readProductImage(pNum);
			MemberDTO dto2 = dao2.readMember(info.getUserId());
			BuyDTO dto3 = dao3.readImage(pNum);
			
			if(dto == null) {
				resp.sendRedirect(cp + "/buy/buymain.do?" + query);
				return;
			}
			
			req.setAttribute("dto", dto);
			req.setAttribute("dto2", dto2);
			req.setAttribute("pNo", pNum);
			req.setAttribute("quantity", quantity);
			req.setAttribute("price", price);
			req.setAttribute("dto3", dto3);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		forward(req, resp, "/WEB-INF/views/buy/buymain.jsp");

	}
	
	protected void buySubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 결제 완료
		BuyDAO dao = new BuyDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp + "/main.do");
			return;
		}
		
		try {
			
			BuyDTO dto = new BuyDTO();
			
			dto.setMid(info.getUserId());
			dto.setDname(info.getUserName());
			

			dto.setDzipcode(req.getParameter("zip"));
			dto.setDaddr1(req.getParameter("addr1"));
			dto.setDaddr2(req.getParameter("addr2"));
			dto.setPno(Integer.parseInt(req.getParameter("pNo")));
			
			// 핸드폰 번호 추가해야함
			String firstPhone = req.getParameter("phonNum");
			String phone1 = req.getParameter("phone1");
			String phone2 = req.getParameter("phone2");
			String phone = firstPhone + "-" + phone1 + "-" + phone2;
			dto.setDtel(phone);
			
			// 이메일 추가
//			String email1 = req.getParameter("email1");
//			String email2 = req.getParameter("email2");
//			String email = email1 + "@" + email2;
			
			// 배송 메모 추가
			dto.setDel_memo(req.getParameter("memo"));
			
			dto.setShipping_fee(0);
			dto.setQuantity(Integer.parseInt(req.getParameter("proQuantity")));
			dto.setWhole_price(Integer.parseInt(req.getParameter("wholePrice")));
			dto.setPay_price(Integer.parseInt(req.getParameter("wholePrice")));
			dto.setOdprice(Integer.parseInt(req.getParameter("onePrice")));
			
			dao.insertBuy(dto);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		forward(req, resp, "/WEB-INF/views/buy/buy_ok.jsp");
		
	}
	
	

}
