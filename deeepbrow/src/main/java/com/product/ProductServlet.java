package com.product;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.member.SessionInfo;
import com.util.FileManager;
import com.util.MyUploadServlet;
import com.util.MyUtil;


@MultipartConfig
@WebServlet("/product/*")
public class ProductServlet extends MyUploadServlet {
	private static final long serialVersionUID = 1L;

	private String pathname;

	@Override
	protected void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");

		String uri = req.getRequestURI();
		HttpSession session = req.getSession();
		
		
		// 이미지 저장 경로
		String root = session.getServletContext().getRealPath("/");
		pathname = root + "uploads" + File.separator + "product";
		
		// uri에 따른 작업 구분
		if (uri.indexOf("list.do") != -1) {
			list(req, resp);
		} else if (uri.indexOf("manage.do") != -1) {
			manage(req, resp);
		} else if (uri.indexOf("write.do") != -1) {
			writeForm(req, resp);
		} else if (uri.indexOf("write_ok.do") != -1) {
			writeSubmit(req, resp);
		} else if (uri.indexOf("article.do") != -1) {
			article(req, resp);
		} else if (uri.indexOf("update.do") != -1) {
			updateForm(req, resp);
		} else if (uri.indexOf("update_ok.do") != -1) {
			updateSubmit(req, resp);
		} else if (uri.indexOf("deleteImage") != -1) {
			deleteImage(req, resp);
		} else if (uri.indexOf("delete.do") != -1) {
			delete(req, resp);
		}
	}

	private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 상품 리스트
		ProductDAO dao = new ProductDAO();
		MyUtil util = new MyUtil();
	
				
		String cp = req.getContextPath();
		try {
			String page = req.getParameter("page");
			int current_page = 1;
			if(page != null) {
				current_page = Integer.parseInt(page);
			}
			
			String pCategory_code = req.getParameter("pCategory_code");
			if(pCategory_code==null) {
				pCategory_code="";
			}

			//데이터 개수
			int dataCount;
			if(pCategory_code.length() == 0) {
				dataCount = dao.dataCount();
			} else {
				dataCount = dao.dataCount(pCategory_code);
			}
			
			// 페이지 수
			int rows = 18; // 출력 상품 갯수 조절 가능(오리지날은 18개 출력)
			int total_page = util.pageCount(rows, dataCount);
			if(current_page > total_page) {
				current_page = total_page;
			}
			
			int start = (current_page - 1) * rows + 1;
			int end = current_page * rows;
			
			
			// 게시물
			List<ProductDTO> list = null;
			if(pCategory_code.length() == 0) {
				list = dao.listProduct(start, end);				
			} else {
				list = dao.listProduct(start, end, pCategory_code);
			}
			
			String query = "";
			if(pCategory_code.length() != 0) {
				query = "pCategory_code=" + pCategory_code;
			}
			
			// 페이징
			String listUrl = cp + "/product/list.do";
			String articleUrl = cp + "/product/article.do?page=" + current_page;
			
			if(query.length() != 0) { // 검색어가 존재하면
				listUrl += "?" + query;
				articleUrl += "&" + query;
			}
			
			String paging = util.paging(current_page, total_page, listUrl);
			
			req.setAttribute("list", list);
		
			req.setAttribute("page", page);
			req.setAttribute("total_page", total_page);
			req.setAttribute("articleUrl", articleUrl);
			req.setAttribute("paging", paging);
			req.setAttribute("pCategory_code", pCategory_code);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		forward(req, resp, "/WEB-INF/views/product/list.jsp");
	}
	
	private void manage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 상품 리스트
		ProductDAO dao = new ProductDAO();
		MyUtil util = new MyUtil();
	
				
		String cp = req.getContextPath();
		try {
			ProductDTO vo = new ProductDTO(); 
					
			
			int current_page = 1;
			
			
			String pCategory_code = req.getParameter("pCategory_code");
			if(pCategory_code==null) {
				pCategory_code="";
			}

			//데이터 개수
			int dataCount;
			if(pCategory_code.length() == 0) {
				dataCount = dao.dataCount();
			} else {
				dataCount = dao.dataCount(pCategory_code);
			}
			
			// 페이지 수
			int rows = 18; // 출력 상품 갯수 조절 가능(오리지날은 18개 출력)
			int total_page = util.pageCount(rows, dataCount);
			if(current_page > total_page) {
				current_page = total_page;
			}
			
			int start = (current_page - 1) * rows + 1;
			int end = current_page * rows;
			
			
			// 게시물
			List<ProductDTO> list = null;
			if(pCategory_code.length() == 0) {
				list = dao.listProduct(start, end);				
			} else {
				list = dao.listProduct(start, end, pCategory_code);
			}
			
			String query = "";
			if(pCategory_code.length() != 0) {
				query = "pCategory_code=" + pCategory_code;
			}
			
			// 페이징
			String listUrl = cp + "/product/list.do";
			String articleUrl = cp + "/product/article.do?page=" + current_page;
			
			if(query.length() != 0) { // 검색어가 존재하면
				listUrl += "?" + query;
				articleUrl += "&" + query;
			}
			
			String paging = util.paging(current_page, total_page, listUrl);
			
			req.setAttribute("list", list);
			req.setAttribute("vo", vo);
			
			req.setAttribute("total_page", total_page);
			req.setAttribute("articleUrl", articleUrl);
			req.setAttribute("paging", paging);
			req.setAttribute("pCategory_code", pCategory_code);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		forward(req, resp, "/WEB-INF/views/product/manage.jsp");
	}

	private void writeForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ProductDAO dao = new ProductDAO();
		List<ProductDTO> list = dao.listCategory();
				
		req.setAttribute("list", list);
		req.setAttribute("mode", "write");		
		forward(req, resp, "/WEB-INF/views/product/write.jsp");
	}

	private void writeSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 상품 등록
		ProductDAO dao = new ProductDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		if(req.getMethod().equalsIgnoreCase("GET")) {
			resp.sendRedirect(cp+"/product/list.do");
			return;
		}
		
		if(!info.getUserId().equals("admin")) {
			resp.sendRedirect(cp+"/product/list.do");
			return;
		}
		
		try {
			ProductDTO dto = new ProductDTO();
			
			dto.setpCategory_code(req.getParameter("pCategory_code"));
			dto.setpName(req.getParameter("pName"));
			dto.setpPrice(Integer.parseInt(req.getParameter("pPrice")));
			dto.setpDesc(req.getParameter("pDesc"));
			dto.setpStock(Integer.parseInt(req.getParameter("pStock")));
			dto.setpDate(req.getParameter("pDate"));
			
			
			Map<String, String[]> map = doFileUpload(req.getParts(), pathname);
			if(map != null) {
				String[] saveImages = map.get("saveFilenames");
				dto.setImage_names(saveImages);
			}
			
			dao.insertProduct(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/product/list.do");
		
	}
	
	private void article(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 상품 보기
		ProductDAO dao = new ProductDAO();
		String cp = req.getContextPath();
		String page = req.getParameter("page");
		
		try {
			int pNo = Integer.parseInt(req.getParameter("pNo"));
			
			ProductDTO dto = dao.readProduct(pNo);
			if(dto == null) {
				resp.sendRedirect(cp+"/product/list.do?page="+page);
				return;
			}
			List<ProductDTO> listProduct = dao.listProductImage(pNo);
			
			req.setAttribute("dto", dto);
			req.setAttribute("listProduct", listProduct);
			req.setAttribute("page", page);
			
			forward(req, resp, "/WEB-INF/views/product/article.jsp");
			return;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/product/list.do?page="+page);
	}
	
	private void updateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 상품 수정
		ProductDAO dao = new ProductDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		String cp = req.getContextPath();
		
		String page = req.getParameter("page");
		String pCategory_code = req.getParameter("pCategory_code");
		
		String query = "pCategory_code=" + pCategory_code + "&page="+pCategory_code;
		try {
			int pNo = Integer.parseInt(req.getParameter("pNo"));
			ProductDTO dto = dao.readProduct(pNo);
			
			if(dto == null) {
				resp.sendRedirect(cp + "/product/list.do?"+query);
				return;
			}
			
			
			List<ProductDTO> listImages = dao.listProductImage(pNo);
			
			req.setAttribute("dto", dto);
			req.setAttribute("page", page);
			req.setAttribute("listImages", listImages);
			
			req.setAttribute("mode", "update");
			
			forward(req, resp, "/WEB-INF/views/product/write.jsp");
			return;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp + "/product/list.do?page="+page);
		
	}
	
	private void updateSubmit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ProductDAO dao = new ProductDAO();
		
		String cp = req.getContextPath();
		String page = req.getParameter("page");
		
		try {
			ProductDTO dto = new ProductDTO();
			dto.setpCategory_code(req.getParameter("pCategory_code"));
			dto.setpName(req.getParameter("pName"));
			dto.setpPrice(Integer.parseInt(req.getParameter("pPrice")));
			dto.setpDesc(req.getParameter("pDesc"));
			dto.setpStock(Integer.parseInt(req.getParameter("pStock")));
			dto.setpDate(req.getParameter("pDate"));
			
			Map<String, String[]> map = doFileUpload(req.getParts(), pathname);
			if(map != null) {
				String[] saveImages = map.get("saveFilenames");
				dto.setImage_names(saveImages);
			}
			
			dao.updateProduct(dto);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(cp + "/product/list.do?page="+page);

	}
	
	private void deleteImage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// 사진만 삭제
		ProductDAO dao = new ProductDAO();
		
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		String cp = req.getContextPath();
		
		String page = req.getParameter("page");
		
		try {
			int pNo = Integer.parseInt(req.getParameter("pNo"));
			int imageNo = Integer.parseInt(req.getParameter("imageNo"));
			
			ProductDTO dto = dao.readProduct(pNo);
			
			if(dto == null) {
				resp.sendRedirect(cp + "/product/list.do?page="+page);
				return;
			}
			
			if(!dto.getmId().equals("admin")) {
				resp.sendRedirect(cp + "/product/list.do?page="+page);
				return;
			}
			
			List<ProductDTO> listImage = dao.listProductImage(pNo);
			
			for(ProductDTO vo : listImage) {
				if(vo.getImageNo() == imageNo) {
					//파일 삭제
					FileManager.doFiledelete(pathname, vo.getImage_name());
					dao.deleteProductImage("one", imageNo);
					listImage.remove(vo);
					break;
				}
				
				req.setAttribute("dto", dto);
				req.setAttribute("listImage", listImage);
				req.setAttribute("page", page);
				req.setAttribute("mode", "update");
				
				forward(req, resp, "/WEB-INF/views/product/write.jsp");
				return;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp + "/product/list.do?page="+page);
	}
	
	
	private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ProductDAO dao = new ProductDAO();
		HttpSession session = req.getSession();
		SessionInfo info = (SessionInfo)session.getAttribute("member");
		
		String cp = req.getContextPath();
		
		
		try {
			int pNo = Integer.parseInt(req.getParameter("pNo"));
			
			ProductDTO dto = dao.readProduct(pNo);
			
			if(dto == null) {
				resp.sendRedirect(cp + "/product/manage.do");
				return;
			}
			
			
			List<ProductDTO> listImage = dao.listProductImage(pNo);
			for(ProductDTO vo : listImage) {
				FileManager.doFiledelete(pathname , vo.getImage_name());
				
			}
			
			dao.deleteProductImage("all", pNo);
			
			dao.deleteProduct(pNo);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		resp.sendRedirect(cp+"/product/manage.do");
	}
}
