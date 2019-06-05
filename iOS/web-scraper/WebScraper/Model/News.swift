//
//  NewsDTO.swift
//  WebScraper
//
//  Created by RSQ Technologies on 11/04/2019.
//  Copyright © 2019 RSQ Technologies. All rights reserved.
//

import UIKit
import ObjectMapper

class News: NSObject, Mappable {
    
    var author: String?
    var date: String?
    var text: String?
    var link: String?
    var photo: String?
    var tags: [String]?
    
    required init?(map: Map) {
        super.init()
        self.mapping(map: map)
    }
    
    func mapping(map: Map) {
        author <- map["author"]
        date <- map["date"]
        text <- map["text"]
        link <- map["link"]
        photo <- map["photo"]
        tags <- map["tags"]
    }
    
}
