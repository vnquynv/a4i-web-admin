package com.a4i.util;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.sql.RowSet;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;

public class DBUtil {
	public static Connection getConnection() throws Exception {
		Context ctx = new InitialContext();
		DataSource dataSource = (DataSource) ctx.lookup("java:/comp/env/jdbc/a4iDB");
		return dataSource.getConnection();
	}
	
	public static String getValue(String funcName, Object[] params, Connection connection) throws SQLException {
		CallableStatement stmt = connection.prepareCall("select " + funcName);
		for (int i = 0; i < params.length; i++) {
			stmt.setObject(i + 1, params[i]);
		}
		
		ResultSet rs = stmt.executeQuery();
		if (rs == null) return null;
		rs.next();
		String value = rs.getString(1);
		rs.close();
		connection.commit();
		connection.close();
		
		return value;
	}
	
	public static RowSet getRef(String proName, Object[] params, Connection connection) throws SQLException{
		CallableStatement stmt = connection.prepareCall("{ call " + proName + " }");
		for (int i = 0; i < params.length; i++) {
			stmt.setObject(i + 1, params[i]);
		}
		
		ResultSet rs = stmt.executeQuery();
		RowSetFactory factory = RowSetProvider.newFactory();
		CachedRowSet rowset = factory.createCachedRowSet();
		rowset.populate(rs);
		
		rs.close();
		connection.close();
		
		return rowset;
	}
	
	public static RowSet executeQuery(String sql, Object[] params, Connection connection) throws SQLException{
		CallableStatement stmt = connection.prepareCall(sql);
		for (int i = 0; i < params.length; i++) {
			stmt.setObject(i + 1, params[i]);
		}
		
		ResultSet rs = stmt.executeQuery();
		RowSetFactory factory = RowSetProvider.newFactory();
		CachedRowSet rowset = factory.createCachedRowSet();
		rowset.populate(rs);
		
		rs.close();
		connection.close();
		
		return rowset;
	}
}
