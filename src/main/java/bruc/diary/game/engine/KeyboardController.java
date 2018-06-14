package bruc.diary.game.engine;

import bruc.diary.game.entity.Renderer;

public class KeyboardController {

	private char lastKey = 'd';

	public KeyboardController(Renderer renderer) {
		renderer.setOnKeyTyped(e -> {
			switch (e.getCharacter()) {
			case "w":
				lastKey = 'w';
				break;
			case "d":
				lastKey = 'd';
				break;
			case "s":

				lastKey = 's';
				break;
			case "a":

				lastKey = 'a';
				break;
			}
		});
	}

	public char getLastKey() {
		return lastKey;
	}
}
