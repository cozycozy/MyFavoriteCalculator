package apps.marketableskill.biz.myfavoritecalculator

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import kotlinx.android.synthetic.main.fragment_calculator.*
import kotlinx.android.synthetic.main.fragment_calculator.view.*
import java.util.Arrays.asList




class CalculatorFragment : Fragment() {

    var fomularArrayList : ArrayList<String> = ArrayList<String>().apply { add("0") }

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

            calculate("1")
            updateUI("1")

        }

        imageView2.setOnClickListener {
            Log.v("View2:", "clicked!")

            calculate("2")
            updateUI("2")

        }

        imageViewkakeru.setOnClickListener {

            Log.v("kakeru:", "clicked!")

            updateUI("2")

        }

        viewModel = ViewModelProviders.of(this).get(CalculatorViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun updateUI(id: String) {

        //計算式の更新
        var fomularText = ""

        for (value in fomularArrayList){
            fomularText = fomularText + value
        }

        textViewfomula.text = fomularText

        textOutcome.text = fomularText

    }

    private fun calculate(input : String){

        if (fomularArrayList.size == 1){

            val fomular = fomularArrayList[0]
//        Boxが1つだったら
            if(fomular == "0") {
//        　→ 0の場合
//        　　 0を消して入力された数値を設定、その後に計算結果と計算式を反映
                fomularArrayList[0] = input
            } else {
//        　→ 0以外の場合
//        　　 数字の後ろに入力された数値を設定、その後に計算結果と計算式を反映
                fomularArrayList[0] = fomular + input
            }
        } else {
//        Boxが2つ以上だったら
            val lastBoxValue = fomularArrayList[fomularArrayList.size - 1]

            if (checkNumeric(lastBoxValue)) {
//        　→ 直近のBoxが数値だった場合
                if (lastBoxValue == "0") {
//        　　 → 数値が0の場合
//        　 　　 0を消して入力された数値を設定、その後に計算結果と計算式を反映
                    fomularArrayList[fomularArrayList.size - 1] = input
                } else {
//        　 　→ 0以外の場合
//        　　 　 数字の後ろに入力された数値を設定、その後に計算結果と計算式を反映
                    fomularArrayList[fomularArrayList.size - 1] = lastBoxValue + input
                }
            } else {
//        　→ 直近のBoxが演算子の場合
//        　　 Boxを作り、入力された数値を設定、その後に計算結果と計算式を反映
                fomularArrayList.add(input)
            }

        }


    }

    private fun checkNumeric(number: String): Boolean{
        val pattern = java.util.regex.Pattern.compile("^[0-9]*$")
        val matcher = pattern.matcher(number)
        return matcher.matches()
    }

}

