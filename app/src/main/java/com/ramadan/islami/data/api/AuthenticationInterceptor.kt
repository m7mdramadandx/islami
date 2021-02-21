import okhttp3.Interceptor
import okhttp3.Response

/**
 * Interceptor used to intercept the actual request and
 * to supply your API Key in REST API calls via a custom header.
 */
class AuthenticationInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val newRequest = chain.request().newBuilder()
//            .addHeader("Accept", "application/json")
            .addHeader("X-CMC_PRO_API_KEY", "CMC_PRO_API_KEY")
            .build()

        return chain.proceed(newRequest)
    }
}