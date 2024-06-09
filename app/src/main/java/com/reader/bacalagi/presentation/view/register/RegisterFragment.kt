package com.reader.bacalagi.presentation.view.register

import androidx.fragment.app.viewModels
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.navigation.fragment.findNavController
import com.reader.bacalagi.R
import com.reader.bacalagi.base.BaseFragment
import com.reader.bacalagi.databinding.FragmentRegisterBinding

class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): FragmentRegisterBinding {
        return FragmentRegisterBinding.inflate(inflater, container, false)
    }

    override fun initUI() {
        binding.dropdownProvinsi.apply {
            val items = listOf("Item 1", "Item 2", "Item 3", "Item 4")
            val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, items)
            (binding.tfProvinsi as? AutoCompleteTextView)?.setAdapter(adapter)

            binding.tfProvinsi.setOnItemClickListener { parent, view, position, id ->
                val selectedItem = parent.getItemAtPosition(position).toString()

                binding.tfProvinsi.setText(selectedItem, false)
            }
        }

        binding.dropdownKota.apply {
            val items = listOf("Item 1", "Item 2", "Item 3", "Item 4")
            val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, items)
            (binding.tfKota as? AutoCompleteTextView)?.setAdapter(adapter)

            binding.tfKota.setOnItemClickListener { parent, view, position, id ->
                val selectedItem = parent.getItemAtPosition(position).toString()

                binding.tfKota.setText(selectedItem, false)
            }
        }

        binding.dropdownKecamatan.apply {
            val items = listOf("Item 1", "Item 2", "Item 3", "Item 4")
            val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, items)
            (binding.tfKecamatan as? AutoCompleteTextView)?.setAdapter(adapter)

            binding.tfKecamatan.setOnItemClickListener { parent, view, position, id ->
                val selectedItem = parent.getItemAtPosition(position).toString()

                binding.tfKecamatan.setText(selectedItem, false)
            }
        }

        binding.dropdownKelurahan.apply {
            val items = listOf("Item 1", "Item 2", "Item 3", "Item 4")
            val adapter = ArrayAdapter(requireContext(), R.layout.dropdown_item, items)
            (binding.tfKelurahan as? AutoCompleteTextView)?.setAdapter(adapter)

            binding.tfKelurahan.setOnItemClickListener { parent, view, position, id ->
                val selectedItem = parent.getItemAtPosition(position).toString()

                binding.tfKelurahan.setText(selectedItem, false)
            }
        }
    }

    override fun initAppBar() {
        binding.mainToolbar.apply {
            mainToolbar.title = "Additional Information"
            mainToolbar.setNavigationIcon(R.drawable.ic_back)

            mainToolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }
}