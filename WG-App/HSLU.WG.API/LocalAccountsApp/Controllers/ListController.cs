using System;
using System.Collections;
using System.Collections.Generic;
using System.Web.Http;
using System.Web.Mvc;
using LocalAccountsApp.Models;
using Microsoft.Ajax.Utilities;

namespace LocalAccountsApp.Controllers
{
    public class ListController : ApiController
    {
        public ICollection<ListModel> Get()
        {
            return new List<ListModel>() {Get("")};
        }

        public ListModel Get(string id)
        {
            
            if (id == "42")
            {
                ListModel model = new ListModel();
                model.Id = 42;
                model.Name = "1337";
                model.Items = new List<ListItemModel>();
                model.Items.Add(new ListItemModel()
                {
                    CreateDate = DateTime.Now,
                    Id = 1,
                    ListId = 42,
                    Name = "Hello Item!"
                });
                return model;
            }
            return new ListModel(){Id = 1, Name = "Hello world!"};
        }
    }
}