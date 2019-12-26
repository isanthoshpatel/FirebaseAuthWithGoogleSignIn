package com.example.firebaseauthwithgooglesignin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import kotlinx.android.synthetic.main.activity_google_sign_in_auth.*

class GoogleSignInAuth : AppCompatActivity() {

    var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_google_sign_in_auth)

        auth = FirebaseAuth.getInstance()


        bt_google_sign_in.setOnClickListener {
            signIn()
        }
    }

    fun mGoogleSignInClient(): GoogleSignInClient {

        var gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        return GoogleSignIn.getClient(this, gso)
    }

    fun signIn() {
        val i = mGoogleSignInClient().signInIntent
        startActivityForResult(i, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            var task = GoogleSignIn.getSignedInAccountFromIntent(data)
            var acc = task.getResult(ApiException::class.java)

            var credential = GoogleAuthProvider.getCredential(acc?.idToken, null)
            signInWithCredentials(credential)
        }
    }

    fun signInWithCredentials(credential:AuthCredential) {

        auth?.signInWithCredential(credential)
            ?.addOnCompleteListener {

                if (it.isSuccessful) {
                    Toast.makeText(this, "Successful Login!!", Toast.LENGTH_LONG).show()
                    startActivity(Intent(this, MainActivity::class.java))
                }
                if(it.exception is FirebaseAuthInvalidCredentialsException){
                    Toast.makeText(this, "Invalid credential", Toast.LENGTH_LONG).show()
                }
                if(it.exception is FirebaseTooManyRequestsException){
                    Toast.makeText(this, "too many request", Toast.LENGTH_LONG).show()

                }

            }
    }
}
