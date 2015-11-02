/**
 * Copyright (C) 2015 Yannis Theocharis
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package csv4j;

import csv4j.annotations.CsvFields;

/**
 * Domain type to use in tests. Uses annotations to match java with csv fields.
 * 
 * The domain type has to provide a public empty constructor and setter methods
 * for the fields under hydration.
 * 
 * All fields need to be of a type T, s.t. T defines a static method "valueOf:
 * String => T". Primitives are allowed, as they are boxed by csv4j.
 * 
 * @author Yannis Theocharis
 */
public class AnnotatedDomainType {

	// no annotation, so it will be match with "field0" csv field
	private int field0;

	// match att1 with both "field1" and "field3" csv fields
	@CsvFields({ "field1", "field3" })
	private String att1;

	// match att2 with "field2" csv field
	@CsvFields({ "field2" })
	private double att2;

	// Necessary for csv4j
	public AnnotatedDomainType() {
	}

	// Not necessary. Added for tests
	public static AnnotatedDomainType of(int field0, String att1, double att2) {
		return new AnnotatedDomainType(field0, att1, att2);
	}

	// Not necessary. Added for tests
	private AnnotatedDomainType(int field0, String att1, double att2) {
		this.field0 = field0;
		this.att1 = att1;
		this.att2 = att2;
	}

	// Setters are necessary for csv4j
	public void setField0(int field0) {
		this.field0 = field0;
	}

	// Setters are necessary for csv4j
	public void setAtt1(String att1) {
		this.att1 = att1;
	}

	// Setters are necessary for csv4j
	public void setAtt2(double att2) {
		this.att2 = att2;
	}

	// Not necessary. Added for tests
	public int getField0() {
		return field0;
	}

	// Not necessary. Added for tests
	public String getAtt1() {
		return att1;
	}

	// Not necessary. Added for tests
	public double getAtt2() {
		return att2;
	}

	// Not necessary. Added for tests
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((att1 == null) ? 0 : att1.hashCode());
		long temp;
		temp = Double.doubleToLongBits(att2);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + field0;
		return result;
	}

	// Not necessary. Added for tests
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AnnotatedDomainType other = (AnnotatedDomainType) obj;
		if (att1 == null) {
			if (other.att1 != null)
				return false;
		} else if (!att1.equals(other.att1))
			return false;
		if (Double.doubleToLongBits(att2) != Double
				.doubleToLongBits(other.att2))
			return false;
		if (field0 != other.field0)
			return false;
		return true;
	}

	// Not necessary. Added for tests
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('(');
		sb.append("field0: ");
		sb.append(field0);
		sb.append(", ");
		sb.append("att1: ");
		sb.append(att1);
		sb.append(", ");
		sb.append("att2: ");
		sb.append(att2);
		sb.append(')');
		return sb.toString();
	}
}