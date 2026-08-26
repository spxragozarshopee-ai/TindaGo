package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.MarketplaceData
import com.example.model.*
import com.example.ui.components.*
import com.example.ui.theme.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme {
        MarketGreenApp()
      }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketGreenApp() {
  val scope = rememberCoroutineScope()
  val snackbarHostState = remember { SnackbarHostState() }

  // State
  var currentTab by remember { mutableStateOf("home") }
  var searchQuery by remember { mutableStateOf("") }
  var isSearchActive by remember { mutableStateOf(false) }
  var searchHistory by remember { mutableStateOf(listOf("ANC Earbuds", "Matcha Powder", "Hydro Flask")) }
  var selectedCategory by remember { mutableStateOf("All") }
  var selectedFeedTab by remember { mutableStateOf("All") }
  var selectedFlashSlot by remember { mutableStateOf("12:00 Ongoing") }

  // User state
  var activeCurrency by remember { mutableStateOf(MarketplaceData.currencies[0]) }
  var activeLanguage by remember { mutableStateOf(MarketplaceData.languages[0]) }
  var userCoins by remember { mutableIntStateOf(240) }
  var userWalletBalance by remember { mutableDoubleStateOf(142.50) }
  val claimedVoucherIds = remember { mutableStateListOf<String>("v_new_user") }
  val favoriteProductIds = remember { mutableStateListOf<String>("fd_1", "prod_1") }

  // Cart state
  val cartItems = remember {
    mutableStateListOf(
      CartItem(
        product = MarketplaceData.flashDeals[0],
        quantity = 1,
        selectedVariant = "Sage Green",
        isChecked = true
      ),
      CartItem(
        product = MarketplaceData.allProducts[0],
        quantity = 2,
        selectedVariant = "Mint Sage Edition",
        isChecked = true
      )
    )
  }
  var isCoinRedeemedInCart by remember { mutableStateOf(false) }
  var appliedVoucher by remember { mutableStateOf<Voucher?>(MarketplaceData.dailyVouchers[0]) }

  // Modals visibility
  var showCartSheet by remember { mutableStateOf(false) }
  var showProductDetail by remember { mutableStateOf<Product?>(null) }
  var showWalletCoinsModal by remember { mutableStateOf(false) }
  var showSellerModal by remember { mutableStateOf(false) }
  var showCurrencyLangModal by remember { mutableStateOf(false) }

  // Countdown timer for Flash Deals (e.g. 3 hours 45 mins left)
  var countdownSeconds by remember { mutableLongStateOf(13542L) }
  LaunchedEffect(Unit) {
    while (true) {
      delay(1000)
      if (countdownSeconds > 0) {
        countdownSeconds--
      } else {
        countdownSeconds = 14400L
      }
    }
  }

  // Price Formatter Helper
  val formatPrice: (Double) -> String = { usdValue ->
    val converted = usdValue * activeCurrency.rateToUsd
    if (activeCurrency.code == "IDR" || activeCurrency.code == "VN") {
      "${activeCurrency.symbol}${String.format("%,.0f", converted)}"
    } else {
      "${activeCurrency.symbol}${String.format("%.2f", converted)}"
    }
  }

  // Filter products based on search and category
  val filteredFeedProducts = remember(searchQuery, selectedCategory, selectedFeedTab) {
    var list = MarketplaceData.allProducts
    if (searchQuery.isNotBlank()) {
      list = list.filter {
        it.title.contains(searchQuery, ignoreCase = true) ||
          it.category.contains(searchQuery, ignoreCase = true) ||
          it.description.contains(searchQuery, ignoreCase = true)
      }
    } else if (selectedCategory != "All") {
      list = when (selectedCategory) {
        "Flash Deals" -> MarketplaceData.flashDeals
        "Official Mall" -> list.filter { it.isMall }
        "Under $10" -> list.filter { it.priceUsd <= 10.0 }
        else -> list.filter { it.category.equals(selectedCategory, ignoreCase = true) }
      }
    }
    // Feed sub-filter
    when (selectedFeedTab) {
      "Popular 🔥" -> list.sortedByDescending { it.reviewCount }
      "Top Sales" -> list.sortedByDescending { it.rating }
      "Under $10" -> list.filter { it.priceUsd <= 10.0 }
      "Official Mall" -> list.filter { it.isMall }
      else -> list
    }
  }

  Scaffold(
    containerColor = FrostedBackground,
    snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
    bottomBar = {
      FrostedBottomNav(
        selectedTab = currentTab,
        onTabSelected = { tab ->
          currentTab = tab
          if (tab == "account") {
            showWalletCoinsModal = true
          }
        }
      )
    }
  ) { innerPadding ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
    ) {
      Column(modifier = Modifier.fillMaxSize()) {
        // Sticky Frosted Top Bar
        MarketTopBar(
          searchQuery = searchQuery,
          isSearchActive = isSearchActive,
          searchHistory = searchHistory,
          cartItemCount = cartItems.sumOf { it.quantity },
          userMintCoins = userCoins,
          userWalletBalance = userWalletBalance,
          activeCurrency = activeCurrency,
          activeLanguage = activeLanguage,
          onSearchQueryChange = { searchQuery = it },
          onSearchSubmit = { query ->
            searchQuery = query
            isSearchActive = false
            if (query.isNotBlank() && !searchHistory.contains(query)) {
              searchHistory = listOf(query) + searchHistory.take(5)
            }
          },
          onClearSearch = {
            searchQuery = ""
            isSearchActive = false
          },
          onSetSearchActive = { isSearchActive = it },
          onOpenCart = { showCartSheet = true },
          onOpenCurrencyLanguageModal = { showCurrencyLangModal = true },
          onDailyCheckIn = { showWalletCoinsModal = true },
          onSelectCategory = { cat ->
            selectedCategory = cat
            searchQuery = ""
          },
          selectedCategory = selectedCategory
        )

        // Main Scrollable Body
        LazyColumn(
          modifier = Modifier
            .weight(1f)
            .fillMaxWidth()
            .testTag("marketplace_main_feed")
        ) {
          if (currentTab == "home" || currentTab == "categories" || currentTab == "feed") {
            // 1. Dynamic Hero Carousel
            item {
              HeroCarousel(
                onBannerClick = { banner ->
                  scope.launch {
                    snackbarHostState.showSnackbar("Claimed ${banner.badge}: ${banner.voucherHighlight}")
                  }
                }
              )
            }

            // 2. Daily Savings Voucher Strip
            item {
              DailyVouchersBar(
                vouchers = MarketplaceData.dailyVouchers,
                claimedVoucherIds = claimedVoucherIds.toSet(),
                onClaimVoucher = { voucherId ->
                  if (!claimedVoucherIds.contains(voucherId)) {
                    claimedVoucherIds.add(voucherId)
                    scope.launch { snackbarHostState.showSnackbar("Voucher claimed! Automatically applied at checkout 🎉") }
                  }
                },
                onClaimAll = {
                  MarketplaceData.dailyVouchers.forEach { v ->
                    if (!claimedVoucherIds.contains(v.id)) claimedVoucherIds.add(v.id)
                  }
                  scope.launch { snackbarHostState.showSnackbar("All daily vouchers collected! 🎉") }
                }
              )
            }

            // 3. Quick Access Shortcut Tiles (Pastel Frosted Rounded 2xl)
            item {
              QuickAccessGrid(
                onItemClick = { item ->
                  when (item.id) {
                    "qa_flash" -> selectedCategory = "Flash Deals"
                    "qa_mall" -> selectedCategory = "Official Mall"
                    "qa_wallet" -> showWalletCoinsModal = true
                    "qa_rewards" -> showWalletCoinsModal = true
                    "qa_ship" -> scope.launch { snackbarHostState.showSnackbar("Free Shipping applied on orders over $0 today!") }
                    else -> selectedCategory = item.title
                  }
                }
              )
            }

            // 4. Flash Deals Section (Countdown timer + stock progress bars)
            item {
              FlashDealsSection(
                countdownSeconds = countdownSeconds,
                activeSlot = selectedFlashSlot,
                onSelectSlot = { selectedFlashSlot = it },
                onProductClick = { product -> showProductDetail = product },
                onAddToCart = { product ->
                  val existing = cartItems.find { it.product.id == product.id }
                  if (existing != null) {
                    existing.quantity++
                  } else {
                    cartItems.add(CartItem(product = product, quantity = 1, selectedVariant = product.variants.firstOrNull() ?: "Standard"))
                  }
                  scope.launch { snackbarHostState.showSnackbar("Added '${product.title.take(24)}...' to Cart! 🛒") }
                },
                formatPrice = formatPrice
              )
            }

            // 5. Seller Onboarding / Commission Promotion Banner
            item {
              SellerOnboardingBanner(
                onOpenSellerRegistration = { showSellerModal = true }
              )
            }

            // 6. Just For You / Personalized Product Grid (2-Column feed with filters)
            item {
              PersonalizedProductGrid(
                products = filteredFeedProducts,
                selectedTab = selectedFeedTab,
                favoriteProductIds = favoriteProductIds.toSet(),
                onSelectTab = { selectedFeedTab = it },
                onProductClick = { product -> showProductDetail = product },
                onAddToCart = { product ->
                  val existing = cartItems.find { it.product.id == product.id }
                  if (existing != null) {
                    existing.quantity++
                  } else {
                    cartItems.add(CartItem(product = product, quantity = 1, selectedVariant = product.variants.firstOrNull() ?: "Standard"))
                  }
                  scope.launch { snackbarHostState.showSnackbar("Added '${product.title.take(24)}...' to Cart! 🛒") }
                },
                onToggleFavorite = { pid ->
                  if (favoriteProductIds.contains(pid)) favoriteProductIds.remove(pid) else favoriteProductIds.add(pid)
                },
                formatPrice = formatPrice
              )
            }

            // 7. Trust Guarantees, FAQ Accordion & Logistics Footer
            item {
              MarketFooterSection(
                onOpenHelp = {
                  scope.launch { snackbarHostState.showSnackbar("24/7 Live Agent connected. How can we help?") }
                },
                onSelectFaq = { faq ->
                  scope.launch { snackbarHostState.showSnackbar("FAQ: ${faq.question}") }
                }
              )
            }
          } else {
            // Notifications Tab view
            item {
              NotificationsView()
            }
          }
        }
      }
    }
  }

  // --- Modals and Sheets ---

  // Product Detail Bottom Modal
  showProductDetail?.let { product ->
    ProductDetailDialog(
      product = product,
      isFavorite = favoriteProductIds.contains(product.id),
      onDismiss = { showProductDetail = null },
      onAddToCart = { prod, qty, variant ->
        val existing = cartItems.find { it.product.id == prod.id && it.selectedVariant == variant }
        if (existing != null) {
          existing.quantity += qty
        } else {
          cartItems.add(CartItem(product = prod, quantity = qty, selectedVariant = variant))
        }
        showProductDetail = null
        scope.launch { snackbarHostState.showSnackbar("Added $qty x ${prod.title.take(20)} to Cart!") }
      },
      onToggleFavorite = { pid ->
        if (favoriteProductIds.contains(pid)) favoriteProductIds.remove(pid) else favoriteProductIds.add(pid)
      },
      formatPrice = formatPrice
    )
  }

  // Shopping Cart Bottom Sheet
  if (showCartSheet) {
    CartSheet(
      cartItems = cartItems,
      userCoins = userCoins,
      isCoinRedeemed = isCoinRedeemedInCart,
      appliedVoucher = appliedVoucher,
      availableVouchers = MarketplaceData.dailyVouchers,
      onDismiss = { showCartSheet = false },
      onUpdateQuantity = { pid, newQty ->
        val item = cartItems.find { it.product.id == pid }
        if (item != null) {
          if (newQty <= 0) {
            cartItems.remove(item)
          } else {
            item.quantity = newQty
          }
        }
      },
      onToggleItemChecked = { pid ->
        val item = cartItems.find { it.product.id == pid }
        item?.let { it.isChecked = !it.isChecked }
      },
      onToggleSelectAll = {
        val allChecked = cartItems.all { it.isChecked }
        cartItems.forEach { it.isChecked = !allChecked }
      },
      onToggleCoins = {
        isCoinRedeemedInCart = !isCoinRedeemedInCart
      },
      onApplyVoucher = { voucher ->
        appliedVoucher = voucher
        scope.launch { snackbarHostState.showSnackbar(if (voucher != null) "Applied ${voucher.title}!" else "Voucher removed.") }
      },
      onCheckout = {
        val count = cartItems.filter { it.isChecked }.sumOf { it.quantity }
        cartItems.removeAll { it.isChecked }
        scope.launch { snackbarHostState.showSnackbar("Order Placed Successfully for $count items! 🚀 Track in Orders.") }
      },
      formatPrice = formatPrice
    )
  }

  // MintPay & Coin Rewards Modal
  if (showWalletCoinsModal) {
    WalletCoinsDialog(
      currentCoins = userCoins,
      walletBalance = userWalletBalance,
      onDismiss = { showWalletCoinsModal = false },
      onClaimCoins = { bonus ->
        userCoins += bonus
        scope.launch { snackbarHostState.showSnackbar("Collected +$bonus Mint Coins! 🪙") }
      },
      formatPrice = formatPrice
    )
  }

  // Seller Hub Registration Dialog
  if (showSellerModal) {
    SellerRegistrationDialog(
      onDismiss = { showSellerModal = false },
      onRegisterSuccess = { storeName ->
        scope.launch { snackbarHostState.showSnackbar("Store '$storeName' is now LIVE with 0% commission! 🏪") }
      }
    )
  }

  // Currency & Language Modal
  if (showCurrencyLangModal) {
    CurrencyLanguageModal(
      activeCurrency = activeCurrency,
      activeLanguage = activeLanguage,
      onSelectCurrency = { activeCurrency = it },
      onSelectLanguage = { activeLanguage = it },
      onDismiss = { showCurrencyLangModal = false }
    )
  }
}

@Composable
fun NotificationsView() {
  Column(
    modifier = Modifier
      .fillMaxWidth()
      .padding(16.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = "Notifications & Order Updates",
      fontSize = 16.sp,
      fontWeight = FontWeight.Bold,
      color = TextDark
    )
    Spacer(modifier = Modifier.height(16.dp))

    val notifications = listOf(
      "📦 Order #MG-9821 has been packed and handed to J&T Express for delivery.",
      "⚡ Flash Deal Alert: MintPod Pro ANC is now 68% OFF for the next 2 hours!",
      "🪙 Daily Check-in Reminder: Claim your 50 Mint Coins before 23:59.",
      "🎉 New voucher drop: 15% MintPay Cashback is waiting in your wallet."
    )

    notifications.forEach { notif ->
      Surface(
        shape = RoundedCornerShape(12.dp),
        color = Color.White,
        border = androidx.compose.foundation.BorderStroke(1.dp, GlassBorderEmerald),
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 4.dp)
      ) {
        Text(
          text = notif,
          fontSize = 12.sp,
          color = TextDark,
          modifier = Modifier.padding(12.dp)
        )
      }
    }
  }
}
