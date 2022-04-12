package com.example.eshoppingassignment.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.eshoppingassignment.R
import com.example.eshoppingassignment.data.models.AddProductRequest
import com.example.eshoppingassignment.repo.AddProductViewModel
import com.example.eshoppingassignment.util.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddProductDialogFragment : DialogFragment() {
    private val viewModel: AddProductViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                AddProductContainer()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listenDataUpdates()
    }

    @Composable
    private fun AddProductContainer() {
        var title by remember { mutableStateOf("") }
        var description by remember { mutableStateOf("") }
        val focusRequester = remember { FocusRequester() }
        var isEmpty by remember { mutableStateOf(false) }

        Column(
            Modifier
                .fillMaxWidth()
                .height(300.dp)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(top = 8.dp),
                text = stringResource(R.string.add_product),
                fontSize = 20.sp,
                color = teal900
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .focusRequester(focusRequester),
                value = title,
                onValueChange = { title = it },
                label = { Text(stringResource(R.string.title), color = teal700) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = teal700,
                    unfocusedBorderColor = teal700,
                    cursorColor = black
                )
            )
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                value = description,
                onValueChange = { description = it },
                label = { Text(stringResource(R.string.description), color = teal700) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = teal700,
                    unfocusedBorderColor = teal700,
                    cursorColor = black
                )
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                onClick = {
                    if (title.isEmpty() || description.isEmpty()) {
                        isEmpty = true
                    } else {
                        isEmpty = false
                        viewModel.addProduct(AddProductRequest("", description, "", 0.0, title))
                        title = ""
                        description = ""
                        focusRequester.requestFocus()
                    }
                }, colors = ButtonDefaults.textButtonColors(
                    backgroundColor = teal900
                )
            ) {
                Text(stringResource(R.string.add), color = white)
            }

            if (isEmpty) {
                Text(
                    text = stringResource(R.string.fields_empty_msg),
                    color = MaterialTheme.colors.error,
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }
        }
    }

    companion object {
        fun create(): DialogFragment = AddProductDialogFragment()
    }

    private fun listenDataUpdates() {
        viewModel.getAddProductLiveDataLiveData().observe(viewLifecycleOwner) {
            when (it) {
                is Resource.ResourceSuccess -> {
                    val msg = getString(R.string.item_add_msg, it.data.id.toString())
                    Toast.makeText(requireContext(), msg, Toast.LENGTH_LONG).show()
                }
                is Resource.ResourceError -> {
                    Toast.makeText(requireContext(), it.error.message, Toast.LENGTH_LONG).show()
                }
                is Resource.ResourceLoading -> {
                }
            }
        }

    }
}