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

            figureArrayList.add("1")
            makeOutput("1")
            updateUI("1")

        }

        imageView2.setOnClickListener {
            Log.v("View2:", "clicked!")

            makeOutput("2")
            updateUI("2")

        }

        imageViewkakeru.setOnClickListener {
            Log.v("kakeru:", "clicked!")

            calculateSymbol("✕")
            updateUI("2")

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

    private fun calculateSymbol(symbol: String) {

//        【演算子をタップされた場合】

        val lastBoxValue = symbolArrayList[symbolArrayList.size - 1]

        if (checkNumeric(lastBoxValue)) {

//        直近のBoxが数値の場合
//        　→ Boxを追加し、演算子を設定、計算式を反映
            symbolArrayList.add(symbol)

        } else {
//        直近のBoxが演算子の場合
//        　→ 直近のBoxの演算子を入力された演算子に置き換える。その後に計算式を反映
            symbolArrayList[symbolArrayList.size - 1] = symbol
        }

    }

    private fun updateUI(id: String) {

        //計算式の更新
        var fomularText = ""

        for (value in symbolArrayList){
            fomularText = fomularText + value
        }

        textViewfomula.text = fomularText

        textOutcome.text = fomularText

    }

    private fun makeOutput(input : String){


        var maxSize = symbolArrayList.size - 1

        for(i in maxSize downTo 0 step -1) {

            var symbol = symbolArrayList[i]

            if (symbol == symbolMultiply || symbol == symbolDivide) {

                val figure1 = figureArrayList[i]
                val figure2 = figureArrayList[i + 1]

                figureArrayList[i] = calculate(figure1,figure2,symbol)
                figureArrayList.removeAt(i+1)
                symbolArrayList.removeAt(i)
            }

        }

        maxSize = symbolArrayList.size -1

        for (i in maxSize downTo 0 step -1){

            val figure1 = figureArrayList[i]
            val figure2 = figureArrayList[i + 1]
            var symbol = symbolArrayList[i]

            figureArrayList[i] = calculate(figure1,figure2,symbol)
            figureArrayList.removeAt(i+1)
            symbolArrayList.removeAt(i)

        }



        if (symbolArrayList.size == 1){

            val fomular = symbolArrayList[0]
//        Boxが1つだったら
            if(fomular == "0") {
//        　→ 0の場合
//        　　 0を消して入力された数値を設定、その後に計算結果と計算式を反映
                symbolArrayList[0] = input
                outputFigure = input
            } else {
//        　→ 0以外の場合
//        　　 数字の後ろに入力された数値を設定、その後に計算結果と計算式を反映
                symbolArrayList[0] = fomular + input
                outputFigure = fomular + input
            }
        } else {
//        Boxが2つ以上だったら
            val lastBoxValue = symbolArrayList[symbolArrayList.size - 1]

            if (checkNumeric(lastBoxValue)) {
//        　→ 直近のBoxが数値だった場合
                if (lastBoxValue == "0") {
//        　　 → 数値が0の場合
//        　 　　 0を消して入力された数値を設定、その後に計算結果と計算式を反映
                    symbolArrayList[symbolArrayList.size - 1] = input
                } else {
//        　 　→ 0以外の場合
//        　　 　 数字の後ろに入力された数値を設定、その後に計算結果と計算式を反映
                    symbolArrayList[symbolArrayList.size - 1] = lastBoxValue + input
                }
                outputFigure = calculate()
            } else {
//        　→ 直近のBoxが演算子の場合
//        　　 Boxを作り、入力された数値を設定、その後に計算結果と計算式を反映
                symbolArrayList.add(input)
            }

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

