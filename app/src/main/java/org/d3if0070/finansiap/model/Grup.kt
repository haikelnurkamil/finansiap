package org.d3if0070.finansiap.model

data class Grup(
    var gid: String = "",
    var namaGrup: String = "",
    var kodeGrup: String = "",
    var joinedUsers: List<String> = listOf(),
    var bendahara: String = "",
    var tagihan: String = "", // Menambahkan tagihan
    var deskripsi: String = "" // Menambahkan deskripsi
)
