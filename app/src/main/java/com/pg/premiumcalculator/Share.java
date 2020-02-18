package com.pg.premiumcalculator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.core.content.FileProvider;

import java.io.File;

public class Share {

    String mailSubject = "Premium Quotation for your vehicle",
            mailText="Dear Sir/Madam,\n\n"+"As per our conversation, Please find the attachment of Quotation for vehicle. Kindly give us an appoinment for your further queries and kindly get back to us at the below mentioned contact.\n\n"+"Thanks,";
    void shareFile(File pdfFile, Context context,Activity activity)
    {
        Uri fileUri;
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        try {
            fileUri = FileProvider.getUriForFile(context, "com.pg.premiumcalculator.fileprovider", pdfFile);
            if(fileUri!=null)
            {
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                shareIntent.setType("application/pdf");
                shareIntent.putExtra(Intent.EXTRA_STREAM,fileUri);
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,mailSubject);
                shareIntent.putExtra(Intent.EXTRA_TEXT,mailText);
                activity.startActivity(Intent.createChooser(shareIntent,"Share File"));
            }
            else
            {
                shareIntent.setDataAndType(null,"");
                activity.setResult(Activity.RESULT_CANCELED,shareIntent);
            }
        }
        catch (IllegalArgumentException e)
        {
            Log.e("File Selector",
                    "The selected file can't be shared: " + pdfFile.toString());
        }
    }
    void shareApp(Context context,Activity activity)
    {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Hey.! Checkout this awesome app");
            String shareMessage= "\nHey.!\n" +
                    "This latest app enables you to calculate the insurance premium of every vehicle in real-time with updated rates and calculations along with your remuneration and the pdf format of quotation.\nDownload this app by clicking on the link below.\n";
            shareMessage = shareMessage + Constants.appLink;
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            activity.startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch(Exception e) {
            //e.toString();
        }
    }
}
