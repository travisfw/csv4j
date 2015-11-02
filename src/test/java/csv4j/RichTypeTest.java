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

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class RichTypeTest {

	@DataProvider
	Object[][] fieldNameParams() {
		return new Object[][] {

		{ SimpleDomainType.class, "field0", "field0" },
				{ SimpleDomainType.class, "field1", "field1" },
				{ SimpleDomainType.class, "field2", "field2" },

				{ AnnotatedDomainType.class, "field0", "field0" },
				{ AnnotatedDomainType.class, "field1", "att1" },
				{ AnnotatedDomainType.class, "field2", "att2" },
				{ AnnotatedDomainType.class, "field3", "att1" },

				{ ComplexDomainType.class, "field0", "field0" },
				{ ComplexDomainType.class, "field1", "field1" },
				{ ComplexDomainType.class, "field2", "field2" },

		};
	}

	@Test(dataProvider = "fieldNameParams")
	public void shouldMapCsvFieldsToJavaFields(Class<?> type, String csvfield,
			String expectedJavaFieldName) {

		RichType<?> richType = RichType.of(type);
		RichField richField = richType.richFieldOf(csvfield);
		String actualJavaFieldName = richField.getField().getName();

		Assert.assertEquals(actualJavaFieldName, expectedJavaFieldName);
	}

}