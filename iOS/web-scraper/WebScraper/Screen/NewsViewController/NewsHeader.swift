//
//  NewsHeader.swift
//  WebScraper
//
//  Created by RSQ Technologies on 08/05/2019.
//  Copyright © 2019 RSQ Technologies. All rights reserved.
//

import UIKit

extension UIView {
    func setRadius(radius: CGFloat? = nil) {
        self.layer.cornerRadius = radius ?? self.frame.width / 2;
        self.layer.masksToBounds = true;
    }
}

class NewsHeader: UITableViewHeaderFooterView {

    @IBOutlet weak var headerView: UIView!
    @IBOutlet weak var tagLabel: UILabel!
    @IBOutlet weak var expandButton: UIButton!
    weak var news: ExpandableNews?
    weak var delegate: NewsViewController?
    var section: Int?
    
    func setButtonLabel() {
        guard let news = self.news else {
            expandButton.setTitle("Pokaz", for: .normal)
            return
        }
        if news.isExpanded {
            expandButton.setTitle("Ukryj", for: .normal)
        } else {
            expandButton.setTitle("Pokaz", for: .normal)
        }
    }
    
    func setTag(_ tag: String) {
        tagLabel.text = tag
    }
    
    @IBAction func expandButtonPressed(_ sender: Any) {
        guard let news = self.news else {
            return
        }
        if news.isExpanded {
            news.isExpanded = false
            expandButton.setTitle("Pokaż", for: .normal)
        } else {
            news.isExpanded = true
            expandButton.setTitle("Ukryj", for: .normal)
        }
        delegate?.reloadTableViewSecion(self.section ?? 0)
    }
    
}
