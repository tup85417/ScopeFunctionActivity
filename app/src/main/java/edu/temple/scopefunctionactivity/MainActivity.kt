package edu.temple.scopefunctionactivity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // You can test your helper functions by calling them from onCreate() and
        // printing their output to the Log, which is visible in the Logcat:
        // eg. Log.d("function output", getTestDataArray().toString())

        val testDataArray = getTestDataArray()
        Log.d("ScopeFunction", "testDataArray = $testDataArray")

        val testDataArrayDouble = testDataArray.map { it.toDouble() }
        val avgLessThanMed = averageLessThanMedian(testDataArrayDouble)
        Log.d("ScopeFunction", "avgLessThanMed = $avgLessThanMed")

        val data = listOf(1, 2, 3)
        val view1 = getView(position = 0, recycledView = null, collection = data, context = this)
        Log.d("ScopeFunction", "view1 text = ${(view1 as TextView).text}")

        val view2 = getView(position = 1, recycledView = view1, collection = data, context = this)
        Log.d("ScopeFunction", "view2 text = ${(view2 as TextView).text}")
        Log.d("ScopeFunction", "Same Obj? ${view1 === view2}")
    }

    private fun getTestDataArray(): List<Int> =
        MutableList(size = 10) { Random.nextInt() }.apply { sort() }

    // Return true if average value in list is less than median value, false otherwise
    private fun averageLessThanMedian(listOfNumbers: List<Double>): Boolean =
        listOfNumbers.average() < listOfNumbers
            .sorted()
            .let { sortedList ->
                if (sortedList.size % 2 == 0)
                    (sortedList[sortedList.size / 2] + sortedList[(sortedList.size - 1) / 2]) / 2
                else
                    sortedList[sortedList.size / 2]
            }

    // Create a view from an item in a collection, but recycle if possible (similar to an AdapterView's adapter)
    private fun getView(
        position: Int,
        recycledView: View?,
        collection: List<Int>,
        context: Context
    ): View =
        ((recycledView as TextView?) ?: TextView(context).apply {
            setPadding(5, 10, 10, 0)
            textSize = 22f
        }).also {
            it.text = collection[position].toString()
        }
}
