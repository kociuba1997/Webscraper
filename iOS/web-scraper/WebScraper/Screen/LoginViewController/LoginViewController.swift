//
//  LoginViewController.swift
//  WebScraper
//
//  Created by RSQ Technologies on 09/04/2019.
//  Copyright © 2019 RSQ Technologies. All rights reserved.
//

import UIKit
import GoogleMobileAds

class LoginViewController: UIViewController {
    
    @IBOutlet weak var passwordTextField: UITextField!
    @IBOutlet weak var userNameTextField: UITextField!
    @IBOutlet weak var bannerView: GADBannerView!
    var output: LoginViewInteractor?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        LoginViewConfigurator.sharedInstance.configure(controller: self)
        NaviRouter.shared.navigationController = self.navigationController
        self.navigationController?.isNavigationBarHidden = true
        bannerView.adUnitID = "ca-app-pub-3940256099942544/2934735716"
        bannerView.rootViewController = self
        let request = GADRequest()
        request.testDevices = [kGADSimulatorID]
        bannerView.load(request)
    }
    
    override func viewWillAppear(_ animated: Bool) {
        self.navigationController?.isNavigationBarHidden = true
    }
    
    @IBAction func registerButtonPressed(_ sender: Any) {
        let registerVc = RegisterViewController(nibName: String(describing: RegisterViewController.self), bundle: nil)
        NaviRouter.shared.pushViewController(registerVc)
    }
    
    @IBAction func loginButtonPressed(_ sender: Any) {
        self.view.endEditing(true)
        guard let username = userNameTextField.text, let password = passwordTextField.text else {
            return
        }
        output?.login(username, password)
    }
    
    func presentSuccess() {
        userNameTextField.text = ""
        passwordTextField.text = ""
        let newsVc = NewsViewController(nibName: String(describing: NewsViewController.self), bundle: nil)
        NaviRouter.shared.pushViewController(newsVc)
    }
    
    func presentError() {
        self.passwordTextField.textColor = .red
        self.userNameTextField.textColor = .red
    }

}
