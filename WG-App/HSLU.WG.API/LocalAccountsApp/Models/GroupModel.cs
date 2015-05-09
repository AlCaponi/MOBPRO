using System;
using System.Collections.Generic;
using System.Data.Entity.Core.Objects;
using System.Linq;
using System.Web;

namespace LocalAccountsApp.Models
{
    public class GroupModel
    {
        private WGAppEntities WGAppEntities;

        public GroupModel()
        {

            //Initialize the ObjectContext
            WGAppEntities = new WGAppEntities();

            // Define a query that returns all Department  
            // objects and course objects, ordered by name.
            var groupQuery = from d in WGAppEntities.Groups
                                  orderby d.GroupName
                                  select d;
            try
            {
                ObjectResult or = ((ObjectQuery)groupQuery).Execute(MergeOption.AppendOnly);
            }
            catch (Exception ex)
            {
                
            }

        }
    }
}