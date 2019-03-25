using HtmlAgilityPack;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;

namespace WebScrapper
{
    class TwitterWrapper
    {

        string link;
        string user;
        string targetLink;
        string message;
        string page;

        HtmlDocument doc = new HtmlDocument();
        HtmlNodeCollection nodes;

        public TwitterWrapper(string link)
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
                userNode = doc.DocumentNode.SelectSingleNode("//*[@id=\"stream-item-tweet-1110095336188665856\"]/div/div[2]/div[1]/a/span[1]/strong");
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
                messageNode = doc.DocumentNode.SelectSingleNode("//*[@id=\"stream-item-tweet-1110095336188665856\"]/div/div[2]/div[3]/p");
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
                // problem z zastępowaniem targetLinku bo odwoluje się do wiekszego postu
                targetLinkNode = doc.DocumentNode.SelectSingleNode("//*[@id=\"stream-item-tweet-1110095336188665856\"]/div");
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
