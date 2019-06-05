//
//  NewsDataSource.swift
//  WebScraper
//
//  Created by RSQ Technologies on 24/04/2019.
//  Copyright © 2019 RSQ Technologies. All rights reserved.
//

import UIKit

class ExpandableNews {
    var isExpanded: Bool
    let news: [News]
    
    init(isExpanded: Bool, news: [News]) {
        self.isExpanded = isExpanded
        self.news = news
    }
}

class NewsDataSource: NSObject, UITableViewDelegate, UITableViewDataSource {
    
    var news: [ExpandableNews] = []
    var tags: [String] = []
    weak var delegate: NewsViewController?
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if news.count > 0 {
            return news[section].isExpanded ? news[section].news.count : 0
        }
        return news.count
    }
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return tags.count
    }
    
    func tableView(_ tableView: UITableView, heightForHeaderInSection section: Int) -> CGFloat {
        return 40
    }
    
    func tableView(_ tableView: UITableView, viewForHeaderInSection section: Int) -> UIView? {
        guard tags.count > 0 else {
            return UIView()
        }
        let tag = tags[section]
        let sectionNews = news[section]
        
        guard let header = tableView.dequeueReusableHeaderFooterView(withIdentifier: String(describing: NewsHeader.self)) as? NewsHeader else {
            return UIView()
        }
        header.setRadius(radius: 20.0)
        header.delegate = delegate
        header.setTag(tag)
        header.news = sectionNews
        header.setButtonLabel()
        header.section = section
        return header
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let cell = tableView.dequeueReusableCell(withIdentifier: String(describing: NewsCell.self), for: indexPath) as? NewsCell else {
            return UITableViewCell()
        }
        let newsList = news[indexPath.section]
        let singleNews = newsList.news[indexPath.row]
        cell.setup(singleNews.author ?? "", tags: singleNews.tags ?? [], date: singleNews.date ?? "", newsText: singleNews.text ?? "", imageLink: singleNews.photo ?? "")
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let newsList = news[indexPath.section]
        let singleNews = newsList.news[indexPath.row]
        delegate?.showNews(singleNews)
    }
    
}
