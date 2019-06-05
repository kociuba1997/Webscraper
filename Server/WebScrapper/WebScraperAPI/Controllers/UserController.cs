using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using MongoDB.Driver;
using WebScraperAPI.Logic;
using WebScraperAPI.Model;
using WebScraperAPI.Scraper;

namespace WebScraperAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UserController : ControllerBase
    {
        IMongoDatabase db = ConnectToDataBase();
        // POST: api/user - registration
        [HttpPost]
        public IActionResult Post([FromBody] User user)
        {
            // add user to database
            try
            {
                var collection = db.GetCollection<User>("Users");
                var results = collection.Find(x => x.username == user.username).ToList();
                if (results.Count > 0)
                {
                    return StatusCode(406);
                }
                collection.InsertOneAsync(user);
                return StatusCode(201);
            }
            catch(Exception)
            {
                return StatusCode(500);
            }
        }

        // GET: api/user/news
        [Route("news")]
        [HttpGet]
        public IActionResult GetNews([FromQuery] bool fetchNews)
        {
            var token = Request.Headers["Authorization"];
            try
            {
                var usersCollection = db.GetCollection<User>("Users");
                var results = usersCollection.Find(x => x.token == token).ToList();
                if (results.Count > 0)
                {
                    if (fetchNews) {
                        var scraper = new WebScraper();
                        scraper.Scrap();
                    }
                    var user = results.First();
                    var tags = user.tags;
                    var newsCollection = db.GetCollection<News>("News");
                    List<News> news = new List<News>();
                    foreach (var tag in tags) {
                        var newsDb = newsCollection.Find(n => n.tags.Contains(tag)).ToList();
                        if (newsDb.Count > 0)
                        {
                            news.AddRange(newsDb);                      
                        }
                    }
                    return Ok(news);
                }
                return StatusCode(403);
            }
            catch (Exception)
            {
                return StatusCode(500);
            }
        }

        // GET: api/user/tags
        [Route("tags")]
        [HttpGet]
        public IActionResult GetTags()
        {
            var token = Request.Headers["Authorization"];
            try
            {
                var usersCollection = db.GetCollection<User>("Users");
                var results = usersCollection.Find(x => x.token == token).ToList();
                if (results.Count > 0)
                {
                    var user = results.First();
                    var tags = user.tags;
                    return Ok(tags);
                }
                return StatusCode(403);
            }
            catch (Exception)
            {
                return StatusCode(500);
            }
        }

        // GET: api/user/popularTags
        [Route("popularTags")]
        [HttpGet]
        public IActionResult GetPopularTags()
        {
            var token = Request.Headers["Authorization"];
            try
            {
                var usersCollection = db.GetCollection<User>("Users");
                var results = usersCollection.Find(x => x.token == token).ToList();
                if (results.Count > 0)
                {
                    var popularTagsCollection = db.GetCollection<Tags>("PopularTags");
                    var tagsResults = popularTagsCollection.Find(x => true).ToList();
                    if (tagsResults.Count > 0)
                    {
                        var tags = tagsResults.First();
                        return Ok(tags.tags);
                    }
                }
                return StatusCode(403);
            }
            catch (Exception)
            {
                return StatusCode(500);
            }
        }

        // PUT: api/user/tags
        [Route("tags")]
        [HttpPut]
        public IActionResult PutTags([FromBody] Tags tags)
        {
            var token = Request.Headers["Authorization"];
            try
            {
                var usersCollection = db.GetCollection<User>("Users");
                var results = usersCollection.Find(x => x.token == token).ToList();
                if (results.Count > 0)
                {
                    var user = results.First();
                    user.tags = tags.tags.ToArray();
                    usersCollection.ReplaceOne(x => x.token == token, user);
                    Task.Factory.StartNew(FetchPopularTags);
                    return StatusCode(201);
                }
                return StatusCode(403);
            }
            catch (Exception)
            {
                return StatusCode(500);
            }
        }

        public static void FetchPopularTags()
        {
            var bqManager = new BigQueryManager();
            var tags = bqManager.GetPopularTags().ToArray();
            Tags t = new Tags
            {
                tags = tags
            };
            var db = ConnectToDataBase();
            db.DropCollection("PopularTags");
            db.CreateCollection("PopularTags");
            var popularTagsCollection = db.GetCollection<Tags>("PopularTags");
            popularTagsCollection.InsertOneAsync(t);
        }

        // DELETE: api/user/tags
        [Route("tags")]
        [HttpDelete]
        public IActionResult Delete()
        {
            var token = Request.Headers["Authorization"];
            try
            {
                var db = ConnectToDataBase();
                var usersCollection = db.GetCollection<User>("Users");
                var results = usersCollection.Find(x => x.token == token).ToList();
                if (results.Count > 0)
                {
                    var user = results.First();
                    user.tags = new string[] { };
                    usersCollection.ReplaceOne(x => x.token == token, user);
                    return StatusCode(201);
                }
                return StatusCode(403);
            }
            catch (Exception)
            {
                return StatusCode(500);
            }
        }

        public static IMongoDatabase ConnectToDataBase()
        {
            var client = new MongoClient("mongodb+srv://webScraperAdmin01:PASSWORD@webscrapercluster-grsrl.mongodb.net/test?retryWrites=true");
            var database = client.GetDatabase("WebScraperDB");
            return database;
        }
    }
}
