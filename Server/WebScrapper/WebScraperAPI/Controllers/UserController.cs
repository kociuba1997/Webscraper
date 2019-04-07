using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using MongoDB.Driver;
using WebScraperAPI.Logic;
using WebScraperAPI.Model;

namespace WebScraperAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UserController : ControllerBase
    {

        // POST: api/user - registration
        [HttpPost]
        public IActionResult Post([FromBody] User user)
        {
            // add user to database
            try
            {
                var db = ConnectToDataBase();
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
        public IActionResult GetNews()
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
                var db = ConnectToDataBase();
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

        // POST: api/user/tags
        [Route("tags")]
        [HttpPost]
        public IActionResult PostTags([FromBody] string[] tags)
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
                    List<string> tagsList = tags.ToList();
                    foreach (var tag in tags)
                    {
                        if(!tagsList.Contains(tag))
                        {
                            tagsList.Add(tag);
                        }
                    }
                    user.tags = tagsList.ToArray();
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
