package bruc.diary.Window;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class MoveableStage extends Scene {

	private double mouseXStart, mouseYStart;
	private Stage stage;

	public MoveableStage(Parent arg0, Stage stage) {
		super(arg0);
		this.stage = stage;

		setOnMousePressed(e -> onClick(e));
		setOnMouseDragged(e -> onDrag(e));
	}

	public void onDrag(MouseEvent e) {
		double deltaX = (e.getScreenX() - mouseXStart);
		double deltaY = (e.getScreenY() - mouseYStart);
	
		stage.setX(stage.getX() + deltaX);
		stage.setY(stage.getY() + deltaY);
		
		mouseXStart = e.getScreenX();
		mouseYStart = e.getScreenY();		
	}

	public void onClick(MouseEvent e) {
		mouseXStart = e.getScreenX();
		mouseYStart = e.getScreenY();	
	}

}
