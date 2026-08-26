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
fun WalletCoinsDialog(
  currentCoins: Int,
  walletBalance: Double,
  onDismiss: () -> Unit,
  onClaimCoins: (Int) -> Unit,
  formatPrice: (Double) -> String
) {
  var claimedToday by remember { mutableStateOf(false) }

  Dialog(onDismissRequest = onDismiss) {
    Surface(
      shape = RoundedCornerShape(20.dp),
      color = Color.White,
      border = androidx.compose.foundation.BorderStroke(1.dp, GlassBorderEmerald),
      shadowElevation = 8.dp,
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)
        .testTag("wallet_coins_dialog")
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
          Text(
            text = "MintPay & Coin Rewards",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
          )
          IconButton(onClick = onDismiss, modifier = Modifier.size(24.dp)) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Close", tint = TextMuted)
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Wallet Balance Card
        Surface(
          shape = RoundedCornerShape(16.dp),
          color = FrostedBackground,
          border = androidx.compose.foundation.BorderStroke(1.dp, GlassBorderEmerald),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier.padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(text = "MintPay Balance", fontSize = 11.sp, color = TextMuted)
              Text(
                text = formatPrice(walletBalance),
                fontSize = 20.sp,
                fontWeight = FontWeight.Black,
                color = MintPrimaryDark
              )
            }
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = MintPrimary
            ) {
              Text(
                text = "+ Top Up",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Coin Reward Card
        Surface(
          shape = RoundedCornerShape(16.dp),
          color = Color(0xFFFEF3C7).copy(alpha = 0.5f),
          border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFFDE68A)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(
            modifier = Modifier.padding(14.dp),
            horizontalAlignment = Alignment.CenterHorizontally
          ) {
            Text(text = "🪙", fontSize = 36.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "$currentCoins Mint Coins",
              fontSize = 16.sp,
              fontWeight = FontWeight.Black,
              color = TextDark
            )
            Text(
              text = "Use 100 coins = $1.00 instant checkout discount",
              fontSize = 10.sp,
              color = TextMuted
            )

            Spacer(modifier = Modifier.height(10.dp))

            Button(
              onClick = {
                if (!claimedToday) {
                  onClaimCoins(50)
                  claimedToday = true
                }
              },
              enabled = !claimedToday,
              shape = RoundedCornerShape(10.dp),
              colors = ButtonDefaults.buttonColors(
                containerColor = StarGold,
                contentColor = Color.White,
                disabledContainerColor = Color(0xFFE2E8F0),
                disabledContentColor = TextMuted
              ),
              modifier = Modifier.fillMaxWidth()
            ) {
              Text(
                text = if (claimedToday) "Claimed (+50 Coins) ✓" else "Daily Check-In: +50 Coins ✨",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
          text = "Earn coins with every purchase • 15% cashback on Official Mall",
          fontSize = 10.sp,
          color = TextMuted
        )
      }
    }
  }
}
