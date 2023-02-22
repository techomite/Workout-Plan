package com.andromite.workoutplan.utils

import android.content.Context
import android.util.Log
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class FireStoreUtils {

    private var db = Firebase.firestore

    fun storeUserInfo(context: Context, user: FirebaseUser) {
        val userIdentity = hashMapOf(
            "displayName" to user.displayName,
            "email" to user.email
        )

        db.collection(Enums.Users.name).document(user.uid).set(userIdentity)

        // save details in local storage
        SP.save(context, Enums.UserId.name, user.uid)
    }

    fun addOrUpdateWorkoutList(context: Context,date : String, workoutList : String) {
        val userId = SP.get(context, Enums.UserId.name)

        val data = hashMapOf("jsonString" to workoutList)
        db.collection(Enums.Users.name).document(userId).collection(Enums.workoutList.name).document(date).set(data)
            .addOnSuccessListener {
                Log.e("asdfasdf", "data added successfully $date")
            }
            .addOnFailureListener {
                Log.e("asdfasdf", "data failed $date ${it.message}")
            }
    }

    fun readWorkoutList(context: Context, docName : String, callback: (result: String?) -> Unit) {
        val userId = SP.get(context, Enums.UserId.name)

        db.collection(Enums.Users.name).document(userId).collection(Enums.workoutList.name).document(docName)
            .get()
            .addOnSuccessListener {
                Log.e("asdfasdf","document list: ${it.get("jsonString")}")
                callback.invoke(it.get("jsonString").toString())
            }
            .addOnFailureListener {
                Log.e("asdfasdf","document list: ${it.message}")
            }
    }
}