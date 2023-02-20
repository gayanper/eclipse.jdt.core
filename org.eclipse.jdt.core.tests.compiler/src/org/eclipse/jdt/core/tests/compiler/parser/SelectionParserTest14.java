/*******************************************************************************
 * Copyright (c) 2023 Gayan Perera and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Gayan Perera - initial API and implementation
 *******************************************************************************/
package org.eclipse.jdt.core.tests.compiler.parser;

import org.eclipse.jdt.core.JavaModelException;

import junit.framework.Test;

public class SelectionParserTest14 extends AbstractSelectionTest {
	static {
		// TESTS_NUMBERS = new int[] { 1 };
		// TESTS_NAMES = new String[] { "test510339_007" };
	}

	public static Test suite() {
		return buildMinimalComplianceTestSuite(SelectionParserTest14.class, F_14);
	}

	public SelectionParserTest14(String testName) {
		super(testName);
	}

	public void testGH769_TypeSelectionInPatternMatchingInstanceOf() throws JavaModelException {
		String source = "public class TestBug {\n"
				+ "private static final void bugDemonstration() {\n"
				+ "	new Object() {\n"
				+ "		private void methodA(Object object) {\n"
				+ "			if (!(object instanceof Random varX))\n"
				+ "				return;\n"
				+ "		}\n"
				+ "\n"
				+ "		private void methodB(Object object) {\n"
				+ "			if (object instanceof String var1) {\n"
				+ "			}\n"
				+ "		}\n"
				+ "	};\n"
				+ "}\n";

		String selection = "String";
		String selectKey = "<SelectOnType:";
		String expectedCompletionNodeToString = selectKey + selection + ">";

		String completionIdentifier = "String";
		String expectedUnitDisplayString = "public class TestBug {\n"
				+ "private static final void bugDemonstration() {\n"
				+ "	new Object() {\n"
				+ "		private void methodA(Object object) {\n"
				+ "			if (!(object instanceof Random varX))\n"
				+ "				return;\n"
				+ "		}\n"
				+ "\n"
				+ "		private void methodB(Object object) {\n"
				+ "			if (object instanceof " + expectedCompletionNodeToString + " var1) {\n"
				+ "			}\n"
				+ "		}\n"
				+ "	};\n"
				+ "}\n";

		String expectedReplacedSource = "String";
		String testName = "TestBug.java";

		int selectionStart = source.lastIndexOf(selection);
		int selectionEnd = source.lastIndexOf(selection) + selection.length() - 1;

		checkMethodParse(
				source.toCharArray(),
				selectionStart,
				selectionEnd,
				expectedCompletionNodeToString,
				expectedUnitDisplayString,
				completionIdentifier,
				expectedReplacedSource,
				testName);
	}
}
