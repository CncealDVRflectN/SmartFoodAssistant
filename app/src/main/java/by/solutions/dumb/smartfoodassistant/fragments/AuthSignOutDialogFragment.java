package by.solutions.dumb.smartfoodassistant.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import by.solutions.dumb.smartfoodassistant.R;
import by.solutions.dumb.smartfoodassistant.activities.SignInActivity;

public class AuthSignOutDialogFragment extends DialogFragment {

    //region Variables

    SignInActivity signInActivity;

    //endregion


    //region DialogFragment lifecycle

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.sign_out_dialog_title));
        builder.setMessage(getString(R.string.sign_out_dialog_message));
        builder.setPositiveButton(getString(R.string.sign_out_dialog_positive),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        signInActivity.signOut();
                    }
                });
        builder.setNegativeButton(getString(R.string.sign_out_dialog_negative),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });
        builder.setCancelable(true);

        return builder.create();
    }

    //endregion


    //region Public methods

    public void setSignInActivity(SignInActivity signInActivity) {
        this.signInActivity = signInActivity;
    }

    //endregion
}
