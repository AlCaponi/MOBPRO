using System.Web.Http;

namespace LocalAccountsApp.Controllers
{
    using System;
    using System.Collections.Generic;
    using System.Data.Entity;
    using System.Linq;
    using System.Threading.Tasks;
    using System.Web;
    using System.Web.Http.Description;

    using Microsoft.Data.OData.Metadata;

    public class ListItemController : ApiController
    {
        public async Task<IHttpActionResult> Get()
        {
            using (WGAppEntities context = new WGAppEntities())
            {
                ICollection<ListItemEntity> list = await context.ListItemEntitySet.ToListAsync();
                return this.Ok(list);
            }
        }

        public async Task<IHttpActionResult> Get(int id)
        {
            using (WGAppEntities context = new WGAppEntities())
            {
                ICollection<ListItemEntity> list =
                    await context.ListItemEntitySet.Where(x => x.ListEntityListID == id).ToListAsync();
                return this.Ok(list);
            }
        }

        public async Task<IHttpActionResult> Put(ListItemEntity listItemEntity)
        {
            using (WGAppEntities context = new WGAppEntities())
            {
                ListItemEntity listItem = await context.ListItemEntitySet.FindAsync(listItemEntity.ListItemID);

                if (listItem != null)
                {
                    listItem.CreatedDate = listItemEntity.CreatedDate;
                    listItem.Name = listItemEntity.Name;
                    listItem.IsChecked = listItemEntity.IsChecked;
                    listItem.ListEntityListID = listItemEntity.ListEntityListID;

                    await context.SaveChangesAsync();
                    return this.Ok(listItemEntity);
                }
            }

            return this.NotFound();
        }

        public async Task<IHttpActionResult> Post(ListItemEntity listItemEntity)
        {
            using (WGAppEntities context = new WGAppEntities())
            {
                if (context.ListItemEntitySet.Any(x => x.ListItemID == listItemEntity.ListItemID) == false)
                {
                    context.ListItemEntitySet.Add(listItemEntity);
                    context.SaveChanges();
                    return this.Ok(listItemEntity);
                }
            }

            return this.NotFound();
        }

        //[ResponseType(typeof(ICollection<ListItemEntity>))]
        //public async Task<IHttpActionResult> GetListItem(string listId)
        //{
        //    string username = HttpContext.Current.User.Identity.Name;

        //    using (WGAppEntities context = new WGAppEntities())
        //    {
        //        ListEntity list = await context.ListEntitySet.FirstOrDefaultAsync(l => l.ListID.ToString() == listId);

        //        if (list == null)
        //        {
        //            return this.NotFound();
        //        }

        //        ICollection<ListItemEntity> items = await context.ListItemEntitySet.Where(x => x.ListEntityListID.ToString() == listId).ToListAsync();

        //        return this.Ok(items);
        //    }
        //}

    }
}