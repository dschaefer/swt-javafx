package org.eclipse.swt.examples.controlexample;

import javafx.application.Application;
import javafx.stage.Stage;

import org.eclipse.swt.SWT;
import org.eclipse.swt.examples.controlexample.ControlExample;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class JavaFXControlExample extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	
	private Display display;
	private ControlExample instance;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		Display.primaryStage = primaryStage;
		display = new Display();
		Shell shell = new Shell(display, SWT.SHELL_TRIM);
		shell.setLayout(new FillLayout());
		instance = new ControlExample(shell);
		shell.setText(ControlExample.getResourceString("window.title"));
		ControlExample.setShellSize(instance, shell);
		shell.open();
	}
	
	@Override
	public void stop() throws Exception {
		instance.dispose();
		display.dispose();
	}
	
}
