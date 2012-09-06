package org.ph.game.gamestate;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;

public class MenuGameState extends BaseGameState {

	private Scene mScene;

	@Override
	public Scene createScene() {
		mScene = new Scene();
		mScene.setBackground(new Background(0, 0, 0));
		return mScene;
	}

}
