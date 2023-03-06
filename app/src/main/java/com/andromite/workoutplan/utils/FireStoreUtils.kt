package com.andromite.workoutplan.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
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

    fun addOrUpdateWorkoutList(context: Context,date : String, workoutList : String, listener: (String)-> Unit) {
        val userId = SP.get(context, Enums.UserId.name)

        val data = hashMapOf("jsonString" to workoutList)
        db.collection(Enums.Users.name).document(userId).collection(Enums.workoutList.name).document(date).set(data)
            .addOnSuccessListener {
                Log.e("asdfasdf", "data added successfully $date")
                listener.invoke("success")
            }
            .addOnFailureListener {
                Log.e("asdfasdf", "data failed $date ${it.message}")
                listener.invoke("failed")
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

    fun deleteWorkoutList(context: Context, docName : String, callback: () -> Unit) {
        val userId = SP.get(context, Enums.UserId.name)

        db.collection(Enums.Users.name).document(userId).collection(Enums.workoutList.name).document(docName)
            .delete()
            .addOnSuccessListener {
                callback.invoke()
            }
            .addOnFailureListener {
                Log.e("asdfasdf","document list: ${it.message}")
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT)
            }
    }
}