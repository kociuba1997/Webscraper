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
        public string message = string.Empty;
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

        

    }
}
