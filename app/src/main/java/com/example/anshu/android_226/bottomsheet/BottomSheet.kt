package com.example.anshu.android_226.bottomsheet
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetExample() {

    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(onClick = {
            showBottomSheet = true
        }) {
            Text("Show Bottom Sheet")
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = "Student Details",
                    style = MaterialTheme.typography.headlineSmall
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text("Name: Anshu")
                Text("Course: B.Tech CSE")
                Text("Semester: 7th")

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        showBottomSheet = false
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Close")
                }
            }
        }
    }
}