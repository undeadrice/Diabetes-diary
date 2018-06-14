package bruc.diary.game;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import bruc.diary.game.engine.GameEngine;
import bruc.diary.game.entity.Board;
import bruc.diary.game.entity.Food;
import bruc.diary.game.entity.Player;
import bruc.diary.game.entity.Renderer;
import bruc.diary.game.entity.SnakeHead;
import bruc.diary.game.entity.SnakeTail;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class Game {

	private int colCount = 30, rowCount = 30;

	private Renderer renderer;
	private Pane root;
	private GameEngine engine;

	private Player player;
	private Board board;
	private Food food;

	private double cellWidth, cellHeight;

	private ObservableList<Node> nodes;

	public Game(Pane root) {
		this.root = root;
		calculateCellSize();
		init();
		renderer = new Renderer(player, food, board, root.getWidth(), root.getHeight(), cellWidth, cellHeight);
		engine = new GameEngine(player, food, renderer);

	}

	public void start() {
		Platform.runLater(() -> {
			nodes = root.getChildren();
			root.getChildren().removeAll(nodes);
			root.getChildren().add(renderer);

			root.setOpacity(1.0);
		});
		engine.engageGameLoop();

	}

	public void init() {
		board = new Board(cellWidth, cellHeight, colCount, rowCount);
		List<SnakeTail> tail = Collections.synchronizedList(new ArrayList<>());
		SnakeTail one = new SnakeTail(colCount / 2 - 1, rowCount / 2);
		SnakeTail two = new SnakeTail(colCount / 2 - 2, rowCount / 2);
		SnakeHead head = new SnakeHead(colCount / 2, rowCount / 2);
		tail.add(one);
		tail.add(two);
		player = new Player(head, tail);
		food = new Food(10, 10);

	}

	public void calculateCellSize() {
		this.cellWidth = root.getWidth() / colCount;
		this.cellHeight = root.getHeight() / rowCount;
	}
	
	public void backToDiary() {
		root.getChildren().remove(renderer);
		root.getChildren().addAll(nodes);
	}

}
