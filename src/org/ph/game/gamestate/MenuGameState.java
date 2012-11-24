package org.ph.game.gamestate;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.font.BitmapFont;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.bitmap.BitmapTexture;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.util.adt.io.in.IInputStreamOpener;
import org.ph.game.testgame.GameContext;
import org.ph.game.widget.AnimatedSpriteButton;
import org.ph.game.widget.ScrollingBackground;

import android.util.Log;

public class MenuGameState extends BaseGameState {

	private static final String TAG = "MenuGameState";
	private static final String BITMAP_FONT_PATH = "font/BitmapFont.fnt";

	private Scene mScene;
	private BitmapFont mBitmapFont;

	private BuildableBitmapTextureAtlas mBitmapTextureAtlas;
	private TiledTextureRegion mTiledButtonRegion;

	private GameContext mGameContext;
	private Engine mEngine;

	private BaseGameState mGameState;
	private BaseGameState mOptionState;

	private class BackgroundData {
		public ITexture mTexture;
		public ITextureRegion mRegion;
		public Sprite mSprite;
	}

	private ArrayList<BackgroundData> mBackgrounds =
			new ArrayList<BackgroundData>();
	private ScrollingBackground mScrollingBackground = 
			new ScrollingBackground();

	private static final String[] mMenuItemList = new String[] {
		"New Game", "Resume Game", "Options", "Quit"
	};

	private MenuItemClickListener[] mMenuItemClickListener;

	private interface MenuItemClickListener {
		public void onClick();
	};

	public MenuGameState(GameContext context) {
		super(context);
		Log.d(TAG, "MenuGameState()");
	}

	@Override
	public void onCreateResources() {
		Log.d(TAG, "onCreateResource()");

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

		initMenuItemClickListeners();

		for (int i = 0; i < 5; i++) {
			BackgroundData bgData = new BackgroundData();
			final int ci = i;
			try {
				bgData.mTexture = new BitmapTexture(textureManager, 
						new IInputStreamOpener() {
					@Override
					public InputStream open() throws IOException {
						return mContext.getAssets()
								.open("sprite/background/scroll_bg0" + ci + ".png");
					}
				});
				bgData.mTexture.load();
				bgData.mRegion = TextureRegionFactory
						.extractFromTexture(bgData.mTexture);
				bgData.mSprite = new Sprite(0, 0 , bgData.mRegion, 
						mEngine.getVertexBufferObjectManager());
				mScrollingBackground.addBackgroundPage(
						new ScrollingBackground.BackgroundPage(bgData.mSprite));
			} catch (IOException e) {
				e.printStackTrace();
			}
			mBackgrounds.add(bgData);
		}
	}

	private void initMenuItemClickListeners() {
		mMenuItemClickListener = new MenuItemClickListener[mMenuItemList.length];
		mMenuItemClickListener[0] = new MenuItemClickListener() {
			@Override
			public void onClick() {
				GameStateManager mgr = mGameContext.getGameStateManager();
				if (mGameState == null) {
					mGameState = new RunGameState(mGameContext);
				}
				mgr.changeState(mGameState);
			}
		};
		mMenuItemClickListener[1] = new MenuItemClickListener() {
			@Override
			public void onClick() {
			}
		};
		mMenuItemClickListener[2] = new MenuItemClickListener() {
			@Override
			public void onClick() {
				GameStateManager mgr = mGameContext.getGameStateManager();
				if (mOptionState == null) {
					mOptionState = new OptionGameState(mGameContext);
				}
				mgr.changeState(mOptionState);
			}
		};
		mMenuItemClickListener[3] = new MenuItemClickListener() {
			@Override
			public void onClick() {
				mGameContext.getGameActivity().finish();
			}
		};
	}

	@Override
	public Scene onCreateScene() {
		Log.d(TAG, "onCreateScene()");

		mScene = new Scene();
		mScene.setBackground(new Background(1.0f, 1.0f, 1.0f));
		mScene.setTouchAreaBindingOnActionDownEnabled(true);

		final Engine engine = mGameContext.getEngine();

		final Camera camera = engine.getCamera();
		int yInterval = 
				(int)(camera.getHeight() / (mMenuItemList.length + 1));

		for (int i = 0; i < mMenuItemList.length; i++) {
			final int ci = i;

			AnimatedSpriteButton button = new AnimatedSpriteButton(0, 0, 
					mTiledButtonRegion, 
					mEngine.getVertexBufferObjectManager()) {

				@Override
				public void onClick() {
					mMenuItemClickListener[ci].onClick();
				}
			};
			button.setText(mMenuItemList[ci], mBitmapFont);
			button.setPosition(0, 
					(ci + 1) * yInterval - button.getHeight() / 2);
			mScene.registerTouchArea(button);
			mScene.attachChild(button);
		}
		mScene.setBackground(mScrollingBackground);

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
}
