﻿using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Threading;
using WebScraperAPI.Controllers;
using WebScraperAPI.Model;
using WebScraperAPI.Scraper;
using MongoDB.Driver;

namespace WebScraperAPI.Scraper
{
    public class WebScraper
    {
        private Timer timer;

        public void StartScraping()
        {
            var startTimeSpan = TimeSpan.Zero;
            var periodTimeSpan = TimeSpan.FromMinutes(2);
            timer = new Timer((e) =>
            {
                Scrap();
            }, null, startTimeSpan, periodTimeSpan);
        }

        private List<string> FetchAllTags(IMongoDatabase db)
        {
            var users = db.GetCollection<User>("Users");
            List<string> tags = new List<string>();
            var userList = users.Find(x => true).ToList();
            foreach(var user in userList)
            {
                tags.AddRange(user.tags);
            }
            tags = tags.Distinct().ToList();
            return tags;
        }

        private void Scrap()
        {
            // make this method async

            // download news
            // clear database
            // add news to database
            try
            {
                //var db = UserController.ConnectToDataBase();
                //db.DropCollection("News");
                //db.CreateCollection("News");
                //var newsCollection = db.GetCollection<News>("News");

                //var allTags = FetchAllTags(db);
                //List<News> newsList = new List<News>();
                //foreach (var tag in allTags)
                //{
                    //WykopWrapper ww = new WykopWrapper();
                    //var tagNews = ww.getNewsList("gorzow");
                    //newsList.AddRange(tagNews);

                    //RedditWrapper rw = new RedditWrapper();
                    //var tagNews2 = rw.getNewsList("almslmck"); 
                    //newsList.AddRange(tagNews);

                    TwitterWrapper rw = new TwitterWrapper();
                    var tagNews3 = rw.getNewsList("gorzow");
                    //newsList.AddRange(tagNews3);

                    // add redit
                //}

                //newsCollection.InsertManyAsync(newsList);
            
            }
            catch(Exception)
            {
                System.Diagnostics.Debug.WriteLine("Failed"); // tu musi być ibsługa braku wydrzeń dla danego tagu
            }
        }
    }
}
