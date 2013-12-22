package org.eclipse.swt.internal;

public class Platform {

	public static final String PLATFORM = "javafx";
	
	public static boolean isLoadable () {
		// TODO is this the right answer?
		return Library.isLoadable ();	
	}

}
