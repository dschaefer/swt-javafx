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
 * The class <code>ImageTransfer</code> provides a platform specific mechanism
 * for converting an Image represented as a java <code>ImageData</code> to a
 * platform specific representation of the data and vice versa.
 * 
 * <p>
 * An example of a java <code>ImageData</code> is shown below:
 * </p>
 * 
 * <code><pre>
 *     Image image = new Image(display, "C:\temp\img1.gif");
 * 	   ImageData imgData = image.getImageData();
 * </code></pre>
 * 
 * @see Transfer
 * 
 * @since 3.4
 */
public class ImageTransfer extends ByteArrayTransfer {

	private static ImageTransfer _instance = new ImageTransfer();

	private ImageTransfer() {
	}

	/**
	 * Returns the singleton instance of the ImageTransfer class.
	 * 
	 * @return the singleton instance of the ImageTransfer class
	 */
	public static ImageTransfer getInstance() {
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
