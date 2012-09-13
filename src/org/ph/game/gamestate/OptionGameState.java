package org.ph.game.gamestate;

import org.andengine.engine.Engine;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.opengl.font.BitmapFont;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.ph.game.testgame.GameContext;
import org.ph.game.widget.AnimatedSpriteButton;

public class OptionGameState extends BaseGameState {
	private static final String BITMAP_FONT_PATH = "font/BitmapFont.fnt";

	private Scene mScene;
	private BitmapFont mBitmapFont;

	private BuildableBitmapTextureAtlas mBitmapTextureAtlas;
	private TiledTextureRegion mTiledButtonRegion;

	private GameContext mGameContext;
	private Engine mEngine;

	public OptionGameState(GameContext context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreateResources() {
		// TODO Auto-generated method stub
		mGameContext = getGameContext();
		mEngine = mGameContext.getEngine();
		TextureManager textureManager = mEngine.getTextureManager();

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("sprite/");
		mBitmapTextureAtlas = 
				new BuildableBitmapTextureAtlas(textureManager, 
						300, 200, TextureOptions.NEAREST);

		mTiledButtonRegion = BitmapTextureAtlasTextureRegionFactory.
				createTiledFromAsset(mBitmapTextureAtlas, 
						mGameContext.getGameActivity(), 
						"tiled_button_sprite0.png", 1, 2);

		try {
			mBitmapTextureAtlas.build(
					new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, 
					BitmapTextureAtlas>(0, 0, 0));
		} catch (TextureAtlasBuilderException e) {
			e.printStackTrace();
		}
		mBitmapTextureAtlas.load();

		mBitmapFont = 
		new BitmapFont(mGameContext.getEngine().getTextureManager(),
				mGameContext.getAssets(), BITMAP_FONT_PATH);
		mBitmapFont.load();
	}

	@Override
	public Scene onCreateScene() {
		// TODO Auto-generated method stub
		mScene = new Scene();
		mScene.setBackground(new Background(1.0f, 0.0f, 0.0f));

		AnimatedSpriteButton button = new AnimatedSpriteButton(0, 0, 
				mTiledButtonRegion, 
				mEngine.getVertexBufferObjectManager()) {

			@Override
			public void onClick() {
				mGameContext.getGameStateManager().removeCurrentState();
			}
		};
		button.setText("Back", mBitmapFont);
		mScene.registerTouchArea(button);
		mScene.attachChild(button);

		return mScene;
	}

}
