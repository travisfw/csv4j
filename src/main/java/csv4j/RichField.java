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

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import com.google.common.base.Preconditions;
import com.google.common.primitives.Primitives;

/**
 * Wraps a field of the domain type along with the setter method for this field.
 * 
 * @author Yannis Theocharis
 */
class RichField {

	private static final String SET = "set";
	private static final String VALUE_OF = "valueOf";

	private final Field field;
	private final Method setter;

	private RichField(Field field, Method setter) {
		this.field = field;
		this.setter = setter;
	}

	/**
	 * Factory method for creating a field enriched with its setter method
	 * 
	 * @param type
	 *            domain type
	 * @param field
	 *            type's field
	 * @return enriched field with setter method
	 */
	static RichField of(final Class<?> type, final Field field) {
		Preconditions.checkNotNull(type);
		Preconditions.checkNotNull(field);
		String fieldName = field.getName();
		Class<?> fieldType = field.getType();
		String setterName = setterName(fieldName);
		Method method = Sane.declaredMethod(type, setterName, fieldType);
		return new RichField(field, method);
	}

	private static String setterName(String fieldName) {
		StringBuilder sb = new StringBuilder(SET);
		sb.append(fieldName.substring(0, 1).toUpperCase());
		sb.append(fieldName.substring(1));
		return sb.toString();
	}

	Field getField() {
		return field;
	}

	Method getSetter() {
		return setter;
	}

	/**
	 * Sets value to object's field. Uses reflection to call the setter of the
	 * field on the object. The value to be set, is first mapped from String to
	 * field's type, by invoking "valueOf" method of field's type.
	 * 
	 * @param object
	 *            the object on which the setter is invoked
	 * @param value
	 *            the value to set in string serialisation
	 */
	<T> void setField(final T object, final String value) {
		Object typedValue = valueOfType(value, field.getType());
		Sane.invokeMethod(setter, object, typedValue);
	}

	private Object valueOfType(final String value, final Class<?> fieldType) {
		if (fieldType == String.class) {
			return value;
		}
		Class<?> possiblyWrappedFieldType = fieldType.isPrimitive() ? Primitives
				.wrap(fieldType) : fieldType;
		Method valueOf = Sane.declaredMethod(possiblyWrappedFieldType,
				VALUE_OF, String.class);
		return Sane.invokeMethod(valueOf, value);
	}
}