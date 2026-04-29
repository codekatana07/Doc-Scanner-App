package com.pdfit.scanner.ui.screens.home.components
import com.pdfit.scanner.R
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.Dialog
import com.pdfit.scanner.ui.viewmodels.PdfViewModel
import com.pdfit.scanner.utils.deleteFile
import com.pdfit.scanner.utils.getFileUri
import com.pdfit.scanner.utils.renameFile
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
                            }
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(60.dp)
                                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), shape = CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Share,
                                    contentDescription = "Share",
                                    modifier = Modifier.padding(2.dp)
                                        .size(30.dp)
                                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.2f), shape = CircleShape),
                                    tint = Color.Red
                                )
                            }

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



                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = Color.White
                            )
                        ) {
                            Text("Rename", color = Color.White)
                        }
                    }
                }
            }
        }
    }
}