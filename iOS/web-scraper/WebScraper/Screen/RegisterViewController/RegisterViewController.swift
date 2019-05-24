//
//  LoginViewController.swift
//  WebScraper
//
//  Created by RSQ Technologies on 09/04/2019.
//  Copyright © 2019 RSQ Technologies. All rights reserved.
//

import UIKit

class RegisterViewController: UIViewController {
    
    @IBOutlet weak var usernameTextField: UITextField!
    @IBOutlet weak var passwordTextField: UITextField!
    @IBOutlet weak var confirmPasswordTextField: UITextField!
    var output: RegisterViewInteractor?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        RegisterViewConfigurator.sharedInstance.configure(controller: self)
        self.navigationController?.isNavigationBarHidden = false
    }
    
    @IBAction func registerButtonPressed(_ sender: Any) {
        guard let username = usernameTextField.text, let password = passwordTextField.text, let passwordConfirmed = confirmPasswordTextField.text else {
            return
        }
        if password == passwordConfirmed {
            output?.register(username, password)
        } else {
            presentError()
        }
    }
    
    func presentSuccess() {
        NaviRouter.shared.popViewController()
    }
    
    func presentError() {
        self.passwordTextField.backgroundColor = .red
        self.confirmPasswordTextField.backgroundColor = .red
    }

}
