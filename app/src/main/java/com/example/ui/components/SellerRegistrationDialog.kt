package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.window.Dialog
import com.example.ui.theme.*

@Composable
fun SellerRegistrationDialog(
  onDismiss: () -> Unit,
  onRegisterSuccess: (String) -> Unit
) {
  var shopName by remember { mutableStateOf("") }
  var ownerName by remember { mutableStateOf("") }
  var category by remember { mutableStateOf("Electronics & Gadgets") }
  var isSubmitted by remember { mutableStateOf(false) }

  Dialog(onDismissRequest = onDismiss) {
    Surface(
      shape = RoundedCornerShape(20.dp),
      color = Color.White,
      border = androidx.compose.foundation.BorderStroke(1.dp, GlassBorderEmerald),
      shadowElevation = 8.dp,
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .testTag("seller_registration_dialog")
    ) {
      Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // Header
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = "🏪", fontSize = 20.sp)
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "Seller Hub Registration",
              fontSize = 15.sp,
              fontWeight = FontWeight.Bold,
              color = TextDark
            )
          }
          IconButton(onClick = onDismiss, modifier = Modifier.size(24.dp)) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = TextMuted)
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        if (isSubmitted) {
          // Success view
          Column(
            modifier = Modifier.padding(vertical = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Box(
              modifier = Modifier
                .size(54.dp)
                .clip(CircleShape)
                .background(MintContainer),
              contentAlignment = Alignment.Center
            ) {
              Icon(imageVector = Icons.Default.Check, contentDescription = null, tint = MintPrimaryDark, modifier = Modifier.size(28.dp))
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
              text = "Welcome aboard, $shopName!",
              fontSize = 15.sp,
              fontWeight = FontWeight.Black,
              color = TextDark
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "Your store has been created with 0% Commission for 30 Days and free promotional tools activated.",
              fontSize = 11.sp,
              color = TextMuted,
              lineHeight = 15.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
              onClick = onDismiss,
              shape = RoundedCornerShape(10.dp),
              colors = ButtonDefaults.buttonColors(containerColor = MintPrimary),
              modifier = Modifier.fillMaxWidth()
            ) {
              Text("Go to Seller Dashboard")
            }
          }
        } else {
          // Form View
          Surface(
            shape = RoundedCornerShape(10.dp),
            color = MintContainer.copy(alpha = 0.5f),
            modifier = Modifier.fillMaxWidth()
          ) {
            Text(
              text = "🎉 0% Commission + Free Shipping Logistics Program for the first 30 days!",
              fontSize = 10.sp,
              fontWeight = FontWeight.SemiBold,
              color = MintPrimaryDark,
              modifier = Modifier.padding(8.dp)
            )
          }

          Spacer(modifier = Modifier.height(12.dp))

          OutlinedTextField(
            value = shopName,
            onValueChange = { shopName = it },
            label = { Text("Shop Name", fontSize = 11.sp) },
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()
          )

          Spacer(modifier = Modifier.height(8.dp))

          OutlinedTextField(
            value = ownerName,
            onValueChange = { ownerName = it },
            label = { Text("Owner / Business Name", fontSize = 11.sp) },
            singleLine = true,
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier.fillMaxWidth()
          )

          Spacer(modifier = Modifier.height(16.dp))

          Button(
            onClick = {
              if (shopName.isNotBlank()) {
                isSubmitted = true
                onRegisterSuccess(shopName)
              }
            },
            enabled = shopName.isNotBlank(),
            shape = RoundedCornerShape(10.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MintPrimary),
            modifier = Modifier.fillMaxWidth()
          ) {
            Text("Complete Store Setup Free", fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }
}
