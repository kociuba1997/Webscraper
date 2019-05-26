//
//  RestService.swift
//  WebScraper
//
//  Created by RSQ Technologies on 09/04/2019.
//  Copyright © 2019 RSQ Technologies. All rights reserved.
//

import UIKit
import Alamofire
import AlamofireObjectMapper

class RestService: NSObject {
    
    static let shared = RestService()
    static let ip = "192.168.0.18"
    
    let url = "https://" + ip + ":5000/api/"
    
    private var defaultManager: SessionManager = {
        let trustPolicies: [String: ServerTrustPolicy] = [
            ip: .disableEvaluation
        ]
        let configuration = URLSessionConfiguration.default
        configuration.httpAdditionalHeaders = SessionManager.defaultHTTPHeaders
        let man = SessionManager(configuration: URLSessionConfiguration.default,
        serverTrustPolicyManager: ServerTrustPolicyManager(policies: trustPolicies))
        return man
    }()
    
    func register(username: String, password: String, completion: @escaping (Bool) -> Void) {
        let parameters: [String: String] = ["username": username,
                                            "password": password]
        let address = url + "user"
        defaultManager.request(address, method: .post, parameters: parameters, encoding: JSONEncoding.default).response { response in
            let ok = self.checkStatusCode(response.response?.statusCode ?? 400)
            completion(ok)
        }
    }
    
    func login(username: String, password: String, completion: @escaping (Bool, String?) -> Void) {
        let parameters: [String: String] = ["username": username,
                                            "password": password]
        let address = url + "login"
        defaultManager.request(address, method: .post, parameters: parameters, encoding: JSONEncoding.default).responseString { response in
            let ok = self.checkStatusCode(response.response?.statusCode ?? 400)
            completion(ok, response.value)
        }
    }
    
    func logout(completion: @escaping () -> Void) {
        let address = url + "login"
        let token = KeychainService.loadToken(service: "tokenService", account: "user")
        let headers = [
            "Authorization": token!
        ]
        defaultManager.request(address, method: .delete, headers: headers).responseString { response in
            completion()
        }
    }
    
    func udpateTags(tags: [String], completion: @escaping (Bool) -> Void) {
        let address = url + "user/tags"
        let token = KeychainService.loadToken(service: "tokenService", account: "user")
        let headers = [
            "Authorization": token!
        ]
        let parameters: Parameters = [
            "tags": tags
        ]
        defaultManager.request(address, method: .put, parameters: parameters, encoding: JSONEncoding.default, headers: headers).responseString { response in
            switch (response.result) {
            case .success:
                completion(true)
            case .failure(_):
                completion(false)
            }
        }
    }
    
    func getNews(fetchNews: Bool, completion: @escaping ([News]?) -> Void) {
        let address = url + "user/news?fetchNews=" + fetchNews.description
        let token = KeychainService.loadToken(service: "tokenService", account: "user")
        let headers = [
            "Authorization": token!
        ]
        defaultManager.request(address, method: .get, headers: headers).responseArray { (response: DataResponse<[News]>) in
            completion(response.result.value)
        }
    }
    
    func getTags(completion: @escaping ([String]?) -> Void) {
        let address = url + "user/tags"
        let token = KeychainService.loadToken(service: "tokenService", account: "user")
        let headers = [
            "Authorization": token!
        ]
        defaultManager.request(address, method: .get, headers: headers).responseJSON { response in
            completion(response.result.value as? [String])
        }
    }
    
    
    func checkStatusCode(_ code: Int) -> Bool {
        switch code {
        case 400...505: return false
        default: return true
        }
    }
}
