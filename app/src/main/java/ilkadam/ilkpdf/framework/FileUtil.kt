package ilkadam.ilkpdf.framework

import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.FileUtils
import android.provider.MediaStore
import android.provider.OpenableColumns
import androidx.core.net.toUri
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import kotlin.concurrent.fixedRateTimer

object FileUtil {
    data class DocumentDetail(val uri: Uri?, val name: String, val size: Int, val thumbnail: String)

    private fun getPdfThumbnailUri(
        context: Context,
        documentUri: String,
        documentName: String
    ): String {

        val pdfRenderer =
            PdfRenderer(context.contentResolver.openFileDescriptor(Uri.parse(documentUri), "r")!!)

        val firstPage = pdfRenderer.openPage(0)
        val bitmap = Bitmap.createBitmap(
            firstPage.width,
            firstPage.height,
            Bitmap.Config.ARGB_8888
        )

        firstPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
        firstPage.close()
        pdfRenderer.close()

        val thumbnailFile = File(context.cacheDir, documentName + "_thumbnail")

        try {
            FileOutputStream(thumbnailFile).use { out: FileOutputStream ->
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
            }
        } catch (exception: IOException) {
            exception.printStackTrace()
        }

        return thumbnailFile.absolutePath
    }

    fun getDocumentDetails(context: Context, documentUri: String): DocumentDetail {
        val projection = arrayOf(MediaStore.MediaColumns.DISPLAY_NAME, MediaStore.MediaColumns.SIZE)
        context.contentResolver.query(Uri.parse(documentUri), projection, null, null, null)
            ?.use { metaCursor ->
                val nameIndex = metaCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                val sizeIndex = metaCursor.getColumnIndex(OpenableColumns.SIZE)
                return if (metaCursor.moveToFirst()) {
                    val name = metaCursor.getString(nameIndex)
                    DocumentDetail(
                        copyDocumentToCacheDir(context,documentUri,name),
                        name,
                        metaCursor.getInt(sizeIndex),
                        getPdfThumbnailUri(context, documentUri, name)
                    )
                } else {
                    DocumentDetail(null, "", 0, "")
                }
            } ?: return DocumentDetail(null, "No name", 0, "")

    }

    private fun copyDocumentToCacheDir(
        context: Context,
        documentUri: String,
        documentName: String
    ): Uri {
        val cacheDocument = File(context.cacheDir, documentName)
        val inputStream = context.contentResolver.openInputStream(Uri.parse(documentUri))
        val outputStream = context.contentResolver.openOutputStream(cacheDocument.toUri())

        val bufferSize = DEFAULT_BUFFER_SIZE
        var bytesCopied: Long = 0
        val buffer = ByteArray(bufferSize)
        var bytes = inputStream!!.read(buffer)
        while (bytes >= 0) {
            outputStream!!.write(buffer, 0, bytes)
            bytesCopied += bytes
            bytes = inputStream.read(buffer)
        }
        return cacheDocument.toUri()
    }
}