package codes.malukimuthusi.newsdemoapp.dataDomain

data class ApiResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
) {
}