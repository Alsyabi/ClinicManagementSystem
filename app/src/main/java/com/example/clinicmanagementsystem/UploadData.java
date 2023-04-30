package com.example.clinicmanagementsystem;

import com.google.firebase.database.Exclude;

    public class UploadData {
    public String pDoc;
    public String pID;
    public String pName;
    public String pNumber;
    public String pDate;
    public String pTime;
    public String pCheck;

    public String mKey;

    public UploadData() {
    }

        public UploadData(String pDoc, String pID, String pName, String pNumber, String pDate, String pTime, String pCheck) {
            this.pDoc = pDoc;
            this.pID = pID;
            this.pName = pName;
            this.pNumber = pNumber;
            this.pDate = pDate;
            this.pTime = pTime;
            this.pCheck = pCheck;
        }

        public String getpDoc() {
            return pDoc;
        }

        public void setpDoc(String pDoc) {
            this.pDoc = pDoc;
        }

        public String getpID() {
            return pID;
        }

        public void setpID(String pID) {
            this.pID = pID;
        }

        public String getpName() {
            return pName;
        }

        public void setpName(String pName) {
            this.pName = pName;
        }

        public String getpNumber() {
            return pNumber;
        }

        public void setpNumber(String pNumber) {
            this.pNumber = pNumber;
        }

        public String getpDate() {
            return pDate;
        }

        public void setpDate(String pDate) {
            this.pDate = pDate;
        }

        public String getpTime() {
            return pTime;
        }

        public void setpTime(String pTime) {
            this.pTime = pTime;
        }

        public String getpCheck() {
            return pCheck;
        }

        public void setpCheck(String pCheck) {
            this.pCheck = pCheck;
        }

        @Exclude
    public void setKey(String Key) {
        mKey = Key;
    }
}
