package com.example.anshu.android_226.Unit_4.employee_management

import android.R
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EmployeeViewModel(private val dao: EmployeeDao) : ViewModel() {

    val employeeList: StateFlow<List<EmployeeEntity>> = dao.getAllEmployees()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addEmployee(name: String, department: String, salary: String) {
        if (name.isBlank() || department.isBlank() || salary.isBlank()) return

        viewModelScope.launch {
            dao.insertEmployee(
                EmployeeEntity(
                    name = name.trim(),
                    department = department.trim(),
                    salary = salary.trim()
                )
            )
        }
    }

    fun deleteEmployee(employee: EmployeeEntity) {
        viewModelScope.launch {
            dao.deleteEmployee(employee)
        }
    }

    fun updateEmployee(employee: EmployeeEntity) {
        viewModelScope.launch {
            dao.insertEmployee(employee) // Room's REPLACE strategy will update it
        }
    }
}

class EmployeeViewModelFactory(private val dao: EmployeeDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EmployeeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EmployeeViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class EmployeeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val database = EmployeeDatabase.getDatabase(applicationContext)
        val factory = EmployeeViewModelFactory(database.employeeDao())

        setContent {
            MaterialTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    val viewModel: EmployeeViewModel = viewModel(factory = factory)
                    EmployeeScreen(viewModel = viewModel)
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeScreen(viewModel: EmployeeViewModel) {
    val employees by viewModel.employeeList.collectAsStateWithLifecycle()
    val context = LocalContext.current

    var nameInput by remember { mutableStateOf("") }
    var departmentInput by remember { mutableStateOf("") }
    var salaryInput by remember { mutableStateOf("") }
    var editingEmployee by remember { mutableStateOf<EmployeeEntity?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Employee Management") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF1A237E),
                    titleContentColor = Color(0xFFFFFFFF)
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {

        // Input Form
        Card(
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    if (editingEmployee == null) "Add New Employee" else "Edit Employee",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = nameInput,
                    onValueChange = { nameInput = it },
                    label = { Text("Employee Name") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Person, contentDescription = "Person Icon")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = departmentInput,
                    onValueChange = { departmentInput = it },
                    label = { Text("Department (e.g., HR)") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.AccountBox, contentDescription = "Department Icon")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = salaryInput,
                    onValueChange = { salaryInput = it },
                    label = { Text("Salary") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = "Salary Icon")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        if (nameInput.isBlank() || departmentInput.isBlank() || salaryInput.isBlank()) {
                            Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
                            return@Button
                        }
                        
                        if (editingEmployee == null) {
                            viewModel.addEmployee(nameInput, departmentInput, salaryInput)
                            Toast.makeText(context, "Employee Saved", Toast.LENGTH_SHORT).show()
                        } else {
                            viewModel.updateEmployee(
                                editingEmployee!!.copy(
                                    name = nameInput.trim(),
                                    department = departmentInput.trim(),
                                    salary = salaryInput.trim()
                                )
                            )
                            editingEmployee = null
                            Toast.makeText(context, "Employee Updated", Toast.LENGTH_SHORT).show()
                        }
                        nameInput = ""
                        departmentInput = ""
                        salaryInput = ""
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(if (editingEmployee == null) "Save Employee" else "Update Employee")
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Stored Employees (${employees.size})",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(8.dp))

        // List Displaying Database Entries
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(employees, key = { it.id }) { employee ->
                EmployeeRowItem(
                    employee = employee,
                    onEdit = {
                        editingEmployee = employee
                        nameInput = employee.name
                        departmentInput = employee.department
                        salaryInput = employee.salary
                    },
                    onDelete = { viewModel.deleteEmployee(employee) }
                )
            }
        }
    }
}
}

@Composable
fun EmployeeRowItem(
    employee: EmployeeEntity,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = employee.name, style = MaterialTheme.typography.titleMedium)
                Text(
                    text = "Dept: ${employee.department} | Salary: ${employee.salary}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Row {
                IconButton(onClick = onEdit) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Record",
                        tint = MaterialTheme.colorScheme.primary
                    )
                }
                IconButton(onClick = onDelete) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete Record",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}
