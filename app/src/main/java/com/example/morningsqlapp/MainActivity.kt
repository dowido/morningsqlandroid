package com.example.morningsqlapp

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    lateinit var edtName:EditText
    lateinit var edtEmail:EditText
    lateinit var edtIdNumber:EditText
    lateinit var btnSave:Button
    lateinit var btnView:Button
    lateinit var btnDelete:Button
    lateinit var db:SQLiteDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        edtName = findViewById(R.id.mEdtName)
        edtEmail =findViewById(R.id.mEdtEmail)
        edtIdNumber = findViewById(R.id.mEdtId)
        btnDelete = findViewById(R.id.mBtnDelete)
        btnSave = findViewById(R.id.mBtnSave)
        btnView = findViewById(R.id.mBtnView)

        // create a database called Emobilis Db
        db = openOrCreateDatabase("eMobilisDb", Context.MODE_PRIVATE, null)

        //Create a table called users inside the database
        db.execSQL("CREATE TABLE IF NOT EXISTS users(jina VARCHAR, arafa VARCHAR, kitambulisho VARCHAR)")

        //Setting i click listeners
        btnSave.setOnClickListener{
            //receive data from the user
            var name = edtName.text.toString().trim()
            var email = edtEmail.text.toString().trim()
            var idNumber = edtIdNumber.text.toString().trim()
            // check if the user is submitting empty fields
            if(name.isEmpty()|| idNumber.isEmpty()) {
                //display an error message
                message("EMPTY FIELDS", "please fill in all inputs")
            }else{
                // proceed to save te data
                db.execSQL("INSERT INTO user VALUES('"+name+"','"+email+"', '"+idNumber+"')")
                clear()
                message("SUCCESS","User saved successfully")
            }

        }
        btnDelete.setOnClickListener{
            val idNumber = edtIdNumber.text.toString().trim()
            if (idNumber.isEmpty()){
                message("EMPTY FIELDS", "Please enter an ID number")
            }else{
                //use cursor to
                var cursor =db.rawQuery("SELECT * FROM users WHERE kitambulisho='"+idNumber+"'",null)
            }

        }
        btnView.setOnClickListener{
            var cursor = db.rawQuery("SELECT * FROM users",null)
            //check if there is any record in the database
            if (cursor.count == 0) {
                message("NO RECORD" ,"sorry no records were found")
            }else{
                var buffer = StringBuffer()
                while (cursor.moveToNext()){
                    var retrievedName = cursor.getString(0)
                    var retrievedEmail = cursor.getString(1)
                    var retrievedidNumber = cursor.getString(2)
                    buffer.append(retrievedName+ "\n")
                    buffer.append(retrievedEmail+ "\n")
                    buffer.append(retrievedidNumber+ "\n\n")

                }
                message("users", buffer.toString())
            }

        }

    }
    fun message(title:String, message: String){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton("close", null)
        alertDialog.create().show()

    }
    fun clear(){
        edtName.setText("")
        edtEmail.setText("")
        edtIdNumber.setText("")
    }
}