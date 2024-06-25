package com.example.task.helper

import com.example.task.R
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.lang.Error

class FirebaseHelper {
    fun getDatabase()=FirebaseDatabase.getInstance().reference
    private fun getAuth()=FirebaseAuth.getInstance()
    fun getIdUser()=getAuth().uid

    fun isAutenticated()=getAuth().currentUser !=null
    fun ValidError(error: String): Int{
        return when {
            error.contains("There is not record correspinding to this identifier") -> {
                R.string.account_not_registered_register_fragment
            }

            error.contains("The email adress is badly formatted") -> {
                R.string.invalid_email_register_fragment
            }

            error.contains("The password is invalid or the user does not hava a password.") -> {
                R.string.invalid_password_register_fragment
            }

            error.contains("The email addres is already in use another account") -> {
                R.string.email_in_use_register_fragment
            }

            else->{
                R.string.error_generic
            }
        }


        }
    }
