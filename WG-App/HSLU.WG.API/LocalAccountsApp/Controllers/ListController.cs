// --------------------------------------------------------------------------------------------------------------------
// <copyright file="ListController.cs" company="">

namespace LocalAccountsApp.Controllers
{
    using System;
    using System.Collections.Generic;
    using System.Data.Entity;
    using System.Data.Entity.Infrastructure;
    using System.Text.RegularExpressions;
    using System.Threading.Tasks;
    using System.Web;
    using System.Web.Http;
    using System.Web.Http.Description;
    using System.Web.Http.OData;
    using System.Web.Http.Results;

    public class ListController : ApiController
    {
      
        [ResponseType(typeof(ICollection<ListEntity>))]
        public async Task<IHttpActionResult> GetListModel()
        {
            using (WGAppEntities context = new WGAppEntities())
            {
                ICollection<ListEntity> lists = await context.ListEntitySet.ToListAsync();
                return this.Ok(lists);
            }
        }

        [ResponseType(typeof(ListEntity))]
        public async Task<IHttpActionResult> GetListModel(string id)
        {
            string username = HttpContext.Current.User.Identity.Name;

            using (WGAppEntities context = new WGAppEntities())
            {
                ListEntity list = await context.ListEntitySet.FirstOrDefaultAsync(l => l.ListID.ToString() == id);

                if (list == null)
                {
                    return this.NotFound();
                }

                return this.Ok(list);
            }
        }

        public async Task<IHttpActionResult> Post (ListEntity listEntity)
        {
            using (WGAppEntities context = new WGAppEntities())
            {
                ListEntity list = await context.ListEntitySet.FirstOrDefaultAsync(x => x.ListID == listEntity.ListID);
                if (list == null)
                {
                    if (listEntity.ListID == 0)
                    {
                        int lastId = await context.ListEntitySet.MaxAsync(x => x.ListID);
                        listEntity.ListID = lastId + 1;
                    }
                    context.ListEntitySet.Add(listEntity);

                    await context.SaveChangesAsync();
                    return this.Ok(listEntity);
                }

                return NotFound();
            }

        }

        public async Task<IHttpActionResult> Put(ListEntity listEntity)
        {
            using (WGAppEntities context = new WGAppEntities())
            {
                ListEntity list = await context.ListEntitySet.FirstOrDefaultAsync(x => x.ListID == listEntity.ListID);
                if (list == null)
                {
                    return NotFound();
                }

                list.GroupsGroupID = listEntity.GroupsGroupID;
                list.ListName = listEntity.ListName;
                
                await context.SaveChangesAsync();

                return this.Ok(list);
            }
        }
    }
}