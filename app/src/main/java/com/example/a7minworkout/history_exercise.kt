package com.example.a7minworkout

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a7minworkout.databinding.ActivityHistoryExerciseBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class history_exercise : AppCompatActivity() {
    private var binding: ActivityHistoryExerciseBinding?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHistoryExerciseBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolbarHistory)

        if(supportActionBar!= null){
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            supportActionBar?.title= "HISTORY"
        }
        binding?.toolbarHistory?.setNavigationOnClickListener {
            onBackPressed()
        }
        val dao = ( application as WorkoutApp).db.historyDao()
        getAllCompletedDates(dao)
    }

    private fun getAllCompletedDates(historyDao: HistoryDao){
        lifecycleScope.launch {
            historyDao.fetchAllDates().collect{
                allCompletedDates->
                for(i in allCompletedDates){
                    Log.e("Date","$i"+i.date)
                }
                if(allCompletedDates.isNotEmpty()){
                    binding?.tvHistory?.visibility = View.VISIBLE
                    binding?.rvHistory?.visibility= View.VISIBLE
                    binding?.tvNoDataAvailable?.visibility= View.INVISIBLE
                    binding?.rvHistory?.layoutManager=LinearLayoutManager(this@history_exercise)

                 val dates=ArrayList<String>()
                    for(date in allCompletedDates){
                        dates.add(date.date)
                    }

                     val historyAdapter= HistoryAdapter(dates)
                    binding?.rvHistory?.adapter= historyAdapter

                }else{
                    binding?.tvHistory?.visibility = View.INVISIBLE
                    binding?.rvHistory?.visibility= View.INVISIBLE
                    binding?.tvNoDataAvailable?.visibility= View.VISIBLE


                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding= null
    }
}