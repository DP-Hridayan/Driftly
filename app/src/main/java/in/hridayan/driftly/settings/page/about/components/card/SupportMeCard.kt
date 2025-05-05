package `in`.hridayan.driftly.settings.page.about.components.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import `in`.hridayan.driftly.R
import `in`.hridayan.driftly.core.presentation.components.svg.DynamicColorImageVectors
import `in`.hridayan.driftly.core.presentation.components.svg.vectors.bmcLogo

@Composable
fun SupportMeCard(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large)
            .background(
                color = MaterialTheme.colorScheme.secondaryContainer
            )
            .clickable(enabled = true, onClick = {})
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            ContactHandles()

            HorizontalDivider(thickness = 1.dp)

            BuyMeACoffee()
        }
    }
}

@Composable
private fun ContactHandles(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(horizontal = 25.dp, vertical = 10.dp)
            .height(IntrinsicSize.Min)
    ) {
        ContactBox(
            modifier = Modifier.weight(1f),
            painter = painterResource(R.drawable.ic_mail),
            text = "E-mail"
        )

        VerticalDivider(thickness = 1.dp, modifier = Modifier.padding(horizontal = 5.dp))

        ContactBox(
            modifier = Modifier.weight(1f),
            painter = painterResource(R.drawable.ic_github),
            text = "Github"
        )

        VerticalDivider(thickness = 1.dp, modifier = Modifier.padding(horizontal = 5.dp))

        ContactBox(
            modifier = Modifier.weight(1f),
            painter = painterResource(R.drawable.ic_telegram),
            text = "Telegram"
        )
    }
}

@Composable
private fun ContactBox(
    modifier: Modifier = Modifier,
    painter: Painter,
    text: String,
    onClick: () -> Unit = {}
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = modifier
            .clip(MaterialTheme.shapes.extraLarge)
            .clickable(enabled = true, onClick = onClick)
            .padding(vertical = 5.dp),
    ) {
        Image(
            modifier = Modifier.size(24.dp),
            painter = painter,
            colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onSecondaryContainer),
            contentDescription = null,
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
private fun BuyMeACoffee(modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(20.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clickable(enabled = true, onClick = onClick)
            .padding(horizontal = 25.dp, vertical = 15.dp)
    ) {
        Spacer(modifier = Modifier.weight(0.4f))
        Image(
            imageVector = DynamicColorImageVectors.bmcLogo(),
            contentDescription = null,
            modifier = Modifier.height(40.dp)
        )

        Text(
            text = stringResource(R.string.support_bmc),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.weight(6f)
        )
    }
}