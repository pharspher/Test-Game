package org.ph.game.world;

import org.andengine.entity.scene.Scene;
import org.ph.game.testgame.GameContext;

import android.view.KeyEvent;

public class BaseGameWorld {
    private Scene mScene;
    private GameContext mContext;
    
    public BaseGameWorld(GameContext pContext) {
        mContext = pContext;
        mScene = new Scene();
    }
    
    public BaseGameWorld(GameContext pContext, Scene pScene) {
        mContext = pContext;
        mScene = pScene;
    }
    
    public Scene getScene() {
        return mScene;
    }
    
    protected void setScene(Scene pScene) {
        mScene = pScene;
    }
    
    protected GameContext getGameContext() {
        return mContext;
    }
    
    public boolean onKeyDown(int pKeyCode, KeyEvent pEvent) {
        return false;
    }
}
