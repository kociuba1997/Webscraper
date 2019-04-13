using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using System.IdentityModel.Tokens.Jwt;
using WebScraperAPI.Logic;
using WebScraperAPI.Model;
using System.Security.Claims;
using MongoDB.Driver;

namespace WebScraperAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class LoginController : ControllerBase
    {

        // POST: api/login
        [HttpPost]
        public IActionResult Post([FromBody] User user)
        {
            string username = user.username;
            string password = user.password;
            try
            {
                var db = UserController.ConnectToDataBase();
                var collection = db.GetCollection<User>("Users");
                var results = collection.Find(x => x.username == username && x.password == password).ToList();
                if (results.Count > 0) {
                    user = results.First();
                    var token = JwtManager.GenerateToken(username);
                    user.token = token;
                    collection.ReplaceOne(x => x.username == username && x.password == password, user);
                    return Ok(token);
                }
                return StatusCode(403);
            }
            catch (Exception)
            {
                return StatusCode(500);
            }
        }

        // DELETE: api/login
        [HttpDelete]
        public IActionResult Delete()
        {
            var token = Request.Headers["Authorization"];
            // find user with this token and remove token from this user
            try
            {
                var db = UserController.ConnectToDataBase();
                var collection = db.GetCollection<User>("Users");
                var results = collection.Find(x => x.token == token).ToList();
                if (results.Count > 0)
                {
                    var user = results.First();
                    user.token = "";
                    collection.ReplaceOne(x => x.token == token, user);
                    return StatusCode(200);
                }
                return StatusCode(403);
            }
            catch (Exception)
            {
                return StatusCode(500);
            }
            // var id = JwtManager.GetUserId(token);
        }
    }
}
