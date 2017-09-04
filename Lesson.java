package com.tatlicilar.visualeducation;

/**
 * Created by Asus on 24.08.2017.
 */

public class Lesson {
    public String lessonId;
    public  String lessonName;


   public  Lesson() {}

    public Lesson(String lessonId, String lessonName) {
        this.lessonId = lessonId;
        this.lessonName = lessonName;

    }
    public String getLessonName() {return lessonName;}

    public void setLessonName(String lessonName) {this.lessonName = lessonName;}

    public String getLessonId() {return lessonId;}

    public void setLessonId(String lessonId) {this.lessonId = lessonId;}

}
