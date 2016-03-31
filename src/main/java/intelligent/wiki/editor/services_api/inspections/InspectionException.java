/*
 * Copyright (C) 2016 Myroslav Rudnytskyi
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 */

package intelligent.wiki.editor.services_api.inspections;

/**
 * Exception, throwing when inspector can not perform inspection due to some problems.
 *
 * @author Myroslav Rudnytskyi
 * @version 28.02.2016
 */
public class InspectionException extends RuntimeException {

	private Class<? extends Inspection> inspectionClass;

	public InspectionException(Class<? extends Inspection> inspectionClass, String message) {
		super(message);
		setInspectionClass(inspectionClass);
	}

	public InspectionException(Class<? extends Inspection> inspectionClass, Throwable cause) {
		super(cause);
		setInspectionClass(inspectionClass);
	}

	public Class<? extends Inspection> getInspectionClass() {
		return inspectionClass;
	}

	public void setInspectionClass(Class<? extends Inspection> inspectionClass) {
		this.inspectionClass = inspectionClass;
	}
}
