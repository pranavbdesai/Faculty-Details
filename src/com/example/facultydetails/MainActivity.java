package com.example.facultydetails;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity implements OnClickListener
{
	EditText FacultyID,FacultyName,ContactNo;
	Button btnAdd,btnDelete,btnView,btnViewAll,btnShowInfo,btnModify;
	SQLiteDatabase db;
    
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FacultyID=(EditText)findViewById(R.id.Facultyid);
        FacultyName=(EditText)findViewById(R.id.Facultyname);
        ContactNo=(EditText)findViewById(R.id.Contactno);
        btnAdd=(Button)findViewById(R.id.btnAdd);
        btnDelete=(Button)findViewById(R.id.btnDelete);
        btnViewAll=(Button)findViewById(R.id.btnViewAll);
        btnShowInfo=(Button)findViewById(R.id.btnShowInfo);
        btnAdd.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        btnViewAll.setOnClickListener(this);
        btnShowInfo.setOnClickListener(this);
        db=openOrCreateDatabase("FacultyDB", Context.MODE_PRIVATE, null);
		db.execSQL("CREATE TABLE IF NOT EXISTS faculty(FacultyID VARCHAR,FacultyName VARCHAR,Contact VARCHAR);");
    }
    public void onClick(View view)
    {
    	if(view==btnAdd)
    	{
    		if(FacultyID.getText().toString().trim().length()==0||
    		   FacultyName.getText().toString().trim().length()==0||
    		   ContactNo.getText().toString().trim().length()==0)
    		{
    			showMessage("Error", "Please enter all values");
    			return;
    		}
    		db.execSQL("INSERT INTO faculty VALUES('"+FacultyID.getText()+"','"+FacultyName.getText()+
    				   "','"+ContactNo.getText()+"');");
    		showMessage("Success", "Record added");
    		clearText();
    	}
    	if(view==btnDelete)
    	{
    		if(FacultyID.getText().toString().trim().length()==0)
    		{
    			showMessage("Error", "Please enter ID");
    			return;
    		}
    		Cursor c=db.rawQuery("SELECT * FROM faculty WHERE FacultyID='"+FacultyID.getText()+"'", null);
    		if(c.moveToFirst())
    		{
    			db.execSQL("DELETE FROM faculty WHERE FacultyID='"+FacultyID.getText()+"'");
    			showMessage("Success", "Record Deleted");
    		}
    		else
    		{
    			showMessage("Error", "Invalid ID");
    		}
    		clearText();
    	}
    	if(view==btnViewAll)
    	{
    		Cursor c=db.rawQuery("SELECT * FROM faculty", null);
    		if(c.getCount()==0)
    		{
    			showMessage("Error", "No records found");
    			return;
    		}
    		StringBuffer buffer=new StringBuffer();
    		while(c.moveToNext())
    		{
    			buffer.append("FacultyID: "+c.getString(0)+"\n");
    			buffer.append("FacultyName: "+c.getString(1)+"\n");
    			buffer.append("ContactNo: "+c.getString(2)+"\n\n");
    		}
    		showMessage("Faculty Details", buffer.toString());
    	}
    	if(view==btnShowInfo)
    	{
			showMessage("Faculty Database System", "Developed By pranav & vasthav");
    	}
    }
    public void showMessage(String title,String message)
    {
    	Builder builder=new Builder(this);
    	builder.setCancelable(true);
    	builder.setTitle(title);
    	builder.setMessage(message);
    	builder.show();
	}
    public void clearText()
    {
    	FacultyID.setText("");
    	FacultyName.setText("");
    	ContactNo.setText("");
    	FacultyID.requestFocus();
    }
}