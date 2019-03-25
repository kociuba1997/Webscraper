using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace WebScrapper
{
    class Program
    {
        static void Main(string[] args)
        {
            //WykopWrapper wykopWrapper = new WykopWrapper("https://www.wykop.pl/tag/gorzow/wszystkie/?nsQ=%23gorzow");
            //Console.WriteLine(wykopWrapper.getUser());
            //Console.WriteLine(wykopWrapper.getMessage());
            //Console.WriteLine(wykopWrapper.getTargetLink());

            //TwitterWrapper twitterWrapper = new TwitterWrapper("https://twitter.com/search?q=%23gorz%C3%B3w&src=typd");
            //Console.WriteLine(twitterWrapper.getUser());
            //Console.WriteLine(twitterWrapper.getMessage());
            ////Console.WriteLine(twitterWrapper.getTargetLink());

            RedditWrapper redditWrapper = new RedditWrapper("https://www.reddit.com/search?q=%23gorz%C3%B3w");
            Console.WriteLine(redditWrapper.getUser());
            Console.WriteLine(redditWrapper.getMessage());
            //Console.WriteLine(redditWrapper.getTargetLink());
            Console.ReadLine();
        }
    }
}
