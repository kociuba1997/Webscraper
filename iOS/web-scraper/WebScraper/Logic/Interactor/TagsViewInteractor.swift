//
//  TagsViewInteractor.swift
//  WebScraper
//
//  Created by RSQ Technologies on 04/05/2019.
//  Copyright © 2019 RSQ Technologies. All rights reserved.
//

import UIKit

class TagsViewInteractor: NSObject {
    var output: TagsViewPresenter?
    
    func getTags() {
        RestService.shared.getTags(completion: { tags in
            self.output?.presentTags(tags ?? [])
        })
    }
    
    func updateTags(_ tags: [String]) {
        RestService.shared.udpateTags(tags: tags, completion: { valid in
            if valid {
                self.output?.presentSuccess()
            }
        })
    }
}
