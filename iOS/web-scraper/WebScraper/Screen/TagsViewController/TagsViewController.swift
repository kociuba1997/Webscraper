//
//  TagsViewController.swift
//  WebScraper
//
//  Created by RSQ Technologies on 04/05/2019.
//  Copyright © 2019 RSQ Technologies. All rights reserved.
//

import UIKit

class TagsViewController: UIViewController {

    @IBOutlet weak var tagsTableView: UITableView!
    let dataSource = TagsDataSource()
    var output: TagsViewInteractor?
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        TagsViewConfigurator.sharedInstance.configure(controller: self)
        self.navigationController?.isNavigationBarHidden = false
        dataSource.delegate = self
        tagsTableView.dataSource = dataSource
        tagsTableView.delegate = dataSource
        
        tagsTableView.register(UINib(nibName: String(describing: TagCell.self), bundle: nil), forCellReuseIdentifier: "TagCell")
        tagsTableView.register(UINib(nibName: String(describing: TagFooter.self), bundle: nil), forHeaderFooterViewReuseIdentifier: "TagFooter")
        
        output?.getTags()
    }
    
    func presentTags(_ tags: [String]) {
        dataSource.tags = tags
        tagsTableView.reloadData()
    }
    
    func displaySuccess() {
        NaviRouter.shared.popViewController()
    }

    @IBAction func saveButtonPressed(_ sender: Any) {
        output?.updateTags(dataSource.tags)
    }
}
