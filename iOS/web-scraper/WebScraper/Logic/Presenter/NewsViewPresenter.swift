//
//  LoginViewPresenter.swift
//  WebScraper
//
//  Created by RSQ Technologies on 11/04/2019.
//  Copyright © 2019 RSQ Technologies. All rights reserved.
//

import UIKit

class NewsViewPresenter: NSObject {
    weak var output: NewsViewController?
    
    func presentNews(_ news: [News]) {
        output?.presentNews(news)
    }
    
    func presentTags(_ tags: [String]) {
        output?.presentTags(tags)
    }
}
