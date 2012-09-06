package org.ph.game.gamestate;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.font.BitmapFont;
import org.andengine.util.HorizontalAlign;
import org.ph.game.testgame.GameContext;

import android.util.Log;

public class MenuGameState extends BaseGameState {

	private static final String TAG = "MenuGameState";
	private static final String BITMAP_FONT_PATH = "font/BitmapFont.fnt";

	private Scene mScene;
	private BitmapFont mBitmapFont;

	private GameContext mGameContext;

	private static String[] mMenuItemList = new String[] {
		"New Game", "Resume Game", "Options", "Quit"
	};

	public MenuGameState(GameContext context) {
		super(context);
		Log.d(TAG, "MenuGameState()");
	}

	@Override
	public void onCreateResources() {
		Log.d(TAG, "onCreateResource()");

		mGameContext = getGameContext();

		mBitmapFont = 
		new BitmapFont(mGameContext.getEngine().getTextureManager(),
				mGameContext.getAssets(), BITMAP_FONT_PATH);
		mBitmapFont.load();
	}

	@Override
	public Scene onCreateScene() {
		Log.d(TAG, "onCreateScene()");

		mScene = new Scene();
		mScene.setBackground(new Background(1.0f, 1.0f, 1.0f));

		final Camera camera = mGameContext.getEngine().getCamera();
		int itemInterval = 
				(int)(camera.getHeight() / (mMenuItemList.length + 1));

		for (int i = 0; i < mMenuItemList.length; i++) {
			Text itemText =  new Text(0, 
					(i + 1) * itemInterval - mBitmapFont.getLineHeight() / 2, 
					mBitmapFont, mMenuItemList[i], 
					new TextOptions(HorizontalAlign.CENTER), 
					mGameContext.getEngine().getVertexBufferObjectManager());

			itemText.setScale(2);
			itemText.setX(camera.getWidth() / 2 - (itemText.getWidth()) / 2);

			mScene.attachChild(itemText);
		}

		return mScene;
	}
}
