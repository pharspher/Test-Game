package org.ph.game.gamestate;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.ph.game.testgame.GameContext;
import org.ph.game.testgame.R;
import org.ph.game.widget.GameDialogFragment;
import org.ph.game.world.ScrollingWorld;

import android.app.FragmentManager;
import android.view.KeyEvent;

public class RunGameState extends BaseGameState {
    //private static final String TAG = RunGameState.class.getSimpleName();
    
    private ScrollingWorld mScrollingWorld;
    
    private BitmapTextureAtlas mBkgAtlas;
    private ITextureRegion mBkgBackRegion;
    private ITextureRegion mBkgMidRegion;
    private ITextureRegion mBkgFrontRegion;
    
    private BitmapTextureAtlas mCharacterAtlas;
    private TiledTextureRegion mPlayerRegion;
    
    public RunGameState(GameContext context) {
        super(context);
    }
    
    @Override
    public void onCreateResources() {
        createBackgroundResources();
        createCharacterResources();
    }
    
    @Override
    public Scene onCreateScene() {
        // TODO: maybe mScrollingWorld can be local variable
        mScrollingWorld = new ScrollingWorld(getGameContext());
        mScrollingWorld.setBackground(creteBackground());
        mScrollingWorld.addPlayer(createPlayer());
        
        mScrollingWorld.create();
        return mScrollingWorld.getScene();
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
    public boolean onKeyDown(int pKeyCode, KeyEvent pEvent) {
        switch (pKeyCode) {
        case KeyEvent.KEYCODE_BACK:
            FragmentManager mgr = mContext.getGameActivity()
                    .getFragmentManager();
            String dialogTitle = mContext.getGameActivity().getResources()
                    .getString(R.string.DIALOG_LEAVE_CONFIRMATION_MESSAGE);
            GameDialogFragment dialog = GameDialogFragment
                    .createDialogFragment(
                            GameDialogFragment.DIALOG_LEAVE_CONFIRM,
                            dialogTitle);
            dialog.setOnResultListener(new GameDialogFragment.OnResultListener() {
                @Override
                public void onConfirm() {
                    mContext.getGameStateManager().popState();
                }

                @Override
                public void onCancel() {
                }
            });
            dialog.show(mgr, "dialog");
            return true;
        default:
            return mScrollingWorld.onKeyDown(pKeyCode, pEvent);
        }
        //return super.onKeyDown(pKeyCode, pEvent);
    }
    
    private void createBackgroundResources() {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("sprite/background/");
        mBkgAtlas = new BitmapTextureAtlas(getEngine().getTextureManager(), 1024, 1024);
        mBkgFrontRegion = BitmapTextureAtlasTextureRegionFactory
                .createFromAsset(mBkgAtlas, getGameContext().getAssets(), 
                        "parallax_background_layer_front.png", 0, 0);
        mBkgBackRegion = BitmapTextureAtlasTextureRegionFactory
                .createFromAsset(mBkgAtlas, getGameContext().getAssets(), 
                        "parallax_background_layer_back.png", 0, 188);
        mBkgMidRegion = BitmapTextureAtlasTextureRegionFactory
                .createFromAsset(mBkgAtlas, getGameContext().getAssets(), 
                        "parallax_background_layer_mid.png", 0, 669);
        mBkgAtlas.load();
    }
    
    private AutoParallaxBackground creteBackground() {
        float camera_height = getEngine().getCamera().getHeight();
        final AutoParallaxBackground background = new AutoParallaxBackground(0, 0, 0, 5);
        final VertexBufferObjectManager vboMgr = getEngine().getVertexBufferObjectManager();
        background.attachParallaxEntity(new ParallaxEntity(0.0f, new Sprite(0, 
                camera_height - mBkgBackRegion.getHeight(), 
                mBkgBackRegion, vboMgr)));
        background.attachParallaxEntity(new ParallaxEntity(-5.0f, new Sprite(0, 
                80, mBkgMidRegion, vboMgr)));
        background.attachParallaxEntity(new ParallaxEntity(-10.0f, 
                new Sprite(0, camera_height - mBkgFrontRegion.getHeight(), 
                        mBkgFrontRegion, vboMgr)));
        return background;
    }
    
    private void createCharacterResources() {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("sprite/");
        mCharacterAtlas = new BitmapTextureAtlas(getEngine().getTextureManager(), 
                256, 128, TextureOptions.BILINEAR);
        mPlayerRegion = BitmapTextureAtlasTextureRegionFactory
                .createTiledFromAsset(mCharacterAtlas, 
                        getGameContext().getAssets(), 
                        "running_player.png", 0, 0, 3, 4);
        mCharacterAtlas.load();
    }
    
    private Sprite createPlayer() {
        final VertexBufferObjectManager vboMgr = getEngine().getVertexBufferObjectManager();
        AnimatedSprite playerSprite = new AnimatedSprite(0, 0,
                mPlayerRegion, vboMgr);
        return playerSprite;
    }
}
