package com.example.a7minworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.view.View
import android.widget.Toast
import com.example.a7minworkout.databinding.ActivityBmiPageBinding
import java.math.BigDecimal
import java.math.RoundingMode

class Bmi_page : AppCompatActivity() {

    companion object{
        private const val METRIC_UNITS= "METRIC_UNIT_VIEW"
        private const val US_UNITS= "US_UNIT_VIEW"
    }

    private  var currentViewVisible: String=  METRIC_UNITS  // a variable to hold value to make a selected view visible
    private var binding: ActivityBmiPageBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiPageBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        setSupportActionBar(binding?.toolbarBmi)
        if (supportActionBar != null) {
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title = "BMI Calculator"
        }
        //onBackPressed= to move back to the previous screen, u must use NavigationOnClickListner
        binding?.toolbarBmi?.setNavigationOnClickListener {
            onBackPressed()
        }
        makeVisibleMetricView()

        binding?.rgUnits?.setOnCheckedChangeListener { _, checkedId ->
            if(checkedId== R.id.rbMetricUnits){
                makeVisibleMetricView()
            }else{
                makeVisibleUSUnitView()
            }
        }
        binding?.calculateBmi?.setOnClickListener{
            calculateUnits()
        }

    }

    private  fun  makeVisibleMetricView(){
        currentViewVisible= METRIC_UNITS
        binding?.tilMetricUnitWeight?.visibility= View.VISIBLE
        binding?.tilMetricUnitHeight?.visibility = View.VISIBLE
        binding?.tilUsUnitHeight?.visibility= View.INVISIBLE
        binding?.tilUsInch?.visibility= View.INVISIBLE

        binding?.etMetricHeight?.text!!.clear()
        binding?.etMetricWeight?.text!!.clear()

        binding?.llDisplayBmiResult?.visibility = View.INVISIBLE
    }

    private fun makeVisibleUSUnitView(){
        currentViewVisible= US_UNITS
        binding?.tilMetricUnitWeight?.visibility= View.VISIBLE
        binding?.tilMetricUnitHeight?.visibility = View.INVISIBLE
        binding?.tilUsUnitHeight?.visibility= View.VISIBLE
        binding?.tilUsInch?.visibility= View.VISIBLE

        binding?.etUsFeet?.text!!.clear()
        binding?.etUsInch?.text!!.clear()
        binding?.llDisplayBmiResult?.visibility= View.INVISIBLE
    }

     private fun calculateUnits(){
    if (currentViewVisible== METRIC_UNITS){
        if(validateMetricUnits()){
            // we divide height by 100 because we want height in meters
            val heightValue : Float = binding?.etMetricHeight?.text.toString().toFloat()/100

            val weightValue : Float = binding?.etMetricWeight?.text.toString().toFloat()

            val bmi = weightValue/(heightValue*heightValue)  // where height is in meters

            displayBmiResult(bmi)


        }else {
            Toast.makeText(this,"Please Enter a valid value",Toast.LENGTH_SHORT).show()

        }
     }
    else{
    if(validateUSUnits()){
        val usWeightValue : Float = binding?.etMetricWeight?.text.toString().toFloat()
        val usFeet : Float= binding?.etUsFeet?.text.toString().toFloat()
        val usInch : Float= binding?.etUsInch?.text.toString().toFloat()
        val heightValue= usFeet+usInch * 12
        val bmi= 703*(usWeightValue/(heightValue*heightValue))
        displayBmiResult(bmi)

    }else{
        Toast.makeText(this,"Please Enter a valid value",Toast.LENGTH_SHORT).show()
    }

    }
     }


        private fun displayBmiResult(bmi: Float){
            val bmiLabel: String
            val bmiDescription: String
            if(bmi.compareTo(15f)<= 0){
                bmiLabel=" Very severely UnderWeight"
                bmiDescription="You Must FOCUS on your Nutrition"
            }else if(bmi.compareTo(15f)>0 && bmi.compareTo(16f)<=0){
                bmiLabel="Severely UnderWeight"
                bmiDescription="You Must FOCUS on your Nutrition"
            }else if(bmi.compareTo(16f)>=0 && bmi.compareTo(18.5f)<=0){
                bmiLabel="UnderWeight"
                bmiDescription="You Must FOCUS on your Nutrition and Workout"
            }
            else if(bmi.compareTo(18.5f)>=0 && bmi.compareTo(25f)<=0){
                bmiLabel="Normal"
                bmiDescription="You are Really Fit n Strong!!!"
            }
            else if(bmi.compareTo(25f)>=0 && bmi.compareTo(30f)<=0){
                bmiLabel="OverWeight"
                bmiDescription="You really must Workout!!!"
            }
            else if(bmi.compareTo(30f)>=0 && bmi.compareTo(35f)<=0){
                bmiLabel="Moderate Obese"
                bmiDescription="You really must Workout!!!"
            }
            else if(bmi.compareTo(35f)>=0 && bmi.compareTo(40f)<=0){
                bmiLabel="Severely  Obese"
                bmiDescription="You are in very dangerous condition act now"
            }
            else {
                bmiLabel="Severely  Obese"
                bmiDescription="You are in very very dangerous condition act now"
            }

            //to convert float to string
            val bmiValue= BigDecimal(bmi.toDouble())
                .setScale(2,RoundingMode.HALF_EVEN).toString()

            binding?.llDisplayBmiResult?.visibility = View.VISIBLE
            binding?.tvBmi?.text= bmiValue
            binding?.tvBmiResult?.text= bmiLabel
            binding?.tvBmiResultDescription?.text= bmiDescription
        }


    private fun validateMetricUnits(): Boolean {
        var isValid = true

        if (binding?.etMetricHeight?.text!!.toString().isEmpty()) {
            isValid = false

        } else if (binding?.etMetricWeight?.text!!.toString().isEmpty()) {
            isValid = false
        }
        return isValid
    }

     private fun validateUSUnits(): Boolean{
         var isValid = true
        if(binding?.etUsFeet?.text!!.toString().isEmpty()){
        isValid= false
        }
          else if(binding?.etUsInch?.text!!.toString().isEmpty()) {
             isValid = false
         }
         return isValid
     }



}