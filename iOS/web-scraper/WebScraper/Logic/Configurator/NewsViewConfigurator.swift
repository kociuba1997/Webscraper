//
//  LoginViewConfigurator.swift
//  WebScraper
//
//  Created by RSQ Technologies on 11/04/2019.
//  Copyright © 2019 RSQ Technologies. All rights reserved.
//

import UIKit

class NewsViewConfigurator: NSObject {
    static let sharedInstance = NewsViewConfigurator()
    
    func configure(controller: NewsViewController) {
        let presenter = NewsViewPresenter()
        let interactor = NewsViewInteractor()
        controller.output = interactor
        interactor.output = presenter
        presenter.output = controller
    }

}
