package com.atguigu.demo01;

import java.util.Objects;

/**
 * Billkin
 * 2023/6/4
 */
public class ExamStudent {
    private int FlowID;
    private int Type;
    private String IDCard;
    private String ExamCard;
    private String StudentName;
    private String Location;
    private int Grade;

    public ExamStudent() {}

    public ExamStudent(int flowID, int type, String IDCard, String examCard, String studentName, String location, int grade) {
        FlowID = flowID;
        Type = type;
        this.IDCard = IDCard;
        ExamCard = examCard;
        StudentName = studentName;
        Location = location;
        Grade = grade;
    }

    public int getFlowID() {
        return FlowID;
    }

    public void setFlowID(int flowID) {
        FlowID = flowID;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getIDCard() {
        return IDCard;
    }

    public void setIDCard(String IDCard) {
        this.IDCard = IDCard;
    }

    public String getExamCard() {
        return ExamCard;
    }

    public void setExamCard(String examCard) {
        ExamCard = examCard;
    }

    public String getStudentName() {
        return StudentName;
    }

    public void setStudentName(String studentName) {
        StudentName = studentName;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public int getGrade() {
        return Grade;
    }

    public void setGrade(int grade) {
        Grade = grade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExamStudent)) return false;
        ExamStudent that = (ExamStudent) o;
        return FlowID == that.FlowID && Type == that.Type && Grade == that.Grade && Objects.equals(IDCard, that.IDCard) && Objects.equals(ExamCard, that.ExamCard) && Objects.equals(StudentName, that.StudentName) && Objects.equals(Location, that.Location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(FlowID, Type, IDCard, ExamCard, StudentName, Location, Grade);
    }

    @Override
    public String toString() {
        return "ExamStudent{" +
                "FlowID=" + FlowID +
                ", Type=" + Type +
                ", IDCard='" + IDCard + '\'' +
                ", ExamCard='" + ExamCard + '\'' +
                ", StudentName='" + StudentName + '\'' +
                ", Location='" + Location + '\'' +
                ", Grade=" + Grade +
                '}';
    }
}
