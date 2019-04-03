/*
 *
 *  * Copyright (c) 2018 Razeware LLC
 *  *
 *  * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  * of this software and associated documentation files (the "Software"), to deal
 *  * in the Software without restriction, including without limitation the rights
 *  * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  * copies of the Software, and to permit persons to whom the Software is
 *  * furnished to do so, subject to the following conditions:
 *  *
 *  * The above copyright notice and this permission notice shall be included in
 *  * all copies or substantial portions of the Software.
 *  *
 *  * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 *  * distribute, sublicense, create a derivative work, and/or sell copies of the
 *  * Software in any work that is designed, intended, or marketed for pedagogical or
 *  * instructional purposes related to programming, coding, application development,
 *  * or information technology.  Permission for such use, copying, modification,
 *  * merger, publication, distribution, sublicensing, creation of derivative works,
 *  * or sale is expressly withheld.
 *  *
 *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  * THE SOFTWARE.
 *
 *
 */

package com.example.newsscraper.data.net

import com.example.newsscraper.data.model.News

class NewsInfoProvider {

    companion object {
        var newsList = initNewsList()

        private fun initNewsList(): MutableList<News> {
            val news = mutableListOf<News>()
            news.add(News("poznań", "Jan Nowak", "Przed poznańskim sądem ruszył proces anarchisty oskarżonego o znieważenie policjanta", "https://www.gorzowianin.com/", "", "13 marca 2019, 9:58",1 ))
            news.add(News("poznań", "Andrzej Gżegżółka", "Pomost na pływakach i ścieżki w konarach drzew jeszcze w tym roku powstaną w Antoninku", "https://www.gorzowianin.com/", "","17 marca 2019, 13:39", 2))
            news.add(News("kaszanka", "Marta Linkiewicz", "Akcja pod kryptonimem \"Kaszanka\": Dziesiątki ludzi idą w ślady Marcina Gortata!", "https://www.gorzowianin.com/", "", "21 marca 2019, 19:48",3))
            news.add(News("piątek", "Maciej Zmaciejowa", "Piątek się odblokował, Milan znów rozczarował", "https://www.gorzowianin.com/", "", "22 marca 2019, 9:59",4))
            news.add(News("gorzow", "Dawid Kudela", "Wczoraj w gorzowie", "https://www.gorzowianin.com/", "", "29 marca 2019, 7:11",5))
            news.add(News("gorzow", "Dawid Kudela", "Wczoraj w gorzowie", "https://www.gorzowianin.com/", "", "1 kwietnia 2019, 16:58",6))
            return news
        }
    }
}
