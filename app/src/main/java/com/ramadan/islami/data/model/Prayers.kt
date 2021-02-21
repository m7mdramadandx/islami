//
//import com.google.gson.annotations.SerializedName
//
//data class Prayers(
//    @SerializedName("code")
//    var code: Int,
//    @SerializedName("data")
//    var `data`: List<Data>,
//    @SerializedName("status")
//    var status: String,
//)
//
//data class Data(
//    @SerializedName("date")
//    var date: Date,
//    @SerializedName("meta")
//    var meta: Meta,
//    @SerializedName("timings")
//    var timings: Timings,
//)
//
//data class Date(
//    @SerializedName("gregorian")
//    var gregorian: Gregorian,
//    @SerializedName("hijri")
//    var hijri: Hijri,
//    @SerializedName("readable")
//    var readable: String,
//    @SerializedName("timestamp")
//    var timestamp: String,
//)
//
//data class Meta(
//    @SerializedName("latitude")
//    var latitude: Double,
//    @SerializedName("latitudeAdjustmentMethod")
//    var latitudeAdjustmentMethod: String,
//    @SerializedName("longitude")
//    var longitude: Double,
//    @SerializedName("method")
//    var method: Method,
//    @SerializedName("midnightMode")
//    var midnightMode: String,
//    @SerializedName("offset")
//    var offset: Offset,
//    @SerializedName("school")
//    var school: String,
//    @SerializedName("timezone")
//    var timezone: String,
//)
//
//data class Timings(
//    @SerializedName("Asr")
//    var asr: String,
//    @SerializedName("Dhuhr")
//    var dhuhr: String,
//    @SerializedName("Fajr")
//    var fajr: String,
//    @SerializedName("Imsak")
//    var imsak: String,
//    @SerializedName("Isha")
//    var isha: String,
//    @SerializedName("Maghrib")
//    var maghrib: String,
//    @SerializedName("Midnight")
//    var midnight: String,
//    @SerializedName("Sunrise")
//    var sunrise: String,
//    @SerializedName("Sunset")
//    var sunset: String,
//)
//
//data class Gregorian(
//    @SerializedName("date")
//    var date: String,
//    @SerializedName("day")
//    var day: String,
//    @SerializedName("designation")
//    var designation: Designation,
//    @SerializedName("format")
//    var format: String,
//    @SerializedName("month")
//    var month: Month,
//    @SerializedName("weekday")
//    var weekday: Weekday,
//    @SerializedName("year")
//    var year: String,
//)
//
//data class Hijri(
//    @SerializedName("date")
//    var date: String,
//    @SerializedName("day")
//    var day: String,
//    @SerializedName("designation")
//    var designation: DesignationX,
//    @SerializedName("format")
//    var format: String,
//    @SerializedName("holidays")
//    var holidays: List<Any>,
//    @SerializedName("month")
//    var month: MonthX,
//    @SerializedName("weekday")
//    var weekday: WeekdayX,
//    @SerializedName("year")
//    var year: String,
//)
//
//data class Designation(
//    @SerializedName("abbreviated")
//    var abbreviated: String,
//    @SerializedName("expanded")
//    var expanded: String,
//)
//
//data class Month(
//    @SerializedName("en")
//    var en: String,
//    @SerializedName("number")
//    var number: Int,
//)
//
//data class Weekday(
//    @SerializedName("en")
//    var en: String,
//)
//
//data class DesignationX(
//    @SerializedName("abbreviated")
//    var abbreviated: String,
//    @SerializedName("expanded")
//    var expanded: String,
//)
//
//data class MonthX(
//    @SerializedName("ar")
//    var ar: String,
//    @SerializedName("en")
//    var en: String,
//    @SerializedName("number")
//    var number: Int,
//)
//
//data class WeekdayX(
//    @SerializedName("ar")
//    var ar: String,
//    @SerializedName("en")
//    var en: String,
//)
//
//data class Method(
//    @SerializedName("id")
//    var id: Int,
//    @SerializedName("name")
//    var name: String,
//    @SerializedName("params")
//    var params: Params,
//)
//
//data class Offset(
//    @SerializedName("Asr")
//    var asr: Int,
//    @SerializedName("Dhuhr")
//    var dhuhr: Int,
//    @SerializedName("Fajr")
//    var fajr: Int,
//    @SerializedName("Imsak")
//    var imsak: Int,
//    @SerializedName("Isha")
//    var isha: Int,
//    @SerializedName("Maghrib")
//    var maghrib: Int,
//    @SerializedName("Midnight")
//    var midnight: Int,
//    @SerializedName("Sunrise")
//    var sunrise: Int,
//    @SerializedName("Sunset")
//    var sunset: Int,
//)
//
//data class Params(
//    @SerializedName("Fajr")
//    var fajr: Double,
//    @SerializedName("Isha")
//    var isha: Double,
//)