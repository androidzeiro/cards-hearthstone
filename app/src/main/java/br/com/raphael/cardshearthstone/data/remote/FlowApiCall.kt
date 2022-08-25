package br.com.raphael.cardshearthstone.data.remote

import android.content.Context
import br.com.raphael.cardshearthstone.R
import br.com.raphael.cardshearthstone.data.model.response.ErrorResponse
import br.com.raphael.cardshearthstone.data.model.dto.Result
import br.com.raphael.cardshearthstone.di.ApiModule
import br.com.raphael.cardshearthstone.util.extension.isInternetAvailable
import kotlinx.coroutines.flow.FlowCollector
import okhttp3.ResponseBody
import retrofit2.Response
import timber.log.Timber

object FlowApiCall {
    private const val TAG_ERROR_BODY = "convertErrorBody()"
    private const val TAG_RESULT = "getResult()"

    suspend fun <T> getResult(
        context: Context,
        flowCollector: FlowCollector<Result<T>>,
        request: suspend () -> Response<T>
    ) {
        if (context.isInternetAvailable()) {
            try {
                val response = request()
                val body = response.body()
                if (response.isSuccessful && body != null) {
                    flowCollector.emit(Result.Success(body, response.code()))
                } else {
                    val errorResponse = convertErrorBody(response.errorBody())
                    if (errorResponse != null) {
                        flowCollector.emit(Result.Error(errorResponse.message, response.code()))
                    } else {
                        val errorMessage = when (response.code()) {
                            401 -> context.getString(R.string.error_unauthorized_message)
                            404 -> context.getString(R.string.error_not_found)
                            else -> context.getString(R.string.error_generic_message)
                        }
                        flowCollector.emit(Result.Error(errorMessage, response.code()))
                    }
                }
            } catch (exception: Exception) {
                Timber.tag(TAG_RESULT).e(exception)
                flowCollector.emit(Result.Error(context.getString(R.string.error_generic_message), 0))
            }
        } else {
            flowCollector.emit(Result.NetworkError(context.getString(R.string.error_network)))
        }
    }

    private fun convertErrorBody(errorBody: ResponseBody?): ErrorResponse? {
        var errorResponse: ErrorResponse? = null
        try {
            errorResponse = errorBody?.source()?.let {
                val jsonAdapter = ApiModule.provideMoshi().adapter(ErrorResponse::class.java)
                val message = it.readString(Charsets.UTF_8)
                jsonAdapter.fromJson(message)
            }
        } catch (e: Exception) {
            Timber.tag(TAG_ERROR_BODY).e(e)
        }
        return errorResponse
    }
}