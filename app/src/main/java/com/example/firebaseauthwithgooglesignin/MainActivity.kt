package com.example.firebaseauthwithgooglesignin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_google_sign_in_auth.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var auth:FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        bt_sign_out.setOnClickListener {
            auth?.signOut()
            Toast.makeText(this,"Signed out successfully",Toast.LENGTH_LONG).show()
            startActivity(Intent(this,GoogleSignInAuth::class.java))
        }

    }

    override fun onStart() {
        super.onStart()
        if(auth?.currentUser == null){
            startActivity(Intent(this,GoogleSignInAuth::class.java))
        }
        else{
            tv_wellcome.append("wellcome\n" + auth?.currentUser?.displayName.toString())
            Picasso.with(this).load(auth?.currentUser?.photoUrl).fit().into(iv_googel_photo)

        }
    }
}

