package com.pdfit.scanner.ui.screens.home.components

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.pdfit.scanner.data.models.pdfEntity
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material3.MaterialTheme

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import com.pdfit.scanner.utils.getFileUri
import com.pdfit.scanner.ui.viewmodels.PdfViewModel
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun pdfLayout(pdfEntity: pdfEntity, pdfViewModel: PdfViewModel) {
    val context = LocalContext.current
    val activity = LocalContext.current as Activity
    val formattedDate = SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault())
        .format(pdfEntity.lastModifiedDate)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(10.dp),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(6.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
            onClick = {
                val getFileUri = getFileUri(context, pdfEntity.name)
                val browserIntent = Intent(Intent.ACTION_VIEW,getFileUri)
                browserIntent.setDataAndType(getFileUri, "application/pdf") //MIME type set
                browserIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                activity.startActivity(browserIntent)
            }
            )
        {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalAlignment= Alignment.CenterVertically,
            ) {
                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f), shape = CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.PictureAsPdf,
                        contentDescription = "PDF",
                        modifier = Modifier.size(44.dp),
                        tint = Color(0xFFF5F5F5)

                    )
                }

                Spacer(modifier = Modifier.width(16.dp))
                Column(
                    modifier = Modifier.weight(1f).padding(8.dp),
//                    verticalArrangement =  Arrangement.Top,
//                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = pdfEntity.name,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.White
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "${pdfEntity.size} • ${formattedDate}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
//                    Text(
//                        text = "Size: ${pdfEntity.size}",
//                        style = MaterialTheme.typography.bodyMedium
//                    )
//                    Text(
//                        text = "Date: ${SimpleDateFormat("dd-MMM-yyyy HH:mm:ss",Locale.getDefault()).format(pdfEntity.lastModifiedDate)}",
//                        style = MaterialTheme.typography.bodyMedium
//                    )
                }

                IconButton(
                    onClick = {
                        pdfViewModel.currentPdfEntity = pdfEntity
                        pdfViewModel.showRenameDialog = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More",
                        modifier = Modifier.padding(2.dp)
                            .size(60.dp),
                        tint = Color.White
                    )
                }


            }
        }

}