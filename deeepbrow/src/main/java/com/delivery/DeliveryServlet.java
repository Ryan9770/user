package com.delivery;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.util.MyServlet;
import com.util.MyUtil;

@WebServlet("/delivery/*")
public class DeliveryServlet extends MyServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String uri = req.getRequestURI();
		
		if(uri.indexOf("list.do") != -1) {
			listForm(req, resp);
		} else if(uri.indexOf("canceled_order.do") != -1) {
			delete(req, resp);
		} else if(uri.indexOf("delivered_order.do") != -1) {
			deliver(req, resp);
		} else if(uri.indexOf("orderDetails.do") != -1) {
			showDetails(req, resp);
		}
	}
	
	protected void listForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		DeliveryDAO dao = new DeliveryDAO();
		MyUtil util = new MyUtil();
		
		String cp = req.getContextPath();

		try {
			String page = req.getParameter("page");
			int current_page = 1;
			if(page != null) {
				current_page = Integer.parseInt(page);
			}
			
			int dataCount = dao.dataCount();
			
			int rows = 12;
			int total_page = util.pageCount(rows, dataCount);
			if(current_page > total_page) {
				current_page = total_page;
			}
			
			int start = (current_page - 1) * rows + 1;
			int end = current_page * rows;
			
			List<DeliveryDTO> list = dao.listDelivery(start, end);
			
			String listUrl = cp + "/delivery/list.do";
			String paging = util.paging(current_page, total_page, listUrl);
			
			req.setAttribute("list", list);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("page", current_page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("paging", paging);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String path = "/WEB-INF/views/delivery/list.jsp";
		forward(req, resp, path);
	}
	
	protected void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		DeliveryDAO dao = new DeliveryDAO();
		
		String cp = req.getContextPath();
		
		
		try {
			
			int orderNo = Integer.parseInt(req.getParameter("orderNo"));
			dao.cancelOrder(orderNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/delivery/list.do");
	}
	
	protected void deliver(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		DeliveryDAO dao = new DeliveryDAO();
		
		String cp = req.getContextPath();
		
		try {
			int orderNo = Integer.parseInt(req.getParameter("orderNo"));
			dao.deliverOrder(orderNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/delivery/list.do");
	}
	
	protected void showDetails(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		DeliveryDAO dao = new DeliveryDAO();
		MyUtil util = new MyUtil();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		try {
			int orderNo = Integer.parseInt(req.getParameter("orderNo"));
			String query = "orderNo=" + orderNo;
			
			DeliveryDTO dto = dao.readOrder(orderNo);
			if(dto == null) {
				resp.sendRedirect(cp + "/delivery/list.do?" + query);
				return;
			}
			
			req.setAttribute("dto", dto);
			
			forward(req, resp, "/WEB-INF/views/delivery/orderDetails.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// resp.sendRedirect(cp + "/delivery/list.do");
	}
}
