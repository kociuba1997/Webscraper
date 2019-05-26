//
//  LoginViewInteractor.swift
//  WebScraper
//
//  Created by RSQ Technologies on 11/04/2019.
//  Copyright © 2019 RSQ Technologies. All rights reserved.
//

import UIKit

class LoginViewInteractor: NSObject {
    var output: LoginViewPresenter?
    
    func login(_ username: String, _ password: String) {
        RestService.shared.login(username: username, password: password, completion: { ok, token in
            if !ok {
                self.output?.presentError()
            } else {
                KeychainService.removeToken(service: "tokenService", account: "user")
                KeychainService.saveToken(service: "tokenService", account: "user", data: token ?? "")
                self.output?.presentSuccess()
            }
        })
    }

}
