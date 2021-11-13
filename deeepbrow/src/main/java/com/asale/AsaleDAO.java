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
	
	public List<AsaleDTO> listAsale(int start, int end) {
		List<AsaleDTO> list = new ArrayList<AsaleDTO>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				AsaleDTO dto = new AsaleDTO();
				
				dto.setpNo(rs.getInt("pNo"));
				dto.setpName(rs.getString("pName"));
				dto.setpPrice(rs.getInt("pPrice"));
				dto.setpCount(rs.getInt("pCount"));
				dto.setFilename(rs.getString("image_name"));
				
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
