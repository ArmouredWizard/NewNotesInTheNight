package uk.co.maddwarf.notesinthenight.database.converters

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromList(list: List<String>): String {
        return list.toString()
    }//end from list

    @TypeConverter
    fun toList(string: String): List<String> {
        val result = mutableListOf<String>()
        val split = string.replace("[", "").replace("]", "").split(",")
        for (sub in split) {
            result.add(sub.trim())
        }
        result.forEach {
            if (it == "") {
                result.remove(it)
            }
        }
        return result
    }//end to List


}//end converters