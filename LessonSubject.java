package com.tatlicilar.visualeducation;

/**
 * Created by Asus on 24.08.2017.
 */

public class LessonSubject {
    public  String userId;
    public String lessonId;
    public  String subjectId;
    public  String subjectOrder;

    public  LessonSubject() {}

    public LessonSubject(String userId, String subjectId, String lessonId, String subjectOrder) {
        this.userId = userId;
        this.subjectId = subjectId;
        this.lessonId = lessonId;
        this.subjectOrder = subjectOrder;
    }
    public String getSubjectId() {return subjectId;}

    public void setSubjectId(String subjectId) {this.subjectId = subjectId;}

    public String getUserId() {return userId;}

    public void setUserId(String userId) {this.userId = userId;}

    public String getLessonId() {return lessonId;}

    public void setLessonId(String lessonId) {this.lessonId = lessonId;}

    public String getSubjectOrder() {return subjectOrder;}

    public void setSubjectOrder(String subjectOrder) {this.subjectOrder = subjectOrder;}
}
