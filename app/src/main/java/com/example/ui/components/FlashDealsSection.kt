package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.MarketplaceData
import com.example.model.Product
import com.example.ui.theme.*

@Composable
fun FlashDealsSection(
  countdownSeconds: Long,
  activeSlot: String,
  onSelectSlot: (String) -> Unit,
  onProductClick: (Product) -> Unit,
  onAddToCart: (Product) -> Unit,
  formatPrice: (Double) -> String,
  modifier: Modifier = Modifier
) {
  val hours = countdownSeconds / 3600
  val minutes = (countdownSeconds % 3600) / 60
  val seconds = countdownSeconds % 60

  val timeSlots = listOf("12:00 Ongoing", "16:00 Soon", "20:00 Night")
  val deals = MarketplaceData.flashDeals

  // Frosted Glass Main Section Container
  Surface(
    shape = RoundedCornerShape(20.dp),
    color = GlassWhite70,
    border = androidx.compose.foundation.BorderStroke(1.dp, GlassBorderWhite),
    shadowElevation = 2.dp,
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 14.dp, vertical = 6.dp)
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(12.dp)
    ) {
      // Header Row: FLASH DEALS + Live Emerald Timer
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Text(
            text = "FLASH DEALS",
            fontSize = 14.sp,
            fontWeight = FontWeight.Black,
            color = MintPrimaryDark,
            letterSpacing = 0.5.sp
          )

          Spacer(modifier = Modifier.width(8.dp))

          // Emerald Timer Blocks (HH : MM : SS)
          FrostedTimerBlock(value = String.format("%02d", hours))
          Text(
            text = ":",
            fontWeight = FontWeight.Bold,
            color = MintPrimary,
            modifier = Modifier.padding(horizontal = 2.dp),
            fontSize = 11.sp
          )
          FrostedTimerBlock(value = String.format("%02d", minutes))
          Text(
            text = ":",
            fontWeight = FontWeight.Bold,
            color = MintPrimary,
            modifier = Modifier.padding(horizontal = 2.dp),
            fontSize = 11.sp
          )
          FrostedTimerBlock(value = String.format("%02d", seconds))
        }

        Text(
          text = "SEE ALL",
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          color = MintPrimary,
          modifier = Modifier.clickable { }
        )
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Time Slot Selector Tabs (Frosted Pills)
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        timeSlots.forEach { slot ->
          val isSelected = activeSlot == slot
          Surface(
            onClick = { onSelectSlot(slot) },
            shape = RoundedCornerShape(10.dp),
            color = if (isSelected) MintContainer else GlassWhite85,
            border = androidx.compose.foundation.BorderStroke(
              1.dp,
              if (isSelected) MintPrimary else GlassBorderEmerald
            ),
            modifier = Modifier
              .weight(1f)
              .testTag("flash_slot_$slot")
          ) {
            Column(
              modifier = Modifier.padding(vertical = 4.dp),
              horizontalAlignment = Alignment.CenterHorizontally
            ) {
              val parts = slot.split(" ")
              Text(
                text = parts[0],
                fontSize = 11.sp,
                fontWeight = FontWeight.Black,
                color = if (isSelected) MintPrimaryDark else TextDark
              )
              Text(
                text = parts.drop(1).joinToString(" "),
                fontSize = 9.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                color = if (isSelected) MintPrimaryDark else TextMuted
              )
            }
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Horizontal Flash Deals Cards
      LazyRow(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        items(deals) { product ->
          FrostedFlashDealCard(
            product = product,
            onProductClick = { onProductClick(product) },
            onAddToCart = { onAddToCart(product) },
            formatPrice = formatPrice
          )
        }
      }
    }
  }
}

@Composable
fun FrostedTimerBlock(value: String) {
  Box(
    modifier = Modifier
      .clip(RoundedCornerShape(4.dp))
      .background(MintPrimary)
      .padding(horizontal = 4.dp, vertical = 2.dp),
    contentAlignment = Alignment.Center
  ) {
    Text(
      text = value,
      fontSize = 10.sp,
      fontWeight = FontWeight.Black,
      color = Color.White
    )
  }
}

@Composable
fun FrostedFlashDealCard(
  product: Product,
  onProductClick: () -> Unit,
  onAddToCart: () -> Unit,
  formatPrice: (Double) -> String,
  modifier: Modifier = Modifier
) {
  val claimedPercent = ((product.totalStock - product.stockLeft).toFloat() / product.totalStock.toFloat()).coerceIn(0.1f, 0.95f)

  Surface(
    shape = RoundedCornerShape(14.dp),
    color = Color.White,
    border = androidx.compose.foundation.BorderStroke(1.dp, GlassBorderEmerald),
    shadowElevation = 1.dp,
    modifier = modifier
      .width(148.dp)
      .clickable { onProductClick() }
      .testTag("flash_deal_${product.id}")
  ) {
    Column(modifier = Modifier.padding(8.dp)) {
      // Visual Box with Top-Right Discount Badge
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(105.dp)
          .clip(RoundedCornerShape(10.dp))
          .background(Color(0xFFE8F5E9)),
        contentAlignment = Alignment.Center
      ) {
        Text(text = product.iconEmoji, fontSize = 42.sp)

        // Discount Tag Top-Right
        Box(
          modifier = Modifier
            .align(Alignment.TopEnd)
            .clip(RoundedCornerShape(bottomStart = 8.dp))
            .background(MintPrimaryLight)
            .padding(horizontal = 6.dp, vertical = 2.dp)
        ) {
          Text(
            text = "-${product.discountPercent}%",
            fontSize = 9.sp,
            fontWeight = FontWeight.Black,
            color = Color.White
          )
        }
      }

      Spacer(modifier = Modifier.height(6.dp))

      // Price Tag (Emerald) + Strikethrough
      Row(
        verticalAlignment = Alignment.Bottom,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
      ) {
        Text(
          text = formatPrice(product.priceUsd),
          fontSize = 13.sp,
          fontWeight = FontWeight.Black,
          color = MintPrimaryDark
        )
        Text(
          text = formatPrice(product.originalPriceUsd),
          fontSize = 9.sp,
          color = TextMuted,
          textDecoration = TextDecoration.LineThrough
        )
      }

      Spacer(modifier = Modifier.height(2.dp))

      // Title
      Text(
        text = product.title,
        fontSize = 10.sp,
        fontWeight = FontWeight.Medium,
        color = TextDark,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis
      )

      Spacer(modifier = Modifier.height(4.dp))

      // Slim Emerald Stock Progress Bar
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(6.dp)
          .clip(RoundedCornerShape(3.dp))
          .background(Color(0xFFD1FAE5))
      ) {
        Box(
          modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(claimedPercent)
            .clip(RoundedCornerShape(3.dp))
            .background(MintPrimary)
        )
      }

      Spacer(modifier = Modifier.height(2.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Text(
          text = "${((claimedPercent) * 100).toInt()}% SOLD",
          fontSize = 8.sp,
          fontWeight = FontWeight.Bold,
          color = TextMuted
        )

        // Add icon
        Surface(
          onClick = onAddToCart,
          shape = CircleShape,
          color = MintContainer,
          modifier = Modifier.size(22.dp)
        ) {
          Box(contentAlignment = Alignment.Center) {
            Icon(
              imageVector = Icons.Default.AddShoppingCart,
              contentDescription = "Add",
              tint = MintPrimaryDark,
              modifier = Modifier.size(12.dp)
            )
          }
        }
      }
    }
  }
}
