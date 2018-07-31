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


class CalculatorFragment : Fragment() {

    var fomularArrayList : ArrayList<String>? = null

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
            Log.v("button:", "clicked!")
            textViewfomula.text = "1"

            updateUI("1")

        }

        viewModel = ViewModelProviders.of(this).get(CalculatorViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun updateUI(id: String) {



    }

}

