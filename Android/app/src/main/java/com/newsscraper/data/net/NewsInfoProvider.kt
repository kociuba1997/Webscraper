package com.newsscraper.data.net

import com.newsscraper.data.model.News

class NewsInfoProvider {

    companion object {
        var newsList = initNewsList()

        private fun initNewsList(): MutableList<News> {
            val news = mutableListOf<News>()
            news.add(
                News(
                    "poznań",
                    "Jan Nowak",
                    "Przed poznańskim sądem ruszył proces anarchisty oskarżonego o znieważenie policjanta",
                    "https://www.gorzowianin.com/",
                    "",
                    "13 marca 2019, 9:58",
                    1
                )
            )
            news.add(
                News(
                    "poznań",
                    "Andrzej Gżegżółka",
                    "Pomost na pływakach i ścieżki w konarach drzew jeszcze w tym roku powstaną w Antoninku",
                    "https://www.gorzowianin.com/",
                    "",
                    "17 marca 2019, 13:39",
                    2
                )
            )
            news.add(
                News(
                    "kaszanka",
                    "Marta Linkiewicz",
                    "Akcja pod kryptonimem \"Kaszanka\": Dziesiątki ludzi idą w ślady Marcina Gortata!",
                    "https://www.gorzowianin.com/",
                    "",
                    "21 marca 2019, 19:48",
                    3
                )
            )
            news.add(
                News(
                    "piątek",
                    "Maciej Zmaciejowa",
                    "Piątek się odblokował, Milan znów rozczarował",
                    "https://www.gorzowianin.com/",
                    "",
                    "22 marca 2019, 9:59",
                    4
                )
            )
            news.add(
                News(
                    "gorzow",
                    "Dawid Kudela",
                    "Wczoraj w gorzowie",
                    "https://www.gorzowianin.com/",
                    "",
                    "29 marca 2019, 7:11",
                    5
                )
            )
            news.add(
                News(
                    "gorzow",
                    "Dawid Kudela",
                    "Wczoraj w gorzowie",
                    "https://www.gorzowianin.com/",
                    "",
                    "1 kwietnia 2019, 16:58",
                    6
                )
            )
            return news
        }
    }
}
