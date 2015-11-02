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
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.mockito.Matchers;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import csv4j.io.DataFeed;

public class HydratorTest {

	@DataProvider
	Object[][] headersParams() {
		return new Object[][] {

				{ Arrays.asList("field0,field1,field2", "0,csv,3.14"),
						new String[] { "field0", "field1", "field2" } },
				{ Arrays.asList("field0,field1,field2"),
						new String[] { "field0", "field1", "field2" } },
				{ Arrays.asList("field0,field1,field2, , "),
						new String[] { "field0", "field1", "field2", " ", " " } },
				{ Arrays.asList("field0,field1,field2,,"),
						new String[] { "field0", "field1", "field2", "", "" } },
				{
						Arrays.asList(",field0, ,,field1,field2,"),
						new String[] { "", "field0", " ", "", "field1",
								"field2", "" } },

		};
	}

	@Test(dataProvider = "headersParams")
	public void headersOrderShouldBePreservedAndEmptyHeadersAllowed(
			List<String> lines, String[] expectedHeaders) {
		DataFeed dataFeed = Mockito.spy(DataFeed.class);
		Mockito.when(dataFeed.lines(Matchers.any(Path.class))).thenReturn(
				lines.stream());

		Hydrator<SimpleDomainType> hydrator = Hydrator.of(
				SimpleDomainType.class, dataFeed);
		Path p = Mockito.mock(Path.class);
		String[] actualHeaders = hydrator.readHeaders(p);
		Assert.assertEquals(actualHeaders, expectedHeaders);
	}

	@Test(expectedExceptions = RuntimeException.class)
	public void headersShouldRethrowCheckedExceptionAsUnchecked() {
		DataFeed dataFeed = Mockito.spy(DataFeed.class);
		Mockito.when(dataFeed.lines(Matchers.any(Path.class))).thenThrow(
				new IOException());

		Hydrator<SimpleDomainType> hydrator = Hydrator.of(
				SimpleDomainType.class, dataFeed);
		Path p = Mockito.mock(Path.class);
		hydrator.readHeaders(p);
	}

	@DataProvider
	Object[][] dataParams() {
		return new Object[][] {

				{
						new String[] { "field0", "field1", "field2" },
						Arrays.asList("", "0,csv,3.14", "1,4,2.71", "2,j,1.61",
								"3,is awesome,1.41"),
						Arrays.asList(SimpleDomainType.of(0, "csv", 3.14),
								SimpleDomainType.of(1, "4", 2.71),
								SimpleDomainType.of(2, "j", 1.61),
								SimpleDomainType.of(3, "is awesome", 1.41)) },

				{
						new String[] { "field0", "field1", "field2" },
						Arrays.asList("", "0,,3.14", "1,,2.71", ",j,1.61",
								"3,is awesome,"),
						Arrays.asList(SimpleDomainType.of(0, null, 3.14),
								SimpleDomainType.of(1, null, 2.71),
								SimpleDomainType.of(0, "j", 1.61),
								SimpleDomainType.of(3, "is awesome", 0.0)) },

		};
	}

	@Test(dataProvider = "dataParams")
	public void testDatalines(String[] headers, List<String> lines,
			List<String> expected) {
		DataFeed dataFeed = Mockito.spy(DataFeed.class);
		Mockito.when(dataFeed.lines(Matchers.any(Path.class))).thenReturn(
				lines.stream());

		Hydrator<SimpleDomainType> hydrator = Hydrator.of(
				SimpleDomainType.class, dataFeed);
		Path p = Mockito.mock(Path.class);
		List<SimpleDomainType> actual = hydrator.readDataLines(p, headers);

		Assert.assertEquals(actual, expected);
	}

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