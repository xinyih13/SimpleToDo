package com.example.simpletodo

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.RecoverySystem
import android.widget.Button
import android.widget.EditText
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset


class MainActivity : AppCompatActivity() {
    var listofTasks = mutableListOf<String>()
    lateinit var adapter : TaskItemAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onlongClickListener = object : TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                //remove item & notify the adapter
                listofTasks.removeAt(position)
                adapter.notifyDataSetChanged()

                saveItems()
            }
        }

        val onClickListener = object : TaskItemAdapter.OnClickListener{
            override fun onItemClicked(position: Int) {
                //remove item & notify the adapter
                launchEditView(position)
            }
        }

        loadItems()

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        adapter = TaskItemAdapter(listofTasks,onlongClickListener,onClickListener)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)


        val inputfield = findViewById<EditText>(R.id.addTaskField)
        findViewById<Button>(R.id.button).setOnClickListener {
            val input = inputfield.text.toString()
            listofTasks.add(input)
            //Important!!! notify the adapter
            adapter.notifyItemInserted(listofTasks.size-1)
            inputfield.setText("")

            saveItems()
        }
    }

    fun launchEditView(position: Int) {
        // first parameter is the context, second is the class of the activity to launch
        val i = Intent(this@MainActivity,EditActivity::class.java)
        i.putExtra("item_name", listofTasks[position])
        i.putExtra("index", position)
        getResult.launch(i)

    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                if (intent != null){
                    val newName = intent.getStringExtra("newTodo",)
                    val position = intent.getIntExtra("index",0)
                    if (newName != null) {
                        listofTasks.set(position,newName)
                    }
                    adapter.notifyItemChanged(position)

                    saveItems()
                }

            }
        }

    //save the data that the user has inputted
    fun getDataFile() : File {
        return File(filesDir,"dta.txt")
    }
    fun loadItems() {
        try {
            listofTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioExceptioin: IOException){
            ioExceptioin.printStackTrace()
        }

    }

    fun saveItems(){
        try{
            FileUtils.writeLines(getDataFile(),listofTasks)
        } catch (ioExceptioin: IOException){
            ioExceptioin.printStackTrace()
        }

    }

}