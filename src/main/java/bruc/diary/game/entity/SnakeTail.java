package bruc.diary.game.entity;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;

public class SnakeTail {

	private Direction dir = Direction.EAST;

	private double posX, posY;

	public SnakeTail(double posX, double posY) {
		this.posX = posX;
		this.posY = posY;
	}

	public SnakeTail(double posX, double posY, Direction dir) {
		this.posX = posX;
		this.posY = posY;
		this.dir = dir;
	}

	public void moveX(double val) {
		this.posX += val;
	}

	public void moveY(double val) {
		this.posY += val;
	}

	public Direction getDir() {
		return dir;
	}

	public void setDir(Direction dir) {
		this.dir = dir;
	}

	public void draw(GraphicsContext g, double width, double height) {
		Stop stops[] = { new Stop(0, Color.RED), new Stop(1, Color.GREEN) };

		RadialGradient gradient = new RadialGradient(5, .1, getPosX() * width + width / 2,
				getPosY() * height + height / 2, width, false, CycleMethod.NO_CYCLE, stops);

		g.setFill(gradient);
		g.fillRect(getPosX() * width, getPosY() * height, width, height);

	}

	public double getPosX() {
		return posX;
	}

	public void setPosX(double posX) {
		this.posX = posX;
	}

	public double getPosY() {
		return posY;
	}

	public void setPosY(double posY) {
		this.posY = posY;
	}

}
