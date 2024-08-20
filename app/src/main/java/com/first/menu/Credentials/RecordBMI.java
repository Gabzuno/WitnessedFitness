package com.first.menu.Credentials;

public class RecordBMI {
    public String gender, height,weight, date, time, category, recordKey,age,bmi;


    public RecordBMI() {}

    public RecordBMI(String gender, String height, String weight, String date, String time, String category, String bmi, String age) {
        this.gender = gender;
        this.height = height;
        this.weight = weight;
        this.date = date;
        this.time = time;
        this.category = category;
        this.bmi = bmi;
        this.age = age;
    }

    public String getRecordKey() {
        return recordKey;
    }

    public void setRecordKey(String recordKey) {
        this.recordKey = recordKey;
    }
}
