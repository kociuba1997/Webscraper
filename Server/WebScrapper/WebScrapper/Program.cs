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
        static async Task startServer(int port)
        {
            TcpListener server = new TcpListener(IPAddress.Any, port);
            server.Start();
            while (true)
            {
                TcpClient client = await server.AcceptTcpClientAsync();
                byte[] buffer = new byte[1024];
                await client.GetStream().ReadAsync(buffer, 0, buffer.Length).ContinueWith(
                async (t) =>
                {
                    int i = t.Result;
                    while (true)
                    {
                        string bufforString = Encoding.Default.GetString(buffer).Substring(0, i);
                        byte[] clientBuffor = new byte[1024];
                        var values = GetWykopNews();
                        clientBuffor = Encoding.ASCII.GetBytes(values);
                        await client.GetStream().WriteAsync(clientBuffor, 0, clientBuffor.Length);
                        i = await client.GetStream().ReadAsync(buffer, 0, buffer.Length);
                    }
                });
            }
        }

        static string GetWykopNews()
        {
            WykopWrapper wykopWrapper = new WykopWrapper("https://www.wykop.pl/tag/gorzow/wszystkie/?nsQ=%23gorzow");
            return wykopWrapper.getMessage();
        }


        static void Main(string[] args)
        {

           //WykopWrapper wykopWrapper = new WykopWrapper("https://www.wykop.pl/tag/politechnikapoznanska/wszystkie/?nsQ=%23politechnikapoznanska");
           //wykopWrapper.getItterator();
           
           //TwitterWrapper twitterWrapper = new TwitterWrapper("https://twitter.com/search?q=%23gorz%C3%B3w&src=typd");
           //twitterWrapper.getItterator();

           RedditWrapper redditWrapper = new RedditWrapper("https://www.reddit.com/search?q=%23gorz%C3%B3w");
           redditWrapper.getItterator();
          
           Console.ReadLine();
        }
    }
}


/////* Task server = startServer(2048);
//// Console.WriteLine("Server started.");
//// Console.WriteLine("Wating for connection...");
//// Task.WaitAll(new Task[] { server });*/


//// //WykopWrapper wykopWrapper = new WykopWrapper("https://www.wykop.pl/tag/informatyka/wszystkie/?nsQ=%23informatyka");
//// //wykopWrapper.getItterator();
//// //Console.WriteLine(wykopWrapper.getUser());
//// //Console.WriteLine(wykopWrapper.getMessage());
//// //Console.WriteLine(wykopWrapper.getTargetLink());

//// //TwitterWrapper twitterWrapper = new TwitterWrapper("https://twitter.com/search?q=%23gorz%C3%B3w&src=typd");
//// //twitterWrapper.getItterator();
//// //Console.WriteLine(twitterWrapper.getUser());
//// //Console.WriteLine(twitterWrapper.getMessage());
//// ////Console.WriteLine(twitterWrapper.getTargetLink());

//// RedditWrapper redditWrapper = new RedditWrapper("https://www.reddit.com/search?q=%23gorz%C3%B3w");
//// redditWrapper.getItterator();
//// //Console.WriteLine(redditWrapper.getUser());
//// //Console.WriteLine(redditWrapper.getMessage());
//// //Console.WriteLine(redditWrapper.getTargetLink());
//// Console.ReadLine();