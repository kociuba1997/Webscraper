//
//  LoginViewPresenter.swift
//  WebScraper
//
//  Created by RSQ Technologies on 11/04/2019.
//  Copyright © 2019 RSQ Technologies. All rights reserved.
//

import UIKit

class LoginViewPresenter: NSObject {
    weak var output: LoginViewController?
    
    func presentSuccess() {
        output?.presentSuccess()
    }
    
    func presentError() {
        output?.presentError()
    }
}
