package com.example.mbg.feature.inputGizi.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.mbg.R
import com.example.mbg.ui.theme.*

@Composable
fun InputGiziScreen(
    viewModel: InputGiziViewModel,
    onNavigateToFormTambahItem: () -> Unit
) {

    val katalogMenu by viewModel.katalogMenu.collectAsState()
    val selectedItems by viewModel.selectedItems.collectAsState()

    val totalKalori by viewModel.totalKalori.collectAsState()
    val totalProtein by viewModel.totalProtein.collectAsState()
    val totalKarbo by viewModel.totalKarbo.collectAsState()
    val totalLemak by viewModel.totalLemak.collectAsState()

    var showEditDialog by remember { mutableStateOf(false) }
    var showUploadSuccessDialog by remember { mutableStateOf(false) }

    val handleSaveSelection =
        { newSelection: Set<com.example.mbg.feature.inputGizi.domain.model.MenuItem> ->
            val toAdd = newSelection - selectedItems.toSet()
            val toRemove = selectedItems.toSet() - newSelection

            toAdd.forEach { viewModel.toggleItemSelection(it, true) }
            toRemove.forEach { viewModel.toggleItemSelection(it, false) }
        }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 24.dp)
                .statusBarsPadding()
        ) {

            Text(
                "Menu & Gizi Hari Ini",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Text(
                "Dapur MBG - Pusat Monitoring",
                fontSize = 14.sp,
                color = TextGray
            )
        }

        Divider(color = Color(0xFFF3F4F6), thickness = 1.dp)

        Box(modifier = Modifier.fillMaxSize()) {

            if (katalogMenu.isEmpty()) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.icon_bowlmakanan),
                        contentDescription = null,
                        modifier = Modifier.size(240.dp)
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        "Belum ada menu yang diinput.",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        "Silahkan masukkan daftar menu dan informasi gizi untuk agar mitra dapat memantau distribusi makanan.",
                        fontSize = 14.sp,
                        color = TextGray,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(48.dp))

                    Button(
                        onClick = onNavigateToFormTambahItem,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = FoundationGreen),
                        shape = RoundedCornerShape(8.dp)
                    ) {

                        Row(verticalAlignment = Alignment.CenterVertically) {

                            Icon(
                                painter = painterResource(id = R.drawable.lingkaran_tambah),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(18.dp)
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                "Tambah Menu",
                                color = Color.White,
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }

            } else if (selectedItems.isEmpty()) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.icon_bowlmakanan),
                        contentDescription = null,
                        modifier = Modifier.size(240.dp)
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        "Belum ada menu yang diinput\nhari ini.",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        "Silahkan pilih daftar menu dan informasi gizi untuk hari ini agar mitra dapat memantau distribusi makanan.",
                        fontSize = 14.sp,
                        color = TextGray,
                        textAlign = TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(48.dp))

                    MenuHariIniDropdown(
                        options = katalogMenu,
                        selectedItems = selectedItems,
                        onSaveSelection = handleSaveSelection,
                        onAddNewItem = onNavigateToFormTambahItem
                    )
                }

            } else {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp)
                        .verticalScroll(rememberScrollState())
                ) {

                    Spacer(modifier = Modifier.height(16.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, Color(0xFFE5E7EB)),
                        shape = RoundedCornerShape(16.dp)
                    ) {

                        Column(modifier = Modifier.padding(16.dp)) {

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Row(verticalAlignment = Alignment.CenterVertically) {

                                    Icon(
                                        painter = painterResource(id = R.drawable.sendokgarpu_icon),
                                        contentDescription = null,
                                        tint = FoundationGreen,
                                        modifier = Modifier.size(20.dp)
                                    )

                                    Spacer(modifier = Modifier.width(8.dp))

                                    Text(
                                        "Rincian Menu & Kandungan",
                                        fontSize = 14.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }

                                Text(
                                    "Edit",
                                    fontSize = 14.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF3B82F6),
                                    textDecoration = TextDecoration.Underline,
                                    modifier = Modifier.clickable { showEditDialog = true }
                                )
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            selectedItems.forEach { item ->

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {

                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier.weight(1f)
                                    ) {

                                        AsyncImage(
                                            model = item.foto_url,
                                            contentDescription = null,
                                            contentScale = ContentScale.Crop,
                                            modifier = Modifier
                                                .size(48.dp)
                                                .clip(RoundedCornerShape(12.dp))
                                                .background(Color(0xFFF3F4F6))
                                        )

                                        Spacer(modifier = Modifier.width(12.dp))

                                        Column {

                                            Text(
                                                item.nama_item,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 14.sp
                                            )

                                            Text(
                                                "Menu Terpilih",
                                                fontSize = 12.sp,
                                                color = TextGray
                                            )
                                        }
                                    }

                                    Column(horizontalAlignment = Alignment.End) {

                                        Text(
                                            "${item.kalori.toInt()} kkal",
                                            fontWeight = FontWeight.Bold
                                        )

                                        Text(
                                            "Porsi: ${item.berat_gram.toInt()}gr",
                                            fontSize = 12.sp,
                                            color = TextGray
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, Color(0xFFE5E7EB)),
                        shape = RoundedCornerShape(16.dp)
                    ) {

                        Column(modifier = Modifier.padding(16.dp)) {

                            Text(
                                "Ringkasan Gizi Hari Ini",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {

                                GiziBoxCard(
                                    modifier = Modifier.weight(1f),
                                    label = "TOTAL KALORI",
                                    value = totalKalori.toInt().toString(),
                                    unit = "kkal"
                                )

                                GiziBoxCard(
                                    modifier = Modifier.weight(1f),
                                    label = "PROTEIN",
                                    value = totalProtein.toInt().toString(),
                                    unit = "g"
                                )
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp)
                            ) {

                                GiziBoxCard(
                                    modifier = Modifier.weight(1f),
                                    label = "KARBOHIDRAT",
                                    value = totalKarbo.toInt().toString(),
                                    unit = "g"
                                )

                                GiziBoxCard(
                                    modifier = Modifier.weight(1f),
                                    label = "SERAT/LEMAK",
                                    value = totalLemak.toInt().toString(),
                                    unit = "g"
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            viewModel.publishMenuHariIni {
                                showUploadSuccessDialog = true
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = FoundationGreen),
                        shape = RoundedCornerShape(8.dp)
                    ) {

                        Text(
                            "Upload",
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))
                }
            }

            if (showEditDialog) {

                EditMenuDialog(
                    katalogMenu = katalogMenu,
                    selectedItems = selectedItems,
                    onSaveSelection = handleSaveSelection,
                    onAddNewItem = {
                        showEditDialog = false
                        onNavigateToFormTambahItem()
                    },
                    onDismiss = { showEditDialog = false }
                )
            }

            if (showUploadSuccessDialog) {

                SuccessDialog(
                    title = "Menu Berhasil\nDiunggah",
                    message = "Menu yang kamu tambahkan\ntelah berhasil disimpan.",
                    onDismiss = { showUploadSuccessDialog = false }
                )
            }
        }
    }
}


// dropdown n dialog


@Composable
fun MenuHariIniDropdown(
    options: List<com.example.mbg.feature.inputGizi.domain.model.MenuItem>,
    selectedItems: List<com.example.mbg.feature.inputGizi.domain.model.MenuItem>,
    onSaveSelection: (Set<com.example.mbg.feature.inputGizi.domain.model.MenuItem>) -> Unit,
    onAddNewItem: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var tempSelected by remember { mutableStateOf(selectedItems.toSet()) }

    LaunchedEffect(expanded) {
        if (expanded) tempSelected = selectedItems.toSet()
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, FoundationGreen),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier
                .fillMaxWidth()
                .clickable { expanded = !expanded }
                .padding(horizontal = 16.dp, vertical = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text("Tambah Menu Hari ini", color = Color.Black, fontSize = 14.sp)
                Icon(
                    if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    null,
                    tint = Color.Black,
                    modifier = Modifier.size(20.dp)
                )
            }

            if (expanded) {
                Divider(color = Color(0xFFF3F4F6), thickness = 1.dp)
                Column(
                    modifier = Modifier
                        .heightIn(max = 250.dp)
                        .verticalScroll(rememberScrollState())
                        .padding(top = 8.dp)
                ) {
                    options.forEach { item ->
                        val isChecked = tempSelected.contains(item)
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                tempSelected =
                                    if (isChecked) tempSelected - item else tempSelected + item
                            }
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = isChecked, onCheckedChange = { checked ->
                                    tempSelected =
                                        if (checked) tempSelected + item else tempSelected - item
                                }, colors = CheckboxDefaults.colors(checkedColor = FoundationGreen)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(item.nama_item, fontSize = 14.sp, color = Color.Black)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Tambahkan Menu",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF3B82F6),
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clickable { expanded = false; onAddNewItem() })
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { onSaveSelection(tempSelected); expanded = false },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = FoundationGreen),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        "Simpan",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
fun EditMenuDialog(
    katalogMenu: List<com.example.mbg.feature.inputGizi.domain.model.MenuItem>,
    selectedItems: List<com.example.mbg.feature.inputGizi.domain.model.MenuItem>,
    onSaveSelection: (Set<com.example.mbg.feature.inputGizi.domain.model.MenuItem>) -> Unit,
    onAddNewItem: () -> Unit,
    onDismiss: () -> Unit
) {
    var tempSelected by remember { mutableStateOf(selectedItems.toSet()) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(1.dp, FoundationGreen),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Tambah Menu Hari ini", color = Color.Black, fontSize = 14.sp)
                    Icon(
                        Icons.Default.KeyboardArrowUp,
                        null,
                        tint = Color.Black,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Divider(color = Color(0xFFF3F4F6), thickness = 1.dp)

                Column(
                    modifier = Modifier
                        .heightIn(max = 250.dp)
                        .verticalScroll(rememberScrollState())
                        .padding(top = 8.dp)
                ) {
                    katalogMenu.forEach { item ->
                        val isChecked = tempSelected.contains(item)
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                tempSelected =
                                    if (isChecked) tempSelected - item else tempSelected + item
                            }
                            .padding(horizontal = 16.dp, vertical = 4.dp),
                            verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = isChecked, onCheckedChange = { checked ->
                                    tempSelected =
                                        if (checked) tempSelected + item else tempSelected - item
                                }, colors = CheckboxDefaults.colors(checkedColor = FoundationGreen)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(item.nama_item, fontSize = 14.sp, color = Color.Black)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Tambahkan Menu",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF3B82F6),
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clickable { onDismiss(); onAddNewItem() })
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = { onSaveSelection(tempSelected); onDismiss() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = FoundationGreen),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        "Simpan",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}


@Composable
fun SuccessDialog(title: String, message: String, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.centang_succes),
                    contentDescription = "Success",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(72.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    title,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    lineHeight = 28.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    message,
                    fontSize = 14.sp,
                    color = TextGray,
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = onDismiss,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = FoundationGreen),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        "Ok", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun GiziBoxCard(modifier: Modifier = Modifier, label: String, value: String, unit: String) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFEAF5E9))
            .padding(16.dp)
    ) {
        Column {
            Text(label, fontSize = 10.sp, fontWeight = FontWeight.Bold, color = TextGray)
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.Bottom) {
                Text(value, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    unit,
                    fontSize = 12.sp,
                    color = TextGray,
                    modifier = Modifier.padding(bottom = 3.dp)
                )
            }
        }
    }
}