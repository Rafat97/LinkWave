package com.example.linkwave;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class VerifiyCodeActivity extends AppCompatActivity {

    Button myButton_SubmitButton;
    EditText myEditText_CodeTextEditText;

    String VerifyCode;
    private FirebaseAuth fbAuth;
    String TAG="FIREBASE_AUTH";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifiy_code);

        myButton_SubmitButton = (Button) findViewById(R.id.Verify_Code_SubmitButton) ;
        myEditText_CodeTextEditText = (EditText) findViewById(R.id.Verify_Code_EditTextView);

        fbAuth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        VerifyCode = intent.getExtras().getString("User_Verify_Code");

        Toast.makeText(VerifiyCodeActivity.this, "Please Check Your Message ", Toast.LENGTH_SHORT).show();


        myButton_SubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String code = myEditText_CodeTextEditText.getText().toString();
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(VerifyCode, code);
                signInWithPhoneAuthCredential(credential);
            }
        });




    }




    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        fbAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();
                            Intent myInternt_Error = new Intent(VerifiyCodeActivity.this,ProfileActivity.class);
                            myInternt_Error.setFlags( Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(myInternt_Error);
                            // ...
                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(VerifiyCodeActivity.this, "Please Check Your Verification Code ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

}
