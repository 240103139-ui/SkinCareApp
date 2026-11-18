
package com.example.skincareapp.model

data class IngredientInfo(
    val name: String,
    val function: String,
    val description: String
)

object IngredientDatabase {

    private val ingredients = listOf(
        IngredientInfo(
            "Glycine Soja Oil",
            "Emollient",
            "Soybean oil that helps soften and condition the skin."
        ),
        IngredientInfo(
            "Prunus Amygdalus Dulcis Oil",
            "Emollient",
            "Sweet almond oil used to moisturize and soften skin."
        ),
        IngredientInfo(
            "Helianthus Annuus Seed Oil",
            "Skin Conditioning",
            "Sunflower seed oil that helps maintain skin moisture."
        ),
        IngredientInfo(
            "C12-15 Alkyl Benzoate",
            "Emollient",
            "An ingredient that gives skincare products a smooth feel."
        ),
        IngredientInfo(
            "Linalool",
            "Fragrance",
            "A fragrance ingredient that may cause reactions in sensitive individuals."
        )
    )

    fun findByName(name: String): IngredientInfo {
        return ingredients.find {
            it.name.equals(name, ignoreCase = true)
        } ?: IngredientInfo(
            name = name,
            function = "Unknown",
            description = "No information available yet."
        )
    }
}
