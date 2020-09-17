package com.example.todolist

import android.app.AlertDialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import java.io.OutputStreamWriter
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    private var tareas: ArrayList<String>? = null
    private var adaptador1: ArrayAdapter<String>? = null
    private var lv1: ListView? = null
    private var et1: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tareas = ArrayList()
        adaptador1 = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, tareas!!)
        lv1 = findViewById(R.id.listView) as ListView
        lv1!!.adapter = adaptador1

        et1 = findViewById(R.id.editText) as EditText

        lv1!!.onItemLongClickListener = AdapterView.OnItemLongClickListener { adapterView, view, i, l ->
            val dialogo1 = AlertDialog.Builder(this@MainActivity)
            dialogo1.setTitle("IMPORTARNTE")
            dialogo1.setMessage("eliminar elementos de la lista")
            dialogo1.setCancelable(false)
            dialogo1.setPositiveButton("CONFIRMAS") { dialogo1, id ->
                tareas!!.removeAt(i)
                adaptador1!!.notifyDataSetChanged()

            }
            dialogo1.setNegativeButton("CANCELAR") { dialogo1, id -> }
            dialogo1.show()

            false
        }
        leerArchivo()
    }

    fun leerArchivo(){
        val scan = Scanner(getResources().openRawResource(R.raw.tareas))

        while (scan.hasNextLine()) {
            var allText = ""
            val line = scan.nextLine()
            allText += line
            tareas!!.add(allText)
        }
        scan.close()
        adaptador1!!.notifyDataSetChanged()
    }


    fun agregar(view: View) {
        tareas!!.add(et1!!.text.toString())
        adaptador1!!.notifyDataSetChanged()
        try {
            val fileOutputStream = openFileOutput("tareas.txt", Context.MODE_PRIVATE)
            for (i in tareas!!.indices){
                val outputStreamWriter = OutputStreamWriter(fileOutputStream)
                outputStreamWriter.write(et1!!.text.toString())
                outputStreamWriter.close()
                Toast.makeText(applicationContext,"ARC GUARDADO",Toast.LENGTH_SHORT).show()
                et1!!.setText("")
            }


        }
        catch (exp: Exception){
            exp.printStackTrace()
        }


    }
}
