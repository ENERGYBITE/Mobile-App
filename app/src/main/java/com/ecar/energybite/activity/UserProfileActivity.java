package com.ecar.energybite.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.ecar.energybite.EVData;
import com.ecar.energybite.R;
import com.ecar.energybite.user.User;
import com.ecar.energybite.widget.CircularImageView;
import com.ecar.energybite.widget.CustomTextView;

/**
 */

public class UserProfileActivity extends BaseActivity {
    User user;

    private CircularImageView civUserImage;
    private CardView cvBasicInfo;
    private TextView tvUserName;
    private TextView tvUserEmail;

    private TextView tvUserContact;
    private CardView cvPersonalInfo;
    private CustomTextView ctvPrimaryAddress;

    private CustomTextView ctvSecondaryAddress;
    View contentView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        contentView = inflater.inflate(R.layout.activity_user_profile, null, false);
        civUserImage = contentView.findViewById(R.id.civUserImage);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            civUserImage.setTranslationZ(6);
            civUserImage.invalidate();
        }
        user = EVData.getUser();
        initializeViewElements();
        setAndDisplayElements();
        mFrameLayout.addView(contentView, 0);
    }


    public void initializeViewElements() {
        cvBasicInfo = (CardView) contentView.findViewById(R.id.cvBasicInfo);
        tvUserName = (TextView) cvBasicInfo.findViewById(R.id.tvUserName);
        tvUserEmail = (TextView) cvBasicInfo.findViewById(R.id.tvUserEmail);
        tvUserContact = (TextView) cvBasicInfo.findViewById(R.id.tvUserContact);

        cvPersonalInfo = (CardView) contentView.findViewById(R.id.cvPersonalInfo);
        ctvPrimaryAddress = (CustomTextView) cvPersonalInfo.findViewById(R.id.ctvPrimaryAddress);
        ctvSecondaryAddress = (CustomTextView) cvPersonalInfo.findViewById(R.id.ctvSecondaryAddress);
    }

    public void setAndDisplayElements() {
        tvUserName.setText(user.getName());
        tvUserEmail.setText(user.getUserEmail());
        tvUserContact.setText(user.getUsername());

        // Address
//        List<DeliveryInfo> contactDetails = userProfile.getPermanentContactDetails();
//        if (!CollectionUtility.isCollectionNullOrEmpty(contactDetails)) {
//            cvPersonalInfo.setVisibility(View.VISIBLE);
//            ctvPrimaryAddress.setText(contactDetails.get(0).getStreet() + contactDetails.get(0).getCity() + contactDetails.get(0).getState() + contactDetails.get(0).getCountry());
//            if (contactDetails.get(1) != null) {
//                ctvSecondaryAddress.setText(contactDetails.get(1).getStreet() + contactDetails.get(1).getCity() + contactDetails.get(1).getState() + contactDetails.get(1).getCountry());
//            }
//        } else {
//            cvPersonalInfo.setVisibility(View.GONE);
//        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(UserProfileActivity.this, MapActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }


}
