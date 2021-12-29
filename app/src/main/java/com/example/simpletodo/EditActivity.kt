package com.example.simpletodo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText

class EditActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        //get item name and display
        val todo = getIntent().getStringExtra("item_name")
        val index = getIntent().getIntExtra("index",0)
        findViewById<EditText>(R.id.editText).setText(todo)

        findViewById<Button>(R.id.button2).setOnClickListener {
            onSubmit(index)
        }
    }

    fun onSubmit(index : Int) {
        val newTodo = findViewById<EditText>(R.id.editText)
        // Prepare data intent
        val data = Intent()
        // Pass relevant data back as a result
        data.putExtra("newTodo", newTodo.getText().toString())
        data.putExtra("index", index)
        data.putExtra("code", 200) // ints work too
        // Activity finished ok, return the data
        setResult(RESULT_OK, data) // set result code and bundle data for response
        finish() // closes the activity, pass data to parent
    }
}