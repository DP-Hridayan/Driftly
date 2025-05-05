@file:OptIn(ExperimentalMaterial3Api::class)

package `in`.hridayan.driftly.settings.page.about.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.lerp
import androidx.compose.ui.unit.sp
import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.navigation.LocalNavController
import `in`.hridayan.driftly.settings.page.about.components.card.SupportMeCard
import `in`.hridayan.driftly.settings.page.about.components.image.ProfilePicCircular

@Composable
fun AboutScreen(modifier: Modifier = Modifier) {
    val navController = LocalNavController.current
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = modifier.fillMaxSize(), topBar = {
            LargeTopAppBar(
                title = {
                    val collapsedFraction = scrollBehavior.state.collapsedFraction

                    val expandedFontSize = 33.sp
                    val collapsedFontSize = 20.sp

                    val fontSize = lerp(expandedFontSize, collapsedFontSize, collapsedFraction)
                    Text(
                        text = stringResource(R.string.about),
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1,
                        fontSize = fontSize,
                        fontFamily = FontFamily.SansSerif,
                        letterSpacing = 0.05.em
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.arrow_back),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        }) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .nestedScroll(scrollBehavior.nestedScrollConnection),
            contentPadding = PaddingValues(
                top = innerPadding.calculateTopPadding()
            )
        ) {
            item {
                Text(
                    text = stringResource(R.string.lead_developer),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 25.dp)
                )

                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top= 25.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    ProfilePicCircular(
                        painter = painterResource(R.mipmap.dp_hridayan),
                        size = 150.dp,
                    )

                    Text(
                        text = "Hridayan",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                    )

                    Text(
                        text = stringResource(R.string.des_hridayan),
                        style = MaterialTheme.typography.bodySmall,
                        fontStyle = FontStyle.Italic,
                    )

                    SupportMeCard(modifier = modifier.padding(horizontal = 20.dp))
                }
            }
        }
    }
}