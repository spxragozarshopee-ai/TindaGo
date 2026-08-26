package com.example.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.MarketplaceData
import com.example.model.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.DecimalFormat

data class MarketplaceUiState(
  val searchQuery: String = "",
  val isSearchActive: Boolean = false,
  val searchHistory: List<String> = listOf("ANC Earbuds", "Matcha Powder", "Air Purifier", "Eco Bottle"),
  val selectedCategory: String = "All",
  val selectedFeedTab: String = "All",
  val activeFlashSlot: String = "12:00 Ongoing",
  val countdownSeconds: Long = 13745L, // ~3 hours 49 mins
  val vouchers: List<Voucher> = MarketplaceData.dailyVouchers,
  val cartItems: List<CartItem> = listOf(
    CartItem(product = MarketplaceData.flashDeals[0], quantity = 1, selectedVariant = "Pure Mint"),
    CartItem(product = MarketplaceData.allProducts[2], quantity = 1, selectedVariant = "50ml Serum")
  ),
  val favoriteProductIds: Set<String> = setOf("fd_1", "prod_6"),
  val selectedProductForDetail: Product? = null,
  val isCartOpen: Boolean = false,
  val isSellerModalOpen: Boolean = false,
  val isCurrencyLanguageModalOpen: Boolean = false,
  val isCheckInSuccess: Boolean = false,
  val userWalletBalance: Double = 142.50,
  val userMintCoins: Int = 580,
  val isDailyCheckedIn: Boolean = false,
  val activeCurrency: Currency = MarketplaceData.currencies[0],
  val activeLanguage: Language = MarketplaceData.languages[0],
  val snackbarMessage: String? = null,
  val checkoutSuccessMessage: String? = null
)

class MarketplaceViewModel : ViewModel() {

  private val _uiState = MutableStateFlow(MarketplaceUiState())
  val uiState: StateFlow<MarketplaceUiState> = _uiState.asStateFlow()

  private val currencyFormatter = DecimalFormat("#,##0.00")

  init {
    startCountdownTimer()
  }

  private fun startCountdownTimer() {
    viewModelScope.launch {
      while (true) {
        delay(1000L)
        _uiState.update { state ->
          val next = if (state.countdownSeconds > 0) state.countdownSeconds - 1 else 14400L
          state.copy(countdownSeconds = next)
        }
      }
    }
  }

  fun onSearchQueryChange(query: String) {
    _uiState.update { it.copy(searchQuery = query) }
  }

  fun onSearchSubmit(query: String) {
    if (query.isNotBlank()) {
      _uiState.update { state ->
        val updatedHistory = (listOf(query.trim()) + state.searchHistory).distinct().take(8)
        state.copy(searchQuery = query, isSearchActive = false, searchHistory = updatedHistory)
      }
    }
  }

  fun clearSearchQuery() {
    _uiState.update { it.copy(searchQuery = "") }
  }

  fun setSearchActive(active: Boolean) {
    _uiState.update { it.copy(isSearchActive = active) }
  }

  fun selectCategory(category: String) {
    _uiState.update { it.copy(selectedCategory = category) }
  }

  fun selectFeedTab(tab: String) {
    _uiState.update { it.copy(selectedFeedTab = tab) }
  }

  fun selectFlashSlot(slot: String) {
    _uiState.update { it.copy(activeFlashSlot = slot) }
  }

  fun claimVoucher(voucherId: String) {
    _uiState.update { state ->
      val updated = state.vouchers.map {
        if (it.id == voucherId) it.copy(isClaimed = true) else it
      }
      val claimed = updated.firstOrNull { it.id == voucherId }
      state.copy(
        vouchers = updated,
        snackbarMessage = "🎉 Claimed voucher: ${claimed?.title ?: "Voucher"}!"
      )
    }
  }

  fun claimAllVouchers() {
    _uiState.update { state ->
      val updated = state.vouchers.map { it.copy(isClaimed = true) }
      state.copy(
        vouchers = updated,
        snackbarMessage = "🎉 All vouchers claimed to your Mint Wallet!"
      )
    }
  }

  fun toggleFavorite(productId: String) {
    _uiState.update { state ->
      val newFavorites = if (state.favoriteProductIds.contains(productId)) {
        state.favoriteProductIds - productId
      } else {
        state.favoriteProductIds + productId
      }
      val isAdded = newFavorites.contains(productId)
      state.copy(
        favoriteProductIds = newFavorites,
        snackbarMessage = if (isAdded) "❤️ Added to Wishlist" else "Removed from Wishlist"
      )
    }
  }

  fun openProductDetail(product: Product) {
    _uiState.update { it.copy(selectedProductForDetail = product) }
  }

  fun closeProductDetail() {
    _uiState.update { it.copy(selectedProductForDetail = null) }
  }

  fun addToCart(product: Product, quantity: Int = 1, variant: String? = null) {
    val chosenVariant = variant ?: product.variants.firstOrNull() ?: "Standard"
    _uiState.update { state ->
      val existingIndex = state.cartItems.indexOfFirst {
        it.product.id == product.id && it.selectedVariant == chosenVariant
      }
      val updatedCart = if (existingIndex >= 0) {
        state.cartItems.mapIndexed { idx, item ->
          if (idx == existingIndex) item.copy(quantity = item.quantity + quantity) else item
        }
      } else {
        state.cartItems + CartItem(product = product, quantity = quantity, selectedVariant = chosenVariant)
      }
      state.copy(
        cartItems = updatedCart,
        snackbarMessage = "🛒 Added '${product.title.take(24)}...' to Cart!"
      )
    }
  }

  fun updateCartItemQuantity(productId: String, variant: String, newQuantity: Int) {
    _uiState.update { state ->
      val updated = if (newQuantity <= 0) {
        state.cartItems.filterNot { it.product.id == productId && it.selectedVariant == variant }
      } else {
        state.cartItems.map {
          if (it.product.id == productId && it.selectedVariant == variant) {
            it.copy(quantity = newQuantity)
          } else it
        }
      }
      state.copy(cartItems = updated)
    }
  }

  fun openCart() {
    _uiState.update { it.copy(isCartOpen = true) }
  }

  fun closeCart() {
    _uiState.update { it.copy(isCartOpen = false) }
  }

  fun openSellerModal() {
    _uiState.update { it.copy(isSellerModalOpen = true) }
  }

  fun closeSellerModal() {
    _uiState.update { it.copy(isSellerModalOpen = false) }
  }

  fun openCurrencyLanguageModal() {
    _uiState.update { it.copy(isCurrencyLanguageModalOpen = true) }
  }

  fun closeCurrencyLanguageModal() {
    _uiState.update { it.copy(isCurrencyLanguageModalOpen = false) }
  }

  fun setCurrency(currency: Currency) {
    _uiState.update { it.copy(activeCurrency = currency) }
  }

  fun setLanguage(language: Language) {
    _uiState.update { it.copy(activeLanguage = language) }
  }

  fun performDailyCheckIn() {
    _uiState.update { state ->
      if (!state.isDailyCheckedIn) {
        state.copy(
          isDailyCheckedIn = true,
          userMintCoins = state.userMintCoins + 50,
          snackbarMessage = "✨ Daily Check-In Success! +50 Mint Coins added to Wallet."
        )
      } else {
        state.copy(snackbarMessage = "You already checked in today! Come back tomorrow.")
      }
    }
  }

  fun completeCheckout() {
    _uiState.update { state ->
      if (state.cartItems.isEmpty()) {
        state.copy(snackbarMessage = "Your cart is empty!")
      } else {
        state.copy(
          cartItems = emptyList(),
          isCartOpen = false,
          checkoutSuccessMessage = "🎉 Order Placed Successfully! Tracking code #MG-${(100000..999999).random()} generated with 100% Buyer Protection."
        )
      }
    }
  }

  fun clearSnackbar() {
    _uiState.update { it.copy(snackbarMessage = null) }
  }

  fun clearCheckoutSuccess() {
    _uiState.update { it.copy(checkoutSuccessMessage = null) }
  }

  fun formatPrice(priceUsd: Double): String {
    val rate = _uiState.value.activeCurrency.rateToUsd
    val symbol = _uiState.value.activeCurrency.symbol
    val converted = priceUsd * rate
    return if (rate > 100) {
      "$symbol${DecimalFormat("#,##0").format(converted)}"
    } else {
      "$symbol${currencyFormatter.format(converted)}"
    }
  }
}
