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

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SegmentEvent;
import org.eclipse.swt.events.SegmentListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Point;

/**
 * Instances of this class are selectable user interface objects that allow the
 * user to enter and modify text. Text controls can be either single or
 * multi-line. When a text control is created with a border, the operating
 * system includes a platform specific inset around the contents of the control.
 * When created without a border, an effort is made to remove the inset such
 * that the preferred size of the control is the same size as the contents.
 * <p>
 * <dl>
 * <dt><b>Styles:</b></dt>
 * <dd>CENTER, ICON_CANCEL, ICON_SEARCH, LEFT, MULTI, PASSWORD, SEARCH, SINGLE,
 * RIGHT, READ_ONLY, WRAP</dd>
 * <dt><b>Events:</b></dt>
 * <dd>DefaultSelection, Modify, Verify, OrientationChange</dd>
 * </dl>
 * <p>
 * Note: Only one of the styles MULTI and SINGLE may be specified, and only one
 * of the styles LEFT, CENTER, and RIGHT may be specified.
 * </p>
 * <p>
 * Note: The styles ICON_CANCEL and ICON_SEARCH are hints used in combination
 * with SEARCH. When the platform supports the hint, the text control shows
 * these icons. When an icon is selected, a default selection event is sent with
 * the detail field set to one of ICON_CANCEL or ICON_SEARCH. Normally,
 * application code does not need to check the detail. In the case of
 * ICON_CANCEL, the text is cleared before the default selection event is sent
 * causing the application to search for an empty string.
 * </p>
 * <p>
 * IMPORTANT: This class is <em>not</em> intended to be subclassed.
 * </p>
 * 
 * @see <a href="http://www.eclipse.org/swt/snippets/#text">Text snippets</a>
 * @see <a href="http://www.eclipse.org/swt/examples.php">SWT Example:
 *      ControlExample</a>
 * @see <a href="http://www.eclipse.org/swt/">Sample code and further
 *      information</a>
 * @noextend This class is not intended to be subclassed by clients.
 */
public class Text extends Scrollable {

	/**
	 * The maximum number of characters that can be entered into a text widget.
	 * <p>
	 * Note that this value is platform dependent, based upon the native widget
	 * implementation.
	 * </p>
	 */
	public final static int LIMIT;

	/**
	 * The delimiter used by multi-line text widgets. When text is queried and
	 * from the widget, it will be delimited using this delimiter.
	 */
	public final static String DELIMITER;

	/*
	 * These values can be different on different platforms. Therefore they are
	 * not initialized in the declaration to stop the compiler from inlining.
	 */
	static {
		LIMIT = 0x7FFFFFFF;
		DELIMITER = "\n";
	}

	/**
	 * Constructs a new instance of this class given its parent and a style
	 * value describing its behavior and appearance.
	 * <p>
	 * The style value is either one of the style constants defined in class
	 * <code>SWT</code> which is applicable to instances of this class, or must
	 * be built by <em>bitwise OR</em>'ing together (that is, using the
	 * <code>int</code> "|" operator) two or more of those <code>SWT</code>
	 * style constants. The class description lists the style constants that are
	 * applicable to the class. Style bits are also inherited from superclasses.
	 * </p>
	 * 
	 * @param parent
	 *            a composite control which will be the parent of the new
	 *            instance (cannot be null)
	 * @param style
	 *            the style of control to construct
	 * 
	 * @exception IllegalArgumentException
	 *                <ul>
	 *                <li>ERROR_NULL_ARGUMENT - if the parent is null</li>
	 *                </ul>
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the parent</li>
	 *                <li>ERROR_INVALID_SUBCLASS - if this class is not an
	 *                allowed subclass</li>
	 *                </ul>
	 * 
	 * @see SWT#SINGLE
	 * @see SWT#MULTI
	 * @see SWT#READ_ONLY
	 * @see SWT#WRAP
	 * @see SWT#LEFT
	 * @see SWT#RIGHT
	 * @see SWT#CENTER
	 * @see SWT#PASSWORD
	 * @see SWT#SEARCH
	 * @see SWT#ICON_SEARCH
	 * @see SWT#ICON_CANCEL
	 * @see Widget#checkSubclass
	 * @see Widget#getStyle
	 */
	public Text(Composite parent, int style) {
		super(parent, style);
		// TODO
	}

	/**
	 * Adds the listener to the collection of listeners who will be notified
	 * when the receiver's text is modified, by sending it one of the messages
	 * defined in the <code>ModifyListener</code> interface.
	 * 
	 * @param listener
	 *            the listener which should be notified
	 * 
	 * @exception IllegalArgumentException
	 *                <ul>
	 *                <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
	 *                </ul>
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 * 
	 * @see ModifyListener
	 * @see #removeModifyListener
	 */
	public void addModifyListener(ModifyListener listener) {
		// TODO
	}

	/**
	 * Adds a segment listener.
	 * <p>
	 * A <code>SegmentEvent</code> is sent whenever text content is being
	 * modified or a segment listener is added or removed. You can customize the
	 * appearance of text by indicating certain characters to be inserted at
	 * certain text offsets. This may be used for bidi purposes, e.g. when
	 * adjacent segments of right-to-left text should not be reordered relative
	 * to each other. E.g., multiple Java string literals in a right-to-left
	 * language should generally remain in logical order to each other, that is,
	 * the way they are stored.
	 * </p>
	 * <p>
	 * <b>Warning</b>: This API is currently only implemented on Windows and
	 * GTK. <code>SegmentEvent</code>s won't be sent on Cocoa.
	 * </p>
	 * 
	 * @param listener
	 *            the listener which should be notified
	 * 
	 * @exception IllegalArgumentException
	 *                <ul>
	 *                <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
	 *                </ul>
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 * 
	 * @see SegmentEvent
	 * @see SegmentListener
	 * @see #removeSegmentListener
	 * 
	 * @since 3.8
	 */
	public void addSegmentListener(SegmentListener listener) {
		// TODO
	}

	/**
	 * Adds the listener to the collection of listeners who will be notified
	 * when the control is selected by the user, by sending it one of the
	 * messages defined in the <code>SelectionListener</code> interface.
	 * <p>
	 * <code>widgetSelected</code> is not called for texts.
	 * <code>widgetDefaultSelected</code> is typically called when ENTER is
	 * pressed in a single-line text, or when ENTER is pressed in a search text.
	 * If the receiver has the <code>SWT.SEARCH | SWT.ICON_CANCEL</code> style
	 * and the user cancels the search, the event object detail field contains
	 * the value <code>SWT.ICON_CANCEL</code>. Likewise, if the receiver has the
	 * <code>SWT.ICON_SEARCH</code> style and the icon search is selected, the
	 * event object detail field contains the value <code>SWT.ICON_SEARCH</code>
	 * .
	 * </p>
	 * 
	 * @param listener
	 *            the listener which should be notified when the control is
	 *            selected by the user
	 * 
	 * @exception IllegalArgumentException
	 *                <ul>
	 *                <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
	 *                </ul>
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 * 
	 * @see SelectionListener
	 * @see #removeSelectionListener
	 * @see SelectionEvent
	 */
	public void addSelectionListener(SelectionListener listener) {
		// TODO
	}

	/**
	 * Adds the listener to the collection of listeners who will be notified
	 * when the receiver's text is verified, by sending it one of the messages
	 * defined in the <code>VerifyListener</code> interface.
	 * 
	 * @param listener
	 *            the listener which should be notified
	 * 
	 * @exception IllegalArgumentException
	 *                <ul>
	 *                <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
	 *                </ul>
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 * 
	 * @see VerifyListener
	 * @see #removeVerifyListener
	 */
	public void addVerifyListener(VerifyListener listener) {
		// TODO
	}

	/**
	 * Appends a string.
	 * <p>
	 * The new text is appended to the text at the end of the widget.
	 * </p>
	 * 
	 * @param string
	 *            the string to be appended
	 * 
	 * @exception IllegalArgumentException
	 *                <ul>
	 *                <li>ERROR_NULL_ARGUMENT - if the string is null</li>
	 *                </ul>
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 */
	public void append(String string) {
		// TODO
	}

	/**
	 * Clears the selection.
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 */
	public void clearSelection() {
		checkWidget();
		// TODO
	}

	/**
	 * Copies the selected text.
	 * <p>
	 * The current selection is copied to the clipboard.
	 * </p>
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 */
	public void copy() {
		// TODO
	}

	/**
	 * Cuts the selected text.
	 * <p>
	 * The current selection is first copied to the clipboard and then deleted
	 * from the widget.
	 * </p>
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 */
	public void cut() {
		// TODO
	}

	/**
	 * Returns the line number of the caret.
	 * <p>
	 * The line number of the caret is returned.
	 * </p>
	 * 
	 * @return the line number
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 */
	public int getCaretLineNumber() {
		// TODO
		return 0;
	}

	/**
	 * Returns a point describing the location of the caret relative to the
	 * receiver.
	 * 
	 * @return a point, the location of the caret
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 */
	public Point getCaretLocation() {
		// TODO
		return null;
	}

	/**
	 * Returns the character position of the caret.
	 * <p>
	 * Indexing is zero based.
	 * </p>
	 * 
	 * @return the position of the caret
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 */
	public int getCaretPosition() {
		// TODO
		return 0;
	}

	/**
	 * Returns the number of characters.
	 * 
	 * @return number of characters in the widget
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 */
	public int getCharCount() {
		// TODO
		return 0;
	}

	/**
	 * Returns the double click enabled flag.
	 * <p>
	 * The double click flag enables or disables the default action of the text
	 * widget when the user double clicks.
	 * </p>
	 * 
	 * @return whether or not double click is enabled
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 */
	public boolean getDoubleClickEnabled() {
		// TODO
		return false;
	}

	/**
	 * Returns the echo character.
	 * <p>
	 * The echo character is the character that is displayed when the user
	 * enters text or the text is changed by the programmer.
	 * </p>
	 * 
	 * @return the echo character
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 * 
	 * @see #setEchoChar
	 */
	public char getEchoChar() {
		// TODO
		return 0;
	}

	/**
	 * Returns the editable state.
	 * 
	 * @return whether or not the receiver is editable
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 */
	public boolean getEditable() {
		// TODO
		return false;
	}

	/**
	 * Returns the number of lines.
	 * 
	 * @return the number of lines in the widget
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 */
	public int getLineCount() {
		// TODO
		return 0;
	}

	/**
	 * Returns the line delimiter.
	 * 
	 * @return a string that is the line delimiter
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 * 
	 * @see #DELIMITER
	 */
	public String getLineDelimiter() {
		// TODO
		return "\n";
	}

	/**
	 * Returns the height of a line.
	 * 
	 * @return the height of a row of text
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 */
	public int getLineHeight() {
		// TODO
		return 0;
	}

	/**
	 * Returns the widget message. The message text is displayed as a hint for
	 * the user, indicating the purpose of the field.
	 * <p>
	 * Typically this is used in conjunction with <code>SWT.SEARCH</code>.
	 * </p>
	 * 
	 * @return the widget message
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 * 
	 * @since 3.3
	 */
	public String getMessage() {
		// TODO
		return null;
	}

	/**
	 * Returns a <code>Point</code> whose x coordinate is the character position
	 * representing the start of the selected text, and whose y coordinate is
	 * the character position representing the end of the selection. An "empty"
	 * selection is indicated by the x and y coordinates having the same value.
	 * <p>
	 * Indexing is zero based. The range of a selection is from 0..N where N is
	 * the number of characters in the widget.
	 * </p>
	 * 
	 * @return a point representing the selection start and end
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 */
	public Point getSelection() {
		return null;
	}

	/**
	 * Returns the number of selected characters.
	 * 
	 * @return the number of selected characters.
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 */
	public int getSelectionCount() {
		// TODO
		return 0;
	}

	/**
	 * Gets the selected text, or an empty string if there is no current
	 * selection.
	 * 
	 * @return the selected text
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 */
	public String getSelectionText() {
		// TODO
		return null;
	}

	/**
	 * Returns the number of tabs.
	 * <p>
	 * Tab stop spacing is specified in terms of the space (' ') character. The
	 * width of a single tab stop is the pixel width of the spaces.
	 * </p>
	 * 
	 * @return the number of tab characters
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 */
	public int getTabs() {
		// TODO
		return 0;
	}

	/**
	 * Returns the widget text.
	 * <p>
	 * The text for a text widget is the characters in the widget, or an empty
	 * string if this has never been set.
	 * </p>
	 * 
	 * @return the widget text
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 */
	public String getText() {
		// TODO
		return null;
	}

	/**
	 * Returns a range of text. Returns an empty string if the start of the
	 * range is greater than the end.
	 * <p>
	 * Indexing is zero based. The range of a selection is from 0..N-1 where N
	 * is the number of characters in the widget.
	 * </p>
	 * 
	 * @param start
	 *            the start of the range
	 * @param end
	 *            the end of the range
	 * @return the range of text
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 */
	public String getText(int start, int end) {
		// TODO
		return null;
	}

	/**
	 * Returns the widget's text as a character array.
	 * <p>
	 * The text for a text widget is the characters in the widget, or a
	 * zero-length array if this has never been set.
	 * </p>
	 * <p>
	 * Note: Use the API to protect the text, for example, when widget is used
	 * as a password field. However, the text can't be protected if Segment
	 * listener is added to the widget.
	 * </p>
	 * 
	 * @return a character array that contains the widget's text
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 * 
	 * @see #setTextChars(char[])
	 * 
	 * @since 3.7
	 */
	public char[] getTextChars() {
		// TODO
		return null;
	}

	/**
	 * Returns the maximum number of characters that the receiver is capable of
	 * holding.
	 * <p>
	 * If this has not been changed by <code>setTextLimit()</code>, it will be
	 * the constant <code>Text.LIMIT</code>.
	 * </p>
	 * 
	 * @return the text limit
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 * 
	 * @see #LIMIT
	 */
	public int getTextLimit() {
		// TODO
		return 0;
	}

	/**
	 * Returns the zero-relative index of the line which is currently at the top
	 * of the receiver.
	 * <p>
	 * This index can change when lines are scrolled or new lines are added or
	 * removed.
	 * </p>
	 * 
	 * @return the index of the top line
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 */
	public int getTopIndex() {
		// TODO
		return 0;
	}

	/**
	 * Returns the top pixel.
	 * <p>
	 * The top pixel is the pixel position of the line that is currently at the
	 * top of the widget. On some platforms, a text widget can be scrolled by
	 * pixels instead of lines so that a partial line is displayed at the top of
	 * the widget.
	 * </p>
	 * <p>
	 * The top pixel changes when the widget is scrolled. The top pixel does not
	 * include the widget trimming.
	 * </p>
	 * 
	 * @return the pixel position of the top line
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 */
	public int getTopPixel() {
		// TODO
		return 0;
	}

	/**
	 * Inserts a string.
	 * <p>
	 * The old selection is replaced with the new text.
	 * </p>
	 * 
	 * @param string
	 *            the string
	 * 
	 * @exception IllegalArgumentException
	 *                <ul>
	 *                <li>ERROR_NULL_ARGUMENT - if the string is
	 *                <code>null</code></li>
	 *                </ul>
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 */
	public void insert(String string) {
		// TODO
	}

	/**
	 * Pastes text from clipboard.
	 * <p>
	 * The selected text is deleted from the widget and new text inserted from
	 * the clipboard.
	 * </p>
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 */
	public void paste() {
		// TODO
	}

	/**
	 * Removes the listener from the collection of listeners who will be
	 * notified when the receiver's text is modified.
	 * 
	 * @param listener
	 *            the listener which should no longer be notified
	 * 
	 * @exception IllegalArgumentException
	 *                <ul>
	 *                <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
	 *                </ul>
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 * 
	 * @see ModifyListener
	 * @see #addModifyListener
	 */
	public void removeModifyListener(ModifyListener listener) {
		// TODO
	}

	/**
	 * Removes the listener from the collection of listeners who will be
	 * notified when the receiver's text is modified.
	 * 
	 * @param listener
	 *            the listener which should no longer be notified
	 * 
	 * @exception IllegalArgumentException
	 *                <ul>
	 *                <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
	 *                </ul>
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 * 
	 * @see SegmentEvent
	 * @see SegmentListener
	 * @see #addSegmentListener
	 * 
	 * @since 3.8
	 */
	public void removeSegmentListener(SegmentListener listener) {
		// TODO
	}

	/**
	 * Removes the listener from the collection of listeners who will be
	 * notified when the control is selected by the user.
	 * 
	 * @param listener
	 *            the listener which should no longer be notified
	 * 
	 * @exception IllegalArgumentException
	 *                <ul>
	 *                <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
	 *                </ul>
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 * 
	 * @see SelectionListener
	 * @see #addSelectionListener
	 */
	public void removeSelectionListener(SelectionListener listener) {
		// TODO
	}

	/**
	 * Removes the listener from the collection of listeners who will be
	 * notified when the control is verified.
	 * 
	 * @param listener
	 *            the listener which should no longer be notified
	 * 
	 * @exception IllegalArgumentException
	 *                <ul>
	 *                <li>ERROR_NULL_ARGUMENT - if the listener is null</li>
	 *                </ul>
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 * 
	 * @see VerifyListener
	 * @see #addVerifyListener
	 */
	public void removeVerifyListener(VerifyListener listener) {
		// TODO
	}

	/**
	 * Selects all the text in the receiver.
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 */
	public void selectAll() {
		// TODO
	}

	/**
	 * Sets the double click enabled flag.
	 * <p>
	 * The double click flag enables or disables the default action of the text
	 * widget when the user double clicks.
	 * </p>
	 * <p>
	 * Note: This operation is a hint and is not supported on platforms that do
	 * not have this concept.
	 * </p>
	 * 
	 * @param doubleClick
	 *            the new double click flag
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 */
	public void setDoubleClickEnabled(boolean doubleClick) {
		// TODO
	}

	/**
	 * Sets the echo character.
	 * <p>
	 * The echo character is the character that is displayed when the user
	 * enters text or the text is changed by the programmer. Setting the echo
	 * character to '\0' clears the echo character and redraws the original
	 * text. If for any reason the echo character is invalid, or if the platform
	 * does not allow modification of the echo character, the default echo
	 * character for the platform is used.
	 * </p>
	 * 
	 * @param echo
	 *            the new echo character
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 */
	public void setEchoChar(char echo) {
		// TODO
	}

	/**
	 * Sets the editable state.
	 * 
	 * @param editable
	 *            the new editable state
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 */
	public void setEditable(boolean editable) {
		// TODO
	}

	/**
	 * Sets the widget message. The message text is displayed as a hint for the
	 * user, indicating the purpose of the field.
	 * <p>
	 * Typically this is used in conjunction with <code>SWT.SEARCH</code>.
	 * </p>
	 * 
	 * @param message
	 *            the new message
	 * 
	 * @exception IllegalArgumentException
	 *                <ul>
	 *                <li>ERROR_NULL_ARGUMENT - if the message is null</li>
	 *                </ul>
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 * 
	 * @since 3.3
	 */
	public void setMessage(String message) {
		// TODO
	}

	/**
	 * Sets the orientation of the receiver, which must be one of the constants
	 * <code>SWT.LEFT_TO_RIGHT</code> or <code>SWT.RIGHT_TO_LEFT</code>.
	 * <p>
	 * Note: This operation is a hint and is not supported on platforms that do
	 * not have this concept.
	 * </p>
	 * 
	 * @param orientation
	 *            new orientation style
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 * 
	 * @since 2.1.2
	 */
	@Override
	public void setOrientation(int orientation) {
		// TODO
	}

	/**
	 * Sets the selection.
	 * <p>
	 * Indexing is zero based. The range of a selection is from 0..N where N is
	 * the number of characters in the widget.
	 * </p>
	 * <p>
	 * Text selections are specified in terms of caret positions. In a text
	 * widget that contains N characters, there are N+1 caret positions, ranging
	 * from 0..N. This differs from other functions that address character
	 * position such as getText () that use the regular array indexing rules.
	 * </p>
	 * 
	 * @param start
	 *            new caret position
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 */
	public void setSelection(int start) {
		// TODO
	}

	/**
	 * Sets the selection to the range specified by the given start and end
	 * indices.
	 * <p>
	 * Indexing is zero based. The range of a selection is from 0..N where N is
	 * the number of characters in the widget.
	 * </p>
	 * <p>
	 * Text selections are specified in terms of caret positions. In a text
	 * widget that contains N characters, there are N+1 caret positions, ranging
	 * from 0..N. This differs from other functions that address character
	 * position such as getText () that use the usual array indexing rules.
	 * </p>
	 * 
	 * @param start
	 *            the start of the range
	 * @param end
	 *            the end of the range
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 */
	public void setSelection(int start, int end) {
		// TODO
	}

	/**
	 * Sets the selection to the range specified by the given point, where the x
	 * coordinate represents the start index and the y coordinate represents the
	 * end index.
	 * <p>
	 * Indexing is zero based. The range of a selection is from 0..N where N is
	 * the number of characters in the widget.
	 * </p>
	 * <p>
	 * Text selections are specified in terms of caret positions. In a text
	 * widget that contains N characters, there are N+1 caret positions, ranging
	 * from 0..N. This differs from other functions that address character
	 * position such as getText () that use the usual array indexing rules.
	 * </p>
	 * 
	 * @param selection
	 *            the point
	 * 
	 * @exception IllegalArgumentException
	 *                <ul>
	 *                <li>ERROR_NULL_ARGUMENT - if the point is null</li>
	 *                </ul>
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 */
	public void setSelection(Point selection) {
		// TODO
	}

	/**
	 * Sets the number of tabs.
	 * <p>
	 * Tab stop spacing is specified in terms of the space (' ') character. The
	 * width of a single tab stop is the pixel width of the spaces.
	 * </p>
	 * 
	 * @param tabs
	 *            the number of tabs
	 * 
	 *            </ul>
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 */
	public void setTabs(int tabs) {
		// TODO
	}

	/**
	 * Sets the contents of the receiver to the given string. If the receiver
	 * has style SINGLE and the argument contains multiple lines of text, the
	 * result of this operation is undefined and may vary from platform to
	 * platform.
	 * 
	 * @param string
	 *            the new text
	 * 
	 * @exception IllegalArgumentException
	 *                <ul>
	 *                <li>ERROR_NULL_ARGUMENT - if the string is null</li>
	 *                </ul>
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 */
	public void setText(String string) {
		// TODO
	}

	/**
	 * Sets the contents of the receiver to the characters in the array. If the
	 * receiver has style <code>SWT.SINGLE</code> and the argument contains
	 * multiple lines of text then the result of this operation is undefined and
	 * may vary between platforms.
	 * <p>
	 * Note: Use the API to protect the text, for example, when the widget is
	 * used as a password field. However, the text can't be protected if Verify
	 * or Segment listener is added to the widget.
	 * </p>
	 * 
	 * @param text
	 *            a character array that contains the new text
	 * 
	 * @exception IllegalArgumentException
	 *                <ul>
	 *                <li>ERROR_NULL_ARGUMENT - if the array is null</li>
	 *                </ul>
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 * 
	 * @see #getTextChars()
	 * 
	 * @since 3.7
	 */
	public void setTextChars(char[] text) {
		// TODO
	}

	/**
	 * Sets the maximum number of characters that the receiver is capable of
	 * holding to be the argument.
	 * <p>
	 * Instead of trying to set the text limit to zero, consider creating a
	 * read-only text widget.
	 * </p>
	 * <p>
	 * To reset this value to the default, use
	 * <code>setTextLimit(Text.LIMIT)</code>. Specifying a limit value larger
	 * than <code>Text.LIMIT</code> sets the receiver's limit to
	 * <code>Text.LIMIT</code>.
	 * </p>
	 * 
	 * @param limit
	 *            new text limit
	 * 
	 * @exception IllegalArgumentException
	 *                <ul>
	 *                <li>ERROR_CANNOT_BE_ZERO - if the limit is zero</li>
	 *                </ul>
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 * 
	 * @see #LIMIT
	 */
	public void setTextLimit(int limit) {
		// TODO
	}

	/**
	 * Sets the zero-relative index of the line which is currently at the top of
	 * the receiver. This index can change when lines are scrolled or new lines
	 * are added and removed.
	 * 
	 * @param index
	 *            the index of the top item
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 */
	public void setTopIndex(int index) {
		// TODO
	}

	/**
	 * Shows the selection.
	 * <p>
	 * If the selection is already showing in the receiver, this method simply
	 * returns. Otherwise, lines are scrolled until the selection is visible.
	 * </p>
	 * 
	 * @exception SWTException
	 *                <ul>
	 *                <li>ERROR_WIDGET_DISPOSED - if the receiver has been
	 *                disposed</li>
	 *                <li>ERROR_THREAD_INVALID_ACCESS - if not called from the
	 *                thread that created the receiver</li>
	 *                </ul>
	 */
	public void showSelection() {
		// TODO
	}

}
