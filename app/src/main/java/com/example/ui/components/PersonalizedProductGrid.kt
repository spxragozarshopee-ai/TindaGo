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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Product
import com.example.ui.theme.*

@Composable
fun PersonalizedProductGrid(
  products: List<Product>,
  selectedTab: String,
  favoriteProductIds: Set<String>,
  onSelectTab: (String) -> Unit,
  onProductClick: (Product) -> Unit,
  onAddToCart: (Product) -> Unit,
  onToggleFavorite: (String) -> Unit,
  formatPrice: (Double) -> String,
  modifier: Modifier = Modifier
) {
  val feedTabs = listOf("All", "Popular 🔥", "Top Sales", "Under $10", "Eco & Fresh", "Official Mall")

  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = 14.dp, vertical = 8.dp)
  ) {
    // Header
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
          modifier = Modifier
            .width(4.dp)
            .height(16.dp)
            .clip(RoundedCornerShape(2.dp))
            .background(MintPrimary)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
          text = "JUST FOR YOU",
          fontSize = 14.sp,
          fontWeight = FontWeight.Black,
          color = TextDark,
          letterSpacing = 0.5.sp
        )
      }

      Text(
        text = "Curated Feed",
        fontSize = 11.sp,
        fontWeight = FontWeight.Medium,
        color = MintPrimaryDark
      )
    }

    Spacer(modifier = Modifier.height(8.dp))

    // Frosted Pill Filter Tabs
    LazyRow(
      horizontalArrangement = Arrangement.spacedBy(6.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      items(feedTabs) { tab ->
        val isSelected = selectedTab == tab
        Surface(
          onClick = { onSelectTab(tab) },
          shape = RoundedCornerShape(16.dp),
          color = if (isSelected) MintPrimary else GlassWhite85,
          border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (isSelected) MintPrimary else GlassBorderEmerald
          ),
          modifier = Modifier.testTag("feed_tab_$tab")
        ) {
          Text(
            text = tab,
            fontSize = 11.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) Color.White else TextSlate700,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
          )
        }
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    // 2-Column Responsive Card Grid
    val rows = products.chunked(2)
    rows.forEach { rowItems ->
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
      ) {
        rowItems.forEach { product ->
          FrostedProductCard(
            product = product,
            isFavorite = favoriteProductIds.contains(product.id),
            onProductClick = { onProductClick(product) },
            onAddToCart = { onAddToCart(product) },
            onToggleFavorite = { onToggleFavorite(product.id) },
            formatPrice = formatPrice,
            modifier = Modifier.weight(1f)
          )
        }
        if (rowItems.size == 1) {
          Spacer(modifier = Modifier.weight(1f))
        }
      }
      Spacer(modifier = Modifier.height(10.dp))
    }
  }
}

@Composable
fun FrostedProductCard(
  product: Product,
  isFavorite: Boolean,
  onProductClick: () -> Unit,
  onAddToCart: () -> Unit,
  onToggleFavorite: () -> Unit,
  formatPrice: (Double) -> String,
  modifier: Modifier = Modifier
) {
  Surface(
    shape = RoundedCornerShape(14.dp),
    color = Color.White,
    border = androidx.compose.foundation.BorderStroke(1.dp, GlassBorderEmerald.copy(alpha = 0.7f)),
    shadowElevation = 1.dp,
    modifier = modifier
      .clickable { onProductClick() }
      .testTag("product_card_${product.id}")
  ) {
    Column {
      // Visual Container
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .height(120.dp)
          .clip(RoundedCornerShape(topStart = 14.dp, topEnd = 14.dp))
          .background(Color(0xFFF0FDF4)),
        contentAlignment = Alignment.Center
      ) {
        Text(text = product.iconEmoji, fontSize = 48.sp)

        // Discount Tag Top-Left
        Box(
          modifier = Modifier
            .align(Alignment.TopStart)
            .clip(RoundedCornerShape(bottomEnd = 8.dp))
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

        // Heart Wishlist Icon Top-Right
        Box(
          modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(6.dp)
            .size(24.dp)
            .clip(CircleShape)
            .background(GlassWhite85)
            .clickable { onToggleFavorite() },
          contentAlignment = Alignment.Center
        ) {
          Icon(
            imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
            contentDescription = "Favorite",
            tint = if (isFavorite) FlashRed else TextMuted,
            modifier = Modifier.size(14.dp)
          )
        }
      }

      // Details
      Column(modifier = Modifier.padding(8.dp)) {
        // Title
        Text(
          text = product.title,
          fontSize = 11.sp,
          fontWeight = FontWeight.Medium,
          color = TextSlate700,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.height(4.dp))

        // Price + Sale tag
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
          Text(
            text = formatPrice(product.priceUsd),
            fontSize = 13.sp,
            fontWeight = FontWeight.Black,
            color = MintPrimaryDark
          )

          Surface(
            shape = RoundedCornerShape(4.dp),
            color = Color(0xFFE8F5E9)
          ) {
            Text(
              text = "Sale",
              fontSize = 8.sp,
              fontWeight = FontWeight.Bold,
              color = MintPrimary,
              modifier = Modifier.padding(horizontal = 4.dp, vertical = 1.dp)
            )
          }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Ratings & Sold count row
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
              imageVector = Icons.Default.Star,
              contentDescription = null,
              tint = StarGold,
              modifier = Modifier.size(10.dp)
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(
              text = "${product.rating}",
              fontSize = 9.sp,
              fontWeight = FontWeight.Bold,
              color = TextDark
            )
            Text(
              text = " | ${product.soldCount}",
              fontSize = 9.sp,
              color = TextMuted
            )
          }

          // Mini Add to cart button
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
}
