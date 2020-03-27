package codes.malukimuthusi.newsdemoapp.data

data class ApiResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
) {
}