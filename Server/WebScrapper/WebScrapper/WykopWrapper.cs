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
        public string htmlPhotoNode = ".//div/div/div[2]/div[1]/a";
        public string htmlUsertLink = ".//div/div/div[1]/a[1]";
        public string htmlTargetLink = ".//div/div/div[1]/a[2]";
        public string htlmStarting = "//*[@id=\"itemsStream\"]/li[1]";

        public List<Wrapper> wrapperList = new List<Wrapper>();

        public WykopWrapper(string link) : base(link) 
        {
        }

        public void getItterator()
        {
           
            foreach (HtmlNode li in htmlPageDoc.DocumentNode.SelectNodes("//*[@id=\"itemsStream\"]/li"))
            {
                Wrapper post = new Wrapper();
                HtmlNode userNode;
                HtmlNode messageNode;
                HtmlNode TargetLink;
                HtmlNode photoNode;

                try
                {
                    userNode = li.SelectSingleNode(htmlUsereNode);
                    post.user = encoder(userNode.InnerText);

                    messageNode = li.SelectSingleNode(htmlMessageNode);
                    post.message = encoder(messageNode.InnerText);
                   
                    TargetLink = li.SelectSingleNode(htmlTargetLink);
                    HtmlAttribute att = TargetLink.Attributes["href"];
                    post.targetLink = encoder(att.Value);

                    try
                    {
                        photoNode = li.SelectSingleNode(htmlPhotoNode);
                        HtmlAttribute pho = photoNode.Attributes["href"];
                        post.photo = pho.Value;
                    }
                    catch
                    {
                        post.photo = null;
                    }
                    wrapperList.Add(post);

                }
                catch
                {
                   
                }
                
            }
        }
    }
}
