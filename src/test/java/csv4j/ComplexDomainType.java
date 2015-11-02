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
 * Domain type to use in tests. Complex in the sense that it has a field that is
 * of a custom type (MyInt field0).
 * 
 * The domain type has to provide a public empty constructor and setter methods
 * for the fields under hydration.
 * 
 * All fields need to be of a type T, s.t. T defines a static method "valueOf:
 * String => T". Primitives are allowed, as they are boxed by csv4j.
 * 
 * @author Yannis Theocharis
 */
public class ComplexDomainType {

	private MyInt field0;
	private String field1;
	private double field2;

	// Needed by csv4j
	public ComplexDomainType() {
	}

	// Added for tests
	public static ComplexDomainType of(MyInt field0, String field1,
			double field2) {
		return new ComplexDomainType(field0, field1, field2);
	}

	private ComplexDomainType(MyInt field0, String field1, double field2) {
		this.field0 = field0;
		this.field1 = field1;
		this.field2 = field2;
	}

	// Setters are necessary for csv4j
	public void setField0(MyInt field0) {
		this.field0 = field0;
	}

	public void setField1(String field1) {
		this.field1 = field1;
	}

	public void setField2(double field2) {
		this.field2 = field2;
	}

	// Not necessary. Added for tests
	public MyInt getField0() {
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
		result = prime * result + ((field0 == null) ? 0 : field0.hashCode());
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
		ComplexDomainType other = (ComplexDomainType) obj;
		if (field0 == null) {
			if (other.field0 != null)
				return false;
		} else if (!field0.equals(other.field0))
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