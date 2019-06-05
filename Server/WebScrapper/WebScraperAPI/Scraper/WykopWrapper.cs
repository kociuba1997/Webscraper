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
    class WykopWrapper : Wrapper
    {
        private string htmlMessageNode = ".//div/div/div[2]/p";
        private string htmlUserNode = ".//div/div/div[1]/a[1]/b";
        private string htmlsUserNode2 = ".//div/div[3]/div[1]/a[1]";
        private string htmlPhotoNode = ".//div/div/div[2]/div[1]/a";
        private string htmlPhotoNode2 = ".//div/div/div[2]/div[1]/a/img";
        private string htmlUsertLinkNode = ".//div/div/div[1]/a[1]";
        private string htmlTargetLinkNode = ".//div/div/div[1]/a[2]";
        private string htmlTargetLinkNode2 = ".//div/div[3]/div[2]/p/a";
        private string htmlPostDateNode = ".//div/div/div[1]/a[2]/small/time";
        private string htmlEventDateNode = ".//div/div[3]/div[3]/span/time";
        private string htlmStartingNode = "//*[@id=\"itemsStream\"]/li";
        private string pageLink = "https://www.wykop.pl/tag/{0}/";

        public List<Wrapper> wrapperList = new List<Wrapper>();

       public List<News> getNewsList(string tag)
        {
            getItterator(tag);
            
            foreach (var wrapp in wrapperList)
            {
                string[] list = { tag };

                News news = new News(list, wrapp.user,  wrapp.message.Trim(), wrapp.targetLink, wrapp.photo, wrapp.date, wrapp.page);

                newsList.Add(news);
            }
            return newsList;
        }

        public async Task<List<News>> getNewsListAsync(string tag)
        {
            await getItteratorAsync(tag);

            foreach (var wrapp in wrapperList)
            {
                string[] list = { tag };

                News news = new News(list, wrapp.user, wrapp.message.Trim(), wrapp.targetLink, wrapp.photo, wrapp.date, wrapp.page);

                newsList.Add(news);
            }
            return newsList;
        }

        public string getUser(HtmlNode userNode)
        {
            try
            {
                var userNodeEvent = userNode.SelectSingleNode(htmlUserNode);
                if(userNodeEvent == null)
                {
                    userNodeEvent = userNode.SelectSingleNode(htmlsUserNode2);
                }
                user = encoder(userNodeEvent.InnerText.TrimStart('@'));
            }
            catch (Exception)
            {
                return null;
            }
            return user;
        }

        public string getMessage(HtmlNode messageNode)
        {
            try
            {
                messageNode = messageNode.SelectSingleNode(htmlMessageNode);
                message = encoder(messageNode.InnerText);
            }
            catch (Exception)
            {
                return "Błędna wiadomość";
            }
            return message;
        }

        public string getTargetLink(HtmlNode targetLinkNode)
        {
            try
            {
                var targetLinkNodeEvent = targetLinkNode.SelectSingleNode(htmlTargetLinkNode);
                if(targetLinkNodeEvent == null || targetLinkNodeEvent.InnerText.Contains("#"))
                {
                   targetLinkNodeEvent = targetLinkNode.SelectSingleNode(htmlTargetLinkNode2);
                }
                HtmlAttribute attribute = targetLinkNodeEvent.Attributes["href"];
                targetLink = attribute.Value;
            }
            catch (Exception)
            {
                return "www.wykop.pl";
            }
            return targetLink;
        }

        public string getPhoto(HtmlNode photoNode)
        {
            try
            {
                var photoNodeEvent = photoNode.SelectSingleNode(htmlPhotoNode);
                if(photoNodeEvent == null || photoNodeEvent.InnerHtml.Contains("src"))
                {
                    photoNodeEvent = photoNode.SelectSingleNode(htmlPhotoNode2);
                    HtmlAttribute attribute = photoNodeEvent.Attributes["src"];
                    photo = attribute.Value;
                }
                else
                {
                    HtmlAttribute attribute = photoNodeEvent.Attributes["href"];
                    photo = attribute.Value;
                }
               
            }
            catch (Exception)
            {
                return null;
            }
            return photo;
        }

        public string getDate(HtmlNode dateNode)
        {
            try
            {
                var dateNodeEvent = dateNode.SelectSingleNode(htmlEventDateNode);
                if (dateNodeEvent == null)
                {
                    dateNodeEvent = dateNode.SelectSingleNode(htmlPostDateNode);
                }

                date = dateNodeEvent.InnerText;
            }
            catch (Exception)
            {
                return null;
            }
            return date;
        }

        public void getItterator(string tag)
        {
            string link = String.Format(pageLink, tag);
            getPage(link);
            page = "Wykop";

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

                    post.page = page;

                    wrapperList.Add(post);
                }
                catch
                {
                   
                }
            }
        }

        public async Task getItteratorAsync(string tag)
        {
            string link = String.Format(pageLink, tag);
            await getPageAsync(link);
            page = "Wykop";

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

                    post.page = page;

                    wrapperList.Add(post);
                }
                catch
                {

                }
            }
        }
    }
}
