package com.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.product.ProductDTO;
import com.util.DBConn;

public class BuyDAO {
	private Connection conn = DBConn.getConnection();
	
	public int insertBuy(BuyDTO dto) throws SQLException {
		
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		int seq;
		
		try {
			
			sql = "SELECT ORDER_SEQ.NEXTVAL FROM dual";
			
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			
			seq = 0;
			if(rs.next()) {
				seq = rs.getInt(1);
			}
			rs.close();
			pstmt.close();
			rs = null;
			pstmt = null;
			
			dto.setOrderno(seq);
			
			sql = "insert into product_order (orderno, order_date, whole_price, shipping_fee, pay_price, pay_date, mid) "
					+ "    values(?, sysdate, ?, ?, ?, sysdate, ?) ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, dto.getOrderno());
			pstmt.setInt(2, dto.getWhole_price());
			pstmt.setInt(3, dto.getShipping_fee());
			pstmt.setInt(4, dto.getPay_price());
			pstmt.setString(5, dto.getMid());
			
			result = pstmt.executeUpdate();
			
			pstmt.close();
			pstmt = null;
			sql = null;
			
			
			sql = "insert into delivery (orderno, dname, dtel, del_memo, delno, daddr1, daddr2, dzipcode, order_date, pay_date, mid, pay_price) "
					+ "    values(? , ?, ?, ?, del_seq.nextval, ?, ?, ?, sysdate, sysdate, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, dto.getOrderno());
			pstmt.setString(2, dto.getDname());
			pstmt.setString(3, dto.getDtel());
			pstmt.setString(4, dto.getDel_memo());
			pstmt.setString(5, dto.getDaddr1());
			pstmt.setString(6, dto.getDaddr2());
			pstmt.setString(7, dto.getDzipcode());
			pstmt.setString(8, dto.getMid());
			pstmt.setInt(9,  dto.getPay_price());
			
			result += pstmt.executeUpdate();
			
			pstmt.close();
			pstmt = null;
			sql = null;
			
			sql = "insert into Order_details (odno, quantity, odprice, pno, orderno) "
					+ "    values(Order_details_SEQ.nextval, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, dto.getQuantity());
			pstmt.setInt(2, dto.getOdprice());
			pstmt.setInt(3, dto.getPno());
			pstmt.setInt(4, dto.getOrderno());
			
			result += pstmt.executeUpdate();
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
		}
		
		return result;
	}
	
	public BuyDTO readImage(int pNum) {
		BuyDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			
			sql = "select * from Product_image where pno = ?";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, pNum);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new BuyDTO();
				
				dto.setImageFilename(rs.getString("image_name"));
			}
			
		} catch (Exception e) {
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
