package com.example.mbg.feature.role.presentation

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mbg.R
import com.example.mbg.feature.role.domain.model.UserRole
import com.example.mbg.supabase.SupabaseClientProvider
import com.example.mbg.ui.theme.inter
import com.example.mbg.ui.theme.poppins

@Composable
fun RoleScreen(
    onRoleSelected: () -> Unit
) {

    val factory = remember {
        RoleViewModelFactory(SupabaseClientProvider.client)
    }
    val viewModel: RoleViewModel = viewModel(factory = factory)

    var selectedRole by remember { mutableStateOf<UserRole?>(null) }
    var startAnimation by remember { mutableStateOf(false) }

    val screenHeight = LocalConfiguration.current.screenHeightDp.dp
    val sheetHeight = screenHeight * 0.85f

    LaunchedEffect(Unit) {
        startAnimation = true
    }

    val transition = updateTransition(
        targetState = startAnimation,
        label = "BottomSheetTransition"
    )

    val offsetY by transition.animateDp(
        transitionSpec = {
            tween(
                durationMillis = 500,
                easing = FastOutSlowInEasing
            )
        },
        label = "OffsetAnimation"
    ) { visible ->
        if (visible) 0.dp else sheetHeight
    }

    // ================= ROOT BACKGROUND =================
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.linearGradient(
                    colors = listOf(
                        Color(0xFFD9F5F2),
                        Color(0xFF43A047),
                        Color(0xFF388E3C)
                    ),
                    start = androidx.compose.ui.geometry.Offset(0f, 0f),
                    end = androidx.compose.ui.geometry.Offset(
                        Float.POSITIVE_INFINITY,
                        Float.POSITIVE_INFINITY
                    )
                )
            )
    ) {

        // ================= BOTTOM SHEET =================
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(sheetHeight)
                .align(Alignment.BottomCenter)
                .offset(y = offsetY),
            shape = RoundedCornerShape(
                topStart = 40.dp,
                topEnd = 40.dp
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 14.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        Brush.verticalGradient(
                            listOf(
                                Color(0xFFF5F5F5),
                                Color(0xFFEDEDED)
                            )
                        )
                    )
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp, vertical = 28.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Silahkan Pilih Peran Anda",
                        style = MaterialTheme.typography.bodyMedium,
                        fontFamily = inter,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 24.sp
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Pilih tingkat akses anda untuk melanjutkan",
                        style = MaterialTheme.typography.bodyMedium,
                        fontFamily = poppins,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.height(28.dp))

                    val roles = remember {
                        listOf(
                            RoleUi(
                                UserRole.SEKOLAH,
                                "Sekolah",
                                "Mengatur data siswa serta\nmonitoring gizi",
                                R.drawable.sekolah
                            ),
                            RoleUi(
                                UserRole.DAPUR_MBG,
                                "Dapur MBG",
                                "Merencanakan menu serta\nmemproduksi makanan",
                                R.drawable.dapur
                            ),
                            RoleUi(
                                UserRole.ORANG_TUA,
                                "Orang Tua",
                                "Memantau asupan gizi dan perkembangan anak",
                                R.drawable.orangtua
                            )
                        )
                    }

                    roles.forEach { item ->

                        RoleItemAnimated(
                            title = item.title,
                            description = item.description,
                            icon = painterResource(id = item.iconRes),
                            selected = selectedRole == item.role
                        ) {
                            selectedRole = item.role
                            handleSelect(
                                role = item.role,
                                viewModel = viewModel,
                                onNavigate = onRoleSelected
                            )
                        }

                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
        }
    }
}

private fun handleSelect(
    role: UserRole,
    viewModel: RoleViewModel,
    onNavigate: () -> Unit
) {
    viewModel.saveRole(role) {
        onNavigate()
    }
}

private data class RoleUi(
    val role: UserRole,
    val title: String,
    val description: String,
    val iconRes: Int
)