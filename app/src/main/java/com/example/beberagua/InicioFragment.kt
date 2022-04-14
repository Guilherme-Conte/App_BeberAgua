package com.example.beberagua

import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.text.NumberFormat
import android.os.Bundle
import android.provider.AlarmClock
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TimePicker
import android.widget.Toast
import com.example.beberagua.databinding.FragmentInicioBinding
import com.example.beberagua.model.CalcularIngestao
import java.util.*


class InicioFragment : Fragment() {

    private lateinit var binding: FragmentInicioBinding
    private lateinit var calcularIngestao: CalcularIngestao
    private var resultadoMl = 0.0
    lateinit var timePickerDialog: TimePickerDialog
    lateinit var calendario: Calendar
    var horaAtual = 0
    var minutosAtual = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentInicioBinding.inflate(layoutInflater, container, false)

        calcularIngestao = CalcularIngestao()



        binding.bdCalcular.setOnClickListener {

            val peso = binding.editPeso.text.toString()
            val idade = binding.editIdade.text.toString()

            if (peso.isEmpty() || idade.isEmpty()) {
                Toast.makeText(
                    context, "Preencha todos os campos!", Toast.LENGTH_LONG
                ).show()
            }else{
                val peso = binding.editPeso.text.toString().toDouble()
                val idade = binding.editIdade.text.toString().toInt()
                val formatar = NumberFormat.getNumberInstance(Locale("pt", "BR"))
                formatar.isGroupingUsed = false

                calcularIngestao.calcularTotalMl(peso, idade)
                resultadoMl = calcularIngestao.resultadoML()
                binding.textResultadoML.text = formatar.format(resultadoMl) + " " + "ml"

            }

            binding.icRedefinir.setOnClickListener {
                val alertDialog = AlertDialog.Builder(context)
                alertDialog.setTitle(R.string.DialogTitulo)
                    .setMessage(R.string.DialogDesc)
                    .setPositiveButton("Sim") { dialogInterface, i ->
                        binding.editPeso.setText("")
                        binding.editIdade.setText("")
                        binding.textResultadoML.text = ""
                    }
                alertDialog.setNegativeButton("Cancelar") { dialogInterface, i ->

                }
                val dialog = alertDialog.create()
                dialog.show()
            }

            binding.btDefinirLembrete.setOnClickListener {

                calendario = Calendar.getInstance()
                horaAtual = calendario.get(Calendar.HOUR_OF_DAY)
                minutosAtual = calendario.get(Calendar.MINUTE)
                timePickerDialog = TimePickerDialog(context, { timePicker: TimePicker,
                                                            hourOfDay: Int, minutes: Int ->
                    binding.textHora.text = String.format("%02d", hourOfDay)
                    binding.textMinutos.text = String.format("%02d", minutes)
                }, horaAtual, minutosAtual, true)
                timePickerDialog.show()
            }

            binding.btAlarme.setOnClickListener {

                val hora = binding.textHora.text.toString()
                val minuto = binding.textHora.text.toString()

                if (!hora.isEmpty() && !minuto.isEmpty()){

                    val intent = Intent(AlarmClock.ACTION_SET_ALARM)
                    intent.putExtra(AlarmClock.EXTRA_HOUR, hora.toInt())
                    intent.putExtra(AlarmClock.EXTRA_MINUTES, minuto.toInt())
                    intent.putExtra(AlarmClock.EXTRA_MESSAGE, "Hora de beber água!")
                    startActivity(intent)

                }
            }
        }
        return binding.root
    }


}
