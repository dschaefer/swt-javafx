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
 * The class <code>RTFTransfer</code> provides a platform specific mechanism for
 * converting text in RTF format represented as a java <code>String</code> to a
 * platform specific representation of the data and vice versa.
 * 
 * <p>
 * An example of a java <code>String</code> containing RTF text is shown below:
 * </p>
 * 
 * <code><pre>
 *     String rtfData = "{\\rtf1{\\colortbl;\\red255\\green0\\blue0;}\\uc1\\b\\i Hello World}";
 * </code></pre>
 * 
 * @see Transfer
 */
public class RTFTransfer extends ByteArrayTransfer {

	private static RTFTransfer _instance = new RTFTransfer();

	private RTFTransfer() {
	}

	/**
	 * Returns the singleton instance of the RTFTransfer class.
	 * 
	 * @return the singleton instance of the RTFTransfer class
	 */
	public static RTFTransfer getInstance() {
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
