//
//  LoginViewConfigurator.swift
//  WebScraper
//
//  Created by RSQ Technologies on 11/04/2019.
//  Copyright © 2019 RSQ Technologies. All rights reserved.
//

import UIKit

class LoginViewConfigurator: NSObject {
    static let sharedInstance = LoginViewConfigurator()
    
    func configure(controller: LoginViewController) {
        let presenter = LoginViewPresenter()
        let interactor = LoginViewInteractor()
        controller.output = interactor
        interactor.output = presenter
        presenter.output = controller
    }

}
