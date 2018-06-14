package bruc.diary.game.entity;

import java.util.ArrayList;
import java.util.List;

public class Player {

	private SnakeHead head;
	private List<SnakeTail> tail;

	public Player(SnakeHead head, List<SnakeTail> tail) {
		this.head = head;
		this.tail = tail;
	}

	public List<SnakeTail> getTail() {
		return tail;
	}

	public void setTail(List<SnakeTail> tail) {
		this.tail = tail;
	}

	public SnakeHead getHead() {
		return head;
	}

	public void setHead(SnakeHead head) {
		this.head = head;
	}

	public void addTailNode() {

	}

}
