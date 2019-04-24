using Microsoft.AspNetCore;
using Microsoft.AspNetCore.Hosting;
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
                .UseUrls("https://192.168.56.1:5000")
                .UseStartup<Startup>();
    }
}
