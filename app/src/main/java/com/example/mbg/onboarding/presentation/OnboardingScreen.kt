package com.example.mbg.onboarding.presentation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.mbg.R
import com.example.mbg.core.ui.component.PrimaryButton
import com.example.mbg.onboarding.presentation.component.*
import com.example.mbg.ui.theme.FoundationBlue
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(
    onFinish: () -> Unit
) {

    val pages = listOf(
        OnboardingPage(
            title = "Makanan Bergizi untuk Setiap Anak",
            description = "Memastikan  setiap siswa mendapatkan makanan bergizi ",
            imageRes = R.drawable.onboarding1
        ),
        OnboardingPage(
            title = "Logistik Siap Diantar",
            description = "Menghubungkan dapur dan sekolah dengan pelacakan waktu nyata ",
            imageRes = R.drawable.onboarding2
        ),
        OnboardingPage(
            title = "Pantau dengan Satu Genggaman",
            description = "Orang tua & sekolah dapat memantau program MBG melalui gawai  pribadi",
            imageRes = R.drawable.onboarding3
        )
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })
    val scope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier.fillMaxSize()) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .statusBarsPadding()
                    .height(48.dp)
                    .padding(end = 20.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (pagerState.currentPage < pages.lastIndex) {
                    TextButton(
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(pages.lastIndex)
                            }
                        }
                    ) {
                        Text("Lewati")
                    }
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->

                val pageOffset by remember {
                    derivedStateOf {
                        (
                                (pagerState.currentPage - page)
                                        + pagerState.currentPageOffsetFraction
                                ).absoluteValue
                    }
                }

                OnboardingPageContent(
                    page = pages[page],
                    pageOffset = pageOffset
                )
            }
        }

        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(pages.size) { index ->
                    IndicatorDot(
                        isSelected = pagerState.currentPage == index
                    )
                }
            }

            PrimaryButton(
                text = if (pagerState.currentPage == pages.lastIndex)
                    "Mulai"
                else
                    "Lanjut",
                onClick = {
                    if (pagerState.currentPage == pages.lastIndex) {
                        onFinish()
                    } else {
                        scope.launch {
                            pagerState.animateScrollToPage(
                                pagerState.currentPage + 1
                            )
                        }
                    }
                },
                containerColor = FoundationBlue,
                contentColor = Color.White,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(horizontal = 24.dp)
            )
        }
    }
}