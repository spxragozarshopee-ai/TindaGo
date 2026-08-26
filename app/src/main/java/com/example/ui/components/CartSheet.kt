package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.model.CartItem
import com.example.model.Voucher
import com.example.ui.theme.*

@Composable
fun CartSheet(
  cartItems: List<CartItem>,
  userCoins: Int,
  isCoinRedeemed: Boolean,
  appliedVoucher: Voucher?,
  availableVouchers: List<Voucher>,
  onDismiss: () -> Unit,
  onUpdateQuantity: (String, Int) -> Unit,
  onToggleItemChecked: (String) -> Unit,
  onToggleSelectAll: () -> Unit,
  onToggleCoins: () -> Unit,
  onApplyVoucher: (Voucher?) -> Unit,
  onCheckout: () -> Unit,
  formatPrice: (Double) -> String
) {
  val selectedItems = cartItems.filter { it.isChecked }
  val rawSubtotal = selectedItems.sumOf { it.product.priceUsd * it.quantity }
  val voucherDiscount = appliedVoucher?.let { 15.0.coerceAtMost(rawSubtotal * 0.25) } ?: 0.0
  val coinsDiscount = if (isCoinRedeemed) (userCoins * 0.01).coerceAtMost(5.0) else 0.0
  val finalTotal = (rawSubtotal - voucherDiscount - coinsDiscount).coerceAtLeast(0.0)

  var showVoucherPicker by remember { mutableStateOf(false) }

  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false)
  ) {
    Surface(
      shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
      color = Color.White,
      modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.94f)
        .padding(top = 32.dp)
        .testTag("cart_sheet_dialog")
    ) {
      Column(modifier = Modifier.fillMaxSize()) {
        // Frosted Header
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .background(FrostedBackground)
            .border(1.dp, GlassBorderEmerald, RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp))
            .padding(horizontal = 16.dp, vertical = 12.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.ShoppingCart, contentDescription = null, tint = MintPrimary)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "Shopping Cart (${cartItems.size})",
              fontSize = 16.sp,
              fontWeight = FontWeight.Bold,
              color = TextDark
            )
          }

          IconButton(onClick = onDismiss) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
          }
        }

        HorizontalDivider(color = BorderLight)

        if (cartItems.isEmpty()) {
          // Empty State
          Box(
            modifier = Modifier
              .weight(1f)
              .fillMaxWidth(),
            contentAlignment = Alignment.Center
          ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
              Text(text = "🛒", fontSize = 54.sp)
              Spacer(modifier = Modifier.height(10.dp))
              Text(
                text = "Your Cart is Empty",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
              )
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = "Explore today's Mint Fresh Deals to add items",
                fontSize = 12.sp,
                color = TextMuted
              )
            }
          }
        } else {
          // List of items
          LazyColumn(
            modifier = Modifier
              .weight(1f)
              .padding(horizontal = 14.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            items(cartItems) { item ->
              FrostedCartItemRow(
                item = item,
                onUpdateQuantity = { qty -> onUpdateQuantity(item.product.id, qty) },
                onToggleChecked = { onToggleItemChecked(item.product.id) },
                formatPrice = formatPrice
              )
            }

            item {
              Spacer(modifier = Modifier.height(8.dp))

              // Voucher Selection Card
              Surface(
                onClick = { showVoucherPicker = !showVoucherPicker },
                shape = RoundedCornerShape(12.dp),
                color = FrostedBackground,
                border = androidx.compose.foundation.BorderStroke(1.dp, GlassBorderEmerald),
                modifier = Modifier.fillMaxWidth()
              ) {
                Row(
                  modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(imageVector = Icons.Default.ConfirmationNumber, contentDescription = null, tint = MintPrimary, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                      text = appliedVoucher?.title ?: "Select or Enter Voucher Code",
                      fontSize = 12.sp,
                      fontWeight = FontWeight.Bold,
                      color = if (appliedVoucher != null) MintPrimaryDark else TextDark
                    )
                  }
                  Text(
                    text = if (appliedVoucher != null) "Change >" else "Apply >",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = MintPrimary
                  )
                }
              }

              if (showVoucherPicker) {
                Spacer(modifier = Modifier.height(6.dp))
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                  availableVouchers.forEach { voucher ->
                    val isApplied = appliedVoucher?.id == voucher.id
                    Surface(
                      onClick = {
                        onApplyVoucher(if (isApplied) null else voucher)
                        showVoucherPicker = false
                      },
                      shape = RoundedCornerShape(8.dp),
                      color = if (isApplied) MintContainer else Color.White,
                      border = androidx.compose.foundation.BorderStroke(1.dp, if (isApplied) MintPrimary else BorderLight),
                      modifier = Modifier.fillMaxWidth()
                    ) {
                      Row(
                        modifier = Modifier.padding(10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                      ) {
                        Column {
                          Text(text = voucher.title, fontSize = 11.sp, fontWeight = FontWeight.Bold, color = TextDark)
                          Text(text = voucher.discountText, fontSize = 10.sp, color = MintPrimary)
                        }
                        Text(
                          text = if (isApplied) "Applied ✓" else "Use",
                          fontSize = 11.sp,
                          fontWeight = FontWeight.Bold,
                          color = if (isApplied) MintPrimaryDark else MintPrimary
                        )
                      }
                    }
                  }
                }
              }

              Spacer(modifier = Modifier.height(8.dp))

              // Coins Redemption Row
              Surface(
                shape = RoundedCornerShape(12.dp),
                color = FrostedBackground,
                border = androidx.compose.foundation.BorderStroke(1.dp, GlassBorderEmerald),
                modifier = Modifier.fillMaxWidth()
              ) {
                Row(
                  modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                  horizontalArrangement = Arrangement.SpaceBetween,
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "🪙", fontSize = 18.sp)
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                      Text(text = "Redeem $userCoins Mint Coins", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = TextDark)
                      Text(text = "Save $${String.format("%.2f", (userCoins * 0.01).coerceAtMost(5.0))}", fontSize = 10.sp, color = TextMuted)
                    }
                  }

                  Switch(
                    checked = isCoinRedeemed,
                    onCheckedChange = { onToggleCoins() },
                    colors = SwitchDefaults.colors(
                      checkedThumbColor = Color.White,
                      checkedTrackColor = MintPrimary
                    )
                  )
                }
              }
            }
          }
        }

        // Bottom Checkout Bar
        Surface(
          color = GlassWhite85,
          border = androidx.compose.foundation.BorderStroke(1.dp, GlassBorderEmerald),
          shadowElevation = 8.dp,
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = "Total (${selectedItems.size} items):",
                fontSize = 11.sp,
                color = TextMuted
              )
              Text(
                text = formatPrice(finalTotal),
                fontSize = 18.sp,
                fontWeight = FontWeight.Black,
                color = MintPrimaryDark
              )
            }

            Button(
              onClick = {
                onCheckout()
                onDismiss()
              },
              enabled = selectedItems.isNotEmpty(),
              shape = RoundedCornerShape(12.dp),
              colors = ButtonDefaults.buttonColors(
                containerColor = MintPrimary,
                contentColor = Color.White
              ),
              modifier = Modifier
                .height(44.dp)
                .testTag("checkout_btn")
            ) {
              Text(
                text = "Check Out",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
              )
            }
          }
        }
      }
    }
  }
}

@Composable
fun FrostedCartItemRow(
  item: CartItem,
  onUpdateQuantity: (Int) -> Unit,
  onToggleChecked: () -> Unit,
  formatPrice: (Double) -> String,
  modifier: Modifier = Modifier
) {
  Surface(
    shape = RoundedCornerShape(14.dp),
    color = Color.White,
    border = androidx.compose.foundation.BorderStroke(1.dp, GlassBorderEmerald.copy(alpha = 0.6f)),
    shadowElevation = 1.dp,
    modifier = modifier.fillMaxWidth()
  ) {
    Row(
      modifier = Modifier.padding(10.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Checkbox(
        checked = item.isChecked,
        onCheckedChange = { onToggleChecked() },
        colors = CheckboxDefaults.colors(
          checkedColor = MintPrimary,
          uncheckedColor = TextMuted
        )
      )

      Box(
        modifier = Modifier
          .size(60.dp)
          .clip(RoundedCornerShape(8.dp))
          .background(Color(0xFFE8F5E9)),
        contentAlignment = Alignment.Center
      ) {
        Text(text = item.product.iconEmoji, fontSize = 28.sp)
      }

      Spacer(modifier = Modifier.width(10.dp))

      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = item.product.title,
          fontSize = 11.sp,
          fontWeight = FontWeight.SemiBold,
          color = TextDark,
          maxLines = 1
        )
        Text(
          text = "Opt: ${item.selectedVariant}",
          fontSize = 9.sp,
          color = TextMuted
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
          text = formatPrice(item.product.priceUsd),
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          color = MintPrimaryDark
        )
      }

      // Quantity controls
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
      ) {
        IconButton(
          onClick = { if (item.quantity > 1) onUpdateQuantity(item.quantity - 1) else onUpdateQuantity(0) },
          modifier = Modifier
            .size(26.dp)
            .clip(CircleShape)
            .background(FrostedBackground)
        ) {
          Icon(
            imageVector = if (item.quantity == 1) Icons.Default.Delete else Icons.Default.Remove,
            contentDescription = null,
            tint = if (item.quantity == 1) FlashRed else TextDark,
            modifier = Modifier.size(14.dp)
          )
        }

        Text(
          text = "${item.quantity}",
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          color = TextDark
        )

        IconButton(
          onClick = { onUpdateQuantity(item.quantity + 1) },
          modifier = Modifier
            .size(26.dp)
            .clip(CircleShape)
            .background(FrostedBackground)
        ) {
          Icon(imageVector = Icons.Default.Add, contentDescription = null, modifier = Modifier.size(14.dp))
        }
      }
    }
  }
}
