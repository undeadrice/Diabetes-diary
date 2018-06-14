package bruc.diary.game.entity;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;

public class Renderer extends Canvas {

	private Player player;
	private Food food;
	private Board board;

	private double cellWidth, cellHeight;

	public Renderer(Player player, Food food, Board board, double width, double height, double cellWidth,
			double cellHeight) {
		setFocusTraversable(true);
		this.setWidth(width);
		this.setHeight(height);

		this.cellWidth = cellWidth;
		this.cellHeight = cellHeight;

		this.player = player;
		this.food = food;
		this.board = board;
		
	}

	public synchronized void drawEntities() {
		refresh();
		GraphicsContext g = this.getGraphicsContext2D();
		
		g.setFill(Paint.valueOf("rgb(40,140,70)"));
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		board.draw(g, cellWidth, cellHeight);
		player.getHead().draw(g, cellWidth, cellHeight);
		player.getTail().forEach(e -> {e.draw(g, cellWidth, cellHeight);});
		food.draw(g, cellWidth, cellHeight);
		

	}

	public void refresh() {
		GraphicsContext g = this.getGraphicsContext2D();
		System.out.println(this.getWidth());
		g.clearRect(0, 0, this.getWidth(), this.getHeight());
	}

}
