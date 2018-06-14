package bruc.diary.game.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

public class Tile {

	private int row, col;

	private double drawingWidth, drawingHeight;

	private boolean containsNode = false;

	public Tile(int row, int col, double drawingWidth, double drawingHeight) {
		this.row = row;
		this.col = col;
		this.drawingWidth = drawingWidth;
		this.drawingHeight = drawingHeight;
	}

	
	public void draw(GraphicsContext g, double width, double height) {
		g.setStroke(Paint.valueOf("GREEN"));
	//	g.fillRect(row*width, col*height, width, height);
	//	g.strokeRect(row*drawingWidth, col*drawingHeight, drawingWidth, drawingHeight);
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public double getDrawingWidth() {
		return drawingWidth;
	}

	public void setDrawingWidth(double drawingWidth) {
		this.drawingWidth = drawingWidth;
	}

	public double getDrawingHeight() {
		return drawingHeight;
	}

	public void setDrawingHeight(double drawingHeight) {
		this.drawingHeight = drawingHeight;
	}

	public boolean isContainsNode() {
		return containsNode;
	}

	public void setContainsNode(boolean containsNode) {
		this.containsNode = containsNode;
	}

}
