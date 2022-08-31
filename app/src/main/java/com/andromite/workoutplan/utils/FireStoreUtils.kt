package com.andromite.workoutplan.utils

import android.content.Context
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FireStoreUtils {

    var db = Firebase.firestore

    fun storeUserInfo(context: Context, user: FirebaseUser) {
        val userIdentity = hashMapOf(
            "displayName" to user.displayName,
            "email" to user.email
        )

        db.collection(Enums.Users.name).document(user.uid).set(userIdentity)

        // save details in local storage
        SP.save(context, Enums.UserId.name, user.uid)
    }

}