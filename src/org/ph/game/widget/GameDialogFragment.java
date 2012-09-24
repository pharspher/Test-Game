package org.ph.game.widget;

import org.ph.game.testgame.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

public class GameDialogFragment extends DialogFragment {
	private static final String KEY_DIALOG_TYPE = "dialog_type";
	private static final String KEY_DIALOG_TITLE = "dialog_title";
	
	public static final int DIALOG_LEAVE_CONFIRM = 0;

	public interface OnResultListener {
		public void onConfirm();
		public void onCancel();
	}

	private OnResultListener mOnResultListener;

	public static GameDialogFragment createDialogFragment(int type, String dialogTitle) {
		GameDialogFragment dialog = new GameDialogFragment();
		Bundle args = new Bundle();
		args.putInt(KEY_DIALOG_TYPE, type);
		args.putString(KEY_DIALOG_TITLE, dialogTitle);
		dialog.setArguments(args);
		return dialog;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		int dialogType = getArguments().getInt(KEY_DIALOG_TYPE);
		String dialogTitle = getArguments().getString(KEY_DIALOG_TITLE);
		switch (dialogType) {
		case DIALOG_LEAVE_CONFIRM:
			return new AlertDialog.Builder(getActivity())
					.setTitle(dialogTitle)
					.setPositiveButton(R.string.DIALOG_CONFIRMATION_OK, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							if (mOnResultListener != null) {
								mOnResultListener.onConfirm();
							}
						}})
					.setNegativeButton(R.string.DIALOG_CONFIRMATION_CANCEL, new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							if (mOnResultListener != null) {
								mOnResultListener.onCancel();
							}
						}})
					.create();
		default:
			return new AlertDialog.Builder(getActivity()).create();
		}
	}

	public void setOnResultListener(OnResultListener listener) {
		mOnResultListener = listener;
	}
}
