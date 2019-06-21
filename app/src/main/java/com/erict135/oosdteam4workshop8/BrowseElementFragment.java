package com.erict135.oosdteam4workshop8;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.erict135.oosdteam4workshop8.model.Customer;
import com.erict135.oosdteam4workshop8.model.Package;
import java.text.NumberFormat;

public class BrowseElementFragment extends Fragment {

    private TextView tvpkgName,tvpkgDesc,tvpkgDuration,tvpkgBasePrice;
    private Button btnBookNow;
    private ImageView ivPreview;

    private Package pkg;
    private int customerid;
    private static Customer customer;

    private static Context context;

    public BrowseElementFragment(){

    }

    public static BrowseElementFragment newInstance(Package pkg, int cid, Context con, Customer cust) {
        customer=cust;
        context=con;
        BrowseElementFragment fragment = new BrowseElementFragment();
        Bundle args = new Bundle();
        args.putParcelable("package", pkg);
        args.putInt("customerid",cid);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_browse_element, container, false);

        ivPreview=(ImageView)rootView.findViewById(R.id.ivPreview);
        tvpkgName=(TextView)rootView.findViewById(R.id.tvpkgName);
        tvpkgDesc=(TextView)rootView.findViewById(R.id.pkgDesc);
        tvpkgDuration=(TextView)rootView.findViewById(R.id.pkgDuration);
        tvpkgBasePrice=(TextView)rootView.findViewById(R.id.pkgBasePrice);
        btnBookNow=(Button)rootView.findViewById(R.id.btnBookNow);
        btnBookNow.setVisibility(MainActivity.isLoggedin()? View.VISIBLE: View.INVISIBLE);

        pkg = getArguments().getParcelable("package");
        customerid=getArguments().getInt("customerid");

        String[] pkgname = pkg.getPkgName().split(" ");
        switch(pkgname[0].toLowerCase()){
            case "polynesian":
                Glide.with(context).load(R.drawable.polynesia).fitCenter().into(ivPreview);
                break;
            case "asian":
                Glide.with(context).load(R.drawable.thailand).fitCenter().into(ivPreview);
                break;
            case "european":
                Glide.with(context).load(R.drawable.europe).fitCenter().into(ivPreview);
                break;
            case "caribbean":
                Glide.with(context).load(R.drawable.caribbean).fitCenter().into(ivPreview);
                break;
            default:
                Glide.with(context).load(R.drawable.tourism).fitCenter().into(ivPreview);
                break;
        }

        tvpkgName.setText(pkg.getPkgName());
        tvpkgDesc.setText(pkg.getPkgDesc());

        String[] startdateARRAY = pkg.getPkgStartDate().toString().split(" ");
        String startdate = startdateARRAY[0] + " " + startdateARRAY[1] + " " + startdateARRAY[2] + ", " + startdateARRAY[startdateARRAY.length-1];
        String[] enddateARRAY = pkg.getPkgEndDate().toString().split(" ");
        String enddate = enddateARRAY[0] + " " + enddateARRAY[1] + " " + enddateARRAY[2] + ", " + enddateARRAY[enddateARRAY.length-1];

        NumberFormat priceformat = NumberFormat.getCurrencyInstance();
        String price = priceformat.format(pkg.getPkgBasePrice());
        tvpkgDuration.setText(startdate + " - " + enddate);
        tvpkgBasePrice.setText(price);
        btnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.container,CreateBookingFragment.newInstance(pkg,customerid,context,customer))
                        .commit();
            }
        });

        return rootView;
    }
}