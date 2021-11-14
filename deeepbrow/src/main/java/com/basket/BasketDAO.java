package com.basket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class BasketDAO {
	private Connection conn = DBConn.getConnection();
	
	public int insertBasket(BasketDTO dto) throws SQLException {
		int result = 0;
		
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			
			sql = "insert into cart (userId, pnum, img, pname, price, quantity) "
					+ "    values(?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getUserId());
			pstmt.setInt(2, dto.getpNum());
			pstmt.setString(3, dto.getImg());
			pstmt.setString(4, dto.getPname());
			pstmt.setInt(5, dto.getPrice());
			pstmt.setInt(6, dto.getQuantity());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
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
	
	public List<BasketDTO> listBasket(String userId) {
		List<BasketDTO> list = new ArrayList<BasketDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql;
		
		try {
			
			sql = "select * from cart where userId = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, userId);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				BasketDTO dto = new BasketDTO();
				
				int q = rs.getInt("quantity");
				int p = rs.getInt("price");
				int onePrice = p/q;
				
				dto.setImg(rs.getString("img"));
				dto.setPname(rs.getString("pname"));
				dto.setpNum(rs.getInt("pnum"));
				dto.setPrice(p);
				dto.setQuantity(q);
				dto.setOnePrice(onePrice);
				dto.setUserId(rs.getString("userId"));
				
				list.add(dto);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
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

}
