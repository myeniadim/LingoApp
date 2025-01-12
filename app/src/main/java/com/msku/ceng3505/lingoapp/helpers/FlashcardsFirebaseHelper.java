package com.msku.ceng3505.lingoapp.helpers;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.msku.ceng3505.lingoapp.models.Vocabulary;

import java.util.ArrayList;
import java.util.List;

public class FlashcardsFirebaseHelper {
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    String userId;

    public FlashcardsFirebaseHelper() {
        this.db = FirebaseFirestore.getInstance();
        this.mAuth = FirebaseAuth.getInstance();
        this.userId = mAuth.getUid();
    }

    public void addVocabulary(Vocabulary vocabulary, FirestoreCallback callback) {
        db.collection("users")
                .document(userId)
                .collection("vocabulary")
                .add(vocabulary)
                .addOnSuccessListener(documentReference -> {

                    callback.onSuccess(vocabulary);
                })
                .addOnFailureListener(e -> {
                    callback.onFailure(e);
                });
    }

    public void getAllVocabularies(FirestoreCallback<List<Vocabulary>> callback) {
        db.collection("users")
                .document(userId)
                .collection("vocabulary")
                .orderBy("englishVocab")
                .limit(100)
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<Vocabulary> vocabList = new ArrayList<>();
                    for (QueryDocumentSnapshot doc : querySnapshot) {
                        Vocabulary vocab = doc.toObject(Vocabulary.class);
                        vocabList.add(vocab);
                    }
                    callback.onSuccess(vocabList);
                })
                .addOnFailureListener(callback::onFailure);
    }

    public interface FirestoreCallback<T> {
        void onSuccess(T result);

        void onFailure(Exception e);
    }
}
