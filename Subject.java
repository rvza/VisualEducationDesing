package com.tatlicilar.visualeducation;

/**
 * Created by Asus on 24.08.2017.
 */

public class Subject {
    public Subject(String subjectId, String subjName, String subjInfo, String subVideo) {
        this.subjectId = subjectId;
        this.subjName = subjName;
        this.subjInfo = subjInfo;
        this.subVideo = subVideo;
    }

    public  String subjectId;
    public String  subjName;
    public String subjInfo;
    public  String subVideo;

    public  Subject() {}

    public String getSubjName() {return subjName;}

    public void setSubjName(String subjName) {this.subjName = subjName;}

    public String getSubjectId() {return subjectId;}

    public void setSubjectId(String subjectId) {this.subjectId = subjectId;}

    public String getSubjInfo() {return subjInfo;}

    public void setSubjInfo(String subjInfo) {this.subjInfo = subjInfo;}

    public String getSubVideo() {return subVideo;}

    public void setSubVideo(String subVideo) {this.subVideo = subVideo;}


}
