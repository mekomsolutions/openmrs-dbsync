/*
 * Copyright (C) Amiyul LLC - All Rights Reserved
 *
 * This source code is protected under international copyright law. All rights
 * reserved and protected by the copyright holder.
 *
 * This file is confidential and only available to authorized individuals with the
 * permission of the copyright holder. If you encounter this file and do not have
 * permission, please contact the copyright holder and delete this file.
 */
package org.openmrs.eip.dbsync;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Map;
import java.util.TreeMap;

import org.junit.jupiter.api.Test;

public class DbSyncUtils {
	
	public static final String DB_NAME = "openmrs_c2c";
	
	public static final String URL = "jdbc:mysql://localhost:3305/" + DB_NAME;
	
	public static final String USER = "";
	
	public static final String PASS = "";
	
	@Test
	public void printRowCount() throws Exception {
		try (Connection connection = DriverManager.getConnection(URL, USER, PASS)) {
			DatabaseMetaData metaData = connection.getMetaData();
			Map<String, Integer> tableNames = new TreeMap<>();
			try (ResultSet tables = metaData.getTables(DB_NAME, null, null, new String[] { "TABLE" })) {
				while (tables.next()) {
					String tableName = tables.getString("TABLE_NAME");
					try (Statement statement = connection.createStatement();
					        ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM " + tableName)) {
						if (resultSet.next()) {
							int rowCount = resultSet.getInt(1);
							if (rowCount > 0) {
								tableNames.put(tableName, rowCount);
							} else {
								//System.out.println(tableName);
							}
						}
					}
				}
			}
			
			tableNames.forEach((k, v) -> System.out.println(k + ", Row Count: " + v));
		}
	}
	
}
