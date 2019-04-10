using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Threading.Tasks;

namespace WebScrapper
{
    class Program
    {


        static void Main(string[] args)
        {
            //WykopWrapper wykopWrapper = new WykopWrapper("https://www.wykop.pl/tag/politechnikapoznanska/wszystkie/?nsQ=%23politechnikapoznanska");
            //wykopWrapper.getItterator();

            // foreach(var post in wykopWrapper.wrapperList)
            // {
            //     Console.WriteLine(post.user);
            //     Console.WriteLine(post.message);
            //     Console.WriteLine(post.targetLink);
            //     Console.WriteLine(post.photo);

            // }

            RedditWrapper reddit = new RedditWrapper("https://www.reddit.com/search?q=%23gorz%C3%B3w");
            reddit.getItterator();

            foreach (var post in reddit.wrapperList)
            {
                Console.WriteLine(post.user);
                Console.WriteLine(post.message);
                Console.WriteLine(post.targetLink);
                Console.WriteLine(post.photo);

            }


            Console.ReadLine();
        }
    }
}


