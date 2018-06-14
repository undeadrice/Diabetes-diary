package bruc.diary.game.engine;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import bruc.diary.game.entity.Board;
import bruc.diary.game.entity.Direction;
import bruc.diary.game.entity.Food;
import bruc.diary.game.entity.Physics;
import bruc.diary.game.entity.Player;
import bruc.diary.game.entity.Renderer;

public class GameEngine {

	private ExecutorService threads = Executors.newSingleThreadExecutor();
	private Renderer renderer;
	private KeyboardController controller;
	private Physics physics;
	private Player player;
	private Food food;
	private Board board;

	public GameEngine(Player player, Food food, Renderer renderer) {
		this.player = player;
		this.renderer = renderer;
		this.food = food;
		physics = new Physics(player, food);
		controller = new KeyboardController(renderer);

	}

	public void engageGameLoop() {
		threads.execute(() -> {

			while (true) {
				try {
					Thread.sleep(50);
					switchInput();
					physics.nextTick();
					renderer.drawEntities();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

	}

	private void switchInput() {
		switch (controller.getLastKey()) {
		case 'w':
			player.getHead().setDir(Direction.NORTH);
			break;
		case 'd':
			player.getHead().setDir(Direction.EAST);
			break;
		case 's':
			player.getHead().setDir(Direction.SOUTH);
			break;
		case 'a':
			player.getHead().setDir(Direction.WEST);
			break;
		}

	}

}
