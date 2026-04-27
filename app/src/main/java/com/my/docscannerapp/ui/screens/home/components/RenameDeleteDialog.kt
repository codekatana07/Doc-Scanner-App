package com.my.docscannerapp.ui.screens.home.components

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.my.docscannerapp.R
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import com.my.docscannerapp.ui.viewmodels.PdfViewModel
import com.my.docscannerapp.utils.deleteFile
import com.my.docscannerapp.utils.getFileUri
import com.my.docscannerapp.utils.renameFile
import java.util.Date

@Composable
fun RenameDeleteDialog(
    pdfViewModel: PdfViewModel
) {
    var newNameText by remember(pdfViewModel.currentPdfEntity) { mutableStateOf(pdfViewModel.currentPdfEntity?.name?: "") }

    var context = LocalContext.current

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
                        IconButton(
                            onClick = {
                                pdfViewModel.currentPdfEntity?.let{
                                    pdfViewModel.showRenameDialog = false
                                    val getFileUri = getFileUri(context, it.name)
                                    val ShareIntent = Intent(Intent.ACTION_SEND)
                                    ShareIntent.type = "application/pdf"
                                    ShareIntent.putExtra(Intent.EXTRA_STREAM,getFileUri)
                                    ShareIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                                    context.startActivity(Intent.createChooser(ShareIntent,"Share"))
                                }
//                                pdfViewModel.currentPdfEntity = pdfEntity
//                                pdfViewModel.showRenameDialog = true
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Share,
                                contentDescription = "Share",
                                modifier = Modifier.padding(2.dp)
                                    .size(40.dp),
                                tint = Color.Red
                            )
                        }
//                        TextButton(
//                            onClick = {
//                                pdfViewModel.showRenameDialog = false
//                            }
//                        ) {
//                            Text("Cancel")
//                        }

                        TextButton(
                            onClick = {
                                pdfViewModel.currentPdfEntity?.let{
                                    pdfViewModel.showRenameDialog = false
                                    if(deleteFile(context,it.name)){
                                        pdfViewModel.deletePdf(it)
                                    }else{

                                    }
                                }

                            }
                        ) {
                            Text("Delete", color = MaterialTheme.colorScheme.error)
                        }

                        Button(
                            onClick = {
                                pdfViewModel.currentPdfEntity?.let { pdf->
                                    //
                                    if(!pdf.name.equals(newNameText,true)){
                                        pdfViewModel.showRenameDialog = false
                                        renameFile(
                                            context,
                                            pdf.name,
                                            newNameText
                                        )
                                        val updatePdf = pdf.copy(name=newNameText, lastModifiedDate = Date())
                                        pdfViewModel.updatePdf(updatePdf)
                                    } else{
                                        pdfViewModel.showRenameDialog = false
                                    }
                                }



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