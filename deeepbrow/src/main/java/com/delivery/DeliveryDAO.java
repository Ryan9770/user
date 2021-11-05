package com.delivery;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class DeliveryDAO {
	private Connection conn = DBConn.getConnection();
	
	public int dataCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM delivery";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			if(rs.next()) {
				result = rs.getInt(1);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (Exception e2) {
				}
			}
			
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e2) {
				}
			}
		}
		
		
		return result;
	}
	
	public List<DeliveryDTO> listDelivery(int start, int end) {
		List<DeliveryDTO> list = new ArrayList<DeliveryDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("SELECT * FROM ( ");
			sb.append("		SELECT ROWNUM rnum, tb.* FROM ( ");
			sb.append("			SELECT po.orderNo, mNo, dName, dTel, del_memo, delNo, dAddr1, dAddr2, dZipCode ");
			sb.append("			FROM delivery d ");
			sb.append("			JOIN productOrder po ON d.orderNo = po.orderNo ");
			sb.append("			) tb WHERE ROWNUM <= ?");
			sb.append("		) WHERE rnum >= ? ");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, end);
			pstmt.setInt(2, start);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				DeliveryDTO dto = new DeliveryDTO();
				
				dto.setOrderNo(rs.getString("orderNo"));
				dto.setdName(rs.getString("dName"));
				dto.setdTel(rs.getString("dTel"));
				dto.setDel_memo(rs.getString("del_memo"));
				dto.setDelNo(rs.getString("delNo"));
				dto.setdAddr1(rs.getString("dAddr1"));
				dto.setdAddr2(rs.getString("dAddr2"));
				dto.setdZipCode(rs.getString("dZipCode"));
				
				list.add(dto);
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e2) {
				}
			}
		}
		
		return list;
	}
}
