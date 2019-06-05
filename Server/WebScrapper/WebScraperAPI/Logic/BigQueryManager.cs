using Google.Cloud.BigQuery.V2;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace WebScraperAPI.Logic
{
    public class BigQueryManager
    {
        BigQueryClient client = BigQueryClient.Create("webscraper-69f11");

        public BigQueryManager()
        {
            const string name = "C:\\BigQuery\\NewsScraper-afd6bcedcc98.json";
            string value = Environment.GetEnvironmentVariable(name);
            Environment.SetEnvironmentVariable(name, value, EnvironmentVariableTarget.Machine);
        }

        public List<string> GetPopularTags()
        {
            const string format = "yyyyMMdd";
            const string tablePrefix = "analytics_198930456.events_";
            const string todayTablePrefix = "analytics_198930456.events_intraday_";

            var todayDate = DateTime.Today;
            string todayTable = todayTablePrefix + todayDate.ToString(format);
            string d2Table = tablePrefix + todayDate.AddDays(-1).ToString(format);
            string d3Table = tablePrefix + todayDate.AddDays(-2).ToString(format);

            CreateTableIfNotExist(todayTable);
            CreateTableIfNotExist(d2Table);
            CreateTableIfNotExist(d3Table);

            string query = @"
            SELECT event_name, COUNT(event_name) AS tag_count FROM ( " +
            "SELECT event_name FROM `" + todayTable + "`, UNNEST(event_params) AS p1 WHERE p1.key = 'TAG' " +
            "UNION ALL SELECT event_name FROM `" + d2Table + "`, UNNEST(event_params) AS p2 WHERE p2.key = 'TAG' " +
            "UNION ALL SELECT event_name FROM `" + d3Table + "`, UNNEST(event_params) AS p3 WHERE p3.key = 'TAG')" +
            "GROUP BY event_name " +
            "ORDER BY tag_count DESC " +
            "LIMIT 5";

            BigQueryJob job = client.CreateQueryJob(
                    sql: query,
                    parameters: null,
                    options: new QueryOptions { UseQueryCache = false });
            job.PollUntilCompleted();

            List<string> tags = new List<string>();
            foreach (BigQueryRow row in client.GetQueryResults(job.Reference))
            {
                var tag = $"{row["event_name"]}";
                tags.Add(tag);
            }
            return tags;
        }

        void CreateTableIfNotExist(string tableName)
        {
            string createQuery = @"
            CREATE TABLE IF NOT EXISTS " + tableName + " (event_name STRING, event_params ARRAY<STRUCT<key STRING>>)";

            BigQueryJob job = client.CreateQueryJob(
                                sql: createQuery,
                                parameters: null,
                                options: new QueryOptions { UseQueryCache = false });
            job.PollUntilCompleted();
            _ = client.GetQueryResults(job.Reference);
        }
    }
}
