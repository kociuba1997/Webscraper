using MongoDB.Bson;
using MongoDB.Bson.Serialization.Attributes;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace WebScraperAPI.Model
{
    public class News
    {
        [BsonId]
        [BsonRepresentation(BsonType.ObjectId)]
        public string id;
        public string[] tags = new string[] { };
        public string author;
        public string text;
        public string link;
        public string photo;

        public News(string[] tags, string author, string text, string link, string photo)
        {
            this.tags = tags;
            this.author = author;
            this.text = text;
            this.link = link;
            this.photo = photo;
        }
    }
}
