package com.example.a7minworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.a7minworkout.databinding.ActivityFinishExerciseBinding
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class finish_exercise : AppCompatActivity() {
    private var binding: ActivityFinishExerciseBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityFinishExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarF)
        binding?.toolbarF?.setNavigationOnClickListener{
            onBackPressed()
            finish()
        }


        if(supportActionBar!= null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        binding?.finish?.setOnClickListener {
            Toast.makeText(applicationContext,
                "ThankYou!!!! ",
                Toast.LENGTH_SHORT).show()

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        val historyDao= ( application as WorkoutApp).db.historyDao()
        addDateToDataBase(historyDao)
    }

    private fun addDateToDataBase(historyDao: HistoryDao){
        val myCalendar= Calendar.getInstance()
        val dateTime= myCalendar.time
        Log.e("date:",""+dateTime)

        val sdf= SimpleDateFormat("dd MMM yyyy HH:mm:ss" ,Locale.getDefault() )
        val date= sdf.format(dateTime)
        Log.e("Formatted Date:", "" +date)

        lifecycleScope.launch {
            historyDao.insert(HistoryEntity(date))
            Log.e("Date",
            "Date Added")
        }

    }



}