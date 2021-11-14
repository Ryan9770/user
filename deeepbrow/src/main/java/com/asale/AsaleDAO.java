package com.asale;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class AsaleDAO {
	private Connection conn = DBConn.getConnection();
	
	public int dataCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM product p "
					+ "JOIN order_details od ON p.pNo = od.pNo ";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return result;
	}
	
	public List<AsaleDTO> listAsale(int start, int end) {
		List<AsaleDTO> list = new ArrayList<AsaleDTO>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("SELECT * FROM ( ");
			sb.append("		SELECT ROWNUM rnum, tb.* FROM ( ");
			sb.append("			SELECT p.pNo, pName, pPrice, SUM(quantity) sq ");
			sb.append("			FROM product p ");
			sb.append("			JOIN order_details od ON p.pNo = od.pNo ");
			sb.append("			GROUP BY p.pNo, pName, pPrice ");
			sb.append("			) tb WHERE ROWNUM <= ?");
			sb.append("		) WHERE rnum >= ? ");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, end);
			pstmt.setInt(2, start);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				AsaleDTO dto = new AsaleDTO();
				
				dto.setpNo(rs.getInt("pNo"));
				dto.setpName(rs.getString("pName"));
				dto.setpPrice(rs.getInt("pPrice"));
				dto.setpCount(rs.getInt("sq"));
				
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
	
	public int totalSum() {
		int sum = 0;
		
		return sum;
	}
}
