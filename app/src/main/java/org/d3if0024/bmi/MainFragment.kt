package org.d3if0024.bmi


import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import org.d3if0024.bmi.databinding.FragmentMainBinding

/**
 * A simple [Fragment] subclass.
 */

const val KEY_BB = "bb_key"
const val KEY_TB = "tb_key"
const val KEY_JK = "jk_key"
const val KEY_NILAI = "nilai_key"
const val KEY_HASIL = "hasil_key"

class MainFragment : Fragment() {
    lateinit var binding: FragmentMainBinding
    var beratBadan: Float = 0.00f
    var tinggiBadan: Float = 0.00f
    var jenisKelamin: String = ""
    var nilaiBMI: Float = 0.00f
    var hasilBMI: String = ""

//    companion object {
//
//    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        reset()

        if (savedInstanceState != null) {
            beratBadan = savedInstanceState.getFloat(KEY_BB)
            tinggiBadan = savedInstanceState.getFloat(KEY_TB)
            jenisKelamin = savedInstanceState.getString(KEY_JK).toString()
            nilaiBMI = savedInstanceState.getFloat(KEY_NILAI)
            hasilBMI = savedInstanceState.getString(KEY_HASIL).toString()

            binding.varNilaiBMI = nilaiBMI
            binding.varHasilBMI = hasilBMI

            when (hasilBMI) {
                "GEMUK" -> binding.tvHasil.setTextColor(resources.getColor(R.color.kategori_gemuk))
                "IDEAL" -> binding.tvHasil.setTextColor(resources.getColor(R.color.kategori_ideal))
                "KURUS" -> binding.tvHasil.setTextColor(resources.getColor(R.color.kategori_kurus))
            }

            updateNilai(nilaiBMI, hasilBMI)
            show()
        }

        binding.apply {
            btnHitung.setOnClickListener {
                if (etBB.text.toString().isEmpty() || etTB.text.toString().isEmpty() || !(rbPria.isChecked || rbWanita.isChecked)) {
                    Toast.makeText(activity, "Isian tidak valid!", Toast.LENGTH_SHORT).show()
                } else {
                    beratBadan = etBB.text.toString().toFloat()
                    tinggiBadan = etTB.text.toString().toFloat()
                    nilaiBMI = beratBadan / (tinggiBadan * tinggiBadan / 10000)
                    varNilaiBMI = nilaiBMI

                    if (rbPria.isChecked) {
                        jenisKelamin = "Pria"
                        if (nilaiBMI >= 27) {
                            hasilBMI = "GEMUK"
                        } else if (nilaiBMI >= 20.5) {
                            hasilBMI = "IDEAL"
                        } else {
                            hasilBMI = "KURUS"
                        }
                    } else if (rbWanita.isChecked) {
                        jenisKelamin = "Wanita"
                        if (nilaiBMI >= 25) {
                            hasilBMI = "GEMUK"
                        } else if (nilaiBMI >= 18.5) {
                            hasilBMI = "IDEAL"
                        } else {
                            hasilBMI = "KURUS"
                        }
                    }
                    varHasilBMI = hasilBMI

                    when (hasilBMI) {
                        "GEMUK" -> tvHasil.setTextColor(resources.getColor(R.color.kategori_gemuk))
                        "IDEAL" -> tvHasil.setTextColor(resources.getColor(R.color.kategori_ideal))
                        "KURUS" -> tvHasil.setTextColor(resources.getColor(R.color.kategori_kurus))
                    }

                    tvNilai.setText("Nilai BMI: $nilaiBMI")
                    tvHasil.setText(hasilBMI)
                    show()
                }
            }
            btnSaran.setOnClickListener {
                when (hasilBMI) {
                    "GEMUK" -> findNavController().navigate(R.id.action_mainFragment_to_gemukFragment)
                    "IDEAL" -> findNavController().navigate(R.id.action_mainFragment_to_idealFragment)
                    "KURUS" -> findNavController().navigate(R.id.action_mainFragment_to_kurusFragment)
                }
            }
            btnBagikan.setOnClickListener {
                val shareIntent = Intent()
                shareIntent.action = Intent.ACTION_SEND
                shareIntent.type = "text/plain"
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Berat saya $hasilBMI")
                shareIntent.putExtra(
                    Intent.EXTRA_TEXT,
                    "Berat badan: $beratBadan\nTinggi badan: $beratBadan\nJenis kelamin: $jenisKelamin\nNilai BMI: $nilaiBMI\nKategori: $hasilBMI"
                )
                shareIntent.putExtra(Intent.EXTRA_EMAIL, "mobpro.d3if@gmail.com")
                startActivity(Intent.createChooser(shareIntent, "Bagikan hasil BMI via..."))
            }
        }

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return NavigationUI.onNavDestinationSelected(item!!, view!!.findNavController())
                || super.onOptionsItemSelected(item)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putFloat(KEY_BB, beratBadan)
        outState.putFloat(KEY_TB, tinggiBadan)
        outState.putString(KEY_JK, jenisKelamin)
        outState.putFloat(KEY_NILAI, nilaiBMI)
        outState.putString(KEY_HASIL, hasilBMI)
        super.onSaveInstanceState(outState)
    }

    @SuppressLint("SetTextI18n")
    private fun updateNilai(f: Float, s: String) {
        binding.tvNilai.setText("Nilai BMI: $f")
        binding.tvHasil.setText(s)
    }

    private fun reset() {
        binding.divider.visibility = View.GONE
        binding.tvNilai.visibility = View.GONE
        binding.tvHasil.visibility = View.GONE
        binding.btnSaran.visibility = View.GONE
        binding.btnBagikan.visibility = View.GONE
    }

    private fun show() {
        binding.divider.visibility = View.VISIBLE
        binding.tvNilai.visibility = View.VISIBLE
        binding.tvHasil.visibility = View.VISIBLE
        binding.btnSaran.visibility = View.VISIBLE
        binding.btnBagikan.visibility = View.VISIBLE
    }
}
