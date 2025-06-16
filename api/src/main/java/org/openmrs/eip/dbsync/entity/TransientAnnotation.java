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
package org.openmrs.eip.dbsync.entity;

import java.lang.annotation.Annotation;

import jakarta.persistence.Transient;

public class TransientAnnotation implements Transient {
	
	@Override
	public Class<? extends Annotation> annotationType() {
		return Transient.class;
	}
	
}
