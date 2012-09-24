package org.ph.game.gamestate;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.font.BitmapFont;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.ph.game.testgame.GameContext;
import org.ph.game.widget.AnimatedSpriteButton;
import org.ph.game.widget.BackgroundScroller;

public class OptionGameState extends BaseGameState {
	private static final String BITMAP_FONT_PATH = "font/BitmapFont.fnt";

	private Scene mScene;
	private BitmapFont mBitmapFont;

	private BuildableBitmapTextureAtlas mBitmapTextureAtlas;
	private TiledTextureRegion mTiledButtonRegion;

	private BitmapTextureAtlas mAtlas;
	private ITextureRegion mBgRegion;
	private ITextureRegion mBgRegion2;
	private Sprite mSprite;
	private Sprite mSprite2;

	

	private GameContext mGameContext;
	private Engine mEngine;

	private BackgroundScroller mBackgroundScroller;

	public OptionGameState(GameContext context) {
		super(context);
	}

	@Override
	public void onCreateResources() {
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

		Camera camera = mEngine.getCamera();
		mBackgroundScroller = new BackgroundScroller(mGameContext, 
				(int)camera.getWidth(), (int)camera.getHeight());
		//BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("sprite/background/");
		for (int i = 0; i < 2; i++) {
			mBackgroundScroller.addBackground("background/scroll_bg0" + (i + 1) + ".png");
		}
		mBackgroundScroller.load();

		//mAtlas = new BitmapTextureAtlas(textureManager, 1280,
		//		1440, TextureOptions.BILINEAR);
		//mAtlas = mBackgroundScroller.getTextureAtlas();
		//mBgRegion = BitmapTextureAtlasTextureRegionFactory
		//		.createFromAsset(mAtlas, 
		//				mContext.getGameActivity(), "background/scroll_bg01.png", 
		//				0, 0);
		//mBgRegion2 = BitmapTextureAtlasTextureRegionFactory
		//		.createFromAsset(mAtlas, 
		//				mContext.getGameActivity(), "background/scroll_bg02.png", 
		//				0, 720);
		//mAtlas.load();
		//p.mSprite = new Sprite(0, 0, mPageWi
	}

	@Override
	public Scene onCreateScene() {
		mScene = new Scene();
		mScene.setBackground(new Background(1.0f, 0.0f, 0.0f));
		mScene.setTouchAreaBindingOnActionDownEnabled(true);

		AnimatedSpriteButton button = new AnimatedSpriteButton(0, 0, 
				mTiledButtonRegion, 
				mEngine.getVertexBufferObjectManager()) {

			@Override
			public void onClick() {
				mGameContext.getGameStateManager().popState();
			}
		};
		button.setText("Back", mBitmapFont);
		mScene.registerTouchArea(button);
		mScene.attachChild(button);

		mBackgroundScroller.apply(mScene);
		
		//mSprite = new Sprite(0, 0, mBgRegion,
		//		mEngine.getVertexBufferObjectManager());
		//mSprite2 = new Sprite(0, 0, mBgRegion2,
		//		mEngine.getVertexBufferObjectManager());
		//mScene.attachChild(mSprite2);

		return mScene;
	}

	@Override
	public void onResume() {
	}

	@Override
	public void onPause() {
	}

	@Override
	public void onDestroy() {
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
