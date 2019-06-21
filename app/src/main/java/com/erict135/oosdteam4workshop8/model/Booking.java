package com.erict135.oosdteam4workshop8.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Booking implements Parcelable {
    private int bookingId;
    private Date bookingDate;
    private String bookingNo;
    private int travelerCount;
    private int customerId;
    private String tripTypeId;
    private int packageId;

    public static final Parcelable.Creator<Booking> CREATOR = new Parcelable.Creator<Booking>() {
        public Booking createFromParcel(Parcel in) {
            return new Booking(in);
        }

        public Booking[] newArray(int size) {
            return new Booking[size];
        }
    };

    private Booking(Parcel in) {
        bookingId = in.readInt();
        bookingDate = new Date(in.readLong());
        bookingNo = in.readString();
        travelerCount = in.readInt();
        customerId = in.readInt();
        tripTypeId = in.readString();
        packageId = in.readInt();
    }

    public Booking() {

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
    }
}