package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ConfirmationNumber
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Sell
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.MarketplaceData
import com.example.model.HeroBanner
import com.example.model.Voucher
import com.example.ui.theme.*
import kotlinx.coroutines.delay

@Composable
fun HeroBannerSection(
  vouchers: List<Voucher>,
  onClaimVoucher: (String) -> Unit,
  onClaimAllVouchers: () -> Unit,
  onBannerAction: (HeroBanner) -> Unit,
  modifier: Modifier = Modifier
) {
  val banners = MarketplaceData.heroBanners
  var currentBannerIndex by remember { mutableIntStateOf(0) }

  // Auto rotate banner every 4.5 seconds
  LaunchedEffect(currentBannerIndex) {
    delay(4500L)
    currentBannerIndex = (currentBannerIndex + 1) % banners.size
  }

  val activeBanner = banners[currentBannerIndex]

  Column(modifier = modifier.fillMaxWidth()) {
    // 1. Dynamic Hero Banner Card
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 14.dp, vertical = 8.dp)
        .clip(RoundedCornerShape(16.dp))
        .background(
          Brush.linearGradient(
            colors = listOf(
              Color(activeBanner.bgGradientStart),
              Color(activeBanner.bgGradientEnd)
            )
          )
        )
        .pointerInput(Unit) {
          detectHorizontalDragGestures { _, dragAmount ->
            if (dragAmount < -30) {
              currentBannerIndex = (currentBannerIndex + 1) % banners.size
            } else if (dragAmount > 30) {
              currentBannerIndex = (currentBannerIndex - 1 + banners.size) % banners.size
            }
          }
        }
        .clickable { onBannerAction(activeBanner) }
        .padding(16.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        // Left details
        Column(modifier = Modifier.weight(1f)) {
          // Badge Pill
          Surface(
            shape = RoundedCornerShape(20.dp),
            color = Color.White.copy(alpha = 0.22f)
          ) {
            Text(
              text = activeBanner.badge,
              fontSize = 10.sp,
              fontWeight = FontWeight.Black,
              color = Color.White,
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
              letterSpacing = 0.5.sp
            )
          }

          Spacer(modifier = Modifier.height(6.dp))

          Text(
            text = activeBanner.title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Black,
            color = Color.White,
            lineHeight = 22.sp
          )

          Spacer(modifier = Modifier.height(4.dp))

          Text(
            text = activeBanner.subtitle,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White.copy(alpha = 0.9f),
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
          )

          Spacer(modifier = Modifier.height(10.dp))

          // CTA Button
          Row(verticalAlignment = Alignment.CenterVertically) {
            Button(
              onClick = { onBannerAction(activeBanner) },
              colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color(activeBanner.bgGradientStart)
              ),
              shape = RoundedCornerShape(20.dp),
              contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
              modifier = Modifier
                .height(34.dp)
                .testTag("hero_banner_cta")
            ) {
              Text(
                text = activeBanner.ctaText,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
              )
            }

            Spacer(modifier = Modifier.width(8.dp))

            Surface(
              shape = RoundedCornerShape(6.dp),
              color = Color.Black.copy(alpha = 0.25f)
            ) {
              Text(
                text = activeBanner.promoTag,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
              )
            }
          }
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Right Graphic Illustration
        Box(
          modifier = Modifier
            .size(76.dp)
            .clip(CircleShape)
            .background(Color.White.copy(alpha = 0.18f)),
          contentAlignment = Alignment.Center
        ) {
          Text(text = activeBanner.emoji, fontSize = 42.sp)
        }
      }
    }

    // Page Indicator Dots
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(top = 2.dp, bottom = 8.dp),
      horizontalArrangement = Arrangement.Center,
      verticalAlignment = Alignment.CenterVertically
    ) {
      banners.indices.forEach { index ->
        val isSelected = index == currentBannerIndex
        Box(
          modifier = Modifier
            .padding(horizontal = 3.dp)
            .height(4.dp)
            .width(if (isSelected) 18.dp else 6.dp)
            .clip(RoundedCornerShape(2.dp))
            .background(if (isSelected) MintPrimary else Color.LightGray)
            .clickable { currentBannerIndex = index }
        )
      }
    }

    // 2. Daily Vouchers Fast Claim Bar
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 14.dp)
    ) {
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
          Spacer(modifier = Modifier.width(4.dp))
          Text(
            text = "DAILY SAVINGS VOUCHERS",
            fontSize = 12.sp,
            fontWeight = FontWeight.Black,
            color = TextDark,
            letterSpacing = 0.5.sp
          )
        }

        Text(
          text = "Claim All ✨",
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          color = MintPrimary,
          modifier = Modifier
            .clickable { onClaimAllVouchers() }
            .testTag("claim_all_vouchers_btn")
        )
      }

      Spacer(modifier = Modifier.height(6.dp))

      LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        items(vouchers) { voucher ->
          VoucherCard(
            voucher = voucher,
            onClaim = { onClaimVoucher(voucher.id) }
          )
        }
      }
    }
  }
}

@Composable
fun VoucherCard(
  voucher: Voucher,
  onClaim: () -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    shape = RoundedCornerShape(10.dp),
    color = if (voucher.isClaimed) LightSurfaceVariant else Color.White,
    border = androidx.compose.foundation.BorderStroke(
      1.dp,
      if (voucher.isClaimed) MintPrimary.copy(alpha = 0.4f) else BorderLight
    ),
    shadowElevation = if (voucher.isClaimed) 0.dp else 2.dp,
    modifier = modifier.width(185.dp)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      Column(modifier = Modifier.weight(1f)) {
        Surface(
          shape = RoundedCornerShape(4.dp),
          color = if (voucher.tag.contains("HOT")) FlashRedContainer else MintContainer
        ) {
          Text(
            text = voucher.tag,
            fontSize = 9.sp,
            fontWeight = FontWeight.Bold,
            color = if (voucher.tag.contains("HOT")) FlashRed else MintPrimaryDark,
            modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
          )
        }

        Spacer(modifier = Modifier.height(2.dp))

        Text(
          text = voucher.discountText,
          fontSize = 12.sp,
          fontWeight = FontWeight.Black,
          color = TextDark,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )

        Text(
          text = voucher.minSpendText,
          fontSize = 10.sp,
          color = TextMuted,
          maxLines = 1
        )
      }

      Spacer(modifier = Modifier.width(6.dp))

      if (voucher.isClaimed) {
        Surface(
          shape = RoundedCornerShape(12.dp),
          color = MintPrimary.copy(alpha = 0.15f)
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Icon(
              imageVector = Icons.Default.Check,
              contentDescription = "Claimed",
              tint = MintPrimary,
              modifier = Modifier.size(12.dp)
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(
              text = "Saved",
              fontSize = 10.sp,
              fontWeight = FontWeight.Bold,
              color = MintPrimaryDark
            )
          }
        }
      } else {
        Button(
          onClick = onClaim,
          shape = RoundedCornerShape(12.dp),
          colors = ButtonDefaults.buttonColors(
            containerColor = MintPrimary,
            contentColor = Color.White
          ),
          contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp),
          modifier = Modifier
            .height(26.dp)
            .testTag("claim_voucher_${voucher.id}")
        ) {
          Text(
            text = "Claim",
            fontSize = 10.sp,
            fontWeight = FontWeight.Bold
          )
        }
      }
    }
  }
}
