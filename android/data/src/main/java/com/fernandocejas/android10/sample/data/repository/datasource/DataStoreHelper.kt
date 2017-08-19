package com.fernandocejas.android10.sample.data.repository.datasource

import android.content.Context
import android.net.Uri
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.apache.commons.io.IOUtils
import java.io.InputStream
import java.net.URI
import android.provider.MediaStore
import android.provider.DocumentsContract
import android.content.ContentUris
import android.database.Cursor
import android.os.Build
import android.os.Build.VERSION_CODES
import android.os.Build.VERSION_CODES.KITKAT
import android.os.Build.VERSION
import android.os.Environment


/**
 *
 *
 * Created on 8/13/17.
 */
class DataStoreHelper constructor(val context: Context) {
  val shared = context.getSharedPreferences("twitter", Context.MODE_PRIVATE)

  fun getToken(): String {
    return shared.getString("token", "")
  }

  fun getTokenSecret(): String {
    return shared.getString("secret", "")
  }

  fun getUserId(): String {
    return shared.getString("user_id", "")
  }

  fun convertURItoUri(javaURI: URI): Uri {
    return Uri.parse(javaURI.toString())
  }

  fun convertUriToInputStream(uri: Uri): InputStream {
    return context.getContentResolver().openInputStream(uri)
  }

  fun createRequestBody(stream: InputStream): RequestBody {
    return RequestBody.create(MediaType.parse("multipart/form-data"), IOUtils.toByteArray(stream))
  }

  fun createMultipardBody(name: String, fileName: String,
      requestBody: RequestBody): MultipartBody.Part {
    return MultipartBody.Part.createFormData(name, fileName, requestBody)
  }

  fun getFileName(uri: Uri): String {
    val path: String? = getPath(uri)
    val path_split: List<String> = path!!.split('/')
    return path_split.last()
  }

  /**
   * Get a file path from a Uri. This will get the the path for Storage Access
   * Framework Documents, as well as the _data field for the MediaStore and
   * other file-based ContentProviders.

   * @param context The context.
   * *
   * @param uri The Uri to query.
   * *
   * @author paulburke
   */
  fun getPath(uri: Uri): String? {
    // DocumentProvider
    if (DocumentsContract.isDocumentUri(context, uri)) {
      // ExternalStorageProvider
      if (isExternalStorageDocument(uri)) {
        val docId = DocumentsContract.getDocumentId(uri)
        val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val type = split[0]

        if ("primary".equals(type, ignoreCase = true)) {
          return Environment.getExternalStorageDirectory().toString() + "/" + split[1]
        }

        // TODO handle non-primary volumes
      } else if (isDownloadsDocument(uri)) {

        val id = DocumentsContract.getDocumentId(uri)
        val contentUri = ContentUris.withAppendedId(
            Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(id)!!)

        return getDataColumn(context, contentUri, null, null)
      } else if (isMediaDocument(uri)) {
        val docId = DocumentsContract.getDocumentId(uri)
        val split = docId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val type = split[0]

        var contentUri: Uri? = null
        if ("image" == type) {
          contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        } else if ("video" == type) {
          contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        } else if ("audio" == type) {
          contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        }

        val selection = "_id=?"
        val selectionArgs = arrayOf(split[1])

        return getDataColumn(context, contentUri, selection, selectionArgs)
      }// MediaProvider
      // DownloadsProvider
    } else if ("content".equals(uri.scheme, ignoreCase = true)) {
      return getDataColumn(context, uri, null, null)
    } else if ("file".equals(uri.scheme, ignoreCase = true)) {
      return uri.path
    }

    return null
  }

  /**
   * Get the value of the data column for this Uri. This is useful for
   * MediaStore Uris, and other file-based ContentProviders.

   * @param context The context.
   * *
   * @param uri The Uri to query.
   * *
   * @param selection (Optional) Filter used in the query.
   * *
   * @param selectionArgs (Optional) Selection arguments used in the query.
   * *
   * @return The value of the _data column, which is typically a file path.
   */
  fun getDataColumn(context: Context, uri: Uri?, selection: String?,
      selectionArgs: Array<String>?): String? {

    var cursor: Cursor? = null
    val column = "_data"
    val projection = arrayOf(column)

    try {
      cursor = context.contentResolver.query(uri, projection, selection, selectionArgs, null)
      if (cursor != null && cursor!!.moveToFirst()) {
        val column_index = cursor!!.getColumnIndexOrThrow(column)
        return cursor!!.getString(column_index)
      }
    } finally {
      if (cursor != null)
        cursor!!.close()
    }
    return null
  }


  /**
   * @param uri The Uri to check.
   * *
   * @return Whether the Uri authority is ExternalStorageProvider.
   */
  fun isExternalStorageDocument(uri: Uri): Boolean {
    return "com.android.externalstorage.documents" == uri.authority
  }

  /**
   * @param uri The Uri to check.
   * *
   * @return Whether the Uri authority is DownloadsProvider.
   */
  fun isDownloadsDocument(uri: Uri): Boolean {
    return "com.android.providers.downloads.documents" == uri.authority
  }

  /**
   * @param uri The Uri to check.
   * *
   * @return Whether the Uri authority is MediaProvider.
   */
  fun isMediaDocument(uri: Uri): Boolean {
    return "com.android.providers.media.documents" == uri.authority
  }
}