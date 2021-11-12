package com.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.util.DBConn;

public class BuyDAO {
	private Connection conn = DBConn.getConnection();
	
	public int insertBuy(BuyDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			
			conn.setAutoCommit(false);
			
			sql = "INSERT INTO product_order (ORDERNO, ORDER_DATE, WHOLE_PRICE, SHIPPING_FEE, PAY_PRICE, PAY_DATE, MID) \r\n"
					+ "    VALUES(ORDER_SEQ.nextval, SYSDATE, ?, ?, ?, SYSDATE, ?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getPay_price());
			pstmt.setString(2, dto.getShipping_fee());
			pstmt.setString(3, dto.getPay_price());
			pstmt.setString(4, dto.getMid());
			
			result = pstmt.executeUpdate();
			
			conn.commit();
			pstmt.close();
			pstmt = null;
			
			String oder_seq = dto.getOrderno();
			
			sql = "";
			pstmt = conn.prepareStatement(sql);
			
			
			
		} catch (SQLException e) {
			try {
				conn.rollback();
			} catch (SQLException e2) {
			}
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
			
			try {
				conn.setAutoCommit(true);
			} catch (SQLException e2) {
			}
		}
		
		return result;
	}
}
