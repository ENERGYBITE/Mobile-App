package com.ecar.energybite.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Toast;

import com.ecar.energybite.BookingResponse;
import com.ecar.energybite.R;
import com.ecar.energybite.booking.BookingModel;
import com.ecar.energybite.http.APIClient;
import com.ecar.energybite.http.EBWebservice;
import com.ecar.energybite.util.StringUtility;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

import androidx.core.app.ActivityCompat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScannedBarcodeActivity extends BaseActivity {
    private static final String TAG = ScannedBarcodeActivity.class.getName();
    EBWebservice apiInterface;
    SurfaceView surfaceView;
    private BarcodeDetector barcodeDetector;
    private CameraSource cameraSource;
    private static final int REQUEST_CAMERA_PERMISSION = 201;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View contentView = inflater.inflate(R.layout.activity_scanned_barcode, null, false);
        initViews(contentView);
        mFrameLayout.addView(contentView, 0);
        apiInterface = APIClient.getClient().create(EBWebservice.class);
    }


    private void initViews(View contentView) {
        surfaceView = (SurfaceView) contentView.findViewById(R.id.surfaceView);
    }

    private void initialiseDetectorsAndSources() {

        Toast.makeText(getApplicationContext(), "Barcode scanner started", Toast.LENGTH_SHORT).show();
        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.ALL_FORMATS)
                .build();

        cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setRequestedPreviewSize(1920, 1080)
                .setAutoFocusEnabled(true) //you should add this feature
                .build();

        surfaceView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                try {
                    if (ActivityCompat.checkSelfPermission(ScannedBarcodeActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        cameraSource.start(surfaceView.getHolder());
                    } else {
                        ActivityCompat.requestPermissions(ScannedBarcodeActivity.this, new
                                String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });


        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {
//                Toast.makeText(getApplicationContext(), "To prevent memory leaks barcode scanner has been stopped", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {
                    String code = barcodes.valueAt(0).displayValue;
                    String[] codes = code.split("_");

                    if (codes != null && codes.length == 2 && StringUtility.trimAndEmptyIsNull(codes[0]) != null && StringUtility.trimAndEmptyIsNull(codes[1]) != null) {
                        bookCharger(codes[0], codes[1]);
                    }

                    Toast.makeText(getApplicationContext(), barcodes.valueAt(0).displayValue, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    protected void onPause() {
        super.onPause();
        cameraSource.release();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initialiseDetectorsAndSources();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ScannedBarcodeActivity.this, MapActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }

    private void bookCharger(final String chargerName, String chargingPoint) {
        final BookingResponse bookingResponse = new BookingResponse(chargerName, chargingPoint);
        Call<BookingResponse> call1 = apiInterface.book(bookingResponse);
        call1.enqueue(new Callback<BookingResponse>() {
            @Override
            public void onResponse(Call<BookingResponse> call, Response<BookingResponse> response) {
                BookingResponse bookingResponse = response.body();
                if (bookingResponse != null) {
                    String responseCode = bookingResponse.getResponseCode();
                    if (responseCode == null || responseCode.equals("404") || responseCode.equalsIgnoreCase("false")) {
                        Toast.makeText(ScannedBarcodeActivity.this, "Not able to connect the server. ", Toast.LENGTH_SHORT).show();
                    } else {
                        Intent bookingStatusIntent = new Intent(ScannedBarcodeActivity.this, BookingStatusActivity.class);
                        bookingStatusIntent.putExtra(BaseActivity.BOOKING_RESPONSE, (BookingModel) bookingResponse.getBookingModel());
                        startActivity(bookingStatusIntent);
                        ScannedBarcodeActivity.this.finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<BookingResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Not able to connect the server. ", Toast.LENGTH_SHORT).show();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e(TAG, t.getMessage());
                call.cancel();
            }
        });
    }
}
