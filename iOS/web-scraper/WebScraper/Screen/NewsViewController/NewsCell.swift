//
//  NewsCell.swift
//  WebScraper
//
//  Created by RSQ Technologies on 24/04/2019.
//  Copyright © 2019 RSQ Technologies. All rights reserved.
//

import UIKit

extension UIImageView {
    func downloaded(from url: URL, contentMode mode: UIView.ContentMode = .scaleAspectFit) {  // for swift 4.2 syntax just use ===> mode: UIView.ContentMode
        contentMode = mode
        URLSession.shared.dataTask(with: url) { data, response, error in
            guard
                let httpURLResponse = response as? HTTPURLResponse, httpURLResponse.statusCode == 200,
                let mimeType = response?.mimeType, mimeType.hasPrefix("image"),
                let data = data, error == nil,
                let image = UIImage(data: data)
                else { return }
            DispatchQueue.main.async() {
                self.image = image
            }
            }.resume()
    }
    func downloaded(from link: String, contentMode mode: UIView.ContentMode = .scaleAspectFit) {  // for swift 4.2 syntax just use ===> mode: UIView.ContentMode
        guard let url = URL(string: link) else { return }
        downloaded(from: url, contentMode: mode)
    }
}

class NewsCell: UITableViewCell {

    @IBOutlet weak var cellView: UIView!
    @IBOutlet weak var authorLabel: UILabel!
    @IBOutlet weak var tagsLabel: UILabel!
    @IBOutlet weak var dateLabel: UILabel!
    @IBOutlet weak var newsImage: UIImageView!
    @IBOutlet weak var newsTextLabel: UILabel!
    
    func setup(_ author: String, tags: [String], date: String, newsText: String, imageLink: String) {
        authorLabel.text = author
        tagsLabel.text = tags.joined(separator: ", ")
        dateLabel.text = date
        newsTextLabel.text = newsText
        newsImage.downloaded(from: imageLink)
        
        cellView.layer.cornerRadius = 8.0
        cellView.clipsToBounds = true
        cellView.layer.masksToBounds = false
        cellView.layer.shadowColor = UIColor.lightGray.cgColor
        cellView.backgroundColor = UIColor.white
        cellView.layer.shadowOffset = CGSize(width: 1.0, height: 1.0)
        cellView.layer.shadowOpacity = 0.5
        
        newsImage.layer.cornerRadius = 8.0
        newsImage.clipsToBounds = true
    }
    
    override func awakeFromNib() {
        super.awakeFromNib()
    }
    
}
