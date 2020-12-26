package com.example.homework6

import java.io.File

class Item(var name: String, var image: Int, var storageType: String) {

    companion object{
        fun removeFile (fileName: String, filesDir: File) {
            File(filesDir, "$fileName.txt").delete()
        }

        fun writeText (fileName: String, text: String, filesDir: File) {
            File(filesDir, "$fileName.txt").bufferedWriter()
                    .use { out ->
                        out.append(text)
                    }
        }

        fun readText (fileName: String, filesDir: File) =
                File(filesDir, "$fileName.txt").bufferedReader()
                        .use { out -> out.readText() }

    }
}
