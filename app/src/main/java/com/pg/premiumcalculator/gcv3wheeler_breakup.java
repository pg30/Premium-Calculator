package com.pg.premiumcalculator;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.LineSeparator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;

public class gcv3wheeler_breakup extends menu {

    //for premium calculation
    double idv,discount,elec,nonelec,ncb,zerodep,patodriver,lltodriver,extcngkit;
    String dateofregistration;
    Zone zone;
    Carrier carrier;
    Rate mrate = new Rate();
    Vehicle currVehicle = Vehicle.GOODSVEHICLE3WHEELER;
    double finalPremium,odPremium,tpPremium,gst,rate,basicOD,basicTP;
    boolean yes,imt_yes,cng_yes;
    DecimalFormat df = new DecimalFormat("0.00");

    //views values
    double tempidv,tempbasicOD,tempelec,tempnonelec,tempimt23=0,tempoddisc,tempncb,tempzerodep;
    double tempbasicTP,temptppd=150,tempownerpa,templltodriver,tempextcngkit,tempcngtp=0;
    double temptotala,temptotalb,temptotalab,tempgst,tempfinalpremium;

    //result views pointers
    TextView idvview,dateview,zoneview,rateview,basicodview,elecview,nonelecview,oddiscview,ncbview,zerodepview,totalaview,basictpview,tppdview,ownerpaview,lltodriverview,totalbview,totalabview,gstview,finalview,imt23view,carrierview,cngview,extcngkitview,cngtpview;
    //text views pointers
    TextView nonelecdisplay,oddiscdisplay,ncbdisplay,zerodepdisplay;
    Button share,knowyourcommission;

    //
    Spinner company_spinner;
    //
    String company_name="";
    //
    final private int REQUEST_CODE_ASK_PERMISSIONS=111;
    private File pdfFile;
    File docsFolder;
    String pdfname;
//    EditText file_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gcv3wheeler_breakup);

        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#32afa9")));
        getSupportActionBar().setTitle("Premium Breakup");

        getValuesFromIntent();
        findViews();
        calculatePremium();
        setDisplayData();
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCompanyBuilder();
            }
        });
        knowyourcommission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setValuesInIntent();
            }
        });
    }

    void setValuesInIntent()
    {
        Intent intent = new Intent(getBaseContext(), commission.class);
        Bundle b = new Bundle();
        Log.d("debug",temptotala+"......"+temptotalb);
        b.putDouble("tppremium",temptotalb);
        b.putDouble("odpremium",temptotala);
        intent.putExtra("commission_bundle",b);
        startActivity(intent);
    }

    void findViews()
    {
        idvview = (TextView) findViewById(R.id.idv_value);
        dateview = (TextView) findViewById(R.id.dateofregistration_value);
        zoneview = (TextView) findViewById(R.id.zone_value);
        rateview = (TextView) findViewById(R.id.rate_value);
        basicodview = (TextView) findViewById(R.id.basicod_value);
        elecview = (TextView) findViewById(R.id.elec_value);
        nonelecview = (TextView) findViewById(R.id.nonelec_value);
        oddiscview = (TextView) findViewById(R.id.disc_value);
        ncbview = (TextView) findViewById(R.id.ncb_value);
        zerodepview = (TextView) findViewById(R.id.zerodep_value);
        totalaview = (TextView) findViewById(R.id.totala_value);
        basictpview = (TextView) findViewById(R.id.basictp_value);
        tppdview = (TextView) findViewById(R.id.tppd_value);
        ownerpaview = (TextView) findViewById(R.id.ownerpa_value);
        lltodriverview = (TextView) findViewById(R.id.llpaid_value);
        totalbview = (TextView) findViewById(R.id.totalb_value);
        totalabview = (TextView) findViewById(R.id.totalab_value);
        gstview = (TextView) findViewById(R.id.gst_value);
        finalview = (TextView) findViewById(R.id.finalpremium_value);
        imt23view = (TextView) findViewById(R.id.imt23_value);
        carrierview = (TextView) findViewById(R.id.carrier_value);
        cngview = (TextView) findViewById(R.id.cng_value);
        extcngkitview = (TextView) findViewById(R.id.cngkit_value);
        cngtpview = (TextView) findViewById(R.id.cngtp_value);

        nonelecdisplay=(TextView) findViewById(R.id.nonelecdisplay);
        oddiscdisplay=(TextView) findViewById(R.id.oddiscdisplay);
        ncbdisplay=(TextView) findViewById(R.id.ncbdisplay);
        zerodepdisplay=(TextView) findViewById(R.id.zerodepdisplay);

        share = (Button) findViewById(R.id.share_button);
        knowyourcommission = (Button) findViewById(R.id.commission_button);
    }

    void getValuesFromIntent()
    {
        Intent intent = getIntent();
        Bundle b = intent.getBundleExtra("gcv3wheeler_breakup_bundle");
        idv = b.getDouble("idv");
        discount = b.getDouble("discount");
        elec = b.getDouble("elec");
        nonelec = b.getDouble("nonelec");
        ncb = b.getDouble("ncb");
        zerodep = b.getDouble("zerodep");
        patodriver = b.getDouble("patodriver");
        lltodriver = b.getDouble("lltodriver");
        dateofregistration = b.getString("dateofregistration");
        extcngkit = b.getDouble("extcngkit");
        yes = b.getBoolean("restrict_tppd");
        imt_yes = b.getBoolean("imt23");
        cng_yes = b.getBoolean("cng");
        zone = (Zone) b.getSerializable("zone");
        carrier = (Carrier) b.getSerializable("carrier");
    }

    void setDisplayData()
    {
        idvview.setText(""+df.format(tempidv));
        Log.d("debug",""+tempidv);
        dateview.setText(""+dateofregistration);
        Log.d("debug",""+dateofregistration);
        zoneview.setText(""+zone);
        Log.d("debug",""+zone);
        rateview.setText(""+(idv>0?rate:0));
        Log.d("debug",""+rate);
        cngview.setText(cng_yes?"YES":"NO");
        Log.d("debug",""+cng_yes);
        carrierview.setText(""+carrier);
        Log.d("debug",""+carrier);
        basicodview.setText(""+df.format(tempbasicOD));
        Log.d("debug",""+tempbasicOD);
        elecview.setText(""+df.format(tempelec));
        Log.d("debug",""+tempelec);
        nonelecview.setText(""+df.format(tempnonelec));
        Log.d("debug",""+tempnonelec);
        imt23view.setText(""+df.format(tempimt23));
        oddiscview.setText(""+df.format(tempoddisc));
        Log.d("debug",""+tempoddisc);
        oddiscdisplay.setText("OD Discount(-"+discount+"%):");
        ncbview.setText(""+df.format(tempncb));
        ncbdisplay.setText("NCB(-"+ncb+"%):");
        Log.d("debug",""+tempncb);
        zerodepview.setText(""+df.format(tempzerodep));
        zerodepdisplay.setText("Zero Dep Premium(+"+zerodep+"%):");
        Log.d("debug",""+tempzerodep);
        totalaview.setText(""+df.format(temptotala));
        Log.d("debug",""+df.format(temptotala));
        basictpview.setText(""+tempbasicTP);
        Log.d("debug",""+df.format(tempbasicTP));
        tppdview.setText(""+df.format(temptppd));
        Log.d("debug",""+temptppd);
        ownerpaview.setText(""+df.format(tempownerpa));
        Log.d("debug",""+tempownerpa);
        lltodriverview.setText(""+df.format(templltodriver));
        Log.d("debug",""+templltodriver);
        totalbview.setText(""+df.format(temptotalb));
        Log.d("debug",""+temptotalb);
        totalabview.setText(""+df.format(temptotalab));
        Log.d("debug",""+temptotalab);
        gstview.setText(""+df.format(tempgst));
        Log.d("debug",""+df.format(tempgst));
        finalview.setText(""+df.format(tempfinalpremium));
        Log.d("debug",""+tempfinalpremium);
        extcngkitview.setText(""+df.format(tempextcngkit));
        Log.d("debug",""+tempextcngkit);
        cngtpview.setText(""+tempcngtp);
        Log.d("debug",""+tempcngtp);
    }

    void calculatePremium()
    {
        rate = mrate.getRate(zone,currVehicle,dateofregistration,carrier);
        basicTP = mrate.getTP(currVehicle,carrier);
        tempbasicTP = basicTP;

        odPremium = calculateOD();
        temptotala = odPremium;

        tpPremium = calculateTP();
        temptotalb = tpPremium+basicTP;

        finalPremium = odPremium+tpPremium;
        temptotalab = finalPremium+basicTP;

        gst = (0.12*basicTP) + 0.18*(finalPremium);
        tempgst = gst;

        finalPremium+=basicTP+gst;
        tempfinalpremium = finalPremium;
    }

    Double calculateOD()
    {
        basicOD = idv*(rate)/100;
        tempidv = idv;
        if(idv==0)
            return 0.0;

        if(cng_yes)
        {
            //inbuilt
            if(extcngkit==0)
            {
                basicOD+=0.05*basicOD;
            }
        }
        tempbasicOD = basicOD;

        double zerodepprem = (zerodep/100)*idv;

        elec = elec*0.4;
        nonelec = (nonelec*rate)/100;
        tempelec = elec;
        tempnonelec = nonelec;

        basicOD+=elec+nonelec;

        if(imt_yes)
        {
            tempimt23 = 0.15*basicOD;
            basicOD += 0.15*basicOD;
        }

        double ncbdisc = basicOD*(ncb/100);
        tempncb = ncbdisc;
        basicOD-=ncbdisc;

        double oddisc = basicOD*(discount/100);
        tempoddisc = oddisc;
        basicOD-=oddisc;

        basicOD+=zerodepprem;

        if(cng_yes)
        {
            //external
            if(extcngkit>0)
            {
                basicOD+=0.04*extcngkit;
                tempextcngkit = 0.04*extcngkit;
            }
        }

        return basicOD;
    }

    Double calculateTP()
    {
        double temp = patodriver+lltodriver;
        tempownerpa = patodriver;
        templltodriver = lltodriver;

        if(yes)
        {
            temp-=temptppd;
            temptppd = 0;
        }
        if(cng_yes)
        {
            temp+=60;
            tempcngtp = 60;
        }
        return temp;
    }

    void createCompanyBuilder()
    {
        AlertDialog.Builder company_builder = new AlertDialog.Builder(gcv3wheeler_breakup.this);
        View company_view = getLayoutInflater().inflate(R.layout.company_spinner_dialog,null);
        company_builder.setTitle("Choose Insurer");
        company_spinner = (Spinner) company_view.findViewById(R.id.company_spinner);
        ArrayAdapter<CharSequence> company_adapter =  ArrayAdapter.createFromResource(gcv3wheeler_breakup.this,R.array.company_list,R.layout.support_simple_spinner_dropdown_item);
        company_spinner.setAdapter(company_adapter);
        company_builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!company_spinner.getSelectedItem().toString().equalsIgnoreCase("Choose an insurer…")) {
                    company_name = company_spinner.getSelectedItem().toString();
                    Log.d("debug","inside"+company_name);
                    dialog.dismiss();
//                    createFileBuilder();
                    int fileid = new PrefManager(getApplicationContext()).getGcv3wheelerId();
                    pdfname = "GCV3WHEELER_"+fileid+".pdf";
                    try {
                        createPdfWrapper();
                        fileid++;
                        new PrefManager(getApplicationContext()).setGcv3WheelerId(fileid);
                        shareFile();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (DocumentException e) {
                        e.printStackTrace();
                    } catch (NullPointerException e)
                    {
                        e.printStackTrace();
                    }
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"You have not chosen company name",Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(),"You have not chosen company name",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });

        company_builder.setView(company_view);
        AlertDialog dialog = company_builder.create();
        dialog.show();
    }

    void shareFile()
    {
        Uri fileUri;
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        try {
            fileUri = FileProvider.getUriForFile(this, "com.pg.premiumcalculator.fileprovider", pdfFile);
            if(fileUri!=null)
            {
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                shareIntent.setDataAndType(fileUri,getContentResolver().getType(fileUri));
//                this.setResult(Activity.RESULT_OK,shareIntent);
                shareIntent.setType("application/pdf");
                shareIntent.putExtra(Intent.EXTRA_STREAM,fileUri);
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,"Premium Quotation for your vehicle");
                String mail_text = "Dear Sir/Madam,\n\n"+"As per our conversation, Please find the attachment of Quotation for vehicle. Kindly give us an appoinment for your further queries and kindly get back to us at the below mentioned contact.\n\n"+"Thanks,";
                shareIntent.putExtra(Intent.EXTRA_TEXT,mail_text);
                startActivity(Intent.createChooser(shareIntent,"Share File"));
            }
            else
            {
                shareIntent.setDataAndType(null,"");
                this.setResult(Activity.RESULT_CANCELED,shareIntent);
            }
        }
        catch (IllegalArgumentException e)
        {
            Log.e("File Selector",
                    "The selected file can't be shared: " + pdfFile.toString());
        }
    }

//    void createFileBuilder()
//    {
//        AlertDialog.Builder filename_builder = new AlertDialog.Builder(taxilessthan6_breakup.this);
//        View filename_view = getLayoutInflater().inflate(R.layout.filename_dialog,null);
//        filename_builder.setTitle("Enter file name");
//        file_edit = (EditText) filename_view.findViewById(R.id.filename);
//        filename_builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                pdfname = ParseString(file_edit.getText().toString());
//                dialog.dismiss();
//                try {
//                    createPdfWrapper();
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (DocumentException e) {
//                    e.printStackTrace();
//                }
//                Intent shareFile = new Intent(Intent.ACTION_SEND);
//                if(pdfFile.exists())
//                {
//                    shareFile.setType("application/pdf");
//                    shareFile.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://"+pdfFile.getAbsolutePath()));
//                    startActivity(Intent.createChooser(shareFile,"Share File"));
//                }
//            }
//        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//        filename_builder.setView(filename_view);
//        AlertDialog file_dialog = filename_builder.create();
//        file_dialog.show();
//    }

    private void createPdfWrapper() throws FileNotFoundException, DocumentException {

        int hasWriteStoragePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!shouldShowRequestPermissionRationale(Manifest.permission.WRITE_CONTACTS)) {
                    showMessageOKCancel("You need to allow access to Storage",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                                REQUEST_CODE_ASK_PERMISSIONS);
                                    }
                                }
                            });
                    return;
                }
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        REQUEST_CODE_ASK_PERMISSIONS);
            }
            return;
        } else {
            createPdf();
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    private void createPdf() throws FileNotFoundException, DocumentException
    {
        Document document = new Document(PageSize.A4);
        try {
            docsFolder = new File(Environment.getExternalStorageDirectory() + "/Premium Quotations");
            if (!docsFolder.exists()) {
                docsFolder.mkdir();
                Log.d("debug", "Created a new directory for PDF");
            }
            pdfFile = new File(docsFolder.getAbsolutePath(), pdfname);
            OutputStream output = new FileOutputStream(pdfFile);
            PdfWriter.getInstance(document, output);
            document.open();

            try {
                LineSeparator lineSeparator = new LineSeparator();
                lineSeparator.setLineColor(new BaseColor(0, 0, 0, 68));
                Font h1_font = new Font(Font.FontFamily.TIMES_ROMAN, 23.0f, Font.UNDERLINE, BaseColor.BLACK);
                Font h2_font = new Font(Font.FontFamily.TIMES_ROMAN, 20.0f, Font.NORMAL, BaseColor.BLACK);
                Font bold_font = new Font(Font.FontFamily.TIMES_ROMAN, 13.0f, Font.BOLD, BaseColor.BLACK);
                Font normal_font = new Font(Font.FontFamily.TIMES_ROMAN, 13.0f, Font.NORMAL, BaseColor.BLACK);
                document.add(new Chunk(lineSeparator));

                //add heading
                Chunk heading = new Chunk(company_name, h1_font);
                Paragraph heading_para = new Paragraph(heading);
                heading_para.setAlignment(Element.ALIGN_CENTER);
                document.add(heading_para);

                document.add(new Paragraph("\n"));

                Chunk qheading = new Chunk("QUOTATION", h2_font);
                Paragraph qheading_para = new Paragraph(qheading);
                qheading_para.setAlignment(Element.ALIGN_CENTER);
                document.add(qheading_para);
                document.add(new Paragraph("\n"));

                //basic details table
                PdfPTable basictable = new PdfPTable(4);
                basictable.setHorizontalAlignment(Element.ALIGN_CENTER);
                basictable.setTotalWidth(PageSize.A4.getWidth());
                basictable.setWidthPercentage(100);
                float[] relativeWidths = new float[]{12f, 5f, 12f, 5f};
                basictable.setWidths(relativeWidths);
                addCell("IDV", basictable, bold_font);
                addCell(idvview.getText().toString(), basictable, normal_font);
                addCell("Zone", basictable, bold_font);
                addCell(zoneview.getText().toString(), basictable, normal_font);

                addCell("Date of Registration", basictable, bold_font);
                addCell(dateview.getText().toString(), basictable, normal_font);
                addCell("Carrier",basictable,bold_font);
                addCell(carrierview.getText().toString(),basictable,normal_font);

                addCell("CNG/LPG", basictable, bold_font);
                addCell(cngview.getText().toString(), basictable, normal_font);
                addCell(" ",basictable,bold_font);
                addCell(" ",basictable,normal_font);

                basictable.setSpacingAfter(2f);
                document.add(basictable);
                document.add(new Paragraph("\n"));

                //premium table
                PdfPTable premiumtable = new PdfPTable(4);
                premiumtable.setHorizontalAlignment(Element.ALIGN_CENTER);
                premiumtable.setTotalWidth(PageSize.A4.getWidth());
                premiumtable.setWidthPercentage(100);
                premiumtable.setWidths(relativeWidths);
                addCell("OD Premium(A)", premiumtable, bold_font);
                addCell("Rupees", premiumtable, bold_font);
                addCell("Liability Premium(B)", premiumtable, bold_font);
                addCell("Rupees", premiumtable, bold_font);

                addCell("Basic Rate", premiumtable, normal_font);
                addCell(rateview.getText().toString(), premiumtable, normal_font);
                addCell("Basic TP Premium", premiumtable, normal_font);
                addCell(basictpview.getText().toString(), premiumtable, normal_font);

                addCell("Basic OD Premium", premiumtable, normal_font);
                addCell(basicodview.getText().toString(), premiumtable, normal_font);
                addCell("Third Party Property Damage(TPPD)", premiumtable, normal_font);
                addCell(tppdview.getText().toString(), premiumtable, normal_font);

                addCell("CNG/LPG Kit", premiumtable, normal_font);
                addCell(extcngkitview.getText().toString(), premiumtable, normal_font);
                addCell("PA to Owner Driver", premiumtable, normal_font);
                addCell(ownerpaview.getText().toString(), premiumtable, normal_font);

                addCell("Electrical Accessories (+4%)", premiumtable, normal_font);
                addCell(elecview.getText().toString(), premiumtable, normal_font);
                addCell("LL to Paid Driver", premiumtable, normal_font);
                addCell(lltodriverview.getText().toString(), premiumtable, normal_font);

                addCell("Non Electrical Accessories Value", premiumtable, normal_font);
                addCell(nonelecview.getText().toString(), premiumtable, normal_font);
                addCell("CNG/LPG-TP", premiumtable, normal_font);
                addCell(cngtpview.getText().toString(), premiumtable, normal_font);

                addCell("IMT 23(+15%)", premiumtable, normal_font);
                addCell(imt23view.getText().toString(), premiumtable, normal_font);
                addCell("", premiumtable, normal_font);
                addCell("", premiumtable, normal_font);

                addCell("OD Discount(-" + discount + "%)", premiumtable, normal_font);
                addCell(oddiscview.getText().toString(), premiumtable, normal_font);
                addCell("", premiumtable, normal_font);
                addCell("", premiumtable, normal_font);

                addCell("NCB(-" + ncb + "%)", premiumtable, normal_font);
                addCell(ncbview.getText().toString(), premiumtable, normal_font);
                addCell("", premiumtable, normal_font);
                addCell("", premiumtable, normal_font);

                addCell("Zero Dep Premium(+" + zerodep + "%)", premiumtable, normal_font);
                addCell(zerodepview.getText().toString(), premiumtable, normal_font);
                addCell("", premiumtable, normal_font);
                addCell("", premiumtable, normal_font);

                addCell("Total OD Premium(A)", premiumtable, normal_font);
                addCell(totalaview.getText().toString(), premiumtable, normal_font);
                addCell("Total TP Premium(B)", premiumtable, normal_font);
                addCell(totalbview.getText().toString(), premiumtable, normal_font);

                addCellWithColSpan("  ", premiumtable, normal_font, 4);

                addCellWithColSpan("Total Premium(A+B) without tax", premiumtable, bold_font, 2);
                addCellWithColSpan(totalabview.getText().toString(), premiumtable, bold_font, 2);

                addCellWithColSpan("GST(@18%+12%)", premiumtable, bold_font, 2);
                addCellWithColSpan(gstview.getText().toString(), premiumtable, bold_font, 2);

                addCellWithColSpan("Final Premium", premiumtable, bold_font, 2);
                addCellWithColSpan(finalview.getText().toString(), premiumtable, bold_font, 2);
                premiumtable.setSpacingAfter(2f);
                document.add(premiumtable);
                document.add(new Paragraph("\n"));

                Paragraph p1 = new Paragraph("Kindly pay Cheque/DD in favor of "+company_name, bold_font);
                document.add(p1);
                document.add(new Paragraph("\n"));
                Paragraph p2 = new Paragraph("Documents Required:", normal_font);
                document.add(p2);
                document.add(new Paragraph("\n"));

                //doc table
                PdfPTable doctable = new PdfPTable(2);
                doctable.setHorizontalAlignment(Element.ALIGN_CENTER);
                doctable.setTotalWidth(PageSize.A4.getWidth());
                doctable.setWidthPercentage(100);
                float[] docwidth = new float[]{1f, 10f};
                doctable.setWidths(docwidth);
                addCell("1.", doctable, bold_font);
                addCell("Previous Policy copy", doctable, normal_font);
                addCell("2.", doctable, bold_font);
                addCell("RC Copy", doctable, normal_font);
                doctable.setSpacingAfter(2f);
                document.add(doctable);

                Paragraph p3 = new Paragraph("Note: In case of any claim, NCB will be revised and hence Quotation is subject to change.", bold_font);
                document.add(p3);
            }
            catch (DocumentException de)
            {
                Log.e("createPdf()","Document Exception :"+de);
            }
            finally {
                document.close();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    void addCell(String str, PdfPTable table, Font f)
    {
        Phrase para = new Phrase(str,f);
        PdfPCell cell = new PdfPCell();
        cell.addElement(para);
        cell.setPaddingRight(2f);
        table.addCell(cell);
    }

    void addCellWithColSpan(String str, PdfPTable table, Font f,int span)
    {
        Phrase para = new Phrase(str,f);
        PdfPCell cell = new PdfPCell();
        cell.addElement(para);
        cell.setColspan(span);
        cell.setPaddingRight(2f);
        table.addCell(cell);
    }
}
