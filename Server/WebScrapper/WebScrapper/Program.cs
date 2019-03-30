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
                (t) =>
                {
                    int i = t.Result;
                    string bufforString = Encoding.Default.GetString(buffer).Substring(0, i);
                    byte[] clientBuffor = new byte[1024];
                    string tag = Encoding.UTF8.GetString(buffer, 0, buffer.Length).Replace("\n", string.Empty).Replace("\0", string.Empty);
                    var values = GetWykopNews(tag).Replace("\t", string.Empty);
                    clientBuffor = Encoding.UTF8.GetBytes(values);
                    client.GetStream().Write(clientBuffor, 0, clientBuffor.Length);
                    client.Close();
                });
            }
        }

        static string GetWykopNews(string tag)
        {
            WykopWrapper wykopWrapper = new WykopWrapper("https://www.wykop.pl/tag/" + tag + "/wszystkie/");
            return wykopWrapper.getMessage();
        }

        static void Main(string[] args)
        {

            Task server = startServer(2048);
            Console.WriteLine("Server started.");
            Console.WriteLine("Wating for connection...");
            Task.WaitAll(new Task[] { server });


            //WykopWrapper wykopWrapper = new WykopWrapper("https://www.wykop.pl/tag/gorzow/wszystkie/?nsQ=%23gorzow");
            //Console.WriteLine(wykopWrapper.getUser());
            //Console.WriteLine(wykopWrapper.getMessage());
            //Console.WriteLine(wykopWrapper.getTargetLink());

            //TwitterWrapper twitterWrapper = new TwitterWrapper("https://twitter.com/search?q=%23gorz%C3%B3w&src=typd");
            //twitterWrapper.getItterator();
            //Console.WriteLine(twitterWrapper.getUser());
            //Console.WriteLine(twitterWrapper.getMessage());
            ////Console.WriteLine(twitterWrapper.getTargetLink());

            //RedditWrapper redditWrapper = new RedditWrapper("https://www.reddit.com/search?q=%23gorz%C3%B3w");
            //Console.WriteLine(redditWrapper.getUser());
            //Console.WriteLine(redditWrapper.getMessage());
            //Console.WriteLine(redditWrapper.getTargetLink());
            //Console.ReadLine();
        }
    }
}
