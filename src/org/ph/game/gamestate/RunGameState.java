package org.ph.game.gamestate;

import org.andengine.entity.scene.Scene;
import org.ph.game.testgame.GameContext;
import org.ph.game.testgame.R;
import org.ph.game.widget.GameDialogFragment;
import org.ph.game.world.ScrollingWorld;

import android.app.FragmentManager;
import android.view.KeyEvent;

public class RunGameState extends BaseGameState {
    private static final String TAG = RunGameState.class.getSimpleName();
    
    private ScrollingWorld mScrollingWorld;
    
    public RunGameState(GameContext context) {
        super(context);
    }
    
    @Override
    public void onCreateResources() {
        
    }
    
    @Override
    public Scene onCreateScene() {
        // TODO: maybe mScrollingWorld can be local variable
        mScrollingWorld = new ScrollingWorld(getGameContext());
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
        }
        return super.onKeyDown(pKeyCode, pEvent);
    }
}
