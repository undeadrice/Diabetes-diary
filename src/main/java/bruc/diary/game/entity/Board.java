package bruc.diary.game.entity;

import javafx.scene.canvas.GraphicsContext;

public class Board {

	private int rowCount, colCount;
	private double tileWidth, tileHeight;

	private Tile[][] tiles;

	public Board(double tileWidth, double tileHeight, int rowCount, int colCount) {
		tiles = new Tile[rowCount][colCount];
		this.tileHeight = tileHeight;
		this.tileWidth = tileWidth;

		this.rowCount = rowCount;
		this.colCount = colCount;
		generate();
	}

	private void generate() {
		for (int col = 0; col < colCount; col++) {
			for (int row = 0; row < rowCount; row++) {
				tiles[row][col] = new Tile(col, row, tileWidth, tileHeight);
			}
		}
	}

	public void draw(GraphicsContext g, double width, double height) {
		for (Tile[] col : tiles) {
			for (Tile row : col) {
				row.draw(g, width, height);
			}
		}

	}

}
