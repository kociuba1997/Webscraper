using HtmlAgilityPack;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;

namespace WebScrapper
{
    class RedditWrapper
    {

        string link;
        string user;
        string targetLink;
        string message;
        string page;

        HtmlDocument doc = new HtmlDocument();
        HtmlNodeCollection nodes;

        public RedditWrapper(string link)
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
                userNode = doc.DocumentNode.SelectSingleNode("//*[@id=\"t3_ar8ef9\"]/div[2]/div/div[2]/div[2]/div[2]/div/a");
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
                messageNode = doc.DocumentNode.SelectSingleNode("//*[@id=\"t3_ar8ef9\"]/div[2]/div/div[2]/div[1]/span/a/h2/span");
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
                // problem z zastępowaniem targetLinku nickiem lub godziną
                targetLinkNode = doc.DocumentNode.SelectSingleNode("//*[@id=\"t3_ar8ef9\"]/div[2]/div/div[2]/div[1]/span/a");
                targetLink = targetLinkNode.InnerText;
            }
            catch (Exception ex)
            {
                return "Błąd";
            }
            return targetLink;

        }
    }
}
