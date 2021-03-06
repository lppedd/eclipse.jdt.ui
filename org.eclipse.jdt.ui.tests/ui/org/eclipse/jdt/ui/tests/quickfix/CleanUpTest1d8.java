/*******************************************************************************
 * Copyright (c) 2020 IBM Corporation and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Fabrice TIERCELIN - initial API and implementation
 *******************************************************************************/
package org.eclipse.jdt.ui.tests.quickfix;

import static org.junit.Assert.assertNotEquals;

import java.util.Arrays;
import java.util.HashSet;

import org.junit.Rule;
import org.junit.Test;

import org.eclipse.core.runtime.CoreException;

import org.eclipse.jdt.core.IClasspathEntry;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;

import org.eclipse.jdt.internal.core.manipulation.CodeTemplateContextType;
import org.eclipse.jdt.internal.core.manipulation.StubUtility;
import org.eclipse.jdt.internal.corext.fix.CleanUpConstants;
import org.eclipse.jdt.internal.corext.fix.FixMessages;

import org.eclipse.jdt.ui.tests.core.rules.Java1d8ProjectTestSetup;
import org.eclipse.jdt.ui.tests.core.rules.ProjectTestSetup;

import org.eclipse.jdt.internal.ui.fix.MultiFixMessages;

public class CleanUpTest1d8 extends CleanUpTestCase {
	@Rule
	public ProjectTestSetup projectSetup= new Java1d8ProjectTestSetup();

	@Override
	public void setUp() throws Exception {
		super.setUp();
		StubUtility.setCodeTemplate(CodeTemplateContextType.OVERRIDECOMMENT_ID, "", null);
	}

	@Override
	protected IJavaProject getProject() {
		return projectSetup.getProject();
	}

	@Override
	protected IClasspathEntry[] getDefaultClasspath() throws CoreException {
		return projectSetup.getDefaultClasspath();
	}

	@Test
	public void testUseAtomicObject() throws Exception {
		IPackageFragment pack= fSourceFolder.createPackageFragment("test1", false, null);
		String input= "" //
				+ "package test1;\n" //
				+ "\n" //
				+ "import java.util.Date;\n" //
				+ "import java.util.function.Supplier;\n" //
				+ "\n" //
				+ "public class E {\n" //
				+ "    public static boolean useAtomicBoolean() {\n" //
				+ "        // Keep this comment\n" //
				+ "        boolean[] booleanRef= new boolean[1];\n" //
				+ "        // Keep this comment also\n" //
				+ "        Runnable runnable = () -> booleanRef[0] = true;\n" //
				+ "        runnable.run();\n" //
				+ "        // Keep this comment too\n" //
				+ "        return booleanRef[0];\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public static int useAtomicInteger() {\n" //
				+ "        // Keep this comment\n" //
				+ "        int[] intRef= new int[1];\n" //
				+ "        // Keep this comment also\n" //
				+ "        Runnable runnable = () -> intRef[0] = 42;\n" //
				+ "        runnable.run();\n" //
				+ "        // Keep this comment too\n" //
				+ "        return intRef[0];\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public static long useAtomicLong() {\n" //
				+ "        // Keep this comment\n" //
				+ "        long[] longRef= new long[1];\n" //
				+ "        // Keep this comment also\n" //
				+ "        Runnable runnable = () -> longRef[0] = 42;\n" //
				+ "        runnable.run();\n" //
				+ "        // Keep this comment too\n" //
				+ "        return longRef[0];\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public static Date useAtomicReference() {\n" //
				+ "        // Keep this comment\n" //
				+ "        Date[] dateRef= new Date[1];\n" //
				+ "        // Keep this comment also\n" //
				+ "        Runnable runnable = () -> dateRef[0] = new Date();\n" //
				+ "        runnable.run();\n" //
				+ "        // Keep this comment too\n" //
				+ "        return dateRef[0];\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public static Long useAtomicLongObject() {\n" //
				+ "        // Keep this comment\n" //
				+ "        Long[] longRef = new Long[1];\n" //
				+ "        // Keep this comment also\n" //
				+ "        Runnable runnable = () -> longRef[0] = Long.valueOf(0);\n" //
				+ "        runnable.run();\n" //
				+ "        // Keep this comment too\n" //
				+ "        return longRef[0];\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public static boolean useInitializedAtomicBoolean() {\n" //
				+ "        // Keep this comment\n" //
				+ "        boolean[] booleanRef= new boolean[] {true};\n" //
				+ "        // Keep this comment also\n" //
				+ "        Runnable runnable = () -> booleanRef[0] = true;\n" //
				+ "        runnable.run();\n" //
				+ "        // Keep this comment too\n" //
				+ "        return booleanRef[0];\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public static String useNonInitializedAtomicString() {\n" //
				+ "        // Keep this comment\n" //
				+ "        String[] stringRef= new String[] {null};\n" //
				+ "        // Keep this comment also\n" //
				+ "        Runnable runnable = () -> stringRef[0] = \"foo\";\n" //
				+ "        runnable.run();\n" //
				+ "        // Keep this comment too\n" //
				+ "        return stringRef[0];\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public static Date useAtomicReferenceInAnonymousClass() {\n" //
				+ "        // Keep this comment\n" //
				+ "        Date[] dateRef= new Date[1];\n" //
				+ "        // Keep this comment also\n" //
				+ "        Runnable runnable = new Runnable() {\n" //
				+ "            @Override\n" //
				+ "            public void run() {\n" //
				+ "                dateRef[0] = new Date();\n" //
				+ "            }\n" //
				+ "        };\n" //
				+ "        runnable.run();\n" //
				+ "        // Keep this comment too\n" //
				+ "        return dateRef[0];\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public static Date useAtomicReferenceOnExtraDimension() {\n" //
				+ "        // Keep this comment\n" //
				+ "        Date dateRef[]= new Date[1];\n" //
				+ "        // Keep this comment also\n" //
				+ "        Runnable runnable = () -> dateRef[0] = new Date();\n" //
				+ "        runnable.run();\n" //
				+ "        // Keep this comment too\n" //
				+ "        return dateRef[0];\n" //
				+ "    }\n" //
				+ "}\n";
		ICompilationUnit cu= pack.createCompilationUnit("E.java", input, false, null);

		enable(CleanUpConstants.ATOMIC_OBJECT);

		String output= "" //
				+ "package test1;\n" //
				+ "\n" //
				+ "import java.util.Date;\n" //
				+ "import java.util.concurrent.atomic.AtomicBoolean;\n" //
				+ "import java.util.concurrent.atomic.AtomicInteger;\n" //
				+ "import java.util.concurrent.atomic.AtomicLong;\n" //
				+ "import java.util.concurrent.atomic.AtomicReference;\n" //
				+ "import java.util.function.Supplier;\n" //
				+ "\n" //
				+ "public class E {\n" //
				+ "    public static boolean useAtomicBoolean() {\n" //
				+ "        // Keep this comment\n" //
				+ "        AtomicBoolean booleanRef= new AtomicBoolean();\n" //
				+ "        // Keep this comment also\n" //
				+ "        Runnable runnable = () -> booleanRef.set(true);\n" //
				+ "        runnable.run();\n" //
				+ "        // Keep this comment too\n" //
				+ "        return booleanRef.get();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public static int useAtomicInteger() {\n" //
				+ "        // Keep this comment\n" //
				+ "        AtomicInteger intRef= new AtomicInteger();\n" //
				+ "        // Keep this comment also\n" //
				+ "        Runnable runnable = () -> intRef.set(42);\n" //
				+ "        runnable.run();\n" //
				+ "        // Keep this comment too\n" //
				+ "        return intRef.get();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public static long useAtomicLong() {\n" //
				+ "        // Keep this comment\n" //
				+ "        AtomicLong longRef= new AtomicLong();\n" //
				+ "        // Keep this comment also\n" //
				+ "        Runnable runnable = () -> longRef.set(42);\n" //
				+ "        runnable.run();\n" //
				+ "        // Keep this comment too\n" //
				+ "        return longRef.get();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public static Date useAtomicReference() {\n" //
				+ "        // Keep this comment\n" //
				+ "        AtomicReference<Date> dateRef= new AtomicReference<>();\n" //
				+ "        // Keep this comment also\n" //
				+ "        Runnable runnable = () -> dateRef.set(new Date());\n" //
				+ "        runnable.run();\n" //
				+ "        // Keep this comment too\n" //
				+ "        return dateRef.get();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public static Long useAtomicLongObject() {\n" //
				+ "        // Keep this comment\n" //
				+ "        AtomicReference<Long> longRef = new AtomicReference<>();\n" //
				+ "        // Keep this comment also\n" //
				+ "        Runnable runnable = () -> longRef.set(Long.valueOf(0));\n" //
				+ "        runnable.run();\n" //
				+ "        // Keep this comment too\n" //
				+ "        return longRef.get();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public static boolean useInitializedAtomicBoolean() {\n" //
				+ "        // Keep this comment\n" //
				+ "        AtomicBoolean booleanRef= new AtomicBoolean(true);\n" //
				+ "        // Keep this comment also\n" //
				+ "        Runnable runnable = () -> booleanRef.set(true);\n" //
				+ "        runnable.run();\n" //
				+ "        // Keep this comment too\n" //
				+ "        return booleanRef.get();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public static String useNonInitializedAtomicString() {\n" //
				+ "        // Keep this comment\n" //
				+ "        AtomicReference<String> stringRef= new AtomicReference<>();\n" //
				+ "        // Keep this comment also\n" //
				+ "        Runnable runnable = () -> stringRef.set(\"foo\");\n" //
				+ "        runnable.run();\n" //
				+ "        // Keep this comment too\n" //
				+ "        return stringRef.get();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public static Date useAtomicReferenceInAnonymousClass() {\n" //
				+ "        // Keep this comment\n" //
				+ "        AtomicReference<Date> dateRef= new AtomicReference<>();\n" //
				+ "        // Keep this comment also\n" //
				+ "        Runnable runnable = new Runnable() {\n" //
				+ "            @Override\n" //
				+ "            public void run() {\n" //
				+ "                dateRef.set(new Date());\n" //
				+ "            }\n" //
				+ "        };\n" //
				+ "        runnable.run();\n" //
				+ "        // Keep this comment too\n" //
				+ "        return dateRef.get();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public static Date useAtomicReferenceOnExtraDimension() {\n" //
				+ "        // Keep this comment\n" //
				+ "        AtomicReference<Date> dateRef= new AtomicReference<>();\n" //
				+ "        // Keep this comment also\n" //
				+ "        Runnable runnable = () -> dateRef.set(new Date());\n" //
				+ "        runnable.run();\n" //
				+ "        // Keep this comment too\n" //
				+ "        return dateRef.get();\n" //
				+ "    }\n" //
				+ "}\n";

		assertGroupCategoryUsed(new ICompilationUnit[] { cu }, new HashSet<>(Arrays.asList(MultiFixMessages.CodeStyleCleanUp_AtomicObject_description)));
		assertRefactoringResultAsExpected(new ICompilationUnit[] { cu }, new String[] { output });
	}

	@Test
	public void testDoNotUseAtomicObject() throws Exception {
		IPackageFragment pack= fSourceFolder.createPackageFragment("test1", false, null);
		String sample= "" //
				+ "package test1;\n" //
				+ "\n" //
				+ "import java.util.Date;\n" //
				+ "import java.util.function.Supplier;\n" //
				+ "\n" //
				+ "public class E {\n" //
				+ "    public static int doNotRefactorIncrementalAssignment() {\n" //
				+ "        int[] intRef= new int[1];\n" //
				+ "        Runnable runnable = () -> intRef[0] += 42;\n" //
				+ "        runnable.run();\n" //
				+ "        return intRef[0];\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public static int doNotRefactorDecrementalAssignment() {\n" //
				+ "        int[] numberRef= new int[1];\n" //
				+ "        Runnable runnable = () -> numberRef[0] -= 42;\n" //
				+ "        runnable.run();\n" //
				+ "        return numberRef[0];\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public static short doNotRefactorShortArray() {\n" //
				+ "        short[] shortRef= new short[1];\n" //
				+ "        Runnable runnable = () -> shortRef[0] = 42;\n" //
				+ "        runnable.run();\n" //
				+ "        return shortRef[0];\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public static void doNotRefactorVoidArray() {\n" //
				+ "        Date[] dateRef= new Date[] {};\n" //
				+ "        Runnable runnable = () -> {\n" //
				+ "            dateRef[0] = new Date();\n" //
				+ "        };\n" //
				+ "        System.out.println(dateRef[0]);\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public static void doNotRefactorBadlyInitializedArray() {\n" //
				+ "        Date[] dateRef= new Date[] {new Date(), new Date()};\n" //
				+ "        Runnable runnable = () -> {\n" //
				+ "            dateRef[0] = new Date();\n" //
				+ "        };\n" //
				+ "        System.out.println(dateRef[0]);\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public static Date doNotTouchUnknownPurpose() {\n" //
				+ "        Date[] dateRef= new Date[1];\n" //
				+ "        dateRef[0] = new Date();\n" //
				+ "        return dateRef[0];\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public static void doNotTouchUnknownPurposeInLambda() {\n" //
				+ "        Runnable runnable = () -> {\n" //
				+ "            Date[] dateRef= new Date[1];\n" //
				+ "            dateRef[0] = new Date();\n" //
				+ "            System.out.println(dateRef[0]);\n" //
				+ "        };\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public static Date doNotRefactorGreatArray() {\n" //
				+ "        Date[] dateRef= new Date[2];\n" //
				+ "        Runnable runnable = () -> dateRef[0] = new Date();\n" //
				+ "        runnable.run();\n" //
				+ "        return dateRef[0];\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public static Date doNotRefactorEmptyArray() {\n" //
				+ "        Date[] dateRef= new Date[0];\n" //
				+ "        Runnable runnable = () -> dateRef[0] = new Date();\n" //
				+ "        runnable.run();\n" //
				+ "        return dateRef[0];\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public static Date doNotRefactorWrongIndex() {\n" //
				+ "        Date[] dateRef= new Date[1];\n" //
				+ "        Runnable runnable = () -> dateRef[1] = new Date();\n" //
				+ "        runnable.run();\n" //
				+ "        return dateRef[0];\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public static Date doNotRefactorAnotherWrongIndex() {\n" //
				+ "        Date[] dateRef= new Date[1];\n" //
				+ "        Runnable runnable = () -> dateRef[0] = new Date();\n" //
				+ "        runnable.run();\n" //
				+ "        return dateRef[1];\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public static Date doNotRefactorSeveralDimensionArray() {\n" //
				+ "        Date[][] dateRef= new Date[1][1];\n" //
				+ "        Runnable runnable = () -> dateRef[0][0] = new Date();\n" //
				+ "        runnable.run();\n" //
				+ "        return dateRef[0][0];\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public static Date[][] doNotRefactorSeveralDeclarations() {\n" //
				+ "        Date[][] dateRef= new Date[1][1], iAmHereToo= null;\n" //
				+ "        Runnable runnable = () -> dateRef[0][0] = new Date();\n" //
				+ "        runnable.run();\n" //
				+ "        System.out.println(dateRef[0]);\n" //
				+ "        return iAmHereToo;\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public static void doNotRefactorReturnedAssignment() {\n" //
				+ "        Date[][] dateRef= new Date[1][1];\n" //
				+ "        Supplier<Date> supplier = () -> dateRef[0][0] = new Date();\n" //
				+ "        supplier.get();\n" //
				+ "        System.out.println(dateRef[0]);\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public static void doNotRefactorReadAssignment() {\n" //
				+ "        Date[][] dateRef= new Date[1][1];\n" //
				+ "        Runnable runnable = () -> {\n" //
				+ "            if ((dateRef[0][0] = new Date()) != null)\n" //
				+ "                System.out.println(\"Filled\");\n" //
				+ "        };\n" //
				+ "        runnable.run();\n" //
				+ "        System.out.println(dateRef[0]);\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public static int doNotRefactorIncrementalRead() {\n" //
				+ "        int[] intRef= new int[1];\n" //
				+ "        Runnable runnable = () -> intRef[0] = 42;\n" //
				+ "        runnable.run();\n" //
				+ "        return intRef[0]++;\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public static int doNotRefactorDecrementalRead() {\n" //
				+ "        int[] intRef= new int[1];\n" //
				+ "        Runnable runnable = () -> intRef[0] = 42;\n" //
				+ "        runnable.run();\n" //
				+ "        return intRef[0]--;\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public static int doNotRefactorPreincrementalRead() {\n" //
				+ "        int[] intRef= new int[1];\n" //
				+ "        Runnable runnable = () -> intRef[0] = 42;\n" //
				+ "        runnable.run();\n" //
				+ "        return ++intRef[0];\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public static int doNotRefactorPredecrementalRead() {\n" //
				+ "        int[] intRef= new int[1];\n" //
				+ "        Runnable runnable = () -> intRef[0] = 42;\n" //
				+ "        runnable.run();\n" //
				+ "        return --intRef[0];\n" //
				+ "    }\n" //
				+ "}\n";
		ICompilationUnit cu= pack.createCompilationUnit("E.java", sample, false, null);

		enable(CleanUpConstants.ATOMIC_OBJECT);

		assertRefactoringHasNoChange(new ICompilationUnit[] { cu });
	}

	@Test
	public void testConvertToLambda01() throws Exception {
		IPackageFragment pack1= fSourceFolder.createPackageFragment("test", false, null);
		String sample= "" //
				+ "package test;\n" //
				+ "public class E {\n" //
				+ "    void foo(){\n" //
				+ "        // Keep this comment\n" //
				+ "        Runnable r = new Runnable() {\n" //
				+ "            @Override\n" //
				+ "            public void run() {\n" //
				+ "                System.out.println(\"do something\");\n" //
				+ "            }\n" //
				+ "        };\n" //
				+ "    };\n" //
				+ "}\n";
		String original= sample;
		ICompilationUnit cu1= pack1.createCompilationUnit("E.java", original, false, null);

		enable(CleanUpConstants.CONVERT_FUNCTIONAL_INTERFACES);
		enable(CleanUpConstants.USE_LAMBDA);

		sample= "" //
				+ "package test;\n" //
				+ "public class E {\n" //
				+ "    void foo(){\n" //
				+ "        // Keep this comment\n" //
				+ "        Runnable r = () -> System.out.println(\"do something\");\n" //
				+ "    };\n" //
				+ "}\n";
		String expected1= sample;

		assertRefactoringResultAsExpected(new ICompilationUnit[] { cu1 }, new String[] { expected1 });

		disable(CleanUpConstants.USE_LAMBDA);
		enable(CleanUpConstants.USE_ANONYMOUS_CLASS_CREATION);

		assertGroupCategoryUsed(new ICompilationUnit[] { cu1 }, new String[] { FixMessages.LambdaExpressionsFix_convert_to_anonymous_class_creation });
		assertRefactoringResultAsExpected(new ICompilationUnit[] { cu1 }, new String[] { original });
	}

	@Test
	public void testConvertToLambda02() throws Exception {
		IPackageFragment pack1= fSourceFolder.createPackageFragment("test", false, null);
		String sample= "" //
				+ "package test;\n" //
				+ "public class E {\n" //
				+ "    void foo(){\n" //
				+ "        Runnable r1 = new Runnable() {\n" //
				+ "            @Override\n" //
				+ "            public void run() {\n" //
				+ "                System.out.println(\"do something\");\n" //
				+ "            }\n" //
				+ "        };\n" //
				+ "        Runnable r2 = new Runnable() {\n" //
				+ "            @Override\n" //
				+ "            public void run() {\n" //
				+ "                System.out.println(\"do one thing\");\n" //
				+ "                System.out.println(\"do another thing\");\n" //
				+ "            }\n" //
				+ "        };\n" //
				+ "    };\n" //
				+ "}\n";
		String original= sample;
		ICompilationUnit cu1= pack1.createCompilationUnit("E.java", original, false, null);

		enable(CleanUpConstants.CONVERT_FUNCTIONAL_INTERFACES);
		enable(CleanUpConstants.USE_LAMBDA);

		sample= "" //
				+ "package test;\n" //
				+ "public class E {\n" //
				+ "    void foo(){\n" //
				+ "        Runnable r1 = () -> System.out.println(\"do something\");\n" //
				+ "        Runnable r2 = () -> {\n" //
				+ "            System.out.println(\"do one thing\");\n" //
				+ "            System.out.println(\"do another thing\");\n" //
				+ "        };\n" //
				+ "    };\n" //
				+ "}\n";
		String expected1= sample;

		assertGroupCategoryUsed(new ICompilationUnit[] { cu1 }, new String[] { FixMessages.LambdaExpressionsFix_convert_to_lambda_expression });
		assertRefactoringResultAsExpected(new ICompilationUnit[] { cu1 }, new String[] { expected1 });

		disable(CleanUpConstants.USE_LAMBDA);
		enable(CleanUpConstants.USE_ANONYMOUS_CLASS_CREATION);

		assertGroupCategoryUsed(new ICompilationUnit[] { cu1 }, new String[] { FixMessages.LambdaExpressionsFix_convert_to_anonymous_class_creation });
		assertRefactoringResultAsExpected(new ICompilationUnit[] { cu1 }, new String[] { original });
	}

	@Test
	public void testConvertToLambda03() throws Exception {
		IPackageFragment pack1= fSourceFolder.createPackageFragment("test", false, null);
		String sample= "" //
				+ "package test1;\n" //
				+ "import java.util.function.Supplier;\n" //
				+ "class E {\n" //
				+ "    Supplier<Supplier<String>> s= new Supplier<Supplier<String>>() {\n" //
				+ "        @Override\n" //
				+ "        public Supplier<String> get() {\n" //
				+ "            return new Supplier<String>() {\n" //
				+ "                @Override\n" //
				+ "                public String get() {\n" //
				+ "                    return \"a\";\n" //
				+ "                }\n" //
				+ "            };\n" //
				+ "        }\n" //
				+ "    };\n" //
				+ "}\n";
		String original= sample;
		ICompilationUnit cu1= pack1.createCompilationUnit("E.java", original, false, null);

		enable(CleanUpConstants.CONVERT_FUNCTIONAL_INTERFACES);
		enable(CleanUpConstants.USE_LAMBDA);

		sample= "" //
				+ "package test1;\n" //
				+ "import java.util.function.Supplier;\n" //
				+ "class E {\n" //
				+ "    Supplier<Supplier<String>> s= () -> () -> \"a\";\n" //
				+ "}\n";
		String expected1= sample;

		assertRefactoringResultAsExpected(new ICompilationUnit[] { cu1 }, new String[] { expected1 });

		disable(CleanUpConstants.USE_LAMBDA);
		enable(CleanUpConstants.USE_ANONYMOUS_CLASS_CREATION);

		assertRefactoringResultAsExpected(new ICompilationUnit[] { cu1 }, new String[] { original });
	}

	@Test
	public void testConvertToLambdaWithConstant() throws Exception {
		IPackageFragment pack1= fSourceFolder.createPackageFragment("test", false, null);
		String sample= "" //
				+ "package test;\n" //
				+ "\n" //
				+ "public class E {\n" //
				+ "    @FunctionalInterface\n" //
				+ "    interface FI1 extends Runnable {\n" //
				+ "        int CONSTANT_VALUE = 123;\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    void foo() {\n" //
				+ "        Runnable r = new FI1() {\n" //
				+ "            @Override\n" //
				+ "            public void run() {\n" //
				+ "                System.out.println(CONSTANT_VALUE);\n" //
				+ "            }\n" //
				+ "        };\n" //
				+ "    };\n" //
				+ "}\n";
		ICompilationUnit cu= pack1.createCompilationUnit("E.java", sample, false, null);

		enable(CleanUpConstants.CONVERT_FUNCTIONAL_INTERFACES);
		enable(CleanUpConstants.USE_LAMBDA);

		sample= "" //
				+ "package test;\n" //
				+ "\n" //
				+ "public class E {\n" //
				+ "    @FunctionalInterface\n" //
				+ "    interface FI1 extends Runnable {\n" //
				+ "        int CONSTANT_VALUE = 123;\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    void foo() {\n" //
				+ "        Runnable r = () -> System.out.println(FI1.CONSTANT_VALUE);\n" //
				+ "    };\n" //
				+ "}\n";
		String expected= sample;

		assertGroupCategoryUsed(new ICompilationUnit[] { cu }, new String[] { FixMessages.LambdaExpressionsFix_convert_to_lambda_expression });
		assertRefactoringResultAsExpected(new ICompilationUnit[] { cu }, new String[] { expected });
	}

	@Test
	public void testConvertToLambdaNestedWithImports() throws Exception {
		IPackageFragment pack1= fSourceFolder.createPackageFragment("test", false, null);
		String sample= "" //
				+ "package test;\n" //
				+ "import java.util.concurrent.Callable;\n" //
				+ "import java.util.concurrent.Executors;\n" //
				+ "public class E {\n" //
				+ "    void foo() {\n" //
				+ "        new Thread(new Runnable() {\n" //
				+ "            @Override\n" //
				+ "            public void run() {\n" //
				+ "                Executors.newSingleThreadExecutor().submit(new Callable<String>() {\n" //
				+ "                    @Override\n" //
				+ "                    public String call() throws Exception {\n" //
				+ "                        return \"hi\";\n" //
				+ "                    }\n" //
				+ "                });\n" //
				+ "            }\n" //
				+ "        });\n" //
				+ "    }\n" //
				+ "}\n";
		String original= sample;
		ICompilationUnit cu1= pack1.createCompilationUnit("E.java", original, false, null);

		enable(CleanUpConstants.CONVERT_FUNCTIONAL_INTERFACES);
		enable(CleanUpConstants.USE_LAMBDA);

		sample= "" //
				+ "package test;\n" //
				+ "import java.util.concurrent.Executors;\n" //
				+ "public class E {\n" //
				+ "    void foo() {\n" //
				+ "        new Thread(() -> Executors.newSingleThreadExecutor().submit(() -> \"hi\"));\n" //
				+ "    }\n" //
				+ "}\n";
		String expected1= sample;

		assertRefactoringResultAsExpected(new ICompilationUnit[] { cu1 }, new String[] { expected1 });

		disable(CleanUpConstants.USE_LAMBDA);
		enable(CleanUpConstants.USE_ANONYMOUS_CLASS_CREATION);

		assertRefactoringResultAsExpected(new ICompilationUnit[] { cu1 }, new String[] { original });
	}

	// fix for https://bugs.eclipse.org/bugs/show_bug.cgi?id=434507#c5
	@Test
	public void testConvertToLambdaAmbiguous01() throws Exception {
		IPackageFragment pack1= fSourceFolder.createPackageFragment("test", false, null);
		String sample= "" //
				+ "package test;\n" //
				+ "\n" //
				+ "interface ISuper {\n" //
				+ "    void foo(FI1 fi1);\n" //
				+ "}\n" //
				+ "\n" //
				+ "interface ISub extends ISuper {\n" //
				+ "    void foo(FI2 fi2);\n" //
				+ "}\n" //
				+ "\n" //
				+ "@FunctionalInterface\n" //
				+ "interface FI1 {\n" //
				+ "    void abc();\n" //
				+ "}\n" //
				+ "\n" //
				+ "@FunctionalInterface\n" //
				+ "interface FI2 {\n" //
				+ "    void xyz();\n" //
				+ "}\n" //
				+ "\n" //
				+ "class Test1 {\n" //
				+ "    private void test1() {\n" //
				+ "        f1().foo(new FI1() {\n" //
				+ "            @Override\n" //
				+ "            public void abc() {\n" //
				+ "                System.out.println();\n" //
				+ "            }\n" //
				+ "        });\n" //
				+ "\n" //
				+ "    }\n" //
				+ "    \n" //
				+ "    private ISub f1() {\n" //
				+ "        return null;\n" //
				+ "    }\n" //
				+ "}\n" //
				+ "\n" //
				+ "abstract class Test2 implements ISub {\n" //
				+ "    private void test2() {\n" //
				+ "        foo(new FI1() {\n" //
				+ "            @Override\n" //
				+ "            public void abc() {\n" //
				+ "                System.out.println();\n" //
				+ "            }\n" //
				+ "        });\n" //
				+ "    }\n" //
				+ "}\n" //
				+ "\n" //
				+ "class Test3 {\n" //
				+ "    void foo(FI1 fi1) {}\n" //
				+ "    void foo(FI2 fi2) {}\n" //
				+ "    private void test3() {\n" //
				+ "        foo(new FI1() {\n" //
				+ "            @Override\n" //
				+ "            public void abc() {\n" //
				+ "                System.out.println();\n" //
				+ "            }\n" //
				+ "        });\n" //
				+ "    }\n" //
				+ "}\n" //
				+ "\n" //
				+ "class Outer {\n" //
				+ "    class Test4 {\n" //
				+ "        {\n" //
				+ "            bar(0, new FI1() {\n" //
				+ "                @Override\n" //
				+ "                public void abc() {\n" //
				+ "                }\n" //
				+ "            });\n" //
				+ "        }\n" //
				+ "    }\n" //
				+ "    void bar(int i, FI1 fi1) {}\n" //
				+ "    void bar(int s, FI2 fi2) {}\n" //
				+ "}\n";
		String original= sample;
		ICompilationUnit cu1= pack1.createCompilationUnit("E.java", original, false, null);

		enable(CleanUpConstants.CONVERT_FUNCTIONAL_INTERFACES);
		enable(CleanUpConstants.USE_LAMBDA);

		sample= "" //
				+ "package test;\n" //
				+ "\n" //
				+ "interface ISuper {\n" //
				+ "    void foo(FI1 fi1);\n" //
				+ "}\n" //
				+ "\n" //
				+ "interface ISub extends ISuper {\n" //
				+ "    void foo(FI2 fi2);\n" //
				+ "}\n" //
				+ "\n" //
				+ "@FunctionalInterface\n" //
				+ "interface FI1 {\n" //
				+ "    void abc();\n" //
				+ "}\n" //
				+ "\n" //
				+ "@FunctionalInterface\n" //
				+ "interface FI2 {\n" //
				+ "    void xyz();\n" //
				+ "}\n" //
				+ "\n" //
				+ "class Test1 {\n" //
				+ "    private void test1() {\n" //
				+ "        f1().foo((FI1) () -> System.out.println());\n" //
				+ "\n" //
				+ "    }\n" //
				+ "    \n" //
				+ "    private ISub f1() {\n" //
				+ "        return null;\n" //
				+ "    }\n" //
				+ "}\n" //
				+ "\n" //
				+ "abstract class Test2 implements ISub {\n" //
				+ "    private void test2() {\n" //
				+ "        foo((FI1) () -> System.out.println());\n" //
				+ "    }\n" //
				+ "}\n" //
				+ "\n" //
				+ "class Test3 {\n" //
				+ "    void foo(FI1 fi1) {}\n" //
				+ "    void foo(FI2 fi2) {}\n" //
				+ "    private void test3() {\n" //
				+ "        foo((FI1) () -> System.out.println());\n" //
				+ "    }\n" //
				+ "}\n" //
				+ "\n" //
				+ "class Outer {\n" //
				+ "    class Test4 {\n" //
				+ "        {\n" //
				+ "            bar(0, (FI1) () -> {\n" //
				+ "            });\n" //
				+ "        }\n" //
				+ "    }\n" //
				+ "    void bar(int i, FI1 fi1) {}\n" //
				+ "    void bar(int s, FI2 fi2) {}\n" //
				+ "}\n";
		String expected1= sample;

		assertRefactoringResultAsExpected(new ICompilationUnit[] { cu1 }, new String[] { expected1 });

		disable(CleanUpConstants.USE_LAMBDA);
		enable(CleanUpConstants.USE_ANONYMOUS_CLASS_CREATION);

		assertRefactoringResultAsExpected(new ICompilationUnit[] { cu1 }, new String[] { original });
	}

	// fix for https://bugs.eclipse.org/bugs/show_bug.cgi?id=434507#c5
	@Test
	public void testConvertToLambdaAmbiguous02() throws Exception {
		IPackageFragment pack1= fSourceFolder.createPackageFragment("test", false, null);
		String sample= "" //
				+ "package test;\n" //
				+ "\n" //
				+ "@FunctionalInterface\n" //
				+ "interface FI1 {\n" //
				+ "    void abc();\n" //
				+ "}\n" //
				+ "\n" //
				+ "@FunctionalInterface\n" //
				+ "interface FI2 {\n" //
				+ "    void xyz();\n" //
				+ "}\n" //
				+ "\n" //
				+ "class Outer {\n" //
				+ "    void outer(FI1 fi1) {}\n" //
				+ "}\n" //
				+ "class OuterSub extends Outer {\n" //
				+ "    OuterSub() {\n" //
				+ "        super.outer(new FI1() {\n" //
				+ "            @Override\n" //
				+ "            public void abc() {\n" //
				+ "                System.out.println();\n" //
				+ "            }\n" //
				+ "        });\n" //
				+ "    }\n" //
				+ "    class Test1 {\n" //
				+ "        private void test1() {\n" //
				+ "            OuterSub.super.outer(new FI1() {\n" //
				+ "                @Override\n" //
				+ "                public void abc() {\n" //
				+ "                    System.out.println();\n" //
				+ "                }\n" //
				+ "            });\n" //
				+ "            OuterSub.this.outer(new FI1() {\n" //
				+ "                @Override\n" //
				+ "                public void abc() {\n" //
				+ "                    System.out.println();\n" //
				+ "                }\n" //
				+ "            });\n" //
				+ "            outer(new FI1() {\n" //
				+ "                @Override\n" //
				+ "                public void abc() {\n" //
				+ "                    System.out.println();\n" //
				+ "                }\n" //
				+ "            });\n" //
				+ "        }\n" //
				+ "    }\n" //
				+ "    @Override\n" //
				+ "    void outer(FI1 fi1) {}\n" //
				+ "    void outer(FI2 fi2) {}\n" //
				+ "}\n" //
				+ "\n" //
				+ "class OuterSub2 extends OuterSub {\n" //
				+ "    OuterSub2() {\n" //
				+ "        super.outer(new FI1() {\n" //
				+ "            @Override\n" //
				+ "            public void abc() {\n" //
				+ "                System.out.println();\n" //
				+ "            }\n" //
				+ "        });\n" //
				+ "    }\n" //
				+ "    class Test2 {\n" //
				+ "        private void test2() {\n" //
				+ "            OuterSub2.super.outer(new FI1() {\n" //
				+ "                @Override\n" //
				+ "                public void abc() {\n" //
				+ "                    System.out.println();\n" //
				+ "                }\n" //
				+ "            });\n" //
				+ "            OuterSub2.this.outer(new FI1() {\n" //
				+ "                @Override\n" //
				+ "                public void abc() {\n" //
				+ "                    System.out.println();\n" //
				+ "                }\n" //
				+ "            });\n" //
				+ "            outer(new FI1() {\n" //
				+ "                @Override\n" //
				+ "                public void abc() {\n" //
				+ "                    System.out.println();\n" //
				+ "                }\n" //
				+ "            });\n" //
				+ "        }\n" //
				+ "    }\n" //
				+ "}\n";
		String original= sample;
		ICompilationUnit cu1= pack1.createCompilationUnit("E.java", original, false, null);

		enable(CleanUpConstants.CONVERT_FUNCTIONAL_INTERFACES);
		enable(CleanUpConstants.USE_LAMBDA);

		sample= "" //
				+ "package test;\n" //
				+ "\n" //
				+ "@FunctionalInterface\n" //
				+ "interface FI1 {\n" //
				+ "    void abc();\n" //
				+ "}\n" //
				+ "\n" //
				+ "@FunctionalInterface\n" //
				+ "interface FI2 {\n" //
				+ "    void xyz();\n" //
				+ "}\n" //
				+ "\n" //
				+ "class Outer {\n" //
				+ "    void outer(FI1 fi1) {}\n" //
				+ "}\n" //
				+ "class OuterSub extends Outer {\n" //
				+ "    OuterSub() {\n" //
				+ "        super.outer(() -> System.out.println());\n" //
				+ "    }\n" //
				+ "    class Test1 {\n" //
				+ "        private void test1() {\n" //
				+ "            OuterSub.super.outer(() -> System.out.println());\n" //
				+ "            OuterSub.this.outer((FI1) () -> System.out.println());\n" //
				+ "            outer((FI1) () -> System.out.println());\n" //
				+ "        }\n" //
				+ "    }\n" //
				+ "    @Override\n" //
				+ "    void outer(FI1 fi1) {}\n" //
				+ "    void outer(FI2 fi2) {}\n" //
				+ "}\n" //
				+ "\n" //
				+ "class OuterSub2 extends OuterSub {\n" //
				+ "    OuterSub2() {\n" //
				+ "        super.outer((FI1) () -> System.out.println());\n" //
				+ "    }\n" //
				+ "    class Test2 {\n" //
				+ "        private void test2() {\n" //
				+ "            OuterSub2.super.outer((FI1) () -> System.out.println());\n" //
				+ "            OuterSub2.this.outer((FI1) () -> System.out.println());\n" //
				+ "            outer((FI1) () -> System.out.println());\n" //
				+ "        }\n" //
				+ "    }\n" //
				+ "}\n";
		String expected1= sample;

		assertRefactoringResultAsExpected(new ICompilationUnit[] { cu1 }, new String[] { expected1 });

		disable(CleanUpConstants.USE_LAMBDA);
		enable(CleanUpConstants.USE_ANONYMOUS_CLASS_CREATION);

		assertRefactoringResultAsExpected(new ICompilationUnit[] { cu1 }, new String[] { original });
	}

	// fix for https://bugs.eclipse.org/bugs/show_bug.cgi?id=434507#c2
	@Test
	public void testConvertToLambdaAmbiguous03() throws Exception {
		IPackageFragment pack1= fSourceFolder.createPackageFragment("test", false, null);
		String sample= "" //
				+ "package test;\n" //
				+ "public interface E {\n" //
				+ "    default void m() {\n" //
				+ "        bar(0, new FI() {\n" //
				+ "            @Override\n" //
				+ "            public int foo(int x) {\n" //
				+ "                return x++;\n" //
				+ "            }\n" //
				+ "        });\n" //
				+ "        baz(0, new ZI() {\n" //
				+ "            @Override\n" //
				+ "            public int zoo() {\n" //
				+ "                return 1;\n" //
				+ "            }\n" //
				+ "        });\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    void bar(int i, FI fi);\n" //
				+ "    void bar(int i, FV fv);\n" //
				+ "\n" //
				+ "    void baz(int i, ZI zi);\n" //
				+ "    void baz(int i, ZV zv);\n" //
				+ "}\n" //
				+ "\n" //
				+ "@FunctionalInterface interface FI { int  foo(int a); }\n" //
				+ "@FunctionalInterface interface FV { void foo(int a); }\n" //
				+ "\n" //
				+ "@FunctionalInterface interface ZI { int  zoo(); }\n" //
				+ "@FunctionalInterface interface ZV { void zoo(); }\n";
		String original= sample;
		ICompilationUnit cu1= pack1.createCompilationUnit("E.java", original, false, null);

		enable(CleanUpConstants.CONVERT_FUNCTIONAL_INTERFACES);
		enable(CleanUpConstants.USE_LAMBDA);

		sample= "" //
				+ "package test;\n" //
				+ "public interface E {\n" //
				+ "    default void m() {\n" //
				+ "        bar(0, (FI) x -> x++);\n" //
				+ "        baz(0, () -> 1);\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    void bar(int i, FI fi);\n" //
				+ "    void bar(int i, FV fv);\n" //
				+ "\n" //
				+ "    void baz(int i, ZI zi);\n" //
				+ "    void baz(int i, ZV zv);\n" //
				+ "}\n" //
				+ "\n" //
				+ "@FunctionalInterface interface FI { int  foo(int a); }\n" //
				+ "@FunctionalInterface interface FV { void foo(int a); }\n" //
				+ "\n" //
				+ "@FunctionalInterface interface ZI { int  zoo(); }\n" //
				+ "@FunctionalInterface interface ZV { void zoo(); }\n";
		String expected1= sample;

		assertRefactoringResultAsExpected(new ICompilationUnit[] { cu1 }, new String[] { expected1 });

		disable(CleanUpConstants.USE_LAMBDA);
		enable(CleanUpConstants.USE_ANONYMOUS_CLASS_CREATION);

		assertRefactoringResultAsExpected(new ICompilationUnit[] { cu1 }, new String[] { original });
	}

	@Test
	public void testConvertToLambdaConflictingNames() throws Exception {
		IPackageFragment pack1= fSourceFolder.createPackageFragment("test", false, null);
		String sample= "" //
				+ "package test1;\n" //
				+ "\n" //
				+ "interface FI {\n" //
				+ "    void run(int x);\n" //
				+ "}\n" //
				+ "\n" //
				+ "public class Test {\n" //
				+ "    {\n" //
				+ "        int e;\n" //
				+ "        FI fi = new FI() {\n" //
				+ "            @Override\n" //
				+ "            public void run(int e) {\n" //
				+ "                class C1 {\n" //
				+ "                    void init1() {\n" //
				+ "                        m(new FI() {\n" //
				+ "                            @Override\n" //
				+ "                            public void run(int e) {\n" //
				+ "                                FI fi = new FI() {\n" //
				+ "                                    @Override\n" //
				+ "                                    public void run(int e) {\n" //
				+ "                                        FI fi = new FI() {\n" //
				+ "                                            @Override\n" //
				+ "                                            public void run(int e) {\n" //
				+ "                                                return;\n" //
				+ "                                            }\n" //
				+ "                                        };\n" //
				+ "                                    }\n" //
				+ "                                };\n" //
				+ "                            }\n" //
				+ "                        });\n" //
				+ "                    }\n" //
				+ "\n" //
				+ "                    void init2() {\n" //
				+ "                        m(new FI() {\n" //
				+ "                            @Override\n" //
				+ "                            public void run(int e) {\n" //
				+ "                                new FI() {\n" //
				+ "                                    @Override\n" //
				+ "                                    public void run(int e3) {\n" //
				+ "                                        FI fi = new FI() {\n" //
				+ "                                            @Override\n" //
				+ "                                            public void run(int e) {\n" //
				+ "                                                return;\n" //
				+ "                                            }\n" //
				+ "                                        };\n" //
				+ "                                    }\n" //
				+ "                                };\n" //
				+ "                            }\n" //
				+ "                        });\n" //
				+ "                    }\n" //
				+ "                }\n" //
				+ "            }\n" //
				+ "        };\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    void m(FI fi) {\n" //
				+ "    };\n" //
				+ "}\n";
		String original= sample;
		ICompilationUnit cu1= pack1.createCompilationUnit("Test.java", original, false, null);

		enable(CleanUpConstants.CONVERT_FUNCTIONAL_INTERFACES);
		enable(CleanUpConstants.USE_LAMBDA);

		sample= "" //
				+ "package test1;\n" //
				+ "\n" //
				+ "interface FI {\n" //
				+ "    void run(int x);\n" //
				+ "}\n" //
				+ "\n" //
				+ "public class Test {\n" //
				+ "    {\n" //
				+ "        int e;\n" //
				+ "        FI fi = e4 -> {\n" //
				+ "            class C1 {\n" //
				+ "                void init1() {\n" //
				+ "                    m(e3 -> {\n" //
				+ "                        FI fi2 = e2 -> {\n" //
				+ "                            FI fi1 = e1 -> {\n" //
				+ "                                return;\n" //
				+ "                            };\n" //
				+ "                        };\n" //
				+ "                    });\n" //
				+ "                }\n" //
				+ "\n" //
				+ "                void init2() {\n" //
				+ "                    m(e2 -> new FI() {\n" //
				+ "                        @Override\n" //
				+ "                        public void run(int e3) {\n" //
				+ "                            FI fi = e1 -> {\n" //
				+ "                                return;\n" //
				+ "                            };\n" //
				+ "                        }\n" //
				+ "                    });\n" //
				+ "                }\n" //
				+ "            }\n" //
				+ "        };\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    void m(FI fi) {\n" //
				+ "    };\n" //
				+ "}\n";
		String expected1= sample;

		assertRefactoringResultAsExpected(new ICompilationUnit[] { cu1 }, new String[] { expected1 });
	}

	@Test
	public void testConvertToLambdaWithMethodAnnotations() throws Exception {
		IPackageFragment pack1= fSourceFolder.createPackageFragment("test", false, null);
		String sample= "" //
				+ "package test;\n" //
				+ "public class C1 {\n" //
				+ "    Runnable r1 = new Runnable() {\n" //
				+ "        @Override @A @Deprecated\n" //
				+ "        public void run() {\n" //
				+ "        }\n" //
				+ "    };\n" //
				+ "    Runnable r2 = new Runnable() {\n" //
				+ "        @Override @Deprecated\n" //
				+ "        public void run() {\n" //
				+ "        }\n" //
				+ "    };\n" //
				+ "}\n" //
				+ "@interface A {}\n";
		String original= sample;
		ICompilationUnit cu1= pack1.createCompilationUnit("C1.java", original, false, null);

		enable(CleanUpConstants.CONVERT_FUNCTIONAL_INTERFACES);
		enable(CleanUpConstants.USE_LAMBDA);

		sample= "" //
				+ "package test;\n" //
				+ "public class C1 {\n" //
				+ "    Runnable r1 = new Runnable() {\n" //
				+ "        @Override @A @Deprecated\n" //
				+ "        public void run() {\n" //
				+ "        }\n" //
				+ "    };\n" //
				+ "    Runnable r2 = () -> {\n" //
				+ "    };\n" //
				+ "}\n" //
				+ "@interface A {}\n";
		String expected1= sample;

		assertRefactoringResultAsExpected(new ICompilationUnit[] { cu1 }, new String[] { expected1 });
	}

	@Test
	public void testConvertToAnonymousWithWildcards() throws Exception {
		IPackageFragment pack1= fSourceFolder.createPackageFragment("test", false, null);
		String sample= "" //
				+ "package test;\n" //
				+ "import java.util.*;\n" //
				+ "public class E {\n" //
				+ "    void foo(Integer[] ints){\n" //
				+ "        Arrays.sort(ints, (i1, i2) -> i1 - i2);\n" //
				+ "        Comparator<?> cw = (w1, w2) -> 0;\n" //
				+ "        Comparator cr = (r1, r2) -> 0;\n" //
				+ "        Comparator<? extends Number> ce = (n1, n2) -> -0;\n" //
				+ "    };\n" //
				+ "}\n";
		String original= sample;
		ICompilationUnit cu1= pack1.createCompilationUnit("E.java", original, false, null);

		enable(CleanUpConstants.CONVERT_FUNCTIONAL_INTERFACES);
		enable(CleanUpConstants.USE_ANONYMOUS_CLASS_CREATION);

		sample= "" //
				+ "package test;\n" //
				+ "import java.util.*;\n" //
				+ "public class E {\n" //
				+ "    void foo(Integer[] ints){\n" //
				+ "        Arrays.sort(ints, new Comparator<Integer>() {\n" //
				+ "            @Override\n" //
				+ "            public int compare(Integer i1, Integer i2) {\n" //
				+ "                return i1 - i2;\n" //
				+ "            }\n" //
				+ "        });\n" //
				+ "        Comparator<?> cw = new Comparator<Object>() {\n" //
				+ "            @Override\n" //
				+ "            public int compare(Object w1, Object w2) {\n" //
				+ "                return 0;\n" //
				+ "            }\n" //
				+ "        };\n" //
				+ "        Comparator cr = new Comparator() {\n" //
				+ "            @Override\n" //
				+ "            public int compare(Object r1, Object r2) {\n" //
				+ "                return 0;\n" //
				+ "            }\n" //
				+ "        };\n" //
				+ "        Comparator<? extends Number> ce = new Comparator<Number>() {\n" //
				+ "            @Override\n" //
				+ "            public int compare(Number n1, Number n2) {\n" //
				+ "                return -0;\n" //
				+ "            }\n" //
				+ "        };\n" //
				+ "    };\n" //
				+ "}\n";
		String expected1= sample;

		assertRefactoringResultAsExpected(new ICompilationUnit[] { cu1 }, new String[] { expected1 });

		disable(CleanUpConstants.USE_ANONYMOUS_CLASS_CREATION);
		enable(CleanUpConstants.USE_LAMBDA);

		assertRefactoringResultAsExpected(new ICompilationUnit[] { cu1 }, new String[] { original });
	}

	@Test
	public void testConvertToAnonymousWithWildcards1() throws Exception {
		IPackageFragment pack1= fSourceFolder.createPackageFragment("test", false, null);
		String sample= "" //
				+ "package test;\n" //
				+ "\n" //
				+ "interface I<M> {\n" //
				+ "    M run(M x);\n" //
				+ "}\n" //
				+ "\n" //
				+ "class Test {\n" //
				+ "    I<?> li = s -> null;\n" //
				+ "}\n";
		String original= sample;
		ICompilationUnit cu1= pack1.createCompilationUnit("Test.java", original, false, null);

		enable(CleanUpConstants.CONVERT_FUNCTIONAL_INTERFACES);
		enable(CleanUpConstants.USE_ANONYMOUS_CLASS_CREATION);

		sample= "" //
				+ "package test;\n" //
				+ "\n" //
				+ "interface I<M> {\n" //
				+ "    M run(M x);\n" //
				+ "}\n" //
				+ "\n" //
				+ "class Test {\n" //
				+ "    I<?> li = new I<Object>() {\n" //
				+ "        @Override\n" //
				+ "        public Object run(Object s) {\n" //
				+ "            return null;\n" //
				+ "        }\n" //
				+ "    };\n" //
				+ "}\n";
		String expected1= sample;

		assertRefactoringResultAsExpected(new ICompilationUnit[] { cu1 }, new String[] { expected1 });

		disable(CleanUpConstants.USE_ANONYMOUS_CLASS_CREATION);
		enable(CleanUpConstants.USE_LAMBDA);

		assertRefactoringResultAsExpected(new ICompilationUnit[] { cu1 }, new String[] { original });
	}

	@Test
	public void testConvertToAnonymousWithJoinedSAM() throws Exception {
		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=428526#c1 and #c6
		IPackageFragment pack1= fSourceFolder.createPackageFragment("test", false, null);
		String sample= "" //
				+ "package test;\n" //
				+ "\n" //
				+ "interface Foo<T, N extends Number> {\n" //
				+ "    void m(T t);\n" //
				+ "    void m(N n);\n" //
				+ "}\n" //
				+ "interface Baz extends Foo<Integer, Integer> {}\n" //
				+ "class Test {\n" //
				+ "    Baz baz = x -> { return; };\n" //
				+ "}\n";
		String original= sample;
		ICompilationUnit cu1= pack1.createCompilationUnit("Test.java", original, false, null);

		enable(CleanUpConstants.CONVERT_FUNCTIONAL_INTERFACES);
		enable(CleanUpConstants.USE_ANONYMOUS_CLASS_CREATION);

		sample= "" //
				+ "package test;\n" //
				+ "\n" //
				+ "interface Foo<T, N extends Number> {\n" //
				+ "    void m(T t);\n" //
				+ "    void m(N n);\n" //
				+ "}\n" //
				+ "interface Baz extends Foo<Integer, Integer> {}\n" //
				+ "class Test {\n" //
				+ "    Baz baz = new Baz() {\n" //
				+ "        @Override\n" //
				+ "        public void m(Integer x) { return; }\n" //
				+ "    };\n" //
				+ "}\n";
		String expected1= sample;

		assertRefactoringResultAsExpected(new ICompilationUnit[] { cu1 }, new String[] { expected1 });

		disable(CleanUpConstants.USE_ANONYMOUS_CLASS_CREATION);
		enable(CleanUpConstants.USE_LAMBDA);

		assertRefactoringResultAsExpected(new ICompilationUnit[] { cu1 }, new String[] { original });
	}

	@Test
	public void testConvertToLambdaWithNonFunctionalTargetType() throws Exception {
		// https://bugs.eclipse.org/bugs/show_bug.cgi?id=468457
		IPackageFragment pack1= fSourceFolder.createPackageFragment("test", false, null);
		String sample= "" //
				+ "package test;\n" //
				+ "\n" //
				+ "public class Snippet {\n" //
				+ "    void test(Interface context) {\n" //
				+ "        context.set(\"bar\", new Runnable() {\n" //
				+ "            @Override\n" //
				+ "            public void run() {}\n" //
				+ "        });\n" //
				+ "        \n" //
				+ "    }    \n" //
				+ "}\n" //
				+ "\n" //
				+ "interface Interface {\n" //
				+ "    public void set(String name, Object value);\n" //
				+ "}\n";
		String original= sample;
		ICompilationUnit cu1= pack1.createCompilationUnit("Snippet.java", original, false, null);

		enable(CleanUpConstants.CONVERT_FUNCTIONAL_INTERFACES);
		enable(CleanUpConstants.USE_LAMBDA);

		sample= "" //
				+ "package test;\n" //
				+ "\n" //
				+ "public class Snippet {\n" //
				+ "    void test(Interface context) {\n" //
				+ "        context.set(\"bar\", (Runnable) () -> {});\n" //
				+ "        \n" //
				+ "    }    \n" //
				+ "}\n" //
				+ "\n" //
				+ "interface Interface {\n" //
				+ "    public void set(String name, Object value);\n" //
				+ "}\n";
		String expected1= sample;

		assertRefactoringResultAsExpected(new ICompilationUnit[] { cu1 }, new String[] { expected1 });

		enable(CleanUpConstants.USE_ANONYMOUS_CLASS_CREATION);
		disable(CleanUpConstants.USE_LAMBDA);

		assertRefactoringResultAsExpected(new ICompilationUnit[] { cu1 }, new String[] { original });
	}

	@Test
	public void testConvertToLambdaWithSynchronizedOrStrictfp() throws Exception {
		IPackageFragment pack1= fSourceFolder.createPackageFragment("test", false, null);
		String sample= "" //
				+ "package test;\n" //
				+ "public class C1 {\n" //
				+ "    Runnable run1 = new Runnable() {\n" //
				+ "        @Override\n" //
				+ "        public synchronized void run() {\n" //
				+ "        }\n" //
				+ "    };\n" //
				+ "    Runnable run2 = new Runnable() {\n" //
				+ "        @Override\n" //
				+ "        public strictfp void run() {\n" //
				+ "        }\n" //
				+ "    };\n" //
				+ "}\n";
		String original= sample;
		ICompilationUnit cu= pack1.createCompilationUnit("C1.java", original, false, null);

		enable(CleanUpConstants.CONVERT_FUNCTIONAL_INTERFACES);
		enable(CleanUpConstants.USE_LAMBDA);

		assertRefactoringHasNoChange(new ICompilationUnit[] { cu });
	}

	// fix for https://bugs.eclipse.org/bugs/show_bug.cgi?id=560018
	@Test
	public void testConvertToLambdaInFieldInitializerWithFinalFieldReference() throws Exception {
		IPackageFragment pack1= fSourceFolder.createPackageFragment("test", false, null);
		String sample= ""
				+ "package test;\n"
				+ "\n"
				+ "public class C1 {\n"
				+ "    final String s;\n"
				+ "\n"
				+ "    Runnable run1 = new Runnable() {\n"
				+ "        @Override\n"
				+ "        public void run() {\n"
				+ "            System.out.println(s);\n"
				+ "        }\n"
				+ "    };\n"
				+ "\n"
				+ "    public C1() {\n"
				+ "        s = \"abc\";\n"
				+ "    };\n"
				+ "}\n";
		ICompilationUnit cu= pack1.createCompilationUnit("C1.java", sample, false, null);

		enable(CleanUpConstants.CONVERT_FUNCTIONAL_INTERFACES);
		enable(CleanUpConstants.USE_LAMBDA);

		assertRefactoringHasNoChange(new ICompilationUnit[] { cu });
	}

	// fix for https://bugs.eclipse.org/bugs/show_bug.cgi?id=560018
	@Test
	public void testConvertToLambdaInFieldInitializerWithFinalFieldReference2() throws Exception {
		IPackageFragment pack1= fSourceFolder.createPackageFragment("test", false, null);
		String sample= ""
				+ "package test;\n"
				+ "public class C1 {\n"
				+ "    final String s = \"abc\";\n"
				+ "    Runnable run1 = new Runnable() {\n"
				+ "        @Override\n"
				+ "        public void run() {\n"
				+ "            System.out.println(s);\n"
				+ "        }\n"
				+ "    };\n"
				+ "}\n";
		ICompilationUnit cu= pack1.createCompilationUnit("C1.java", sample, false, null);

		enable(CleanUpConstants.CONVERT_FUNCTIONAL_INTERFACES);
		enable(CleanUpConstants.USE_LAMBDA);

		String expected1= ""
				+ "package test;\n"
				+ "public class C1 {\n"
				+ "    final String s = \"abc\";\n"
				+ "    Runnable run1 = () -> System.out.println(s);\n"
				+ "}\n";
		assertRefactoringResultAsExpected(new ICompilationUnit[] { cu }, new String[] { expected1 });
	}

	@Test
	public void testConvertToLambdaAndQualifyNextField() throws Exception {
		IPackageFragment pack1= fSourceFolder.createPackageFragment("test", false, null);
		String sample= ""
				+ "package test;\n"
				+ "\n"
				+ "public class C1 {\n"
				+ "    static final String previousField = \"abc\";\n"
				+ "\n"
				+ "    Runnable run1 = new Runnable() {\n"
				+ "        @Override\n"
				+ "        public void run() {\n"
				+ "            System.out.println(previousField + instanceField + classField + getString());\n"
				+ "        }\n"
				+ "    };\n"
				+ "\n"
				+ "    static final String classField = \"abc\";\n"
				+ "    final String instanceField = \"abc\";\n"
				+ "    public String getString() {\n"
				+ "        return \"\";\n"
				+ "    }\n"
				+ "}\n";
		ICompilationUnit cu= pack1.createCompilationUnit("C1.java", sample, false, null);

		enable(CleanUpConstants.CONVERT_FUNCTIONAL_INTERFACES);
		enable(CleanUpConstants.USE_LAMBDA);

		String expected= ""
				+ "package test;\n"
				+ "\n"
				+ "public class C1 {\n"
				+ "    static final String previousField = \"abc\";\n"
				+ "\n"
				+ "    Runnable run1 = () -> System.out.println(previousField + this.instanceField + C1.classField + getString());\n"
				+ "\n"
				+ "    static final String classField = \"abc\";\n"
				+ "    final String instanceField = \"abc\";\n"
				+ "    public String getString() {\n"
				+ "        return \"\";\n"
				+ "    }\n"
				+ "}\n";
		assertRefactoringResultAsExpected(new ICompilationUnit[] { cu }, new String[] { expected });
	}

	@Test
	public void testConvertToLambdaWithQualifiedField() throws Exception {
		IPackageFragment pack1= fSourceFolder.createPackageFragment("test", false, null);
		String sample= ""
				+ "package test;\n"
				+ "\n"
				+ "public class C1 {\n"
				+ "    static final String previousField = \"abc\";\n"
				+ "\n"
				+ "    Runnable run1 = new Runnable() {\n"
				+ "        @Override\n"
				+ "        public void run() {\n"
				+ "            System.out.println(C1.previousField + C1.this.instanceField + C1.classField + C1.this.getString());\n"
				+ "        }\n"
				+ "    };\n"
				+ "\n"
				+ "    static final String classField = \"def\";\n"
				+ "    final String instanceField = \"abc\";\n"
				+ "    public String getString() {\n"
				+ "        return \"\";\n"
				+ "    }\n"
				+ "}\n";
		ICompilationUnit cu= pack1.createCompilationUnit("C1.java", sample, false, null);

		enable(CleanUpConstants.CONVERT_FUNCTIONAL_INTERFACES);
		enable(CleanUpConstants.USE_LAMBDA);

		String expected= ""
				+ "package test;\n"
				+ "\n"
				+ "public class C1 {\n"
				+ "    static final String previousField = \"abc\";\n"
				+ "\n"
				+ "    Runnable run1 = () -> System.out.println(C1.previousField + this.instanceField + C1.classField + this.getString());\n"
				+ "\n"
				+ "    static final String classField = \"def\";\n"
				+ "    final String instanceField = \"abc\";\n"
				+ "    public String getString() {\n"
				+ "        return \"\";\n"
				+ "    }\n"
				+ "}\n";
		assertRefactoringResultAsExpected(new ICompilationUnit[] { cu }, new String[] { expected });
	}

	@Test
	public void testDoNotRefactorWithExpressions() throws Exception {
		IPackageFragment pack1= fSourceFolder.createPackageFragment("test1", false, null);
		String sample= "" //
				+ "package test1;\n" //
				+ "\n" //
				+ "import java.util.Date;\n" //
				+ "import java.util.function.Supplier;\n" //
				+ "\n" //
				+ "public class E {\n" //
				+ "    public Supplier<Date> doNotRefactorWithAnonymousBody() {\n" //
				+ "        return () -> new Date() {\n" //
				+ "            @Override\n" //
				+ "            public String toString() {\n" //
				+ "                return \"foo\";\n" //
				+ "            }\n" //
				+ "        };\n" //
				+ "    }\n" //
				+ "}\n";
		ICompilationUnit cu= pack1.createCompilationUnit("E.java", sample, false, null);

		enable(CleanUpConstants.SIMPLIFY_LAMBDA_EXPRESSION_AND_METHOD_REF);

		assertRefactoringHasNoChange(new ICompilationUnit[] { cu });
	}

	@Test
	public void testSimplifyLambdaExpression() throws Exception {
		IPackageFragment pack1= fSourceFolder.createPackageFragment("test1", false, null);
		String sample= "" //
				+ "package test1;\n" //
				+ "\n" //
				+ "import static java.util.Calendar.getInstance;\n" //
				+ "import static java.util.Calendar.getAvailableLocales;\n" //
				+ "\n" //
				+ "import java.time.Instant;\n" //
				+ "import java.util.ArrayList;\n" //
				+ "import java.util.Calendar;\n" //
				+ "import java.util.Date;\n" //
				+ "import java.util.Locale;\n" //
				+ "import java.util.Vector;\n" //
				+ "import java.util.function.BiFunction;\n" //
				+ "import java.util.function.Function;\n" //
				+ "import java.util.function.Supplier;\n" //
				+ "\n" //
				+ "public class E extends Date {\n" //
				+ "    public String changeableText = \"foo\";\n" //
				+ "\n" //
				+ "    public Function<String, String> removeParentheses() {\n" //
				+ "        return (someString) -> someString.trim().toLowerCase();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public Function<String, String> doNotRemoveParenthesesWithSingleVariableDeclaration() {\n" //
				+ "        return (String someString) -> someString.trim().toLowerCase();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public BiFunction<String, String, Integer> doNotRemoveParenthesesWithTwoParameters() {\n" //
				+ "        return (someString, anotherString) -> someString.trim().compareTo(anotherString.trim());\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public Supplier<Boolean> doNotRemoveParenthesesWithNoParameter() {\n" //
				+ "        return () -> {System.out.println(\"foo\");return true;};\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public Function<String, String> removeReturnAndBrackets() {\n" //
				+ "        return someString -> {return someString.trim().toLowerCase();};\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public Function<String, String> removeReturnAndBracketsWithParentheses() {\n" //
				+ "        return someString -> {return someString.trim().toLowerCase() + \"bar\";};\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public Function<String, String> doNotRemoveReturnWithSeveralStatements() {\n" //
				+ "        return someString -> {String trimmed = someString.trim();\n" //
				+ "        return trimmed.toLowerCase();};\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public Supplier<ArrayList<String>> useCreationReference() {\n" //
				+ "        return () -> new ArrayList<>();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public Function<Integer, ArrayList<String>> useCreationReferenceWithParameter() {\n" //
				+ "        return capacity -> new ArrayList<>(capacity);\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public Function<Integer, ArrayList<String>> useCreationReferenceWithParameterAndType() {\n" //
				+ "        // TODO this can be refactored like useCreationReferenceWithParameter\n" //
				+ "        return (Integer capacity) -> new ArrayList<>(capacity);\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public Function<Integer, ArrayList<String>> doNotRefactorWithExpressions() {\n" //
				+ "        return capacity -> new ArrayList<>(capacity + 1);\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public BiFunction<Integer, Integer, Vector<String>> useCreationReferenceWithParameters() {\n" //
				+ "        return (initialCapacity, capacityIncrement) -> new Vector<>(initialCapacity, capacityIncrement);\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public BiFunction<Integer, Integer, Vector<String>> doNotRefactorShuffledParams() {\n" //
				+ "        return (initialCapacity, capacityIncrement) -> new Vector<>(capacityIncrement, initialCapacity);\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public Function<Date, Long> useMethodReference() {\n" //
				+ "        return date -> date.getTime();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public BiFunction<Date, Date, Integer> useMethodReferenceWithParameter() {\n" //
				+ "        return (date, anotherDate) -> date.compareTo(anotherDate);\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public Function<String, Long> useTypeReference() {\n" //
				+ "        return numberInText -> Long.getLong(numberInText);\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public static Function<Instant, Date> useTypeReferenceOnClassMethod() {\n" //
				+ "        return instant -> Date.from(instant);\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public static Function<Locale, Calendar> useTypeReferenceOnImportedMethod() {\n" //
				+ "        return locale -> Calendar.getInstance(locale);\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public static Supplier<Locale[]> useTypeReferenceAsSupplier() {\n" //
				+ "        return () -> Calendar.getAvailableLocales();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public Function<String, Integer> useExpressionMethodReferenceOnLiteral() {\n" //
				+ "        return textToSearch -> \"AutoRefactor\".indexOf(textToSearch);\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public Function<String, Integer> doNotUseExpressionMethodReferenceOnVariable() {\n" //
				+ "        return textToSearch -> this.changeableText.indexOf(textToSearch);\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public Function<Date, Integer> useThisMethodReference() {\n" //
				+ "        return anotherDate -> compareTo(anotherDate);\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public class InnerClass {\n" //
				+ "        public Function<Date, Integer> doNotUseThisMethodReferenceOnTopLevelClassMethod() {\n" //
				+ "            return anotherDate -> compareTo(anotherDate);\n" //
				+ "        }\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public Function<Date, Integer> useThisMethodReferenceAddThis() {\n" //
				+ "        return anotherDate -> this.compareTo(anotherDate);\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public Function<Date, Integer> useSuperMethodReference() {\n" //
				+ "        return anotherDate -> super.compareTo(anotherDate);\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public Function<Integer, String> doNotUseConflictingMethodReference() {\n" //
				+ "        return numberToPrint -> numberToPrint.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public Function<Integer, String> doNotUseConflictingStaticMethodReference() {\n" //
				+ "        return numberToPrint -> Integer.toString(numberToPrint);\n" //
				+ "    }\n" //
				+ "}\n";
		ICompilationUnit cu1= pack1.createCompilationUnit("E.java", sample, false, null);

		enable(CleanUpConstants.SIMPLIFY_LAMBDA_EXPRESSION_AND_METHOD_REF);

		sample= "" //
				+ "package test1;\n" //
				+ "\n" //
				+ "import static java.util.Calendar.getInstance;\n" //
				+ "import static java.util.Calendar.getAvailableLocales;\n" //
				+ "\n" //
				+ "import java.time.Instant;\n" //
				+ "import java.util.ArrayList;\n" //
				+ "import java.util.Calendar;\n" //
				+ "import java.util.Date;\n" //
				+ "import java.util.Locale;\n" //
				+ "import java.util.Vector;\n" //
				+ "import java.util.function.BiFunction;\n" //
				+ "import java.util.function.Function;\n" //
				+ "import java.util.function.Supplier;\n" //
				+ "\n" //
				+ "public class E extends Date {\n" //
				+ "    public String changeableText = \"foo\";\n" //
				+ "\n" //
				+ "    public Function<String, String> removeParentheses() {\n" //
				+ "        return someString -> someString.trim().toLowerCase();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public Function<String, String> doNotRemoveParenthesesWithSingleVariableDeclaration() {\n" //
				+ "        return (String someString) -> someString.trim().toLowerCase();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public BiFunction<String, String, Integer> doNotRemoveParenthesesWithTwoParameters() {\n" //
				+ "        return (someString, anotherString) -> someString.trim().compareTo(anotherString.trim());\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public Supplier<Boolean> doNotRemoveParenthesesWithNoParameter() {\n" //
				+ "        return () -> {System.out.println(\"foo\");return true;};\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public Function<String, String> removeReturnAndBrackets() {\n" //
				+ "        return someString -> someString.trim().toLowerCase();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public Function<String, String> removeReturnAndBracketsWithParentheses() {\n" //
				+ "        return someString -> (someString.trim().toLowerCase() + \"bar\");\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public Function<String, String> doNotRemoveReturnWithSeveralStatements() {\n" //
				+ "        return someString -> {String trimmed = someString.trim();\n" //
				+ "        return trimmed.toLowerCase();};\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public Supplier<ArrayList<String>> useCreationReference() {\n" //
				+ "        return ArrayList::new;\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public Function<Integer, ArrayList<String>> useCreationReferenceWithParameter() {\n" //
				+ "        return ArrayList::new;\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public Function<Integer, ArrayList<String>> useCreationReferenceWithParameterAndType() {\n" //
				+ "        // TODO this can be refactored like useCreationReferenceWithParameter\n" //
				+ "        return (Integer capacity) -> new ArrayList<>(capacity);\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public Function<Integer, ArrayList<String>> doNotRefactorWithExpressions() {\n" //
				+ "        return capacity -> new ArrayList<>(capacity + 1);\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public BiFunction<Integer, Integer, Vector<String>> useCreationReferenceWithParameters() {\n" //
				+ "        return Vector::new;\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public BiFunction<Integer, Integer, Vector<String>> doNotRefactorShuffledParams() {\n" //
				+ "        return (initialCapacity, capacityIncrement) -> new Vector<>(capacityIncrement, initialCapacity);\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public Function<Date, Long> useMethodReference() {\n" //
				+ "        return Date::getTime;\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public BiFunction<Date, Date, Integer> useMethodReferenceWithParameter() {\n" //
				+ "        return Date::compareTo;\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public Function<String, Long> useTypeReference() {\n" //
				+ "        return Long::getLong;\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public static Function<Instant, Date> useTypeReferenceOnClassMethod() {\n" //
				+ "        return instant -> Date.from(instant);\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public static Function<Locale, Calendar> useTypeReferenceOnImportedMethod() {\n" //
				+ "        return Calendar::getInstance;\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public static Supplier<Locale[]> useTypeReferenceAsSupplier() {\n" //
				+ "        return Calendar::getAvailableLocales;\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public Function<String, Integer> useExpressionMethodReferenceOnLiteral() {\n" //
				+ "        return \"AutoRefactor\"::indexOf;\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public Function<String, Integer> doNotUseExpressionMethodReferenceOnVariable() {\n" //
				+ "        return textToSearch -> this.changeableText.indexOf(textToSearch);\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public Function<Date, Integer> useThisMethodReference() {\n" //
				+ "        return this::compareTo;\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public class InnerClass {\n" //
				+ "        public Function<Date, Integer> doNotUseThisMethodReferenceOnTopLevelClassMethod() {\n" //
				+ "            return anotherDate -> compareTo(anotherDate);\n" //
				+ "        }\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public Function<Date, Integer> useThisMethodReferenceAddThis() {\n" //
				+ "        return this::compareTo;\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public Function<Date, Integer> useSuperMethodReference() {\n" //
				+ "        return super::compareTo;\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public Function<Integer, String> doNotUseConflictingMethodReference() {\n" //
				+ "        return numberToPrint -> numberToPrint.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public Function<Integer, String> doNotUseConflictingStaticMethodReference() {\n" //
				+ "        return numberToPrint -> Integer.toString(numberToPrint);\n" //
				+ "    }\n" //
				+ "}\n";
		String expected1= sample;

		assertRefactoringResultAsExpected(new ICompilationUnit[] { cu1 }, new String[] { expected1 });
	}

	@Test
	public void testConvertToLambdaWithRecursion() throws Exception {
		IPackageFragment pack1= fSourceFolder.createPackageFragment("test", false, null);
		String sample= ""
				+ "package test;\n" //
				+ "\n" //
				+ "import java.util.function.Function;\n" //
				+ "\n" //
				+ "public class C1 {\n" //
				+ "\n" //
				+ "    public interface I1 {\n" //
				+ "        public int add(int a);\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    I1 k = new I1() {\n" //
				+ "        @Override\n" //
				+ "        public int add(int a) {\n" //
				+ "            if (a == 2) {\n" //
				+ "                return add(3);\n" //
				+ "            }\n" //
				+ "            return a + 7;\n" //
				+ "        }\n" //
				+ "    };\n" //
				+ "\n" //
				+ "    public static I1 j = new I1() {\n" //
				+ "        @Override\n" //
				+ "        public int add(int a) {\n" //
				+ "            if (a == 2) {\n" //
				+ "                return add(4);\n" //
				+ "            }\n" //
				+ "            return a + 8;\n" //
				+ "        }\n" //
				+ "    };\n" //
				+ "}\n"; //
		ICompilationUnit cu= pack1.createCompilationUnit("C1.java", sample, false, null);

		enable(CleanUpConstants.CONVERT_FUNCTIONAL_INTERFACES);
		enable(CleanUpConstants.USE_LAMBDA);

		String expected= ""
				+ "package test;\n" //
				+ "\n" //
				+ "import java.util.function.Function;\n" //
				+ "\n" //
				+ "public class C1 {\n" //
				+ "\n" //
				+ "    public interface I1 {\n" //
				+ "        public int add(int a);\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    I1 k = a -> {\n" //
				+ "        if (a == 2) {\n" //
				+ "            return this.k.add(3);\n" //
				+ "        }\n" //
				+ "        return a + 7;\n" //
				+ "    };\n" //
				+ "\n" //
				+ "    public static I1 j = a -> {\n" //
				+ "        if (a == 2) {\n" //
				+ "            return C1.j.add(4);\n" //
				+ "        }\n" //
				+ "        return a + 8;\n" //
				+ "    };\n" //
				+ "}\n"; //
		assertRefactoringResultAsExpected(new ICompilationUnit[] { cu }, new String[] { expected });
	}

	@Test
	public void testDoNotConvertLocalRecursiveClass() throws Exception {
		IPackageFragment pack1= fSourceFolder.createPackageFragment("test1", false, null);
		String sample= "" //
				+ "package test1;\n" //
				+ "\n" //
				+ "import java.util.function.Function;\n" //
				+ "\n" //
				+ "public class C2 {\n" //
				+ "\n" //
				+ "    public interface I1 {\n" //
				+ "        public int add(int a);\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public int foo() {\n" //
				+ "        I1 doNotConvert = new I1() {\n" //
				+ "            @Override\n" //
				+ "            public int add(int a) {\n" //
				+ "                if (a == 2) {\n" //
				+ "                    return add(5);\n" //
				+ "                }\n" //
				+ "                return a + 9;\n" //
				+ "            }\n" //
				+ "        };\n" //
				+ "        return doNotConvert.add(9);\n" //
				+ "    }\n" //
				+ "}\n"; //
		ICompilationUnit cu= pack1.createCompilationUnit("C2.java", sample, false, null);

		enable(CleanUpConstants.SIMPLIFY_LAMBDA_EXPRESSION_AND_METHOD_REF);

		assertRefactoringHasNoChange(new ICompilationUnit[] { cu });
	}

	@Test
	public void testJoin() throws Exception {
		IPackageFragment pack= fSourceFolder.createPackageFragment("test1", false, null);
		String input= "" //
				+ "package test1;\n" //
				+ "\n" //
				+ "public class E {\n" //
				+ "    public String refactorConcatenation(String[] texts) {\n" //
				+ "        // Keep this comment\n" //
				+ "        boolean isFirst = true;\n" //
				+ "        // Keep this comment too\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "\n" //
				+ "        // Keep this comment also\n" //
				+ "        for (int i = 0; i < texts.length; i++) {\n" //
				+ "            if (isFirst) {\n" //
				+ "                isFirst = false;\n" //
				+ "            } else {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            }\n" //
				+ "            concatenation.append(texts[i]);\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorReassignment(String[] texts) {\n" //
				+ "        boolean isFirst = true;\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        for (String text : texts) {\n" //
				+ "            if (isFirst) {\n" //
				+ "                isFirst = false;\n" //
				+ "            } else {\n" //
				+ "                concatenation = concatenation.append(\", \");\n" //
				+ "            }\n" //
				+ "            concatenation = concatenation.append(text);\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public Runnable refactorFinalConcatenation(String[] names) {\n" //
				+ "        boolean isFirst = true;\n" //
				+ "        final StringBuilder concatenation = new StringBuilder();\n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        for (int i = 0; i < names.length; i++) {\n" //
				+ "            if (isFirst) {\n" //
				+ "                isFirst = false;\n" //
				+ "            } else {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            }\n" //
				+ "            concatenation.append(names[i]);\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        Runnable supplier= new Runnable() {\n" //
				+ "            @Override\n" //
				+ "            public void run() {\n" //
				+ "                System.out.println(concatenation.toString());\n" //
				+ "            }\n" //
				+ "        };\n" //
				+ "        return supplier;\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorConcatenationWithChar(String[] titles) {\n" //
				+ "        boolean isFirst = true;\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        for (String title : titles) {\n" //
				+ "            if (isFirst) {\n" //
				+ "                isFirst = false;\n" //
				+ "            } else {\n" //
				+ "                concatenation.append(',');\n" //
				+ "            }\n" //
				+ "            concatenation.append(title);\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorConcatenationWithHardCodedDelimiter(String[] texts) {\n" //
				+ "        boolean isFirst = true;\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        for (int i = 0; i < texts.length; i++) {\n" //
				+ "            if (isFirst) {\n" //
				+ "                isFirst = false;\n" //
				+ "            } else {\n" //
				+ "                concatenation = concatenation.append(\" \" + 1);\n" //
				+ "            }\n" //
				+ "            concatenation.append(texts[i]);\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorConcatenationWithBuilderFirst(String[] texts) {\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "        boolean isFirst = true;\n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        for (int i = 0; i < texts.length; i++) {\n" //
				+ "            if (isFirst) {\n" //
				+ "                isFirst = false;\n" //
				+ "            } else {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            }\n" //
				+ "            concatenation.append(texts[i]);\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorConcatenationWithStringBuffer(String[] texts) {\n" //
				+ "        boolean isFirst = true;\n" //
				+ "        StringBuffer concatenation = new StringBuffer();\n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        for (int i = 0; i < texts.length; i++) {\n" //
				+ "            if (isFirst) {\n" //
				+ "                isFirst = false;\n" //
				+ "            } else {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            }\n" //
				+ "            concatenation.append(texts[i]);\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorConcatenationWithBooleanObject(String[] texts) {\n" //
				+ "        Boolean isFirst = Boolean.TRUE;\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        for (int i = 0; i < texts.length; i++) {\n" //
				+ "            if (isFirst) {\n" //
				+ "                isFirst = Boolean.FALSE;\n" //
				+ "            } else {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            }\n" //
				+ "            concatenation = concatenation.append(texts[i]);\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorConcatenationWithNegatedBoolean(String[] texts) {\n" //
				+ "        Boolean isFirst = true;\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        for (int i = 0; i < texts.length; i++) {\n" //
				+ "            if (!isFirst) {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            } else {\n" //
				+ "                isFirst = false;\n" //
				+ "            }\n" //
				+ "            concatenation.append(texts[i]);\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorConcatenationWithReversedBoolean(String[] texts) {\n" //
				+ "        boolean isVisited = Boolean.FALSE;\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        for (int i = 0; i < texts.length; i++) {\n" //
				+ "            if (isVisited) {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            } else {\n" //
				+ "                isVisited = Boolean.TRUE;\n" //
				+ "            }\n" //
				+ "            concatenation.append(texts[i]);\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorConcatenationWithLotsOfMethods(String[] texts) {\n" //
				+ "        boolean isFirst = true;\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        for (int i = 0; i < texts.length; i++) {\n" //
				+ "            if (isFirst) {\n" //
				+ "                isFirst = false;\n" //
				+ "            } else {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            }\n" //
				+ "            concatenation.append(texts[i]);\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        System.out.println(concatenation.charAt(0));\n" //
				+ "        System.out.println(concatenation.chars());\n" //
				+ "        System.out.println(concatenation.codePoints());\n" //
				+ "        System.out.println(concatenation.indexOf(\"foo\", 0));\n" //
				+ "        System.out.println(concatenation.lastIndexOf(\"foo\"));\n" //
				+ "        System.out.println(concatenation.lastIndexOf(\"foo\", 0));\n" //
				+ "        System.out.println(concatenation.length());\n" //
				+ "        System.out.println(concatenation.subSequence(0, 0));\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorConcatenationOnForeach(String[] texts) {\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "        boolean isFirst = true;\n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        for (String text : texts) {\n" //
				+ "            if (isFirst) {\n" //
				+ "                isFirst = false;\n" //
				+ "            } else {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            }\n" //
				+ "            concatenation.append(text);\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorConcatenationWithConditionOnIndex(String[] texts) {\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        for (int i = 0; i < texts.length; i++) {\n" //
				+ "            if (i > 0) {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            }\n" //
				+ "            concatenation.append(texts[i]);\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorConcatenationWithInequalityOnIndex(String[] texts) {\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        for (int i = 0; i < texts.length; i++) {\n" //
				+ "            if (i != 0) {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            }\n" //
				+ "            concatenation.append(texts[i]);\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorConcatenationWithReversedConditionOnIndex(String[] texts) {\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        for (int i = 0; i < texts.length; i++) {\n" //
				+ "            if (0 < i) {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            }\n" //
				+ "            concatenation.append(texts[i]);\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorConcatenationWithGreaterOrEqualsOnIndex(String[] texts) {\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        for (int i = 0; i < texts.length; i++) {\n" //
				+ "            if (i >= 1) {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            }\n" //
				+ "            concatenation.append(texts[i]);\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorConcatenationWithDelimiterAtTheEnd(String[] texts) {\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        for (int i = 0; i < texts.length; i++) {\n" //
				+ "            concatenation.append(texts[i]);\n" //
				+ "            if (i < texts.length - 1) {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            }\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorConcatenationWithMirroredCondition(String[] texts) {\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        for (int i = 0; i < texts.length; i++) {\n" //
				+ "            concatenation.append(texts[i]);\n" //
				+ "            if (texts.length - 1 > i) {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            }\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorConcatenationWithNotEqualsCondition(String[] texts) {\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        for (int i = 0; i < texts.length; i++) {\n" //
				+ "            concatenation.append(texts[i]);\n" //
				+ "            if (i < texts.length - 1) {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            }\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorConcatenationWithLessOrEqualsCondition(String[] texts) {\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        for (int i = 0; i < texts.length; i++) {\n" //
				+ "            concatenation.append(texts[i]);\n" //
				+ "            if (i <= texts.length - 2) {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            }\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorConcatenationTestingLength(String[] texts) {\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        for (int i = 0; i < texts.length; i++) {\n" //
				+ "            if (concatenation.length() > 0) {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            }\n" //
				+ "            concatenation.append(texts[i]);\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorConcatenationTestingNotEmpty(String[] texts) {\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        for (int i = 0; i < texts.length; i++) {\n" //
				+ "            if (concatenation.length() != 0) {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            }\n" //
				+ "            concatenation.append(texts[i]);\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorConcatenationTestingGreaterOrEqualsOne(String[] texts) {\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        for (int i = 0; i < texts.length; i++) {\n" //
				+ "            if (concatenation.length() >= 1) {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            }\n" //
				+ "            concatenation.append(texts[i]);\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorConcatenationTestingLengthMirrored(String[] texts) {\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        for (int i = 0; i < texts.length; i++) {\n" //
				+ "            if (0 < concatenation.length()) {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            }\n" //
				+ "            concatenation.append(texts[i]);\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorConcatenationTestingNotEmptyMirrored(String[] texts) {\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        for (int i = 0; i < texts.length; i++) {\n" //
				+ "            if (0 != concatenation.length()) {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            }\n" //
				+ "            concatenation.append(texts[i]);\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorConcatenationTestingGreaterOrEqualsOneMirrored(String[] texts) {\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        for (int i = 0; i < texts.length; i++) {\n" //
				+ "            if (1 <= concatenation.length()) {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            }\n" //
				+ "            concatenation.append(texts[i]);\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorConstantBooleanShift(String[] texts) {\n" //
				+ "        boolean isFirst = true;\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        for (String text : texts) {\n" //
				+ "            if (!isFirst) {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            }\n" //
				+ "            isFirst = false;\n" //
				+ "            concatenation.append(text);\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorWithBooleanShiftAtTheEnd(String[] texts) {\n" //
				+ "        boolean isFirst = true;\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        for (String text : texts) {\n" //
				+ "            if (!isFirst) {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            }\n" //
				+ "            concatenation.append(text);\n" //
				+ "            isFirst = false;\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorWithReversedBooleanShift(String[] texts) {\n" //
				+ "        boolean isNotFirst = false;\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        for (String text : texts) {\n" //
				+ "            if (isNotFirst) {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            }\n" //
				+ "            concatenation.append(text);\n" //
				+ "            isNotFirst = true;\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "}\n";
		ICompilationUnit cu= pack.createCompilationUnit("E.java", input, false, null);

		enable(CleanUpConstants.JOIN);

		String output= "" //
				+ "package test1;\n" //
				+ "\n" //
				+ "public class E {\n" //
				+ "    public String refactorConcatenation(String[] texts) {\n" //
				+ "        // Keep this comment\n" //
				+ "        \n" //
				+ "        // Keep this comment too\n" //
				+ "        \n" //
				+ "\n" //
				+ "        // Keep this comment also\n" //
				+ "        String concatenation = String.join(\", \", texts);\n" //
				+ "\n" //
				+ "        return concatenation;\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorReassignment(String[] texts) {\n" //
				+ "        \n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        String concatenation = String.join(\", \", texts);\n" //
				+ "\n" //
				+ "        return concatenation;\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public Runnable refactorFinalConcatenation(String[] names) {\n" //
				+ "        \n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        final String concatenation = String.join(\", \", names);\n" //
				+ "\n" //
				+ "        Runnable supplier= new Runnable() {\n" //
				+ "            @Override\n" //
				+ "            public void run() {\n" //
				+ "                System.out.println(concatenation);\n" //
				+ "            }\n" //
				+ "        };\n" //
				+ "        return supplier;\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorConcatenationWithChar(String[] titles) {\n" //
				+ "        \n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        String concatenation = String.join(String.valueOf(','), titles);\n" //
				+ "\n" //
				+ "        return concatenation;\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorConcatenationWithHardCodedDelimiter(String[] texts) {\n" //
				+ "        \n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        String concatenation = String.join(\" \" + 1, texts);\n" //
				+ "\n" //
				+ "        return concatenation;\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorConcatenationWithBuilderFirst(String[] texts) {\n" //
				+ "        \n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        String concatenation = String.join(\", \", texts);\n" //
				+ "\n" //
				+ "        return concatenation;\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorConcatenationWithStringBuffer(String[] texts) {\n" //
				+ "        \n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        String concatenation = String.join(\", \", texts);\n" //
				+ "\n" //
				+ "        return concatenation;\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorConcatenationWithBooleanObject(String[] texts) {\n" //
				+ "        \n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        String concatenation = String.join(\", \", texts);\n" //
				+ "\n" //
				+ "        return concatenation;\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorConcatenationWithNegatedBoolean(String[] texts) {\n" //
				+ "        \n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        String concatenation = String.join(\", \", texts);\n" //
				+ "\n" //
				+ "        return concatenation;\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorConcatenationWithReversedBoolean(String[] texts) {\n" //
				+ "        \n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        String concatenation = String.join(\", \", texts);\n" //
				+ "\n" //
				+ "        return concatenation;\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorConcatenationWithLotsOfMethods(String[] texts) {\n" //
				+ "        \n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        String concatenation = String.join(\", \", texts);\n" //
				+ "\n" //
				+ "        System.out.println(concatenation.charAt(0));\n" //
				+ "        System.out.println(concatenation.chars());\n" //
				+ "        System.out.println(concatenation.codePoints());\n" //
				+ "        System.out.println(concatenation.indexOf(\"foo\", 0));\n" //
				+ "        System.out.println(concatenation.lastIndexOf(\"foo\"));\n" //
				+ "        System.out.println(concatenation.lastIndexOf(\"foo\", 0));\n" //
				+ "        System.out.println(concatenation.length());\n" //
				+ "        System.out.println(concatenation.subSequence(0, 0));\n" //
				+ "        return concatenation;\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorConcatenationOnForeach(String[] texts) {\n" //
				+ "        \n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        String concatenation = String.join(\", \", texts);\n" //
				+ "\n" //
				+ "        return concatenation;\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorConcatenationWithConditionOnIndex(String[] texts) {\n" //
				+ "        \n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        String concatenation = String.join(\", \", texts);\n" //
				+ "\n" //
				+ "        return concatenation;\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorConcatenationWithInequalityOnIndex(String[] texts) {\n" //
				+ "        \n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        String concatenation = String.join(\", \", texts);\n" //
				+ "\n" //
				+ "        return concatenation;\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorConcatenationWithReversedConditionOnIndex(String[] texts) {\n" //
				+ "        \n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        String concatenation = String.join(\", \", texts);\n" //
				+ "\n" //
				+ "        return concatenation;\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorConcatenationWithGreaterOrEqualsOnIndex(String[] texts) {\n" //
				+ "        \n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        String concatenation = String.join(\", \", texts);\n" //
				+ "\n" //
				+ "        return concatenation;\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorConcatenationWithDelimiterAtTheEnd(String[] texts) {\n" //
				+ "        \n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        String concatenation = String.join(\", \", texts);\n" //
				+ "\n" //
				+ "        return concatenation;\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorConcatenationWithMirroredCondition(String[] texts) {\n" //
				+ "        \n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        String concatenation = String.join(\", \", texts);\n" //
				+ "\n" //
				+ "        return concatenation;\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorConcatenationWithNotEqualsCondition(String[] texts) {\n" //
				+ "        \n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        String concatenation = String.join(\", \", texts);\n" //
				+ "\n" //
				+ "        return concatenation;\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorConcatenationWithLessOrEqualsCondition(String[] texts) {\n" //
				+ "        \n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        String concatenation = String.join(\", \", texts);\n" //
				+ "\n" //
				+ "        return concatenation;\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorConcatenationTestingLength(String[] texts) {\n" //
				+ "        \n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        String concatenation = String.join(\", \", texts);\n" //
				+ "\n" //
				+ "        return concatenation;\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorConcatenationTestingNotEmpty(String[] texts) {\n" //
				+ "        \n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        String concatenation = String.join(\", \", texts);\n" //
				+ "\n" //
				+ "        return concatenation;\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorConcatenationTestingGreaterOrEqualsOne(String[] texts) {\n" //
				+ "        \n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        String concatenation = String.join(\", \", texts);\n" //
				+ "\n" //
				+ "        return concatenation;\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorConcatenationTestingLengthMirrored(String[] texts) {\n" //
				+ "        \n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        String concatenation = String.join(\", \", texts);\n" //
				+ "\n" //
				+ "        return concatenation;\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorConcatenationTestingNotEmptyMirrored(String[] texts) {\n" //
				+ "        \n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        String concatenation = String.join(\", \", texts);\n" //
				+ "\n" //
				+ "        return concatenation;\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorConcatenationTestingGreaterOrEqualsOneMirrored(String[] texts) {\n" //
				+ "        \n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        String concatenation = String.join(\", \", texts);\n" //
				+ "\n" //
				+ "        return concatenation;\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorConstantBooleanShift(String[] texts) {\n" //
				+ "        \n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        String concatenation = String.join(\", \", texts);\n" //
				+ "\n" //
				+ "        return concatenation;\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorWithBooleanShiftAtTheEnd(String[] texts) {\n" //
				+ "        \n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        String concatenation = String.join(\", \", texts);\n" //
				+ "\n" //
				+ "        return concatenation;\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String refactorWithReversedBooleanShift(String[] texts) {\n" //
				+ "        \n" //
				+ "\n" //
				+ "        // Keep this comment\n" //
				+ "        String concatenation = String.join(\", \", texts);\n" //
				+ "\n" //
				+ "        return concatenation;\n" //
				+ "    }\n" //
				+ "}\n";

		assertNotEquals("The class must be changed", input, output);
		assertGroupCategoryUsed(new ICompilationUnit[] { cu }, new String[] { MultiFixMessages.JoinCleanup_description });
		assertRefactoringResultAsExpected(new ICompilationUnit[] { cu }, new String[] { output });
	}

	@Test
	public void testDoNotJoin() throws Exception {
		IPackageFragment pack= fSourceFolder.createPackageFragment("test1", false, null);
		String sample= "" //
				+ "package test1;\n" //
				+ "\n" //
				+ "public class E {\n" //
				+ "    public boolean doNotRefactorUsedBoolean(String[] texts) {\n" //
				+ "        boolean isFirst = true;\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "\n" //
				+ "        for (int i = 0; i < texts.length; i++) {\n" //
				+ "            if (isFirst) {\n" //
				+ "                isFirst = false;\n" //
				+ "            } else {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            }\n" //
				+ "            concatenation.append(texts[i]);\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        System.out.println(concatenation.toString());\n" //
				+ "        return isFirst;\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String doNotRefactorUnhandledMethod(String[] texts) {\n" //
				+ "        boolean isFirst = true;\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "\n" //
				+ "        for (int i = 0; i < texts.length; i++) {\n" //
				+ "            if (isFirst) {\n" //
				+ "                isFirst = false;\n" //
				+ "            } else {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            }\n" //
				+ "            concatenation.append(texts[i]);\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        System.out.println(concatenation.codePointAt(0));\n" //
				+ "        System.out.println(concatenation.codePointBefore(0));\n" //
				+ "        System.out.println(concatenation.codePointCount(0, 0));\n" //
				+ "        concatenation.getChars(0, 0, new char[0], 0);\n" //
				+ "        System.out.println(concatenation.indexOf(\"foo\"));\n" //
				+ "        System.out.println(concatenation.offsetByCodePoints(0, 0));\n" //
				+ "        System.out.println(concatenation.substring(0));\n" //
				+ "        System.out.println(concatenation.substring(0, 0));\n" //
				+ "        System.out.println(concatenation.capacity());\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String doNotRefactorPartialConcatenation(String[] texts) {\n" //
				+ "        boolean isFirst = true;\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "\n" //
				+ "        for (int i = 1; i < texts.length; i++) {\n" //
				+ "            if (isFirst) {\n" //
				+ "                isFirst = false;\n" //
				+ "            } else {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            }\n" //
				+ "            concatenation.append(texts[i]);\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String doNotRefactorUnfinishedConcatenation(String[] texts) {\n" //
				+ "        boolean isFirst = true;\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "\n" //
				+ "        for (int i = 0; i < texts.length - 1; i++) {\n" //
				+ "            if (isFirst) {\n" //
				+ "                isFirst = false;\n" //
				+ "            } else {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            }\n" //
				+ "            concatenation.append(texts[i]);\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String doNotRefactorReversedConcatenation(String[] texts) {\n" //
				+ "        boolean isFirst = true;\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "\n" //
				+ "        for (int i = texts.length - 1; i >= 0; i--) {\n" //
				+ "            if (isFirst) {\n" //
				+ "                isFirst = false;\n" //
				+ "            } else {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            }\n" //
				+ "            concatenation.append(texts[i]);\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String doNotRefactorWithOppositeBoolean(String[] texts) {\n" //
				+ "        boolean isFirst = true;\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "\n" //
				+ "        for (int i = 1; i < texts.length; i++) {\n" //
				+ "            if (isFirst) {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            } else {\n" //
				+ "                isFirst = false;\n" //
				+ "            }\n" //
				+ "            concatenation.append(texts[i]);\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String doNotRefactorOnObjects(Object[] texts) {\n" //
				+ "        boolean isFirst = true;\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "\n" //
				+ "        for (int i = 0; i < texts.length; i++) {\n" //
				+ "            if (isFirst) {\n" //
				+ "                isFirst = false;\n" //
				+ "            } else {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            }\n" //
				+ "            concatenation.append(texts[i]);\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String doNotRefactorWithOtherAppending(String[] texts) {\n" //
				+ "        boolean isFirst = true;\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "\n" //
				+ "        for (int i = 0; i < texts.length; i++) {\n" //
				+ "            if (isFirst) {\n" //
				+ "                isFirst = false;\n" //
				+ "            } else {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            }\n" //
				+ "            concatenation.append(texts[i]);\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        concatenation.append(\"foo\");\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String doNotRefactorWithInitialization(String[] texts) {\n" //
				+ "        boolean isFirst = true;\n" //
				+ "        StringBuilder concatenation = new StringBuilder(\"foo\");\n" //
				+ "\n" //
				+ "        for (int i = 0; i < texts.length; i++) {\n" //
				+ "            if (isFirst) {\n" //
				+ "                isFirst = false;\n" //
				+ "            } else {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            }\n" //
				+ "            concatenation.append(texts[i]);\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String doNotRefactorWithWrongIndex(String[] texts) {\n" //
				+ "        boolean isFirst = true;\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "\n" //
				+ "        for (int i = 0; i < texts.length; i++) {\n" //
				+ "            if (isFirst) {\n" //
				+ "                isFirst = false;\n" //
				+ "            } else {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            }\n" //
				+ "            concatenation.append(texts[0]);\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String doNotRefactorWithWrongBoolean(String[] texts, boolean isSecond) {\n" //
				+ "        boolean isFirst = true;\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "\n" //
				+ "        for (int i = 0; i < texts.length; i++) {\n" //
				+ "            if (isSecond) {\n" //
				+ "                isFirst = false;\n" //
				+ "            } else {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            }\n" //
				+ "            concatenation.append(texts[i]);\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String doNotRefactorWithWrongBoolean(String[] texts) {\n" //
				+ "        boolean isSecond = false;\n" //
				+ "        boolean isFirst = true;\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "\n" //
				+ "        for (int i = 0; i < texts.length; i++) {\n" //
				+ "            if (isFirst) {\n" //
				+ "                isSecond = false;\n" //
				+ "            } else {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            }\n" //
				+ "            concatenation.append(texts[i]);\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String doNotRefactorWithWrongArray(String[] texts, String[] names) {\n" //
				+ "        boolean isFirst = true;\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "\n" //
				+ "        for (int i = 0; i < texts.length; i++) {\n" //
				+ "            if (isFirst) {\n" //
				+ "                isFirst = false;\n" //
				+ "            } else {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            }\n" //
				+ "            concatenation.append(names[i]);\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String doNotRefactorWithWrongBuilder(String[] texts, StringBuilder otherBuilder) {\n" //
				+ "        boolean isFirst = true;\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "\n" //
				+ "        for (int i = 0; i < texts.length; i++) {\n" //
				+ "            if (isFirst) {\n" //
				+ "                isFirst = false;\n" //
				+ "            } else {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            }\n" //
				+ "            otherBuilder.append(texts[i]);\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String doNotRefactorWithAnotherBuilder(String[] texts, StringBuilder otherBuilder) {\n" //
				+ "        boolean isFirst = true;\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "\n" //
				+ "        for (int i = 0; i < texts.length; i++) {\n" //
				+ "            if (isFirst) {\n" //
				+ "                isFirst = false;\n" //
				+ "            } else {\n" //
				+ "                otherBuilder.append(\", \");\n" //
				+ "            }\n" //
				+ "            concatenation.append(texts[i]);\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String doNotRefactorWithAdditionalStatement(String[] texts) {\n" //
				+ "        boolean isFirst = true;\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "\n" //
				+ "        for (int i = 0; i < texts.length; i++) {\n" //
				+ "            if (isFirst) {\n" //
				+ "                isFirst = false;\n" //
				+ "            } else {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            }\n" //
				+ "            concatenation.append(texts[i]);\n" //
				+ "            System.out.println(\"Hi!\");\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String doNotRefactorWithWrongMethod(String[] texts) {\n" //
				+ "        boolean isFirst = true;\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "\n" //
				+ "        for (int i = 0; i < texts.length; i++) {\n" //
				+ "            if (isFirst) {\n" //
				+ "                isFirst = false;\n" //
				+ "            } else {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            }\n" //
				+ "            concatenation.append(texts[i], 0, 2);\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String doNotRefactorWrongVariable(String[] texts, String test) {\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "        boolean isFirst = true;\n" //
				+ "\n" //
				+ "        for (String text : texts) {\n" //
				+ "            if (isFirst) {\n" //
				+ "                isFirst = false;\n" //
				+ "            } else {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            }\n" //
				+ "            concatenation.append(test);\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String doNotRefactorWithBooleanShiftFirst(String[] texts) {\n" //
				+ "        boolean isFirst = true;\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "\n" //
				+ "        for (String text : texts) {\n" //
				+ "            isFirst = false;\n" //
				+ "            if (!isFirst) {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            }\n" //
				+ "            concatenation.append(text);\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String doNotRefactorWithAppendingFirst(String[] texts) {\n" //
				+ "        boolean isFirst = true;\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "\n" //
				+ "        for (String text : texts) {\n" //
				+ "            concatenation.append(text);\n" //
				+ "            if (!isFirst) {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            }\n" //
				+ "            isFirst = false;\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String doNotRefactorWithConditionAtTheEnd(String[] texts) {\n" //
				+ "        boolean isFirst = true;\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "\n" //
				+ "        for (String text : texts) {\n" //
				+ "            concatenation.append(text);\n" //
				+ "            isFirst = false;\n" //
				+ "            if (!isFirst) {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            }\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String doNotRefactorWithNonsense(String[] texts) {\n" //
				+ "        boolean isFirst = true;\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "\n" //
				+ "        for (String text : texts) {\n" //
				+ "            isFirst = false;\n" //
				+ "            concatenation.append(text);\n" //
				+ "            if (!isFirst) {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            }\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String doNotRefactorUnshiftedBoolean(String[] texts) {\n" //
				+ "        boolean isFirst = true;\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "\n" //
				+ "        for (String text : texts) {\n" //
				+ "            if (!isFirst) {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            }\n" //
				+ "            isFirst = true;\n" //
				+ "            concatenation.append(text);\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String doNotRefactorWrongCondition(String[] texts) {\n" //
				+ "        boolean isFirst = true;\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "\n" //
				+ "        for (String text : texts) {\n" //
				+ "            if (isFirst) {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            }\n" //
				+ "            isFirst = false;\n" //
				+ "            concatenation.append(text);\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "\n" //
				+ "    public String doNotRefactorWrongInit(String[] texts) {\n" //
				+ "        boolean isFirst = false;\n" //
				+ "        StringBuilder concatenation = new StringBuilder();\n" //
				+ "\n" //
				+ "        for (String text : texts) {\n" //
				+ "            if (!isFirst) {\n" //
				+ "                concatenation.append(\", \");\n" //
				+ "            }\n" //
				+ "            isFirst = false;\n" //
				+ "            concatenation.append(text);\n" //
				+ "        }\n" //
				+ "\n" //
				+ "        return concatenation.toString();\n" //
				+ "    }\n" //
				+ "}\n";
		ICompilationUnit cu= pack.createCompilationUnit("E.java", sample, false, null);

		enable(CleanUpConstants.JOIN);

		assertRefactoringHasNoChange(new ICompilationUnit[] { cu });
	}
}
