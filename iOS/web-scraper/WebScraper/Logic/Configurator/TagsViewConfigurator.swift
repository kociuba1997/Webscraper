//
//  TagsViewConfigurator.swift
//  WebScraper
//
//  Created by RSQ Technologies on 04/05/2019.
//  Copyright © 2019 RSQ Technologies. All rights reserved.
//

import UIKit

class TagsViewConfigurator: NSObject {
    static let sharedInstance = TagsViewConfigurator()
    
    func configure(controller: TagsViewController) {
        let presenter = TagsViewPresenter()
        let interactor = TagsViewInteractor()
        controller.output = interactor
        interactor.output = presenter
        presenter.output = controller
    }
}
