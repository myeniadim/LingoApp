package com.msku.ceng3505.lingoapp.helpers;

import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.msku.ceng3505.lingoapp.models.UserProgress;
import com.msku.ceng3505.lingoapp.models.Vocabulary;

import java.util.ArrayList;
import java.util.List;

public class UserProgressFirebaseHelper {
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    String userId;

    public UserProgressFirebaseHelper() {
        this.db = FirebaseFirestore.getInstance();
        this.mAuth = FirebaseAuth.getInstance();
        this.userId = mAuth.getUid();
    }

    public void addUserProgress(UserProgress userProgress, FirestoreCallback<Void> callback) {
        db.collection("users")
                .document(userId)
                .collection("progress")
                .add(userProgress)
                .addOnSuccessListener(documentReference -> {
                    callback.onSuccess(null); // Void tipinde bir şey dönmez
                })
                .addOnFailureListener(e -> {
                    callback.onFailure(e);
                });
    }

    public void getAllUserProgress(FirestoreCallback<List<UserProgress>> callback){
        db.collection("users")
                .document(userId)
                .collection("progress")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<UserProgress> progressList = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : querySnapshot) {
                        UserProgress progress = doc.toObject(UserProgress.class);
                        progressList.add(progress);
                    }
                    callback.onSuccess(progressList);
                })
                .addOnFailureListener(callback::onFailure);
    }

    public void updateUserProgressBySectionId(String sectionId, UserProgress updatedProgress, FirestoreCallback<Void> callback) {
        db.collection("users")
                .document(userId)
                .collection("progress")
                .whereEqualTo("sectionId", sectionId)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    if (!querySnapshot.isEmpty()) {
                        // İlk eşleşen belgeyi al
                        String documentId = querySnapshot.getDocuments().get(0).getId();

                        // Belgeyi güncelle
                        db.collection("users")
                                .document(userId)
                                .collection("progress")
                                .document(documentId)
                                .set(updatedProgress) // Güncellenmiş veriyi kaydet
                                .addOnSuccessListener(aVoid -> {
                                    Log.d("Firestore", "Progress updated successfully for sectionId: " + sectionId);
                                    callback.onSuccess(null);
                                })
                                .addOnFailureListener(e -> {
                                    Log.w("Firestore", "Error updating progress for sectionId: " + sectionId, e);
                                    callback.onFailure(e);
                                });
                    } else {
                        // Eğer belge yoksa
                        Log.w("Firestore", "No progress found for sectionId: " + sectionId);
                        callback.onFailure(new Exception("No progress found for sectionId: " + sectionId));
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Error fetching progress for sectionId: " + sectionId, e);
                    callback.onFailure(e);
                });
    }

    public void getUserProgressBySectionId(String sectionId, FirestoreCallback<UserProgress> callback) {
        db.collection("users")
                .document(userId)
                .collection("progress")
                .whereEqualTo("sectionId", sectionId)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    if (!querySnapshot.isEmpty()) {
                        UserProgress progress = querySnapshot.getDocuments().get(0).toObject(UserProgress.class);
                        callback.onSuccess(progress);
                    } else {
                        callback.onFailure(new Exception("No progress found for sectionId: " + sectionId));
                    }
                })
                .addOnFailureListener(callback::onFailure);
    }


    public interface FirestoreCallback<T> {
        void onSuccess(T result);

        void onFailure(Exception e);
    }
}
