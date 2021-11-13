package com.member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


import com.util.DBConn;

public class MemberDAO {
	private Connection conn = DBConn.getConnection();
	
	public MemberDTO loginMember(String mId, String mPassword) {
		MemberDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("SELECT mId, mPassword, mName, mReg_date ");
			sb.append(" FROM member ");
			sb.append(" WHERE mId=? AND mPassword=? AND enabled=1");
			
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, mId);
			pstmt.setString(2, mPassword);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				dto = new MemberDTO();
				dto.setmId(rs.getString("mId"));
				dto.setmPassword(rs.getString("mPassword"));				
				dto.setmName(rs.getString("mName"));
				dto.setmReg_date(rs.getString("mReg_date"));
				
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
			
			if(pstmt != null) {
				try {
					pstmt.close();
				} catch (Exception e2) {
				}
			}
			
		}
		
		return dto;
	}
	
	public int insertMember(MemberDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			conn.setAutoCommit(false);
			
			sql = "INSERT INTO member(mId, mNo, mName, mBirth, mReg_date, mPassword, enabled, mZipcode, "
					+ "mEmail, mAddr1, mAddr2, mTel) VALUES (?, member_seq.nextVal, ?, TO_DATE(?,'YYYYMMDD'), SYSDATE, ?, 1, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getmId());
			pstmt.setString(2, dto.getmName());
			pstmt.setString(3, dto.getmBirth());
			pstmt.setString(4, dto.getmPassword());
			pstmt.setString(5, dto.getmZipcode());
			pstmt.setString(6, dto.getmEmail());
			pstmt.setString(7, dto.getmAddr1());
			pstmt.setString(8, dto.getmAddr2());
			pstmt.setString(9, dto.getmTel());

			
			result = pstmt.executeUpdate();

			
			conn.commit();

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
	
	public MemberDTO readMember(String mId) {
		MemberDTO dto = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();
		
		try {
			sb.append("SELECT mId, mNo, mName, TO_CHAR(mBirth, 'YYYY-MM-DD') mBirth, mReg_date, ");
			sb.append("      mPassword, enabled, mZipcode, mEmail, mAddr1, mAddr2, mTel ");
			sb.append("  FROM member where mId=? ");
		
			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, mId);
			rs = pstmt.executeQuery();
			if(rs.next()) {
				dto = new MemberDTO();
				dto.setmId(rs.getString("mId"));
				dto.setmName(rs.getString("mName"));
				dto.setmBirth(rs.getString("mBirth"));
				dto.setmReg_date(rs.getString("mReg_date"));
				dto.setmPassword(rs.getString("mPassword"));
				dto.setEnabled(rs.getInt("enabled"));
				dto.setmTel(rs.getString("mTel"));
				if(dto.getmTel() != null) {
					String[] ss = dto.getmTel().split("-");
					if(ss.length == 3) {
						dto.setmTel1(ss[0]);
						dto.setmTel2(ss[1]);
						dto.setmTel3(ss[2]);
					}
				}
				dto.setmEmail(rs.getString("mEmail"));
				if(dto.getmEmail() != null) {
					String[] ss = dto.getmEmail().split("@");
					if(ss.length == 2) {
						dto.setmEmail1(ss[0]);
						dto.setmEmail2(ss[1]);
					}
				}
				dto.setmZipcode(rs.getString("mZipcode"));
				dto.setmAddr1(rs.getString("mAddr1"));
				dto.setmAddr2(rs.getString("mAddr2"));
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
	
	public int updateMember(MemberDTO dto) throws SQLException {
		int result = 0;
		PreparedStatement pstmt = null;
		String sql;
		
		try {
			sql = "UPDATE member SET mBirth=TO_DATE(?,'YYYYMMDD'), mPassword=?, mZipcode=?, mEmail=?, mAddr1=?, mAddr2=?, mTel=?  WHERE mId=?";
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, dto.getmBirth());
			pstmt.setString(2, dto.getmPassword());
			pstmt.setString(3, dto.getmZipcode());
			pstmt.setString(4, dto.getmEmail());			
			pstmt.setString(5, dto.getmAddr1());
			pstmt.setString(6, dto.getmAddr2());
			pstmt.setString(7, dto.getmTel());
			pstmt.setString(8, dto.getmId());
			
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
				}
			}
		}
		return result;
	}
	
	public int deleteMember(String mId) throws SQLException {
		int result = 0;
		PreparedStatement pstmt=null;
		String sql;
		
		try {
			sql = "DELETE FROM member WHERE mId=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mId);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(pstmt!=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
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
			sql = "SELECT NVL(COUNT(*), 0) FROM member";
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
	
	// 데이터 개수
	public int dataCount(String condition, String keyword) {
		int result = 0;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql;
		
		try {
			sql = "SELECT NVL(COUNT(*), 0) FROM member mId ";
			sql += "  WHERE INSTR(" + condition + ", ?) >= 1 ";
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, keyword);
			
			rs = pstmt.executeQuery();
			if(rs.next() ) {
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

	public List<MemberDTO> listMember(int start, int end){
		List<MemberDTO> list = new ArrayList<MemberDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append(" SELECT * FROM ( ");
			sb.append("     SELECT ROWNUM rnum, tb.* FROM ( ");
			sb.append("         SELECT mNo, mName, mId, mEmail, mTel, TO_CHAR(mReg_date, 'YYYY-MM-DD') mReg_date");
			sb.append("         FROM member ");
			sb.append("         ORDER BY mReg_date ASC ");
			sb.append("     ) tb WHERE ROWNUM <= ? ");
			sb.append(" ) WHERE rnum >= ? ");

			pstmt = conn.prepareStatement(sb.toString());
			
			pstmt.setInt(1, end);
			pstmt.setInt(2, start);

			rs = pstmt.executeQuery();

			while (rs.next()) {
				MemberDTO dto = new MemberDTO();
				
				dto.setmNo(rs.getString("mNo"));
				dto.setmName(rs.getString("mName"));
				dto.setmId(rs.getString("mId"));
				dto.setmEmail(rs.getString("mEmail"));
				dto.setmTel(rs.getString("mTel"));
				dto.setmReg_date(rs.getString("mReg_date"));
				
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
	
	public List<MemberDTO> listMember(int start, int end, String condition, String keyword) {
		List<MemberDTO> list = new ArrayList<MemberDTO>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		StringBuilder sb = new StringBuilder();

		try {
			sb.append(" SELECT * FROM ( ");
			sb.append("     SELECT ROWNUM rnum, tb.* FROM ( ");
			sb.append("         SELECT mNo, mName, mId, mEmail, mTel, ");
			sb.append("               TO_CHAR(mReg_date, 'YYYY-MM-DD') mReg_date ");
			sb.append("         FROM member ");
			sb.append("         WHERE INSTR(" + condition + ", ?) >= 1 ");
			sb.append("         ORDER BY mReg_date ASC");
			sb.append("     ) tb WHERE ROWNUM <= ? ");
			sb.append(" ) WHERE rnum >= ? ");

			pstmt = conn.prepareStatement(sb.toString());
			pstmt.setString(1, keyword);
			pstmt.setInt(2, end);
			pstmt.setInt(3, start);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				MemberDTO dto = new MemberDTO();

				dto.setmNo(rs.getString("mNo"));
				dto.setmName(rs.getString("mName"));
				dto.setmId(rs.getString("mId"));
				dto.setmEmail(rs.getString("mEmail"));
				dto.setmTel(rs.getString("mTel"));
				dto.setmReg_date(rs.getString("mReg_date"));

				list.add(dto);
			}
		} catch (SQLException e) {
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
