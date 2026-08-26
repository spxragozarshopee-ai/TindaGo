package com.example.ui.components

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.MarketplaceData
import com.example.model.HeroBanner
import com.example.model.Voucher
import com.example.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun HeroCarousel(
  onBannerClick: (HeroBanner) -> Unit,
  modifier: Modifier = Modifier
) {
  val banners = MarketplaceData.heroBanners
  var currentIndex by remember { mutableIntStateOf(0) }

  // Auto slide
  LaunchedEffect(currentIndex) {
    delay(4500)
    currentIndex = (currentIndex + 1) % banners.size
  }

  val currentBanner = banners[currentIndex]

  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 14.dp, vertical = 6.dp)
  ) {
    // Frosted Glass Gradient Banner Card
    Surface(
      shape = RoundedCornerShape(20.dp),
      color = Color.Transparent,
      shadowElevation = 6.dp,
      modifier = Modifier
        .fillMaxWidth()
        .height(130.dp)
        .clip(RoundedCornerShape(20.dp))
        .clickable { onBannerClick(currentBanner) }
        .testTag("hero_banner_card")
    ) {
      Box(
        modifier = Modifier
          .fillMaxSize()
          .background(
            Brush.linearGradient(
              colors = listOf(
                Color(0xFF34D399), // Emerald 400
                Color(0xFF0D9488)  // Teal 600
              )
            )
          )
          .border(1.dp, GlassBorderWhite, RoundedCornerShape(20.dp))
          .padding(14.dp)
      ) {
        // Decorative background watermarks
        Text(
          text = currentBanner.emoji,
          fontSize = 90.sp,
          color = Color.White.copy(alpha = 0.16f),
          modifier = Modifier
            .align(Alignment.BottomEnd)
            .offset(x = 12.dp, y = 16.dp)
        )

        Column(
          modifier = Modifier
            .fillMaxHeight()
            .fillMaxWidth(0.78f),
          verticalArrangement = Arrangement.Center
        ) {
          // Frosted Glass Badge Chip
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = GlassWhite20,
            border = androidx.compose.foundation.BorderStroke(1.dp, GlassWhite30)
          ) {
            Text(
              text = currentBanner.badge,
              fontSize = 9.sp,
              fontWeight = FontWeight.Black,
              color = Color.White,
              letterSpacing = 0.8.sp,
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
            )
          }

          Spacer(modifier = Modifier.height(4.dp))

          Text(
            text = currentBanner.title,
            fontSize = 17.sp,
            fontWeight = FontWeight.Black,
            color = Color.White,
            lineHeight = 20.sp
          )

          Spacer(modifier = Modifier.height(2.dp))

          Text(
            text = currentBanner.subtitle,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            color = Color(0xFFE6FFFA),
            maxLines = 1
          )
        }

        // Indicators on Bottom Left
        Row(
          modifier = Modifier
            .align(Alignment.BottomStart)
            .padding(bottom = 2.dp),
          horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          banners.indices.forEach { index ->
            val isSelected = index == currentIndex
            Box(
              modifier = Modifier
                .height(4.dp)
                .width(if (isSelected) 18.dp else 5.dp)
                .clip(CircleShape)
                .background(if (isSelected) Color.White else Color.White.copy(alpha = 0.45f))
            )
          }
        }
      }
    }
  }
}

@Composable
fun DailyVouchersBar(
  vouchers: List<Voucher>,
  claimedVoucherIds: Set<String>,
  onClaimVoucher: (String) -> Unit,
  onClaimAll: () -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 14.dp, vertical = 4.dp)
  ) {
    // Header
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
          imageVector = Icons.Default.ConfirmationNumber,
          contentDescription = null,
          tint = MintPrimary,
          modifier = Modifier.size(16.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = "DAILY SAVINGS VOUCHERS",
          fontSize = 12.sp,
          fontWeight = FontWeight.Black,
          color = TextDark,
          letterSpacing = 0.5.sp
        )
      }

      Surface(
        onClick = onClaimAll,
        shape = RoundedCornerShape(12.dp),
        color = GlassWhite70,
        border = androidx.compose.foundation.BorderStroke(1.dp, GlassBorderEmerald),
        modifier = Modifier.testTag("claim_all_vouchers_btn")
      ) {
        Text(
          text = "Claim All ✨",
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          color = MintPrimaryDark,
          modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
        )
      }
    }

    Spacer(modifier = Modifier.height(8.dp))

    // Voucher cards horizontal scroll
    LazyRow(
      horizontalArrangement = Arrangement.spacedBy(8.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      items(vouchers) { voucher ->
        val isClaimed = claimedVoucherIds.contains(voucher.id)
        FrostedVoucherTicket(
          voucher = voucher,
          isClaimed = isClaimed,
          onClaim = { onClaimVoucher(voucher.id) }
        )
      }
    }
  }
}

@Composable
fun FrostedVoucherTicket(
  voucher: Voucher,
  isClaimed: Boolean,
  onClaim: () -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    shape = RoundedCornerShape(12.dp),
    color = if (isClaimed) MintContainer.copy(alpha = 0.85f) else GlassWhite85,
    border = androidx.compose.foundation.BorderStroke(
      1.dp,
      if (isClaimed) MintPrimary.copy(alpha = 0.5f) else GlassBorderEmerald
    ),
    shadowElevation = 2.dp,
    modifier = modifier
      .width(185.dp)
      .testTag("voucher_ticket_${voucher.id}")
  ) {
    Row(
      modifier = Modifier.padding(8.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = voucher.title,
          fontSize = 10.sp,
          fontWeight = FontWeight.Black,
          color = if (isClaimed) MintPrimaryDark else TextDark,
          maxLines = 1
        )
        Text(
          text = voucher.discountText,
          fontSize = 11.sp,
          fontWeight = FontWeight.Bold,
          color = MintPrimary
        )
        Text(
          text = voucher.minSpendText,
          fontSize = 9.sp,
          color = TextMuted
        )
      }

      Spacer(modifier = Modifier.width(6.dp))

      Button(
        onClick = onClaim,
        enabled = !isClaimed,
        shape = RoundedCornerShape(8.dp),
        colors = ButtonDefaults.buttonColors(
          containerColor = if (isClaimed) MintPrimaryDark else MintPrimary,
          contentColor = Color.White,
          disabledContainerColor = MintPrimary.copy(alpha = 0.2f),
          disabledContentColor = MintPrimaryDark
        ),
        contentPadding = PaddingValues(horizontal = 6.dp, vertical = 2.dp),
        modifier = Modifier.height(28.dp)
      ) {
        if (isClaimed) {
          Icon(imageVector = Icons.Default.Check, contentDescription = null, modifier = Modifier.size(12.dp))
          Spacer(modifier = Modifier.width(2.dp))
          Text(text = "Saved", fontSize = 9.sp, fontWeight = FontWeight.Bold)
        } else {
          Text(text = "Claim", fontSize = 10.sp, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}
