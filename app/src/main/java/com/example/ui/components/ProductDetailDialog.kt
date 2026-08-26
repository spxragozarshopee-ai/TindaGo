package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import com.example.model.Product
import com.example.ui.theme.*

@Composable
fun ProductDetailDialog(
  product: Product,
  isFavorite: Boolean,
  onDismiss: () -> Unit,
  onAddToCart: (Product, Int, String) -> Unit,
  onToggleFavorite: (String) -> Unit,
  formatPrice: (Double) -> String
) {
  var selectedVariant by remember { mutableStateOf(product.variants.firstOrNull() ?: "Standard") }
  var quantity by remember { mutableIntStateOf(1) }

  Dialog(
    onDismissRequest = onDismiss,
    properties = DialogProperties(usePlatformDefaultWidth = false)
  ) {
    Surface(
      shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
      color = Color.White,
      modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight(0.92f)
        .padding(top = 28.dp)
        .testTag("product_detail_modal")
    ) {
      Column(modifier = Modifier.fillMaxSize()) {
        // Modal Header with Close & Favorite buttons
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          IconButton(onClick = onDismiss) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
          }
          Text(
            text = "Product Details",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
          )
          IconButton(onClick = { onToggleFavorite(product.id) }) {
            Icon(
              imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
              contentDescription = "Favorite",
              tint = if (isFavorite) FlashRed else TextDark
            )
          }
        }

        HorizontalDivider(color = BorderLight)

        // Scrollable content
        Column(
          modifier = Modifier
            .weight(1f)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
        ) {
          // Visual Hero Box
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .height(200.dp)
              .clip(RoundedCornerShape(16.dp))
              .background(LightSurfaceVariant),
            contentAlignment = Alignment.Center
          ) {
            Text(text = product.iconEmoji, fontSize = 84.sp)

            // Discount Badge
            Box(
              modifier = Modifier
                .align(Alignment.TopStart)
                .padding(10.dp)
                .clip(RoundedCornerShape(6.dp))
                .background(FlashRed)
                .padding(horizontal = 8.dp, vertical = 3.dp)
            ) {
              Text(
                text = "-${product.discountPercent}% OFF",
                fontSize = 11.sp,
                fontWeight = FontWeight.Black,
                color = Color.White
              )
            }

            if (product.isMall) {
              Box(
                modifier = Modifier
                  .align(Alignment.TopEnd)
                  .padding(10.dp)
                  .clip(RoundedCornerShape(6.dp))
                  .background(MallBlue)
                  .padding(horizontal = 8.dp, vertical = 3.dp)
              ) {
                Text(
                  text = "100% AUTHENTIC MALL",
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold,
                  color = Color.White
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          // Price Tag + Savings Calculation
          Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Text(
              text = formatPrice(product.priceUsd),
              fontSize = 24.sp,
              fontWeight = FontWeight.Black,
              color = MintPrimaryDark
            )
            Text(
              text = formatPrice(product.originalPriceUsd),
              fontSize = 14.sp,
              color = TextMuted,
              textDecoration = TextDecoration.LineThrough
            )
            Surface(
              shape = RoundedCornerShape(4.dp),
              color = MintContainer
            ) {
              Text(
                text = "Save ${formatPrice(product.originalPriceUsd - product.priceUsd)}",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = MintPrimaryDark,
                modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
              )
            }
          }

          Spacer(modifier = Modifier.height(8.dp))

          // Title
          Text(
            text = product.title,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark,
            lineHeight = 22.sp
          )

          Spacer(modifier = Modifier.height(10.dp))

          // Ratings, Reviews, Sold stats
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = StarGold,
                modifier = Modifier.size(16.dp)
              )
              Spacer(modifier = Modifier.width(4.dp))
              Text(
                text = "${product.rating}",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark
              )
              Text(
                text = " (${product.reviewCount} reviews)",
                fontSize = 12.sp,
                color = TextMuted
              )
            }

            Text(
              text = "•   ${product.soldCount}",
              fontSize = 12.sp,
              fontWeight = FontWeight.Medium,
              color = TextDark
            )

            Text(
              text = "•   ${product.location}",
              fontSize = 12.sp,
              color = SoftSage
            )
          }

          Spacer(modifier = Modifier.height(14.dp))
          HorizontalDivider(color = BorderLight)
          Spacer(modifier = Modifier.height(14.dp))

          // Variant Selection
          Text(
            text = "Select Variant / Option:",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
          )
          Spacer(modifier = Modifier.height(8.dp))

          LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            items(product.variants) { variant ->
              val isSelected = selectedVariant == variant
              Surface(
                onClick = { selectedVariant = variant },
                shape = RoundedCornerShape(8.dp),
                color = if (isSelected) MintContainer else LightSurfaceVariant,
                border = if (isSelected) androidx.compose.foundation.BorderStroke(1.5.dp, MintPrimary) else null,
                modifier = Modifier.testTag("variant_option_$variant")
              ) {
                Text(
                  text = variant,
                  fontSize = 12.sp,
                  fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                  color = if (isSelected) MintPrimaryDark else TextDark,
                  modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(16.dp))

          // Quantity Stepper
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(
              text = "Quantity (In Stock: ${product.stockLeft})",
              fontSize = 13.sp,
              fontWeight = FontWeight.Bold,
              color = TextDark
            )

            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              IconButton(
                onClick = { if (quantity > 1) quantity-- },
                modifier = Modifier
                  .size(32.dp)
                  .clip(CircleShape)
                  .background(LightSurfaceVariant)
              ) {
                Icon(imageVector = Icons.Default.Remove, contentDescription = "Decrease", modifier = Modifier.size(16.dp))
              }

              Text(
                text = "$quantity",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = TextDark,
                modifier = Modifier.padding(horizontal = 8.dp)
              )

              IconButton(
                onClick = { if (quantity < product.stockLeft) quantity++ },
                modifier = Modifier
                  .size(32.dp)
                  .clip(CircleShape)
                  .background(LightSurfaceVariant)
              ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Increase", modifier = Modifier.size(16.dp))
              }
            }
          }

          Spacer(modifier = Modifier.height(16.dp))
          HorizontalDivider(color = BorderLight)
          Spacer(modifier = Modifier.height(14.dp))

          // Description
          Text(
            text = "Product Specifications & Highlights",
            fontSize = 13.sp,
            fontWeight = FontWeight.Bold,
            color = TextDark
          )
          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = product.description,
            fontSize = 13.sp,
            color = Color(0xFF374151),
            lineHeight = 19.sp
          )

          Spacer(modifier = Modifier.height(14.dp))

          // Delivery & Guarantee box
          Surface(
            shape = RoundedCornerShape(10.dp),
            color = LightSurfaceVariant,
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(12.dp)) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.LocalShipping, contentDescription = null, tint = MintPrimary, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "Guaranteed Free Delivery within 48 Hours", fontSize = 12.sp, fontWeight = FontWeight.Bold, color = MintPrimaryDark)
              }
              Spacer(modifier = Modifier.height(4.dp))
              Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = Icons.Default.VerifiedUser, contentDescription = null, tint = SoftSage, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "100% Genuine Guarantee • 15 Days Free Return", fontSize = 12.sp, color = TextDark)
              }
            }
          }
        }

        // Bottom CTA Action Bar
        Surface(
          shadowElevation = 8.dp,
          color = Color.White,
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
          ) {
            OutlinedButton(
              onClick = {
                onAddToCart(product, quantity, selectedVariant)
                onDismiss()
              },
              shape = RoundedCornerShape(10.dp),
              colors = ButtonDefaults.outlinedButtonColors(
                contentColor = MintPrimaryDark
              ),
              border = androidx.compose.foundation.BorderStroke(1.5.dp, MintPrimary),
              modifier = Modifier
                .weight(1f)
                .height(46.dp)
                .testTag("modal_add_to_cart_btn")
            ) {
              Icon(imageVector = Icons.Default.AddShoppingCart, contentDescription = null, modifier = Modifier.size(18.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text(text = "Add to Cart", fontSize = 13.sp, fontWeight = FontWeight.Bold)
            }

            Button(
              onClick = {
                onAddToCart(product, quantity, selectedVariant)
                onDismiss()
              },
              shape = RoundedCornerShape(10.dp),
              colors = ButtonDefaults.buttonColors(
                containerColor = DealOrange,
                contentColor = Color.White
              ),
              modifier = Modifier
                .weight(1f)
                .height(46.dp)
                .testTag("modal_buy_now_btn")
            ) {
              Text(text = "Buy Now", fontSize = 14.sp, fontWeight = FontWeight.Black)
            }
          }
        }
      }
    }
  }
}
