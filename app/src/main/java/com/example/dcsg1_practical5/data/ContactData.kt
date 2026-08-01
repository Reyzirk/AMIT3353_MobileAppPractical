package com.example.dcsg1_practical5.data

import com.example.dcsg1_practical5.model.Contact

object ContactData {
    val contacts = listOf<Contact>(
        Contact(1, "Alice Tan", "012-345 6789"),
        Contact(2, "Brandon Lee", "013-456 7890"),
        Contact(3, "Chloe Wong", "014-567 8901"),
        Contact(4, "David Kumar", "015-678 9012"),
        Contact(5, "Evelyn Chong", "016-789 0123"),
        Contact(6, "Farid Rahman", "017-890 1234"),
        Contact(7, "Grace Lim", "018-901 2345"),
        Contact(8, "Hafiz Zulkifli", "019-012 3456"),
        Contact(9, "Ivy Cheah", "011-123 4567"),
        Contact(10, "Jason Ooi", "012-234 5678"),
        Contact(11, "Karen Yap", "013-345 6789"),
        Contact(12, "Leon Teh", "014-456 7890")
    )

    fun getById(id: Int): Contact? {
        return contacts.find { it.id == id }
    }
}