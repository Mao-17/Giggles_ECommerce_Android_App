package com.example.Giggles

class Toy {
    var key: String? = null
    var title: String? = null
    var category: String? = null
    var brand: String? = null
    var condition: String? = null
    var expectedPrice = 0
    var imageUrl: String? = null

    constructor() {}
    constructor(
        key: String?,
        title: String?,
        category: String?,
        brand: String?,
        condition: String?,
        expectedPrice: Int,
        imageUrl: String?
    ) {
        this.key = key
        this.title = title
        this.category = category
        this.brand = brand
        this.condition = condition
        this.expectedPrice = expectedPrice
        this.imageUrl = imageUrl
    }
}