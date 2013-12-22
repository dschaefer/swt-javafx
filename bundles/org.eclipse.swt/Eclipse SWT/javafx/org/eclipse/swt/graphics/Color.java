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
package org.eclipse.swt.graphics;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;

/**
 * Instances of this class manage the operating system resources that implement
 * SWT's RGB color model. To create a color you can either specify the
 * individual color components as integers in the range 0 to 255 or provide an
 * instance of an <code>RGB</code>.
 * <p>
 * Application code must explicitly invoke the <code>Color.dispose()</code>
 * method to release the operating system resources managed by each instance
 * when those instances are no longer required.
 * </p>
 * 
 * @see RGB
 * @see Device#getSystemColor
 * @see <a href="http://www.eclipse.org/swt/snippets/#color">Color and RGB
 *      snippets</a>
 * @see <a href="http://www.eclipse.org/swt/examples.php">SWT Example:
 *      PaintExample</a>
 * @see <a href="http://www.eclipse.org/swt/">Sample code and further
 *      information</a>
 */
public final class Color extends Resource {
	/**
	 * Constructs a new instance of this class given a device and the desired
	 * red, green and blue values expressed as ints in the range 0 to 255 (where
	 * 0 is black and 255 is full brightness). On limited color devices, the
	 * color instance created by this call may not have the same RGB values as
	 * the ones specified by the arguments. The RGB values on the returned
	 * instance will be the color values of the operating system color.
	 * <p>
	 * You must dispose the color when it is no longer required.
	 * </p>
	 * 
	 * @param device
	 *            the device on which to allocate the color
	 * @param red
	 *            the amount of red in the color
	 * @param green
	 *            the amount of green in the color
	 * @param blue
	 *            the amount of blue in the color
	 * 
	 * @exception IllegalArgumentException
	 *                <ul>
	 *                <li>ERROR_NULL_ARGUMENT - if device is null and there is
	 *                no current device</li>
	 *                <li>ERROR_INVALID_ARGUMENT - if the red, green or blue
	 *                argument is not between 0 and 255</li>
	 *                </ul>
	 * 
	 * @see #dispose
	 */
	public Color(Device device, int red, int green, int blue) {
		super(device);
		// TODO
	}

	/**
	 * Constructs a new instance of this class given a device and an
	 * <code>RGB</code> describing the desired red, green and blue values. On
	 * limited color devices, the color instance created by this call may not
	 * have the same RGB values as the ones specified by the argument. The RGB
	 * values on the returned instance will be the color values of the operating
	 * system color.
	 * <p>
	 * You must dispose the color when it is no longer required.
	 * </p>
	 * 
	 * @param device
	 *            the device on which to allocate the color
	 * @param rgb
	 *            the RGB values of the desired color
	 * 
	 * @exception IllegalArgumentException
	 *                <ul>
	 *                <li>ERROR_NULL_ARGUMENT - if device is null and there is
	 *                no current device</li>
	 *                <li>ERROR_NULL_ARGUMENT - if the rgb argument is null</li>
	 *                <li>ERROR_INVALID_ARGUMENT - if the red, green or blue
	 *                components of the argument are not between 0 and 255</li>
	 *                </ul>
	 * 
	 * @see #dispose
	 */
	public Color(Device device, RGB rgb) {
		super(device);
		// TODO
	}

	/**
	 * Returns the amount of blue in the color, from 0 to 255.
	 * 
	 * @return the blue component of the color
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_GRAPHIC_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 */
	public int getBlue() {
		// TODO
		return 0;
	}

	/**
	 * Returns the amount of green in the color, from 0 to 255.
	 * 
	 * @return the green component of the color
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_GRAPHIC_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 */
	public int getGreen() {
		// TODO
		return 0;
	}

	/**
	 * Returns the amount of red in the color, from 0 to 255.
	 * 
	 * @return the red component of the color
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_GRAPHIC_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 */
	public int getRed() {
		// TODO
		return 0;
	}

	/**
	 * Returns an <code>RGB</code> representing the receiver.
	 * 
	 * @return the RGB for the color
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_GRAPHIC_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 */
	public RGB getRGB() {
		if (isDisposed())
			SWT.error(SWT.ERROR_GRAPHIC_DISPOSED);
		return new RGB(getRed(), getGreen(), getBlue());
	}

	/**
	 * Returns <code>true</code> if the color has been disposed, and
	 * <code>false</code> otherwise.
	 * <p>
	 * This method gets the dispose state for the color. When a color has been
	 * disposed, it is an error to invoke any other method (except
	 * {@link #dispose()}) using the color.
	 * 
	 * @return <code>true</code> when the color is disposed and
	 *         <code>false</code> otherwise
	 */
	@Override
	public boolean isDisposed() {
		// TODO
		return false;
	}

	/**
	 * Returns a string containing a concise, human-readable description of the
	 * receiver.
	 * 
	 * @return a string representation of the receiver
	 */
	@Override
	public String toString() {
		if (isDisposed())
			return "Color {*DISPOSED*}";
		return "Color {" + getRed() + ", " + getGreen() + ", " + getBlue()
				+ "}";
	}

}
