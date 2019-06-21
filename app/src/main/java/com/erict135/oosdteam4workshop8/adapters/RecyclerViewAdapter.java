package com.erict135.oosdteam4workshop8.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.myapps.a740657.travelexpertscustomer.R;
import com.myapps.a740657.travelexpertscustomer.model.BookingWPackageDetails;
import com.myapps.a740657.travelexpertscustomer.model.ViewHolder;

import java.text.NumberFormat;
import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private ArrayList<BookingWPackageDetails> bookingsWdetails = new ArrayList<>();
    private Context context;

    private ViewHolder viewHolder;
    private static int bgcolor = 0;
    public RecyclerViewAdapter(ArrayList<BookingWPackageDetails> b, Context c) {
        bookingsWdetails = b;
        context = c;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_booking_row, parent, false);
        ViewHolder holder = new ViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        viewHolder=holder;

        viewHolder.getTvbookingNo().setText(String.valueOf(bookingsWdetails.get(position).getBookingNo()));

        String[] bookdateARRAY = bookingsWdetails.get(position).getBookingDate().toString().split(" ");
        String bookdate = bookdateARRAY[0] + " " + bookdateARRAY[1] + " " + bookdateARRAY[2] + ", " + bookdateARRAY[bookdateARRAY.length-1];
        viewHolder.getTvbookingDate().setText(bookdate);

        viewHolder.getTvtravelerCount().setText(String.valueOf(bookingsWdetails.get(position).getTravelerCount()));

        switch (bookingsWdetails.get(position).getTripTypeId()) {
            case "B":
                viewHolder.getTvtriptypeId().setText("Business");
                break;
            case "G":
                viewHolder.getTvtriptypeId().setText("Group");
                break;
            case "L":
                viewHolder.getTvtriptypeId().setText("Leisure");
                break;
            default:
                viewHolder.getTvtriptypeId().setText("Leisure");
                break;
        }
        bgcolor++;
        switch (bgcolor%2){
            case 0:
                viewHolder.getRlBookingRow().setBackgroundResource(R.drawable.container_blue);
                break;
            default:
                viewHolder.getRlBookingRow().setBackgroundResource(R.drawable.container_gray);
                break;
        }

        viewHolder.getTvpkgName_DETAILS().setText(bookingsWdetails.get(position).getPkgName());
        viewHolder.getTvpkgDesc_DETAILS().setText(bookingsWdetails.get(position).getPkgDesc());

        String[] startdateARRAY = bookingsWdetails.get(position).getPkgStartDate().toString().split(" ");
        String startdate = startdateARRAY[0] + " " + startdateARRAY[1] + " " + startdateARRAY[2] + ", " + startdateARRAY[startdateARRAY.length-1];
        String[] enddateARRAY = bookingsWdetails.get(position).getPkgEndDate().toString().split(" ");
        String enddate = enddateARRAY[0] + " " + enddateARRAY[1] + " " + enddateARRAY[2] + ", " + enddateARRAY[enddateARRAY.length-1];
        viewHolder.getTvpkgDuration_DETAILS().setText(startdate + " - " + enddate);

        NumberFormat priceformat = NumberFormat.getCurrencyInstance();
        String price = priceformat.format(bookingsWdetails.get(position).getPkgBasePrice()*bookingsWdetails.get(position).getTravelerCount());
        viewHolder.getTvpkgBasePrice_DETAILS().setText(String.valueOf(price));
        //animate(holder);
    }

    @Override
    public int getItemCount() {
        return bookingsWdetails.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void insert(int position, BookingWPackageDetails data) {
        bookingsWdetails.add(position, data);
        notifyItemInserted(position);
    }

    public void remove(BookingWPackageDetails data) {
        int position = bookingsWdetails.indexOf(data);
        bookingsWdetails.remove(position);
        notifyItemRemoved(position);
    }
}