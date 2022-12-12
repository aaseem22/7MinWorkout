package com.example.a7minworkout

object Constants {
    fun defaultExerciseList(): ArrayList<ExerciseModel>{
        // val exerciseList will give us the arraylist of exercises
        val exerciseList= ArrayList<ExerciseModel>()

        val jumpingRope = ExerciseModel(
            1,
            "Jumping Ropes",
            R.drawable.jumpingrope,
            false,
            false
        )
        exerciseList.add(jumpingRope)

        val pushUps = ExerciseModel(
            2,
            "PushUps",
            R.drawable.pilates,
            false,
            false
        )
        exerciseList.add(pushUps)

        val dips = ExerciseModel(
            3,
            "Dips",
            R.drawable.dips1,
            false,
            false
        )
        exerciseList.add(dips)

        val sitUps = ExerciseModel(
            4,
            "SitUps",
            R.drawable.situps,
            false,
            false
        )
        exerciseList.add(sitUps)

        val bicepCurls = ExerciseModel(
            5,
            "Bicep Curls",
            R.drawable.bicep_curls,
            false,
            false
        )
        exerciseList.add(bicepCurls)

        val shoulderPress = ExerciseModel(
            6,
            "Shoulder Press",
            R.drawable.shoulderpress2,
            false,
            false
        )
        exerciseList.add(shoulderPress)

        val squats = ExerciseModel(
            7,
            "Squats",
            R.drawable.squat,
            false,
            false
        )
        exerciseList.add(squats)

        val squatsPlate = ExerciseModel(
            8,
            "Squats Plate",
            R.drawable.squats_plate,
            false,
            false
        )
        exerciseList.add(squatsPlate)

        val cardio = ExerciseModel(
            9,
            "Running",
            R.drawable.runner,
            false,
            false
        )
        exerciseList.add(cardio)

        val plank = ExerciseModel(
            10,
            "Plank",
            R.drawable.plank,
            false,
            false
        )
        exerciseList.add(plank)



        return exerciseList
    }
}