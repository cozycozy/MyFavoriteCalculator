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

    var pushEqual: Boolean = false

    val symbolPlus : String = "+"
    val symbolsubtract : String = "-"
    val symbolMultiply : String = "✕"
    val symbolDivide : String = "÷"
    val symbolClear : String = "C"
    val symbolDelete : String = "<<<"

    var sum: Long = 0

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

    fun doSymbleOnclickListener(input: String) {

//        var inputSymbol = ""
//
//        when(input) {
//            symbolPlus -> inputSymbol = symbolPlus
//            symbolsubtract -> inputSymbol = symbolsubtract
//            symbolMultiply -> inputSymbol = symbolMultiply
//            symbolDivide -> inputSymbol = symbolDivide
//        }

        Log.v("Symbol:" + input, symbolMultiply + " clicked!")

        if (input == symbolDelete) {

            if (storeSymbolArrayList.size == 0) {
                if (storeFigureArrayList[storeFigureArrayList.lastIndex].length <= 1) {
                    storeFigureArrayList[storeFigureArrayList.lastIndex] = "0"
                } else {
                    storeFigureArrayList[storeFigureArrayList.lastIndex]=
                            storeFigureArrayList[storeFigureArrayList.lastIndex].dropLast(1)
                }
            } else {
                if (storeFigureArrayList.size == storeSymbolArrayList.size) {
                    storeSymbolArrayList.removeAt(storeSymbolArrayList.lastIndex)
                } else {
                    if (storeFigureArrayList[storeFigureArrayList.lastIndex].length <= 1) {
                        storeFigureArrayList.removeAt(storeFigureArrayList.lastIndex)
                    } else {
                        storeFigureArrayList[storeFigureArrayList.lastIndex]=
                                storeFigureArrayList[storeFigureArrayList.lastIndex].dropLast(1)
                    }
                }
            }

        } else {
            inputSymbol(input)
        }
        doCalculate()
        updateUI()

    }

    fun doFigureOnclickListener(input: String) {

        Log.v("View" + input + ":", "clicked!")
        inputFigure(input)
        doCalculate()
        updateUI()

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        //初期化
        storeFigureArrayList.add("0")

        //数値のタップ
        imageView1.setOnClickListener {
            val inputValue = "1"
            doFigureOnclickListener(inputValue)
        }
        imageView2.setOnClickListener {
            val inputValue = "2"
            doFigureOnclickListener(inputValue)
        }
        imageView3.setOnClickListener {
            val inputValue = "3"
            doFigureOnclickListener(inputValue)
        }
        imageView4.setOnClickListener {
            val inputValue = "4"
            doFigureOnclickListener(inputValue)
        }
        imageView5.setOnClickListener {
            val inputValue = "5"
            doFigureOnclickListener(inputValue)
        }
        imageView6.setOnClickListener {
            val inputValue = "6"
            doFigureOnclickListener(inputValue)
        }
        imageView7.setOnClickListener {
            val inputValue = "7"
            doFigureOnclickListener(inputValue)
        }
        imageView8.setOnClickListener {
            val inputValue = "8"
            doFigureOnclickListener(inputValue)
        }
        imageView9.setOnClickListener {
            val inputValue = "9"
            doFigureOnclickListener(inputValue)
        }
        imageView0.setOnClickListener {
            val inputValue = "0"
            doFigureOnclickListener(inputValue)
        }

        //記号のタップ
        imageViewPlus.setOnClickListener {
            doSymbleOnclickListener(symbolPlus)
        }

        imageViewSubtract.setOnClickListener {
            doSymbleOnclickListener(symbolsubtract)
        }

        imageViewMultiply.setOnClickListener {
            doSymbleOnclickListener(symbolMultiply)
        }

        imageViewDivide.setOnClickListener {
            doSymbleOnclickListener(symbolDivide)
        }

        imageViewClear.setOnClickListener{

            textViewfomula.text = "0"
            textOutcome.text = "0"

            //各ストア情報の初期化
            sum = 0
            storeFigureArrayList = ArrayList<String>()
            storeFigureArrayList.add("0")
            storeSymbolArrayList = ArrayList<String>()

        }

        imageViewEqual.setOnClickListener {

            val outcome = textOutcome.text

            //各ストア情報の初期化
            storeFigureArrayList = ArrayList<String>()
            storeFigureArrayList.add(outcome.toString())
            storeSymbolArrayList = ArrayList<String>()

            textOutcome.text = outcome
            textViewfomula.text = outcome

            pushEqual = true

        }

        imageViewDel.setOnClickListener {
            doSymbleOnclickListener(symbolDelete)
        }

        imageViewPercent.setOnClickListener {

        }

        imageViewPN.setOnClickListener {

        }


        viewModel = ViewModelProviders.of(this).get(CalculatorViewModel::class.java)
        // TODO: Use the ViewModel
    }


    private fun inputFigure(input: String) {

        if (pushEqual) {
            storeFigureArrayList[0] = input
            pushEqual = false
            return
        }

        if (storeSymbolArrayList.size == 0) {
            if (storeFigureArrayList[0] == "0" ){
                storeFigureArrayList[0] = input
            } else {
                storeFigureArrayList[0] = storeFigureArrayList[0] + input
            }
        } else {
            if (storeFigureArrayList.size == storeSymbolArrayList.size){
                storeFigureArrayList.add(input)
            } else {
                if (storeFigureArrayList.last() == "0") {
                    storeFigureArrayList[storeFigureArrayList.lastIndex] = input
                } else {
                    storeFigureArrayList[storeFigureArrayList.lastIndex] += input
                }
            }
        }

    }

    private fun inputSymbol(symbol: String) {

//        【演算子をタップされた場合】
        if (pushEqual) {
            pushEqual = false
        }

        if (storeFigureArrayList.size > storeSymbolArrayList.size) {

            storeSymbolArrayList.add(symbol)

        } else {

            storeSymbolArrayList[storeSymbolArrayList.lastIndex] = symbol

        }

    }

    private fun updateUI() {

        var makefomular = ""
        var makeResult = ""

        if (storeSymbolArrayList.size == 0) {
            //数字1つだけの入力の場合

            makefomular = storeFigureArrayList[0]
            makeResult = storeFigureArrayList[0]

        } else {
            //数字も演算子もある場合

            val count = storeFigureArrayList.size - 1
            var fomular = ""

            for (i in 0..count) {

                if (storeSymbolArrayList.size <= i) {
                    fomular = ""
                } else {
                    fomular = storeSymbolArrayList[i]
                }

                makefomular = makefomular + storeFigureArrayList[i] + fomular

            }

            makeResult = sum.toString()

        }

        textViewfomula.text = makefomular
        textOutcome.text = makeResult

    }

    private fun doCalculate(){

        symbolArrayList = ArrayList<String>(storeSymbolArrayList)
        figureArrayList = ArrayList<String>(storeFigureArrayList)

        if (figureArrayList.size <= 1) {
            sum = figureArrayList[0].toLong()
            return
        }

        var maxSize = figureArrayList.size - 1

        for(i in maxSize downTo 1) {

            var symbol = symbolArrayList[i-1]

            if (symbol == symbolMultiply || symbol == symbolDivide) {

                val figure1 = figureArrayList[i-1]
                val figure2 = figureArrayList[i]

                figureArrayList[i-1] = calculate(figure1.toLong(),figure2.toLong(),symbol).toString()
                figureArrayList.removeAt(i)
                symbolArrayList.removeAt(i-1)
            }

        }

        maxSize = figureArrayList.size

        if (maxSize <= 1) {
            //計算する必要がないケースのため、合計値に数値を入れて戻る
            sum = figureArrayList[0].toLong()
            return
        }

        sum = figureArrayList[0].toLong()

        for (i in 1..maxSize - 1){
            val figure1 = figureArrayList[i]
            var symbol = symbolArrayList[i - 1]

            sum = calculate(sum,figure1.toLong(),symbol)
        }
    }


    private fun calculate(f1:Long, f2:Long, symbol: String): Long {

        var output : Long = 0

        if (symbol == symbolPlus){
            output = f1 + f2
        }

        if (symbol == symbolsubtract){
            output = f1 - f2
        }

        if (symbol == symbolMultiply){
            output = f1 * f2
        }

        if (symbol == symbolDivide){
            output = f1 / f2
        }

        return output

    }

    private fun checkNumeric(number: String): Boolean{
        val pattern = java.util.regex.Pattern.compile("^[0-9]*$")
        val matcher = pattern.matcher(number)
        return matcher.matches()
    }

}

