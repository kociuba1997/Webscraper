//
//  LoginViewInteractor.swift
//  WebScraper
//
//  Created by RSQ Technologies on 11/04/2019.
//  Copyright © 2019 RSQ Technologies. All rights reserved.
//

import UIKit

class NewsViewInteractor: NSObject {
    var output: NewsViewPresenter?
    
    func getNews(_ fetchNews: Bool) {
        RestService.shared.getNews(fetchNews: fetchNews, completion: { news in
            self.output?.presentNews(news ?? [])
        })
    }
    
    func getTags() {
        RestService.shared.getTags(completion: { tags in
            self.output?.presentTags(tags ?? [])
        })
    }

}
