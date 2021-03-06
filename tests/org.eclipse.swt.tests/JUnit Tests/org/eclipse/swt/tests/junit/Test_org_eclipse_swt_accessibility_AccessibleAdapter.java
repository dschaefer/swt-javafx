/*******************************************************************************
 * Copyright (c) 2000, 2003 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.tests.junit;

import java.util.Enumeration;
import java.util.Vector;

import junit.framework.*;
import junit.textui.*;

/**
 * Automated Test Suite for class org.eclipse.swt.accessibility.AccessibleAdapter
 *
 * @see org.eclipse.swt.accessibility.AccessibleAdapter
 */
public class Test_org_eclipse_swt_accessibility_AccessibleAdapter extends SwtTestCase {

public Test_org_eclipse_swt_accessibility_AccessibleAdapter(String name) {
	super(name);
}

public static void main(String[] args) {
	TestRunner.run(suite());
}

public void test_Constructor() {
	warnUnimpl("Test test_Constructor not written");
}

public void test_getDescriptionLorg_eclipse_swt_accessibility_AccessibleEvent() {
	warnUnimpl("Test test_getDescriptionLorg_eclipse_swt_accessibility_AccessibleEvent not written");
}

public void test_getHelpLorg_eclipse_swt_accessibility_AccessibleEvent() {
	warnUnimpl("Test test_getHelpLorg_eclipse_swt_accessibility_AccessibleEvent not written");
}

public void test_getKeyboardShortcutLorg_eclipse_swt_accessibility_AccessibleEvent() {
	warnUnimpl("Test test_getKeyboardShortcutLorg_eclipse_swt_accessibility_AccessibleEvent not written");
}

public void test_getNameLorg_eclipse_swt_accessibility_AccessibleEvent() {
	warnUnimpl("Test test_getNameLorg_eclipse_swt_accessibility_AccessibleEvent not written");
}


public static Test suite() {
	TestSuite suite = new TestSuite();
	Vector<String> methodNames = methodNames();
	Enumeration<String> e = methodNames.elements();
	while (e.hasMoreElements()) {
		suite.addTest(new Test_org_eclipse_swt_accessibility_AccessibleAdapter(e.nextElement()));
	}
	return suite;
}

public static Vector<String> methodNames() {
	Vector<String> methodNames = new Vector<String>();
	methodNames.addElement("test_Constructor");
	methodNames.addElement("test_getDescriptionLorg_eclipse_swt_accessibility_AccessibleEvent");
	methodNames.addElement("test_getHelpLorg_eclipse_swt_accessibility_AccessibleEvent");
	methodNames.addElement("test_getKeyboardShortcutLorg_eclipse_swt_accessibility_AccessibleEvent");
	methodNames.addElement("test_getNameLorg_eclipse_swt_accessibility_AccessibleEvent");
	return methodNames;
}

@Override
protected void runTest() throws Throwable {
	if (getName().equals("test_Constructor")) test_Constructor();
	else if (getName().equals("test_getDescriptionLorg_eclipse_swt_accessibility_AccessibleEvent")) test_getDescriptionLorg_eclipse_swt_accessibility_AccessibleEvent();
	else if (getName().equals("test_getHelpLorg_eclipse_swt_accessibility_AccessibleEvent")) test_getHelpLorg_eclipse_swt_accessibility_AccessibleEvent();
	else if (getName().equals("test_getKeyboardShortcutLorg_eclipse_swt_accessibility_AccessibleEvent")) test_getKeyboardShortcutLorg_eclipse_swt_accessibility_AccessibleEvent();
	else if (getName().equals("test_getNameLorg_eclipse_swt_accessibility_AccessibleEvent")) test_getNameLorg_eclipse_swt_accessibility_AccessibleEvent();
}
}
