//
//  TagsViewPresenter.swift
//  WebScraper
//
//  Created by RSQ Technologies on 04/05/2019.
//  Copyright © 2019 RSQ Technologies. All rights reserved.
//

import UIKit

class TagsViewPresenter: NSObject {
    weak var output: TagsViewController?
    
    func presentTags(_ tags: [String]) {
        output?.presentTags(tags)
    }
    
    func presentSuccess() {
        output?.displaySuccess()
    }
}
