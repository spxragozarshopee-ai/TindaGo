package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.MarketplaceData
import com.example.model.FaqItem
import com.example.ui.theme.*

@Composable
fun MarketFooterSection(
  modifier: Modifier = Modifier,
  onOpenHelp: () -> Unit = {},
  onSelectFaq: (FaqItem) -> Unit = {}
) {
  var expandedFaqIndex by remember { mutableIntStateOf(-1) }

  Column(
    modifier = modifier
      .fillMaxWidth()
      .background(Color(0xFF0F172A))
      .padding(horizontal = 16.dp, vertical = 20.dp)
  ) {
    // 1. Trust & Safety Badges
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      TrustBadge(icon = "🛡️", title = "100% Genuine", subtitle = "Mall Guarantee")
      TrustBadge(icon = "🔄", title = "15-Day Free", subtitle = "Easy Returns")
      TrustBadge(icon = "🔒", title = "SSL 256-bit", subtitle = "Safe Checkout")
    }

    Spacer(modifier = Modifier.height(18.dp))
    HorizontalDivider(color = Color.White.copy(alpha = 0.12f))
    Spacer(modifier = Modifier.height(16.dp))

    // 2. Customer Care & FAQ Accordion Header
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = "HELP CENTER & FAQ",
        fontSize = 12.sp,
        fontWeight = FontWeight.Black,
        color = MintContainer,
        letterSpacing = 0.5.sp
      )

      Surface(
        onClick = onOpenHelp,
        shape = RoundedCornerShape(12.dp),
        color = Color.White.copy(alpha = 0.15f),
        modifier = Modifier.testTag("footer_live_help_btn")
      ) {
        Text(
          text = "24/7 Chat Support",
          fontSize = 10.sp,
          fontWeight = FontWeight.Bold,
          color = Color.White,
          modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    // FAQ Items
    MarketplaceData.faqs.forEachIndexed { index, faq ->
      val isExpanded = expandedFaqIndex == index
      Surface(
        shape = RoundedCornerShape(10.dp),
        color = if (isExpanded) Color.White.copy(alpha = 0.1f) else Color.White.copy(alpha = 0.05f),
        border = androidx.compose.foundation.BorderStroke(1.dp, Color.White.copy(alpha = 0.08f)),
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 3.dp)
          .clickable {
            expandedFaqIndex = if (isExpanded) -1 else index
            onSelectFaq(faq)
          }
      ) {
        Column(modifier = Modifier.padding(10.dp)) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = faq.question,
              fontSize = 11.sp,
              fontWeight = FontWeight.SemiBold,
              color = Color.White,
              modifier = Modifier.weight(1f)
            )
            Icon(
              imageVector = if (isExpanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
              contentDescription = null,
              tint = MintPrimaryLight,
              modifier = Modifier.size(18.dp)
            )
          }

          AnimatedVisibility(
            visible = isExpanded,
            enter = expandVertically(),
            exit = shrinkVertically()
          ) {
            Text(
              text = faq.answer,
              fontSize = 10.sp,
              color = Color(0xFFCBD5E1),
              lineHeight = 14.sp,
              modifier = Modifier.padding(top = 6.dp)
            )
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(18.dp))
    HorizontalDivider(color = Color.White.copy(alpha = 0.12f))
    Spacer(modifier = Modifier.height(14.dp))

    // Logistics & Payment Partners
    Text(
      text = "LOGISTICS & PAYMENT PARTNERS",
      fontSize = 10.sp,
      fontWeight = FontWeight.Bold,
      color = Color(0xFF94A3B8),
      letterSpacing = 0.5.sp
    )

    Spacer(modifier = Modifier.height(8.dp))

    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      MarketplaceData.logisticsPartners.take(4).forEach { partner ->
        Surface(
          shape = RoundedCornerShape(6.dp),
          color = Color.White.copy(alpha = 0.08f),
          modifier = Modifier.weight(1f)
        ) {
          Text(
            text = partner,
            fontSize = 8.sp,
            color = Color(0xFFE2E8F0),
            modifier = Modifier.padding(vertical = 4.dp),
            textAlign = androidx.compose.ui.text.style.TextAlign.Center,
            maxLines = 1
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(14.dp))

    Text(
      text = "© 2026 MarketGreen Inc. All rights reserved. Frosted Edition.",
      fontSize = 9.sp,
      color = Color(0xFF64748B),
      textAlign = androidx.compose.ui.text.style.TextAlign.Center,
      modifier = Modifier.fillMaxWidth()
    )
  }
}

@Composable
private fun TrustBadge(icon: String, title: String, subtitle: String) {
  Row(
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(6.dp)
  ) {
    Text(text = icon, fontSize = 20.sp)
    Column {
      Text(
        text = title,
        fontSize = 10.sp,
        fontWeight = FontWeight.Black,
        color = Color.White
      )
      Text(
        text = subtitle,
        fontSize = 9.sp,
        color = MintContainer
      )
    }
  }
}
