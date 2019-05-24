//
//  TagsDataSource.swift
//  WebScraper
//
//  Created by RSQ Technologies on 04/05/2019.
//  Copyright © 2019 RSQ Technologies. All rights reserved.
//

import UIKit

class TagsDataSource: NSObject, UITableViewDelegate, UITableViewDataSource {
    
    var tags: [String] = []
    weak var delegate: TagsViewController?
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        return tags.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        guard let cell = tableView.dequeueReusableCell(withIdentifier: String(describing: TagCell.self), for: indexPath) as? TagCell else {
            return UITableViewCell()
        }
        let tag = tags[indexPath.row]
        cell.setup(tag, self)
        return cell
    }
    
    func updateTableView() {
        delegate?.tagsTableView.reloadData()
    }
    
    func tableView(_ tableView: UITableView, viewForFooterInSection section: Int) -> UIView? {
        guard let footer = tableView.dequeueReusableHeaderFooterView(withIdentifier: String(describing: TagFooter.self)) as? TagFooter else {
            return UIView()
        }
        footer.delegate = self
        return footer
    }
    
    func tableView(_ tableView: UITableView, heightForFooterInSection section: Int) -> CGFloat {
        return 50
    }
    
}
