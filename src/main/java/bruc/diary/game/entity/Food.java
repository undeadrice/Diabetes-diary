package bruc.diary.game.entity;

import java.util.Random;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Paint;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;

public class Food {

	private double posX, posY;

	private Random rand = new Random();

	public Food(double posX, double posY) {
		this.posX = posX;
		this.posY = posY;
	}

	public void draw(GraphicsContext g, double width, double height) {
		Stop stops[] = { new Stop(0, Color.BLUE), new Stop(1, Color.WHITE) };
		RadialGradient gradient = new RadialGradient(5, .1, getPosX() * width + width / 2,
				getPosY() * height + height / 2, width, false, CycleMethod.NO_CYCLE, stops);
		g.setFill(gradient);
		g.fillRect(posX * width, posY * height, width, height);
		g.strokeText("F", posX * width + width / 2 - 3, posY * height + height / 2 + 3);
	}

	public void reroll() {
		this.setPosX(rand.nextInt(30));
		this.setPosY(rand.nextInt(30));
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
