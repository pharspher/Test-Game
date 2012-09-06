package org.ph.game.testgame;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.ph.game.gamestate.GameStateManager;

public class MainActivity extends SimpleBaseGameActivity {

	private static final int CAMERA_WIDTH = 720;
	private static final int CAMERA_HEIGHT = 480;

	private GameStateManager mGameStateManager;

	@Override
	public EngineOptions onCreateEngineOptions() {
		return new EngineOptions(true, 
				ScreenOrientation.LANDSCAPE_FIXED, 
				new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), 
				new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT));
	}

	@Override
	protected void onCreateResources() {
		mGameStateManager = GameStateManager.getInstance();
	}

	@Override
	protected Scene onCreateScene() {
		Scene scene = mGameStateManager.getCurrentScene();
		return scene;
	}
}
