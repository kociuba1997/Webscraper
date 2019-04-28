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
    class TwitterWrapper : Wrapper
    {
        //*[@id="stream-items-id"]

        private string htmlMessageNode1 = ".//div/div[2]/div[2]/p";
        private string htmlMessageNode2 = ".//div/div[2]/div[3]/p";
        private string htmlUsereNode = ".//div/div[2]/div[1]/a/span[1]/strong";
        private string htmlPhotoNode1 = ".//div/div[2]/div[3]/div/div/div/div/img";
        private string htmlPhotoNode2 = ".//div/div[2]/div[4]/div/div/div/div/img ";
        private string htmlPhotoNode3 = ".//div/div[2]/div[3]/div/div/div/div[1]/div/img";
        private string htmlUsertLinkNode = ".//div/div/div[1]/a[1]";
        private string htmlTargetLinkNode = ".//div/div[2]/div[2]/p/a";
        private string htmlTargetLinkAnswearNode = ".//div/div[2]/div[3]/p/a";
        private string htmlPostDateNode = ".//div/div/div[1]/a[2]/small/time";
        private string htmlEventDateNode = ".//div/div[3]/div[3]/span/time";
        private string htmlDateNode = ".//div/div[2]/div[1]/small/a/span[1]";
        private string htlmStartingNode = "//*[@id=\"stream-items-id\"]/li";



        public List<Wrapper> wrapperList = new List<Wrapper>();

        public TwitterWrapper(string link) : base(link)
        {
        }


        public List<News> getNewsList(string tag)
        {
            getItterator();

            foreach (var wrapp in wrapperList)
            {
                string[] list = { tag };

                News news = new News(list, wrapp.user, wrapp.message, wrapp.targetLink, wrapp.photo, wrapp.date);

                newsList.Add(news);
            }
            return newsList;
        }

        public string getUser(HtmlNode userNode)
        {
            try
            {
                userNode = userNode.SelectSingleNode(htmlUsereNode);
                user = encoder(userNode.InnerText);
            }
            catch (Exception ex)
            {
                return null;
            }
            return user;
        }

        public string getMessage(HtmlNode messageNode)
        {
            try
            {
                var messageEvent = messageNode.SelectSingleNode(htmlMessageNode1);
                if (messageEvent == null)
                {
                    messageEvent = messageNode.SelectSingleNode(htmlMessageNode2);
                }
                message = encoder(messageEvent.InnerText);

                if (message.Contains("pic.twitter"))
                {
                    int index = message.IndexOf("pic.twitter");
                    message = message.Substring(0, index);
                }
            }
            catch (Exception ex)
            {
                return null;
            }
            return message;
        }

        public string getTargetLink(HtmlNode targetLinkNode)
        {
            try
            {
              if(targetLinkNode.SelectSingleNode(htmlTargetLinkNode) != null)
                {
                    foreach (HtmlNode a in htmlPageDoc.DocumentNode.SelectNodes(htmlTargetLinkNode))
                    {
                        HtmlAttribute attribute = a.Attributes["href"];
                        if(attribute.Value.Contains("https"))
                        { targetLink = attribute.Value; break; }
                       
                    }
                }
                else
                {
                    foreach (HtmlNode a in htmlPageDoc.DocumentNode.SelectNodes(htmlTargetLinkAnswearNode))
                    {
                        HtmlAttribute attribute = a.Attributes["href"];
                        if (attribute.Value.Contains("https"))
                        { targetLink = attribute.Value; break; }

                    }
                }
                
            }
            catch (Exception ex)
            {
                    return null;
            }
            return targetLink;
        }

        public string getPhoto(HtmlNode photoNode)
        {
            try
            {
                var photoEvent = photoNode.SelectSingleNode(htmlPhotoNode1);
                if (photoEvent == null)
                {
                    photoEvent = photoNode.SelectSingleNode(htmlPhotoNode2);
                }
                if (photoEvent == null)
                {
                    photoEvent = photoNode.SelectSingleNode(htmlPhotoNode3);
                }
                HtmlAttribute attribute = photoEvent.Attributes["src"];
                photo = attribute.Value;
            }
            catch (Exception ex)
            {
                return null;
            }
            return photo;
        }

        public string getDate(HtmlNode dateNode)
        {
            try
            {
                dateNode = dateNode.SelectSingleNode(htmlDateNode);
                date = dateNode.InnerText;
            }
            catch (Exception ex)
            {
                return null;
            }
            return date;
        }

        public void getItterator()
        {

            foreach (HtmlNode li in htmlPageDoc.DocumentNode.SelectNodes(htlmStartingNode))
            {
                Wrapper post = new Wrapper();

                try
                {
                    post.user = getUser(li);

                    post.message = getMessage(li);

                    post.targetLink = getTargetLink(li);

                    post.photo = getPhoto(li);

                    post.date = getDate(li);

                    wrapperList.Add(post);
                }
                catch
                {

                }
            }
        }
    }
}
