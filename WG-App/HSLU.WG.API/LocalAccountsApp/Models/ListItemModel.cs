using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace LocalAccountsApp.Models
{

    [Table("ListItem")]
    public class ListItemModel
    {
        [Key]
        public int Id { get; set; }

        [ForeignKey("ListListItem")]
        public int ListId { get; set; }

        public virtual ListModel List { get; set; }

        public string Name { get; set; }

        public bool IsChecked { get; set; }

        public DateTime CreateDate { get; set; }

    }
}