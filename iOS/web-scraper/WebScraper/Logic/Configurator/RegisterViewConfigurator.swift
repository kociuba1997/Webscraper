//
//  LoginViewConfigurator.swift
//  WebScraper
//
//  Created by RSQ Technologies on 11/04/2019.
//  Copyright © 2019 RSQ Technologies. All rights reserved.
//

import UIKit

class RegisterViewConfigurator: NSObject {
    static let sharedInstance = RegisterViewConfigurator()
    
    func configure(controller: RegisterViewController) {
        let presenter = RegisterViewPresenter()
        let interactor = RegisterViewInteractor()
        controller.output = interactor
        interactor.output = presenter
        presenter.output = controller
    }

}
