package by.solutions.dumb.smartfoodassistant.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.GoogleAuthProvider;

import by.solutions.dumb.smartfoodassistant.R;
import by.solutions.dumb.smartfoodassistant.fragments.AuthSignOutDialogFragment;

public class SignInActivity extends AppCompatActivity {

    //region Variables

    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN_GOOGLE = 8841;
    private static final String TAG_DIALOG_SIGN_OUT = "LogOutDialog";
    AuthSignOutDialogFragment signOutDialog;
    private FirebaseAuth firebaseAuth;
    private GoogleApiClient googleApiClient;
    private TextView userLoginTextView;
    private TextView userIdTextView;
    private View userInfoContainerView;
    private Button signInButton;
    private ActionBar actionBar;
    private MenuItem signOutItem;
    private ProgressDialog progressDialog;
    private FragmentManager fragmentManager;


    //endregion


    //region Activity lifecycle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        userInfoContainerView = findViewById(R.id.user_info_container);
        userLoginTextView = findViewById(R.id.user_login);
        userIdTextView = findViewById(R.id.user_id);
        signInButton = findViewById(R.id.sign_in_button);
        actionBar = getSupportActionBar();

        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(R.string.title_authentication);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                        Log.d(TAG, "Google Services Error" + connectionResult);
                        Toast.makeText(SignInActivity.this, "Google Services Error", Toast.LENGTH_SHORT).show();
                    }
                })
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        firebaseAuth = FirebaseAuth.getInstance();

        fragmentManager = getSupportFragmentManager();
        signOutDialog = new AuthSignOutDialogFragment();
        signOutDialog.setSignInActivity(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressDialog();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN_GOOGLE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            } else {
                updateUI(null);
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        setResult(RESULT_OK);
        finish();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sign_in_activity_toolbar_actions, menu);
        signOutItem = menu.findItem(R.id.action_sign_out);
        signOutItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                signOutDialog.show(fragmentManager, TAG_DIALOG_SIGN_OUT);
                return false;
            }
        });
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        updateUI(currentUser);
        return super.onCreateOptionsMenu(menu);
    }

    //endregion


    //region Private methods

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN_GOOGLE);
    }

    private void revokeAccess() {
        firebaseAuth.signOut();

        Auth.GoogleSignInApi.revokeAccess(googleApiClient)
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        updateUI(null);
                    }
                });
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        Log.d(TAG, "Google user ID: " + account.getId());

        showProgressDialog();

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Firebase login: success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            setResult(RESULT_OK);
                            finish();
                        } else {
                            Log.w(TAG, "Firebase login: fail", task.getException());
                            Toast.makeText(SignInActivity.this, "Authentication error",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                        hideProgressDialog();
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        hideProgressDialog();
        if (user != null) {
            user.getIdToken(true).addOnSuccessListener(new OnSuccessListener<GetTokenResult>() {
                @Override
                public void onSuccess(GetTokenResult getTokenResult) {
                    String token = getTokenResult.getToken();
                }
            });

            actionBar.setTitle(R.string.title_account);
            userLoginTextView.setText(user.getEmail());
            userIdTextView.setText(user.getUid());

            userInfoContainerView.setVisibility(View.VISIBLE);
            signInButton.setVisibility(View.GONE);
            signOutItem.setVisible(true);
        } else {
            actionBar.setTitle(R.string.title_authentication);
            userLoginTextView.setText(null);
            userIdTextView.setText(null);

            userInfoContainerView.setVisibility(View.GONE);
            signInButton.setVisibility(View.VISIBLE);
            signOutItem.setVisible(false);
        }
    }

    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage(getString(R.string.loading));
            progressDialog.setIndeterminate(true);
        }

        progressDialog.show();
    }

    private void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    //endregion


    //region Public methods

    public void signOut() {
        firebaseAuth.signOut();

        Auth.GoogleSignInApi.signOut(googleApiClient)
                .setResultCallback(new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
                        setResult(RESULT_OK);
                        finish();
                    }
                });
    }

    //endregion
}
