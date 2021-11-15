package com.order;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

	public int insertBuy2(BuyDTO dto) throws SQLException {
		
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
			
			
			for(int i = 0; i < dto.getpNums().length ; i++) {
			
				pstmt.setInt(1, dto.getQuantitys()[i]);
				pstmt.setInt(2, dto.getOnePrices()[i]);
				pstmt.setInt(3,  dto.getpNums()[i]);
				pstmt.setInt(4, dto.getOrderno());
				
				result += pstmt.executeUpdate();
			}
			
			
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
	
	// 이미지 불러오기 메소드
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
	
	// 구매 리스트
	public List<BuyDTO> BuyList(int start, int end){
		List<BuyDTO> list = new ArrayList<BuyDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append(" SELECT * FROM ( ");
			sb.append("		SELECT ROWNUM rnum, tb.* FROM ( ");
			sb.append("			SELECT po.orderNo, order_date, whole_price, shipping_fee, pay_price, pay_date, po.mid ");
			sb.append("			FROM Product_order po ");
			sb.append("			JOIN member m ON po.mid = m.mid ");
			sb.append("			LEFT OUTER JOIN (");
			sb.append("				SELECT odNo, quantity, odPrice, pNo, orderNo");
			sb.append("				FROM order_details");
			sb.append("			) od ON po.orderNo = od.orderNo");
			sb.append("			LEFT OUTER JOIN (");
			sb.append("				SELECT orderNo, dsDate, ds_manage");
			sb.append("			 	FROM delivery_status");
			sb.append("			) ds ON po.orderNo = ds.orderNo ");
			sb.append("			ORDER BY po.orderNo DESC ");
			sb.append("		) tb WHERE ROWNUM <= ? ");
			sb.append("	) WHERE rnum >= ? ");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, end);
			pstmt.setInt(2, start);
			
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				BuyDTO dto = new BuyDTO();
				
				dto.setOrderno(rs.getInt("orderNo"));
				dto.setOrder_date(rs.getString("order_date"));
				dto.setWhole_price(rs.getInt("whole_price"));
				dto.setShipping_fee(rs.getInt("shipping_fee"));
				dto.setPay_price(rs.getInt("pay_price"));
				dto.setPay_date(rs.getString("pay_date"));
				dto.setMid(rs.getString("mid"));
				
				list.add(dto);
			}
			
		} catch (Exception e) {
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
				} catch (SQLException e) {
				}
			}
		}
		
		
		return list;
	}
		
	// 데이터 카운트
	public int dataCount() {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM Product_order";
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

	public int deleteOrder(int orderNo) throws SQLException{
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "DELETE FROM Product_order WHERE orderNo = ? ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, orderNo);
			
			result = pstmt.executeUpdate();

		} catch (Exception e) {
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
}
