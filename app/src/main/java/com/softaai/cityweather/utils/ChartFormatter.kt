package com.softaai.cityweather.utils

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.utils.ViewPortHandler
import com.softaai.cityweather.utils.StringFormatter.convertToValueWithUnit
import com.softaai.cityweather.utils.StringFormatter.unitDegrees

/**
 * Created by Amol Pawar on 03-01-2019.
 * softAai Apps
 */

object ChartFormatter{
    class AxisValueFormatter(val values: ArrayList<String>) : IAxisValueFormatter {

        override fun getFormattedValue(value: Float, axis: AxisBase): String {
            return values[value.toInt()]
        }
    }

    class ValueFormatter : IValueFormatter {

        override fun getFormattedValue(value: Float, entry: Entry, dataSetIndex: Int, viewPortHandler: ViewPortHandler): String {
            return convertToValueWithUnit(1, unitDegrees, value.toDouble())
        }
    }
}
