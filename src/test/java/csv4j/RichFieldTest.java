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

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class RichFieldTest {

	@DataProvider
	Object[][] setterParams() {
		return new Object[][] {

				{ SimpleDomainType.class, "field0", "setField0", int.class },
				{ SimpleDomainType.class, "field1", "setField1", String.class },
				{ SimpleDomainType.class, "field2", "setField2", double.class },

				{ AnnotatedDomainType.class, "field0", "setField0", int.class },
				{ AnnotatedDomainType.class, "att1", "setAtt1", String.class },
				{ AnnotatedDomainType.class, "att2", "setAtt2", double.class },

				{ ComplexDomainType.class, "field0", "setField0", MyInt.class },
				{ ComplexDomainType.class, "field1", "setField1", String.class },
				{ ComplexDomainType.class, "field2", "setField2", double.class },

		};
	}

	@Test(dataProvider = "setterParams")
	public void shouldRecognizeSetterMethods(Class<?> type, String fieldName,
			String setterName, Class<?> fieldType) throws NoSuchFieldException,
			SecurityException, NoSuchMethodException {

		Field field = type.getDeclaredField(fieldName);
		RichField richField = RichField.of(type, field);
		Method actualSetter = richField.getSetter();

		Method expectedSetter = type.getDeclaredMethod(setterName, fieldType);
		Assert.assertEquals(actualSetter, expectedSetter);
	}

	@DataProvider
	Object[][] setFieldParams() {
		return new Object[][] {

				{ new SimpleDomainType(), "field0", "5",
						SimpleDomainType.of(5, null, 0.0) },
				{ new SimpleDomainType(), "field1", "abc",
						SimpleDomainType.of(0, "abc", 0.0) },
				{ new SimpleDomainType(), "field2", "1.234",
						SimpleDomainType.of(0, null, 1.234) },

				{ new AnnotatedDomainType(), "field0", "5",
						AnnotatedDomainType.of(5, null, 0.0) },
				{ new AnnotatedDomainType(), "att1", "abc",
						AnnotatedDomainType.of(0, "abc", 0.0) },
				{ new AnnotatedDomainType(), "att2", "1.234",
						AnnotatedDomainType.of(0, null, 1.234) },

				{ new ComplexDomainType(), "field0", "5",
						ComplexDomainType.of(new MyInt(5), null, 0.0) },
				{ new ComplexDomainType(), "field1", "abc",
						ComplexDomainType.of(null, "abc", 0.0) },
				{ new ComplexDomainType(), "field2", "1.234",
						ComplexDomainType.of(null, null, 1.234) },

		};
	}

	@Test(dataProvider = "setFieldParams")
	public void shouldSetFieldProperly(Object object, String fieldName,
			String value, Object expected) throws NoSuchFieldException,
			SecurityException {

		Class<?> type = object.getClass();
		Field field = type.getDeclaredField(fieldName);
		RichField richField = RichField.of(type, field);
		richField.setField(object, value);

		Assert.assertEquals(object, expected);
	}

}