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
import java.sql.SQLSyntaxErrorException;
import java.sql.Statement;

import org.junit.jupiter.api.Test;

public class DbSyncUtils {
	
	public static final String URL = "jdbc:mysql://localhost:3305/openmrs_c2c";
	
	public static final String USER = "";
	
	public static final String PASS = "";
	
	@Test
	public void printRowCount() throws Exception {
		try (Connection connection = DriverManager.getConnection(URL, USER, PASS)) {
			DatabaseMetaData metaData = connection.getMetaData();
			try (ResultSet tables = metaData.getTables(null, null, null, new String[] { "TABLE" })) {
				while (tables.next()) {
					String tableName = tables.getString("TABLE_NAME");
					try (Statement statement = connection.createStatement();
					        ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM " + tableName)) {
						if (resultSet.next()) {
							int rowCount = resultSet.getInt(1);
							if (rowCount > 0) {
								System.out.println(tableName + ", Row Count: " + rowCount);
							}
						}
					}
					catch (SQLSyntaxErrorException e) {
						if (!e.getMessage().equalsIgnoreCase("Table 'openmrs_c2c." + tableName + "' doesn't exist")) {
							throw e;
						}
					}
				}
			}
		}
	}
	
}
