package bruc.diary.game.entity;

public class Physics {

	private Player player;
	private Food food;

	public Physics(Player player, Food food) {
		this.player = player;
		this.food = food;
	}

	public void nextTick() {
		move();
		checkCollisions();
		checkIfEating();
	}

	public void move() {
		switch (player.getHead().getDir()) {
		case EAST:
			player.getHead().moveX(1);
			break;
		case NORTH:
			player.getHead().moveY(-1);
			break;
		case SOUTH:
			player.getHead().moveY(1);
			break;
		case WEST:
			player.getHead().moveX(-1);
			break;
		default:
			break;
		}
		for (int i = player.getTail().size() - 1; i >= 0; i--) {

			switch (player.getTail().get(i).getDir()) {
			case EAST:
				player.getTail().get(i).moveX(1);
				break;
			case NORTH:
				player.getTail().get(i).moveY(-1);
				break;
			case SOUTH:
				player.getTail().get(i).moveY(1);
				break;
			case WEST:
				player.getTail().get(i).moveX(-1);
				break;
			default:
				break;
			}

			updateTailDir(player.getTail().get(i), i);
		}

	}

	public void updateTailDir(SnakeTail tail, int index) {
		if (index == 0) {
			tail.setDir(player.getHead().getDir());
		} else {
			if (index == player.getTail().size())
				return;

			tail.setDir(player.getTail().get(index - 1).getDir());
		}
	}

	public void checkCollisions() {
		if (player.getHead().getPosX() < 0 || player.getHead().getPosX() >= 30 || player.getHead().getPosY() < 0
				|| player.getHead().getPosY() >= 30) {
			System.exit(0);
		}
		for (SnakeTail tail : player.getTail()) {
			if (player.getHead().getPosX() == tail.getPosX() && player.getHead().getPosY() == tail.getPosY()) {
				System.exit(0);
			}
		}

	}

	public void checkIfEating() {

		if ((player.getHead().getPosX() == food.getPosX()) && (player.getHead().getPosY() == food.getPosY())) {

			SnakeTail last = player.getTail().get(player.getTail().size() - 1);

			switch (last.getDir()) {
			case EAST:
				player.getTail().add(new SnakeTail(last.getPosX() - 1, last.getPosY(), Direction.EAST));
				break;
			case NORTH:
				player.getTail().add(new SnakeTail(last.getPosX(), last.getPosY() + 1, Direction.NORTH));
				break;
			case SOUTH:
				player.getTail().add(new SnakeTail(last.getPosX(), last.getPosY() - 1, Direction.SOUTH));
				break;
			case WEST:
				player.getTail().add(new SnakeTail(last.getPosX() + 1, last.getPosY(), Direction.WEST));
				break;
			default:
				break;

			}
			food.reroll();

		}
	}

}
