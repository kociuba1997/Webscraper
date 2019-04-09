using HtmlAgilityPack;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;
using System.Threading.Tasks;

namespace WebScrapper
{
    class WykopWrapper : Wrapper
    {
  

        public string htmlMessageNode = ".//div/div/div[2]/p";
        public string htmlUsereNode = ".//div/div/div[1]/a[1]/b";
        public string htmlPageNode;
        public string htmlPhotoNode;
        public string htmltargetLink = ".//div/div/div[1]/a[1]";
        public string htlmStarting = "//*[@id=\"itemsStream\"]/li[1]";

        public WykopWrapper(string link) : base(link) 
        {
        }

        public void getItterator()
        {
            int counter = 1;
            //List<Record> lstRecords = new List<Record>();
            foreach (HtmlNode li in htmlPageDoc.DocumentNode.SelectNodes("//*[@id=\"itemsStream\"]/li"))
            {
                HtmlNode userNode;
                HtmlNode messageNode;
                HtmlNode TargetLink;

                try
                {
                    userNode = li.SelectSingleNode(htmlUsereNode);
                    Console.WriteLine("Uzytkownik: " + encoder(userNode.InnerText));

                    messageNode = li.SelectSingleNode(htmlMessageNode);
                    
                    Console.WriteLine("Wpis: ");
                    Console.WriteLine(encoder(messageNode.InnerText));

                    TargetLink = li.SelectSingleNode(htmltargetLink);

                    Console.WriteLine(encoder(TargetLink.InnerText));
                    Console.WriteLine("/////////////////////////////////////////////////////////////////////////////////////");


                }
                catch
                {
                    Console.WriteLine("");
                }
                counter++;
            }
        }
    }
}
