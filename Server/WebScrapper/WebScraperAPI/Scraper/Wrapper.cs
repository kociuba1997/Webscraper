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
    class Wrapper
    {
        public string link;
        public string user;
        public string targetLink;
        public string message;
        public string page;
        public string photo = null;
        public string date;
        protected HtmlDocument htmlPageDoc = new HtmlDocument();
        protected HtmlNodeCollection pageNodes;
        protected List<News> newsList = new List<News>();

        public Wrapper() { }
        public Wrapper(string link)
        {
            this.link = link;

            using (var webClient = new WebClient())
            {
                page = webClient.DownloadString(link);

                htmlPageDoc.LoadHtml(page);
            }

        }

        public string encoder(string en)
        {
            byte[] bytes = Encoding.Default.GetBytes(en);
            string decode = Encoding.UTF8.GetString(bytes);

            return decode;
        }

        public string getUser(string htmlNode)
        {
            HtmlNode userNode;

            try
            {
                userNode = htmlPageDoc.DocumentNode.SelectSingleNode(htmlNode);
                user = encoder(userNode.InnerText);
            }
            catch (Exception ex)
            {
                return "Błąd";
            }

            return user;
        }

        public string getMessage(string htmlNode)
        {
            HtmlNode messageNode;

            try
            {
                messageNode = htmlPageDoc.DocumentNode.SelectSingleNode(htmlNode);
                message = encoder(messageNode.InnerText);
            }
            catch (Exception ex)
            {
                return "Błąd";
            }
            return message;

        }

        public string getTargetLink(string htmlNode)
        {
            HtmlNode targetLinkNode;

            try
            {
                // problem z zastępowaniem targetLinku tytułem wiadomosci
                targetLinkNode = htmlPageDoc.DocumentNode.SelectSingleNode(htmlNode);
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
