using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using System.Threading;
using WebScraperAPI.Controllers;
using WebScraperAPI.Model;
using WebScraperAPI.Scraper;

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

        private void Scrap()
        {
            // make this method async

            // download news
            // clear database
            // add news to database
            WykopWrapper ww = new WykopWrapper("https://www.wykop.pl/tag/gniezno/");
            var news = ww.getNewsList();
            try
            {
                var db = UserController.ConnectToDataBase();
                db.DropCollection("News");
                db.CreateCollection("News");
                var newsCollection = db.GetCollection<News>("News");
                newsCollection.InsertManyAsync(news);
            }
            catch(Exception ex)
            {
                System.Diagnostics.Debug.WriteLine("Failed");
            }
        }
    }
}
