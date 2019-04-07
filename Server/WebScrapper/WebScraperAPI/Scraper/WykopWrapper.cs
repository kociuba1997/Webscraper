using HtmlAgilityPack;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;
using WebScraperAPI.Model;

namespace WebScraperAPI.Scraper
{
    class WykopWrapper
    {
        string link;
        string user;
        string targetLink;
        string message;
        string page;

        HtmlDocument doc = new HtmlDocument();
        HtmlNodeCollection nodes;

        public WykopWrapper(string link)
        {
            this.link = link;

            using (var webClient = new WebClient())
            {
                page = webClient.DownloadString(link);

                doc.LoadHtml(page);
            }

        }

        public string getUser()
        {
            HtmlNode userNode;

            try
            {
                userNode = doc.DocumentNode.SelectSingleNode("//*[@id=\"itemsStream\"]/li[1]/div/div/div[1]/a[1]/b");
                byte[] bytes = Encoding.Default.GetBytes(userNode.InnerText);
                user = Encoding.UTF8.GetString(bytes);
            }
            catch (Exception ex)
            {
                return "Błąd";
            }
            return user;

        }

        public string getMessage()
        {
            HtmlNode messageNode;

            try
            {
                messageNode = doc.DocumentNode.SelectSingleNode("//*[@id=\"itemsStream\"]/li[1]/div/div/div[2]/p");
                byte[] bytes = Encoding.Default.GetBytes(messageNode.InnerText);
                message = Encoding.UTF8.GetString(bytes);
            }
            catch (Exception ex)
            {
                return "Błąd";
            }
            return message;

        }

        public string getTargetLink()
        {
            HtmlNode targetLinkNode;

            try
            {
                // problem z zastępowaniem targetLinku tytułem wiadomosci
                targetLinkNode = doc.DocumentNode.SelectSingleNode("//*[@id=\"itemsStream\"]/li[1]/div/div/div[2]/a");
                targetLink = targetLinkNode.InnerText;
            }
            catch (Exception ex)
            {
                return "Błąd";
            }
            return targetLink;

        }

        public List<News> getItterator()
        {
            int counter = 1;
            List<News> news = new List<News>();
            foreach (HtmlNode li in doc.DocumentNode.SelectNodes("//*[@id=\"itemsStream\"]/li"))
            {
                HtmlNode userNode;
                HtmlNode messageNode;
                HtmlNode targetLinkNode;

                try
                {
                    userNode = li.SelectSingleNode(".//div/div/div[1]/a[1]/b");
                    Console.WriteLine("Uzytkownik: " + userNode.InnerText);

                    messageNode = li.SelectSingleNode(".//div/div/div[2]/p");
                    byte[] bytes = Encoding.Default.GetBytes(messageNode.InnerText);
                    message = Encoding.UTF8.GetString(bytes);

                    News n = new News();
                    n.author = userNode.InnerText;
                    n.text = message;
                    n.tags = new string[] { "gniezno" };
                    news.Add(n);
                }
                catch
                {
                    Console.WriteLine("Błąd");
                }
                counter++;
            }
            return news;
        }
    }
}
