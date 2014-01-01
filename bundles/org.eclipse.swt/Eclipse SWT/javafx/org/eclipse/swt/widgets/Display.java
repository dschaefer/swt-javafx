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
package org.eclipse.swt.widgets;

import javafx.application.Platform;
import javafx.stage.Stage;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.DeviceData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

/**
 * Instances of this class are responsible for managing the connection between
 * SWT and the underlying operating system. Their most important function is to
 * implement the SWT event loop in terms of the platform event model. They also
 * provide various methods for accessing information about the operating system,
 * and have overall control over the operating system resources which SWT
 * allocates.
 * <p>
 * Applications which are built with SWT will <em>almost always</em> require
 * only a single display. In particular, some platforms which SWT supports will
 * not allow more than one <em>active</em> display. In other words, some
 * platforms do not support creating a new display if one already exists that
 * has not been sent the <code>dispose()</code> message.
 * <p>
 * In SWT, the thread which creates a <code>Display</code> instance is
 * distinguished as the <em>user-interface thread</em> for that display.
 * </p>
 * The user-interface thread for a particular display has the following special
 * attributes:
 * <ul>
 * <li>
 * The event loop for that display must be run from the thread.</li>
 * <li>
 * Some SWT API methods (notably, most of the public methods in
 * <code>Widget</code> and its subclasses), may only be called from the thread.
 * (To support multi-threaded user-interface applications, class
 * <code>Display</code> provides inter-thread communication methods which allow
 * threads other than the user-interface thread to request that it perform
 * operations on their behalf.)</li>
 * <li>
 * The thread is not allowed to construct other <code>Display</code>s until that
 * display has been disposed. (Note that, this is in addition to the restriction
 * mentioned above concerning platform support for multiple displays. Thus, the
 * only way to have multiple simultaneously active displays, even on platforms
 * which support it, is to have multiple threads.)</li>
 * </ul>
 * Enforcing these attributes allows SWT to be implemented directly on the
 * underlying operating system's event model. This has numerous benefits
 * including smaller footprint, better use of resources, safer memory
 * management, clearer program logic, better performance, and fewer overall
 * operating system threads required. The down side however, is that care must
 * be taken (only) when constructing multi-threaded applications to use the
 * inter-thread communication mechanisms which this class provides when
 * required. </p>
 * <p>
 * All SWT API methods which may only be called from the user-interface thread
 * are distinguished in their documentation by indicating that they throw the "
 * <code>ERROR_THREAD_INVALID_ACCESS</code>" SWT exception.
 * </p>
 * <dl>
 * <dt><b>Styles:</b></dt>
 * <dd>(none)</dd>
 * <dt><b>Events:</b></dt>
 * <dd>Close, Dispose, OpenDocument, Settings, Skin</dd>
 * </dl>
 * <p>
 * IMPORTANT: This class is <em>not</em> intended to be subclassed.
 * </p>
 * 
 * @see #syncExec
 * @see #asyncExec
 * @see #wake
 * @see #readAndDispatch
 * @see #sleep
 * @see Device#dispose
 * @see <a href="http://www.eclipse.org/swt/snippets/#display">Display
 *      snippets</a>
 * @see <a href="http://www.eclipse.org/swt/">Sample code and further
 *      information</a>
 * @noextend This class is not intended to be subclassed by clients.
 */
public class Display extends Device {

	Tray tray;
	public static Stage primaryStage;
	static Shell primaryShell;
	static Object startupMutex = new Object();
	static Display defaultDisplay;

	/*
	 * TEMPORARY CODE. Install the runnable that gets the current display. This
	 * code will be removed in the future.
	 */
	static {
		DeviceFinder = new Runnable() {
			public void run() {
				Device device = getCurrent();
				if (device == null) {
					device = getDefault();
				}
				setDevice(device);
			}
		};
	}

	/*
	 * TEMPORARY CODE.
	 */
	static void setDevice(Device device) {
		CurrentDevice = device;
	}

	/**
	 * Constructs a new instance of this class.
	 * <p>
	 * Note: The resulting display is marked as the <em>current</em> display. If
	 * this is the first display which has been constructed since the
	 * application started, it is also marked as the <em>default</em> display.
	 * </p>
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if called from a thread
	 *                that already created an existing display</li>
	 *                <li>ERROR_INVALID_SUBCLASS - if this class is not an
	 *                allowed subclass</li>
	 *                </ul>
	 * 
	 * @see #getCurrent
	 * @see #getDefault
	 * @see Widget#checkSubclass
	 * @see Shell
	 */
	public Display() {
		this(null);
	}

	/**
	 * Constructs a new instance of this class using the parameter.
	 * 
	 * @param data
	 *            the device data
	 */
	public Display(DeviceData data) {
		super(data);
		defaultDisplay = this;
	}

	/**
	 * Adds the listener to the collection of listeners who will be notified
	 * when an event of the given type occurs anywhere in a widget. The event
	 * type is one of the event constants defined in class <code>SWT</code>.
	 * When the event does occur, the listener is notified by sending it the
	 * <code>handleEvent()</code> message.
	 * <p>
	 * Setting the type of an event to <code>SWT.None</code> from within the
	 * <code>handleEvent()</code> method can be used to change the event type
	 * and stop subsequent Java listeners from running. Because event filters
	 * run before other listeners, event filters can both block other listeners
	 * and set arbitrary fields within an event. For this reason, event filters
	 * are both powerful and dangerous. They should generally be avoided for
	 * performance, debugging and code maintenance reasons.
	 * </p>
	 * 
	 * @param eventType
	 *            the type of event to listen for
	 * @param listener
	 *            the listener which should be notified when the event occurs
	 * 
	 * @exception IllegalArgumentException
	 *                <ul>
	 *                <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
	 *                </ul>
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                <li>ERROR_DEVICE_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 * 
	 * @see Listener
	 * @see SWT
	 * @see #removeFilter
	 * @see #removeListener
	 * 
	 * @since 3.0
	 */
	public void addFilter(int eventType, Listener listener) {
		// TODO
	}

	/**
	 * Adds the listener to the collection of listeners who will be notified
	 * when an event of the given type occurs. The event type is one of the
	 * event constants defined in class <code>SWT</code>. When the event does
	 * occur in the display, the listener is notified by sending it the
	 * <code>handleEvent()</code> message.
	 * 
	 * @param eventType
	 *            the type of event to listen for
	 * @param listener
	 *            the listener which should be notified when the event occurs
	 * 
	 * @exception IllegalArgumentException
	 *                <ul>
	 *                <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
	 *                </ul>
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                <li>ERROR_DEVICE_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 * 
	 * @see Listener
	 * @see SWT
	 * @see #removeListener
	 * 
	 * @since 2.0
	 */
	public void addListener(int eventType, Listener listener) {
		// TODO
	}

	/**
	 * Causes the <code>run()</code> method of the runnable to be invoked by the
	 * user-interface thread at the next reasonable opportunity. The caller of
	 * this method continues to run in parallel, and is not notified when the
	 * runnable has completed. Specifying <code>null</code> as the runnable
	 * simply wakes the user-interface thread when run.
	 * <p>
	 * Note that at the time the runnable is invoked, widgets that have the
	 * receiver as their display may have been disposed. Therefore, it is
	 * necessary to check for this case inside the runnable before accessing the
	 * widget.
	 * </p>
	 * 
	 * @param runnable
	 *            code to run on the user-interface thread or <code>null</code>
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_DEVICE_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 * 
	 * @see #syncExec
	 */
	public void asyncExec(Runnable runnable) {
		// TODO
	}

	/**
	 * Causes the system hardware to emit a short sound (if it supports this
	 * capability).
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                <li>ERROR_DEVICE_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 */
	public void beep() {
		// TODO
	}

	/**
	 * Requests that the connection between SWT and the underlying operating
	 * system be closed.
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                <li>ERROR_DEVICE_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 * 
	 * @see Device#dispose
	 * 
	 * @since 2.0
	 */
	public void close() {
		// TODO
	}

	/**
	 * Returns the display which the given thread is the user-interface thread
	 * for, or null if the given thread is not a user-interface thread for any
	 * display. Specifying <code>null</code> as the thread will return
	 * <code>null</code> for the display.
	 * 
	 * @param thread
	 *            the user-interface thread
	 * @return the display for the given thread
	 */
	public static Display findDisplay(Thread thread) {
		// TODO
		return null;
	}

	/**
	 * Causes the <code>run()</code> method of the runnable to be invoked by the
	 * user-interface thread just before the receiver is disposed. Specifying a
	 * <code>null</code> runnable is ignored.
	 * 
	 * @param runnable
	 *            code to run at dispose time.
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                <li>ERROR_DEVICE_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 */
	public void disposeExec(Runnable runnable) {
		// TODO
	}

	/**
	 * Given the operating system handle for a widget, returns the instance of
	 * the <code>Widget</code> subclass which represents it in the currently
	 * running application, if such exists, or null if no matching widget can be
	 * found.
	 * <p>
	 * <b>IMPORTANT:</b> This method should not be called from application code.
	 * The arguments are platform-specific.
	 * </p>
	 * 
	 * @param handle
	 *            the handle for the widget
	 * @return the SWT widget that the handle represents
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                <li>ERROR_DEVICE_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 * 
	 * @noreference This method is not intended to be referenced by clients.
	 */
	public Widget findWidget(long /* int */handle) {
		// TODO
		return null;
	}

	/**
	 * Given the operating system handle for a widget, and widget-specific id,
	 * returns the instance of the <code>Widget</code> subclass which represents
	 * the handle/id pair in the currently running application, if such exists,
	 * or null if no matching widget can be found.
	 * <p>
	 * <b>IMPORTANT:</b> This method should not be called from application code.
	 * The arguments are platform-specific.
	 * </p>
	 * 
	 * @param handle
	 *            the handle for the widget
	 * @param id
	 *            the id for the subwidget (usually an item)
	 * @return the SWT widget that the handle/id pair represents
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                <li>ERROR_DEVICE_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 * 
	 * @noreference This method is not intended to be referenced by clients.
	 * 
	 * @since 3.1
	 */
	public Widget findWidget(long /* int */handle, long /* int */id) {
		// TODO
		return null;
	}

	/**
	 * Given a widget and a widget-specific id, returns the instance of the
	 * <code>Widget</code> subclass which represents the widget/id pair in the
	 * currently running application, if such exists, or null if no matching
	 * widget can be found.
	 * 
	 * @param widget
	 *            the widget
	 * @param id
	 *            the id for the subwidget (usually an item)
	 * @return the SWT subwidget (usually an item) that the widget/id pair
	 *         represents
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                <li>ERROR_DEVICE_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 * 
	 * @noreference This method is not intended to be referenced by clients.
	 * 
	 * @since 3.3
	 */
	public Widget findWidget(Widget widget, long /* int */id) {
		// TODO
		return null;
	}

	/**
	 * Returns the currently active <code>Shell</code>, or null if no shell
	 * belonging to the currently running application is active.
	 * 
	 * @return the active shell or null
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                <li>ERROR_DEVICE_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 */
	public Shell getActiveShell() {
		// TODO
		return null;
	}

	/**
	 * Returns the display which the currently running thread is the
	 * user-interface thread for, or null if the currently running thread is not
	 * a user-interface thread for any display.
	 * 
	 * @return the current display
	 */
	public static Display getCurrent() {
		return getDefault();
	}

	/**
	 * Returns the control which the on-screen pointer is currently over top of,
	 * or null if it is not currently over one of the controls built by the
	 * currently running application.
	 * 
	 * @return the control under the cursor or <code>null</code>
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                <li>ERROR_DEVICE_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 */
	public Control getCursorControl() {
		// TODO
		return null;
	}

	/**
	 * Returns the location of the on-screen pointer relative to the top left
	 * corner of the screen.
	 * 
	 * @return the cursor location
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                <li>ERROR_DEVICE_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 */
	public Point getCursorLocation() {
		// TODO
		return null;
	}

	/**
	 * Returns an array containing the recommended cursor sizes.
	 * 
	 * @return the array of cursor sizes
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                <li>ERROR_DEVICE_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 * 
	 * @since 3.0
	 */
	public Point[] getCursorSizes() {
		// TODO
		return null;
	}

	/**
	 * Returns the application defined property of the receiver with the
	 * specified name, or null if it has not been set.
	 * <p>
	 * Applications may have associated arbitrary objects with the receiver in
	 * this fashion. If the objects stored in the properties need to be notified
	 * when the display is disposed of, it is the application's responsibility
	 * to provide a <code>disposeExec()</code> handler which does so.
	 * </p>
	 * 
	 * @param key
	 *            the name of the property
	 * @return the value of the property or null if it has not been set
	 * 
	 * @exception IllegalArgumentException
	 *                <ul>
	 *                <li>ERROR_NULL_ARGUMENT - if the key is null</li>
	 *                </ul>
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                <li>ERROR_DEVICE_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 * 
	 * @see #setData(String, Object)
	 * @see #disposeExec(Runnable)
	 */
	public Object getData(String key) {
		// TODO
		return null;
	}

	/**
	 * Returns the application defined, display specific data associated with
	 * the receiver, or null if it has not been set. The
	 * <em>display specific data</em> is a single, unnamed field that is stored
	 * with every display.
	 * <p>
	 * Applications may put arbitrary objects in this field. If the object
	 * stored in the display specific data needs to be notified when the display
	 * is disposed of, it is the application's responsibility to provide a
	 * <code>disposeExec()</code> handler which does so.
	 * </p>
	 * 
	 * @return the display specific data
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                <li>ERROR_DEVICE_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 * 
	 * @see #setData(Object)
	 * @see #disposeExec(Runnable)
	 */
	public Object getData() {
		// TODO
		return null;
	}

	/**
	 * Returns the default display. One is created (making the thread that
	 * invokes this method its user-interface thread) if it did not already
	 * exist.
	 * 
	 * @return the default display
	 */
	public static Display getDefault() {
		return defaultDisplay;
	}

	static <T> boolean isValidClass(Class<T> clazz) {
		// TODO
		return false;
	}

	/**
	 * Returns the single instance of the application menu bar, or
	 * <code>null</code> if there is no application menu bar for the platform.
	 * 
	 * @return the application menu bar, or <code>null</code>
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_DEVICE_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 * 
	 * @since 3.7
	 */
	public Menu getMenuBar() {
		// TODO
		return null;
	}

	/**
	 * Returns the button dismissal alignment, one of <code>LEFT</code> or
	 * <code>RIGHT</code>. The button dismissal alignment is the ordering that
	 * should be used when positioning the default dismissal button for a
	 * dialog. For example, in a dialog that contains an OK and CANCEL button,
	 * on platforms where the button dismissal alignment is <code>LEFT</code>,
	 * the button ordering should be OK/CANCEL. When button dismissal alignment
	 * is <code>RIGHT</code>, the button ordering should be CANCEL/OK.
	 * 
	 * @return the button dismissal order
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                <li>ERROR_DEVICE_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 * 
	 * @since 2.1
	 */
	public int getDismissalAlignment() {
		// TODO
		return 0;
	}

	/**
	 * Returns the longest duration, in milliseconds, between two mouse button
	 * clicks that will be considered a <em>double click</em> by the underlying
	 * operating system.
	 * 
	 * @return the double click time
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                <li>ERROR_DEVICE_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 */
	public int getDoubleClickTime() {
		// TODO
		return 0;
	}

	/**
	 * Returns the control which currently has keyboard focus, or null if
	 * keyboard events are not currently going to any of the controls built by
	 * the currently running application.
	 * 
	 * @return the focus control or <code>null</code>
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                <li>ERROR_DEVICE_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 */
	public Control getFocusControl() {
		// TODO
		return null;
	}

	/**
	 * Returns true when the high contrast mode is enabled. Otherwise, false is
	 * returned.
	 * <p>
	 * Note: This operation is a hint and is not supported on platforms that do
	 * not have this concept.
	 * </p>
	 * 
	 * @return the high contrast mode
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                <li>ERROR_DEVICE_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 * 
	 * @since 3.0
	 */
	public boolean getHighContrast() {
		// TODO
		return false;
	}

	/**
	 * Returns the maximum allowed depth of icons on this display, in bits per
	 * pixel. On some platforms, this may be different than the actual depth of
	 * the display.
	 * 
	 * @return the maximum icon depth
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                <li>ERROR_DEVICE_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 * 
	 * @see Device#getDepth
	 */
	public int getIconDepth() {
		// TODO
		return 0;
	}

	/**
	 * Returns an array containing the recommended icon sizes.
	 * 
	 * @return the array of icon sizes
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                <li>ERROR_DEVICE_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 * 
	 * @see Decorations#setImages(Image[])
	 * 
	 * @since 3.0
	 */
	public Point[] getIconSizes() {
		// TODO
		return null;
	}

	/**
	 * Returns an array of monitors attached to the device.
	 * 
	 * @return the array of monitors
	 * 
	 * @since 3.0
	 */
	public Monitor[] getMonitors() {
		// TODO
		return null;
	}

	/**
	 * Returns the primary monitor for that device.
	 * 
	 * @return the primary monitor
	 * 
	 * @since 3.0
	 */
	public Monitor getPrimaryMonitor() {
		// TODO
		return null;
	}

	/**
	 * Returns a (possibly empty) array containing all shells which have not
	 * been disposed and have the receiver as their display.
	 * 
	 * @return the receiver's shells
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                <li>ERROR_DEVICE_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 */
	public Shell[] getShells() {
		// TODO
		return null;
	}

	/**
	 * Gets the synchronizer used by the display.
	 * 
	 * @return the receiver's synchronizer
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                <li>ERROR_DEVICE_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 * 
	 * @since 3.4
	 */
	public Synchronizer getSynchronizer() {
		// TODO
		return null;
	}

	/**
	 * Returns the thread that has invoked <code>syncExec</code> or null if no
	 * such runnable is currently being invoked by the user-interface thread.
	 * <p>
	 * Note: If a runnable invoked by asyncExec is currently running, this
	 * method will return null.
	 * </p>
	 * 
	 * @return the receiver's sync-interface thread
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_DEVICE_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 */
	public Thread getSyncThread() {
		// TODO
		return null;
	}

	/**
	 * Returns the matching standard platform cursor for the given constant,
	 * which should be one of the cursor constants specified in class
	 * <code>SWT</code>. This cursor should not be free'd because it was
	 * allocated by the system, not the application. A value of
	 * <code>null</code> will be returned if the supplied constant is not an SWT
	 * cursor constant.
	 * 
	 * @param id
	 *            the SWT cursor constant
	 * @return the corresponding cursor or <code>null</code>
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                <li>ERROR_DEVICE_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 * 
	 * @see SWT#CURSOR_ARROW
	 * @see SWT#CURSOR_WAIT
	 * @see SWT#CURSOR_CROSS
	 * @see SWT#CURSOR_APPSTARTING
	 * @see SWT#CURSOR_HELP
	 * @see SWT#CURSOR_SIZEALL
	 * @see SWT#CURSOR_SIZENESW
	 * @see SWT#CURSOR_SIZENS
	 * @see SWT#CURSOR_SIZENWSE
	 * @see SWT#CURSOR_SIZEWE
	 * @see SWT#CURSOR_SIZEN
	 * @see SWT#CURSOR_SIZES
	 * @see SWT#CURSOR_SIZEE
	 * @see SWT#CURSOR_SIZEW
	 * @see SWT#CURSOR_SIZENE
	 * @see SWT#CURSOR_SIZESE
	 * @see SWT#CURSOR_SIZESW
	 * @see SWT#CURSOR_SIZENW
	 * @see SWT#CURSOR_UPARROW
	 * @see SWT#CURSOR_IBEAM
	 * @see SWT#CURSOR_NO
	 * @see SWT#CURSOR_HAND
	 * 
	 * @since 3.0
	 */
	public Cursor getSystemCursor(int id) {
		// TODO
		return null;
	}

	/**
	 * Returns the matching standard platform image for the given constant,
	 * which should be one of the icon constants specified in class
	 * <code>SWT</code>. This image should not be free'd because it was
	 * allocated by the system, not the application. A value of
	 * <code>null</code> will be returned either if the supplied constant is not
	 * an SWT icon constant or if the platform does not define an image that
	 * corresponds to the constant.
	 * 
	 * @param id
	 *            the SWT icon constant
	 * @return the corresponding image or <code>null</code>
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                <li>ERROR_DEVICE_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 * 
	 * @see SWT#ICON_ERROR
	 * @see SWT#ICON_INFORMATION
	 * @see SWT#ICON_QUESTION
	 * @see SWT#ICON_WARNING
	 * @see SWT#ICON_WORKING
	 * 
	 * @since 3.0
	 */
	public Image getSystemImage(int id) {
		// TODO
		return null;
	}

	/**
	 * Returns the single instance of the system-provided menu for the
	 * application, or <code>null</code> on platforms where no menu is provided
	 * for the application.
	 * 
	 * @return the system menu, or <code>null</code>
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                <li>ERROR_DEVICE_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 * 
	 * @since 3.7
	 */
	public Menu getSystemMenu() {
		// TODO
		return null;
	}

	/**
	 * Returns the single instance of the system taskBar or null when there is
	 * no system taskBar available for the platform.
	 * 
	 * @return the system taskBar or <code>null</code>
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_DEVICE_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 * 
	 * @since 3.6
	 */
	public TaskBar getSystemTaskBar() {
		// TODO
		return null;
	}

	/**
	 * Returns the single instance of the system tray or null when there is no
	 * system tray available for the platform.
	 * 
	 * @return the system tray or <code>null</code>
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_DEVICE_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 * 
	 * @since 3.0
	 */
	public Tray getSystemTray() {
		// TODO
		return null;
	}

	/**
	 * Returns the user-interface thread for the receiver.
	 * 
	 * @return the receiver's user-interface thread
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_DEVICE_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 */
	public Thread getThread() {
		// TODO
		return null;
	}

	/**
	 * Returns a boolean indicating whether a touch-aware input device is
	 * attached to the system and is ready for use.
	 * 
	 * @return <code>true</code> if a touch-aware input device is detected, or
	 *         <code>false</code> otherwise
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                <li>ERROR_DEVICE_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 * 
	 * @since 3.7
	 */
	public boolean getTouchEnabled() {
		// TODO
		return false;
	}

	boolean isValidThread() {
		return Platform.isFxApplicationThread();
	}

	/**
	 * Maps a point from one coordinate system to another. When the control is
	 * null, coordinates are mapped to the display.
	 * <p>
	 * NOTE: On right-to-left platforms where the coordinate systems are
	 * mirrored, special care needs to be taken when mapping coordinates from
	 * one control to another to ensure the result is correctly mirrored.
	 * 
	 * Mapping a point that is the origin of a rectangle and then adding the
	 * width and height is not equivalent to mapping the rectangle. When one
	 * control is mirrored and the other is not, adding the width and height to
	 * a point that was mapped causes the rectangle to extend in the wrong
	 * direction. Mapping the entire rectangle instead of just one point causes
	 * both the origin and the corner of the rectangle to be mapped.
	 * </p>
	 * 
	 * @param from
	 *            the source <code>Control</code> or <code>null</code>
	 * @param to
	 *            the destination <code>Control</code> or <code>null</code>
	 * @param point
	 *            to be mapped
	 * @return point with mapped coordinates
	 * 
	 * @exception IllegalArgumentException
	 *                <ul>
	 *                <li>ERROR_NULL_ARGUMENT - if the point is null</li>
	 *                <li>ERROR_INVALID_ARGUMENT - if the Control from or the
	 *                Control to have been disposed</li>
	 *                </ul>
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                <li>ERROR_DEVICE_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 * 
	 * @since 2.1.2
	 */
	public Point map(Control from, Control to, Point point) {
		// TODO
		return null;
	}

	/**
	 * Maps a point from one coordinate system to another. When the control is
	 * null, coordinates are mapped to the display.
	 * <p>
	 * NOTE: On right-to-left platforms where the coordinate systems are
	 * mirrored, special care needs to be taken when mapping coordinates from
	 * one control to another to ensure the result is correctly mirrored.
	 * 
	 * Mapping a point that is the origin of a rectangle and then adding the
	 * width and height is not equivalent to mapping the rectangle. When one
	 * control is mirrored and the other is not, adding the width and height to
	 * a point that was mapped causes the rectangle to extend in the wrong
	 * direction. Mapping the entire rectangle instead of just one point causes
	 * both the origin and the corner of the rectangle to be mapped.
	 * </p>
	 * 
	 * @param from
	 *            the source <code>Control</code> or <code>null</code>
	 * @param to
	 *            the destination <code>Control</code> or <code>null</code>
	 * @param x
	 *            coordinates to be mapped
	 * @param y
	 *            coordinates to be mapped
	 * @return point with mapped coordinates
	 * 
	 * @exception IllegalArgumentException
	 *                <ul>
	 *                <li>ERROR_INVALID_ARGUMENT - if the Control from or the
	 *                Control to have been disposed</li>
	 *                </ul>
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                <li>ERROR_DEVICE_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 * 
	 * @since 2.1.2
	 */
	public Point map(Control from, Control to, int x, int y) {
		// TODO
		return null;
	}

	/**
	 * Maps a point from one coordinate system to another. When the control is
	 * null, coordinates are mapped to the display.
	 * <p>
	 * NOTE: On right-to-left platforms where the coordinate systems are
	 * mirrored, special care needs to be taken when mapping coordinates from
	 * one control to another to ensure the result is correctly mirrored.
	 * 
	 * Mapping a point that is the origin of a rectangle and then adding the
	 * width and height is not equivalent to mapping the rectangle. When one
	 * control is mirrored and the other is not, adding the width and height to
	 * a point that was mapped causes the rectangle to extend in the wrong
	 * direction. Mapping the entire rectangle instead of just one point causes
	 * both the origin and the corner of the rectangle to be mapped.
	 * </p>
	 * 
	 * @param from
	 *            the source <code>Control</code> or <code>null</code>
	 * @param to
	 *            the destination <code>Control</code> or <code>null</code>
	 * @param rectangle
	 *            to be mapped
	 * @return rectangle with mapped coordinates
	 * 
	 * @exception IllegalArgumentException
	 *                <ul>
	 *                <li>ERROR_NULL_ARGUMENT - if the rectangle is null</li>
	 *                <li>ERROR_INVALID_ARGUMENT - if the Control from or the
	 *                Control to have been disposed</li>
	 *                </ul>
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                <li>ERROR_DEVICE_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 * 
	 * @since 2.1.2
	 */
	public Rectangle map(Control from, Control to, Rectangle rectangle) {
		// TODO
		return null;
	}

	/**
	 * Maps a point from one coordinate system to another. When the control is
	 * null, coordinates are mapped to the display.
	 * <p>
	 * NOTE: On right-to-left platforms where the coordinate systems are
	 * mirrored, special care needs to be taken when mapping coordinates from
	 * one control to another to ensure the result is correctly mirrored.
	 * 
	 * Mapping a point that is the origin of a rectangle and then adding the
	 * width and height is not equivalent to mapping the rectangle. When one
	 * control is mirrored and the other is not, adding the width and height to
	 * a point that was mapped causes the rectangle to extend in the wrong
	 * direction. Mapping the entire rectangle instead of just one point causes
	 * both the origin and the corner of the rectangle to be mapped.
	 * </p>
	 * 
	 * @param from
	 *            the source <code>Control</code> or <code>null</code>
	 * @param to
	 *            the destination <code>Control</code> or <code>null</code>
	 * @param x
	 *            coordinates to be mapped
	 * @param y
	 *            coordinates to be mapped
	 * @param width
	 *            coordinates to be mapped
	 * @param height
	 *            coordinates to be mapped
	 * @return rectangle with mapped coordinates
	 * 
	 * @exception IllegalArgumentException
	 *                <ul>
	 *                <li>ERROR_INVALID_ARGUMENT - if the Control from or the
	 *                Control to have been disposed</li>
	 *                </ul>
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                <li>ERROR_DEVICE_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 * 
	 * @since 2.1.2
	 */
	public Rectangle map(Control from, Control to, int x, int y, int width,
			int height) {
		// TODO
		return null;
	}

	/**
	 * Generate a low level system event.
	 * 
	 * <code>post</code> is used to generate low level keyboard and mouse
	 * events. The intent is to enable automated UI testing by simulating the
	 * input from the user. Most SWT applications should never need to call this
	 * method.
	 * <p>
	 * Note that this operation can fail when the operating system fails to
	 * generate the event for any reason. For example, this can happen when
	 * there is no such key or mouse button or when the system event queue is
	 * full.
	 * </p>
	 * <p>
	 * <b>Event Types:</b>
	 * <p>
	 * KeyDown, KeyUp
	 * <p>
	 * The following fields in the <code>Event</code> apply:
	 * <ul>
	 * <li>(in) type KeyDown or KeyUp</li>
	 * <p>
	 * Either one of:
	 * <li>(in) character a character that corresponds to a keyboard key</li>
	 * <li>(in) keyCode the key code of the key that was typed, as defined by
	 * the key code constants in class <code>SWT</code></li>
	 * </ul>
	 * <p>
	 * MouseDown, MouseUp
	 * </p>
	 * <p>
	 * The following fields in the <code>Event</code> apply:
	 * <ul>
	 * <li>(in) type MouseDown or MouseUp
	 * <li>(in) button the button that is pressed or released
	 * </ul>
	 * <p>
	 * MouseMove
	 * </p>
	 * <p>
	 * The following fields in the <code>Event</code> apply:
	 * <ul>
	 * <li>(in) type MouseMove
	 * <li>(in) x the x coordinate to move the mouse pointer to in screen
	 * coordinates
	 * <li>(in) y the y coordinate to move the mouse pointer to in screen
	 * coordinates
	 * </ul>
	 * <p>
	 * MouseWheel
	 * </p>
	 * <p>
	 * The following fields in the <code>Event</code> apply:
	 * <ul>
	 * <li>(in) type MouseWheel
	 * <li>(in) detail either SWT.SCROLL_LINE or SWT.SCROLL_PAGE
	 * <li>(in) count the number of lines or pages to scroll
	 * </ul>
	 * </dl>
	 * 
	 * @param event
	 *            the event to be generated
	 * 
	 * @return true if the event was generated or false otherwise
	 * 
	 * @exception IllegalArgumentException
	 *                <ul>
	 *                <li>ERROR_NULL_ARGUMENT - if the event is null</li>
	 *                </ul>
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_DEVICE_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 * 
	 * @since 3.0
	 * 
	 */
	public boolean post(Event event) {
		// TODO
		return false;
	}

	/**
	 * Reads an event from the operating system's event queue, dispatches it
	 * appropriately, and returns <code>true</code> if there is potentially more
	 * work to do, or <code>false</code> if the caller can sleep until another
	 * event is placed on the event queue.
	 * <p>
	 * In addition to checking the system event queue, this method also checks
	 * if any inter-thread messages (created by <code>syncExec()</code> or
	 * <code>asyncExec()</code>) are waiting to be processed, and if so handles
	 * them before returning.
	 * </p>
	 * 
	 * @return <code>false</code> if the caller can sleep upon return from this
	 *         method
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                <li>ERROR_DEVICE_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_FAILED_EXEC - if an exception occurred while
	 *                running an inter-thread message</li>
	 *                </ul>
	 * 
	 * @see #sleep
	 * @see #wake
	 */
	public boolean readAndDispatch() {
		// TODO
		return false;
	}

	/**
	 * Removes the listener from the collection of listeners who will be
	 * notified when an event of the given type occurs anywhere in a widget. The
	 * event type is one of the event constants defined in class
	 * <code>SWT</code>.
	 * 
	 * @param eventType
	 *            the type of event to listen for
	 * @param listener
	 *            the listener which should no longer be notified when the event
	 *            occurs
	 * 
	 * @exception IllegalArgumentException
	 *                <ul>
	 *                <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
	 *                </ul>
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 * 
	 * @see Listener
	 * @see SWT
	 * @see #addFilter
	 * @see #addListener
	 * 
	 * @since 3.0
	 */
	public void removeFilter(int eventType, Listener listener) {
		// TODO
	}

	/**
	 * Removes the listener from the collection of listeners who will be
	 * notified when an event of the given type occurs. The event type is one of
	 * the event constants defined in class <code>SWT</code>.
	 * 
	 * @param eventType
	 *            the type of event to listen for
	 * @param listener
	 *            the listener which should no longer be notified
	 * 
	 * @exception IllegalArgumentException
	 *                <ul>
	 *                <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
	 *                </ul>
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                <li>ERROR_DEVICE_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 * 
	 * @see Listener
	 * @see SWT
	 * @see #addListener
	 * 
	 * @since 2.0
	 */
	public void removeListener(int eventType, Listener listener) {
		// TODO
	}

	/**
	 * Returns the application name.
	 * 
	 * @return the application name
	 * 
	 * @see #setAppName(String)
	 * 
	 * @since 3.6
	 */
	public static String getAppName() {
		// TODO
		return null;
	}

	/**
	 * Returns the application version.
	 * 
	 * @return the application version
	 * 
	 * @see #setAppVersion(String)
	 * 
	 * @since 3.6
	 */
	public static String getAppVersion() {
		// TODO
		return null;
	}

	void sendPreEvent(Event event) {
		// TODO
	}

	void sendPostEvent(Event event) {
		// TODO
	}

	/**
	 * Sets the application name to the argument.
	 * <p>
	 * The application name can be used in several ways, depending on the
	 * platform and tools being used. On Motif, for example, this can be used to
	 * set the name used for resource lookup. Accessibility tools may also ask
	 * for the application name.
	 * </p>
	 * <p>
	 * Specifying <code>null</code> for the name clears it.
	 * </p>
	 * 
	 * @param name
	 *            the new app name or <code>null</code>
	 */
	public static void setAppName(String name) {
		// TODO
	}

	/**
	 * Sets the application version to the argument.
	 * 
	 * @param version
	 *            the new app version
	 * 
	 * @since 3.6
	 */
	public static void setAppVersion(String version) {
		// TODO
	}

	/**
	 * Sets the location of the on-screen pointer relative to the top left
	 * corner of the screen. <b>Note: It is typically considered bad practice
	 * for a program to move the on-screen pointer location.</b>
	 * 
	 * @param x
	 *            the new x coordinate for the cursor
	 * @param y
	 *            the new y coordinate for the cursor
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                <li>ERROR_DEVICE_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 * 
	 * @since 2.1
	 */
	public void setCursorLocation(int x, int y) {
		// TODO
	}

	/**
	 * Sets the location of the on-screen pointer relative to the top left
	 * corner of the screen. <b>Note: It is typically considered bad practice
	 * for a program to move the on-screen pointer location.</b>
	 * 
	 * @param point
	 *            new position
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                <li>ERROR_NULL_ARGUMENT - if the point is null
	 *                <li>ERROR_DEVICE_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 * 
	 * @since 2.0
	 */
	public void setCursorLocation(Point point) {
		// TODO
	}

	/**
	 * Sets the application defined property of the receiver with the specified
	 * name to the given argument.
	 * <p>
	 * Applications may have associated arbitrary objects with the receiver in
	 * this fashion. If the objects stored in the properties need to be notified
	 * when the display is disposed of, it is the application's responsibility
	 * provide a <code>disposeExec()</code> handler which does so.
	 * </p>
	 * 
	 * @param key
	 *            the name of the property
	 * @param value
	 *            the new value for the property
	 * 
	 * @exception IllegalArgumentException
	 *                <ul>
	 *                <li>ERROR_NULL_ARGUMENT - if the key is null</li>
	 *                </ul>
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                <li>ERROR_DEVICE_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 * 
	 * @see #getData(String)
	 * @see #disposeExec(Runnable)
	 */
	public void setData(String key, Object value) {
		// TODO
	}

	/**
	 * Sets the application defined, display specific data associated with the
	 * receiver, to the argument. The <em>display specific data</em> is a
	 * single, unnamed field that is stored with every display.
	 * <p>
	 * Applications may put arbitrary objects in this field. If the object
	 * stored in the display specific data needs to be notified when the display
	 * is disposed of, it is the application's responsibility provide a
	 * <code>disposeExec()</code> handler which does so.
	 * </p>
	 * 
	 * @param data
	 *            the new display specific data
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                <li>ERROR_DEVICE_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 * 
	 * @see #getData()
	 * @see #disposeExec(Runnable)
	 */
	public void setData(Object data) {
		// TODO
	}

	/**
	 * Sets the synchronizer used by the display to be the argument, which can
	 * not be null.
	 * 
	 * @param synchronizer
	 *            the new synchronizer for the display (must not be null)
	 * 
	 * @exception IllegalArgumentException
	 *                <ul>
	 *                <li>ERROR_NULL_ARGUMENT - if the synchronizer is null</li>
	 *                </ul>
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                <li>ERROR_DEVICE_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_FAILED_EXEC - if an exception occurred while
	 *                running an inter-thread message</li>
	 *                </ul>
	 */
	public void setSynchronizer(Synchronizer synchronizer) {
		// TODO
	}

	/**
	 * Causes the user-interface thread to <em>sleep</em> (that is, to be put in
	 * a state where it does not consume CPU cycles) until an event is received
	 * or it is otherwise awakened.
	 * 
	 * @return <code>true</code> if an event requiring dispatching was placed on
	 *         the queue.
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                <li>ERROR_DEVICE_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 * 
	 * @see #wake
	 */
	public boolean sleep() {
		// TODO
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * Causes the <code>run()</code> method of the runnable to be invoked by the
	 * user-interface thread after the specified number of milliseconds have
	 * elapsed. If milliseconds is less than zero, the runnable is not executed.
	 * <p>
	 * Note that at the time the runnable is invoked, widgets that have the
	 * receiver as their display may have been disposed. Therefore, it is
	 * necessary to check for this case inside the runnable before accessing the
	 * widget.
	 * </p>
	 * 
	 * @param milliseconds
	 *            the delay before running the runnable
	 * @param runnable
	 *            code to run on the user-interface thread
	 * 
	 * @exception IllegalArgumentException
	 *                <ul>
	 *                <li>ERROR_NULL_ARGUMENT - if the runnable is null</li>
	 *                </ul>
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                <li>ERROR_DEVICE_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 * 
	 * @see #asyncExec
	 */
	public void timerExec(int milliseconds, Runnable runnable) {
		// TODO
	}

	/**
	 * Causes the <code>run()</code> method of the runnable to be invoked by the
	 * user-interface thread at the next reasonable opportunity. The thread
	 * which calls this method is suspended until the runnable completes.
	 * Specifying <code>null</code> as the runnable simply wakes the
	 * user-interface thread.
	 * <p>
	 * Note that at the time the runnable is invoked, widgets that have the
	 * receiver as their display may have been disposed. Therefore, it is
	 * necessary to check for this case inside the runnable before accessing the
	 * widget.
	 * </p>
	 * 
	 * @param runnable
	 *            code to run on the user-interface thread or <code>null</code>
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_FAILED_EXEC - if an exception occurred when
	 *                executing the runnable</li>
	 *                <li>ERROR_DEVICE_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 * 
	 * @see #asyncExec
	 */
	public void syncExec(final Runnable runnable) {
		if (Platform.isFxApplicationThread()) {
			runnable.run();
			return;
		}
		
		final Object mutex = new Object();
		synchronized (mutex) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					runnable.run();
					synchronized (mutex) {
						mutex.notifyAll();
					}
				}
			});
			try {
				mutex.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * Forces all outstanding paint requests for the display to be processed
	 * before this method returns.
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                <li>ERROR_DEVICE_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 * 
	 * @see Control#update()
	 */
	public void update() {
		// TODO
	}

	/**
	 * If the receiver's user-interface thread was <code>sleep</code>ing, causes
	 * it to be awakened and start running again. Note that this method may be
	 * called from any thread.
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_DEVICE_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                </ul>
	 * 
	 * @see #sleep
	 */
	public void wake() {
		// TODO
	}

	void wakeThread() {
		// TODO
	}

}
