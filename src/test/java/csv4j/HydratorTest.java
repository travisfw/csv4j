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

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class HydratorTest {

	@DataProvider
	Object[][] simpleCsvToExpected() {
		return new Object[][] {
				{
						"data.csv",
						Arrays.asList(SimpleDomainType.of(0, "csv", 3.14),
								SimpleDomainType.of(1, "4", 2.71),
								SimpleDomainType.of(2, "j", 1.61),
								SimpleDomainType.of(3, "is awesome", 1.41)) },
				{
						"missingField.csv",
						Arrays.asList(SimpleDomainType.of(0, null, 3.14),
								SimpleDomainType.of(1, null, 2.71),
								SimpleDomainType.of(2, null, 1.61),
								SimpleDomainType.of(3, null, 1.41)) },
				{
						"additionalField.csv",
						Arrays.asList(SimpleDomainType.of(0, "csv", 3.14),
								SimpleDomainType.of(1, "4", 2.71),
								SimpleDomainType.of(2, "j", 1.61),
								SimpleDomainType.of(3, "is awesome", 1.41)) },

				{
						"missingColumsAndValues.csv",
						Arrays.asList(SimpleDomainType.of(0, "csv", 3.14),
								SimpleDomainType.of(1, "4", 2.71),
								SimpleDomainType.of(2, "j", 1.61),
								SimpleDomainType.of(3, "is awesome", 1.41)) }, };
	}

	@Test(dataProvider = "simpleCsvToExpected")
	public void simpleDomainType(String csvFileName,
			List<SimpleDomainType> expected) {
		Path p = toPath(csvFileName);
		Hydrator<SimpleDomainType> hydrator = Hydrator
				.of(SimpleDomainType.class);
		List<SimpleDomainType> actual = hydrator.fromCSV(p);
		Assert.assertEquals(actual, expected);
	}

	@Test
	public void complexDomainType() {
		Path p = toPath("data.csv");
		Hydrator<ComplexDomainType> hydrator = Hydrator
				.of(ComplexDomainType.class);
		List<ComplexDomainType> actual = hydrator.fromCSV(p);
		List<ComplexDomainType> expected = Arrays.asList(
				ComplexDomainType.of(new MyInt(0), "csv", 3.14),
				ComplexDomainType.of(new MyInt(1), "4", 2.71),
				ComplexDomainType.of(new MyInt(2), "j", 1.61),
				ComplexDomainType.of(new MyInt(3), "is awesome", 1.41));
		Assert.assertEquals(actual, expected);
	}

	private static final List<AnnotatedDomainType> ANNOTATED_EXPECTED = Arrays
			.asList(AnnotatedDomainType.of(0, "csv", 3.14),
					AnnotatedDomainType.of(1, "4", 2.71),
					AnnotatedDomainType.of(2, "j", 1.61),
					AnnotatedDomainType.of(3, "is awesome", 1.41));

	@DataProvider
	Object[][] annotatedCsvToExpected() {
		return new Object[][] { { "data.csv", ANNOTATED_EXPECTED },
				{ "data2.csv", ANNOTATED_EXPECTED } };
	}

	@Test(dataProvider = "annotatedCsvToExpected")
	public void annotatedDomainType(String csvFileName,
			List<AnnotatedDomainType> expected) {
		Path p = toPath(csvFileName);
		Hydrator<AnnotatedDomainType> hydrator = Hydrator
				.of(AnnotatedDomainType.class);
		List<AnnotatedDomainType> actual = hydrator.fromCSV(p);
		Assert.assertEquals(actual, expected);
	}

	private Path toPath(String relativeFileName) {
		String dataFilePath = this.getClass().getClassLoader()
				.getResource(relativeFileName).getFile();
		return new File(dataFilePath).toPath();
	}

}