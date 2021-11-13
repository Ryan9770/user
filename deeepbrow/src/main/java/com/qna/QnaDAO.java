package com.qna;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.util.DBConn;

public class QnaDAO {
	private Connection conn = DBConn.getConnection();
	
	public int insertQna(QnaDTO dto) throws SQLException{
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql="INSERT INTO qna (qNo, qSubject, qContent, qReg_date, qCategory, pNo, mId) "
					+ "VALUES (qna_seq.NEXTVAL, ?,?,SYSDATE,?,?,?)";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getqSubject());
			pstmt.setString(2, dto.getqContent());
			pstmt.setString(3, dto.getqCategory());
			pstmt.setInt(4, dto.getpNo());
			pstmt.setString(5, dto.getmId());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt!=null) {
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
			sql = "SELECT NVL(COUNT(*), 0) FROM qna";
			pstmt = conn.prepareStatement(sql);
			
			rs = pstmt.executeQuery();
			if (rs.next()) {
				result = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs!=null) {
				try {
					rs.close();
				} catch (Exception e2) {
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
	
	public int dataCount(String condition, String keyword) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM qna q JOIN member m ON q.mId=m.mId ";
			if (condition.equals("all")) {
				sql += "  WHERE INSTR(qSubject, ?) >= 1 OR INSTR(qContent, ?) >= 1 ";
			} else if (condition.equals("qRreg_date")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sql += "  WHERE TO_CHAR(qReg_date, 'YYYYMMDD') = ? ";
			} else {
				sql += "  WHERE INSTR(" + condition + ", ?) >= 1 ";
			}

			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, keyword);
			if (condition.equals("all")) {
				pstmt.setString(2, keyword);
			}

			rs = pstmt.executeQuery();

			if (rs.next()) {
				result = rs.getInt(1);
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
		return result;
	}
	
	// QnA 리스트
	public List<QnaDTO> listQna(int start, int end){
		List<QnaDTO> list = new ArrayList<QnaDTO>();
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append(" SELECT * FROM ( ");
			sb.append("     SELECT ROWNUM rnum, tb.* FROM ( ");
			sb.append("         SELECT q.qNo, q.mId, q.qSubject, q.qReg_date, q.qCategory, q.pNo, pName, image_name, ");
			sb.append("               NVL(replyCount, 0) replyCount ");
			sb.append("         FROM qna q ");
			sb.append("         JOIN member m ON q.mId = m.mId ");
			sb.append("         LEFT OUTER JOIN ( ");
			sb.append("             SELECT qNo, COUNT(*) replyCount FROM qna_Manage");
			sb.append("             GROUP BY qNo");
			sb.append("         ) c ON q.qNo = c.qNo");
			sb.append("         LEFT OUTER JOIN ( ");
			sb.append("             select pNo, pName from product");
			sb.append("           ) p on q.pNo = p.pNo");
			sb.append("         LEFT OUTER JOIN ( ");
			sb.append("             select pNo, MIN(image_name) image_name");
			sb.append("             from product_image pi");
			sb.append("         	group by pno ");
			sb.append("          ) pi on p.pno = pi.pno ");
			sb.append("     ) tb WHERE ROWNUM <= ? ");
			sb.append(" ) WHERE rnum >= ? ");

			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, end);
			pstmt.setInt(2, start);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				QnaDTO dto = new QnaDTO();

				dto.setqNo(rs.getInt("qNo"));
				dto.setmId(rs.getString("mId"));
				dto.setqSubject(rs.getString("qSubject"));
				dto.setqReg_date(rs.getString("qReg_date"));
				dto.setqCategory(rs.getString("qCategory"));
				dto.setpNo(rs.getInt("pNo"));
				dto.setpName(rs.getString("pName"));
				dto.setImageFilename(rs.getString("image_name"));
				
				dto.setReplyCount(rs.getInt("replyCount"));

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
	
	// 검색에서 리스트
	public List<QnaDTO> listQna(int start, int end, String condition, String keyword) {
		List<QnaDTO> list = new ArrayList<QnaDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append(" SELECT * FROM ( ");
			sb.append("     SELECT ROWNUM rnum, tb.* FROM ( ");
			sb.append("         SELECT q.qNo, q.mId, q.qSubject, q.qReg_date, q.qCategory, q.pNo, pName, image_name, ");
			sb.append("               NVL(replyCount, 0) replyCount ");
			sb.append("         FROM qna q ");
			sb.append("         JOIN member m ON q.mId = m.mId ");
			sb.append("         LEFT OUTER JOIN ( ");
			sb.append("             SELECT qmNo, COUNT(*) replyCount FROM qna_Manage");
			sb.append("             GROUP BY qmNo");
			sb.append("         ) c ON q.qNo = c.qmNo");
			sb.append("         LEFT OUTER JOIN ( ");
			sb.append("             select pNo, pname from product");
			sb.append("             ) p on q.pNo = p.pNo");
			sb.append("         LEFT OUTER JOIN ( ");
			sb.append("             select pNo, MIN(image_name) image_name");
			sb.append("             from product_image pi");
			sb.append("         	group by pno ");
			sb.append("          ) pi on p.pno = pi.pno ");
			if (condition.equals("all")) {
				sb.append("     WHERE INSTR(qSubject, ?) >= 1 OR INSTR(qContent, ?) >= 1 ");
			} else if (condition.equals("qReg_date")) {
				keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
				sb.append("     WHERE TO_CHAR(qReg_date, 'YYYYMMDD') = ?");
			} else {
				sb.append("     WHERE INSTR(" + condition + ", ?) >= 1 ");
			}
			sb.append("     ) tb WHERE ROWNUM <= ? ");
			sb.append(" ) WHERE rnum >= ? ");
			
			pstmt = conn.prepareStatement(sb.toString());
			
			if (condition.equals("all")) {
				pstmt.setString(1, keyword);
				pstmt.setString(2, keyword);
				pstmt.setInt(3, end);
				pstmt.setInt(4, start);
			} else {
				pstmt.setString(1, keyword);
				pstmt.setInt(2, end);
				pstmt.setInt(3, start);
			}
			rs = pstmt.executeQuery();

			while (rs.next()) {
				QnaDTO dto = new QnaDTO();

				dto.setqNo(rs.getInt("qNo"));
				dto.setmId(rs.getString("mId"));
				dto.setqSubject(rs.getString("qSubject"));
				dto.setqReg_date(rs.getString("qReg_date"));
				dto.setqCategory(rs.getString("qCategory"));
				dto.setpNo(rs.getInt("pNo"));
				dto.setpName(rs.getString("pName"));
				dto.setImageFilename(rs.getString("image_name"));
				
				dto.setReplyCount(rs.getInt("replyCount"));

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
		
	public QnaDTO readQna(int num) {
		QnaDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;

		try {
			sql = "SELECT q.qNo, q.mId, q.qSubject, q.qContent, q.qReg_date, q.qCategory, q.pNo, pName "
					+ " FROM qna q "
					+ " JOIN member m ON q.mId=m.mId "
					+ " LEFT OUTER JOIN ("
					+ " SELECT pNo, pName from product "
					+ ") p ON q.pNo = p.pNo "
					+ " WHERE qNo = ?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, num);

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new QnaDTO();

				dto.setqNo(rs.getInt("qNo"));
				dto.setmId(rs.getString("mId"));
				dto.setqSubject(rs.getString("qSubject"));
				dto.setqContent(rs.getString("qContent"));
				dto.setqReg_date(rs.getString("qReg_date"));
				dto.setqCategory(rs.getString("qCategory"));
				dto.setpNo(rs.getInt("pNo"));
				dto.setpName(rs.getString("pName"));
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
	
	
	// 이전글
	public QnaDTO preReadQna(int num, String condition, String keyword) {
		QnaDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			if (keyword != null && keyword.length() != 0) {
				sb.append(" SELECT * FROM ( ");
				sb.append("    SELECT qNo, qSubject ");
				sb.append("    FROM qna q ");
				sb.append("    JOIN member m ON q.mId = m.mId ");
				sb.append("    WHERE ( qNo > ? ) ");
				if (condition.equals("all")) {
					sb.append("   AND ( INSTR(qSubject, ?) >= 1 OR INSTR(qContent, ?) >= 1 ) ");
				} else if (condition.equals("qReg_date")) {
					keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
					sb.append("   AND ( TO_CHAR(qReg_date, 'YYYYMMDD') = ? ) ");
				} else {
					sb.append("   AND ( INSTR(" + condition + ", ?) >= 1 ) ");
				}
				sb.append("     ORDER BY qNo ASC ");
				sb.append(" ) WHERE ROWNUM = 1 ");

				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setInt(1, num);
				pstmt.setString(2, keyword);
				if (condition.equals("all")) {
					pstmt.setString(3, keyword);
				}
			} else {
				sb.append(" SELECT * FROM ( ");
				sb.append("     SELECT qNo, qSubject FROM qna ");
				sb.append("     WHERE qNo > ? ");
				sb.append("     ORDER BY qNo ASC ");
				sb.append(" ) WHERE ROWNUM = 1 ");

				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setInt(1, num);
			}

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new QnaDTO();
				dto.setqNo(rs.getInt("qNo"));
				dto.setqSubject(rs.getString("qSubject"));
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
	
	// 다음글
	public QnaDTO nextReadQna(int num, String condition, String keyword) {
		QnaDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			if (keyword != null && keyword.length() != 0) {
				sb.append(" SELECT * FROM ( ");
				sb.append("    SELECT qNo, qSubject ");
				sb.append("    FROM qna q ");
				sb.append("    JOIN member m ON q.mId = m.mId ");
				sb.append("    WHERE ( qNo < ? ) ");
				if (condition.equals("all")) {
					sb.append("   AND ( INSTR(qSubject, ?) >= 1 OR INSTR(qContent, ?) >= 1 ) ");
				} else if (condition.equals("qReg_date")) {
					keyword = keyword.replaceAll("(\\-|\\/|\\.)", "");
					sb.append("   AND ( TO_CHAR(qReg_date, 'YYYYMMDD') = ? ) ");
				} else {
					sb.append("   AND ( INSTR(" + condition + ", ?) >= 1 ) ");
				}
				sb.append("     ORDER BY qNo DESC ");
				sb.append(" ) WHERE ROWNUM = 1 ");

				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setInt(1, num);
				pstmt.setString(2, keyword);
				if (condition.equals("all")) {
					pstmt.setString(3, keyword);
				}
			} else {
				sb.append(" SELECT * FROM ( ");
				sb.append("     SELECT qNo, qSubject FROM qna ");
				sb.append("     WHERE qNo < ? ");
				sb.append("     ORDER BY qNo ASC ");
				sb.append(" ) WHERE ROWNUM = 1 ");

				pstmt = conn.prepareStatement(sb.toString());
				
				pstmt.setInt(1, num);
			}

			rs = pstmt.executeQuery();

			if (rs.next()) {
				dto = new QnaDTO();
				dto.setqNo(rs.getInt("qNo"));
				dto.setqSubject(rs.getString("qSubject"));
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
	
	// 수정
	public int updateQna(QnaDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;

		try {
			sql = "UPDATE bbs SET qSubject=?, qContent=?, qCategory=?, pNo=? WHERE qNo=? AND mId=?";
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, dto.getqSubject());
			pstmt.setString(2, dto.getqContent());
			pstmt.setString(3, dto.getqCategory());
			pstmt.setInt(4, dto.getpNo());
			pstmt.setInt(5, dto.getqNo());
			pstmt.setString(6, dto.getmId());
			
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
	
	// 삭제 (답글도 한번에 삭제되게 수정해야함)
	public int deleteQna(int num, String mId) throws SQLException {
		int result,result2 = 0;
		
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		
		String sql;

		try {
			if (mId.equals("admin")) {
				sql= "DELETE FROM qna_manage WHERE qNo=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, num);
				result = pstmt.executeUpdate();
				
				sql = "DELETE FROM qna WHERE qNo=?";
				pstmt2 = conn.prepareStatement(sql);
				pstmt2.setInt(1, num);
				result2 = pstmt.executeUpdate();
			} else {
				sql= "DELETE FROM qna_manage WHERE qNo=?";
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, num);
				result = pstmt.executeUpdate();
				
				sql = "DELETE FROM qna WHERE qNo=? AND mId=?";
				pstmt2 = conn.prepareStatement(sql);
				pstmt2.setInt(1, num);
				pstmt2.setString(2, mId);
				result2 = pstmt2.executeUpdate();
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
			if (pstmt2 != null) {
				try {
					pstmt2.close();
				} catch (SQLException e) {
				}
			}
		}
		return result&result2;
	}
	// 게시물 답글 추가
	public int insertQm(QnamanageDTO dto) throws SQLException{
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql= "INSERT INTO qna_Manage (qmNo, qNo, qmReg_date, qmContent, mId)"
					+ " VALUES(Qnamanage_seq.NEXTVAL, ?,SYSDATE,?,?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, dto.getqNo());
			pstmt.setString(2, dto.getQmContent());
			pstmt.setString(3, dto.getmId());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt != null)
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
		}
		
		return result;
	}
	// 답글 세기
	public int dataCountQm(int qNo) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM qna_Manage WHERE qNo=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, qNo);
			
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
		
		
		return result;
	}
	// 답글 리스트
	public List<QnamanageDTO> listQm(int num, int start, int end){
		List<QnamanageDTO> list = new ArrayList<>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append(" SELECT * FROM ( ");
			sb.append("     SELECT ROWNUM rnum, tb.* FROM ( ");
			sb.append("         SELECT r.qmNo, r.mId, qNo, qmContent, r.qmReg_date ");
			sb.append("         FROM qna_manage r ");
			sb.append("         JOIN member m ON r.mId = m.mId ");
			sb.append("	        WHERE qNo = ?");
			sb.append("         ORDER BY r.qmNo DESC ");
			sb.append("     ) tb WHERE ROWNUM <= ? ");
			sb.append(" ) WHERE rnum >= ? ");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setInt(1, num);
			pstmt.setInt(2, end);
			pstmt.setInt(3, start);

			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				QnamanageDTO dto = new QnamanageDTO();
				
				dto.setmId(rs.getString("mId"));
				dto.setQmNo(rs.getInt("qmNo"));
				dto.setQmContent(rs.getString("qmContent"));
				dto.setQmReg_date(rs.getString("qmReg_date"));
				
				list.add(dto);
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
				} catch (SQLException e) {
				}
			}
		}
		
		return list;
	}
	// 답글 읽기
	public QnamanageDTO readQm(int qmNo) {
		QnamanageDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT qmNo, qNo, r.mId, qmContent ,r.qmReg_date "
					+ "  FROM qna_Manage r JOIN member m ON r.mId=m.mId  "
					+ "  WHERE qmNo = ? ";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, qmNo);

			rs=pstmt.executeQuery();
			
			if(rs.next()) {
				dto=new QnamanageDTO();
				
				dto.setQmNo(rs.getInt("qmNo"));
				dto.setqNo(rs.getInt("qNo"));
				dto.setmId(rs.getString("userId"));
				dto.setQmContent(rs.getString("qmContent"));
				dto.setQmReg_date(rs.getString("reg_date"));
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
				} catch (SQLException e) {
				}
			}
		}
		
		return dto;
	}
	// 답글 삭제
	public int deleteQm(int qmNo, String mId) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		if(! mId.equals("admin")) {
			QnamanageDTO dto = readQm(qmNo);
			if(dto == null || (! mId.equals(dto.getmId()))) {
				return result;
			}
		}
		try {
			sql = "DELETE FROM qna_Manage WHERE qmNo=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, qmNo);
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
}
