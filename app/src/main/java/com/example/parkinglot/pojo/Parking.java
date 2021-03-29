package com.example.parkinglot.pojo;

public class Parking {

    private int id;
    private String parkName;                    //停车场名称
    private String vacancy;                     //空位
    private String priceCaps;                   //价格上限
    private String imgUrl;                      //图片url地址
    private String rates;                       //利率
    private String address;                     ////停车厂地点
    private String distance;                    //距离
    private String allPark;                     //停车位总数


    public Parking(int id, String parkName, String vacancy, String priceCaps, String imgUrl, String rates, String address, String distance, String allPark) {
        this.id = id;
        this.parkName = parkName;
        this.vacancy = vacancy;
        this.priceCaps = priceCaps;
        this.imgUrl = imgUrl;
        this.rates = rates;
        this.address = address;
        this.distance = distance;
        this.allPark = allPark;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getParkName() {
        return parkName;
    }

    public void setParkName(String parkName) {
        this.parkName = parkName;
    }

    public String getVacancy() {
        return vacancy;
    }

    public void setVacancy(String vacancy) {
        this.vacancy = vacancy;
    }

    public String getPriceCaps() {
        return priceCaps;
    }

    public void setPriceCaps(String priceCaps) {
        this.priceCaps = priceCaps;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getRates() {
        return rates;
    }

    public void setRates(String rates) {
        this.rates = rates;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getAllPark() {
        return allPark;
    }

    public void setAllPark(String allPark) {
        this.allPark = allPark;
    }


    @Override
    public String toString() {
        return "Parking{" +
                "id=" + id +
                ", parkName='" + parkName + '\'' +
                ", vacancy='" + vacancy + '\'' +
                ", priceCaps='" + priceCaps + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", rates='" + rates + '\'' +
                ", address='" + address + '\'' +
                ", distance='" + distance + '\'' +
                ", allPark='" + allPark + '\'' +
                '}';
    }
}
