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
}
