package rf.foundation.utils;

import rf.foundation.context.AppContext;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class DBUtils {
	/**
	 * query long value by SQL: select count(*) from table
	 * 
	 * @param sql
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	static public Long queryLongValue(String sql, List<Object> parameters) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		long value = 0;
		try {
			DataSource dataSource = AppContext.getBean("dataSource",DataSource.class);
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(sql);
			if (parameters != null) {
				for (int i = 0; i < parameters.size(); i++) {
					pstmt.setObject(i + 1, parameters.get(i));
				}
			}
			rs = pstmt.executeQuery();
			if (rs.next()) {
				value = rs.getLong(1);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}
		return value;
	}

	/**
	 * execute DML SQL: insert/update/delete
	 * 
	 * @param strSQL
	 * @param parameters
	 * @return
	 * @throws Exception
	 */
	static public Integer executeUpdateSQL(String strSQL, List<Object> parameters) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		try {
			DataSource dataSource = AppContext.getBean("dataSource",DataSource.class);;
			conn = dataSource.getConnection();
			pstmt = conn.prepareStatement(strSQL);
			if (parameters != null) {
				for (int i = 0; i < parameters.size(); i++) {
					pstmt.setObject(i + 1, parameters.get(i));
				}
			}
			return pstmt.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}
	}

	static public Long executeBatchSQL(String[] executeSqls, String querySql) throws Exception {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		long value = 0;
		try {
			DataSource dataSource = AppContext.getBean("dataSource",DataSource.class);;
			conn = dataSource.getConnection();
			for (String exeSql : executeSqls) {
				pstmt = conn.prepareStatement(exeSql);
				pstmt.executeUpdate();
			}
			pstmt.close();

			pstmt = conn.prepareStatement(querySql);
			rs = pstmt.executeQuery();
			if (rs.next()) {
				value = rs.getLong(1);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (rs != null)
				rs.close();
			if (pstmt != null)
				pstmt.close();
			if (conn != null)
				conn.close();
		}
		return value;
	}
}
