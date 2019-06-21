package com.erict135.oosdteam4workshop8.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class BookingWPackageDetails implements Parcelable {
    private int bookingId;
    private Date bookingDate;
    private String bookingNo;
    private int travelerCount;
    private int customerId;
    private String tripTypeId;
    private int packageId;
    private String pkgName;
    private Date pkgStartDate;
    private Date pkgEndDate;
    private String pkgDesc;
    private double pkgBasePrice;
    private double pkgAgencyCommission;

    public static final Parcelable.Creator<BookingWPackageDetails> CREATOR = new Parcelable.Creator<BookingWPackageDetails>() {
        public BookingWPackageDetails createFromParcel(Parcel in) {
            return new BookingWPackageDetails(in);
        }

        public BookingWPackageDetails[] newArray(int size) {
            return new BookingWPackageDetails[size];
        }
    };

    private BookingWPackageDetails(Parcel in) {
        bookingId = in.readInt();
        bookingDate = new Date(in.readLong());
        bookingNo = in.readString();
        travelerCount = in.readInt();
        customerId = in.readInt();
        tripTypeId = in.readString();
        packageId = in.readInt();
        pkgName=in.readString();
        pkgStartDate=new Date(in.readLong());
        pkgEndDate=new Date(in.readLong());
        pkgDesc=in.readString();
        pkgBasePrice=in.readDouble();
        pkgAgencyCommission=in.readDouble();
    }

    public BookingWPackageDetails() {

    }

    public static Creator<BookingWPackageDetails> getCREATOR() {
        return CREATOR;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public Date getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Date bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getBookingNo() {
        return bookingNo;
    }

    public void setBookingNo(String bookingNo) {
        this.bookingNo = bookingNo;
    }

    public int getTravelerCount() {
        return travelerCount;
    }

    public void setTravelerCount(int travelerCount) {
        this.travelerCount = travelerCount;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getTripTypeId() {
        return tripTypeId;
    }

    public void setTripTypeId(String tripTypeId) {
        this.tripTypeId = tripTypeId;
    }

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bookingId);
        dest.writeLong(bookingDate.getTime());
        dest.writeString(bookingNo);
        dest.writeInt(travelerCount);
        dest.writeInt(customerId);
        dest.writeString(tripTypeId);
        dest.writeInt(packageId);
        dest.writeString(pkgName);
        dest.writeLong(pkgStartDate.getTime());
        dest.writeLong(pkgEndDate.getTime());
        dest.writeString(pkgDesc);
        dest.writeDouble(pkgBasePrice);
        dest.writeDouble(pkgAgencyCommission);
    }
}
