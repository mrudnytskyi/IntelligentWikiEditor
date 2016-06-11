/*
 * Copyright (C) 2016 Myroslav Rudnytskyi
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 */

package intelligent.wiki.editor.io.wiki.template_data;

import intelligent.wiki.editor.io_api.wiki.template_data.TemplateParameter;
import intelligent.wiki.editor.io_impl.wiki.template_data.TemplateParameterBuilder;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.*;

/**
 * @author Myroslav Rudnytskyi
 * @version 01.06.2016
 * @see TemplateParameterBuilder
 * @see TemplateParameter
 */
public class TemplateParameterTest {

	public static final String EMPTY_STRING = "";

	@Test
	public void testParameterWithName() throws Exception {
		// setup
		String expectName = "test name";
		// execute
		TemplateParameter actual = TemplateParameterBuilder.parameterWithName(expectName).build();
		// verify
		assertThat(actual, is(notNullValue()));
		assertEquals(actual.getName(), expectName);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testParameterWithNameNullArgument() throws Exception {
		TemplateParameterBuilder.parameterWithName(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testParameterWithNameEmptyArgument() throws Exception {
		TemplateParameterBuilder.parameterWithName(EMPTY_STRING);
	}

	@Test
	public void testWithDescription() throws Exception {
		// setup
		String expectDescription = "test description";
		// execute
		TemplateParameter actual = TemplateParameterBuilder.parameterWithName("test name")
				.withDescription(expectDescription).build();
		// verify
		assertThat(actual, is(notNullValue()));
		assertEquals(actual.getDescription(), expectDescription);
	}

	@Test
	public void testWithDescriptionWrongParameter() throws Exception {
		// setup
		// execute
		TemplateParameter actual = TemplateParameterBuilder.parameterWithName("test name")
				.withDescription(null).build();
		// verify
		assertThat(actual, is(notNullValue()));
		assertEquals(actual.getDescription(), EMPTY_STRING);
	}

	@Test
	public void testWithDefaultValue() throws Exception {
		// setup
		String expectDefaultValue = "test default";
		// execute
		TemplateParameter actual = TemplateParameterBuilder.parameterWithName("test name")
				.withDefaultValue(expectDefaultValue).build();
		// verify
		assertThat(actual, is(notNullValue()));
		assertEquals(actual.getDefault(), expectDefaultValue);
	}

	@Test
	public void testWithDefaultValueWrongParameter() throws Exception {
		// setup
		// execute
		TemplateParameter actual = TemplateParameterBuilder.parameterWithName("test name")
				.withDefaultValue(null).build();
		// verify
		assertThat(actual, is(notNullValue()));
		assertEquals(actual.getDefault(), EMPTY_STRING);
	}

	@Test
	public void testWithType() throws Exception {
		// setup
		String expectType = "unbalanced-wikitext";
		// execute
		TemplateParameter actual = TemplateParameterBuilder.parameterWithName("test name")
				.withType(expectType).build();
		// verify
		assertThat(actual, is(notNullValue()));
		assertEquals(actual.getType(), TemplateParameter.TemplateParameterType.UNBALANCED_WIKITEXT);
	}

	@Test
	public void testWithTypeWrongParameter() throws Exception {
		// setup
		// execute
		TemplateParameter actual = TemplateParameterBuilder.parameterWithName("test name")
				.withType(null).build();
		// verify
		assertThat(actual, is(notNullValue()));
		assertEquals(actual.getType(), TemplateParameter.TemplateParameterType.UNKNOWN);
	}

	@Test
	public void testWithRequiredFlag() throws Exception {
		// setup
		// execute
		TemplateParameter actual = TemplateParameterBuilder.parameterWithName("test name")
				.withRequiredFlag(true).build();
		// verify
		assertThat(actual, is(notNullValue()));
		assertTrue(actual.isRequired());
	}

	@Test
	public void testWithSuggestedFlag() throws Exception {
		// setup
		// execute
		TemplateParameter actual = TemplateParameterBuilder.parameterWithName("test name")
				.withSuggestedFlag(true).build();
		// verify
		assertThat(actual, is(notNullValue()));
		assertTrue(actual.isSuggested());
	}

	@Test
	public void testWithDeprecatedFlag() throws Exception {
		// setup
		// execute
		TemplateParameter actual = TemplateParameterBuilder.parameterWithName("test name")
				.withDeprecatedFlag(true).build();
		// verify
		assertThat(actual, is(notNullValue()));
		assertTrue(actual.isDeprecated());
	}
}