//
//  MenuViewController.swift
//  WebScraper
//
//  Created by RSQ Technologies on 04/05/2019.
//  Copyright © 2019 RSQ Technologies. All rights reserved.
//

import UIKit

class MenuViewController: UIViewController {

    override func viewDidLoad() {
        super.viewDidLoad()
    }

    @IBAction func tagsButtonPressed(_ sender: Any) {
        let tagsVc = TagsViewController(nibName: String(describing: TagsViewController.self), bundle: nil)
        NaviRouter.shared.pushViewController(tagsVc)
        dismiss(animated: true, completion: nil)
    }
    
    @IBAction func logoutButtonPressed(_ sender: Any) {
        RestService.shared.logout(completion: { [weak self] in
            self?.dismiss(animated: true, completion: nil)
            NaviRouter.shared.popToRootViewController()
        })
    }
}
