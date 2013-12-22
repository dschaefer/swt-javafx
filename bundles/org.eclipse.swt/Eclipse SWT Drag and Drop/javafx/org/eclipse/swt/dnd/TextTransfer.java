/*******************************************************************************
 * Copyright (c) 2000, 2012 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.swt.dnd;

/**
 * The class <code>TextTransfer</code> provides a platform specific mechanism
 * for converting plain text represented as a java <code>String</code> to a
 * platform specific representation of the data and vice versa.
 * 
 * <p>
 * An example of a java <code>String</code> containing plain text is shown
 * below:
 * </p>
 * 
 * <code><pre>
 *     String textData = "Hello World";
 * </code></pre>
 * 
 * <p>
 * Note the <code>TextTransfer</code> does not change the content of the text
 * data. For a better integration with the platform, the application should
 * convert the line delimiters used in the text data to the standard line
 * delimiter used by the platform.
 * </p>
 * 
 * @see Transfer
 */
public class TextTransfer extends ByteArrayTransfer {

	private static TextTransfer _instance = new TextTransfer();

	private TextTransfer() {
	}

	/**
	 * Returns the singleton instance of the TextTransfer class.
	 * 
	 * @return the singleton instance of the TextTransfer class
	 */
	public static TextTransfer getInstance() {
		return _instance;
	}

	@Override
	public TransferData[] getSupportedTypes() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isSupportedType(TransferData transferData) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	protected String[] getTypeNames() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected int[] getTypeIds() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void javaToNative(Object object, TransferData transferData) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Object nativeToJava(TransferData transferData) {
		// TODO Auto-generated method stub
		return null;
	}

}
