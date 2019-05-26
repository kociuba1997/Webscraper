//
//  NewsViewController.swift
//  WebScraper
//
//  Created by RSQ Technologies on 11/04/2019.
//  Copyright © 2019 RSQ Technologies. All rights reserved.
//

import UIKit
import SideMenu
import JGProgressHUD

class NewsViewController: UIViewController {

    @IBOutlet weak var newsTableView: UITableView!
    
    var dataSource = NewsDataSource()
    var output: NewsViewInteractor?
    let hud = JGProgressHUD(style: .dark)
    
    @IBAction func refreshButtonPressed(_ sender: Any) {
        hud.show(in: self.view)
        output?.getNews(true)
    }
    
    override func viewDidLoad() {
        hud.textLabel.text = "Ładowanie"
        
        super.viewDidLoad()
        NewsViewConfigurator.sharedInstance.configure(controller: self)
        dataSource.delegate = self
        newsTableView.dataSource = dataSource
        newsTableView.delegate = dataSource
        newsTableView.register(UINib(nibName: String(describing: NewsCell.self), bundle: nil), forCellReuseIdentifier: "NewsCell")
        newsTableView.register(UINib(nibName: String(describing: NewsHeader.self), bundle: nil), forHeaderFooterViewReuseIdentifier: "NewsHeader")
        
        let vc = MenuViewController(nibName: String(describing: MenuViewController.self), bundle: nil)
        let menu = UISideMenuNavigationController(rootViewController: vc)
        menu.isNavigationBarHidden = true
        SideMenuManager.default.menuRightNavigationController = menu
        SideMenuManager.default.menuAddPanGestureToPresent(toView: self.navigationController!.navigationBar)
        SideMenuManager.default.menuAddScreenEdgePanGesturesToPresent(toView: self.navigationController!.view)
        SideMenuManager.default.menuFadeStatusBar = false
    }
    
    override func viewWillAppear(_ animated: Bool) {
        self.navigationController?.isNavigationBarHidden = true
        hud.show(in: self.view)
        output?.getTags()
    }
    
    func presentTags(_ tags: [String]) {
        dataSource.tags = tags
        output?.getNews(false)
    }
    
    func presentNews(_ news: [News]) {
        dataSource.news = []
        for tag in dataSource.tags {
            let tagNews = news.filter({($0.tags?.contains(tag))!})
            dataSource.news.append(ExpandableNews(isExpanded: false, news: tagNews))
        }
        newsTableView.reloadData()
        hud.dismiss(afterDelay: 0.0)
    }
    
    func showNews(_ news: News) {
        let newsVc = SingleNewsViewController(nibName: String(describing: SingleNewsViewController.self), bundle: nil)
        newsVc.news = news
        NaviRouter.shared.pushViewController(newsVc)
    }

    @IBAction func menuButtonPressed(_ sender: Any) {
       self.present(SideMenuManager.default.menuRightNavigationController!, animated: true, completion: nil)
    }
    
    func reloadTableView() {
        newsTableView.reloadData()
    }
    
    func reloadTableViewSecion(_ section: Int) {
        newsTableView.reloadSections(IndexSet(integer: section), with: .automatic)
    }
}
