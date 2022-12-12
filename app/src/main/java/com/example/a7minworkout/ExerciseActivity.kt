package com.example.a7minworkout

import android.app.Dialog
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minworkout.databinding.ActivityExerciseBinding
import com.example.a7minworkout.databinding.ActivityFinishExerciseBinding
import com.example.a7minworkout.databinding.CustomDialogBoxBinding
import com.google.android.material.snackbar.Snackbar
import java.lang.Exception
import java.util.*
import kotlin.collections.ArrayList

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    private  var  binding : ActivityExerciseBinding? = null
    private var restTimer: CountDownTimer?= null
    private var restProgress:Int=0
    private var exerciseTimer: CountDownTimer?= null
    private var exerciseProgress:Int=0
    // for 10 sec timer
    private var endProgress: Int= 10
    private var endExerciseProgress: Int= 30

    private var restTimeDuration: Long=10
    private  var exerciseTimeDuration : Long= 30

    private var exerciseList : ArrayList<ExerciseModel>? = null
    private var currentExercisePosition = -1
    private var tts: TextToSpeech?= null
    private var player : MediaPlayer? =null

    private var exerciseAdapter: ExerciseStatusAdapter?= null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbar)

        if(supportActionBar!= null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
        // to get the exercise list
         exerciseList = Constants.defaultExerciseList()
        //onBackPressed= to move back to the previous screen, u must use NavigationOnClickListner
        binding?.toolbar?.setNavigationOnClickListener{
           customDialogBox()
        }

        tts= TextToSpeech(this,this,)
       setRestView()
        // below fun is called only after setting the exercise lists i.e ,
        // exerciseList = Constants.defaultExerciseList()
        //other wise it cause error(nullable)
        setExerciseStatusRecyclerView()
    }

    private fun customDialogBox(){
            val customDialog = Dialog(this)
            val dialogBinding =  CustomDialogBoxBinding.inflate(layoutInflater)
        customDialog.setContentView(dialogBinding.root)
        customDialog.setCanceledOnTouchOutside(false)

        dialogBinding.tvYes.setOnClickListener {
//            Toast.makeText(this,"", Toast.LENGTH_LONG)
//                .show()
            this@ExerciseActivity.finish()
            customDialog.dismiss()

        }
        dialogBinding.tvNo.setOnClickListener {
                    customDialog.dismiss()
        }
        customDialog.show()

    }

    override fun onBackPressed() {
        customDialogBox()
    }


    private fun setExerciseStatusRecyclerView(){
        binding?.rvExerciseStatus?.layoutManager=
            //for grid layout
           // GridLayoutManager(this,LinearLayoutManager.HORIZONTAL)
             LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

                exerciseAdapter= ExerciseStatusAdapter(exerciseList!!)
                binding?.rvExerciseStatus?.adapter= exerciseAdapter
            }

// to make sure that timer gets reset, so we call RestProgressbar inside setRestView (Inheritance)
    private fun setRestView(){
// to add sound
    try{
        val soundUri= Uri.parse("android.resource://com.example.a7minworkout/"
                +R.raw.press_start)
        player=MediaPlayer.create(applicationContext,soundUri)
        player?.isLooping=false
        player?.start()
    }catch(e:Exception){
        e.printStackTrace()

    }
    binding?.flRestView?.visibility=View.VISIBLE
    binding?.tvTitle?.visibility= View.VISIBLE

    binding?.flExerciseView?.visibility= View.INVISIBLE
    binding?.tvExercise?.visibility= View.INVISIBLE
    binding?.ivImageView?.visibility= View.INVISIBLE

    binding?.upcomingLabel?.visibility= View.VISIBLE
    binding?.tvupcomingExerciseName?.visibility= View.VISIBLE
//to get upcoming Exercise Name from the exercise Model
   binding?.tvupcomingExerciseName?.text=
        exerciseList!![currentExercisePosition+1].getName()

        if(restTimer != null) {
            restTimer?.cancel()
            restProgress = 0
        }
        //speakOut("Please Rest")
        setRestProgressBar()
    }
    private fun setUpExerciseView(){
        // make rest view invisible and make exercise View visible
        binding?.flRestView?.visibility=View.INVISIBLE
        binding?.tvTitle?.visibility= View.INVISIBLE

        binding?.flExerciseView?.visibility= View.VISIBLE
        binding?.tvExercise?.visibility= View.VISIBLE
        binding?.ivImageView?.visibility= View.VISIBLE

        binding?.upcomingLabel?.visibility=View.INVISIBLE
        binding?.tvupcomingExerciseName?.visibility= View.INVISIBLE


        if(exerciseTimer != null){
            exerciseTimer?.cancel()
            exerciseProgress=0
        }
// to speak the exercise name
        speakOut(exerciseList!![currentExercisePosition].getName())
        binding?.ivImageView?.setImageResource(
            //get images from objects and arraylist created
                            //to get image at the exact location, we use currentExactPosition
            exerciseList!![currentExercisePosition].getImage())

        binding?.tvExercise?.text=exerciseList!![currentExercisePosition].getName()

        setExerciseProgressBar()
    }

// to set  Rest timer
    private fun setRestProgressBar(){
        binding?.progressBar?.progress = restProgress
                                            // 10 secs               //1 sec
    restTimer= object :CountDownTimer(restTimeDuration*1000,1000){
        override fun onTick(millisUntilFinished: Long) {
            restProgress++
            binding?.progressBar?.progress= endProgress-restProgress
            binding?.tvTimer?.text= (endProgress-restProgress).toString()
        }
        override fun onFinish() {

            // to move to next exercise after completion of progress bar
            currentExercisePosition++
            //to select the exercise
            exerciseList!![currentExercisePosition].setIsSelected(true)
            exerciseAdapter?.notifyDataSetChanged()
            setUpExerciseView()
        }
    }.start()
    }

    // to set exercise timer
    private fun setExerciseProgressBar(){
        binding?.progressBarNo1?.progress = exerciseProgress

        exerciseTimer= object :CountDownTimer(exerciseTimeDuration*1000,1000){
            override fun onTick(millisUntilFinished: Long) {
                exerciseProgress++
                binding?.progressBarNo1?.progress= endExerciseProgress-exerciseProgress
                binding?.tvTimerNo1?.text= (endExerciseProgress-exerciseProgress).toString()
            }
            override fun onFinish() {
                if(currentExercisePosition<exerciseList?.size!!-1){
                    exerciseList!![currentExercisePosition].setIsSelected(false)
                    exerciseList!![currentExercisePosition].setIsCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged()
                    setRestView()
                }else{
                        Toast.makeText(applicationContext,"Congrats, You WorkedOut", Toast.LENGTH_LONG)
                           .show()
                     val i=Intent(this@ExerciseActivity,finish_exercise::class.java)
                    startActivity(i)
                }
            }
        }.start()
    }
// to avoid leakages.
    override fun onDestroy() {
        super.onDestroy()
        binding = null

        if(restTimer != null){
            restTimer?.cancel()
            restProgress=0
        }
        if(exerciseTimer != null){
            exerciseTimer?.cancel()
            exerciseProgress=0
        }
        if(tts != null){
            tts?.stop()
            tts?.shutdown()
        }
        if(player!= null){
            player?.stop()
        }
    }

    override fun onInit(status: Int) {

        if(status==TextToSpeech.SUCCESS){
            val result = tts?.setLanguage(Locale.UK)

            if(result==TextToSpeech.LANG_MISSING_DATA ||
                result==TextToSpeech.LANG_NOT_SUPPORTED){
                Log.e("TTS","Language is not Supported,sry")
                Toast.makeText(this,"Language is not Supported,sry",Toast.LENGTH_LONG).show()
            }
        }else{
            Log.e("TTS","Initialization Failed")
            Toast.makeText(this,"Initialization Failed",Toast.LENGTH_LONG).show()
        }
    }
    private fun speakOut(text:String){
        tts?.speak(text,TextToSpeech.QUEUE_FLUSH,null,"")

    }
}