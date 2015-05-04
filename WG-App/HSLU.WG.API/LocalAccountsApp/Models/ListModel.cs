using System.Collections.Generic;
using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace LocalAccountsApp.Models
{
    [Table("List")]
    public class ListModel
    {
        [Key]
        public int Id { get; set; }

        [Required]
        public string Name { get; set; }

        public virtual ICollection<ListItemModel> Items { get; set; } 
    }
}