package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.MarketplaceData
import com.example.model.Currency
import com.example.model.Language
import com.example.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketTopBar(
  searchQuery: String,
  isSearchActive: Boolean,
  searchHistory: List<String>,
  cartItemCount: Int,
  userMintCoins: Int,
  userWalletBalance: Double,
  activeCurrency: Currency,
  activeLanguage: Language,
  onSearchQueryChange: (String) -> Unit,
  onSearchSubmit: (String) -> Unit,
  onClearSearch: () -> Unit,
  onSetSearchActive: (Boolean) -> Unit,
  onOpenCart: () -> Unit,
  onOpenCurrencyLanguageModal: () -> Unit,
  onDailyCheckIn: () -> Unit,
  onSelectCategory: (String) -> Unit,
  selectedCategory: String,
  modifier: Modifier = Modifier
) {
  val focusManager = LocalFocusManager.current

  Column(
    modifier = modifier
      .fillMaxWidth()
      .background(
        Brush.verticalGradient(
          colors = listOf(
            MintPrimaryDark,
            MintPrimary
          )
        )
      )
      .statusBarsPadding()
      .padding(horizontal = 14.dp, vertical = 8.dp)
  ) {
    // Row 1: Brand Logo & User Quick Action Pills (Language/Currency, Coins & Cart)
    Row(
      modifier = Modifier.fillMaxWidth(),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween
    ) {
      // Brand Logo & Slogan
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable { onSelectCategory("All") }
      ) {
        Box(
          modifier = Modifier
            .size(34.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White),
          contentAlignment = Alignment.Center
        ) {
          Text(text = "🛍️", fontSize = 18.sp)
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
              text = "Market",
              fontSize = 18.sp,
              fontWeight = FontWeight.Black,
              color = Color.White,
              letterSpacing = (-0.5).sp
            )
            Text(
              text = "Green",
              fontSize = 18.sp,
              fontWeight = FontWeight.Black,
              color = Color(0xFFD1FAE5),
              letterSpacing = (-0.5).sp
            )
          }
          Text(
            text = "Fresh Deals • 100% Mall",
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            color = Color.White.copy(alpha = 0.85f)
          )
        }
      }

      // Action Badges (Currency/Lang selector, Coins pill, Cart Badge)
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(6.dp)
      ) {
        // Currency & Language Selector Chip
        Surface(
          onClick = onOpenCurrencyLanguageModal,
          shape = RoundedCornerShape(20.dp),
          color = Color.White.copy(alpha = 0.18f),
          modifier = Modifier.testTag("lang_currency_button")
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
          ) {
            Text(text = activeLanguage.flagEmoji, fontSize = 13.sp)
            Text(
              text = activeCurrency.code,
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = Color.White
            )
          }
        }

        // Daily Check-In / Coins Badge
        Surface(
          onClick = onDailyCheckIn,
          shape = RoundedCornerShape(20.dp),
          color = Color(0xFFF59E0B).copy(alpha = 0.9f),
          modifier = Modifier.testTag("daily_coins_button")
        ) {
          Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(3.dp)
          ) {
            Text(text = "🪙", fontSize = 11.sp)
            Text(
              text = "$userMintCoins",
              fontSize = 11.sp,
              fontWeight = FontWeight.ExtraBold,
              color = Color.White
            )
          }
        }

        // Cart Icon with Badge
        Box(contentAlignment = Alignment.TopEnd) {
          IconButton(
            onClick = onOpenCart,
            modifier = Modifier
              .size(38.dp)
              .testTag("cart_button")
          ) {
            Icon(
              imageVector = Icons.Default.ShoppingCart,
              contentDescription = "Shopping Cart",
              tint = Color.White
            )
          }
          if (cartItemCount > 0) {
            Box(
              modifier = Modifier
                .offset(x = 2.dp, y = (-2).dp)
                .clip(CircleShape)
                .background(DealOrange)
                .padding(horizontal = 5.dp, vertical = 1.dp),
              contentAlignment = Alignment.Center
            ) {
              Text(
                text = if (cartItemCount > 99) "99+" else "$cartItemCount",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
              )
            }
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(10.dp))

    // Row 2: Omnibar Search with Auto-Suggest & Scan/Mic icons
    Surface(
      shape = RoundedCornerShape(12.dp),
      color = Color.White,
      shadowElevation = 3.dp,
      modifier = Modifier
        .fillMaxWidth()
        .height(46.dp)
    ) {
      Row(
        modifier = Modifier
          .fillMaxSize()
          .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
      ) {
        Icon(
          imageVector = Icons.Default.Search,
          contentDescription = "Search",
          tint = MintPrimary,
          modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))

        Box(modifier = Modifier.weight(1f)) {
          if (searchQuery.isEmpty()) {
            Text(
              text = "Search ANC Earbuds, Matcha, 6.6 Deals...",
              fontSize = 13.sp,
              color = TextMuted,
              maxLines = 1,
              overflow = TextOverflow.Ellipsis
            )
          }
          TextField(
            value = searchQuery,
            onValueChange = {
              onSearchQueryChange(it)
              onSetSearchActive(true)
            },
            singleLine = true,
            colors = TextFieldDefaults.colors(
              focusedContainerColor = Color.Transparent,
              unfocusedContainerColor = Color.Transparent,
              disabledContainerColor = Color.Transparent,
              focusedIndicatorColor = Color.Transparent,
              unfocusedIndicatorColor = Color.Transparent,
              focusedTextColor = TextDark,
              unfocusedTextColor = TextDark
            ),
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(
              onSearch = {
                focusManager.clearFocus()
                onSearchSubmit(searchQuery)
              }
            ),
            modifier = Modifier
              .fillMaxWidth()
              .testTag("search_input_field")
          )
        }

        if (searchQuery.isNotEmpty()) {
          IconButton(
            onClick = onClearSearch,
            modifier = Modifier.size(28.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Close,
              contentDescription = "Clear search",
              tint = TextMuted,
              modifier = Modifier.size(18.dp)
            )
          }
        }

        IconButton(
          onClick = { onSearchSubmit("Camera Scan Deal") },
          modifier = Modifier.size(28.dp)
        ) {
          Icon(
            imageVector = Icons.Outlined.PhotoCamera,
            contentDescription = "Visual Search",
            tint = MintPrimary,
            modifier = Modifier.size(19.dp)
          )
        }
      }
    }

    // Auto-suggest dropdown / Trending search tags when active
    AnimatedVisibility(
      visible = isSearchActive && (searchQuery.isNotEmpty() || searchHistory.isNotEmpty()),
      enter = fadeIn(),
      exit = fadeOut()
    ) {
      Surface(
        shape = RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp),
        color = Color.White,
        shadowElevation = 6.dp,
        modifier = Modifier
          .fillMaxWidth()
          .padding(top = 4.dp)
      ) {
        Column(modifier = Modifier.padding(12.dp)) {
          if (searchHistory.isNotEmpty()) {
            Row(
              modifier = Modifier.fillMaxWidth(),
              horizontalArrangement = Arrangement.SpaceBetween,
              verticalAlignment = Alignment.CenterVertically
            ) {
              Text(
                text = "Recent Searches",
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = TextMuted
              )
              Text(
                text = "Close",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = MintPrimary,
                modifier = Modifier.clickable { onSetSearchActive(false) }
              )
            }
            Spacer(modifier = Modifier.height(6.dp))
            LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
              items(searchHistory) { item ->
                Surface(
                  onClick = {
                    onSearchQueryChange(item)
                    onSearchSubmit(item)
                  },
                  shape = RoundedCornerShape(16.dp),
                  color = LightSurfaceVariant
                ) {
                  Text(
                    text = "🔍 $item",
                    fontSize = 11.sp,
                    color = TextDark,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                  )
                }
              }
            }
          }

          Spacer(modifier = Modifier.height(8.dp))
          Text(
            text = "🔥 Trending Deals & Suggestions",
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            color = TextMuted
          )
          Spacer(modifier = Modifier.height(4.dp))

          MarketplaceData.searchSuggestions
            .filter { searchQuery.isEmpty() || it.contains(searchQuery, ignoreCase = true) }
            .take(4)
            .forEach { suggestion ->
              Row(
                modifier = Modifier
                  .fillMaxWidth()
                  .clickable {
                    onSearchQueryChange(suggestion)
                    onSearchSubmit(suggestion)
                  }
                  .padding(vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Icon(
                  imageVector = Icons.Default.TrendingUp,
                  contentDescription = null,
                  tint = DealOrange,
                  modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                  text = suggestion,
                  fontSize = 13.sp,
                  color = TextDark,
                  fontWeight = FontWeight.Medium
                )
              }
            }
        }
      }
    }

    Spacer(modifier = Modifier.height(8.dp))

    // Category Fast Switch Strip
    LazyRow(
      horizontalArrangement = Arrangement.spacedBy(6.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      items(MarketplaceData.categoryList) { category ->
        val isSelected = selectedCategory == category
        Surface(
          onClick = { onSelectCategory(category) },
          shape = RoundedCornerShape(14.dp),
          color = if (isSelected) Color.White else Color.White.copy(alpha = 0.16f),
          modifier = Modifier.testTag("category_tab_$category")
        ) {
          Text(
            text = category,
            fontSize = 12.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            color = if (isSelected) MintPrimaryDark else Color.White,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
          )
        }
      }
    }
  }
}
