package org.d3if0024.bmi


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import org.d3if0024.bmi.databinding.FragmentIdealBinding

/**
 * A simple [Fragment] subclass.
 */
class IdealFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding: FragmentIdealBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_ideal, container, false)
        return binding.root

        //return inflater.inflate(R.layout.fragment_ideal, container, false)
    }


}
