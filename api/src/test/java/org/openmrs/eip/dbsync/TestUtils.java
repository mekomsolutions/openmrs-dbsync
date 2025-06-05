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

import org.springframework.test.context.transaction.TestTransaction;

public class TestUtils {
	
	/**
	 * Should be called from a test method to flushes the active test transaction.
	 * 
	 * @see org.springframework.test.context.transaction.TestTransaction
	 */
	public static void flush() {
		if (!TestTransaction.isActive()) {
			throw new RuntimeException("No active test transaction was found");
		}
		
		TestTransaction.flagForCommit();
		TestTransaction.end();
	}
	
	/**
	 * Should be called from a test method to flush the active test transaction and start a new one.
	 *
	 * @see org.springframework.test.context.transaction.TestTransaction
	 */
	public static void flushAndStart() {
		flush();
		TestTransaction.start();
	}
	
}
