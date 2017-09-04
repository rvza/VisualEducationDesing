package com.tatlicilar.visualeducation;

/**
 * Created by Asus on 24.08.2017.
 */

public class ClassLesson {

    public  String classId;
    public  String lessonId;
    public  String lessonOrder;

    public  ClassLesson() {}

    public ClassLesson(String classId, String lessonId, String lessonOrder) {
        this.classId = classId;
        this.lessonId = lessonId;
        this.lessonOrder = lessonOrder;
    }

    public String getLessonOrder() {return lessonOrder;}

    public void setLessonOrder(String lessonOrder) {this.lessonOrder = lessonOrder;}

    public String getLessonId() {return lessonId;}

    public void setLessonId(String lessonId) {this.lessonId = lessonId;}

    public String getClassId() {return classId;}

    public void setClassId(String classId) {this.classId = classId;}
}
