package com.basket;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import com.member.SessionInfo;
import com.order.BuyDAO;
import com.order.BuyDTO;

import com.util.MyServlet;

@WebServlet("/basket/*")
public class BasketServlet extends MyServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		req.setCharacterEncoding("utf-8");
		
		String uri = req.getRequestURI();
		
		if (uri.indexOf("basket.do") != -1) {
			basket(req, resp);
		} else if(uri.indexOf("basket_ok.do") != -1) {
			basketSubmit(req, resp);
		} else if(uri.indexOf("delete_ok.do") != -1) {
			delete(req, resp);
		} else if(uri.indexOf("basketDeleteAll.do") != -1) {
			deleteAll(req, resp);
		} 
		/*
		else if(uri.indexOf("basketBuySubmit.do") != -1) {
			basketBuySubmit(req, resp);
		}
		*/
	}
	
	private void basket(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = "/WEB-INF/views/basket/basket.jsp";
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		BasketDAO dao = new BasketDAO();
		
		try {
			
			List<BasketDTO> list = dao.listBasket(info.getUserId());
			if(list == null) {
				String m = "장바구니가 비어있습니다.";
				req.setAttribute("m", m);
				return;
			}
			req.setAttribute("list", list);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		forward(req, resp, path);
	}
	
	private void basketSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BasketDAO dao = new BasketDAO();
		BuyDAO dao3 = new BuyDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		int pNum = Integer.parseInt(req.getParameter("pNo"));
		
		try {
			
			String quantity = req.getParameter("quantity");
			String price = req.getParameter("price");
			String pName = req.getParameter("pName");
			
			BasketDTO dto = new BasketDTO();
			BuyDTO dto3 = dao3.readImage(pNum);
			String img = dto3.getImageFilename();
			
			dto.setUserId(info.getUserId());
			dto.setPname(pName);
			dto.setPrice(Integer.parseInt(price));
			dto.setQuantity(Integer.parseInt(quantity));
			dto.setImg(img);
			dto.setpNum(pNum);
			
			dao.insertBasket(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		forward(req, resp, "/WEB-INF/views/basket/basket_ok.jsp");
		
	}
	

	private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		BasketDAO dao = new BasketDAO();
		
		try {
			
			int pNo = Integer.parseInt(req.getParameter("pNo"));
						
				dao.deleteBasket(pNo);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		forward(req, resp, "/WEB-INF/views/basket/basket.jsp");
		
	}
	
	private void deleteAll(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		BasketDAO dao = new BasketDAO();
		
		try {
			
			dao.deleteAll(info.getUserId());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		forward(req, resp, "/WEB-INF/views/basket/basket.jsp");
		
	}

}
