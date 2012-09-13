package org.ph.game.testgame;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.ph.game.gamestate.GameStateManager;
import org.ph.game.gamestate.MenuGameState;

public class MainActivity extends SimpleBaseGameActivity {

	private static final int CAMERA_WIDTH = 1280;
	private static final int CAMERA_HEIGHT = 720;

	private GameContext mContext;
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
		mContext = new GameContext(this);
		//mGameStateManager = new GameStateManager(mContext);
		mGameStateManager = mContext.getGameStateManager();
		mGameStateManager.addGameState(new MenuGameState(mContext));
	}

	@Override
	protected Scene onCreateScene() {
		//return mGameStateManager.getCurrentScene();
		return mContext.getGameStateManager().getCurrentScene();
	}

	@Override
	public void onResumeGame() {
		super.onResumeGame();
	}

	@Override
	public void onPauseGame() {
		super.onPauseGame();
	}
}
