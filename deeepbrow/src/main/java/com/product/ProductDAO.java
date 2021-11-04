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
		String sql = null;
		
		try {
			
			sql = "INSERT INTO Product(pNo, pName, pPrice, pDesc, pStock, pDate, discount, pCategory_code) "
					+ " VALUES (product_seq.NEXTVAL,?,?,?,?,SYSDATE,?,?)";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getpName());
			pstmt.setInt(2, dto.getpPrice());
			pstmt.setString(3, dto.getpDesc());
			pstmt.setInt(4, dto.getpStock());
			
			pstmt.setInt(5, dto.getDiscount());
			pstmt.setString(6, dto.getpCategory_code());
			
			result = pstmt.executeUpdate();
			pstmt.close();
			pstmt = null;
			
			if(dto.getImage_names() !=null) {
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
	
	public List<ProductDTO> listProduct(int start, int end){
		List<ProductDTO> list = new ArrayList<ProductDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append(" SELECT * FROM ( ");
			sb.append(" 	SELECT ROWNUM rnum, tb.* FROM ( ");
			sb.append("			SELECT p.pNo, pName, pPrice, pDesc, pStock, ");
			sb.append(" 			TO_CHAR(pDate, 'YYYY-MM-DD) pDate, ");
			sb.append("				discount, p.pCategory_code, pCategory_name, image_name ");
			sb.append("				FROM Product p");
			sb.append("				JOIN Product_category pc ON p.pCategory_code = pc.pCategory_code ");
			sb.append("				LEFT OUTER JOIN (");
			sb.append("					SELECT imageNo, pNo, image_name, ");
			sb.append("						ROW_NUMBER() OVER(PARTITION BY pNo ORDER BY imageNo ASC) rank ");
			sb.append("					FROM Product_image");
			sb.append("				) WHERE rank = 1 ");
			sb.append("			) pi ON p.pNo = i.pNo ");
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
				dto.setDiscount(rs.getInt("Discount"));
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

	
		public ProductDTO readProduct(String pNo) {
			ProductDTO dto = null;
			PreparedStatement pstmt = null;
			ResultSet rs = null;
			String sql;
			
			try {
				sql = "SELECT pNo, pName, pPrice, pDesc, pStock, "
						+ " TO_CHAR(pDate, 'YYYY-MM-DD) pDate, "
						+ " discount, p.pCategory_code, pCategory_name "
						+ " FROM Product p "
						+ " JOIN Product_category pc ON p.pCategory_code = pc.pCategory_code "
						+ " WHERE pNo = ?";
				pstmt = conn.prepareStatement(sql);
				
				pstmt.setString(1, pNo);

				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					dto = new ProductDTO();
					dto.setpNo(rs.getInt("pNo"));
					dto.setpName(rs.getString("pName"));
					dto.setpPrice(rs.getInt("pPrice"));
					dto.setpDesc(rs.getString("pDesc"));
					dto.setpStock(rs.getInt("pStock"));
					dto.setpDate(rs.getString("pDate"));
					dto.setDiscount(rs.getInt("Discount"));
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
	
	public int updateProduct(ProductDTO dto) throws SQLException{
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE Product SET pName = ?, pPrice=?,pDesc=?,pStock=?, Discount=? WHERE pNo=?";
								
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getpName());
			pstmt.setInt(2, dto.getpPrice());
			pstmt.setString(3, dto.getpDesc());
			pstmt.setInt(4, dto.getpStock());
			pstmt.setInt(5, dto.getDiscount());
			pstmt.setInt(6, dto.getpNo());
			
			result = pstmt.executeUpdate();
			pstmt.close();
			pstmt = null;
			
			if(dto.getImage_names() !=null) {
				sql = "INSERT INTO Product_image(imageNo,pNo,image_name) VALUES "
						+ " (Product_image_seq.NEXTVAL,?,?)";
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
			sql = "SELECT imageNo, pNo, image_name FROM Product WHERE pNo = ?";
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
}
	

