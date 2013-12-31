/*******************************************************************************
 * Copyright (c) 2000, 2013 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.program;

import org.eclipse.swt.graphics.ImageData;

/**
 * Instances of this class represent programs and their associated file
 * extensions in the operating system.
 * 
 * @see <a href="http://www.eclipse.org/swt/snippets/#program">Program
 *      snippets</a>
 * @see <a href="http://www.eclipse.org/swt/">Sample code and further
 *      information</a>
 */
public final class Program {

	/**
	 * Prevents uninitialized instances from being created outside the package.
	 */
	Program() {
	}

	/**
	 * Finds the program that is associated with an extension. The extension may
	 * or may not begin with a '.'. Note that a <code>Display</code> must
	 * already exist to guarantee that this method returns an appropriate
	 * result.
	 * 
	 * @param extension
	 *            the program extension
	 * @return the program or <code>null</code>
	 * 
	 * @exception IllegalArgumentException
	 *                <ul>
	 *                <li>ERROR_NULL_ARGUMENT when extension is null</li>
	 *                </ul>
	 */
	public static Program findProgram(String extension) {
		// TODO
		return null;
	}

	/**
	 * Answer all program extensions in the operating system. Note that a
	 * <code>Display</code> must already exist to guarantee that this method
	 * returns an appropriate result.
	 * 
	 * @return an array of extensions
	 */
	public static String[] getExtensions() {
		// TODO
		return null;
	}

	/**
	 * Answers all available programs in the operating system. Note that a
	 * <code>Display</code> must already exist to guarantee that this method
	 * returns an appropriate result.
	 * 
	 * @return an array of programs
	 */
	public static Program[] getPrograms() {
		// TODO
		return null;
	}

	/**
	 * Launches the operating system executable associated with the file or URL
	 * (http:// or https://). If the file is an executable then the executable
	 * is launched. Note that a <code>Display</code> must already exist to
	 * guarantee that this method returns an appropriate result.
	 * 
	 * @param fileName
	 *            the file or program name or URL (http:// or https://)
	 * @return <code>true</code> if the file is launched, otherwise
	 *         <code>false</code>
	 * 
	 * @exception IllegalArgumentException
	 *                <ul>
	 *                <li>ERROR_NULL_ARGUMENT when fileName is null</li>
	 *                </ul>
	 */
	public static boolean launch(String fileName) {
		// TODO
		return false;
	}

	/**
	 * Launches the operating system executable associated with the file or URL
	 * (http:// or https://). If the file is an executable then the executable
	 * is launched. The program is launched with the specified working directory
	 * only when the <code>workingDir</code> exists and <code>fileName</code> is
	 * an executable. Note that a <code>Display</code> must already exist to
	 * guarantee that this method returns an appropriate result.
	 * 
	 * @param fileName
	 *            the file name or program name or URL (http:// or https://)
	 * @param workingDir
	 *            the name of the working directory or null
	 * @return <code>true</code> if the file is launched, otherwise
	 *         <code>false</code>
	 * 
	 * @exception IllegalArgumentException
	 *                <ul>
	 *                <li>ERROR_NULL_ARGUMENT when fileName is null</li>
	 *                </ul>
	 * 
	 * @since 3.6
	 */
	public static boolean launch(String fileName, String workingDir) {
		// TODO
		return false;
	}

	/**
	 * Executes the program with the file as the single argument in the
	 * operating system. It is the responsibility of the programmer to ensure
	 * that the file contains valid data for this program.
	 * 
	 * @param fileName
	 *            the file or program name
	 * @return <code>true</code> if the file is launched, otherwise
	 *         <code>false</code>
	 * 
	 * @exception IllegalArgumentException
	 *                <ul>
	 *                <li>ERROR_NULL_ARGUMENT when fileName is null</li>
	 *                </ul>
	 */
	public boolean execute(String fileName) {
		// TODO
		return false;
	}

	/**
	 * Returns the receiver's image data. This is the icon that is associated
	 * with the receiver in the operating system.
	 * 
	 * @return the image data for the program, may be null
	 */
	public ImageData getImageData() {
		// TODO
		return null;
	}

	/**
	 * Returns the receiver's name. This is as short and descriptive a name as
	 * possible for the program. If the program has no descriptive name, this
	 * string may be the executable name, path or empty.
	 * 
	 * @return the name of the program
	 */
	public String getName() {
		// TODO
		return null;
	}

}
