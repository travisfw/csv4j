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

import java.lang.reflect.Method;

/**
 * Bypasses java's epic failure called "checked exception"
 * 
 * @author Yannis Theocharis
 */
class Sane {

	static Method declaredMethod(Class<?> type, String setterName,
			Class<?> fieldType) {
		try {
			return type.getDeclaredMethod(setterName, fieldType);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	static <T> T newInstance(Class<T> type) {
		try {
			return type.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	static Object invokeMethod(Method method, String value) {
		return invokeMethod(method, null, value);
	}

	static <T, U> Object invokeMethod(Method method, T object, U value) {
		try {
			return method.invoke(object, value);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
