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

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.base.Preconditions;

/**
 * Hydrates (deserializes) csv formatted data into java objects of a given
 * domain type.
 * 
 * @author Yannis Theocharis
 *
 * @param <T>
 *            domain type whose objects need to be hydrated
 */
public class Hydrator<T> {

	private static final String DELIMETER = ",";

	private final RichType<T> richType;

	private Hydrator(final Class<T> type) {
		this.richType = RichType.of(type);
	}

	/**
	 * Hydrator factory
	 * 
	 * @param type
	 *            the domain type
	 * @return hydrator of the given type
	 */
	public static <U> Hydrator<U> of(final Class<U> type) {
		Preconditions.checkNotNull(type);
		return new Hydrator<U>(type);
	}

	/**
	 * Reads a csv file and returns list of hydrated objects of the given type
	 * 
	 * @param p
	 *            path to the input csv file
	 * @return list of objects hydrated with data from the input csv file
	 */
	public List<T> fromCSV(final Path p) {
		final String[] csvFields = readHeaders(p);
		return readDataLines(p, csvFields);
	}

	private String[] readHeaders(final Path p) {
		Preconditions.checkNotNull(p);
		try (Stream<String> lines = Sane.fileLines(p)) {
			Optional<String> firstLine = lines.findFirst();
			return firstLine.get().split(DELIMETER);
		}
	}

	private List<T> readDataLines(final Path p, final String[] csvFields) {
		try (Stream<String> lines = Sane.fileLines(p)) {
			return lines
					.skip(1)
					.map(line -> toObject(csvFields, line.split(DELIMETER, -1)))
					.collect(Collectors.toList());
		}
	}

	private T toObject(final String[] csvFields, final String[] scvValues) {
		Preconditions.checkState(csvFields.length == scvValues.length);
		T object = Sane.newInstance(richType.getType());

		for (int i = 0; i < csvFields.length; i++) {
			String csvField = csvFields[i];
			String csvValue = scvValues[i];
			RichField richField = richType.richFieldOf(csvField);
			// ignore csv fields not matching any domain object field
			if (richField != null && !csvValue.isEmpty()) {
				richField.setField(object, csvValue);
			}
		}
		return object;
	}
}