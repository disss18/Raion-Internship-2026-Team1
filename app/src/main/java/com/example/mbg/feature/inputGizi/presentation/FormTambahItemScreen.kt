package com.example.mbg.feature.inputGizi.presentation

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import com.example.mbg.R
import com.example.mbg.core.ui.component.button.PrimaryButton
import com.example.mbg.ui.theme.*

data class GiziItem(
    val id: String = java.util.UUID.randomUUID().toString(),
    val jenis: String,
    val jumlah: String,
    val satuan: String
)

@Composable
fun FormTambahItemScreen(
    onBackClick: () -> Unit,
    onSimpanClick: (kategori: String, namaMenu: String, satuanPorsi: String, jumlahPorsi: String, giziList: List<GiziItem>, imageUri: Uri, deskripsi: String) -> Unit
) {
    var selectedKategori by remember { mutableStateOf("") }
    var namaMenu by remember { mutableStateOf("") }
    var selectedSatuanPorsi by remember { mutableStateOf("") }
    var jumlahPorsi by remember { mutableStateOf("") }
    var selectedGizi by remember { mutableStateOf("") }
    var jumlahGizi by remember { mutableStateOf("") }
    var selectedSatuanGizi by remember { mutableStateOf("") }
    var giziList by remember { mutableStateOf(listOf<GiziItem>()) }
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var deskripsi by remember { mutableStateOf("") }

    var showSuccessDialog by remember { mutableStateOf(false) }

    val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? -> imageUri = uri }

    val isSimpanGiziEnabled = selectedGizi.isNotEmpty() && jumlahGizi.isNotEmpty() && selectedSatuanGizi.isNotEmpty()
    val isSubmitAllEnabled = namaMenu.isNotEmpty() && jumlahPorsi.isNotEmpty() && giziList.isNotEmpty() && imageUri != null

    Column(modifier = Modifier.fillMaxSize().background(FoundationGreen)) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 24.dp).statusBarsPadding()
        ) {
            Icon(Icons.Default.ArrowBack, "Back", tint = Color.White, modifier = Modifier.clickable { onBackClick() })
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text("Input gizi & makanan", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Text("Masukkan data makanan yang disiapkan", fontSize = 12.sp, color = Color.White)
            }
        }

        Box(modifier = Modifier.fillMaxSize().clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)).background(Color.White)) {
            Column(
                modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                CardSection(title = "Informasi Menu", iconResId = R.drawable.sendokgarpu_icon) {
                    FormLabel("Kategori Menu")
                    DynamicDropdown(placeholder = "Pilih kategori", options = listOf("Makanan pokok", "Lauk Protein", "Sayur", "Buah", "Tambahan"), selectedValue = selectedKategori, onValueChange = { selectedKategori = it })
                    Spacer(modifier = Modifier.height(12.dp))
                    FormLabel("Nama Menu")
                    GrayTextField(value = namaMenu, onValueChange = { namaMenu = it }, placeholder = "Masukkan menu")
                }

                CardSection(title = "Informasi Porsi", iconResId = R.drawable.porsi_icon) {
                    FormLabel("Satuan")
                    DynamicDropdown(placeholder = "Pilih satuan", options = listOf("Gram (g)", "Porsi", "Potong", "Buah"), selectedValue = selectedSatuanPorsi, onValueChange = { selectedSatuanPorsi = it })
                    Spacer(modifier = Modifier.height(12.dp))
                    FormLabel("Jumlah")
                    GrayTextField(
                        value = jumlahPorsi,
                        onValueChange = { jumlahPorsi = it },
                        placeholder = "150",
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                }

                CardSection(title = "Informasi Gizi", iconResId = R.drawable.informasigizi_icon) {
                    FormLabel("Gizi")
                    DynamicDropdown(placeholder = "Pilih gizi", options = listOf("Kalori", "Karbohidrat", "Protein", "Lemak", "Serat"), selectedValue = selectedGizi, onValueChange = { selectedGizi = it })
                    Spacer(modifier = Modifier.height(12.dp))
                    FormLabel("Jumlah")
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Box(modifier = Modifier.weight(1f)) {
                            GrayTextField(
                                value = jumlahGizi,
                                onValueChange = { jumlahGizi = it },
                                placeholder = "150",
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                        }
                        Box(modifier = Modifier.weight(1f)) { DynamicDropdown(placeholder = "Satuan", options = listOf("Gram (g)", "Kkal"), selectedValue = selectedSatuanGizi, onValueChange = { selectedSatuanGizi = it }) }
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    Button(
                        onClick = {
                            if (isSimpanGiziEnabled) {
                                giziList = giziList + GiziItem(jenis = selectedGizi, jumlah = jumlahGizi, satuan = selectedSatuanGizi)
                                selectedGizi = ""; jumlahGizi = ""; selectedSatuanGizi = ""
                            }
                        },
                        enabled = isSimpanGiziEnabled, modifier = Modifier.fillMaxWidth().height(48.dp), colors = ButtonDefaults.buttonColors(containerColor = if (isSimpanGiziEnabled) FoundationGreen else Color(0xFFD1D5DB)), shape = RoundedCornerShape(8.dp)
                    ) { Text("Simpan", color = Color.White) }

                    giziList.forEach { item ->
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth().background(Color(0xFFF3F4F6), RoundedCornerShape(8.dp)).padding(12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(modifier = Modifier.background(Color(0xFFD1FAE5), RoundedCornerShape(4.dp)).padding(horizontal = 8.dp, vertical = 4.dp)) {
                                    Text("${item.jumlah} ${item.satuan}", fontSize = 12.sp, color = FoundationGreen, fontWeight = FontWeight.Bold)
                                }
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(item.jenis, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                            }
                            Icon(Icons.Default.Delete, "Hapus", tint = Color.Gray, modifier = Modifier.size(20.dp).clickable { giziList = giziList.filter { it.id != item.id } })
                        }
                    }
                }

                CardSection(title = "Upload Foto Menu", iconResId = R.drawable.uploadfotomenu_icon) {
                    Box(modifier = Modifier.fillMaxWidth().height(120.dp).clip(RoundedCornerShape(8.dp)).background(BackgroundGray).clickable { galleryLauncher.launch("image/*") }, contentAlignment = Alignment.Center) {
                        if (imageUri == null) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Icon(painter = painterResource(id = R.drawable.ambilfotomenu_icon), null, tint = FoundationGreen, modifier = Modifier.size(32.dp))
                                Spacer(modifier = Modifier.height(8.dp))
                                Text("Ambil Foto Menu", fontSize = 12.sp, color = TextGray)
                            }
                        } else {
                            Text("Foto Berhasil Dipilih", color = FoundationGreen, fontWeight = FontWeight.Bold)
                        }
                    }
                }

                CardSection(title = "Deskripsi Singkat Menu", iconResId = R.drawable.deskripsi_icon) {
                    GrayTextField(value = deskripsi, onValueChange = { deskripsi = it }, placeholder = "Deskripsi Nutrisi / Catatan Menu", singleLine = false, modifier = Modifier.height(80.dp))
                }

                PrimaryButton(
                    text = "Tambah ke Menu",
                    onClick = {
                        if (isSubmitAllEnabled) {
                            onSimpanClick(selectedKategori, namaMenu, selectedSatuanPorsi, jumlahPorsi, giziList, imageUri!!, deskripsi)
                            showSuccessDialog = true
                        }
                    },
                    enabled = isSubmitAllEnabled, containerColor = if (isSubmitAllEnabled) FoundationGreen else Color(0xFFD1D5DB), contentColor = if (isSubmitAllEnabled) Color.White else Color.Gray
                )
                Spacer(modifier = Modifier.height(32.dp))
            }

            if (showSuccessDialog) {
                // Manggil SuccessDialog
                SuccessDialog(
                    title = "Menu Berhasil\nDitambahkan",
                    message = "Menu yang kamu tambahkan\ntelah berhasil disimpan.",
                    onDismiss = {
                        showSuccessDialog = false
                        onBackClick()
                    }
                )
            }
        }
    }
}

// Helpers
@Composable fun FormLabel(text: String) { Text(text, fontSize = 12.sp, color = TextGray); Spacer(modifier = Modifier.height(4.dp)) }
@Composable fun CardSection(title: String, iconResId: Int, content: @Composable () -> Unit) { Card(modifier = Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = Color.White), shape = RoundedCornerShape(12.dp), border = BorderStroke(1.dp, BorderGray)) { Column(modifier = Modifier.padding(16.dp)) { Row(verticalAlignment = Alignment.CenterVertically) { Icon(painter = painterResource(id = iconResId), null, tint = FoundationGreen, modifier = Modifier.size(18.dp)); Spacer(modifier = Modifier.width(8.dp)); Text(title, fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black) }; Spacer(modifier = Modifier.height(16.dp)); content() } } }
@Composable fun GrayTextField(value: String, onValueChange: (String) -> Unit, placeholder: String, singleLine: Boolean = true, keyboardOptions: KeyboardOptions = KeyboardOptions.Default, modifier: Modifier = Modifier) { BasicTextField(value = value, onValueChange = onValueChange, singleLine = singleLine, keyboardOptions = keyboardOptions, textStyle = androidx.compose.ui.text.TextStyle(fontSize = 14.sp, color = Color.Black), decorationBox = { inner -> Box(modifier = modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp)).background(BackgroundGray).padding(horizontal = 16.dp, vertical = 12.dp)) { if (value.isEmpty()) Text(placeholder, fontSize = 14.sp, color = TextGray); inner() } }) }
@Composable fun DynamicDropdown(placeholder: String, options: List<String>, selectedValue: String, onValueChange: (String) -> Unit) { var expanded by remember { mutableStateOf(false) }; var boxWidth by remember { mutableIntStateOf(0) }; Box { Box(modifier = Modifier.fillMaxWidth().height(48.dp).onGloballyPositioned { boxWidth = it.size.width }.clip(RoundedCornerShape(8.dp)).background(BackgroundGray).clickable { expanded = !expanded }.padding(horizontal = 16.dp), contentAlignment = Alignment.CenterStart) { Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) { Text(if (selectedValue.isEmpty()) placeholder else selectedValue, fontSize = 14.sp, color = if (selectedValue.isEmpty()) TextGray else Color.Black); Icon(if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown, null, tint = TextGray, modifier = Modifier.size(20.dp)) } }; if (expanded) { Popup(alignment = Alignment.TopStart, properties = PopupProperties(focusable = true), onDismissRequest = { expanded = false }) { Card(modifier = Modifier.width(with(LocalDensity.current) { boxWidth.toDp() }).padding(top = 52.dp), shape = RoundedCornerShape(8.dp), colors = CardDefaults.cardColors(containerColor = Color.White), elevation = CardDefaults.cardElevation(8.dp)) { Column(modifier = Modifier.padding(vertical = 8.dp)) { options.forEach { opt -> Text(opt, modifier = Modifier.fillMaxWidth().clickable { onValueChange(opt); expanded = false }.padding(horizontal = 16.dp, vertical = 12.dp), fontSize = 14.sp) } } } } } } }