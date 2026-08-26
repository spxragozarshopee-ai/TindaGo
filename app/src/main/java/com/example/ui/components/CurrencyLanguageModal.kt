package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
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
import androidx.compose.ui.window.Dialog
import com.example.data.MarketplaceData
import com.example.model.Currency
import com.example.model.Language
import com.example.ui.theme.*

@Composable
fun CurrencyLanguageModal(
  activeCurrency: Currency,
  activeLanguage: Language,
  onSelectCurrency: (Currency) -> Unit,
  onSelectLanguage: (Language) -> Unit,
  onDismiss: () -> Unit
) {
  var selectedTab by remember { mutableIntStateOf(0) } // 0: Currency, 1: Language

  Dialog(onDismissRequest = onDismiss) {
    Surface(
      shape = RoundedCornerShape(20.dp),
      color = Color.White,
      border = androidx.compose.foundation.BorderStroke(1.dp, GlassBorderEmerald),
      shadowElevation = 8.dp,
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .testTag("currency_lang_modal")
    ) {
      Column(modifier = Modifier.padding(16.dp)) {
        // Header
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(
            text = "Region & Preferences",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
          )
          IconButton(onClick = onDismiss, modifier = Modifier.size(24.dp)) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = TextMuted)
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Tab switcher
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .background(FrostedBackground)
            .padding(3.dp)
        ) {
          Surface(
            onClick = { selectedTab = 0 },
            shape = RoundedCornerShape(8.dp),
            color = if (selectedTab == 0) Color.White else Color.Transparent,
            shadowElevation = if (selectedTab == 0) 2.dp else 0.dp,
            modifier = Modifier.weight(1f)
          ) {
            Text(
              text = "Currency (${activeCurrency.code})",
              fontSize = 11.sp,
              fontWeight = if (selectedTab == 0) FontWeight.Bold else FontWeight.Medium,
              color = if (selectedTab == 0) MintPrimaryDark else TextMuted,
              modifier = Modifier.padding(vertical = 8.dp),
              textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
          }

          Surface(
            onClick = { selectedTab = 1 },
            shape = RoundedCornerShape(8.dp),
            color = if (selectedTab == 1) Color.White else Color.Transparent,
            shadowElevation = if (selectedTab == 1) 2.dp else 0.dp,
            modifier = Modifier.weight(1f)
          ) {
            Text(
              text = "Language (${activeLanguage.name.split(" ")[0]})",
              fontSize = 11.sp,
              fontWeight = if (selectedTab == 1) FontWeight.Bold else FontWeight.Medium,
              color = if (selectedTab == 1) MintPrimaryDark else TextMuted,
              modifier = Modifier.padding(vertical = 8.dp),
              textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // List
        LazyColumn(
          modifier = Modifier
            .fillMaxWidth()
            .heightIn(max = 260.dp),
          verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
          if (selectedTab == 0) {
            items(MarketplaceData.currencies) { cur ->
              val isSelected = cur.code == activeCurrency.code
              Surface(
                onClick = {
                  onSelectCurrency(cur)
                  onDismiss()
                },
                shape = RoundedCornerShape(10.dp),
                color = if (isSelected) MintContainer else FrostedBackground,
                border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) MintPrimary else Color.Transparent),
                modifier = Modifier.fillMaxWidth()
              ) {
                Row(
                  modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Column {
                    Text(text = cur.name, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextDark)
                    Text(text = "1 USD = ${cur.rateToUsd} ${cur.code}", fontSize = 10.sp, color = TextMuted)
                  }
                  if (isSelected) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = MintPrimaryDark, modifier = Modifier.size(18.dp))
                  }
                }
              }
            }
          } else {
            items(MarketplaceData.languages) { lang ->
              val isSelected = lang.code == activeLanguage.code
              Surface(
                onClick = {
                  onSelectLanguage(lang)
                  onDismiss()
                },
                shape = RoundedCornerShape(10.dp),
                color = if (isSelected) MintContainer else FrostedBackground,
                border = androidx.compose.foundation.BorderStroke(1.dp, if (isSelected) MintPrimary else Color.Transparent),
                modifier = Modifier.fillMaxWidth()
              ) {
                Row(
                  modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = lang.flagEmoji, fontSize = 18.sp)
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(text = lang.name, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextDark)
                  }
                  if (isSelected) {
                    Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = MintPrimaryDark, modifier = Modifier.size(18.dp))
                  }
                }
              }
            }
          }
        }
      }
    }
  }
}
