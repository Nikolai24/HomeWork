package com.example.homework10

import java.io.File

class Logger {
    companion object{
        fun create (fileName: String, filesDir: File) {
            File(filesDir, "$fileName.txt").bufferedWriter()
                    .use { out ->
                        out.append("")
                    }
        }

        fun writeText (fileName: String, text: String, filesDir: File) {
            File(filesDir, "$fileName.txt").appendText(text)

        }

        fun readText (fileName: String, filesDir: File) =
                File(filesDir, "$fileName.txt").bufferedReader()
                        .use { out -> out.readText() }

    }
}
