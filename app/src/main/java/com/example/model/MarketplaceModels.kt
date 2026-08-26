package com.example.model

data class Currency(
  val code: String,
  val symbol: String,
  val rateToUsd: Double,
  val name: String
)

data class Language(
  val code: String,
  val name: String,
  val flagEmoji: String
)

data class Voucher(
  val id: String,
  val title: String,
  val discountText: String,
  val minSpendText: String,
  val expiryText: String,
  val code: String,
  val isClaimed: Boolean = false,
  val tag: String = "Voucher"
)

data class Product(
  val id: String,
  val title: String,
  val category: String,
  val priceUsd: Double,
  val originalPriceUsd: Double,
  val discountPercent: Int,
  val rating: Double,
  val reviewCount: Int,
  val soldCount: String,
  val location: String,
  val isMall: Boolean = false,
  val isPreferred: Boolean = false,
  val hasFreeShipping: Boolean = true,
  val stockLeft: Int,
  val totalStock: Int,
  val description: String,
  val variants: List<String> = listOf("Standard", "Pro Edition", "Eco Green"),
  val iconEmoji: String = "🛍️",
  val colorTag: Long = 0xFF059669
)

data class HeroBanner(
  val id: String,
  val title: String,
  val subtitle: String,
  val badge: String,
  val promoTag: String,
  val ctaText: String,
  val voucherHighlight: String,
  val accentColor: Long,
  val bgGradientStart: Long,
  val bgGradientEnd: Long,
  val emoji: String
)

data class QuickAccessItem(
  val id: String,
  val title: String,
  val badge: String? = null,
  val category: String,
  val iconEmoji: String,
  val tintColor: Long
)

data class CartItem(
  val product: Product,
  var quantity: Int = 1,
  val selectedVariant: String = "Standard",
  var isChecked: Boolean = true
)

data class FlashDeal(
  val product: Product,
  val slotTime: String,
  val claimedPercent: Int
)

data class FaqItem(
  val question: String,
  val answer: String
)

data class Category(
  val id: String,
  val name: String,
  val iconEmoji: String,
  val productCount: Int
)
