package org.eclipse.swt.internal;

import javafx.scene.Node;
import javafx.scene.layout.GridPane;

import org.eclipse.swt.layout.GridLayout;

/**
 * GridPane's don't automatically layout the children on a grid like GridLayout's do.
 * This class manages which row and column the children end up in.
 */
public class GridPaneLayout extends PaneLayout {

	private GridLayout layout;
	private int currCol = 1, currRow = 1;
	
	public GridPaneLayout(GridPane pane, GridLayout layout) {
		super(pane);
		this.layout = layout;
	}

	private GridPane getGridPane() {
		return (GridPane)pane;
	}

	@Override
	public void addChild(Node child) {
		getGridPane().add(child, currCol++, currRow);
		
		if (currCol > layout.numColumns) {
			currCol = 1;
			currRow++;
		}
	}

}
