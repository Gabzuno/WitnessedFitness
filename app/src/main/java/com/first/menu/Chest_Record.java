package com.first.menu;

public class Chest_Record {
    public String knee, diamond, pushUp, WideArm, Clap, Incline, Decline, Hindu, Medicine, Burpees,ChestTotal,recordKey;


    public Chest_Record() {}

    public Chest_Record(String knee, String diamond, String pushUp, String WideArm, String Clap, String Incline, String Decline, String Hindu, String Medicine, String Burpees, String ChestTotal) {
        this.knee = knee;
        this.diamond = diamond;
        this.pushUp = pushUp;
        this.WideArm = WideArm;
        this.Clap = Clap;
        this.Incline = Incline;
        this.Decline = Decline;
        this.Hindu = Hindu;
        this.Medicine = Medicine;
        this.Burpees = Burpees;
        this.ChestTotal = ChestTotal;
    }
    public void setKneeCB(String calorieBurn) {
        this.knee = calorieBurn;
    }
    public String getKneeCB() {
        return knee;
    }
    public void setDiamondCB(String calorieBurn) {
        this.diamond = calorieBurn;
    }
    public String getDiamondCB() {
        return diamond;
    }
    public void setPushUpCB(String calorieBurn) {
        this.pushUp = calorieBurn;
    }
    public String getPushUpCB() {
        return pushUp;
    }
    public void setWideArmCB(String calorieBurn) {
        this.WideArm = calorieBurn;
    }
    public String getWideArmCB() {
        return WideArm;
    }
    public void setClapCB(String calorieBurn) {
        this.Clap = calorieBurn;
    }
    public String getClapCB() {
        return Clap;
    }
    public void setInclineCB(String calorieBurn) {
        this.Incline = calorieBurn;
    }
    public String getInclineCB() {
        return Incline;
    }
    public void setDeclineCB(String calorieBurn) {
        this.Decline = calorieBurn;
    }
    public String getDeclineCB() {
        return Decline;
    }
    public void setHinduCB(String calorieBurn) {
        this.Hindu = calorieBurn;
    }
    public String getHinduCB() {
        return Hindu;
    }
    public void setMedicineCB(String calorieBurn) {
        this.Incline = calorieBurn;
    }
    public String getMedicineCB() {
        return Medicine;
    }
    public void setBurpeesCB(String calorieBurn) {
        this.Burpees = calorieBurn;
    }
    public String getBurpeesCB() {
        return Burpees;
    }

    public String getRecordKey() {
        return recordKey;
    }

    public void setRecordKey(String recordKey) {
        this.recordKey = recordKey;
    }
}
