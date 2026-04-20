package com.my.docscannerapp.screens.home.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.my.docscannerapp.R
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import com.my.docscannerapp.viewmodels.pdfViewModel

@Composable
fun RenameDeleteDialog(
    pdfViewModel: pdfViewModel
) {
    var newNameText by remember { mutableStateOf("") }

    if (pdfViewModel.showRenameDialog) {

        Dialog(
            onDismissRequest = {
                pdfViewModel.showRenameDialog = false
            }
        ) {
            Surface(
                shape = MaterialTheme.shapes.medium,
                color = MaterialTheme.colorScheme.surface
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = stringResource(id = R.string.rename_pdf),
                        style = MaterialTheme.typography.headlineSmall
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = newNameText,
                        onValueChange = { newNameText = it },
                        label = {
                            Text(text = stringResource(id = R.string.pdf_name))
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {

                        TextButton(
                            onClick = {
                                pdfViewModel.showRenameDialog = false
                            }
                        ) {
                            Text("Cancel")
                        }

                        TextButton(
                            onClick = {
                                pdfViewModel.deletePdf()   // implement in VM
                                pdfViewModel.showRenameDialog = false
                            }
                        ) {
                            Text("Delete", color = MaterialTheme.colorScheme.error)
                        }

                        Button(
                            onClick = {
                                pdfViewModel.renamePdf(newNameText) // implement in VM
                                pdfViewModel.showRenameDialog = false
                            }
                        ) {
                            Text("Rename")
                        }
                    }
                }
            }
        }
    }
}