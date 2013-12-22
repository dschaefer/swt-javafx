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
 * The class <code>HTMLTransfer</code> provides a platform specific mechanism
 * for converting text in HTML format represented as a java <code>String</code>
 * to a platform specific representation of the data and vice versa.
 * 
 * <p>
 * An example of a java <code>String</code> containing HTML text is shown below:
 * </p>
 * 
 * <code><pre>
 *     String htmlData = "<p>This is a paragraph of text.</p>";
 * </code></pre>
 * 
 * @see Transfer
 */
public class HTMLTransfer extends ByteArrayTransfer {

	private static HTMLTransfer _instance = new HTMLTransfer();

	private HTMLTransfer() {
	}

	/**
	 * Returns the singleton instance of the HTMLTransfer class.
	 * 
	 * @return the singleton instance of the HTMLTransfer class
	 */
	public static HTMLTransfer getInstance() {
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
