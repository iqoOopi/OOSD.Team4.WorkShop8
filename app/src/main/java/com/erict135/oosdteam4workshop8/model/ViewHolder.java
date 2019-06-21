package com.erict135.oosdteam4workshop8.model;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.erict135.oosdteam4workshop8.R;

//import com.myapps.a740657.travelexpertscustomer.R;

public class ViewHolder extends RecyclerView.ViewHolder {

    CardView cv;

    TextView tvbookingNo, tvbookingDate, tvtravelerCount, tvtriptypeId;
    TextView tvpkgName_DETAILS, tvpkgDesc_DETAILS, tvpkgDuration_DETAILS, tvpkgBasePrice_DETAILS;
    RelativeLayout rlBookingRow;

    public ViewHolder(View rootView) {
        super(rootView);

        cv = (CardView) itemView.findViewById(R.id.BookingElement);

        tvbookingNo = (TextView) rootView.findViewById(R.id.tvbookingNo);
        tvbookingDate = (TextView) rootView.findViewById(R.id.tvbookingDate);
        tvtravelerCount = (TextView) rootView.findViewById(R.id.tvtravelerCount);
        tvtriptypeId = (TextView) rootView.findViewById(R.id.tvtripTypeId);

        tvpkgName_DETAILS = (TextView) rootView.findViewById(R.id.tvpkgName_DETAILS);
        tvpkgDesc_DETAILS = (TextView) rootView.findViewById(R.id.tvpkgDesc_DETAILS);
        tvpkgDuration_DETAILS = (TextView) rootView.findViewById(R.id.tvpkgDuration_DETAILS);
        tvpkgBasePrice_DETAILS = (TextView) rootView.findViewById(R.id.tvpkgBasePrice_DETAILS);

        rlBookingRow=(RelativeLayout)rootView.findViewById(R.id.rlBookingRow);
    }

    public RelativeLayout getRlBookingRow() {
        return rlBookingRow;
    }

    public void setRlBookingRow(RelativeLayout rlBookingRow) {
        this.rlBookingRow = rlBookingRow;
    }

    public CardView getCv() {
        return cv;
    }

    public void setCv(CardView cv) {
        this.cv = cv;
    }

    public TextView getTvbookingNo() {
        return tvbookingNo;
    }

    public void setTvbookingNo(TextView tvbookingNo) {
        this.tvbookingNo = tvbookingNo;
    }

    public TextView getTvbookingDate() {
        return tvbookingDate;
    }

    public void setTvbookingDate(TextView tvbookingDate) {
        this.tvbookingDate = tvbookingDate;
    }

    public TextView getTvtravelerCount() {
        return tvtravelerCount;
    }

    public void setTvtravelerCount(TextView tvtravelerCount) {
        this.tvtravelerCount = tvtravelerCount;
    }

    public TextView getTvtriptypeId() {
        return tvtriptypeId;
    }

    public void setTvtriptypeId(TextView tvtriptypeId) {
        this.tvtriptypeId = tvtriptypeId;
    }

    public TextView getTvpkgName_DETAILS() {
        return tvpkgName_DETAILS;
    }

    public void setTvpkgName_DETAILS(TextView tvpkgName_DETAILS) {
        this.tvpkgName_DETAILS = tvpkgName_DETAILS;
    }

    public TextView getTvpkgDesc_DETAILS() {
        return tvpkgDesc_DETAILS;
    }

    public void setTvpkgDesc_DETAILS(TextView tvpkgDesc_DETAILS) {
        this.tvpkgDesc_DETAILS = tvpkgDesc_DETAILS;
    }

    public TextView getTvpkgDuration_DETAILS() {
        return tvpkgDuration_DETAILS;
    }

    public void setTvpkgDuration_DETAILS(TextView tvpkgDuration_DETAILS) {
        this.tvpkgDuration_DETAILS = tvpkgDuration_DETAILS;
    }

    public TextView getTvpkgBasePrice_DETAILS() {
        return tvpkgBasePrice_DETAILS;
    }

    public void setTvpkgBasePrice_DETAILS(TextView tvpkgBasePrice_DETAILS) {
        this.tvpkgBasePrice_DETAILS = tvpkgBasePrice_DETAILS;
    }
}