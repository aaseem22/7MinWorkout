package com.example.a7minworkout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.Toast
import com.example.a7minworkout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
                         // xml file to access the ids
    private var binding : ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // viewBinding is a easier method rather than using findViewById Method
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)


            // use of bindingView- it is faster in compile time
        // u cannot use underscore in the id name while using binding
        binding?.flStart?.setOnClickListener{
            Toast.makeText(applicationContext,
                "Start Workout ",
                Toast.LENGTH_SHORT).show()

             val intent = Intent(this, ExerciseActivity::class.java)
            startActivity(intent)
        }

        // if you use FindViewById Method
//        val flStart : FrameLayout= findViewById(R.id.flStart)
//        flStart.setOnClickListener{
//            Toast.makeText(applicationContext,
//            "Start Workout ",
//            Toast.LENGTH_SHORT).show()
//        }


        binding?.Bmi?.setOnClickListener(){
            val  a = Intent(this,Bmi_page::class.java)
            startActivity(a)
        }
        binding?.history?.setOnClickListener(){
            val history = Intent(this, history_exercise::class.java)
            startActivity(history)
        }


    }

    override fun onDestroy() {
        super.onDestroy()

        binding= null
    }
}