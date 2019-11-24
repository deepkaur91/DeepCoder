package test.app.barcodereader;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.widget.Toast;

import com.google.android.gms.vision.barcode.Barcode;

import java.util.List;

import test.app.barcode_assignement.BarcodeReader;

public class MainActivity extends AppCompatActivity implements BarcodeReader.BarcodeReaderListener {
   private BarcodeReader barcodeReader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // getting barcode instance
        barcodeReader = (BarcodeReader) getSupportFragmentManager().findFragmentById(R.id.barcode_fragment);


        /***
         * Providing beep sound. The sound file has to be placed in
         * `assets` folder
         */
        // barcodeReader.setBeepSoundFile("shutter.mp3");

        /**
         * Pausing / resuming barcode reader. This will be useful when you want to
         * do some foreground user interaction while leaving the barcode
         * reader in background
         * */
        // barcodeReader.pauseScanning();
        // barcodeReader.resumeScanning();
    }

    @Override
    public void onScanned(final Barcode barcode) {
            try {
                Log.e("Response--------", "onScanned: " + barcode.displayValue);
                barcodeReader.playBeep();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                     AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                        setTitle("Scanning Result");
                        builder1.setMessage("BarCode is: " + barcode.displayValue);
                        builder1.setCancelable(true);

                        builder1.setPositiveButton(
                                "Ok",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                        AlertDialog alert11 = builder1.create();
                        alert11.show();
                    }
                });
            }catch (Exception e){
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

            }
    }

    @Override
    public void onScannedMultiple(List<Barcode> barcodes) {

        try {
            Log.e("Response---------", "onScannedMultiple: " + barcodes.size());

            String codes = "";
            for (Barcode barcode : barcodes) {
                codes += barcode.displayValue + ", ";
            }

            final String finalCodes = codes;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

        AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                    setTitle("Scanning Result");
                    builder1.setMessage(finalCodes);
                    builder1.setCancelable(true);

                    builder1.setPositiveButton(
                            "Ok",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });

                    AlertDialog alert11 = builder1.create();
                    alert11.show();


                }
            });
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void onBitmapScanned(SparseArray<Barcode> sparseArray) {

    }

    @Override
    public void onScanError(String errorMessage) {

         try {
             Log.e("Error-------", errorMessage);

             AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
             setTitle("Scanning Result");
             builder1.setMessage("Barcode is not readable");
             builder1.setCancelable(true);

             builder1.setPositiveButton(
                     "Ok",
                     new DialogInterface.OnClickListener() {
                         public void onClick(DialogInterface dialog, int id) {
                             dialog.cancel();
                         }
                     });

             AlertDialog alert11 = builder1.create();
             alert11.show();
         }catch (Exception e){
             Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();

         }
         }

    @Override
    public void onCameraPermissionDenied() {
        Toast.makeText(getApplicationContext(), "Camera permission denied!", Toast.LENGTH_LONG).show();
        finish();
    }
}