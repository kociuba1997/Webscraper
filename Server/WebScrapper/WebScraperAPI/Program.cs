using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore;
using Microsoft.AspNetCore.Hosting;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.Logging;
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
                .UseUrls("https://192.168.0.18:5000")
                .UseStartup<Startup>();
    }
}
