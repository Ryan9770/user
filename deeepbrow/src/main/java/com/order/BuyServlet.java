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
		}
		
	}
	
	protected void buyMain(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// buymain.jsp
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		ProductDAO dao = new ProductDAO();
		MemberDAO dao2 = new MemberDAO();
		
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
			
			if(dto == null) {
				resp.sendRedirect(cp + "/buy/buymain.do?" + query);
				return;
			}
			
			req.setAttribute("dto", dto);
			req.setAttribute("dto2", dto2);
			req.setAttribute("page", page);
			req.setAttribute("query", query);
			req.setAttribute("quantity", quantity);
			req.setAttribute("price", price);
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		forward(req, resp, "/WEB-INF/views/buy/buymain.jsp");

	}

}
