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
 * A custom type used from ComplexDomainType as a POC that custom typed fields
 * can be hydrated as long as they provide a "valueOf" method that maps a String
 * into an instance of the custom type.
 * 
 * @author Yannis Theocharis
 */
public class MyInt {

	private final int n;

	public MyInt(int n) {
		this.n = n;
	}

	public static MyInt valueOf(String s) {
		return new MyInt(Integer.valueOf(s));
	}

	// Not necessary. Added for tests
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + n;
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
		MyInt other = (MyInt) obj;
		if (n != other.n)
			return false;
		return true;
	}

	// Not necessary. Added for tests
	@Override
	public String toString() {
		return String.valueOf(n);
	}
}