package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*

@Composable
fun FrostedBottomNav(
  selectedTab: String,
  onTabSelected: (String) -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    color = GlassWhite85,
    border = androidx.compose.foundation.BorderStroke(1.dp, GlassBorderEmerald.copy(alpha = 0.5f)),
    shadowElevation = 8.dp,
    modifier = modifier.fillMaxWidth()
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 8.dp, vertical = 6.dp),
      horizontalArrangement = Arrangement.SpaceAround,
      verticalAlignment = Alignment.CenterVertically
    ) {
      NavItem(
        icon = Icons.Default.Home,
        label = "Home",
        isSelected = selectedTab == "home",
        onClick = { onTabSelected("home") }
      )
      NavItem(
        icon = Icons.Outlined.GridView,
        label = "Categories",
        isSelected = selectedTab == "categories",
        onClick = { onTabSelected("categories") }
      )
      NavItem(
        icon = Icons.Outlined.FavoriteBorder,
        label = "Feed",
        isSelected = selectedTab == "feed",
        onClick = { onTabSelected("feed") }
      )
      NavItem(
        icon = Icons.Outlined.Notifications,
        label = "Notifications",
        isSelected = selectedTab == "notifications",
        onClick = { onTabSelected("notifications") }
      )
      NavItem(
        icon = Icons.Outlined.Person,
        label = "Account",
        isSelected = selectedTab == "account",
        onClick = { onTabSelected("account") }
      )
    }
  }
}

@Composable
private fun NavItem(
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  label: String,
  isSelected: Boolean,
  onClick: () -> Unit
) {
  Column(
    modifier = Modifier
      .clickable { onClick() }
      .padding(horizontal = 8.dp, vertical = 2.dp)
      .testTag("nav_tab_$label"),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Icon(
      imageVector = icon,
      contentDescription = label,
      tint = if (isSelected) MintPrimary else TextSlate400,
      modifier = Modifier.size(20.dp)
    )
    Spacer(modifier = Modifier.height(2.dp))
    Text(
      text = label,
      fontSize = 10.sp,
      fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
      color = if (isSelected) MintPrimary else TextSlate400
    )
  }
}
