package com.pdfit.scanner.ui.screens.home

import android.app.Activity
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import com.pdfit.scanner.R
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.google.mlkit.vision.documentscanner.GmsDocumentScannerOptions
import com.google.mlkit.vision.documentscanner.GmsDocumentScanning
import com.google.mlkit.vision.documentscanner.GmsDocumentScanningResult
import com.pdfit.scanner.data.models.pdfEntity
import com.pdfit.scanner.ui.screens.common.ErrorScreen
import com.pdfit.scanner.ui.screens.common.LoadingDialog
import com.pdfit.scanner.ui.screens.home.components.RenameDeleteDialog
import com.pdfit.scanner.ui.screens.home.components.pdfLayout
import com.pdfit.scanner.utils.copyPdfFileToAppDirectory
import com.pdfit.scanner.utils.showToast
import com.pdfit.scanner.ui.viewmodels.PdfViewModel
import com.pdfit.scanner.utils.getFileSize
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(pdfViewModel:PdfViewModel) {

    LoadingDialog(pdfViewModel=pdfViewModel)
    RenameDeleteDialog(pdfViewModel=pdfViewModel)
    val activity = LocalContext.current as Activity
    val context = LocalContext.current

    val pdfList by pdfViewModel.pdfStateFlow.collectAsState()

    val scannerLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartIntentSenderForResult() )
        {result ->
            if(result.resultCode == Activity.RESULT_OK){
                val scanningResult =
                    GmsDocumentScanningResult.fromActivityResultIntent(result.data)

                scanningResult?.pdf?.let { pdf ->
                    Log.d("pdfName", pdf.uri.lastPathSegment.toString())
                    val date = Date()
                    val fileName = SimpleDateFormat(

                        "dd-MMM-yyyy HH:mm:ss",
                        Locale.getDefault()
                    ).format(date) + ".pdf"

                    copyPdfFileToAppDirectory(context,pdf.uri,fileName)
                    val pdfEntity = pdfEntity(UUID.randomUUID().toString(),fileName,getFileSize(context,fileName),date)
                    pdfViewModel.insertPdf(pdfEntity)


                }
            }


        }

    //Initialise ML kit, most imp
    val scanner = remember {
        GmsDocumentScanning.getClient(
            GmsDocumentScannerOptions.Builder()
                .setGalleryImportAllowed(true)
                .setResultFormats(GmsDocumentScannerOptions.RESULT_FORMAT_PDF)
                .setScannerMode(GmsDocumentScannerOptions.SCANNER_MODE_FULL)
                .build()
        )
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.app_name))
                }, actions = {
                    Switch(
                        checked = pdfViewModel.isDarkMode,
                        onCheckedChange = {
                            pdfViewModel.isDarkMode = it
                        },
                        modifier = Modifier.padding(end = 8.dp),
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = MaterialTheme.colorScheme.primary,
                            uncheckedThumbColor = Color.Gray,
                            uncheckedTrackColor = Color.LightGray
                        )
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Red,
                    titleContentColor = Color.White
                )
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(onClick = {
                scanner.getStartScanIntent(activity).addOnSuccessListener {
                    scannerLauncher.launch(
                        IntentSenderRequest.Builder(it).build()
                    )
                }.addOnFailureListener {
                    it.printStackTrace()
                    context.showToast(it.message.toString())
                }
            }, text = {
                Text(text= stringResource(R.string.scan))
            }, icon={
                Icon(painter = painterResource(R.drawable.outline_add_a_photo_24), contentDescription = "camera" )
            },
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = Color.White
            )
        }
    ) { paddingValues ->
        if(pdfList.isEmpty()){
            ErrorScreen(message = "NO PDF")
        }
        else {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(paddingValues)
            ) {
                items(items = pdfList, key = { pdfEntity ->
                    pdfEntity.id
                }
                ) { pdfEntity ->
                    pdfLayout(pdfEntity = pdfEntity, pdfViewModel = pdfViewModel)
                }
            }
        }
    }
}