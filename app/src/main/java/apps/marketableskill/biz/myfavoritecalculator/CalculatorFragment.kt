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

    private var pushEqual: Boolean = false

    private val symbolPlus : String = "+"
    private val symbolsubtract : String = "-"
    private val symbolMultiply : String = "✕"
    private val symbolDivide : String = "÷"
    private val symbolDelete : String = "<<<"
    private val symbolDotto : String = "."
    private val symbolPercent : String = "%"

    private var sum: Float = 0f

    private var symbolArrayList : ArrayList<String> = ArrayList<String>()
    private var figureArrayList : ArrayList<String> = ArrayList<String>()

    private var storeSymbolArrayList : ArrayList<String> = ArrayList<String>()
    private var storeFigureArrayList : ArrayList<String> = ArrayList<String>()

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
            sum = 0f
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

            inputPercent()
            doCalculate()
            updateUI()

        }

        imageViewPN.setOnClickListener {

        }

        imageViewDotto.setOnClickListener {
            inputDotto()
            doCalculate()
            updateUI()
        }

        viewModel = ViewModelProviders.of(this).get(CalculatorViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun doSymbleOnclickListener(input: String) {

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

        Log.v("View $input:", "clicked!")
        inputFigure(input)
        doCalculate()
        updateUI()

    }

    private fun inputDotto() {

        if (storeSymbolArrayList.size == 0) {
            storeFigureArrayList[0] = storeFigureArrayList[0] + symbolDotto
            return
        }

        if (storeFigureArrayList.size == storeSymbolArrayList.size){
            storeFigureArrayList.add("0$symbolDotto")
        } else {
            storeFigureArrayList[storeFigureArrayList.lastIndex] += symbolDotto
        }

    }

    private fun inputPercent() {

        if (storeSymbolArrayList.size == 0) {
            storeFigureArrayList[0] = storeFigureArrayList[0] + symbolPercent
            return
        }

        if (storeFigureArrayList.size != storeSymbolArrayList.size){
            storeFigureArrayList[storeFigureArrayList.lastIndex] += symbolPercent
        }

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

        if(makeResult.contains(".0")) {
            textOutcome.text = makeResult.replace(".0","")
            return
        }
        textOutcome.text = makeResult

    }

    private fun exchangeFloat(txt : String) : Float {

        var exchange = 0F

        if ( txt.contains("%")) {
            var removePercent = txt.replace(symbolPercent,"")
            exchange = removePercent.toFloat() / 100
        } else {
            exchange = txt.toFloat()
        }

        return exchange
    }

    private fun doCalculate(){

        symbolArrayList = ArrayList<String>(storeSymbolArrayList)
        figureArrayList = ArrayList<String>(storeFigureArrayList)

        if (figureArrayList.size <= 1) {
            sum = exchangeFloat(figureArrayList[0])
            return
        }

        var maxSize = figureArrayList.size - 1

        for(i in maxSize downTo 1) {

            val symbol = symbolArrayList[i-1]

            if (symbol == symbolMultiply || symbol == symbolDivide) {

                val figure1 = figureArrayList[i-1]
                val figure2 = figureArrayList[i]

                figureArrayList[i-1] = calculate(exchangeFloat(figure1),exchangeFloat(figure2),symbol).toString()
                figureArrayList.removeAt(i)
                symbolArrayList.removeAt(i-1)
            }

        }

        maxSize = figureArrayList.size

        if (maxSize <= 1) {
            //計算する必要がないケースのため、合計値に数値を入れて戻る
            sum = exchangeFloat(figureArrayList[0])
            return
        }

        sum = exchangeFloat(figureArrayList[0])

        for (i in 1..maxSize - 1){
            val figure1 = figureArrayList[i]
            val symbol = symbolArrayList[i - 1]

            sum = calculate(sum,exchangeFloat(figure1),symbol)
        }
    }


    private fun calculate(f1:Float, f2:Float, symbol: String): Float {

        var output : Float = 0F

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

}

