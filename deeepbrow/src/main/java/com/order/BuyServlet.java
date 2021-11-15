package com.order;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;

import com.basket.BasketDAO;
import com.basket.BasketDTO;
import com.member.MemberDAO;
import com.member.MemberDTO;
import com.member.SessionInfo;
import com.product.ProductDAO;
import com.product.ProductDTO;
import com.util.MyServlet;
import com.util.MyUtil;

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
		} else if(uri.indexOf("buyList.do") != -1) {
			buyList(req, resp);
		} else if(uri.indexOf("delete.do")!= -1) {
			delete(req, resp);
		} else if(uri.indexOf("basketBuy.do")!= -1) {
			basketBuy(req, resp);
		} else if(uri.indexOf("basketSubmit_ok.do")!= -1) {
			basketSubmit(req, resp);
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
	
	protected void basketBuy(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String path = "/WEB-INF/views/buy/basketBuy.jsp";
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		BasketDAO dao = new BasketDAO();
		MemberDAO dao2 = new MemberDAO();
		
		try {
			
			List<BasketDTO> list = dao.listBasket(info.getUserId());
			MemberDTO dto2 = dao2.readMember(info.getUserId());
			if(list == null) {
				String m = "장바구니가 비어있습니다.";
				req.setAttribute("m", m);
				return;
			}
			req.setAttribute("list", list);
			req.setAttribute("dto2", dto2);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		forward(req, resp, path);

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
	
	protected void basketSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 장바구니에서 결제 완료
		BuyDAO dao = new BuyDAO();
		BuyDTO dto = new BuyDTO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo) session.getAttribute("member");
		
		try {
			
			String []s1 = req.getParameterValues("pNo");
			String []s2 = req.getParameterValues("quantity");
			String []s3 = req.getParameterValues("onePrice");
			int totalPrice = Integer.parseInt(req.getParameter("totalPrice"));
			
			dto.setWhole_price(totalPrice);
			dto.setPay_price(totalPrice);
			
			int []n1 = new int[s1.length];
			int []n2 = new int[s2.length];
			int []n3 = new int[s3.length];
			for(int i=0; i<s1.length; i++) {
				n1[i] = Integer.parseInt(s1[i]);
				n2[i] = Integer.parseInt(s2[i]);
				n3[i] = Integer.parseInt(s3[i]);
			}
			dto.setpNums(n1);
			dto.setQuantitys(n2);
			dto.setOnePrices(n3);
			
			dto.setMid(info.getUserId());
			dto.setDname(info.getUserName());
			
			dto.setDzipcode(req.getParameter("zip"));
			dto.setDaddr1(req.getParameter("addr1"));
			dto.setDaddr2(req.getParameter("addr2"));
			
			String firstPhone = req.getParameter("phonNum");
			String phone1 = req.getParameter("phone1");
			String phone2 = req.getParameter("phone2");
			String phone = firstPhone + "-" + phone1 + "-" + phone2;
			dto.setDtel(phone);
			
			dto.setDel_memo(req.getParameter("memo"));
			dto.setShipping_fee(0);
			
			
			dao.insertBuy2(dto);
			
			BasketDAO dao2 = new BasketDAO();
			dao2.deleteAll(info.getUserId());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		forward(req, resp, "/WEB-INF/views/buy/basketSubmit_ok.jsp");
		
	}
	
	protected void buyList(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BuyDAO dao = new BuyDAO();
		MyUtil util = new MyUtil();
		
		String cp = req.getContextPath();
		
		try {
			String page = req.getParameter("page");
			int current_page = 1;
			if(page != null) {
				current_page = Integer.parseInt(page);
			}
			
			int dataCount = dao.dataCount();
			
			int rows = 5;
			int total_page = util.pageCount(rows, dataCount);
			if(current_page > total_page) {
				current_page = total_page;
			}
			
			int start = (current_page - 1) * rows + 1;
			int end = current_page * rows;
			
			List<BuyDTO> list = dao.BuyList(start, end);
			
			
			String listUrl = cp + "/buy/buyList.do";
			String articleUrl = cp + "/buy/buyList.do?page="+current_page;
			
			String paging = util.paging(current_page, total_page, listUrl);
			
			req.setAttribute("list", list);
			req.setAttribute("total_page", total_page);
			req.setAttribute("dataCount", dataCount);
			req.setAttribute("articleUrl", articleUrl);
			req.setAttribute("paging", paging);
	
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		forward(req, resp, "/WEB-INF/views/buy/buyList.jsp");
	}

	private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		BuyDAO dao = new BuyDAO();
		
		String state = "false";
		try {
			int orderNo = Integer.parseInt(req.getParameter("orderNo"));
						
				dao.deleteOrder(orderNo);
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
	

}
