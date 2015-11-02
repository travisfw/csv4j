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

/**
 * Domain type to use in tests. Simple in the sense that all fields are either
 * strings or primitives.
 * 
 * The domain type has to provide a public empty constructor and setter methods
 * for the fields under hydration.
 * 
 * All fields need to be of a type T, s.t. T defines a static method "valueOf:
 * String => T". Primitives are allowed, as they are boxed by csv4j.
 * 
 * @author Yannis Theocharis
 */
public class SimpleDomainType {

	private int field0;
	private String field1;
	private double field2;

	// Necessary for csv4j
	public SimpleDomainType() {
	}

	// Not necessary. Added for tests
	public static SimpleDomainType of(int field0, String field1, double field2) {
		return new SimpleDomainType(field0, field1, field2);
	}

	// Not necessary. Added for tests
	private SimpleDomainType(int field0, String field1, double field2) {
		this.field0 = field0;
		this.field1 = field1;
		this.field2 = field2;
	}

	// Setters are necessary for csv4j
	public void setField0(int field0) {
		this.field0 = field0;
	}

	// Setters are necessary for csv4j
	public void setField1(String field1) {
		this.field1 = field1;
	}

	// Setters are necessary for csv4j
	public void setField2(double field2) {
		this.field2 = field2;
	}

	// Not necessary. Added for tests
	public int getField0() {
		return field0;
	}

	// Not necessary. Added for tests
	public String getField1() {
		return field1;
	}

	// Not necessary. Added for tests
	public double getField2() {
		return field2;
	}

	// Not necessary. Added for tests
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + field0;
		result = prime * result + ((field1 == null) ? 0 : field1.hashCode());
		long temp;
		temp = Double.doubleToLongBits(field2);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		SimpleDomainType other = (SimpleDomainType) obj;
		if (field0 != other.field0)
			return false;
		if (field1 == null) {
			if (other.field1 != null)
				return false;
		} else if (!field1.equals(other.field1))
			return false;
		if (Double.doubleToLongBits(field2) != Double
				.doubleToLongBits(other.field2))
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
		sb.append("field1: ");
		sb.append(field1);
		sb.append(", ");
		sb.append("field2: ");
		sb.append(field2);
		sb.append(')');
		return sb.toString();
	}
}