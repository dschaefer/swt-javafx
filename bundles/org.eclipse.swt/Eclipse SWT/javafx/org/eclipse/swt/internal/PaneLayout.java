package org.eclipse.swt.internal;

import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class PaneLayout {

	protected Pane pane;
	
	public PaneLayout(Pane pane) {
		this.pane = pane;
	}
	
	public void addChild(Node child) {
		pane.getChildren().add(child);
	}

	public void removeChild(Node child) {
		pane.getChildren().remove(child);
	}

	public Pane getPane() {
		return pane;
	}

}
