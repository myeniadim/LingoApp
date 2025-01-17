package com.msku.ceng3505.lingoapp.helpers;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.msku.ceng3505.lingoapp.models.Question;
import com.msku.ceng3505.lingoapp.models.Section;

import java.util.ArrayList;
import java.util.List;

public class SectionsFirebaseHelper {
    FirebaseFirestore db;

    public SectionsFirebaseHelper(){
        this.db = FirebaseFirestore.getInstance();
    }

    public void getAllSections(FlashcardsFirebaseHelper.FirestoreCallback<List<Section>> callback) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();


        db.collection("sections")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        List<Section> sections = new ArrayList<>();
                        QuerySnapshot querySnapshot = task.getResult();

                        if (querySnapshot != null) {
                            for (QueryDocumentSnapshot document : querySnapshot) {
                                Section section = new Section();
                                section.setSectionId(document.getString("sectionId"));
                                section.setTitle(document.getString("title"));
                                section.setDifficultyLevel(document.getString("difficultyLevel"));
                                section.setReadingHeader(document.getString("readingHeader"));
                                section.setReadingContent(document.getString("readingContent"));


                                String sectionId = document.getId();
                                db.collection("sections")
                                        .document(sectionId)
                                        .collection("questions")
                                        .get()
                                        .addOnCompleteListener(questionTask -> {
                                            if (questionTask.isSuccessful()) {
                                                List<Question> questions = new ArrayList<>();
                                                QuerySnapshot questionSnapshot = questionTask.getResult();

                                                if (questionSnapshot != null) {
                                                    for (QueryDocumentSnapshot questionDoc : questionSnapshot) {
                                                        Question question = new Question(
                                                                questionDoc.getString("questionId"),
                                                                questionDoc.getString("question"),
                                                                ((List<String>) questionDoc.get("options")).toArray(new String[0]),
                                                                questionDoc.getString("answer")
                                                        );
                                                        questions.add(question);
                                                    }
                                                }
                                                section.setQuestions(questions);


                                                sections.add(section);


                                                if (sections.size() == querySnapshot.size()) {
                                                    callback.onSuccess(sections);
                                                }
                                            } else {
                                                callback.onFailure(questionTask.getException());
                                            }
                                        });
                            }
                        }
                    } else {
                        callback.onFailure(task.getException());
                    }
                });
    }


    public interface FirestoreCallback<T> {
        void onSuccess(T result);

        void onFailure(Exception e);
    }
}
