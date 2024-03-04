/*
 * Copyright 2015-2022 the original author or authors.
 *
 * All rights reserved. This program and the accompanying materials are
 * made available under the terms of the Eclipse Public License v2.0 which
 * accompanies this distribution and is available at
 *
 * http://www.eclipse.org/legal/epl-v20.html
 */

package com.example.project;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CalculatorTests {


	@ParameterizedTest(name = "{0} lowcase to  {1} ")
	@CsvSource({"ABa#1/0,aba#1/0",
			"Null,null",
			" , "})
	void lowerTest(String test,String expectedResult) {
		assertEquals(expectedResult,MyTest.lowerCase(test));
	}

	@ParameterizedTest(name = "{0} trim to  {1} ")
	@CsvSource({
			"  asdewcx ascs ,asdewcx ascs"})
	void trimTest(String test, String expectedResult) {
		assertEquals(expectedResult,MyTest.trimToEmpty(test));
//		assertEquals(expectedResult, myTest.add(first, second),
//				() -> first + " + " + second + " should equal " + expectedResult);
	}
}
