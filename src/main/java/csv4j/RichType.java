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
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.google.common.base.Preconditions;

import csv4j.annotations.CsvFields;

/**
 * Wrapps domain type along with two maps: "java field name" => RichField and
 * "csv file name" => "java field name"
 * 
 * 
 * @author Yannis Theocharis
 */
class RichType<T> {

	private final Class<T> type;
	private final Map<String, RichField> jfieldNameToRichField;
	private final Map<String, String> csvfieldToJfield;

	private RichType(Class<T> type) {
		this.type = type;
		jfieldNameToRichField = jfieldNameToRichField();
		csvfieldToJfield = csvfieldToJfield();
	}

	static <U> RichType<U> of(Class<U> type) {
		return new RichType<U>(type);
	}

	private Map<String, RichField> jfieldNameToRichField() {
		Field[] fields = type.getDeclaredFields();
		return Arrays
				.asList(fields)
				.stream()
				.collect(
						Collectors.toMap(Field::getName,
								f -> RichField.of(type, f)));
	}

	private Map<String, String> csvfieldToJfield() {
		Map<String, String> map = new HashMap<>();
		Field[] fields = type.getDeclaredFields();
		for (Field f : fields) {
			CsvFields annotation = f.getAnnotation(CsvFields.class);
			if (annotation != null) {
				String fieldName = f.getName();
				for (String v : annotation.value()) {
					map.put(v, fieldName);
				}
			}
		}
		return map;
	}

	Class<T> getType() {
		return type;
	}

	RichField richFieldOf(String csvfield) {
		Preconditions.checkNotNull(csvfield);
		String jfield = csvfieldToJfield.get(csvfield);
		return jfield == null ? jfieldNameToRichField.get(csvfield)
				: jfieldNameToRichField.get(jfield);
	}
}
