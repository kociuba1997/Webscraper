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

        }//*[@id="stream-item-tweet-1107714778510831618"]/div/div[2]/div[3]/p

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

        public void getItterator()
        {
            int counter = 1;
            //List<Record> lstRecords = new List<Record>();
            foreach (HtmlNode li in doc.DocumentNode.SelectNodes("//*[@id=\"stream-items-id\"]"))
            {
                HtmlNode userNode;
                HtmlNode messageNode;
                HtmlNode targetLinkNode;

                try
                {
                    userNode = li.SelectSingleNode(".//div/div[2]/div[1]/a/span[1]/strong");
                    Console.WriteLine(userNode.InnerText);

                    messageNode = li.SelectSingleNode(".//div/div[2]/div[3]/p");
                    byte[] bytes = Encoding.Default.GetBytes(messageNode.InnerText);
                    message = Encoding.UTF8.GetString(bytes);
                    Console.WriteLine(message);


                }
                catch
                {
                    Console.WriteLine("Błąd");
                }
                counter++;
            }
        }
    }
}
