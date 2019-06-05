using HtmlAgilityPack;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Text;
using System.Text.RegularExpressions;
using System.Threading.Tasks;
using WebScraperAPI.Model;

namespace WebScraperAPI.Scraper
{
    class RedditWrapper : Wrapper
    {

        private string htmlMessageNode = ".//div[2]/div/div[2]/div[1]/span/a/h2/span";
        private string htmlUsereNode = ".//div[2]/div/div[2]/div[2]/div[2]/div/a";
        private string htmlPhotoNode = ".//div[2]/div/div[1]/div/div/a/div";
        private string htmlUsertLinkNode = ".//div/div/div[1]/a[1]";
        private string htmlTargetLinkNode = ".//div[2]/div/div[2]/div[1]/span/a";
        private string htmlDateNode = ".//div[2]/div/div[2]/div[2]/div[2]/a";
        private string htlmStartingNode = "//*[@id=\"SHORTCUT_FOCUSABLE_DIV\"]/div[2]/div/div/div/div[2]/div[3]/div[1]/div[3]/div";
        private string pageLink = "https://www.reddit.com/search?q=%23{0}";

        public List<Wrapper> wrapperList = new List<Wrapper>();

        public List<News> getNewsList(string tag)
        {
            getItterator(tag);
            
            foreach (var wrapp in wrapperList)
            {
                string[] list = { tag };

                News news = new News(list, wrapp.user, wrapp.message.Trim(), wrapp.targetLink, wrapp.photo, wrapp.date, wrapp.page);

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
                userNode = userNode.SelectSingleNode(htmlUsereNode);
                user = encoder(userNode.InnerText);
                user = user.Substring(2);
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
                targetLinkNode = targetLinkNode.SelectSingleNode(htmlTargetLinkNode);
                HtmlAttribute attribute = targetLinkNode.Attributes["href"];
                targetLink = "https://www.reddit.com";
                targetLink += attribute.Value;
            }
            catch (Exception)
            {
                return "https://www.reddit.com/";
            }
            return targetLink;

        }

        public string getPhoto(HtmlNode photoNode)
        {
            try
            {
                string extractionLink;
                photoNode = photoNode.SelectSingleNode(htmlPhotoNode);
                HtmlAttribute attribute = photoNode.Attributes["style"];
                extractionLink = attribute.Value;

                Regex rx = new Regex(@"\((.*?)\)");
                MatchCollection matches = rx.Matches(extractionLink);

                photo = matches[0].Groups[0].Value.TrimStart('(').TrimEnd(')');
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
                var dateNodeEvent = dateNode.SelectSingleNode(htmlDateNode);
                if (dateNodeEvent == null)
                {
                    dateNodeEvent = dateNode.SelectSingleNode(htmlDateNode);
                }
                //HtmlAttribute attribute = dateNodeEvent.Attributes["title"];
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
            page = "Reddit";
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
            page = "Reddit";
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
