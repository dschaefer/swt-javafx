/*******************************************************************************
 * Copyright (c) 2007, 2012 IBM Corporation and others.
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
 * The class <code>URLTransfer</code> provides a platform specific mechanism for
 * converting text in URL format represented as a java <code>String</code> to a
 * platform specific representation of the data and vice versa. The string must
 * contain a fully specified url.
 * 
 * <p>
 * An example of a java <code>String</code> containing a URL is shown below:
 * </p>
 * 
 * <code><pre>
 *     String url = "http://www.eclipse.org";
 * </code></pre>
 * 
 * @see Transfer
 * @since 3.4
 */
public class URLTransfer extends ByteArrayTransfer {

	static URLTransfer _instance = new URLTransfer();

	private URLTransfer() {
	}

	/**
	 * Returns the singleton instance of the URLTransfer class.
	 * 
	 * @return the singleton instance of the URLTransfer class
	 */
	public static URLTransfer getInstance() {
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
