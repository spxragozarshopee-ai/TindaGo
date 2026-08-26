package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Storefront
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun SellerOnboardingBanner(
  onOpenSellerRegistration: () -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    shape = RoundedCornerShape(16.dp),
    color = Color.Transparent,
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 14.dp, vertical = 6.dp)
      .clip(RoundedCornerShape(16.dp))
      .clickable { onOpenSellerRegistration() }
      .testTag("seller_promo_strip")
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .background(
          Brush.horizontalGradient(
            colors = listOf(
              Color(0xFF059669),
              Color(0xFF0D9488)
            )
          )
        )
        .border(1.dp, GlassBorderWhite, RoundedCornerShape(16.dp))
        .padding(horizontal = 14.dp, vertical = 10.dp)
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Box(
            modifier = Modifier
              .size(28.dp)
              .clip(CircleShape)
              .background(GlassWhite20),
            contentAlignment = Alignment.Center
          ) {
            Text(text = "🏪", fontSize = 14.sp)
          }

          Column {
            Text(
              text = "EARN COMMISSIONS AS A SELLER",
              fontSize = 10.sp,
              fontWeight = FontWeight.Black,
              color = Color.White,
              letterSpacing = 0.5.sp
            )
            Text(
              text = "0% Commission for 30 Days • Instant Payouts",
              fontSize = 9.sp,
              color = Color.White.copy(alpha = 0.85f)
            )
          }
        }

        Surface(
          shape = RoundedCornerShape(20.dp),
          color = Color.White,
          shadowElevation = 2.dp,
          modifier = Modifier.clickable { onOpenSellerRegistration() }
        ) {
          Text(
            text = "JOIN NOW",
            fontSize = 9.sp,
            fontWeight = FontWeight.Black,
            color = MintPrimaryDark,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp)
          )
        }
      }
    }
  }
}
