package com.product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class ProductDAO {
	private Connection conn = DBConn.getConnection();
	
	// 상품 추가
	public int insertProduct(ProductDTO dto) throws SQLException{
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		int seq;
		
		
		try {
			sql = "SELECT Product_seq.NEXTVAL FROM dual";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			seq = 0;
			if(rs.next()) {
				seq = rs.getInt(1);
			}
			dto.setpNo(seq);
			
			rs.close();
			pstmt.close();
			rs = null;
			pstmt = null;
			
			sql = "INSERT INTO Product(pNo, pName, pPrice, pDesc, pStock, pDate, pCategory_code) "
					+ " VALUES (?,?,?,?,?,TO_CHAR(SYSDATE,'YYYYMMDD'),?) ";
			
			pstmt = conn.prepareStatement(sql);
		
			pstmt.setInt(1, dto.getpNo());
			pstmt.setString(2, dto.getpName());
			pstmt.setInt(3, dto.getpPrice());
			pstmt.setString(4, dto.getpDesc());
			pstmt.setInt(5, dto.getpStock());
			
			pstmt.setString(6, dto.getpCategory_code());
			
			result = pstmt.executeUpdate();
			pstmt.close();
			pstmt = null;
			
			if(dto.getImage_names()!=null) {
				sql = "INSERT INTO Product_image(imageNo,pNo,image_name) VALUES "
						+ " (Product_image_seq.NEXTVAL,?,?)";
				pstmt = conn.prepareStatement(sql);
				for(int i=0; i< dto.getImage_names().length; i++) {
					pstmt.setInt(1, dto.getpNo());
					pstmt.setString(2, dto.getImage_names()[i]);
					pstmt.executeUpdate();
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		
		return result;
	}
	
	// 데이터 카운트
	public int dataCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM Product";
			pstmt = conn.prepareStatement(sql);

			rs = pstmt.executeQuery();
			if (rs.next())
				result = rs.getInt(1);

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}

		return result;
	}
	
	// 카테고리 데이터 카운트
		public int dataCount(String pCategory_code) {
			int result = 0;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql;

			try {
				sql = "SELECT NVL(COUNT(*), 0) FROM Product";
				if(pCategory_code.length() != 0) {
					sql += " WHERE pCategory_code = ? ";
				}
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, pCategory_code);
				
				rs = pstmt.executeQuery();
				if (rs.next())
					result = rs.getInt(1);

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException e) {
					}
				}

				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
					}
				}
			}

			return result;
		}
	
	// 리스트
	public List<ProductDTO> listProduct(int start, int end){
		List<ProductDTO> list = new ArrayList<ProductDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append(" SELECT * FROM ( ");
			sb.append(" 	SELECT ROWNUM rnum, tb.* FROM ( ");
			sb.append("			SELECT p.pNo, pName, pPrice, pDesc, pStock, ");
			sb.append(" 			   TO_CHAR(pDate, 'YYYY-MM-DD') pDate, ");
			sb.append("				   p.pCategory_code, pCategory_name, image_name ");
			sb.append("				FROM Product p");
			sb.append("				JOIN Product_category pc ON p.pCategory_code = pc.pCategory_code ");
			sb.append("				LEFT OUTER JOIN (");
			sb.append("					SELECT imageNo, pNo, image_name FROM ( ");
			sb.append("						SELECT imageNo, pNo, image_name, ");
			sb.append("							ROW_NUMBER() OVER(PARTITION BY pNo ORDER BY imageNo ASC) rank ");
			sb.append("						FROM Product_image");
			sb.append("					) WHERE rank = 1 ");
			sb.append("				) pi ON p.pNo = pi.pNo ");
			sb.append("				ORDER BY pNo DESC ");
			sb.append("			) tb WHERE ROWNUM <= ? ");
			sb.append(" ) WHERE rnum >= ? ");
		
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, end);
			pstmt.setInt(2, start);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				ProductDTO dto = new ProductDTO();
			
				dto.setpNo(rs.getInt("pNo"));
				dto.setpName(rs.getString("pName"));
				dto.setpPrice(rs.getInt("pPrice"));
				dto.setpDesc(rs.getString("pDesc"));
				dto.setpStock(rs.getInt("pStock"));
				dto.setpDate(rs.getString("pDate"));
				dto.setpCategory_code(rs.getString("pCategory_code"));
				dto.setpCategory_name(rs.getString("pCategory_name"));
				dto.setImage_name(rs.getString("image_name"));
				list.add(dto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e2) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e2) {
				}
			}
		}
			return list;

		}		
	
		// 리스트 검색
		public List<ProductDTO> listProduct(int start, int end, String pCategory_code){
		List<ProductDTO> list = new ArrayList<ProductDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append(" SELECT * FROM ( ");
			sb.append(" 	SELECT ROWNUM rnum, tb.* FROM ( ");
			sb.append("			SELECT p.pNo, pName, pPrice, pDesc, pStock, ");
			sb.append(" 			   TO_CHAR(pDate, 'YYYY-MM-DD') pDate, ");
			sb.append("				    p.pCategory_code, pCategory_name, image_name ");
			sb.append("				FROM Product p");
			sb.append("				JOIN Product_category pc ON p.pCategory_code = pc.pCategory_code ");
			sb.append("				LEFT OUTER JOIN (");
			sb.append("					SELECT imageNo, pNo, image_name FROM ( ");
			sb.append("						SELECT imageNo, pNo, image_name, ");
			sb.append("							ROW_NUMBER() OVER(PARTITION BY pNo ORDER BY imageNo ASC) rank ");
			sb.append("						FROM Product_image");
			sb.append("					) WHERE rank = 1 ");
			sb.append("				) pi ON p.pNo = pi.pNo ");
			sb.append("             WHERE p.pCategory_code = ? ");
			sb.append("				ORDER BY pNo DESC ");
			sb.append("			) tb WHERE ROWNUM <= ? ");
			sb.append(" ) WHERE rnum >= ? ");
		
			pstmt = conn.prepareStatement(sb.toString());

			pstmt.setString(1, pCategory_code);
			pstmt.setInt(2, end);
			pstmt.setInt(3, start);

			rs = pstmt.executeQuery();
			while(rs.next()) {
				ProductDTO dto = new ProductDTO();
			
				dto.setpNo(rs.getInt("pNo"));
				dto.setpName(rs.getString("pName"));
				dto.setpPrice(rs.getInt("pPrice"));
				dto.setpDesc(rs.getString("pDesc"));
				dto.setpStock(rs.getInt("pStock"));
				dto.setpDate(rs.getString("pDate"));
				dto.setpCategory_code(rs.getString("pCategory_code"));
				dto.setpCategory_name(rs.getString("pCategory_name"));
				dto.setImage_name(rs.getString("image_name"));
				
				list.add(dto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e2) {
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e2) {
				}
			
			}
		}
			return list;

		}
	
		// 카테고리 리스트
		public List<ProductDTO> listCategory(){
			List<ProductDTO> list = new ArrayList<ProductDTO>();	
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql;
			
			try {
				sql = "SELECT pCategory_code, pCategory_name  FROM Product_category";
				
				pstmt = conn.prepareStatement(sql);
				rs = pstmt.executeQuery();
				
				while(rs.next()) {
					ProductDTO dto = new ProductDTO();
					
					dto.setpCategory_code(rs.getString("pCategory_code"));
					dto.setpCategory_name(rs.getString("pCategory_name"));
					
					list.add(dto);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				if(pstmt!=null) {
					try {
						pstmt.close();
					} catch (Exception e2) {
					}
				}
				if(rs!=null) {
					try {
						rs.close();
					} catch (Exception e2) {
					}
				}
			}
					
			return list;
		}
		
		public int searchCount(String keyword) {
			int result = 0;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql;

			try {
				sql = "SELECT NVL(COUNT(*), 0) FROM Product WHERE INSTR(pName,  ?) >= 1 OR INSTR(pDesc, ?) >= 1";
				
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, keyword);
				pstmt.setString(2, keyword);
				
				rs = pstmt.executeQuery();
				if (rs.next())
					result = rs.getInt(1);

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException e) {
					}
				}

				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
					}
				}
			}

			return result;
		}
		
		// 리스트 검색
		public List<ProductDTO> searchProduct(int start, int end, String keyword){
			List<ProductDTO> list = new ArrayList<ProductDTO>();
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			StringBuilder sb = new StringBuilder();
			
			try {
				sb.append(" SELECT * FROM ( ");
				sb.append(" 	SELECT ROWNUM rnum, tb.* FROM ( ");
				sb.append("			SELECT p.pNo, pName, pPrice, pDesc, pStock, ");
				sb.append(" 			   TO_CHAR(pDate, 'YYYY-MM-DD') pDate, ");
				sb.append("				    p.pCategory_code, pCategory_name, image_name ");
				sb.append("				FROM Product p");
				sb.append("				JOIN Product_category pc ON p.pCategory_code = pc.pCategory_code ");
				sb.append("				LEFT OUTER JOIN (");
				sb.append("					SELECT imageNo, pNo, image_name FROM ( ");
				sb.append("						SELECT imageNo, pNo, image_name, ");
				sb.append("							ROW_NUMBER() OVER(PARTITION BY pNo ORDER BY imageNo ASC) rank ");
				sb.append("						FROM Product_image");
				sb.append("					) WHERE rank = 1 ");
				sb.append("				) pi ON p.pNo = pi.pNo ");
				sb.append("             WHERE INSTR(pName, ?) >= 1 OR INSTR(pDesc, ?) >= 1 ");
				sb.append("				ORDER BY pNo DESC ");
				sb.append("			) tb WHERE ROWNUM <= ? ");
				sb.append(" ) WHERE rnum >= ? ");
			
				pstmt = conn.prepareStatement(sb.toString());

				pstmt.setString(1, keyword);
				pstmt.setString(2, keyword);
				pstmt.setInt(3, end);
				pstmt.setInt(4, start);

				rs = pstmt.executeQuery();
				while(rs.next()) {
					ProductDTO dto = new ProductDTO();
				
					dto.setpNo(rs.getInt("pNo"));
					dto.setpName(rs.getString("pName"));
					dto.setpPrice(rs.getInt("pPrice"));
					dto.setpDesc(rs.getString("pDesc"));
					dto.setpStock(rs.getInt("pStock"));
					dto.setpDate(rs.getString("pDate"));
					dto.setpCategory_code(rs.getString("pCategory_code"));
					dto.setpCategory_name(rs.getString("pCategory_name"));
					dto.setImage_name(rs.getString("image_name"));
					
					list.add(dto);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException e2) {
					}
				}
				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e2) {
					}
				
				}
			}
				return list;

			}
	

		// 아티클
		public ProductDTO readProduct(int pNo) {
			ProductDTO dto = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql;
			
			try {
				sql = "SELECT p.pNo, pName, pPrice, pDesc, pStock, "
						+ " TO_CHAR(pDate, 'YYYY-MM-DD') pDate, "
						+ " p.pCategory_code, pCategory_name "
						+ " FROM Product p "
						+ " JOIN Product_category pc ON p.pCategory_code = pc.pCategory_code "
						+ " WHERE pNo = ?";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setInt(1, pNo);

				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					dto = new ProductDTO();
					dto.setpNo(rs.getInt("pNo"));
					dto.setpName(rs.getString("pName"));
					dto.setpPrice(rs.getInt("pPrice"));
					dto.setpDesc(rs.getString("pDesc"));
					dto.setpStock(rs.getInt("pStock"));
					dto.setpDate(rs.getString("pDate"));
					dto.setpCategory_code(rs.getString("pCategory_Code"));
					dto.setpCategory_name(rs.getString("pCategory_name"));
					
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (Exception e2) {
					}
				}

				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (Exception e2) {
					}
				}
			}
			
			return dto;
		}
	
	// 수정
	public int updateProduct(ProductDTO dto) throws SQLException{
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE Product SET pName = ?, pPrice=?,pDesc=?,pStock=?, WHERE pNo=?";
								
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getpName());
			pstmt.setInt(2, dto.getpPrice());
			pstmt.setString(3, dto.getpDesc());
			pstmt.setInt(4, dto.getpStock());
			pstmt.setInt(5, dto.getpNo());
			
			result = pstmt.executeUpdate();
			pstmt.close();
			pstmt = null;
			
			if(dto.getImage_names() !=null) {
				sql = "INSERT INTO Product_image(imageNo,pNo,image_name) VALUES "
						+ " (Product_image_seq.NEXTVAL, ?, ?)";
				pstmt = conn.prepareStatement(sql);
				for(int i=0; i< dto.getImage_names().length; i++) {
					pstmt.setInt(1, dto.getpNo());
					pstmt.setString(2, dto.getImage_names()[i]);
					pstmt.executeUpdate();
				}
			}
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return result;
	}
	
	//삭제
	public int deleteProduct(int pNo) throws SQLException{
		int result = 0;
		PreparedStatement pstmt =null;
		String sql;
		
		try {
			
			sql = "DELETE FROM Product WHERE pNo = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, pNo);
			
			result = pstmt.executeUpdate();
					
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		
		
		return result;
	}
	
	
	public List<ProductDTO> listProductImage(int pNo) {
		List<ProductDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT imageNo, pNo, image_name FROM Product_image WHERE pNo = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, pNo);
			
			rs = pstmt.executeQuery();

			while (rs.next()) {
				ProductDTO dto = new ProductDTO();
				dto.setImageNo(rs.getInt("imageNo"));
				dto.setpNo(rs.getInt("pNo"));
				dto.setImage_name(rs.getString("image_name"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}

		return list;
	}

	public ProductDTO readProductImage(int fileNum) {
		ProductDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT imageNo, pNo, image_name FROM Product WHERE pNo = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, fileNum);
			
			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new ProductDTO();

				dto.setImageNo(rs.getInt("imageNo"));
				dto.setpNo(rs.getInt("pNo"));
				dto.setImage_name(rs.getString("image_name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}

			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}

		return dto;
	}
	
	public int deleteProductImage(String mode, int num) throws SQLException{
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			if(mode.equals("all")) {
				sql = "DELETE FROM Product_image WHERE pNo = ?";
			} else {
				sql = "DELETE FROM Product_image WHERE imageNo = ?";
			}
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, num);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		
		return result;
	}
	
	public int saleProductCount(int pNo) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM Order_Details WHERE pNo = ? ";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, pNo);
			rs = pstmt.executeQuery();
			if(rs.next())
				result = rs.getInt(1);
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
			
			
		}
		
		
		return result;
	}
	
	
	
	public int insertReview(ReviewDTO dto) throws SQLException{
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		int seq;
		
		
		try {
			sql = "SELECT Product_seq.NEXTVAL FROM dual";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			seq = 0;
			if(rs.next()) {
				seq = rs.getInt(1);
			}
			dto.setpNo(seq);
			
			rs.close();
			pstmt.close();
			rs = null;
			pstmt = null;
			
			sql = " INSERT INTO Review(rNo, rContent,rReg_date,pNo,rRate,mId) "
					+ " VALUES(review_seq.NEXTVAL, ?, TO_CHAR(SYSDATE,'YYYYMMDD'), ?,?,?) ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getrContent());
			pstmt.setInt(2, dto.getpNo());
			pstmt.setInt(3, dto.getrRate());
			pstmt.setString(4, dto.getmId());
			result = pstmt.executeUpdate();
			
			pstmt.close();
			pstmt = null;
			
			if(dto.getImage_names()!=null) {
				sql = "INSERT INTO Product_image(imageNo,pNo,image_name) VALUES "
						+ " (Product_image_seq.NEXTVAL,?,?)";
				pstmt = conn.prepareStatement(sql);
				for(int i=0; i< dto.getImage_names().length; i++) {
					pstmt.setInt(1, dto.getpNo());
					pstmt.setString(2, dto.getImage_names()[i]);
					pstmt.executeUpdate();
				}
			}
						
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		
		return result;
	}
	
	public List<ReviewDTO> listReview(int rNo, int start, int end){
		List<ReviewDTO> list = new ArrayList<ReviewDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append(" SELECT * FROM ( ");
			sb.append("		SELECT ROWNUM rnum, tb.* FROM ( ");
			sb.append("			SELECT rNo, rContent, rReg_date, r.pNo, r.mId, rRate, image_name ");
			sb.append("			FROM Review r ");
			sb.append("			JOIN Product p ON r.pNo = p.pNo ");
			sb.append("			JOIN Member m ON r.mId = m.mId" );
			sb.append("				LEFT OUTER JOIN (");
			sb.append("					SELECT imageNo, pNo, image_name FROM ( ");
			sb.append("						SELECT imageNo, pNo, image_name, ");
			sb.append("							ROW_NUMBER() OVER(PARTITION BY pNo ORDER BY imageNo ASC) rank ");
			sb.append("						FROM Product_image");
			sb.append("					) WHERE rank = 1 ");
			sb.append("				) pi ON r.pNo = pi.pNo ");
			sb.append("			WHERE rNo = ? ");
			sb.append("			ORDER BY r.rNo DESC ");
			sb.append("			) tb WHERE ROWNUM <= ? ");
			sb.append("	) WHERE rnum >= ? ");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, rNo);
			pstmt.setInt(2, end);
			pstmt.setInt(3, start);
		
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				ReviewDTO dto = new ReviewDTO();
				
				dto.setrNo(rs.getInt("rNo"));
				dto.setrContent(rs.getString("rContent"));
				dto.setrReg_date(rs.getString("rReg_date"));
				dto.setpNo(rs.getInt("pNo"));
				dto.setmId(rs.getString("mId"));
				dto.setrRate(rs.getInt("rRate"));
				dto.setImage_name(rs.getString("image_name"));
				
				list.add(dto);
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}				
			}if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return list;
	}
	
	public ReviewDTO readReview(int rNo) {
		ReviewDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT rNo, rContent, rReg_date, pNo, r.mId, rRate "
					+ " FROM Review r"
					+ " JOIN member m ON r.mId = m.mId "
					+ " WHERE rNo = ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, rNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto=new ReviewDTO();
				
				dto.setrNo(rs.getInt("rNo"));
				dto.setrContent(rs.getString("rContent"));
				dto.setrReg_date(rs.getString("rReg_date"));
				dto.setpNo(rs.getInt("pNo"));
				dto.setmId(rs.getString("mId"));
				dto.setrRate(rs.getInt("rRate"));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}				
			}if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return dto;
	}
	
	
	public int deleteReview(int rNo, String mId) throws SQLException{
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		if(! mId.equals("admin")) {
			ReviewDTO dto = readReview(rNo);
			if(dto == null || (! mId.equals(dto.getmId()))) {
				return result;
			}
		}
		
		try {
			sql = "DELETE FROM Review "
					+ " WHERE rNo = ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, rNo);
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return result;
	}
	
	// 데이터 카운트
		public int reviewCount() {
			int result = 0;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql;

			try {
				sql = "SELECT NVL(COUNT(*), 0) FROM Review";
				pstmt = conn.prepareStatement(sql);

				rs = pstmt.executeQuery();
				if (rs.next())
					result = rs.getInt(1);

			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (rs != null) {
					try {
						rs.close();
					} catch (SQLException e) {
					}
				}

				if (pstmt != null) {
					try {
						pstmt.close();
					} catch (SQLException e) {
					}
				}
			}

			return result;
		}
		
	
}
	

