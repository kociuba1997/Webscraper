//
//  TagCell.swift
//  WebScraper
//
//  Created by RSQ Technologies on 04/05/2019.
//  Copyright © 2019 RSQ Technologies. All rights reserved.
//

import UIKit

class TagCell: UITableViewCell {

    @IBOutlet weak var tagLabel: UILabel!
    weak var delegate: TagsDataSource?
    weak var tagsViewDelegate: TagsViewController?
    
    func setup(_ tag: String, _ delegate: TagsDataSource) {
        tagLabel.text = tag
        self.delegate = delegate
    }
    
    override func awakeFromNib() {
        super.awakeFromNib()
    }
    
    @IBAction func removeButtonPressed(_ sender: Any) {
        let index = delegate?.tags.index(of: tagLabel.text ?? "")
        if let index = index {
            delegate?.tags.remove(at: index)
        }
        delegate?.updateTableView()
    }
    
}
