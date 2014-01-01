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


/**
 * Implementers of <code>Drawable</code> can have a graphics context (GC)
 * created for them, and then they can be drawn on by sending messages to
 * their associated GC. SWT images, and device objects such as the Display
 * device and the Printer device, are drawables.
 * <p>
 * <b>IMPORTANT:</b> This interface is <em>not</em> part of the SWT
 * public API. It is marked public only so that it can be shared
 * within the packages provided by SWT. It should never be
 * referenced from application code.
 * </p>
 * 
 * @see Device
 * @see Image
 * @see GC
 */
public interface Drawable {

}
