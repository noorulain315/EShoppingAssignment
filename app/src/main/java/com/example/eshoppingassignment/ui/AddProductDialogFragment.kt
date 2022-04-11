package com.example.eshoppingassignment.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import com.example.eshoppingassignment.repo.ProductViewModel
import com.example.eshoppingassignment.util.black
import com.example.eshoppingassignment.util.teal700
import com.example.eshoppingassignment.util.teal900
import com.example.eshoppingassignment.util.white
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddProductDialogFragment : DialogFragment() {
    private val viewModel: ProductViewModel by viewModels()
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
    }

    @Composable
    private fun AddProductContainer() {
        var title by remember { mutableStateOf("") }
        var description by remember { mutableStateOf("") }
        val focusRequester = remember { FocusRequester() }

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
                    viewModel.addProduct(AddProductRequest("", description, "", 0.0, title))
                    title = ""
                    description = ""
                    focusRequester.requestFocus()
                }, colors = ButtonDefaults.textButtonColors(
                    backgroundColor = teal900
                )
            ) {
                Text(stringResource(R.string.add), color = white)
            }
        }
    }

    companion object {
        fun create(): DialogFragment = AddProductDialogFragment()
    }
}