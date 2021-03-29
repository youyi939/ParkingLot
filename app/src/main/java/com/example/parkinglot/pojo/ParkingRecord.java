package com.example.parkinglot.pojo;

public class ParkingRecord {
    private String outTime ;            //出场时间
    private String entryTime ;          //入场时间
    private String plateNumber ;        //车牌号
    private String monetary ;           //收费金额
    private String parkName ;           //停车场名称


    public ParkingRecord(String outTime, String entryTime, String plateNumber, String monetary, String parkName) {
        this.outTime = outTime;
        this.entryTime = entryTime;
        this.plateNumber = plateNumber;
        this.monetary = monetary;
        this.parkName = parkName;
    }


    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getMonetary() {
        return monetary;
    }

    public void setMonetary(String monetary) {
        this.monetary = monetary;
    }

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }

    @Override
    public String toString() {
        return "ParkingRecord{" +
                "outTime='" + outTime + '\'' +
                ", entryTime='" + entryTime + '\'' +
                ", plateNumber='" + plateNumber + '\'' +
                ", monetary='" + monetary + '\'' +
                ", parkName='" + parkName + '\'' +
                '}';
    }
}
