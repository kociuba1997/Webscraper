//
//  LoginViewPresenter.swift
//  WebScraper
//
//  Created by RSQ Technologies on 11/04/2019.
//  Copyright © 2019 RSQ Technologies. All rights reserved.
//

import UIKit

class RegisterViewPresenter: NSObject {
    weak var output: RegisterViewController?
    
    func presentSuccess() {
        output?.presentSuccess()
    }
    
    func presentError() {
        output?.presentError()
    }
}
