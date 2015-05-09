using System;
using System.Collections.Generic;
using System.Data;
using System.Data.Entity;
using System.Data.Entity.Infrastructure;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Threading.Tasks;
using System.Web.Http;
using System.Web.Http.ModelBinding;
using System.Web.Http.OData;
using System.Web.Http.OData.Routing;
using LocalAccountsApp;

namespace LocalAccountsApp.Controllers
{
    /*
    The WebApiConfig class may require additional changes to add a route for this controller. Merge these statements into the Register method of the WebApiConfig class as applicable. Note that OData URLs are case sensitive.

    using System.Web.Http.OData.Builder;
    using System.Web.Http.OData.Extensions;
    using LocalAccountsApp;
    ODataConventionModelBuilder builder = new ODataConventionModelBuilder();
    builder.EntitySet<Groups>("Groups");
    config.Routes.MapODataServiceRoute("odata", "odata", builder.GetEdmModel());
    */
    public class GroupsController : ODataController
    {
        private WGAppEntities db = new WGAppEntities();

        // GET: odata/Groups
        [EnableQuery]
        public IQueryable<Groups> GetGroups()
        {
            return db.Groups;
        }

        // GET: odata/Groups(5)
        [EnableQuery]
        public SingleResult<Groups> GetGroups([FromODataUri] int key)
        {
            return SingleResult.Create(db.Groups.Where(groups => groups.GroupID == key));
        }

        // PUT: odata/Groups(5)
        public async Task<IHttpActionResult> Put([FromODataUri] int key, Delta<Groups> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            Groups groups = await db.Groups.FindAsync(key);
            if (groups == null)
            {
                return NotFound();
            }

            patch.Put(groups);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!GroupsExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(groups);
        }

        // POST: odata/Groups
        public async Task<IHttpActionResult> Post(Groups groups)
        {
            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            db.Groups.Add(groups);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateException)
            {
                if (GroupsExists(groups.GroupID))
                {
                    return Conflict();
                }
                else
                {
                    throw;
                }
            }

            return Created(groups);
        }

        // PATCH: odata/Groups(5)
        [AcceptVerbs("PATCH", "MERGE")]
        public async Task<IHttpActionResult> Patch([FromODataUri] int key, Delta<Groups> patch)
        {
            Validate(patch.GetEntity());

            if (!ModelState.IsValid)
            {
                return BadRequest(ModelState);
            }

            Groups groups = await db.Groups.FindAsync(key);
            if (groups == null)
            {
                return NotFound();
            }

            patch.Patch(groups);

            try
            {
                await db.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                if (!GroupsExists(key))
                {
                    return NotFound();
                }
                else
                {
                    throw;
                }
            }

            return Updated(groups);
        }

        // DELETE: odata/Groups(5)
        public async Task<IHttpActionResult> Delete([FromODataUri] int key)
        {
            Groups groups = await db.Groups.FindAsync(key);
            if (groups == null)
            {
                return NotFound();
            }

            db.Groups.Remove(groups);
            await db.SaveChangesAsync();

            return StatusCode(HttpStatusCode.NoContent);
        }

        protected override void Dispose(bool disposing)
        {
            if (disposing)
            {
                db.Dispose();
            }
            base.Dispose(disposing);
        }

        private bool GroupsExists(int key)
        {
            return db.Groups.Count(e => e.GroupID == key) > 0;
        }
    }
}
