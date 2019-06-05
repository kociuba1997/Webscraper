using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore;
using WebScraperAPI.Scraper;

namespace WebScraperAPI
{
    public class Program
    {
        public static void Main(string[] args)
        {
            var scraper = new WebScraper();
            scraper.StartScraping();
            CreateWebHostBuilder(args).Build().Run();
        }

        public static IWebHostBuilder CreateWebHostBuilder(string[] args) =>
            WebHost.CreateDefaultBuilder(args)
                .UseUrls("https://192.168.0.5:5000")
                .UseStartup<Startup>();
    }
}
