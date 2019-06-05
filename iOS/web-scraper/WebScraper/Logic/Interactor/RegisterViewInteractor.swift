//
//  LoginViewInteractor.swift
//  WebScraper
//
//  Created by RSQ Technologies on 11/04/2019.
//  Copyright © 2019 RSQ Technologies. All rights reserved.
//

import UIKit

class RegisterViewInteractor: NSObject {
    var output: RegisterViewPresenter?
    
    func register(_ username: String, _ password: String) {
        RestService.shared.register(username: username, password: password, completion: { ok in
            if !ok {
                self.output?.presentError()
            } else {
                self.output?.presentSuccess()
            }
        })
    }
}
