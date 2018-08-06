package apps.marketableskill.biz.myfavoritecalculator

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_calculator.*


class CalculatorFragment : Fragment() {

    val symbolPlus : String = "+"
    val symbolsubtract : String = "-"
    val symbolMultiply : String = "✕"
    val symbolDivide : String = "÷"

    var symbolArrayList : ArrayList<String> = ArrayList<String>()
    var figureArrayList : ArrayList<String> = ArrayList<String>()

    var storeSymbolArrayList : ArrayList<String> = ArrayList<String>()
    var storeFigureArrayList : ArrayList<String> = ArrayList<String>()

    var outputFomular : String = ""
    var outputFigure : String = ""

    companion object {
        fun newInstance() = CalculatorFragment()
    }

    private lateinit var viewModel: CalculatorViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_calculator, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        imageView1.setOnClickListener {
            Log.v("View1:", "clicked!")

            inputFigure("1")
            doCalculate("1")
            updateUI("1")

        }

        imageView2.setOnClickListener {
            Log.v("View2:", "clicked!")

            inputFigure("2")
            doCalculate("2")
            updateUI("2")

        }

        imageViewkakeru.setOnClickListener {
            Log.v("symbolMultiply:", symbolMultiply + " clicked!")

            inputSymbol(symbolMultiply)
            doCalculate(symbolMultiply)
            updateUI(symbolMultiply)

        }

//        imageViewkakeru.setOnClickListener {
//
//            Log.v("kakeru:", "clicked!")
//
//            calculateSymbol("✕")
//
//            updateUI("2")
//
//        }

        viewModel = ViewModelProviders.of(this).get(CalculatorViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun inputFigure(input: String) {

        if (storeSymbolArrayList.size == 0) {
            if (storeFigureArrayList[0] == "0" ){
                storeFigureArrayList[0] == input
            } else {
                storeFigureArrayList[0] = storeFigureArrayList[0] + input
            }
        } else {
            if (storeFigureArrayList.size == storeSymbolArrayList.size){
                storeFigureArrayList.add(input)
            } else {
                if (storeFigureArrayList.last() == "0") {
                    storeFigureArrayList[storeSymbolArrayList.lastIndex] = input
                } else {
                    storeFigureArrayList[storeSymbolArrayList.lastIndex] =
                            storeFigureArrayList[storeSymbolArrayList.lastIndex] + input
                }
            }
        }

    }

    private fun inputSymbol(symbol: String) {

//        【演算子をタップされた場合】

        if (storeFigureArrayList.size > storeSymbolArrayList.size) {

            storeSymbolArrayList.add(symbol)

        } else {

            storeSymbolArrayList[storeSymbolArrayList.lastIndex] = symbol

        }

    }

    private fun updateUI(id: String) {

        var makefomular = ""
        var makeResult = ""

        if (storeSymbolArrayList.size == 0) {
            //数字1つだけの入力の場合

            makefomular = storeFigureArrayList[0]
            makeResult = storeFigureArrayList[0]

        } else {
            //数字も演算子もある場合

            val count = storeFigureArrayList.size - 1
            var diffString = ""

            for (i in 0..count) {

                if (storeSymbolArrayList.size == count) {
                    diffString = ""
                } else {
                    diffString = storeSymbolArrayList[i]
                }

                makefomular = storeFigureArrayList[i] + diffString

            }

            makeResult = figureArrayList[0]

        }

        textViewfomula.text = makefomular
        textOutcome.text = makeResult

    }

    private fun doCalculate(input : String){

        symbolArrayList = storeSymbolArrayList
        figureArrayList = storeFigureArrayList

        if (figureArrayList.size <= 1) {
            return
        }

        var maxSize = symbolArrayList.size

        for(i in maxSize downTo 0 step -1) {

            var symbol = symbolArrayList[i-1]

            if (symbol == symbolMultiply || symbol == symbolDivide) {

                val figure1 = figureArrayList[i-1]
                val figure2 = figureArrayList[i]

                figureArrayList[i-1] = calculate(figure1,figure2,symbol)
                figureArrayList.removeAt(i)
                symbolArrayList.removeAt(i-1)
            }

        }

        maxSize = symbolArrayList.size

        for (i in maxSize downTo 0 step -1){

            val figure1 = figureArrayList[i-1]
            val figure2 = figureArrayList[i]
            var symbol = symbolArrayList[i-1]

            figureArrayList[i-1] = calculate(figure1,figure2,symbol)
            figureArrayList.removeAt(i)
            symbolArrayList.removeAt(i-1)

        }


    }


    private fun calculate(f1:String, f2:String, symbol: String): String {

        var output : Long = 0

        if (symbol == symbolPlus){
            output = f1.toLong() + f2.toLong()
        }

        if (symbol == symbolsubtract){
            output = f1.toLong() - f2.toLong()
        }

        if (symbol == symbolMultiply){
            output = f1.toLong() * f2.toLong()
        }

        if (symbol == symbolDivide){
            output = f1.toLong() / f2.toLong()
        }

        return output.toString()

    }

    private fun checkNumeric(number: String): Boolean{
        val pattern = java.util.regex.Pattern.compile("^[0-9]*$")
        val matcher = pattern.matcher(number)
        return matcher.matches()
    }

}

