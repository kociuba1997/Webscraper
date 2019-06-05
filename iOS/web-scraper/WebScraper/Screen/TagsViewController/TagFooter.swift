//
//  TagFooter.swift
//  WebScraper
//
//  Created by RSQ Technologies on 04/05/2019.
//  Copyright © 2019 RSQ Technologies. All rights reserved.
//

import UIKit

class TagFooter: UITableViewHeaderFooterView {
    
    @IBOutlet weak var tagTextField: UITextField!
    weak var delegate: TagsDataSource?
    
    @IBAction func addButtonPressed(_ sender: Any) {
        guard let tag = tagTextField.text else {
            return
        }
        delegate?.tags.append(tag)
        delegate?.updateTableView()
        tagTextField.text = ""
    }
}
