package org.ph.game.gamestate;

import org.andengine.entity.scene.Scene;

public abstract class BaseGameState {
	private Scene mScene;

	public BaseGameState() {
		mScene = createScene();
	}

	public Scene getScene() {
		return mScene;
	}

	public abstract Scene createScene();
}
