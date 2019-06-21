package com.erict135.oosdteam4workshop8.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Package implements Parcelable {
    private int packageId;
    private String pkgName;
    private Date pkgStartDate;
    private Date pkgEndDate;
    private String pkgDesc;
    private double pkgBasePrice;
    private double pkgAgencyCommission;

    public int getPackageId() {
        return packageId;
    }

    public void setPackageId(int packageId) {
        this.packageId = packageId;
    }

    public String getPkgName() {
        return pkgName;
    }

    public void setPkgName(String pkgName) {
        this.pkgName = pkgName;
    }

    public Date getPkgStartDate() {
        return pkgStartDate;
    }

    public void setPkgStartDate(Date pkgStartDate) {
        this.pkgStartDate = pkgStartDate;
    }

    public Date getPkgEndDate() {
        return pkgEndDate;
    }

    public void setPkgEndDate(Date pkgEndDate) {
        this.pkgEndDate = pkgEndDate;
    }

    public String getPkgDesc() {
        return pkgDesc;
    }

    public void setPkgDesc(String pkgDesc) {
        this.pkgDesc = pkgDesc;
    }

    public double getPkgBasePrice() {
        return pkgBasePrice;
    }

    public void setPkgBasePrice(double pkgBasePrice) {
        this.pkgBasePrice = pkgBasePrice;
    }

    public double getPkgAgencyCommission() {
        return pkgAgencyCommission;
    }

    public void setPkgAgencyCommission(double pkgAgencyCommission) {
        this.pkgAgencyCommission = pkgAgencyCommission;
    }

    public static final Parcelable.Creator<Package> CREATOR = new Parcelable.Creator<Package>() {
        public Package createFromParcel(Parcel in) {
            return new Package(in);
        }

        public Package[] newArray(int size) {
            return new Package[size];
        }
    };

    private Package(Parcel in) {
        packageId=in.readInt();
        pkgName=in.readString();
        pkgStartDate=new Date(in.readLong());
        pkgEndDate=new Date(in.readLong());
        pkgDesc=in.readString();
        pkgBasePrice=in.readDouble();
        pkgAgencyCommission=in.readDouble();
    }

    public Package(){

    }
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(packageId);
        dest.writeString(pkgName);
        dest.writeLong(pkgStartDate.getTime());
        dest.writeLong(pkgEndDate.getTime());
        dest.writeString(pkgDesc);
        dest.writeDouble(pkgBasePrice);
        dest.writeDouble(pkgAgencyCommission);
    }
}