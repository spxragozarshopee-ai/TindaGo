package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.MarketplaceData
import com.example.model.QuickAccessItem
import com.example.ui.theme.*

@Composable
fun QuickAccessGrid(
  onItemClick: (QuickAccessItem) -> Unit,
  modifier: Modifier = Modifier
) {
  val items = MarketplaceData.quickAccessItems

  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 14.dp, vertical = 6.dp)
  ) {
    // 2 rows of 4 items with frosted styling
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      items.take(4).forEach { item ->
        FrostedQuickAccessTile(
          item = item,
          onClick = { onItemClick(item) },
          modifier = Modifier.weight(1f)
        )
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      items.drop(4).take(4).forEach { item ->
        FrostedQuickAccessTile(
          item = item,
          onClick = { onItemClick(item) },
          modifier = Modifier.weight(1f)
        )
      }
    }
  }
}

@Composable
fun FrostedQuickAccessTile(
  item: QuickAccessItem,
  onClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .clickable { onClick() }
      .padding(horizontal = 2.dp)
      .testTag("quick_access_${item.id}"),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Box(contentAlignment = Alignment.TopEnd) {
      // Rounded 2xl pastel container with subtle frosted border
      Box(
        modifier = Modifier
          .size(48.dp)
          .clip(RoundedCornerShape(16.dp))
          .background(
            when (item.id) {
              "qa_cat" -> Color(0xFFD1FAE5)  // emerald-100
              "qa_flash" -> Color(0xFFFEE2E2) // red-100
              "qa_ship" -> Color(0xFFCCFBF1)  // teal-100
              "qa_mall" -> Color(0xFFE8F5E9)  // sage-100
              "qa_wallet" -> Color(0xFFCFFAFE) // cyan-100
              "qa_live" -> Color(0xFFFCE7F3)  // pink-100
              "qa_group" -> Color(0xFFEDE9FE) // purple-100
              else -> Color(0xFFE2E8F0)       // slate-200
            }
          )
          .border(1.dp, GlassBorderWhite, RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center
      ) {
        Text(text = item.iconEmoji, fontSize = 22.sp)
      }

      // Badge if any
      if (item.badge != null) {
        Box(
          modifier = Modifier
            .offset(x = 6.dp, y = (-4).dp)
            .clip(RoundedCornerShape(8.dp))
            .background(if (item.badge.contains("HOT") || item.badge.contains("-")) FlashRed else MintPrimary)
            .padding(horizontal = 4.dp, vertical = 1.dp)
        ) {
          Text(
            text = item.badge,
            fontSize = 8.sp,
            fontWeight = FontWeight.Black,
            color = Color.White
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(4.dp))

    Text(
      text = item.title,
      fontSize = 11.sp,
      fontWeight = FontWeight.Medium,
      color = TextSlate700,
      textAlign = TextAlign.Center,
      maxLines = 1,
      overflow = TextOverflow.Ellipsis
    )
  }
}
