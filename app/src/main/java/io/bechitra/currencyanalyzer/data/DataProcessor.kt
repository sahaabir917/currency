package io.bechitra.currencyanalyzer.data

class DataProcessor {

    fun getCurrencyData():Collection<Currency> {
        return listOf(
            Currency(
            125.6,
            "JPY",
            "USD",
            "20/10/2020"
            ),
            Currency(
                1.65,
                "GBP",
                "USD",
                "20/10/2020"
            ),
            Currency(
                85.6,
                "BDT",
                "USD",
                "20/10/2020"
            ),
            Currency(
                74.6,
                "INR",
                "USD",
                "20/10/2020"
            ),
            Currency(
                1.98,
                "EUR",
                "USD",
                "20/10/2020"
            )
        )
    }
}