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
			sb.append("			SELECT po.orderNo, po.order_date, po.pay_date, po.mId, po.pay_price, dName, ds_manage, dTel, del_memo, delNo, dAddr1, dAddr2, dZipCode ");
			sb.append("			FROM delivery d ");
			sb.append("			JOIN product_order po ON d.orderNo = po.orderNo ");
			sb.append("			LEFT OUTER JOIN delivery_status ds ON d.orderNo = ds.orderNo ");
			sb.append("			) tb WHERE ROWNUM <= ?");
			sb.append("		) WHERE rnum >= ? ");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, end);
			pstmt.setInt(2, start);
			
			rs = pstmt.executeQuery();
			while(rs.next()) {
				DeliveryDTO dto = new DeliveryDTO();
				
				dto.setOrderNo(rs.getInt("orderNo"));
				dto.setOrder_date(rs.getString("order_date"));
				dto.setPay_date(rs.getString("pay_date"));
				dto.setmId(rs.getString("mId"));
				dto.setPay_price(rs.getInt("pay_price"));
				dto.setdName(rs.getString("dName"));
				dto.setdTel(rs.getString("dTel"));
				dto.setDel_memo(rs.getString("del_memo"));
				dto.setDelNo(rs.getString("delNo"));
				dto.setdAddr1(rs.getString("dAddr1"));
				dto.setdAddr2(rs.getString("dAddr2"));
				dto.setdZipCode(rs.getString("dZipCode"));
				dto.setDs_manage(rs.getString("ds_manage"));
				
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
	
	public int cancelOrder(int orderNo) throws SQLException {
		int result = 0;
		
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "INSERT INTO delivery_status(orderNo, dsDate, ds_manage) VALUES(?, SYSDATE, '주문취소')";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, orderNo);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		return result;
	}
	
	public int deliverOrder(int orderNo) throws SQLException {
		int result = 0;
		
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "INSERT INTO delivery_status(orderNo, dsDate, ds_manage) VALUES(?, SYSDATE, '발송완료')";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, orderNo);
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		
		return result;
	}
	
	public DeliveryDTO readOrder(int orderNo) {
		DeliveryDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT po.orderNo, po.order_date, po.pay_date, po.pay_price, po.mId, "
					+ "dName, dTel, dZipCode, dAddr1, dAddr2, del_memo, delNo, ds_manage "
					+ "FROM delivery d "
					+ "JOIN product_order po ON d.orderNo = po.orderNo "
					+ "JOIN delivery_status ds ON d.orderNo = ds.orderNo "
					+ "WHERE po.orderNo = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, orderNo);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new DeliveryDTO();
				
				dto.setOrderNo(rs.getInt("orderNo"));
				dto.setOrder_date(rs.getString("order_date"));
				dto.setPay_date(rs.getString("pay_date"));
				dto.setPay_price(rs.getInt("pay_price"));
				dto.setmId(rs.getString("mId"));
				dto.setdName(rs.getString("dName"));
				dto.setdTel(rs.getString("dTel"));
				dto.setdZipCode(rs.getString("dZipCode"));
				dto.setdAddr1(rs.getString("dAddr1"));
				dto.setdAddr2(rs.getString("dAddr2"));
				dto.setDel_memo(rs.getString("del_memo"));
				dto.setDelNo(rs.getString("delNo"));
				dto.setDs_manage(rs.getString("ds_manage"));
			}
					
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
				}
			}
			
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
		}
		
		return dto;
	}
}
