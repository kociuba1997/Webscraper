//
//  NaviRouter.swift
//  WebScraper
//
//  Created by RSQ Technologies on 11/04/2019.
//  Copyright © 2019 RSQ Technologies. All rights reserved.
//

import UIKit

class NaviRouter: NSObject {
    static let shared = NaviRouter()
    var navigationController: UINavigationController?
    
    func pushViewController(_ controller: UIViewController) {
        navigationController?.pushViewController(controller, animated: false)
    }
    
    func popViewController() {
        navigationController?.popViewController(animated: false)
    }
    
    func popToRootViewController() {
        navigationController?.popToRootViewController(animated: false)
    }
}
