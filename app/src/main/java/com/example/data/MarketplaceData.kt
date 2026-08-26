package com.example.data

import com.example.model.*

object MarketplaceData {

  val currencies = listOf(
    Currency(code = "USD", symbol = "$", rateToUsd = 1.0, name = "US Dollar ($)"),
    Currency(code = "EUR", symbol = "€", rateToUsd = 0.92, name = "Euro (€)"),
    Currency(code = "SGD", symbol = "S$", rateToUsd = 1.34, name = "Singapore Dollar (S$)"),
    Currency(code = "PHP", symbol = "₱", rateToUsd = 56.5, name = "Philippine Peso (₱)"),
    Currency(code = "MYR", symbol = "RM", rateToUsd = 4.65, name = "Malaysian Ringgit (RM)"),
    Currency(code = "IDR", symbol = "Rp", rateToUsd = 15800.0, name = "Indonesian Rupiah (Rp)"),
    Currency(code = "THB", symbol = "฿", rateToUsd = 36.0, name = "Thai Baht (฿)")
  )

  val languages = listOf(
    Language(code = "EN", name = "English (US)", flagEmoji = "🇺🇸"),
    Language(code = "ES", name = "Español", flagEmoji = "🇪🇸"),
    Language(code = "ID", name = "Bahasa Indonesia", flagEmoji = "🇮🇩"),
    Language(code = "TH", name = "ไทย (Thai)", flagEmoji = "🇹🇭"),
    Language(code = "VN", name = "Tiếng Việt", flagEmoji = "🇻🇳"),
    Language(code = "ZH", name = "中文 (Simplified)", flagEmoji = "🇨🇳")
  )

  val heroBanners = listOf(
    HeroBanner(
      id = "hero_1",
      title = "6.6 MEGA MID-YEAR SALE",
      subtitle = "Up to 80% OFF • 0$ Min. Spend Free Shipping",
      badge = "MEGA SALE",
      promoTag = "Code: MEGA80",
      ctaText = "Claim All Vouchers",
      voucherHighlight = "EXTRA $20 OFF",
      accentColor = 0xFF059669,
      bgGradientStart = 0xFF064E3B,
      bgGradientEnd = 0xFF059669,
      emoji = "🎁"
    ),
    HeroBanner(
      id = "hero_2",
      title = "GREEN TECH & AUDIO EXPO",
      subtitle = "Eco-Smart ANC Earbuds & Solar Power Banks",
      badge = "OFFICIAL MALL",
      promoTag = "Flash Cashback 25%",
      ctaText = "Shop Mall Brands",
      voucherHighlight = "100% AUTHENTIC",
      accentColor = 0xFF0284C7,
      bgGradientStart = 0xFF0369A1,
      bgGradientEnd = 0xFF0EA5E9,
      emoji = "🎧"
    ),
    HeroBanner(
      id = "hero_3",
      title = "ORGANIC & GREEN LIVING FEST",
      subtitle = "Natural Skincare, Zero Waste Essentials & Teas",
      badge = "SUPER SAVER",
      promoTag = "Buy 1 Get 1 Free",
      ctaText = "Explore Deals",
      voucherHighlight = "EXTRA 15% OFF",
      accentColor = 0xFFD97706,
      bgGradientStart = 0xFF92400E,
      bgGradientEnd = 0xFFF59E0B,
      emoji = "🌿"
    ),
    HeroBanner(
      id = "hero_4",
      title = "FLASH VOUCHER DROP",
      subtitle = "Claim hourly $12 vouchers for next checkout",
      badge = "LIMITED TIME",
      promoTag = "Valid for 2 Hours",
      ctaText = "Collect Voucher",
      voucherHighlight = "FREE SHIPPING",
      accentColor = 0xFFDC2626,
      bgGradientStart = 0xFF991B1B,
      bgGradientEnd = 0xFFEF4444,
      emoji = "⚡"
    )
  )

  val dailyVouchers = listOf(
    Voucher(
      id = "v_free_ship",
      title = "FREE SHIPPING 0$ MIN",
      discountText = "100% Off Shipping",
      minSpendText = "No Min. Spend Required",
      expiryText = "Exp: 23:59 Tonight",
      code = "FREESHIPNOW",
      tag = "HOT VOUCHER"
    ),
    Voucher(
      id = "v_mint_cashback",
      title = "15% MINTPAY CASHBACK",
      discountText = "15% Coin Return",
      minSpendText = "Min. Spend $25",
      expiryText = "Exp in 3 days",
      code = "MINTCASH15",
      tag = "MINT WALLET"
    ),
    Voucher(
      id = "v_mall_discount",
      title = "$15 OFF OFFICIAL MALL",
      discountText = "$15 Instant Cut",
      minSpendText = "Min. Spend $60",
      expiryText = "Valid this week",
      code = "MALLSAVE15",
      tag = "OFFICIAL MALL"
    ),
    Voucher(
      id = "v_new_user",
      title = "NEW SHOPPER TREAT",
      discountText = "30% OFF First Order",
      minSpendText = "Min. Spend $10",
      expiryText = "Valid for 7 days",
      code = "WELCOME30",
      tag = "NEW USER"
    )
  )

  val quickAccessItems = listOf(
    QuickAccessItem("qa_cat", "Categories", null, "all", "📂", 0xFF059669),
    QuickAccessItem("qa_flash", "Flash Sale", "HOT 🔥", "flash", "⚡", 0xFFEF4444),
    QuickAccessItem("qa_ship", "Free Shipping", "NEW", "shipping", "🚚", 0xFFD97706),
    QuickAccessItem("qa_mall", "Official Mall", "100%", "mall", "👑", 0xFF2563EB),
    QuickAccessItem("qa_wallet", "MintPay", "$142.50", "wallet", "💳", 0xFF10B981),
    QuickAccessItem("qa_live", "Live Deals", "STREAM", "live", "🔴", 0xFFEC4899),
    QuickAccessItem("qa_group", "Group Buy", "-70%", "group", "👥", 0xFF8B5CF6),
    QuickAccessItem("qa_rewards", "Daily Spin", "+50 Coins", "rewards", "🎰", 0xFFF59E0B)
  )

  val flashDeals = listOf(
    Product(
      id = "fd_1",
      title = "MintPod Pro ANC Wireless Earbuds (Spatial Audio & 40h Battery)",
      category = "Electronics",
      priceUsd = 24.99,
      originalPriceUsd = 79.99,
      discountPercent = 68,
      rating = 4.9,
      reviewCount = 3840,
      soldCount = "15.8k sold",
      location = "Official Mall • Fast 24h",
      isMall = true,
      hasFreeShipping = true,
      stockLeft = 7,
      totalStock = 100,
      description = "Next-gen active noise cancelling earbuds with transparency mode, Bluetooth 5.4, ultra-low latency gaming mode, and eco-friendly recyclable bamboo case.",
      variants = listOf("Sage Green", "Matte Black", "Pure Mint"),
      iconEmoji = "🎧",
      colorTag = 0xFF059669
    ),
    Product(
      id = "fd_2",
      title = "Smart Eco Solar 30000mAh Power Bank (Fast 65W PD Charging)",
      category = "Electronics",
      priceUsd = 19.50,
      originalPriceUsd = 49.00,
      discountPercent = 60,
      rating = 4.8,
      reviewCount = 2190,
      soldCount = "9.2k sold",
      location = "Local Hub • Express",
      isPreferred = true,
      hasFreeShipping = true,
      stockLeft = 12,
      totalStock = 80,
      description = "High capacity rugged solar power bank with 4 output ports, built-in flashlight, waterproof shockproof frame, and rapid fast charge for laptop and phone.",
      variants = listOf("Forest Green", "Solar Orange", "Obsidian"),
      iconEmoji = "🔋",
      colorTag = 0xFFD97706
    ),
    Product(
      id = "fd_3",
      title = "HydroMint Insulated Thermal Bottle 1000ml (24h Cold / 12h Hot)",
      category = "Home & Living",
      priceUsd = 11.90,
      originalPriceUsd = 28.00,
      discountPercent = 58,
      rating = 4.9,
      reviewCount = 5410,
      soldCount = "24.1k sold",
      location = "Official Mall",
      isMall = true,
      hasFreeShipping = true,
      stockLeft = 4,
      totalStock = 60,
      description = "Double-wall vacuum insulated stainless steel canteen with leakproof spout lid, BPA-free powder coat grip, and magnetic phone holder handle.",
      variants = listOf("Mint Mist (1L)", "Sage Olive (1L)", "Charcoal (1L)"),
      iconEmoji = "🥤",
      colorTag = 0xFF10B981
    ),
    Product(
      id = "fd_4",
      title = "Ultra-Fast Mini Espresso & Matcha Frother Wand",
      category = "Home & Living",
      priceUsd = 6.80,
      originalPriceUsd = 18.00,
      discountPercent = 62,
      rating = 4.7,
      reviewCount = 1830,
      soldCount = "7.8k sold",
      location = "Local Seller",
      isPreferred = true,
      hasFreeShipping = true,
      stockLeft = 18,
      totalStock = 120,
      description = "Rechargeable 3-speed stainless steel handheld mixer for latte foam, matcha, keto protein shakes, and baking. USB-C charging dock included.",
      variants = listOf("Mint Green", "Silky White", "Graphite"),
      iconEmoji = "☕",
      colorTag = 0xFF059669
    )
  )

  val allProducts = listOf(
    Product(
      id = "prod_1",
      title = "MintAir Pro HEPA Air Purifier & Aroma Diffuser for Home",
      category = "Home & Living",
      priceUsd = 48.00,
      originalPriceUsd = 95.00,
      discountPercent = 50,
      rating = 4.9,
      reviewCount = 4210,
      soldCount = "11.5k sold",
      location = "Official Mall 🚚",
      isMall = true,
      hasFreeShipping = true,
      stockLeft = 45,
      totalStock = 200,
      description = "Medical-grade H13 true HEPA filter capturing 99.97% allergens, dust, smoke, and PM2.5. Ultra-quiet sleep mode and gentle ambient mint LED glow.",
      variants = listOf("Standard White", "Mint Sage Edition"),
      iconEmoji = "🍃",
      colorTag = 0xFF059669
    ),
    Product(
      id = "prod_2",
      title = "Ergonomic Mesh Office Chair with Dynamic Lumbar & 3D Armrest",
      category = "Home & Living",
      priceUsd = 89.90,
      originalPriceUsd = 180.00,
      discountPercent = 50,
      rating = 4.8,
      reviewCount = 1620,
      soldCount = "3.4k sold",
      location = "Local Warehouse",
      isPreferred = true,
      hasFreeShipping = true,
      stockLeft = 14,
      totalStock = 50,
      description = "Breathable high-density mesh back with self-adjusting lumbar support, tilt reclining mechanism up to 135 degrees, and heavy duty steel gas lift.",
      variants = listOf("Cool Grey", "Sage Green Accent", "Midnight"),
      iconEmoji = "🪑",
      colorTag = 0xFF52796F
    ),
    Product(
      id = "prod_3",
      title = "Bio-Botanical Organic Facial Serum & Gentle Hydrating Gel",
      category = "Beauty & Health",
      priceUsd = 14.50,
      originalPriceUsd = 32.00,
      discountPercent = 55,
      rating = 4.9,
      reviewCount = 8920,
      soldCount = "38.2k sold",
      location = "Official Mall",
      isMall = true,
      hasFreeShipping = true,
      stockLeft = 82,
      totalStock = 500,
      description = "Cruelty-free vegan skincare formulated with Centella Asiatica, Green Tea extract, and Hyaluronic Acid to calm redness and lock 72h deep moisture.",
      variants = listOf("50ml Serum", "100ml Jumbo Size", "Glow Duo Set"),
      iconEmoji = "✨",
      colorTag = 0xFF10B981
    ),
    Product(
      id = "prod_4",
      title = "Smart Fitness Smartwatch with Heart Rate, SpO2 & GPS Tracker",
      category = "Electronics",
      priceUsd = 32.80,
      originalPriceUsd = 69.90,
      discountPercent = 53,
      rating = 4.7,
      reviewCount = 3140,
      soldCount = "14.1k sold",
      location = "Preferred+ Seller",
      isPreferred = true,
      hasFreeShipping = true,
      stockLeft = 29,
      totalStock = 150,
      description = "1.85-inch vibrant AMOLED display, 120+ sport modes, sleep cycle analytics, Bluetooth calling, and 14-day battery life on a single magnetic charge.",
      variants = listOf("Mint Olive Strap", "Space Grey", "Rose Gold"),
      iconEmoji = "⌚",
      colorTag = 0xFF0284C7
    ),
    Product(
      id = "prod_5",
      title = "Vintage Washed Cotton Linen Oversized Relaxed Shirt",
      category = "Fashion",
      priceUsd = 16.90,
      originalPriceUsd = 36.00,
      discountPercent = 53,
      rating = 4.8,
      reviewCount = 4780,
      soldCount = "19.3k sold",
      location = "Local Boutique",
      isPreferred = true,
      hasFreeShipping = true,
      stockLeft = 60,
      totalStock = 300,
      description = "100% natural organic washed linen-cotton blend. Ultra-breathable, moisture-wicking, effortless casual fit ideal for all-day comfort.",
      variants = listOf("Sage Green (M)", "Sage Green (L)", "Natural Oat (L)", "Olive (XL)"),
      iconEmoji = "👕",
      colorTag = 0xFF52796F
    ),
    Product(
      id = "prod_6",
      title = "Organic Ceremonial Grade Uji Matcha Powder (100g Tin)",
      category = "Groceries",
      priceUsd = 18.00,
      originalPriceUsd = 35.00,
      discountPercent = 48,
      rating = 4.9,
      reviewCount = 6320,
      soldCount = "22.7k sold",
      location = "Direct Japan Imports",
      isMall = true,
      hasFreeShipping = true,
      stockLeft = 33,
      totalStock = 200,
      description = "First-harvest ceremonial grade green tea powder stone-ground from Kyoto. Vibrant emerald color, smooth umami finish with zero bitterness.",
      variants = listOf("100g Single Tin", "200g Bundle + Whisk"),
      iconEmoji = "🍵",
      colorTag = 0xFF059669
    ),
    Product(
      id = "prod_7",
      title = "Wireless Mechanical Keyboard (Hot-swappable Mint Switches)",
      category = "Electronics",
      priceUsd = 45.00,
      originalPriceUsd = 90.00,
      discountPercent = 50,
      rating = 4.9,
      reviewCount = 2890,
      soldCount = "8.6k sold",
      location = "Official Mall",
      isMall = true,
      hasFreeShipping = true,
      stockLeft = 19,
      totalStock = 90,
      description = "75% compact gasket-mounted mechanical keyboard with RGB backlighting, tri-mode connection (Bluetooth 5.1/2.4G/Type-C), and sound dampening foam.",
      variants = listOf("Mint Matcha Keycaps", "Ice Cream Pastel", "Retro Grey"),
      iconEmoji = "⌨️",
      colorTag = 0xFF10B981
    ),
    Product(
      id = "prod_8",
      title = "Eco-Friendly Bamboo Cutting Board Set with Juice Grooves",
      category = "Home & Living",
      priceUsd = 13.90,
      originalPriceUsd = 29.90,
      discountPercent = 53,
      rating = 4.8,
      reviewCount = 1940,
      soldCount = "6.5k sold",
      location = "Local Hub",
      isPreferred = true,
      hasFreeShipping = true,
      stockLeft = 40,
      totalStock = 160,
      description = "Set of 3 antimicrobial organic moso bamboo chopping boards with non-slip silicone feet and built-in knife sharpener slot.",
      variants = listOf("3-Piece Set", "Extra Large Chef Board"),
      iconEmoji = "🪵",
      colorTag = 0xFF84A98C
    ),
    Product(
      id = "prod_9",
      title = "Pro Compact Induction Hair Dryer (Ionic 110,000 RPM Motor)",
      category = "Beauty & Health",
      priceUsd = 39.90,
      originalPriceUsd = 85.00,
      discountPercent = 53,
      rating = 4.9,
      reviewCount = 5120,
      soldCount = "16.8k sold",
      location = "Official Mall",
      isMall = true,
      hasFreeShipping = true,
      stockLeft = 22,
      totalStock = 110,
      description = "High-speed brushless motor drying hair in 3 minutes without extreme heat damage. 200 million negative ions for silky frizz-free styling.",
      variants = listOf("Mint Frost", "Glossy White", "Midnight Grey"),
      iconEmoji = "💨",
      colorTag = 0xFF059669
    ),
    Product(
      id = "prod_10",
      title = "Stainless Steel Multi-Function Vegetable Slicer & Mandoline",
      category = "Home & Living",
      priceUsd = 8.50,
      originalPriceUsd = 19.00,
      discountPercent = 55,
      rating = 4.6,
      reviewCount = 1450,
      soldCount = "9.1k sold",
      location = "Local Seller",
      isPreferred = false,
      hasFreeShipping = true,
      stockLeft = 70,
      totalStock = 250,
      description = "6 interchangeable blades with finger safety guard and storage container. Perfect for julienne carrots, potato chips, and cabbage slaw.",
      variants = listOf("Standard 6-Blade", "Pro 8-Blade with Peeler"),
      iconEmoji = "🥗",
      colorTag = 0xFF059669
    )
  )

  val categoryList = listOf("All", "Flash Deals", "Official Mall", "Electronics", "Home & Living", "Beauty & Health", "Fashion", "Groceries", "Under $10")

  val searchSuggestions = listOf(
    "Wireless Earbuds ANC",
    "Eco Bamboo Water Bottle",
    "Matcha Ceremonial Powder",
    "Air Purifier HEPA",
    "Mechanical Keyboard 75%",
    "Organic Skincare Serum",
    "Linen Casual Shirt",
    "Solar Power Bank 65W"
  )

  val faqs = listOf(
    FaqItem(
      "How do Flash Deals and Vouchers work?",
      "Flash deals refresh every 4 hours with limited inventory. Vouchers can be claimed with one tap and are automatically applied at checkout for maximum savings."
    ),
    FaqItem(
      "What is the MarketGreen Buyer Protection guarantee?",
      "Every purchase is secured by 100% Authentic Mall Guarantee, 15-Day Free Return policy, and escrow payment release only upon satisfactory item delivery."
    ),
    FaqItem(
      "How fast is shipping and delivery?",
      "Items marked with 'Fast 24h' are dispatched within 24 hours. Local deliveries typically arrive in 1-3 business days with full real-time courier tracking."
    ),
    FaqItem(
      "How do I earn and use Mint Coins?",
      "Earn 1 Mint Coin for every $1 spent and check in daily to collect bonus coins. 100 Coins = $1 discount off any future checkout!"
    )
  )

  val logisticsPartners = listOf(
    "J&T Express", "DHL Express", "NinjaVan", "SpeedPost Global", "GreenEco Fleet", "FedEx"
  )

  val paymentGateways = listOf(
    "MintPay Wallet", "Visa", "Mastercard", "Google Pay", "Apple Pay", "Bank Transfer", "Cash On Delivery (COD)"
  )
}
